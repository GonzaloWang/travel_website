package com.itheima.travel.basic;

import com.itheima.travel.config.SpringConfig;
import com.itheima.travel.service.CategoryService;
import com.itheima.travel.service.FavoriteService;
import com.itheima.travel.service.RouteService;
import com.itheima.travel.service.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Description：基础测试类：此类必须为抽象类，要不会报错
 * WebAppConfiguration 加载WEB环境测试: 注解在类上,
 * 用来声明加载的ApplicationContex是一个WebApplicationContext
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
@WebAppConfiguration
public abstract class TestConfig {
    @Autowired
    public UserService userService;

    @Autowired
    public FavoriteService favoriteService;

    @Autowired
    public CategoryService categoryService;

    @Autowired
    public RouteService routeService;
}
