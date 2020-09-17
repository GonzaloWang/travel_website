package com.itheima.travel.mapper;

import com.itheima.travel.pojo.Seller;
import com.itheima.travel.pojo.SellerExample;
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
public interface SellerMapper {
    @SelectProvider(type=SellerSqlProvider.class, method="countByExample")
    long countByExample(SellerExample example);

    @DeleteProvider(type=SellerSqlProvider.class, method="deleteByExample")
    int deleteByExample(SellerExample example);

    @Delete({
        "delete from tab_seller",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into tab_seller (seller_name, telephone, ",
        "address, username, ",
        "password, birthday)",
        "values (#{sellerName,jdbcType=VARCHAR}, #{telephone,jdbcType=VARCHAR}, ",
        "#{address,jdbcType=VARCHAR}, #{username,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{birthday,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Seller record);

    @InsertProvider(type=SellerSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(Seller record);

    @SelectProvider(type=SellerSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="seller_name", property="sellerName", jdbcType=JdbcType.VARCHAR),
        @Result(column="telephone", property="telephone", jdbcType=JdbcType.VARCHAR),
        @Result(column="address", property="address", jdbcType=JdbcType.VARCHAR),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="birthday", property="birthday", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Seller> selectByExample(SellerExample example);

    @Select({
        "select",
        "id, seller_name, telephone, address, username, password, birthday",
        "from tab_seller",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="seller_name", property="sellerName", jdbcType=JdbcType.VARCHAR),
        @Result(column="telephone", property="telephone", jdbcType=JdbcType.VARCHAR),
        @Result(column="address", property="address", jdbcType=JdbcType.VARCHAR),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="birthday", property="birthday", jdbcType=JdbcType.TIMESTAMP)
    })
    Seller selectByPrimaryKey(Long id);

    @UpdateProvider(type=SellerSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Seller record, @Param("example") SellerExample example);

    @UpdateProvider(type=SellerSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Seller record, @Param("example") SellerExample example);

    @UpdateProvider(type=SellerSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Seller record);

    @Update({
        "update tab_seller",
        "set seller_name = #{sellerName,jdbcType=VARCHAR},",
          "telephone = #{telephone,jdbcType=VARCHAR},",
          "address = #{address,jdbcType=VARCHAR},",
          "username = #{username,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "birthday = #{birthday,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Seller record);
}