package com.itheima.travel.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 对象转换工具
 */
@Log4j2
public class BeanConv {

    public static <T, X> T toBean(X entity, T targetEntity) {

        try {
            PropertyUtils.copyProperties(targetEntity, entity);
        } catch (Exception e) {
            log.error("对象转换出错：{}",ExceptionsUtil.getStackTraceAsString(e));
        }
        return targetEntity;
    }

    public static <T, X> T toBean(X entity, Class<T> cls) {
        if (EmptyUtil.isNullOrEmpty(entity)) {
            return null;
        }
        T t = null;
        try {
            t = cls.newInstance();
            ConvertUtils.register(new DateConverter(null), Date.class);
            PropertyUtils.copyProperties(t, entity);
        } catch (Exception e) {
            log.error("对象转换出错：{}",ExceptionsUtil.getStackTraceAsString(e));
        }
        return t;
    }

    public static <T, X> List<T> toBeanList(List<X> list, Class<T> cls) {
        if (EmptyUtil.isNullOrEmpty(list)) {
            return new ArrayList<T>();
        }
        List<T> result = new ArrayList<>();
        list.forEach(entity -> {
            result.add(toBean(entity, cls));
        });
        return result;
    }


}
