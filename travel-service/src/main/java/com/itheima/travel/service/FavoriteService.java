package com.itheima.travel.service;

import com.github.pagehelper.PageInfo;
import com.itheima.travel.req.FavoriteVo;
import com.itheima.travel.req.RouteVo;

public interface FavoriteService {


    /**
     * @Description 查询当前用户我的收藏
     * @return RouteVo 路线信息
     */
    PageInfo<RouteVo> findMyFavorite(FavoriteVo favoriteVo, int pageNum, int pageSize);

    /**
     * @Description 是否收藏
     * @param favoriteVo 关注请求参数
     * @return 是否收藏
     */
    Boolean isFavorited(FavoriteVo favoriteVo);

    /**
     * @Description 添加收藏
     * @return 当前路线新收藏个数
     */
    Integer addFavorite(FavoriteVo favoriteVo);
}
