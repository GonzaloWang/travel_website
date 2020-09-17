package com.itheima.travel.mapper;

import com.itheima.travel.pojo.Route;
import com.itheima.travel.pojo.RouteExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface RouteMapper {
    @SelectProvider(type=RouteSqlProvider.class, method="countByExample")
    long countByExample(RouteExample example);

    @DeleteProvider(type=RouteSqlProvider.class, method="deleteByExample")
    int deleteByExample(RouteExample example);

    @Delete({
        "delete from tab_route",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into tab_route (route_name, price, ",
        "route_Introduce, flag, ",
        "is_theme_tour, attention_count, ",
        "category_id, seller_id, ",
        "created_time)",
        "values (#{routeName,jdbcType=VARCHAR}, #{price,jdbcType=DECIMAL}, ",
        "#{routeIntroduce,jdbcType=VARCHAR}, #{flag,jdbcType=CHAR}, ",
        "#{isThemeTour,jdbcType=CHAR}, #{attentionCount,jdbcType=INTEGER}, ",
        "#{categoryId,jdbcType=BIGINT}, #{sellerId,jdbcType=BIGINT}, ",
        "#{createdTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Route record);

    @InsertProvider(type=RouteSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(Route record);

    @SelectProvider(type=RouteSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
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
    List<Route> selectByExample(RouteExample example);

    @Select({
        "select",
        "id, route_name, price, route_Introduce, flag, is_theme_tour, attention_count, ",
        "category_id, seller_id, created_time",
        "from tab_route",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
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
    Route selectByPrimaryKey(Long id);

    @UpdateProvider(type=RouteSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Route record, @Param("example") RouteExample example);

    @UpdateProvider(type=RouteSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Route record, @Param("example") RouteExample example);

    @UpdateProvider(type=RouteSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Route record);

    @Update({
        "update tab_route",
        "set route_name = #{routeName,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=DECIMAL},",
          "route_Introduce = #{routeIntroduce,jdbcType=VARCHAR},",
          "flag = #{flag,jdbcType=CHAR},",
          "is_theme_tour = #{isThemeTour,jdbcType=CHAR},",
          "attention_count = #{attentionCount,jdbcType=INTEGER},",
          "category_id = #{categoryId,jdbcType=BIGINT},",
          "seller_id = #{sellerId,jdbcType=BIGINT},",
          "created_time = #{createdTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Route record);
}