package com.itheima.travel.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @Description：附件表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AffixVo implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 业务ID
     */
    private Long businessId;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 后缀名
     */
    private String suffix;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 访问路径
     */
    private String pathUrl;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
