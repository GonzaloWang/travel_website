package com.itheima.travel.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Route implements Serializable {
    /** 
    * 主键
    */
    private Long id;

    /** 
    * 线路名称
    */
    private String routeName;

    /** 
    * 价格
    */
    private BigDecimal price;

    /** 
    * 线路描述
    */
    private String routeIntroduce;

    /** 
    * 标记
    */
    private String flag;

    /** 
    * 是否主体之旅
    */
    private String isThemeTour;

    /** 
    * 当前统计
    */
    private Integer attentionCount;

    /** 
    * 分类id
    */
    private Long categoryId;

    /** 
    * 供应商
    */
    private Long sellerId;

    /** 
    * 发布时间
    */
    private Date createdTime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}