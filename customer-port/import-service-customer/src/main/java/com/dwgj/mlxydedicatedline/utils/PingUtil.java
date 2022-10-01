package com.dwgj.mlxydedicatedline.utils;

import com.dwgj.mlxydedicatedline.entity.RoutePrice;
import com.dwgj.mlxydedicatedline.entity.ping.PingMainOrder;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.utils.CalculationUtils;
import com.dwgj.mlxydedicatedline.vo.route.RouteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.dwgj.mlxydedicatedline.enums.ResultStatus.ROUTE_PRICE_NULL_ERROR;


@Configuration
public class PingUtil {

    @Autowired
    private CalculationUtils calculationUtils;

    /**
     * 拼团价格区间匹配
     * @param transportationRoute
     * @param targetWeight
     */
    public RoutePrice priceMatching(RouteVo transportationRoute, double targetWeight){
        // 根据目标重量匹配对应的价格区间

        for (RoutePrice routePrice : transportationRoute.getRoutePrices()) {
            if(routePrice.getStartWeight() == null){
                routePrice.setStartWeight(transportationRoute.getVolConversion()* routePrice.getStartVolume());
                routePrice.setEndWeight(transportationRoute.getVolConversion()* routePrice.getEndVolume());
            }

            // 判断目标重量小于价格结尾重量，true则跳出循环
            if (targetWeight <= routePrice.getEndWeight()) {
                return routePrice;
            }
        }

        return null;
        // 价格区间匹配 end
    }

    /**
     * 拼团价格计算
     * @param routeVo
     * @param routePriceList
     * @param auctualWeight
     * @param auctualVol
     * @param auctualPrice
     * @param pingMainOrder
     * @return
     */
    public ResponseEntity<ResultModel> priceConversion(RouteVo routeVo, List<RoutePrice> routePriceList, double auctualWeight,
                                                       double auctualVol, BigDecimal auctualPrice, PingMainOrder pingMainOrder){
        Map<String, Object> routePriceMap ;
        if(routeVo.getRouteType() == 1){
            // 申请拼团计算价格时，默认计算体积重  isCalculateVolWeight = true
            routePriceMap = calculationUtils.countKGMoney(routePriceList, routeVo, auctualWeight, auctualVol, true, true);

            if (routePriceMap == null) {
                System.out.println("超出限制，返回");
                return new ResponseEntity<>(ResultModel.error(ROUTE_PRICE_NULL_ERROR), HttpStatus.OK);
            }
            if (routePriceMap.containsKey("finalPrice")) {
                auctualPrice = BigDecimal.valueOf(Double.valueOf(routePriceMap.get("finalPrice").toString()));
            }
            if (routePriceMap.containsKey("finalKG")) {
                pingMainOrder.setSettlementWeight((Double) routePriceMap.get("finalKG"));
            }
            // 体积过大，转体积重
            if (routePriceMap.containsKey("KG_VOL")) {
                pingMainOrder.setSettlementWeight((Double) routePriceMap.get("KG_VOL"));
                pingMainOrder.setRemarks( pingMainOrder.getRemarks() + ";由于体积过大,转换为体积重:" + routePriceMap.get("KG_VOL") + "KG");
            }

        }else {
            routePriceMap = calculationUtils.countVOLMoney(routePriceList, routeVo, auctualVol, auctualWeight, true);

            if (routePriceMap == null) {
                System.out.println("超出限制，返回");
                return new ResponseEntity<>(ResultModel.error(ROUTE_PRICE_NULL_ERROR), HttpStatus.OK);
            }
            if (routePriceMap.containsKey("finalPrice")) {
                auctualPrice = BigDecimal.valueOf(Double.valueOf(routePriceMap.get("finalPrice").toString()));
            }
            if (routePriceMap.containsKey("finalVOL")) {
                pingMainOrder.setSettlementVol((Double) routePriceMap.get("finalVOL"));
            }
            // 体积过大，转体积重
            if (routePriceMap.containsKey("KG_VOL")) {
                pingMainOrder.setSettlementWeight((Double) routePriceMap.get("KG_VOL"));
                pingMainOrder.setRemarks( pingMainOrder.getRemarks() + ";由于体积过大,转换为体积重:" + routePriceMap.get("KG_VOL") + "KG");
            }
        }
        return null;
    }

}
