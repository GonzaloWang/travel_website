package com.itheima.travel.service;

import com.itheima.travel.req.UserVo;

/**
 * @Description 用户服务
 */
public interface UserService {

    /**
     * @Description 用户注册
     * @param userVo
     * @return 注册是否成功
     */
    Boolean registerUser(UserVo userVo);

    /**
     * @Description 用户登录
     * @param userVo
     * @return 登录成功后返回
     */
    UserVo loginUser(UserVo userVo);

    /**
     * @Description 用户退出
     */
    void loginOutUser();

    /**
     * @Description 用户是否登录
     */
    Boolean isLogin();
}
