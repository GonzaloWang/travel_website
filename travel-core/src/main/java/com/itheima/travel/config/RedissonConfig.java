package com.itheima.travel.config;

import lombok.extern.log4j.Log4j2;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * rsson操作redis配置类
 **/
@Configuration
@Log4j2
@PropertySource(value = "classpath:db.properties")
public class RedissonConfig {

    /**
     * redis连接地址
     */
    @Value("${redis.nodes}")
    private String nodes;

    /**
     * 获取连接超时时间
     */
    @Value("${redis.connectTimeout}")
    private int connectTimeout;

    /**
     * 最小空闲连接数
     */
    @Value("${redis.connectPoolSize}")
    private int connectPoolSize;

    /**
     * 最小连接数
     */
    @Value("${redis.connectionMinimumidleSize}")
    private int connectionMinimumidleSize;

    /**
     * 等待数据返回超时时间
     */
    @Value("${redis.timeout}")
    private int timeout;

    /**
     * 刷新时间
     */
    @Value("${redis.retryInterval}")
    private int retryInterval;

    @Bean(value = "redissonClient",destroyMethod="shutdown")
    public RedissonClient config() {
        log.info("=====初始化RedissonClient开始======");
        String[] nodeList = nodes.split(",");
        Config config = new Config();
        //单节点
        if (nodeList.length == 1) {
            config.useSingleServer().setAddress(nodeList[0])
                    .setConnectTimeout(connectTimeout)
                    .setConnectionMinimumIdleSize(connectPoolSize)
                    .setConnectionPoolSize(connectPoolSize)
                    .setTimeout(timeout);
            //集群节点
        } else {
            config.useClusterServers().addNodeAddress(nodeList)
                    .setConnectTimeout(connectTimeout)
                    .setRetryInterval(retryInterval)
                    .setMasterConnectionMinimumIdleSize(connectionMinimumidleSize)
                    .setMasterConnectionPoolSize(connectPoolSize)
                    .setSlaveConnectionMinimumIdleSize(connectionMinimumidleSize)
                    .setSlaveConnectionPoolSize(connectPoolSize)
                    .setTimeout(3000);
        }
        log.info("=====初始化RedissonClient完成======");
        return Redisson.create(config);
    }

}
