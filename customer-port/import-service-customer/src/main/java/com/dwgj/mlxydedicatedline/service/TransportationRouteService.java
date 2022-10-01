package com.dwgj.mlxydedicatedline.service;

import com.dwgj.mlxydedicatedline.entity.TransportationRoute;
import com.dwgj.mlxydedicatedline.vo.route.RouteVo;

import java.util.List;
import java.util.Map;

public interface TransportationRouteService {

    /**
     * 分页
     * @param map
     * @return
     */
    int findRouteAll(Map map);

    /**
     * 根据路线类型查询路线
     * @param routeType
     * @return
     */
    List<TransportationRoute> findRoute(int routeType);

    /**
     * 查询所有路线
     * @param map
     * @return
     */
    List<TransportationRoute> findAllRoute(Map map);

    /**
     * 根据路线id查询 价格详细情况
     * @param routeId
     * @return
     */
    RouteVo findByRouteId(Integer routeId);
}
