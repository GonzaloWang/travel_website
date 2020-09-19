package com.itheima.travel.service;

import com.itheima.travel.basic.ListData;
import com.itheima.travel.basic.MapData;
import com.itheima.travel.basic.SingleData;

import java.util.List;
import java.util.Map;

/**
 * @Description: redis数据缓存服务
 */
public interface RedisCacheService {

    void setCurrentTest(boolean currentTest);

    /**
     * @Description 产生1800到3600秒之间的整数
     */
    Integer RandomTime();

    /**
     * @Description 限流器
     * @param key 单位时间内同一个人放为同一个方法同一个key能够访问的次数
     * * @return
     */
    void tryRateLimiter(String key);

    /**
     * @Description 缓存单个对象,需要注意的是这里如果之前缓存中有值，则会覆盖
     * @param key 格式:业务唯一key
     * @return
     */
    <T> T addSingleCache(T t, String key);

    /**
     * @Description 缓存单个对象,需要注意的是这里如果之前缓存中有值，则会覆盖
     * @param key 格式:业务唯一key
     * @param  seconds 超时时间
     * @return
     */
    <T> T addSingleCache(T t, String key, Long seconds);

    /**
     * @Description 查询对象：缓存不存在，查询数据，再返回，同时还要放入缓存中
     *              注意：缓存时间0.5-1小时之间
     * @param singleData 缓存对象
     * @return
     */
    <T> T singleCache(SingleData<T> singleData, String key);

    /**
     * @Description 查询对象：缓存不存在，查询数据，再返回，同时还要放入缓存中
     * @param singleData 缓存对象
     * @return
     */
    <T> T singleCache(SingleData<T> singleData, String key, Long seconds);

    /**
     * @Description 删除singleCache缓存
     */
    Boolean deleSingleCache(String key);


    /**
     * @Description 查询list对象：缓存不存在，查询数据，再返回，同时还要放入缓存中
     *              注意：缓存时间0.5-1小时之间
     * @param listData 缓存对象
     * @return
     */
    <T> List<T> listCache(ListData<T> listData, String key);

    /**
     * @Description 查询list对象：缓存不存在，查询数据，再返回，同时还要放入缓存中
     * @param listData 缓存对象
     * @return
     */
    <T> List<T> listCache(ListData<T> listData, String key, Long seconds);

    /**
     * @Description 删除list缓存
     */
    Boolean deleListCache(String key);

    /**
     * @Description 查询mapData对象缓存不存在，查询数据，再返回，同时还要放入缓存中
     *              注意：缓存时间0.5-1小时之间
     * @param mapData 缓存对象
     * @return
     */
    <K, V> Map<K, V> mapCache(MapData<K, V> mapData, String key);

    /**
     * @Description mapData对象添加缓存层并指定超时时间：缓存不存在，查询数据，再返回，同时还要放入缓存中
     * @param mapData 缓存对象
     * @return
     */
    <K, V> Map<K, V> mapCache(MapData<K, V> mapData, String key, Long seconds);

    /**
     * @Description 删除mapCache缓存
     */
    Boolean deleMapCache(String key);


}
