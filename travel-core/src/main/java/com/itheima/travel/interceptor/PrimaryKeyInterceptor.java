package com.itheima.travel.interceptor;

import com.itheima.travel.config.SnowflakeIdWorker;
import com.itheima.travel.utils.EmptyUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 这个类的实现方式与pagehelp插件类型，不同的是，
 * 在执行update操作的时候进行处理，当然这里的所值
 * 的update操作指定的sql操作：instert语句
 * @Description 主键雪花算法
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class,Object.class})})
@Log4j2
public class PrimaryKeyInterceptor implements Interceptor {

    //主键生成策略
    private SnowflakeIdWorker snowflakeIdWorker;

    //主键标识
    private String primaryKey ;

    public PrimaryKeyInterceptor() {

    }

    public PrimaryKeyInterceptor(SnowflakeIdWorker snowflakeIdWorker) {
        this.snowflakeIdWorker = snowflakeIdWorker;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        if (args == null || args.length != 2) {
            return invocation.proceed();
        } else {
            MappedStatement ms = (MappedStatement) args[0];
            // 操作类型
            SqlCommandType sqlCommandType = ms.getSqlCommandType();
            // 只处理insert操作
            if (!EmptyUtil.isNullOrEmpty(sqlCommandType) && sqlCommandType == SqlCommandType.INSERT) {
                if (args[1] instanceof Map) {
                    // 批量插入
                    List list = (List) ((Map) args[1]).get("list");
                    for (Object obj : list) {
                        setProperty(obj, primaryKey, snowflakeIdWorker.nextId());
                    }
                } else {
                    setProperty(args[1], primaryKey, snowflakeIdWorker.nextId());
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {

        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
        //指定主键
        primaryKey = properties.getProperty("primaryKey");
        if (EmptyUtil.isNullOrEmpty(primaryKey)){
            primaryKey="id";
        }
    }

    /**
     * 设置对象属性值
     *
     * @param obj      对象
     * @param property 属性名称
     * @param value    属性值
     */
    private void setProperty(Object obj, String property, Object value)
            throws NoSuchFieldException,
            IllegalAccessException,
            InvocationTargetException {

        Field field = obj.getClass().getDeclaredField(property);
        if (!EmptyUtil.isNullOrEmpty(field)) {
            field.setAccessible(true);
            Object val = field.get(obj);
            if (EmptyUtil.isNullOrEmpty(val)) {
                BeanUtils.setProperty(obj, property, value);
            }
        }
    }

    public void setSnowflakeIdWorker(SnowflakeIdWorker snowflakeIdWorker) {
        this.snowflakeIdWorker = snowflakeIdWorker;
    }
}
