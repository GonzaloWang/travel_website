package com.itheima.travel.mapperExt;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @Description：线路扩展mapper
 */
@Mapper
public interface RouteMapperExt {
    /**
     * @Description 修改线路统计
     * @param routeId 线路ID
     * @return
     */
    @Update("update tab_route set attention_count = attention_count+1 where id = #{routeId}" )
    Long updateRouteCountById(Long routeId);
}
