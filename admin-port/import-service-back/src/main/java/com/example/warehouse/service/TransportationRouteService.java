package com.example.warehouse.service;

import com.example.warehouse.entity.TransportationRoute;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.route.RouteRequestVo;
import com.example.warehouse.vo.route.RouteVo;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * @author lrq
 * 2019年11月4日13:09:13
 */
public interface TransportationRouteService {

    int countSelectAll(Map<String, Object> paramMap);

    /**
     * 获取所有路线情况
     * @param paramMap
     * @return
     */
    List<RouteVo> findAll(Map<String, Object> paramMap);

    ResponseEntity<ResultModel> findAllRoute(RouteRequestVo routeRequestVo);

    /**
     * 获取路线内容
     * @param id
     * @return
     */
    ResponseEntity<ResultModel> findById(int id);

    /**
     * 新增路线
     * @param map
     * 具体参数：
     *   路线表参数：
     *      routeName 路线名称； englishName  英文名称   routeType  运输方式；   originatingPlace  始发地址；    destination   目的地；
     *      noteType  货币类型； transportationTime   预计运送时间； remoteFee  偏远费用；   remarks  备注；
     *   先生成路线表在生成价格表
     *   价格表参数：
     *      routeId   路线id ； routeType  路线类型 ； countType  计算类型  1、重量  2、体积;
     *      startWeight 起始重量（按照大于计算）；endWeight  结尾重量（按照小于等于）；
     *      startvolume   起始体积（按照大于计算）；endVolume 结束体积（按照小于等于）；
     *      price  价格；
     * @return
     */
    ResponseEntity<ResultModel> addRoute(Map<String, Object> map);

    /**
     * 修改路线
     * @param map
     * 具体参数：
     *   路线表参数：
     *      id  id;
     *      routeName 路线名称； englishName  英文名称   routeType  运输方式；   originatingPlace  始发地址；    destination   目的地；
     *      noteType  货币类型； transportationTime   预计运送时间； remoteFee  偏远费用；   remarks  备注； routePrice   价格内容；
     *   先生成路线表在生成价格表
     *   价格表 参数 用 routePrice 属性包装起来 ：
     *      routeId   路线id ； routeType  路线类型 ； countType  计算类型  1、重量  2、体积;
     *      startWeight 起始重量（按照大于计算）；endWeight  结尾重量（按照小于等于）；
     *      startvolume   起始体积（按照大于计算）；endVolume 结束体积（按照小于等于）；
     *      price  价格； priceNo  价格代码
     */
    ResponseEntity<ResultModel> updateRoute(Map<String, Object> map);

    /**
     * 根据id集合删除路线
     * @param ids
     * @return
     */
    int deleteByIds(String ids);

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

    /**
     * 根据 价格代码 删除 相关单位的价格
     * @param priceNo
     */
    void deleteRoutePriceByPriceNo(String priceNo);

}
