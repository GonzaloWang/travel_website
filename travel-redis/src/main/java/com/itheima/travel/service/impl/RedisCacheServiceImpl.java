package com.itheima.travel.service.impl;

import com.itheima.travel.basic.ListData;
import com.itheima.travel.basic.MapData;
import com.itheima.travel.basic.SingleData;
import com.itheima.travel.service.RedisCacheService;
import com.itheima.travel.session.*;
import com.itheima.travel.utils.EmptyUtil;
import com.itheima.travel.utils.ExceptionsUtil;
import com.itheima.travel.utils.MD5Coder;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Description：redis数据缓存服务
 */
@Log4j2
@Service
public class RedisCacheServiceImpl implements RedisCacheService {

    //分布式锁后缀
    public static final String LOCK_SUFFIX = "_lock";

    //默认redis等待时间
    public static final int REDIS_WAIT_TIME = 3000;

    //默认redis自动释放时间
    public static final int REDIS_LEASETIME = 1000;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    SubjectSellerContext subjectSellerContext;

    @Autowired
    SubjectUserContext subjectUserContext;

    @Autowired
    IpAddrHandler ipAddrHandler;

    //兼容多线程测试
    boolean isCurrentTest = false;

    @Override
    public void setCurrentTest(boolean currentTest) {
        isCurrentTest = currentTest;
    }

    @Override
    public Integer RandomTime(){
        int max=3600;
        int min=1800;
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }


    @Override
    public void tryRateLimiter(String key) {
        //key的值：findUserByid:10101011
        String keyHandler = key;
        String token = null;
        Integer rate = 10;
        if (!isCurrentTest){
            //如果登录，按用户限流
            SubjectSeller subjectSeller = subjectSellerContext.getSubject();
            if (!EmptyUtil.isNullOrEmpty(subjectSeller)){
                token = subjectSeller.getToken();
            }else {
                SubjectUser subjectUser = subjectUserContext.getSubject();
                if (!EmptyUtil.isNullOrEmpty(subjectUser)){
                    token=subjectUser.getToken();
                }
            }
            //如果未登录按IP限流
            if (EmptyUtil.isNullOrEmpty(token)){
                token = MD5Coder.md5Encode(ipAddrHandler.requestIpAddr());
                rate=2000;
            }
        }else {
            token = "current_test";
        }
        //keyHandler的值：findUserByid:10101011sdfsdf-sdfsdf-sdfsdfsd-sdfs__rate_limiter
        keyHandler = keyHandler+":"+token+"_rate_limiter";
        // 初始化
        RRateLimiter rateLimiter=redissonClient.getRateLimiter(keyHandler);
        // //设置访问速率，var2为访问数，var3为单位时间，var4为时间单位 最大流速 = 每1秒钟产生10个令牌
        rateLimiter.trySetRate(RateType.OVERALL,
                rate, 1, RateIntervalUnit.SECONDS);
        //从这个RateLimiter获得一个许可，阻塞直到有一个可用
        rateLimiter.acquire();
    }

    @Override
    public <T> T addSingleCache(T t, String key) {
        return addSingleCache(t, key, null);
    }

    @Override
    public <T> T addSingleCache(T t, String key,Long seconds) {
        //限流
        this.tryRateLimiter(key);
        //创建桶
        RBucket<T> bucket = redissonClient.getBucket(key);
        bucket.set(t, EmptyUtil.isNullOrEmpty(seconds) ? RandomTime() : seconds,
                TimeUnit.SECONDS);
        return t;
    }

    @Override
    public <T> T singleCache(SingleData<T> singleData, String key) {
        return singleCache(singleData, key,null);
    }

    @Override
    public <T> T singleCache(SingleData<T> singleData, String key, Long seconds) {
        //1、限流：为1秒时间内，同一个方法，同一个人，同一个业务key能访问10次
        this.tryRateLimiter(key);
        //2、构建桶位
        RBucket<T> bucket = redissonClient.getBucket(key);
        //3、缓存中获得对象
        T t = bucket.get();
        //4、获得对象为空
        if (EmptyUtil.isNullOrEmpty(t)) {
            //5、构建锁桶位
            RLock lock = redissonClient.getLock(key + LOCK_SUFFIX);
            try {
                //6、锁等待时间为10秒，默认释放时间1.5秒
                if (lock.tryLock(REDIS_WAIT_TIME,REDIS_LEASETIME,TimeUnit.MILLISECONDS)) {
                    //7、并发情况下，在10秒内阻塞的线程会再次从缓存中获得对象，最大可能性防止非空返回
                    t = bucket.get();
                    if (!EmptyUtil.isNullOrEmpty(t)){
                        log.info("等待线程:对象返回:{}",t);
                        return t;
                    }
                    try {
                        log.info("加锁，放过第一个请求");
                        //8、执行默认方法，查询数据库
                        t = singleData.getData();
                        //9、查询出对象，且指定超时时间
                        if (!EmptyUtil.isNullOrEmpty(t)) {
                            log.info("放入缓存");
                            bucket.set(t, EmptyUtil.isNullOrEmpty(seconds) ? RandomTime() : seconds,
                                    TimeUnit.SECONDS);
                        }
                        log.info("执行数据查询后返回");
                        return t;
                    }catch (Exception e){
                        //10、如果执行业务体发生异常则返回空对象
                        log.error("业务执行异常：{}", ExceptionsUtil.getStackTraceAsString(e));
                        return null;
                    }finally {
                        //11、最终释放所有锁，防止缓存的阻塞
                        log.info("快速释放锁，防止阻塞");
                        lock.unlock();
                    }
                }else {
                    //12、并发情况下，等待3秒超时的线程会再次从缓存中获得对象，最大可能性防止非空返回
                    t = bucket.get();
                    if (!EmptyUtil.isNullOrEmpty(t)){
                        log.info("超时线程:对象返回:{}",t);
                        return t;
                    }
                }
            } catch (InterruptedException e) {
                //13、拿锁异常抛出，直接返回空，且在1.5秒内自动释放锁
                log.info("熔断，直接返回空");
                return null;
            }
        }
        //14、缓存对象不为空直接返回
        log.info("缓存返回:{}",t);
        return t;
    }

    @Override
    public Boolean deleSingleCache(String key) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        if (!EmptyUtil.isNullOrEmpty(bucket)){
            return bucket.delete();
        }
        return true;
    }

    @Override
    public <T> List<T> listCache(ListData<T> listData, String key) {
        return listCache(listData, key, null);
    }

    @Override
    public <T> List<T> listCache(ListData<T> listData, String key, Long seconds) {
        //1、限流：为1秒时间内，同一个方法，同一个人，同一个业务key能访问10次
        this.tryRateLimiter(key);
        //2、构建桶位
        RList<T> list = redissonClient.getList(key);
        //3、缓存中获得对象
        List<T> t = list.readAll();
        //4、获得对象为空
        if (EmptyUtil.isNullOrEmpty(t)) {
            //5、构建锁桶位
            RLock lock = redissonClient.getLock(key + LOCK_SUFFIX);
            try {
                //6、锁等待时间为3秒，默认释放时间1.5秒
                if ( lock.tryLock(REDIS_WAIT_TIME, REDIS_LEASETIME,TimeUnit.MILLISECONDS)) {
                    //7、并发情况下，在10秒内阻塞的线程会再次从缓存中获得对象，最大可能性防止非空返回
                    t = list.readAll();
                    if (!EmptyUtil.isNullOrEmpty(t)){
                        log.info("等待线程:对象返回:{}",t);
                        return t;
                    }
                    try {
                        log.info("加锁，放过第一个请求");
                        //8、执行默认方法，查询数据库
                        t = listData.getData();
                        //9、查询出对象，且指定超时时间
                        if (!EmptyUtil.isNullOrEmpty(t)) {
                            list.delete();
                            list.addAll(t);
                            list.expire(EmptyUtil.isNullOrEmpty(seconds) ? RandomTime() : seconds,
                                    TimeUnit.SECONDS);
                            log.info("放入缓存");
                        }

                        log.info("执行数据查询后返回");
                        return list;
                    }catch (Exception e){
                        //10、如果执行业务体发生异常则返回空对象
                        log.error("业务执行异常：{}", ExceptionsUtil.getStackTraceAsString(e));
                        return null;
                    }finally {
                        //11、最终释放所有锁，防止缓存的阻塞
                        log.info("快速释放锁，防止阻塞");
                        lock.unlock();
                    }
                }else {
                    //12、并发情况下，等待3秒超时的线程会再次从缓存中获得对象，最大可能性防止非空返回
                    t = list.readAll();
                    if (!EmptyUtil.isNullOrEmpty(t)){
                        log.info("超时线程:对象返回:{}",t);
                        return t;
                    }
                }
            } catch (InterruptedException e) {
                //13、拿锁异常抛出，直接返回空，且在1.5秒内自动释放锁
                log.info("熔断，直接返回空");
                return null;
            }
        }
        //14、缓存对象不为空直接返回
        log.info("缓存返回:{}",t);
        return t;
    }

    @Override
    public Boolean deleListCache(String key) {
        RList<Object> list = redissonClient.getList(key);
        if (!EmptyUtil.isNullOrEmpty(list)){
            return list.delete();
        }
        return true;
    }


    @Override
    public <K, V> Map<K, V> mapCache(MapData<K, V> mapData, String key) {
        return mapCache(mapData, key, null);
    }


    @Override
    public <K, V> Map<K, V> mapCache(MapData<K, V> mapData, String key, Long seconds) {
        //1、限流：为1秒时间内，同一个方法，同一个人，同一个业务key能访问10次
        this.tryRateLimiter(key);
        //2、构建桶位
        RMapCache<K, V> rMapCache = redissonClient.getMapCache(key);
        //3、缓存中获得对象
        Map<K, V> map = rMapCache.readAllMap();
        //4、获得对象为空
        if (EmptyUtil.isNullOrEmpty(map)) {
            //5、构建锁桶位
            RLock lock = redissonClient.getLock(key + LOCK_SUFFIX);
            try {
                //6、锁等待时间为10秒，默认释放时间1.5秒
                if (lock.tryLock(REDIS_WAIT_TIME, REDIS_LEASETIME,TimeUnit.MILLISECONDS)) {
                    //7、并发情况下，在10秒内阻塞的线程会再次从缓存中获得对象，最大可能性防止非空返回
                    map = rMapCache.readAllMap();
                    if (!EmptyUtil.isNullOrEmpty(map)){
                        log.info("等待线程:对象返回:{}",map);
                        return map;
                    }
                    try {
                        log.info("加锁，放过第一个请求");
                        //8、执行默认方法，查询数据库
                        map = mapData.getData();
                        //9、查询出对象，且指定超时时间
                        if (!EmptyUtil.isNullOrEmpty(map)) {
                            rMapCache.delete();
                            rMapCache.putAll(map);
                            rMapCache.expire(EmptyUtil.isNullOrEmpty(seconds) ? RandomTime() : seconds,
                                    TimeUnit.SECONDS);
                            log.info("放入缓存");
                        }
                        log.info("执行数据查询后返回");
                        return rMapCache;
                    }catch (Exception e){
                        //10、如果执行业务体发生异常则返回空对象
                        log.error("业务执行异常：{}", ExceptionsUtil.getStackTraceAsString(e));
                        return null;
                    }finally {
                        //11、最终释放所有锁，防止缓存的阻塞
                        log.info("快速释放锁，防止阻塞");
                        lock.unlock();
                    }
                }else {
                    //13、并发情况下，等待3秒超时的线程会再次从缓存中获得对象，最大可能性防止非空返回
                    map = rMapCache.readAllMap();
                    if (!EmptyUtil.isNullOrEmpty(map)){
                        log.info("等待线程:对象返回:{}",map);
                        return map;
                    }
                }
            } catch (InterruptedException e) {
                //14、拿锁异常抛出，直接返回空，且在1.5秒内自动释放锁
                log.info("熔断，直接返回空");
                return null;
            }
        }
        //13、缓存对象不为空直接返回
        log.info("缓存返回:{}",map);
        return map;
    }

    @Override
    public Boolean deleMapCache(String key) {
        RMapCache<Object, Object> mapCache = redissonClient.getMapCache(key);
        if (!EmptyUtil.isNullOrEmpty(mapCache)){
           return mapCache.delete();
        }
        return true;
    }


}
