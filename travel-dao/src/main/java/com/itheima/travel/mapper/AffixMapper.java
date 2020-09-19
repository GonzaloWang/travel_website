package com.itheima.travel.mapper;

import com.itheima.travel.pojo.Affix;
import com.itheima.travel.pojo.AffixExample;
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
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface AffixMapper {
    @SelectProvider(type=AffixSqlProvider.class, method="countByExample")
    long countByExample(AffixExample example);

    @DeleteProvider(type=AffixSqlProvider.class, method="deleteByExample")
    int deleteByExample(AffixExample example);

    @Delete({
        "delete from tab_affix",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into tab_affix (id, business_id, ",
        "business_type, suffix, ",
        "file_name, path_url)",
        "values (#{id,jdbcType=BIGINT}, #{businessId,jdbcType=BIGINT}, ",
        "#{businessType,jdbcType=VARCHAR}, #{suffix,jdbcType=VARCHAR}, ",
        "#{fileName,jdbcType=VARCHAR}, #{pathUrl,jdbcType=VARCHAR})"
    })
    int insert(Affix record);

    @InsertProvider(type=AffixSqlProvider.class, method="insertSelective")
    int insertSelective(Affix record);

    @SelectProvider(type=AffixSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="business_id", property="businessId", jdbcType=JdbcType.BIGINT),
        @Result(column="business_type", property="businessType", jdbcType=JdbcType.VARCHAR),
        @Result(column="suffix", property="suffix", jdbcType=JdbcType.VARCHAR),
        @Result(column="file_name", property="fileName", jdbcType=JdbcType.VARCHAR),
        @Result(column="path_url", property="pathUrl", jdbcType=JdbcType.VARCHAR)
    })
    List<Affix> selectByExample(AffixExample example);

    @Select({
        "select",
        "id, business_id, business_type, suffix, file_name, path_url",
        "from tab_affix",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="business_id", property="businessId", jdbcType=JdbcType.BIGINT),
        @Result(column="business_type", property="businessType", jdbcType=JdbcType.VARCHAR),
        @Result(column="suffix", property="suffix", jdbcType=JdbcType.VARCHAR),
        @Result(column="file_name", property="fileName", jdbcType=JdbcType.VARCHAR),
        @Result(column="path_url", property="pathUrl", jdbcType=JdbcType.VARCHAR)
    })
    Affix selectByPrimaryKey(Long id);

    @UpdateProvider(type=AffixSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Affix record, @Param("example") AffixExample example);

    @UpdateProvider(type=AffixSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Affix record, @Param("example") AffixExample example);

    @UpdateProvider(type=AffixSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Affix record);

    @Update({
        "update tab_affix",
        "set business_id = #{businessId,jdbcType=BIGINT},",
          "business_type = #{businessType,jdbcType=VARCHAR},",
          "suffix = #{suffix,jdbcType=VARCHAR},",
          "file_name = #{fileName,jdbcType=VARCHAR},",
          "path_url = #{pathUrl,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Affix record);
}