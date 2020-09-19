package com.itheima.travel.mapperExt;

import com.itheima.travel.pojo.Route;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @Description：收藏扩展Mapper
 */
@Mapper
public interface FavoriteMapperExt {

    @Select({
            "select",
            "r.id, r.route_name, r.price, r.route_Introduce, r.flag, r.is_theme_tour, r.attention_count, r.category_id, ",
            "r.seller_id, created_time",
            "from tab_favorite f left join tab_route r on f.route_id = r.id ",
            "where f.user_id = #{userId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="route_name", property="routeName", jdbcType=JdbcType.VARCHAR),
            @Result(column="price", property="price", jdbcType=JdbcType.DECIMAL),
            @Result(column="route_Introduce", property="routeIntroduce", jdbcType=JdbcType.VARCHAR),
            @Result(column="flag", property="flag", jdbcType=JdbcType.CHAR),
            @Result(column="is_theme_tour", property="isThemeTour", jdbcType=JdbcType.CHAR),
            @Result(column="attention_count", property="attentionCount", jdbcType=JdbcType.INTEGER),
            @Result(column="category_id", property="categoryId", jdbcType=JdbcType.BIGINT),
            @Result(column="seller_id", property="sellerId", jdbcType=JdbcType.BIGINT),
            @Result(column="created_time", property="createdTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Route> findFavoriteByUserId(Long userId);
}
