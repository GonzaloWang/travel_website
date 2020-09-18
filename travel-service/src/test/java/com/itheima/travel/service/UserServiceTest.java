package com.itheima.travel.service;

import com.itheima.travel.basic.TestConfig;
import com.itheima.travel.req.UserVo;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.util.Date;

@Log4j2
public class UserServiceTest extends TestConfig {

    @Test
    public void testRegisterUser(){
        log.info("testRegisterUser----开始");
        UserVo userVo = UserVo.builder()
                .username("shuwenqi")
                .password("pass")
                .realName("束XX")
                .birthday(new Date())
                .sex("1")
                .telephone("15156408888")
                .email("15156408888@qq.com").build();
        boolean flag = userService.registerUser(userVo);
        log.info("testRegisterUser----通过--->{}",flag);
    }

    @Test
    public void testLoginUser(){
        log.info("testLoginUser----开始");
        UserVo userVo = UserVo.builder()
                .username("admin")
                .password("pass")
                .build();
        UserVo userVoResult = userService.loginUser(userVo);
        log.info("testLoginUser----通过--->{}",userVoResult);
    }

    @Test
    public void testLoginOutUser(){
        log.info("testLoginUser----开始");
        UserVo userVo = UserVo.builder()
                .username("admin")
                .password("pass")
                .build();
        UserVo userVoResult = userService.loginUser(userVo);
        log.info("testLoginUser----通过--->{}",userVoResult.toString());

        log.info("testLoginOutUser----开始");
        userService.loginOutUser();
        log.info("testLoginOutUser----通过--->{}");
    }

    @Test
    public void testIsLogin(){
        log.info("testLoginUser----开始");
        UserVo userVo = UserVo.builder()
                .username("admin")
                .password("pass")
                .build();
        UserVo userVoResult = userService.loginUser(userVo);
        log.info("testLoginUser----通过--->{}",userVoResult.toString());

        log.info("testIsLogin----开始");
        Boolean flag = userService.isLogin();
        log.info("testIsLogin----通过--->{}",flag);
    }
}
