package com.itheima.travel.service.impl;

import com.itheima.travel.mapper.UserMapper;
import com.itheima.travel.pojo.User;
import com.itheima.travel.req.UserVo;
import com.itheima.travel.service.UserService;
import com.itheima.travel.utils.BeanConv;
import com.itheima.travel.utils.MD5Coder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Boolean registerUser(UserVo userVo) {
        User user = BeanConv.toBean(userVo, User.class);
        user.setPassword(MD5Coder.md5Encode(user.getPassword()));
        int changeRowNum = userMapper.insert(user);
        return changeRowNum > 0;
    }

    @Override
    public UserVo loginUser(UserVo userVo) {

        return null;
    }

    @Override
    public void loginOutUser() {

    }

    @Override
    public Boolean isLogin() {
        return null;
    }
}
