package com.itheima.travel.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @Description：声明spring的配置
 * Configuration:声明此类为配置类，这里替换原有的spring.xml文件
 * ComponentScan:约定大于配置，扫描除web之外的所有的类
 * EnableTransactionManagement:开启事务管理器
 * EnableAspectJAutoProxy:开启aop的支持
 */
@Configuration
@ComponentScan(value = "com.itheima.travel",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,
                classes = {Controller.class, ControllerAdvice.class})})
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class SpringConfig {
}
