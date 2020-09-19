package com.itheima.travel.service;

import com.itheima.travel.basic.TestConfig;
import com.itheima.travel.pojo.User;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;

/**
 * @Description：
 */
@Log4j2
public class RedisCacheServiceTest extends TestConfig {

    /**
     * @Description 支持多线程测试，防止当前多线程下session的绑定问题
     */
    @Before
    public void before(){
        redisCacheService.setCurrentTest(true);
    }

    /**
     * 缓存穿透是指缓存和数据库中都没有的数据，而用户不断发起请求，比如发起查询请求,根据id“101010”查询数据,但是数据不存在。这时的用户很可能是攻击者，会不停的发起请求攻击数据库，这样会导致数据库压力过大
     *
     * ==限制的目标：限制同一个人在单位时间内访问同一个路径的次数-==
     * 给每个用户一个令牌,1秒有10次机会,用完就不允许访问同一个路径了,下一秒刷新令牌
     * @throws InterruptedException
     */
    @Test
    public void TestTryRateLimiter() throws InterruptedException {
        //初始化50个线程，让每个线程执行一个请求
        ExecutorService executorServicePool = Executors.newFixedThreadPool(50);
        //初始化等待数：50
        //外部阻塞
        CountDownLatch countDownLatch = new CountDownLatch(50);
        //内部阻塞
        CyclicBarrier cyclicBarrier = new CyclicBarrier(50);
        for (int i = 0; i < 50; i++) {
            executorServicePool.execute(()-> {
                //阻塞
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info("请求线程"+Thread.currentThread().getId()+"时间：{}",new Date());
                redisCacheService.tryRateLimiter("tryRateLimiter_101010101");
                log.info("处理线程"+Thread.currentThread().getId()+"时间：{}",new Date());
                //等待数-1，挂起当前线程
                countDownLatch.countDown();
            });
        }
        //等待,计数为0时一次性执行，模拟50个并发
        countDownLatch.await();
    }

    @Test
    public void testAddSingleCache() throws InterruptedException {

        String key ="testAddSingleCache_101010101";
        //初始化50个线程，让每个线程执行一个请求
        ExecutorService executorServicePool = Executors.newFixedThreadPool(50);
        //初始化等待数：50
        //外部阻塞
        CountDownLatch countDownLatch = new CountDownLatch(50);
        //内部阻塞
        CyclicBarrier cyclicBarrier = new CyclicBarrier(50);
        for (int i = 0; i < 50; i++) {
            executorServicePool.execute(()-> {
                //阻塞
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info("请求线程"+Thread.currentThread().getId()+"时间：{}",new Date());
                User user = User.builder().id(1111L).build();
                redisCacheService.addSingleCache(user, key);
                log.info("处理线程"+Thread.currentThread().getId()+"时间：{}",new Date());
                //等待数-1，挂起当前线程
                countDownLatch.countDown();
            });
        }
        //等待,计数为0时一次性执行，模拟50个并发
        countDownLatch.await();
    }


    @Test
    public void testDeleSingleCache() throws InterruptedException {
        String key ="testAddSingleCache_101010101";
        redisCacheService.deleSingleCache(key);
    }

    @Test
    public void testSingleCache() throws InterruptedException {

        String key ="testSingleCache_101010101";
        //初始化50个线程，让每个线程执行一个请求
        ExecutorService executorServicePool = Executors.newFixedThreadPool(50);
        //初始化等待数：50
        //外部阻塞
        CountDownLatch countDownLatch = new CountDownLatch(50);
        //内部阻塞
        CyclicBarrier cyclicBarrier = new CyclicBarrier(50);
        for (int i = 0; i < 50; i++) {
            executorServicePool.execute(()-> {
                //阻塞
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info("请求线程"+Thread.currentThread().getId()+"时间：{}",new Date());
                redisCacheService.singleCache(() -> {
                    log.info("执行数据查询");
                    return User.builder().id(1111L).build();
                }, key);
                log.info("处理线程" + Thread.currentThread().getId() + "时间：{}", new Date());
                //等待数-1，挂起当前线程
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
    }


    @Test
    public void testListCache() throws InterruptedException {

        String key ="testListCache_101010101";
        //初始化50个线程，让每个线程执行一个请求
        ExecutorService executorServicePool = Executors.newFixedThreadPool(50);
        //外部阻塞
        CountDownLatch countDownLatch = new CountDownLatch(50);
        //内部阻塞
        CyclicBarrier cyclicBarrier = new CyclicBarrier(50);
        for (int i = 0; i < 50; i++) {
            executorServicePool.execute(()-> {
                //阻塞线程等待
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.info("请求线程"+Thread.currentThread().getId()+"时间：{}",new Date());
                redisCacheService.listCache(() -> {
                    log.info("执行数据查询");
                    List<User> list = new ArrayList<>();
                    list.add(User.builder().id(1111L).build());
                    return list;
                }, key);
                log.info("处理线程" + Thread.currentThread().getId() + "时间：{}", new Date());
                //等待数-1，
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
    }


    @Test
    public void testDeleListCache() throws InterruptedException {
        String key ="testListCache_101010101";
        redisCacheService.deleSingleCache(key);

    }

    @Test
    public void testMapCache() throws InterruptedException {

        String key ="testMapCache_101010101";
        ///初始化50个线程，让每个线程执行一个请求
        ExecutorService executorServicePool = Executors.newFixedThreadPool(50);
        //外部阻塞
        CountDownLatch countDownLatch = new CountDownLatch(50);
        //内部阻塞
        CyclicBarrier cyclicBarrier = new CyclicBarrier(50);
        for (int i = 0; i < 50; i++) {
            executorServicePool.execute(()-> {
                log.info("请求线程"+Thread.currentThread().getId()+"时间：{}",new Date());
                //阻塞线程等待
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                redisCacheService.mapCache(() -> {
                    log.info("执行数据查询");
                    Map<Integer,User> map = new HashMap<>();
                    map.put(1111,User.builder().id(1111L).build());
                    return map;
                }, key);
                log.info("处理线程" + Thread.currentThread().getId() + "时间：{}", new Date());
                //等待数-1，挂起当前线程
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
    }

    @Test
    public void testDeleMapCache() throws InterruptedException {

        String key ="testMapCache_101010101";
        redisCacheService.deleMapCache(key);
    }



}
