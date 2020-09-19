package com.itheima.travel.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import com.itheima.travel.interceptor.PrimaryKeyInterceptor;
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
 * TODO:Mybatis的核心配置类
 * @Description：声明mybatis的配置
 * @PropertySource(value = "classpath:db.properties") : 加载properties配置文件
 * @MapperScan: 扫描dao层对应的接口,生成接口的实现类,生成时需要依赖sqlSessionFactoryBean
 *          在dao层的接口上需要添加 @Mapper注解
 *          com.itheima.travel.mapper:
 *                 这个包下的dao接口为代码生成器生成的单表增删改查
 *          com.itheima.travel.mapperExt:
 *                  这个包下的dao接口为我们自己编写的多表的增删改查
 */
@Configuration
//读取外部配置文件
@PropertySource(value = "classpath:db.properties")
@MapperScan(
        basePackages =
                {"com.itheima.travel.mapper",
                        "com.itheima.travel.mapperExt"},
        sqlSessionFactoryRef = "sqlSessionFactoryBean"
)
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
        //指定高级配置：驼峰、是否返回主键、缓存、日志、插件
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        // 设置Log4j2日志的支持
        configuration.setLogImpl(Log4j2Impl.class);
        // Pagehelper插件
        // 配置一个拦截器,在执行insert操作时,调用雪花算法,
        // 生成一个唯一数字,将生成的数值作为新增记录的id
        sqlSessionFactoryBean.setPlugins(
                new Interceptor[]{
                        initPageInterceptor(),
                        initPrimaryKeyInterceptor()
                });

        // 将相关的配置信息交个sqlSessionFactoryBean使用
        sqlSessionFactoryBean.setConfiguration(configuration);
        return sqlSessionFactoryBean;
    }

    /**
     * 创建雪花算法工具对象,存放到IOC容器中
     * @Description 雪花算法支持
     */
    @Bean
    public SnowflakeIdWorker snowflakeIdWorker(){
        // 机房编号  机器编号
        return new SnowflakeIdWorker(Integer.valueOf(workerId), Integer.valueOf(datacenterId));
    }

    /**
     * @Description 主键生成拦截
     */
    @Bean
    public PrimaryKeyInterceptor initPrimaryKeyInterceptor(){
        // 创建拦截器对象
        PrimaryKeyInterceptor primaryKeyInterceptor =  new PrimaryKeyInterceptor();
        // 设置拦截器的相关参数信息
        Properties properties = new Properties();
        // primaryKey: 自定义的键名称
        // id: 需要替换的字段
        properties.setProperty("primaryKey", "id");
        primaryKeyInterceptor.setProperties(properties);
        // 给主键生成拦截器传入雪花算法工具类
        primaryKeyInterceptor.setSnowflakeIdWorker(snowflakeIdWorker());
        return primaryKeyInterceptor;
    }

    /**
     * @Description 分页插件
     */
    @Bean
    public PageInterceptor initPageInterceptor(){
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        // 数据库方言(对那个数据库进行分页)
        properties.setProperty("helperDialect", "mysql");
        // 分页偏移量设置(默认值为false,需要开启)
        // 开启后可以使用当前页页码与每页显示条数进行分页
        properties.setProperty("offsetAsPageNum", "true");
        // 开启分页时查询总数量
        properties.setProperty("rowBoundsWithCount", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

}
