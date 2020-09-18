package com.itheima.travel.session;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description：用于存储当前登录后放入session中的user对象
 */
@Data
@NoArgsConstructor
public class SubjectSeller {

    /**
     * 主键
     */
    private Long id;

    /**
     * 供应商名称
     */
    private String sellerName;

    /**
     * 联系方式
     */
    private String telephone;

    /**
     * 地址
     */
    private String address;

    /**
     * 账户
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 此次会话的token
     */
    private String token;
}
