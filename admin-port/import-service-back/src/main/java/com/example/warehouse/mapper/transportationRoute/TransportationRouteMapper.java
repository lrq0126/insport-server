package com.example.warehouse.mapper.transportationRoute;

import com.example.warehouse.entity.TransportationRoute;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.route.RouteRequestVo;
import com.example.warehouse.vo.route.RouteVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * TransportationRouteMapper继承基类
 * @author lrq
 * 2019年11月4日13:59:33
 */
@Repository
public interface TransportationRouteMapper extends MyBatisBaseDao<TransportationRoute, Integer> {

    /**
     * 查询所有路线信息(条件分页)
     * @param paramMap
     * @return
     */
    List<RouteVo> findAll(Map<String,Object> paramMap);

    /**
     * 查询所有路线信息(不分页)
     * @param routeRequestVo
     * @return
     */
    List<RouteVo> getAll(RouteRequestVo routeRequestVo);


    /**
     * 查询路线内容
     * @param id
     * @return
     */
    RouteVo findById(int id);

    /**
     * 根据 id 删除对应 路线，逻辑删除
     * @param ids
     * @return
     */
    int deleteByIds(@Param("ids") List ids);

    /**
     * 查询总数
     * @param paramMap
     * @return
     */
    int countSelectAll(Map<String,Object> paramMap);

    int selectRouteCount(RouteRequestVo routeRequestVo);

    List<RouteVo> selectAllRoute(RouteRequestVo routeRequestVo);

    /**
     * 启用
     * @param id
     * @return
     */
    int enableRoute(int id);

    /**
     * 禁用
     * @param id
     * @return
     */
    int prohibitRoute(int id);


    List<RouteVo> findRouteByCountryId(Integer countryId);
}