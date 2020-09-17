package com.itheima.travel.pojo;

import java.io.Serializable;
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
public class User implements Serializable {
    /** 
    * 主键
    */
    private Long id;

    /** 
    * 账户
    */
    private String username;

    /** 
    * 密码
    */
    private String password;

    /** 
    * 真实姓名
    */
    private String realName;

    /** 
    * 生日
    */
    private Date birthday;

    /** 
    * 性别
    */
    private String sex;

    /** 
    * 电话
    */
    private String telephone;

    /** 
    * 邮箱
    */
    private String email;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}