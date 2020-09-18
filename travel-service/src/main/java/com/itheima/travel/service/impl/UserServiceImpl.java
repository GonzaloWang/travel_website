package com.itheima.travel.service.impl;

import com.itheima.travel.mapper.UserMapper;
import com.itheima.travel.pojo.User;
import com.itheima.travel.pojo.UserExample;
import com.itheima.travel.req.UserVo;
import com.itheima.travel.service.UserService;
import com.itheima.travel.session.SubjectUser;
import com.itheima.travel.session.SubjectUserContext;
import com.itheima.travel.utils.BeanConv;
import com.itheima.travel.utils.MD5Coder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    SubjectUserContext subjectUserContext;

    @Override
    public Boolean registerUser(UserVo userVo) {
        User user = BeanConv.toBean(userVo, User.class);
        user.setPassword(MD5Coder.md5Encode(user.getPassword()));
        int changeRowNum = userMapper.insert(user);
        return changeRowNum > 0;
    }

    @Override
    public UserVo loginUser(UserVo userVo) {
        // 组装条件
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(userVo.getUsername()).andPasswordEqualTo(MD5Coder.md5Encode(userVo.getPassword()));
        //查询list结果集
        List<User> users = userMapper.selectByExample(example);
        //转换VO
        List<UserVo> list = BeanConv.toBeanList(users, UserVo.class);
        if (list.size() == 1) {
            UserVo userVo1 = list.get(0);
            // 放入会话
            String token = UUID.randomUUID().toString();
            userVo1.setToken(token);
            subjectUserContext.createdSubject(token, BeanConv.toBean(userVo1, SubjectUser.class));
            return userVo1;
        }
        return null;
    }

    @Override
    public void loginOutUser() {
        subjectUserContext.deleteSubject();
    }

    @Override
    public Boolean isLogin() {
        return subjectUserContext.existSubject();
    }
}
