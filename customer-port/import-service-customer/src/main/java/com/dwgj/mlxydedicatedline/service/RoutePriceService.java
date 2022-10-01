package com.dwgj.mlxydedicatedline.service;

import com.dwgj.mlxydedicatedline.vo.route.RouteQuotationVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author lrq
 * 2019年11月6日12:55:41
 */
public interface RoutePriceService {

    /**
     * @param map
     *      -- routeId 路线 id；kg  包裹重量； vol  包裹体积
     * @return
     */
    BigDecimal calculation(Map map);

    /**
     * 报价系统——获取所有路线的价格
     * @param map
     *      actualWeight  实际重量；actualVol   体积； length  长 ； width  宽 ；  height 高 ； routeId； routeType
     * @return
     */
    List<RouteQuotationVo> quotationPrice(Map<String, Object> map);



}
