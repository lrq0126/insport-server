package com.example.warehouse.common;

import com.example.warehouse.entity.*;
import com.example.warehouse.entity.CustomerPack;
import com.example.warehouse.entity.CustomerPackNumber;
import com.example.warehouse.entity.ping.PingMainOrder;
import com.example.warehouse.entity.ping.PingPack;
import com.example.warehouse.entity.ping.PingPackGoods;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.customerPack.CustomerPackNumberVo;
import com.example.warehouse.vo.ping.responseVo.PingPackRespVo;
import com.example.warehouse.vo.route.RouteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.example.warehouse.enums.ResultStatus.ROUTE_WEIGHT_OR_VOLUME_LIMIT_ERROR;
import static com.example.warehouse.enums.ResultStatus.TRANSPORTROUTE_ERROR;

@Configuration
public class PingUtil {

    @Autowired
    private CalculationUtils calculationUtils;

    /**
     * 拼团价格区间匹配
     * @param route
     * @param targetWeight
     */
    public RouteVo priceMatching(RouteVo route, double targetWeight){
        // 根据目标重量匹配对应的价格区间
        List<RoutePrice> routePriceList = new ArrayList<>();
        if(route.getRoutePrices() != null && !route.getRoutePrices().isEmpty()){
            for (RoutePrice routePrice : route.getRoutePrices()) {
                if(routePrice.getStartWeight() == null || routePrice.getStartWeight() == 0){
                    routePrice.setStartWeight(route.getVolConversion()* routePrice.getStartVolume());
                    routePrice.setEndWeight(route.getVolConversion()* routePrice.getEndVolume());
                }

                if (targetWeight >= routePrice.getStartWeight() && targetWeight <= routePrice.getEndWeight()) {
                    routePriceList.add(routePrice);
                }
            }
        }
        route.setRoutePrices(routePriceList);
        return route;
        // 价格区间匹配 end
    }


    /**
     * 拼团价格计算
     * @param routeVo
     * @param routePriceList
     * @param actualWeight
     * @param actualVol
     * @param pingMainOrder
     * @return
     */
    public ResponseEntity<ResultModel> priceConversion(RouteVo routeVo, List<RoutePrice> routePriceList, double actualWeight,
                                                              double actualVol, PingMainOrder pingMainOrder){
        Map<String, Object> routePriceMap ;
        if(routeVo.getRouteType() == 1){
            // 拼团价格计算， 默认计算体积重
            routePriceMap = calculationUtils.countKGMoney(routePriceList, routeVo, actualWeight, actualVol, true, true);

            if (routePriceMap == null) {
                System.out.println("暂未获取到路线价格内容");
                return new ResponseEntity<>(ResultModel.error(ROUTE_WEIGHT_OR_VOLUME_LIMIT_ERROR), HttpStatus.OK);
            }
            if (routePriceMap.containsKey("finalPrice")) {
                pingMainOrder.setAuctualPrice(BigDecimal.valueOf(Double.valueOf(routePriceMap.get("finalPrice").toString())));
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
            routePriceMap = calculationUtils.countVOLMoney(routePriceList, routeVo, actualVol, actualWeight, true);

            if (routePriceMap == null) {
                System.out.println("暂未获取到路线价格内容");
                return new ResponseEntity<>(ResultModel.error(TRANSPORTROUTE_ERROR), HttpStatus.OK);
            }
            if (routePriceMap.containsKey("finalPrice")) {
                pingMainOrder.setAuctualPrice(BigDecimal.valueOf(Double.valueOf(routePriceMap.get("finalPrice").toString())));
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



    /**
     * 拼装CustomerPack和CustomerPackNumber的数据
     *
     * @param pingMainOrder 拼团客户包裹数据
     * @param customerPack  客户包裹数据
     */
    public void customerPackSetting(PingMainOrder pingMainOrder, CustomerPack customerPack, List<GoodsVo> goodsVoList, RouteVo route) {
        goodsVoList.forEach(goodsVo -> {
            goodsVo.setKg(goodsVo.getKg() == null ? 0 : goodsVo.getKg());
            goodsVo.setVol(goodsVo.getVol() == null ? 0 : goodsVo.getVol());
        });
//        List<PingPackRespVo> pingPackList = pingMainOrderRespVo.getPingPack();

        customerPack.setInsideMessage(pingMainOrder.getInsideMessage());//内部备注
        customerPack.setPickedTime(pingMainOrder.getPickTime());//已拣货完成时间
        customerPack.setUserId(pingMainOrder.getCreateId());//操作员id
        customerPack.setOrderNumber(pingMainOrder.getOrderNumber());//订单号
        customerPack.setPackType(2);//packType：1、待打包；  2、已打包；  3、确认发货；  4、已出库； 7、待拆包 ； 9、已收货
        customerPack.setGoodsSum(pingMainOrder.getGoodsNumber());//包裹数量
        customerPack.setActualPrice(pingMainOrder.getAuctualPrice());//实际价格
        customerPack.setPackTime(pingMainOrder.getPackTime());//打包时间
        customerPack.setActualWeight(pingMainOrder.getActualWeight());
        customerPack.setSettlementWeight(pingMainOrder.getSettlementWeight());//结算重量
        customerPack.setActualVol(pingMainOrder.getActualVol());
        customerPack.setSettlementVol(pingMainOrder.getSettlementVol());//结算体积
        customerPack.setPreQuotedPrice(pingMainOrder.getPreQoutedPrice().doubleValue()); //预计价格
        customerPack.setRemarks(pingMainOrder.getRemarks());//备注
        customerPack.setIncidental(pingMainOrder.getIncidental());//杂费；
        customerPack.setInternationalTransshipmentNo(pingMainOrder.getAgentNumber());//国际转运单号
        customerPack.setDiscount(pingMainOrder.getDiscount());//优惠价格
        customerPack.setIsSensitive(pingMainOrder.getIsSensitive() == null ? 0 : pingMainOrder.getIsSensitive());//是否敏感货物
        customerPack.setCustomerId(pingMainOrder.getCustomerId());//客户id
        customerPack.setBusinessNumber(SequenceCode.businessNumber()); // 流水号

        customerPack.setTransportationRouteId(pingMainOrder.getRouteId());//路线id
        customerPack.setRouteType(route.getRouteType());//路线类型  1、空运   2、海运   3、其他


    }


    /**
     * CustomerPackNumber数据拼装
     *
     * @param pingPackList           拼图客户子包裹数据
     * @param customerPackNumberList 客户子包裹数据
     */
    public void customerPackNumberSetting(List<PingPackRespVo> pingPackList, List<CustomerPackNumber> customerPackNumberList) {

        for (PingPack pingPack : pingPackList) {
            CustomerPackNumber customerPackNumber = new CustomerPackNumber();
            customerPackNumber.setCreateId(pingPack.getCreateId());//创建人
            customerPackNumber.setUpdateId(pingPack.getLastUpdateId());//修改人
            customerPackNumber.setStatus(1);//1、可使用，0、已删除
            customerPackNumber.setVersion(1);//版本号 默认为1，每次修改+1
            customerPackNumber.setLength(pingPack.getActualL());//长， 单位：cm
            customerPackNumber.setWidth(pingPack.getActualW());//宽， 单位：cm
            customerPackNumber.setHeight(pingPack.getActualH());//高， 单位：cm
            customerPackNumber.setActualWeight(pingPack.getActualWeight());//实际重量
            customerPackNumber.setActualVol(pingPack.getActualVol());//实际体积
            customerPackNumber.setPackTime(new Date());//打包时间
            customerPackNumber.setSettlementWeight(pingPack.getActualWeight());//结算重量
            customerPackNumber.setSettlementVol(pingPack.getActualVol());//结算体积
            customerPackNumber.setIsSensitive(pingPack.getIsSensitive());//是否敏感货物
            customerPackNumber.setPackOrder(pingPack.getPackOrder()); // 包裹订单号
            customerPackNumberList.add(customerPackNumber);
        }

        //customerPackNumber.setCustomerPackId();//包裹id
        //customerPackNumber.setOutStorageTime();//出库时间/发货时间
        //customerPackNumber.setActualPrice();//实际价格
        //customerPackNumber.setGoodsNos();//快递包裹 goods_no  集合
        //customerPackNumber.setInternationalTransshipmentNo();//国际转运单号
    }

    public List<Goods2pack> goods2packSetting(List<CustomerPackNumberVo> customerPackNumberList, List<PingPackRespVo> pingPackVoList){
        List<Goods2pack> goods2packList = new ArrayList<>();
        for (CustomerPackNumberVo customerPackNumber : customerPackNumberList) {
            for (PingPackRespVo pingPackVo : pingPackVoList) {
                System.out.println("拼团子包裹订单号：" + pingPackVo.getPackOrder());
                System.out.println("客户子包裹订单号："+customerPackNumber.getPackOrder());
                System.out.println("对比结果："+ pingPackVo.getPackOrder().equals(customerPackNumber.getPackOrder()));
                if (pingPackVo.getPackOrder().equals(customerPackNumber.getPackOrder())) {
                    List<PingPackGoods> pingPackGoods = pingPackVo.getPingPackGoods();
                    for (PingPackGoods pingPackGood : pingPackGoods) {
                        Goods2pack goods2pack = new Goods2pack();
                        goods2pack.setPackId(customerPackNumber.getCustomerPackId());
                        goods2pack.setSonPackId(customerPackNumber.getId());
                        goods2pack.setGoodsId(pingPackGood.getGoodsId());
                        goods2pack.setStatus(1);
                        goods2packList.add(goods2pack);
                    }
                    break;
                }
            }
        }
        goods2packList.forEach(goods2pack -> System.out.println("箱子ID："+goods2pack.getSonPackId()));
        return goods2packList;
    }

}
