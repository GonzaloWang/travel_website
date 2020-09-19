package com.itheima.travel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.travel.mapper.FavoriteMapper;
import com.itheima.travel.mapper.RouteMapper;
import com.itheima.travel.mapperExt.FavoriteMapperExt;
import com.itheima.travel.mapperExt.RouteMapperExt;
import com.itheima.travel.pojo.Favorite;
import com.itheima.travel.pojo.FavoriteExample;
import com.itheima.travel.pojo.Route;
import com.itheima.travel.pojo.RouteExample;
import com.itheima.travel.service.FavoriteService;
import com.itheima.travel.session.SubjectUserContext;
import com.itheima.travel.session.SubjectUser;
import com.itheima.travel.utils.BeanConv;
import com.itheima.travel.req.FavoriteVo;
import com.itheima.travel.req.RouteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description：收藏服务类
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    FavoriteMapper favoriteMapper;

    @Autowired
    FavoriteMapperExt favoriteMapperExt;

    @Autowired
    RouteMapper routeMapper;

    @Autowired
    RouteMapperExt routeMapperExt;

    @Autowired
    SubjectUserContext subjectUserContext;

    @Override
    public PageInfo<RouteVo> findMyFavorite(FavoriteVo favoriteVo,int pageNum, int pageSize) {
        //使用分页
        PageHelper.startPage(pageNum, pageSize);
        //获取当前登录用户
        SubjectUser subjectUser = subjectUserContext.getSubject();
        //需要注意的是不要直接使用BeanConv对list进行copy,这样会到导致统计页面出错
        List<Route> list = favoriteMapperExt.findFavoriteByUserId(subjectUser.getId());
        PageInfo<Route> pageInfo = new PageInfo<>(list);
        PageInfo<RouteVo> pageInfoVo = new PageInfo<>();
        //外部对象拷贝
        BeanConv.toBean(pageInfo, pageInfoVo);
        //结果集转换
        List<RouteVo> routeVoList = BeanConv.toBeanList(pageInfo.getList(), RouteVo.class);
        pageInfoVo.setList(routeVoList);
        return pageInfoVo;
    }

    @Transactional
    @Override
    public Integer addFavorite(FavoriteVo favoriteVo) {
        //1、获得当前用户
        SubjectUser subjectUser = subjectUserContext.getSubject();
        favoriteVo.setUserId(subjectUser.getId());
        //2.向tab_favorite表插入一条数据
        favoriteMapper.insert(BeanConv.toBean(favoriteVo, Favorite.class));
        //3.更新tab_route表的count字段+1
        Long flag = routeMapperExt.updateRouteCountById(favoriteVo.getRouteId());
        if (flag==0){
            throw new RuntimeException("修改统计表出错");
        }
        //4.重新查询tab_route表的count字段
        Route route = routeMapper.selectByPrimaryKey(favoriteVo.getRouteId());
        return route.getAttentionCount();
    }


    @Override
    public Boolean isFavorited(FavoriteVo favoriteVo) {
        //获取当前登录用户
        SubjectUser subjectUser = subjectUserContext.getSubject();
        FavoriteExample favoriteExample = new FavoriteExample();
        favoriteExample.createCriteria().andRouteIdEqualTo(favoriteVo.getRouteId())
                .andUserIdEqualTo(subjectUser.getId());
        List<Favorite> favoriteList = favoriteMapper.selectByExample(favoriteExample);
        return favoriteList.size()>0;
    }
}
