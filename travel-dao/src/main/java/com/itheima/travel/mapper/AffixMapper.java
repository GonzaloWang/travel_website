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
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface AffixMapper {
    /**
     * @Description 按条件统计
     * @param example 查询条件
     * @return 统计结果
     */
    @SelectProvider(type=AffixSqlProvider.class, method="countByExample")
    long countByExample(AffixExample example);

    /**
     * @Description 按条件删除
     * @param example 查询条件
     * @return 影响行数
     */
    @DeleteProvider(type=AffixSqlProvider.class, method="deleteByExample")
    int deleteByExample(AffixExample example);

    /**
     * @Description 按主键删除
     * @param id 主键
     * @return 影响行数
     */
    @Delete({
        "delete from tab_affix",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * @Description 插入记录：insert则会插入所有字段，会插入null
     * @param record 插入对象
     * @return 影响行数
     */
    @Insert({
        "insert into tab_affix (business_id, business_type, ",
        "suffix, file_name, ",
        "path_url)",
        "values (#{businessId,jdbcType=BIGINT}, #{businessType,jdbcType=VARCHAR}, ",
        "#{suffix,jdbcType=VARCHAR}, #{fileName,jdbcType=VARCHAR}, ",
        "#{pathUrl,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Affix record);

    /**
     * @Description 插入记录：insertSelective：只会插入数据不为null的字段值
     * @param record 插入对象
     * @return 影响行数
     */
    @InsertProvider(type=AffixSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(Affix record);

    /**
     * @Description 按条件查询
     * @param example 查询条件
     * @return 符合条件的结果集
     */
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

    /**
     * @Description 按主键查询对象
     * @param id 主键
     * @return 符合条件的结果
     */
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

    /**
     * @Description 按条件修改数据
     * @param record 要修改的部分值组成的对象，其中有些属性为null则表示该项不修改
     * @param example 修改条件
     * @return 影响行数
     */
    @UpdateProvider(type=AffixSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Affix record, @Param("example") AffixExample example);

    /**
     * @Description 按条件修改数据
     * @param record 要修改的部分值组成的对象，其中有些属性为null会被修改
     * @param example 修改条件
     * @return 影响行数
     */
    @UpdateProvider(type=AffixSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Affix record, @Param("example") AffixExample example);

    /**
     * @Description 按主键修改数据
     * @param record 要修改的部分值组成的对象，其中有些属性为null则表示该项不修改
     * @return 影响行数
     */
    @UpdateProvider(type=AffixSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Affix record);

    /**
     * @Description 按主键修改数据
     * @param record 要修改的部分值组成的对象，其中有些属性为null会被修改
     * @return 影响行数
     *//**
     * @Description 按主键修改数据
     * @param record 要修改的部分值组成的对象，其中有些属性为null会被修改
     * @return 影响行数
     */
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