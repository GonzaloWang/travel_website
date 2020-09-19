package com.itheima.travel.service;

import com.github.pagehelper.PageInfo;
import com.itheima.travel.basic.TestConfig;
import com.itheima.travel.req.RouteVo;
import com.itheima.travel.req.SellerVo;
import com.itheima.travel.req.UserVo;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @Description：线路实现类
 */
@Log4j2
public class RouteServiceTest extends TestConfig {

    @Before
    public void before(){
        UserVo userVo = UserVo.builder()
                .username("admin")
                .password("pass")
                .build();
        UserVo userVoResult = userService.loginUser(userVo);

    }

    @Test
    public void addRoute(){
        log.info("addRoute----开始");
        RouteVo routeVo = RouteVo.builder()
                .routeName("【春节】三亚 双飞3天2晚 自由行套票【含广州直飞往返机票+2晚海居铂尔曼酒店高级花园房+每日中西式自助早餐+VIP专车接送机服务】")
                .price(new BigDecimal(1010))
                .routeIntroduce("含广州直飞三亚往返机票+2晚海居铂尔曼酒店高级花园房+每日中西式自助早餐+VIP专车接送机服务！")
                .flag("1")
                .isThemeTour("1")
                .attentionCount(100)
                .categoryId(1L)
                .sellerId(1L)
                .build();
        Integer flag = routeService.addRoute(routeVo);
        log.info("addRoute----结束:{}",flag);
    }

    @Test
    public void updateRoute(){
        log.info("updateRoute----开始");
        RouteVo routeVo = RouteVo.builder()
                .id(22L)
                .routeName("测试")
                .price(new BigDecimal(1010))
                .routeIntroduce("测试")
                .flag("1")
                .isThemeTour("1")
                .attentionCount(100)
                .categoryId(1L)
                .sellerId(1L)
                .build();
        Integer flag = routeService.updateRoute(routeVo);
        log.info("updateRoute----结束:{}",flag);
    }

    @Test
    public void findRouteById(){
        log.info("findRouteById----开始");
        RouteVo routeVo = RouteVo.builder()
                .id(1L)
                .build();
        RouteVo routeById = routeService.findRouteById(routeVo);
        log.info("findRouteById----结束:{}",routeById.toString());
    }

    @Test
    public void findRouteByPage(){
        log.info("findRouteByPage----开始");
        PageInfo<RouteVo> routeByPage = routeService.findRouteByPage(new RouteVo(), 1, 2);
        log.info("findRouteByPage----结束:{}",routeByPage.toString());
    }

}
