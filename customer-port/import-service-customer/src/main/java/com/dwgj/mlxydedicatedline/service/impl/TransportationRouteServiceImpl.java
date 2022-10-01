package com.dwgj.mlxydedicatedline.service.impl;

import com.dwgj.mlxydedicatedline.entity.RoutePrice;
import com.dwgj.mlxydedicatedline.entity.TransportationRoute;
import com.dwgj.mlxydedicatedline.mapper.RoutePriceMapper;
import com.dwgj.mlxydedicatedline.mapper.TransportationRouteMapper;
import com.dwgj.mlxydedicatedline.service.TransportationRouteService;
import com.dwgj.mlxydedicatedline.vo.route.BigPackagePriceVo;
import com.dwgj.mlxydedicatedline.vo.route.RouteVo;
import com.dwgj.mlxydedicatedline.vo.route.SmallPackagePriceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lrq
 */
@Service
public class TransportationRouteServiceImpl implements TransportationRouteService {

    @Autowired
    private TransportationRouteMapper routeMapper;
    @Autowired
    private RoutePriceMapper priceMapper;

    @Override
    public int findRouteAll(Map map) {
        return routeMapper.findRouteAll(map);
    }

    @Override
    public List<TransportationRoute> findRoute(int routeType) {
        Map<String,Object> map = new HashMap<>();
        map.put("routeType",routeType);
        return routeMapper.findRoute(map);
    }

    @Override
    public List<TransportationRoute> findAllRoute(Map map) {
        List<TransportationRoute> routes = routeMapper.findRoute(map);
        routes.forEach(route -> {
            List<RoutePrice> routePrice = priceMapper.fingByRouteId(route.getId());
            route.setRoutePrices(routePrice);
        });
        return routes;
    }

    @Override
    public RouteVo findByRouteId(Integer routeId) {
        RouteVo routeVo = routeMapper.findRouteVoByRouteId(routeId);
        if(routeVo.getNoteType() != null && "1".equals(routeVo.getNoteType())){
            routeVo.setNoteType("人民币");
        }
        List<BigPackagePriceVo> bigPackagePriceVos = priceMapper.findBigPackageByRouteId(routeId);
        routeVo.setBigPackagePrices(bigPackagePriceVos);
        return routeVo;
    }


}
