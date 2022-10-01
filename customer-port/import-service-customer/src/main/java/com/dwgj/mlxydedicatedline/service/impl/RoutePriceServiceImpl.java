package com.dwgj.mlxydedicatedline.service.impl;

import com.dwgj.mlxydedicatedline.entity.RoutePrice;
import com.dwgj.mlxydedicatedline.entity.SysDictDetail;
import com.dwgj.mlxydedicatedline.entity.TransportationRoute;
import com.dwgj.mlxydedicatedline.mapper.RoutePriceMapper;
import com.dwgj.mlxydedicatedline.mapper.TransportationRouteMapper;
import com.dwgj.mlxydedicatedline.mapper.sysDictDetail.SysDictDetailDao;
import com.dwgj.mlxydedicatedline.service.RoutePriceService;
import com.dwgj.mlxydedicatedline.utils.CalculationUtils;
import com.dwgj.mlxydedicatedline.vo.GoodsVo;
import com.dwgj.mlxydedicatedline.vo.route.RouteQuotationVo;
import com.dwgj.mlxydedicatedline.vo.route.RouteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lrq
 * 2019年11月6日13:03:11
 */
@Service
public class RoutePriceServiceImpl implements RoutePriceService {

    @Autowired
    private RoutePriceMapper priceMapper;
    @Autowired
    private TransportationRouteMapper routeMapper;
    @Autowired
    private SysDictDetailDao sysDictDetailDao;

    @Resource
    private CalculationUtils calculationUtils;

    /**
     * @param map -- routeId 路线 id；kg  包裹重量； vol  包裹体积
     * @return
     */
    @Override
    public BigDecimal calculation(Map map) {
        int routeId = Integer.parseInt(map.get("routeId").toString());
        TransportationRoute transportationRoute = routeMapper.selectByPrimaryKey(routeId);
        int routeType = transportationRoute.getRouteType();
        double kg = Double.valueOf(map.get("kg").toString());
        double vol = Double.valueOf(map.get("vol").toString());
        RoutePrice price;
        BigDecimal allPrice;
        if (routeType == 1) {
            if (vol * 167 > kg) {
                price = priceMapper.findByWeight(routeId, vol * 167);
                allPrice = price.getPrice().multiply(BigDecimal.valueOf(vol * 167));
            } else {
                price = priceMapper.findByWeight(routeId, kg);
                allPrice = price.getPrice().multiply(BigDecimal.valueOf(kg));
            }
            return allPrice;
        } else {
            if (vol < 1 && kg >= 500) {
                vol = 1;
                price = priceMapper.findByVol(routeId, vol);
            } else {
                price = priceMapper.findByVol(routeId, vol);
            }
            allPrice = price.getPrice().multiply(BigDecimal.valueOf(vol));
            return allPrice;
        }
    }

    @Override
    public List<RouteQuotationVo> quotationPrice(Map<String, Object> map) {
        long startTime = System.currentTimeMillis();
        // TODO 1、获取重量、体积
        List<RouteVo> transportationRoutes = routeMapper.findRouteVo(map);
        double weight;
        double volume;
        boolean boolVol;
        boolean boolWeight;
        if (map.containsKey("weight")) {
            weight = Double.valueOf((String) map.get("weight"));
            boolWeight = true;
        } else {
            weight = 1;
            boolWeight = false;
        }
        if (map.containsKey("volume")) {
            volume = Double.valueOf((String) map.get("volume"));
            boolVol = true;
        } else {
            volume = 0.00;
            boolVol = false;
        }
        if (!boolVol && !boolWeight) {
            return null;
        }

        // 体积 在 boolVol = true 时 使用
        BigDecimal b = new BigDecimal(0);
        if (boolVol) {

            b = new BigDecimal(volume);
        }
        double dVol = b.setScale(4, BigDecimal.ROUND_UP).doubleValue();

        List<RouteQuotationVo> routeQuotationVos = new ArrayList<>();
        List<RoutePrice> routePriceList = priceMapper.getAllPrice();

        for(RouteVo route : transportationRoutes) {
            RouteQuotationVo routeQuotationVo = new RouteQuotationVo();
            Map<String, Object> utilsMap;
            boolean lengthOrWeightBool = true;
            if (route.getRouteType() == 1) {
                // 报价系统 查询 路线价格，默认计算体积重  isCalculateVolWeight = true
                utilsMap = calculationUtils.countKGMoney(routePriceList, route, weight, dVol, false, true);
            } else {
                utilsMap = calculationUtils.countVOLMoney(routePriceList, route, dVol, weight, false);
            }
            if ( null == utilsMap ){
                continue;
            }
            //RoutePrice routePrice = priceMapper.selectByPrimaryKey((Integer) utilsMap.get("route"));
            RoutePrice routePrice = new RoutePrice();
            for (RoutePrice routePriceAll : routePriceList) {
                if(routePriceAll.getId().equals(utilsMap.get("route"))){
                    routePrice = routePriceAll;
                    break;
                }
            }
            if(routePrice.getId() == null){
                continue;
            }
            if(route.getTransportType() == 1){
                // 判断空运包裹的重量是否超过设置上限
                if(route.getUpperLimitWeight() != null){
                    double cubeUpper = route.getUpperLimitWeight();
                    if(weight > cubeUpper){
                        lengthOrWeightBool = false;
                    }
                }
                // 判断空运包裹的边长是否超过设置上限
                if(route.getFirstLength() != null && route.getSecondLength() != null){
                    double volCupper = route.getFirstLength() * route.getSecondLength() * route.getSecondLength() / 1000000;
                    if(dVol > volCupper){
                        lengthOrWeightBool = false;
                    }
                }
            }

            if (lengthOrWeightBool) {
                BigDecimal prePrice;
                String remarks = route.getRemarks();
                String kgOrVol;
                routeQuotationVo.setRouteId(route.getId());
                routeQuotationVo.setRouteName(route.getRouteName());
                routeQuotationVo.setDestination(route.getDestination());
                prePrice = (BigDecimal) utilsMap.get("finalPrice");
                if (routePrice.getStartVolume() != null) {
                    routeQuotationVo.setVol(routePrice.getStartVolume() + "-" + routePrice.getEndVolume());
                }
                if (routePrice.getStartWeight() != null) {
                    routeQuotationVo.setWeight(routePrice.getStartWeight() + "-" + routePrice.getEndWeight());
                }
                routeQuotationVo.setSettleWeight(weight);
                routeQuotationVo.setSettleVol(volume);

                if(utilsMap.containsKey("finalKG")){
                    routeQuotationVo.setSettleWeight((Double) utilsMap.get("finalKG"));
                }
                if(utilsMap.containsKey("finalVOL")){
                    routeQuotationVo.setSettleVol((Double) utilsMap.get("finalVOL"));
                }
                if(route.getRouteType() == 1 && utilsMap.containsKey("KG_VOL")){
                    routeQuotationVo.setSettleWeight((Double) utilsMap.get("KG_VOL"));
                }
                if(route.getRouteType() == 2 && utilsMap.containsKey("KG_VOL")){
                    routeQuotationVo.setSettleVol((Double) utilsMap.get("KG_VOL"));
                }
                String specialRemarks = "";
                if(routePrice.getRemarks() != null){
                    specialRemarks = routePrice.getRemarks();
                }
                if (utilsMap.containsKey("ForkliftFee")) {
                    BigDecimal forkliftFee = (BigDecimal) utilsMap.get("ForkliftFee");
                    routeQuotationVo.setIncidental(forkliftFee);
                    if(specialRemarks != ""){
                        specialRemarks = specialRemarks + "；叉车费：" + forkliftFee + "元";
                    }else {
                        specialRemarks = "叉车费：" + forkliftFee + "元";
                    }
                }
                if (route.getRouteType() == 1) {
                    if (utilsMap.containsKey("KG_VOL")) {
                        kgOrVol = "由于货物体积过大，计算的重量转换为体积重量：" + String.valueOf(utilsMap.get("KG_VOL")) + " Kg";
                        if (specialRemarks != "") {
                            specialRemarks = specialRemarks + "；" + kgOrVol;
                        } else {
                            specialRemarks = kgOrVol;
                        }
                        if (boolWeight) {
                            routeQuotationVo.setActualWeight(Double.valueOf(utilsMap.get("KG_VOL").toString()));
                        }
                        if (boolVol) {
                            routeQuotationVo.setActualVol(Double.valueOf(utilsMap.get("KG_VOL").toString()));
                        }
                    }
                } else {
                    if (utilsMap.containsKey("KG_VOL")) {
                        kgOrVol = "由于单位立方重量超出限制，计算体积转换为：" + String.valueOf(utilsMap.get("KG_VOL")) + " m³";
                        if (specialRemarks != "") {
                            specialRemarks = specialRemarks + "；" + kgOrVol;
                        } else {
                            specialRemarks = kgOrVol;
                        }
                        if (boolWeight) {
                            routeQuotationVo.setActualWeight(Double.valueOf(utilsMap.get("KG_VOL").toString()));
                        }
                        if (boolVol) {
                            routeQuotationVo.setActualVol(Double.valueOf(utilsMap.get("KG_VOL").toString()));
                        }
                    }
                }
                if(route.getTransportType() == 1){
                    routeQuotationVo.setRouteType("空运");
                }else {
                    routeQuotationVo.setRouteType("海/铁运");
                }
                routeQuotationVo.setSpecialRemarks(specialRemarks);
                routeQuotationVo.setNoteType(route.getNoteType());
                routeQuotationVo.setPrice(prePrice);
                routeQuotationVo.setUnitPrice(routePrice.getPrice());
                routeQuotationVo.setTransportationTime(route.getTransportationTime());
                routeQuotationVo.setRemarks(remarks);
                routeQuotationVo.setCountryId(route.getCountryId());
                routeQuotationVo.setCountry(route.getCountry());
                routeQuotationVo.setRoutePriceId(routePrice.getId());
                routeQuotationVos.add(routeQuotationVo);
            }
        }
        long endTime = System.currentTimeMillis();

        System.out.println("查询执行时间："+ (endTime - startTime) +"毫秒");
        return routeQuotationVos;

    }


}
