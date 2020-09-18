package com.itheima.travel.session;

import com.itheima.travel.utils.ToString;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description：用于存储当前登录后放入session中的user对象
 */
@Data
@NoArgsConstructor
public class SubjectUser extends ToString {

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

    /**
     * 此次会话的token
     */
    private String token;
}
