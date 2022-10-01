package com.dwgj.mlxydedicatedline.mapper;

import com.dwgj.mlxydedicatedline.entity.TransportationRoute;
import com.dwgj.mlxydedicatedline.vo.GoodsPackVo;
import com.dwgj.mlxydedicatedline.vo.route.RouteVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * TransportationRouteMapper继承基类
 */
@Repository
public interface TransportationRouteMapper extends MyBatisBaseDao<TransportationRoute, Integer> {

    List<TransportationRoute> findRoute(Map map);

    List<TransportationRoute> findRouteById(@Param("ids")List<Integer> ids);

    List<RouteVo> findRouteVo(Map map);

    Integer findRouteAll(Map map);

    RouteVo findRouteVoByRouteId(Integer routeId);

    RouteVo findByRouteIdNotStatus(Integer transportationRouteId);
}