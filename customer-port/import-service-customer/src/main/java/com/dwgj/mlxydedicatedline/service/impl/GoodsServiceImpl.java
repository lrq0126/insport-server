package com.dwgj.mlxydedicatedline.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dwgj.mlxydedicatedline.commons.DateUtil;
import com.dwgj.mlxydedicatedline.commons.SequenceCode;
import com.dwgj.mlxydedicatedline.config.UserThreadContext;
import com.dwgj.mlxydedicatedline.entity.*;
import com.dwgj.mlxydedicatedline.entity.customerPack.CustomerPackPriceDetail;
import com.dwgj.mlxydedicatedline.entity.customerPack.PackInsurancePrice;
import com.dwgj.mlxydedicatedline.entity.customerPack.PackValuation;
import com.dwgj.mlxydedicatedline.entity.image.ImageType;
import com.dwgj.mlxydedicatedline.entity.image.Images;
import com.dwgj.mlxydedicatedline.entity.insurance.Insurance;
import com.dwgj.mlxydedicatedline.entity.insurance.InsurancePrice;
import com.dwgj.mlxydedicatedline.entity.sys.CommercialArea;
import com.dwgj.mlxydedicatedline.mapper.*;
import com.dwgj.mlxydedicatedline.mapper.customerPack.CustomerPackPriceDetailMapper;
import com.dwgj.mlxydedicatedline.mapper.customerPack.PackInsurancePriceMapper;
import com.dwgj.mlxydedicatedline.mapper.customerPack.PackValuationMapper;
import com.dwgj.mlxydedicatedline.mapper.customerPackReceiverAddress.CustomerPackReceiverAddressDao;
import com.dwgj.mlxydedicatedline.mapper.goods2pack.Goods2packMapper;
import com.dwgj.mlxydedicatedline.mapper.image.ImagesMapper;
import com.dwgj.mlxydedicatedline.mapper.insurance.InsuranceMapper;
import com.dwgj.mlxydedicatedline.mapper.insurance.InsurancePriceMapper;
import com.dwgj.mlxydedicatedline.mapper.sys.CommercialAreaMapper;
import com.dwgj.mlxydedicatedline.mapper.sysDictDetail.SysDictDetailDao;
import com.dwgj.mlxydedicatedline.mapper.user.LoginTokenMapper;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.GoodsService;
import com.dwgj.mlxydedicatedline.service.wechat.SendMessageServer;
import com.dwgj.mlxydedicatedline.utils.CalculationUtils;
import com.dwgj.mlxydedicatedline.utils.CustomerPackUtil;
import com.dwgj.mlxydedicatedline.utils.GoodsUtil;
import com.dwgj.mlxydedicatedline.vo.GoodsVo;
import com.dwgj.mlxydedicatedline.vo.goods.ConfirmPackReqVo;
import com.dwgj.mlxydedicatedline.vo.insurance.InsuranceRespVo;
import com.dwgj.mlxydedicatedline.vo.route.RouteQuotationVo;
import com.dwgj.mlxydedicatedline.vo.route.RouteVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.dwgj.mlxydedicatedline.enums.ResultStatus.*;

@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private TransportationRouteMapper transportationRouteMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerPackMapper customerPackMapper;
    @Autowired
    private CustomerPackPriceDetailMapper customerPackPriceDetailMapper;
    @Autowired
    private RoutePriceMapper routePriceMapper;
    @Autowired
    private ImagesMapper imagesMapper;
    @Autowired
    private PackValuationMapper packValuationMapper;
    @Autowired
    private InsuranceMapper insuranceMapper;
    @Autowired
    private InsurancePriceMapper insurancePriceMapper;
    @Autowired
    private PackInsurancePriceMapper packInsurancePriceMapper;
    @Autowired
    private CommercialAreaMapper commercialAreaMapper;

    @Resource
    private CalculationUtils calculationUtils;
    @Autowired
    private Goods2packMapper goods2packMapper;
    @Autowired
    private CustomerAddressMapper customerAddressMapper;
    @Autowired
    private CustomerPackReceiverAddressDao customerPackReceiverAddressDao;
    @Autowired
    private SendMessageServer sendMessageServer;
    @Autowired
    private GoodsUtil goodsUtil;
    @Autowired
    private SysDictDetailDao sysDictDetailDao;
    @Autowired
    private LoginTokenMapper loginTokenMapper;


    @Override
    public ResponseEntity<ResultModel> getGoodsCommercialArea(HttpServletRequest request) {
        String userToken = request.getHeader("authorization");
        Customer customer = loginTokenMapper.getUser(userToken);
        if(customer == null){
            return new ResponseEntity<>(ResultModel.error(NOT_LOGIN_ERROR), HttpStatus.OK);
        }
        List<Map<String, Object>> goodsCommercialAreaList = goodsMapper.getGoodsCommercialArea(customer.getCustomerNo());
        return new ResponseEntity<>(ResultModel.ok(goodsCommercialAreaList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> selectGoodsInfoByDeliveryOrderNo(String deliveryOrderNo) {
        if(deliveryOrderNo == null || deliveryOrderNo.isEmpty()){
            return new ResponseEntity<>(ResultModel.error(ERROR,"请选择订单号"), HttpStatus.OK);
        }
        Goods goods = goodsMapper.findDeliveryOrderNo(deliveryOrderNo);
        if(goods == null){
            return new ResponseEntity<>(ResultModel.ok("暂无该订单信息"), HttpStatus.OK);
        }
        List<Map<String, Object>> goodsVoList = goodsMapper.selectGoodsLocationByCustomerNo(goods.getCustomerNo());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("goods", goods);
        resultMap.put("locations", goodsVoList);

        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
    }

    @Override
    public List<GoodsVo> findGoods(Map<String, Object> map) {

        if (map.containsKey("iutStorageTime") && !"".equals(map.get("iutStorageTime").toString())) {
            timeChange(map);
        }
        List<GoodsVo> goodsVoList = goodsMapper.findGoods(map);
        return goodsVoList;
    }

    @Override
    public int del(Map<String, Object> map) {
        return goodsMapper.deleteByIds(map);
    }

    @Override
    public int update(Goods goods) {
        goodsUtil.goodsNameCheck(goods);
        return goodsMapper.updateByPrimaryKeySelective(goods);
    }

    @Override
    public int countFindAll(Map<String, Object> map) {
        if (map.containsKey("outStorageTime") && !"".equals(map.get("outStorageTime").toString())) {
            timeChange(map);
        }
        return goodsMapper.countSelectAll(map);
    }

    /**
     * 申请打包
     *
     * @param map
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> applyPack(Map<String, Object> map) {
        String goodsNosStr = (String) map.get("goodsNos");
        List<String> goodsNos = Arrays.asList(goodsNosStr.split(","));
        List<GoodsVo> goodsVoList = goodsMapper.findByGoodsNos(goodsNos);
        boolean resultBool = false;
        for (GoodsVo goodsVo : goodsVoList) {
            if (goodsVo.getKg() == null && goodsVo.getVol() == null) {
                resultBool = true;
                break;
            }
        }

        if (resultBool) {
            return new ResponseEntity<>(ResultModel.error(GOODS_WEIGHT_OR_VOL_NULL_ERROR), HttpStatus.OK);
        }
        String countryId = "";
        if(map.containsKey("countryId") && !StringUtils.isEmpty(map.get("countryId").toString())){
            countryId = map.get("countryId").toString();
        }else{
            Map<String, Object> sddMap = new HashMap<>();
            sddMap.put("sdmCode", "country");

            List<SysDictDetail> sysDictDetailList = sysDictDetailDao.selectDictByType(sddMap);
            if(!CollectionUtils.isEmpty(sysDictDetailList)){
                countryId = sysDictDetailList.get(0).getId().toString();
            }
        }

        Map<String, Object> map2 = new HashMap<>();

        map2.put("countryId", countryId);
        map2.put("commercialAreaId", map.get("commercialAreaId").toString());
        map2.put("transportType", map.get("routeType").toString());
        // 由于设计原因，前台传递的routeType等于后端的transportType；表示运输类型

        List<RouteVo> routeVoList = transportationRouteMapper.findRouteVo(map2);

        List<RouteQuotationVo> routeQuotationVos = new ArrayList<>();
//        List<RoutePrice> allRoutePriceList = routePriceMapper.getAllPrice();
        for (RouteVo transportationRoute : routeVoList) {
            // 以下是测试计算重量加钱的
            double k = 0.0;
            double v = 0.0;
            RouteQuotationVo routeQuotationVo = new RouteQuotationVo();
            BigDecimal money = new BigDecimal(0);
            Double settleWeight = null;
            Double settleVol = null;
            String kgOrVol = "";
            List<RoutePrice> routePriceList = transportationRoute.getRoutePrices();
            if (routePriceList == null) {
                System.out.println("未找到匹配路线，返回");
                break;
            }

            if (transportationRoute.getRouteType() == 2) {
                for (GoodsVo goods : goodsVoList) {
                    if (goods.getKg() == null) {
                        k += goods.getVol() * transportationRoute.getVolConversion();
                    } else {
                        k += goods.getKg();
                    }
                    if (goods.getVol() == null) {
                        v += goods.getKg() / transportationRoute.getVolConversion();
                    } else {
                        v += goods.getVol();
                    }
                }
                map2 = calculationUtils.countVOLMoney(routePriceList, transportationRoute, v, k, false);
                // 如果 map2 为空，即没有获取到相关的渠道价格，跳过此操作
                if (map2 == null) {
                    continue;
                }
                if (map2.containsKey("finalPrice")) {
                    money = BigDecimal.valueOf(Double.valueOf(map2.get("finalPrice").toString()));
                }
                if (map2.containsKey("finalVOL")) {
                    settleVol = (Double) map2.get("finalVOL");
                    routeQuotationVo.setSettleVol(settleVol);
                }

                if (map2.containsKey("KG_VOL")) {
                    kgOrVol = "由于单位立方重量超出限制，计算体积转换为：" + String.valueOf(map2.get("KG_VOL")) + " m³";
                }
            } else {
                boolean isCalculateVolWeight = false;
                for (GoodsVo goodsVo : goodsVoList) {

                    if (goodsVo.getKg() == null) {
                        k += (goodsVo.getVol() * 1000000) / transportationRoute.getVolConversion();
                    } else {
                        k += goodsVo.getKg();
                    }

                    if (goodsVo.getVol() == null) {
                        v += (goodsVo.getKg() * transportationRoute.getVolConversion()) / 1000000;
                    } else {
                        v += goodsVo.getVol();
                    }

                    if (!isCalculateVolWeight) {
                        isCalculateVolWeight = transportationRoute.getSideLengthLimit() == null ||
                                goodsUtil.checkIsCalculateVolWeight(transportationRoute.getSideLengthLimit(),
                                        goodsVo.getLength(), goodsVo.getWidth(), goodsVo.getHeight());
                    }

                }
                // 申请打包获取价格，isCalculateVolWeight 是否计算体积重
                map2 = calculationUtils.countKGMoney(routePriceList, transportationRoute, k, v, false, isCalculateVolWeight);
                // 如果 map2 为空，即没有获取到相关的渠道价格，跳过此操作
                if (map2 == null) {
                    continue;
                }

                if (map2.containsKey("finalPrice")) {
                    money = BigDecimal.valueOf(Double.valueOf(map2.get("finalPrice").toString()));
                }
                if (map2.containsKey("finalKG")) {
                    settleWeight = (Double) map2.get("finalKG");
                    routeQuotationVo.setSettleWeight(settleWeight);
                }

                if (map2.containsKey("KG_VOL")) {
                    kgOrVol = "由于货物体积重与实际重量对比，体积重较大，计算的重量转换为体积重量：" + String.valueOf(map2.get("KG_VOL")) + " Kg";
                }
            }
            //RoutePrice routePrice = routePriceMapper.selectByPrimaryKey(Integer.valueOf(map2.get("route").toString()));

            RoutePrice routePrice = null;
            for (RoutePrice eachPrice : routePriceList) {
                if (map2.get("route").equals(eachPrice.getId())) {
                    routePrice = eachPrice;
                    break;
                }
            }
            if (routePrice == null) {
                System.out.println("未找到匹配价格，返回");
                break;
            }

            boolean boo = true;
            if (transportationRoute.getTransportType() == 1) {
                for (GoodsVo goods : goodsVoList) {
                    if (transportationRoute.getFirstLength() != null && transportationRoute.getSecondLength() != null &&
                            goods.getLength() != null && goods.getWidth() != null && goods.getHeight() != null) {
                        boo = calculationUtils.upperLimitOfLength(transportationRoute.getFirstLength(),
                                transportationRoute.getSecondLength(), goods.getLength(), goods.getWidth(), goods.getHeight());
                    }
                    if (!boo) {
                        break;
                    }
                    if (transportationRoute.getUpperLimitWeight() != null && goods.getKg() != null) {
                        boo = calculationUtils.upperLimitOfWeight(transportationRoute.getUpperLimitWeight(), goods.getKg());
                    }
                    if (!boo) {
                        break;
                    }
                }
            }
            if (!boo) {
                continue;
            }
            // 将长度过长的double字段截取成2位小数
            DecimalFormat df = new DecimalFormat("#.##");

            //填入结算重量、体积
            routeQuotationVo.setSettleWeight(Double.valueOf(df.format(k)));
            routeQuotationVo.setSettleVol(Double.valueOf(df.format(v)));

            // 符合条件就更新 结算重量/结算体积
            if (map2.containsKey("finalKG")) {
                routeQuotationVo.setSettleWeight((Double) map2.get("finalKG"));
            }
            if (map2.containsKey("finalVOL")) {
                routeQuotationVo.setSettleVol((Double) map2.get("finalVOL"));
            }
            if (transportationRoute.getRouteType() == 1 && map2.containsKey("KG_VOL")) {
                routeQuotationVo.setSettleWeight((Double) map2.get("KG_VOL"));
            }
            if (transportationRoute.getRouteType() == 2 && map2.containsKey("KG_VOL")) {
                routeQuotationVo.setSettleVol((Double) map2.get("KG_VOL"));
            }
            String remarks = "";
            if (transportationRoute.getRemarks() != null) {
                remarks = transportationRoute.getRemarks();
            }

            // 为返回的实体的体积区间和重量区间赋值
            if (routePrice.getStartVolume() != null) {
                routeQuotationVo.setVol(routePrice.getStartVolume() + "-" + routePrice.getEndVolume());
            }
            if (routePrice.getStartWeight() != null) {
                routeQuotationVo.setWeight(routePrice.getStartWeight() + "-" + routePrice.getEndWeight());
            }

            // 为备注赋值
            String specialRemarks = "";
            if (routePrice.getRemarks() != null) {
                specialRemarks = routePrice.getRemarks();
            }
            if (map2.containsKey("ForkliftFee")) {
                BigDecimal forkliftFee = BigDecimal.valueOf(Double.valueOf(map2.get("ForkliftFee").toString()));
                routeQuotationVo.setIncidental(forkliftFee);
                if (specialRemarks != "") {
                    specialRemarks = specialRemarks + "; 叉车费：" + forkliftFee + "元";
                } else {
                    specialRemarks = "叉车费：" + forkliftFee + "元";
                }
            }
            if (map2.containsKey("KG_VOL")) {
                if (!"".equals(specialRemarks)) {
                    specialRemarks = specialRemarks + ";" + kgOrVol;
                } else {
                    specialRemarks = kgOrVol;
                }
            }
            routeQuotationVo.setSpecialRemarks(specialRemarks);

            // 为实际重量/实际体积赋值
            routeQuotationVo.setActualVol(Double.valueOf(df.format(v)));
            routeQuotationVo.setActualWeight(Double.valueOf(df.format(k)));
            // 为实体属性赋值
            routeQuotationVo.setRouteName(transportationRoute.getRouteName());
            if (transportationRoute.getNoteType() != null) {
                if ("1".equals(transportationRoute.getNoteType())) {
                    routeQuotationVo.setNoteType("人民币");
                }
            }
            if (transportationRoute.getTransportType() == 1) {
                routeQuotationVo.setRouteType("空运");
            } else if (transportationRoute.getTransportType() == 2) {
                routeQuotationVo.setRouteType("海/铁运");
            }
            routeQuotationVo.setRemarks(remarks);
            routeQuotationVo.setOriginatingPlace(transportationRoute.getOriginatingPlace());
            routeQuotationVo.setUnitPrice(routePrice.getPrice());
            routeQuotationVo.setPrice(money);
            routeQuotationVo.setTransportationTime(transportationRoute.getTransportationTime());
            routeQuotationVo.setRouteId(transportationRoute.getId());
            routeQuotationVo.setDestination(transportationRoute.getDestination());
            routeQuotationVo.setCountry(transportationRoute.getCountry());
            routeQuotationVo.setCountryId(transportationRoute.getCountryId());
            routeQuotationVo.setRoutePriceId(routePrice.getId());

            routeQuotationVo.setIsTariffs(transportationRoute.getIsTariffs());
            routeQuotationVo.setTariffs(transportationRoute.getTariffs());

            routeQuotationVo.setIsInsurance(transportationRoute.getIsInsurance());
            routeQuotationVo.setInsuranceId(transportationRoute.getInsuranceId());
            routeQuotationVo.setInsurance(transportationRoute.getInsurance());
            routeQuotationVo.setCurrencyData(transportationRoute.getCurrencyData());
            routeQuotationVos.add(routeQuotationVo);
        }
        if (map2 != null) {
            log.info("================================finalPrice：{}", map2.get("finalPrice") == null ? "0" : map2.get("finalPrice").toString());
        }
//        return routeQuotationVos;
//        List<RouteQuotationVo> routeQuotationVoList = goodsService.applyPack(paramMap,goods);

        if (!routeQuotationVos.isEmpty()) {  // 排序
            Collections.sort(routeQuotationVos);
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("goods", goodsVoList);
        resultMap.put("routePrice", routeQuotationVos);
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);

    }


    /**
     * 打包确认操作
     *
     * @param confirmPackReqVo
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<ResultModel> confirmPacking(ConfirmPackReqVo confirmPackReqVo) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("确认打包操作");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {

            Customer customer = UserThreadContext.getUser();
            if (customer == null) {
                return new ResponseEntity<>(ResultModel.error(NOT_LOGIN_ERROR), HttpStatus.OK);
            }

            List<String> goodsNos = Arrays.asList(confirmPackReqVo.getGoodsNos().split(","));

            BigDecimal prePrice = confirmPackReqVo.getPrice();

            TransportationRoute route = transportationRouteMapper.selectByPrimaryKey(confirmPackReqVo.getTransportationRouteId());

            List<GoodsVo> goodsVoList = goodsMapper.findByGoodsNos(goodsNos);

            for (GoodsVo goodsVo : goodsVoList) {
                if (!"1".equals(goodsVo.getGoodsType())) {
                    return new ResponseEntity<>(ResultModel.error(GOODS_TYPE_PACKED_ERROR), HttpStatus.OK);
                }
            }
            Double actualWeight = 0.0;
            Double actualVol = 0.0;
            for (GoodsVo goods : goodsVoList) {
                if (goods.getKg() == null) {
                    return new ResponseEntity<>(ResultModel.error(GOODS_WEIGHT_OR_VOL_NULL_ERROR), HttpStatus.OK);
                }
                actualWeight += goods.getKg();
                if (goods.getVol() == null) {
                    return new ResponseEntity<>(ResultModel.error(GOODS_WEIGHT_OR_VOL_NULL_ERROR), HttpStatus.OK);
                }
                actualVol += goods.getVol();
            }
            if (confirmPackReqVo.getAddressId() == null) {
                return new ResponseEntity<>(ResultModel.error(NULL_ADDRESS_ERROR), HttpStatus.OK);//地址为空
            }

            CustomerAddress customerAddress = customerAddressMapper.selectByPrimaryKey(confirmPackReqVo.getAddressId());
            CommercialArea commercialArea = commercialAreaMapper.selectByPrimaryKey(confirmPackReqVo.getCommercialAreaId());
            CustomerPack customerPack = new CustomerPack();
            customerPack.setCommercialAreaId(confirmPackReqVo.getCommercialAreaId());
            customerPack.setCommercialAreaName(commercialArea.getCommercialAreaName());
            CustomerPackPriceDetail customerPackPriceDetail = new CustomerPackPriceDetail();
            // 如果存在关税预付
            List<PackValuation> packValuations = null;

            // 根据是否需要关税做判断
            if (route.getIsTariffs() == 1) {
                if (confirmPackReqVo.getPackValuations() == null || confirmPackReqVo.getPackValuations().isEmpty()) {
                    return new ResponseEntity<>(ResultModel.error(ROUTE_IS_TARIFFS_PLEASE_INPUT_PACK_VALUATION), HttpStatus.OK);
                }
                packValuations = JSON.parseArray(confirmPackReqVo.getPackValuations(), PackValuation.class);
                for (PackValuation packValuation : packValuations) {
                    if(packValuation.getGoodsValue() == null || packValuation.getGoodsValue().compareTo(new BigDecimal(0)) <= 0){
                        return new ResponseEntity<>(ResultModel.error(ROUTE_IS_TARIFFS_PLEASE_INPUT_PACK_VALUATION), HttpStatus.OK);
                    }
                    if(packValuation.getUnitPrice() == null || packValuation.getUnitPrice().compareTo(new BigDecimal(0)) <= 0){
                        return new ResponseEntity<>(ResultModel.error(ROUTE_IS_TARIFFS_PLEASE_INPUT_PACK_VALUATION), HttpStatus.OK);
                    }
                }
                customerPack.setTariffsPrice(confirmPackReqVo.getTariffsPrice());
                prePrice = prePrice.add(confirmPackReqVo.getTariffsPrice());  // 预报价加上关税价格
                customerPackPriceDetail.setTariffsPrice(confirmPackReqVo.getTariffsPrice());
            }else {

                packValuations = JSON.parseArray(confirmPackReqVo.getPackValuations(), PackValuation.class);
                // 产品货值计算，
                packValuations =  CustomerPackUtil.dealWithPackValuations(packValuations);

            }



            // 是否有使用保险
            PackInsurancePrice packInsurancePrice = null;
            if (confirmPackReqVo.getInsuranceId() != null && confirmPackReqVo.getInsurancePriceId() != null) {

                Insurance insurance = insuranceMapper.selectByPrimaryKey(confirmPackReqVo.getInsuranceId());
                InsurancePrice insurancePrice = insurancePriceMapper.selectByPrimaryKey(confirmPackReqVo.getInsurancePriceId());
                if (insurance != null && insurancePrice != null) {
                    packInsurancePrice = new PackInsurancePrice();
                    packInsurancePrice.setStatus(1);
                    packInsurancePrice.setVersion(1);

                    packInsurancePrice.setCreateId(customer.getId());
                    packInsurancePrice.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));

                    packInsurancePrice.setInsuranceId(insurance.getId());
                    packInsurancePrice.setInsuranceName(insurance.getInsuranceName());
                    packInsurancePrice.setInsuranceNo(insurance.getInsuranceNo());
                    packInsurancePrice.setClaimsDetail(insurance.getClaimsDetail());

                    packInsurancePrice.setInsurancePriceId(insurancePrice.getId());
                    packInsurancePrice.setInsurancePrice(insurancePrice.getPrice());
                    packInsurancePrice.setGinsengPrice(insurancePrice.getGinsengPrice());
                    packInsurancePrice.setPriceClaimsDetail(insurancePrice.getClaimsDetail());

                    packInsurancePrice.setPackId(customerPack.getId());
                    packInsurancePrice.setOrderNumber(customerPack.getOrderNumber());

                    customerPack.setInsurancePrice(insurancePrice.getPrice());
                    prePrice = prePrice.add(insurancePrice.getPrice());  // 预报价加上保险金额价格
                    customerPackPriceDetail.setInsurancePrice(insurancePrice.getPrice());
                }
            }

            customerPack.setActualWeight(actualWeight);
            customerPack.setTransportationRouteId(confirmPackReqVo.getTransportationRouteId());
            customerPack.setActualVol(actualVol);
            customerPack.setRouteType(route.getRouteType());
            customerPack.setAddressId(confirmPackReqVo.getAddressId());
            customerPack.setCustomerId(customer.getId());
            customerPack.setPreQuotedPrice(prePrice);
            customerPack.setBusinessNumber(SequenceCode.businessNumber());

            customerPack.setGoodsNo(confirmPackReqVo.getGoodsNos());
            customerPack.setGoodsSum(goodsNos.size());

            customerPackMapper.insertSelective(customerPack);

            // 插入价格明细内容
            customerPackPriceDetail.setPackId(customerPack.getId());
            customerPackPriceDetail.setStatus(1);
            customerPackPriceDetail.setVersion(1);
            customerPackPriceDetail.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
            customerPackPriceDetail.setCreateId(customer.getId());
            customerPackPriceDetailMapper.insertSelective(customerPackPriceDetail);

            if (packValuations != null && !packValuations.isEmpty()) {
                for (PackValuation packValuation : packValuations) {
                    packValuation.setPackId(customerPack.getId());

                    packValuation.setCreateId(customer.getId());
                    packValuation.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));

                    packValuation.setStatus(1);
                    packValuation.setVersion(1);
                }
                packValuationMapper.insertList(packValuations);
            }

            // 如果订单保险信息不为空
            if (packInsurancePrice != null) {
                packInsurancePrice.setPackId(customerPack.getId());

                packInsurancePriceMapper.insert(packInsurancePrice);
            }

            // 申请打包发送消息给客户
            sendMessageServer.sendMessageForApplyPack(customerPack);
            // 插入收货地址信息到发货表
            CustomerPackReceiverAddress customerPackReceiverAddress = new CustomerPackReceiverAddress();
            customerPackReceiverAddress.setReceiverAddress(customerAddress.getReceiverAddress());
            customerPackReceiverAddress.setCustomerPackId(customerPack.getId());
            customerPackReceiverAddress.setPhoneNumber(customerAddress.getPhoneNumber());
            customerPackReceiverAddress.setAddressee(customerAddress.getAddressee());
            customerPackReceiverAddress.setCode(customerAddress.getCode());
            customerPackReceiverAddress.setProvinces(customerAddress.getProvinces());
            customerPackReceiverAddressDao.insert(customerPackReceiverAddress);

            customerPack.setAddressId(customerPackReceiverAddress.getId());
            customerPackMapper.updateByPrimaryKeySelective(customerPack);

            goodsMapper.updateOfApplyPackGoodsTypeChangeByGoodsNo(goodsNos);

            // 加入中间表
            List<GoodsVo> goodsVo2PackList = goodsMapper.findByGoodsNos(goodsNos);

            List<Goods2pack> goods2packList = new ArrayList<>();

            for (GoodsVo goodsVo : goodsVo2PackList) {
                Goods2pack goods2pack = new Goods2pack();
                goods2pack.setGoodsId(goodsVo.getId());
                goods2pack.setPackId(customerPack.getId());
                goods2packList.add(goods2pack);
            }

            goods2packMapper.insertList(goods2packList);
            transactionManager.commit(status);

            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("确认打包出错：{}", e.getMessage());
            transactionManager.rollback(status);
            return new ResponseEntity<>(ResultModel.error(GOODS_WEIGHT_EXCEEDING_ERROR), HttpStatus.OK);
        }


    }

    @Override
    public List<GoodsVo> findByGoodsNos(List<String> goodsNo) {
        return goodsMapper.findByGoodsNos(goodsNo);
    }

    @Override
    public ResponseEntity<ResultModel> checkPicture(String imageType, String deliveryOrderNo) {
        List<Images> imagesList =
                imagesMapper.getImagesByTypeAndContentId(imageType, deliveryOrderNo);
        if(CollectionUtils.isEmpty(imagesList)){
            return new ResponseEntity<>(ResultModel.error(IMAGES_NULL_ERROR), HttpStatus.OK);
        }
        if(imagesList.size() > 1){
            return new ResponseEntity<>(ResultModel.error(IMAGES_NUMBER_NIMIETIES_ERROR), HttpStatus.OK);
        }
        String picUrl = imagesList.get(0).getPicUrl();
        if(StringUtils.isEmpty(picUrl)){
            return new ResponseEntity<>(ResultModel.error(IMAGES_NULL_ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(picUrl), HttpStatus.OK);
    }



    /**
     * 时间格式转换
     *
     * @param map
     * @return
     */
    public Map timeChange(Map<String, Object> map) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String outStorageTime = (String) map.get("iutStorageTime");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate formatted = LocalDate.parse(outStorageTime, formatter);
        //设置零点
        LocalDateTime beginTime = LocalDateTime.of(formatted, LocalTime.MIN);
        String time1 = beginTime.format(dtf);
        //设置当天的结束时间
        LocalDateTime endTime = LocalDateTime.of(formatted, LocalTime.MAX);
        String time2 = dtf.format(endTime);
        map.put("startTime", time1);
        map.put("endTime", time2);
        return map;
    }


}
