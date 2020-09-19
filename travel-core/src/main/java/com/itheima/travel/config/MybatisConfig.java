package com.itheima.travel.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Description：声明mybatis的配置
 * @Configuration:声明此类是配置类
 * @PropertySource:读取外部properties配置文件
 * @MapperScan:可以把dao的接口实例化并放到IOC容器中,TODO:注意:dao层的接口需要写上@Mapper注解
 *      com.itheima.travel.mapper 这个包下的dao接口为代码生成器生成的单表增删改查
 *      com.itheima.travel.mapperExt 这个包下的dao接口为我们自己写的多表增删改查
 *
 */
@Configuration
@PropertySource(value = "classpath:db.properties")
@MapperScan(basePackages = {"com.itheima.travel.mapper","com.itheima.travel.mapperExt"},sqlSessionFactoryRef = "sqlSessionFactoryBean")
public class MybatisConfig {

    @Value("${dataSource.driverClassName}")
    private String driverClassName;

    @Value("${dataSource.url}")
    private String url;

    @Value("${dataSource.username}")
    private String username;

    @Value("${dataSource.password}")
    private String password;

    @Value("${seq.workerId}")
    private String workerId;

    @Value("${seq.datacenterId}")
    private String datacenterId;

    /**
     * @Description 数据源配置
     * 细节：默认不指定名称使用当前方法名为bean的名称
     */
    @Bean("druidDataSource")
    public DruidDataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * @Description 配置事务管理器
     * 细节：名称必须是：transactionManager，如果更换需要在使用时指定
     */
    @Bean("transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("druidDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * @Description 配置会话管理器
     */
    @Bean("sqlSessionFactoryBean")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("druidDataSource") DataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //指定数据源
        sqlSessionFactoryBean.setDataSource(dataSource);
        //指定对应的别名
        sqlSessionFactoryBean.setTypeAliasesPackage("com.itheima.travel.pojo");
        //指定核心xml的配置
//        try {
//            sqlSessionFactoryBean.setMapperLocations(
//                    new PathMatchingResourcePatternResolver()
//                    .getResources("sqlMapper/*Mapper.xml"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //指定高级配置：驼峰、是否返回主键、缓存、日志
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setLogImpl(Log4j2Impl.class);
        sqlSessionFactoryBean.setConfiguration(configuration);
        //插接
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{initPageInterceptor()});
        return sqlSessionFactoryBean;
    }

    /**
     * @Description 分页插件
     */
    @Bean
    public PageInterceptor initPageInterceptor(){
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

}
