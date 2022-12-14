package com.example.warehouse.service.agentApplyPack.impl;

import com.example.warehouse.common.CalculationUtils;
import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PackUtils;
import com.example.warehouse.common.SequenceCode;
import com.example.warehouse.entity.*;
import com.example.warehouse.entity.sys.SysNotice;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.enums.goods.GoodsStatus;
import com.example.warehouse.mapper.CustomerAddressMapper;
import com.example.warehouse.mapper.GoodsMapper;
import com.example.warehouse.mapper.customerPack.CustomerPackMapper;
import com.example.warehouse.mapper.customerPackReceiverAddress.CustomerPackReceiverAddressDao;
import com.example.warehouse.mapper.goods2pack.Goods2packMapper;
import com.example.warehouse.mapper.routePrice.RoutePriceMapper;
import com.example.warehouse.mapper.sys.SysNoticeMapper;
import com.example.warehouse.mapper.transportationRoute.TransportationRouteMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.agentApplyPack.ApplyPackService;
import com.example.warehouse.service.wechat.SendMessageServer;
import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.applyPack.GoodsPackingVo;
import com.example.warehouse.vo.applyPack.GoodsRequestVo;
import com.example.warehouse.vo.applyPack.RouteQuotationVo;
import com.example.warehouse.vo.route.RouteVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.warehouse.enums.ResultStatus.*;
import static com.example.warehouse.enums.goods.GoodsStatus.*;

@Service
@Slf4j
public class ApplyPackServiceImpl implements ApplyPackService {

    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private TransportationRouteMapper transportationRouteMapper;
    @Autowired
    private RoutePriceMapper routePriceMapper;
    @Autowired
    private CustomerPackMapper customerPackMapper;
    @Autowired
    private Goods2packMapper goods2packMapper;
    @Autowired
    private CustomerAddressMapper customerAddressMapper;
    @Autowired
    private CustomerPackReceiverAddressDao customerPackReceiverAddressDao;

    @Autowired
    private SysNoticeMapper sysNoticeMapper;
    @Autowired
    private SendMessageServer sendMessageServer;

    @Autowired
    private CalculationUtils calculationUtils;
    @Autowired
    private PackUtils packUtils;

    @Override
    public ResponseEntity<ResultModel> getGoods(GoodsRequestVo goodsRequestVo) {
        if(goodsRequestVo.getCustomerId() == null){
            return new ResponseEntity<>(ResultModel.error(ERROR, "?????????????????????"), HttpStatus.OK);
        }
        List<GoodsVo> goodsVos = goodsMapper.selectByVo(goodsRequestVo, INTO_WARE.getStatus());
        return new ResponseEntity<>(ResultModel.ok(goodsVos), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getRoutePrice(GoodsRequestVo goodsRequestVo) {
        long startTime = System.currentTimeMillis();

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }
        // ????????????????????????????????????????????????????????????
        if(user.getCommercialAreaId() != 1){
//            map.put("commercialAreaId", user.getCommercialAreaId());
            goodsRequestVo.setCommercialAreaId(user.getCommercialAreaId());
        }

        Map<String, Object> resultMap = new HashMap<>();
        List<RouteVo> routeVoList = transportationRouteMapper.getAll(goodsRequestVo);
        if(routeVoList == null || routeVoList.isEmpty()){
            return new ResponseEntity<>(ResultModel.error(TRANSPORTROUTE_ERROR), HttpStatus.OK);
        }
        // ?????????????????????id???????????????????????????????????????????????????????????????????????????
        if(goodsRequestVo.getCountryId() == null && (goodsRequestVo.getCountry() == null || goodsRequestVo.getCountry().isEmpty())){
            List<RouteVo> routeVoByCountryList = new ArrayList<>();
            for (RouteVo routeVo : routeVoList) {
                if(routeVo.getCountryId().equals(routeVoList.get(0).getCountryId())){
                    routeVoByCountryList.add(routeVo);
                }
            }
            routeVoList = routeVoByCountryList;
        }

        List<RoutePrice> routePriceList = routePriceMapper.findByRouteIdList(routeVoList.stream().map(RouteVo::getId).collect(Collectors.toList()));

        for (RouteVo routeVo : routeVoList) {
            List<RoutePrice> routePrices = new ArrayList<>();
            for (RoutePrice routePrice : routePriceList) {
                if(routePrice.getRouteId().equals(routeVo.getId())){
                    routePrices.add(routePrice);
                }
            }
            routeVo.setRoutePrices(routePrices);
        }

        // ??????????????????
        List<GoodsVo> goodsVoList = goodsMapper.selectByIds(goodsRequestVo.getGoodsIds());

        // ????????????????????????????????????
        List<RouteQuotationVo> routeQuotationVos = new ArrayList<>();
        for (RouteVo routeVo : routeVoList) {
            if(routeVo.getRoutePrices() == null || routeVo.getRoutePrices().isEmpty()){
                continue;
            }
            RouteQuotationVo routeQuotationVo = new RouteQuotationVo();
            String msg = applyPackPriceCalculate(routeQuotationVo, routeVo, goodsVoList);
            if(msg != null){
                System.out.println( "?????????" + routeVo.getRouteName() + "??????????????????:"+ msg);
                System.out.println("------------------------?????????-------------------------");
                continue;
            }

            routeQuotationVo.setGoodsSum(goodsVoList.size());
            log.info("?????????" + routeQuotationVo.getRouteName() + "????????????");
            routeQuotationVos.add(routeQuotationVo);
        }

        // ????????????????????????
        List<CustomerAddress> address = customerAddressMapper.findByCustomerId(goodsRequestVo.getCustomerId());

        resultMap.put("goods",goodsVoList);
        resultMap.put("address",address);
        resultMap.put("routePrice",routeQuotationVos);
        long endTime = System.currentTimeMillis();
        System.out.println("???????????????" + (endTime - startTime) + "??????");
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ResultModel> goodsPacking(GoodsPackingVo goodsPackingVo) {

        if(goodsPackingVo.getCustomerId() == null){
            return new ResponseEntity<>(ResultModel.error(CUSTOMER_ID_NULL_ERROR), HttpStatus.OK);
        }
        if(goodsPackingVo.getAddressId() == null){
            return new ResponseEntity<>(ResultModel.error(ADDRESS_NULL_ERROR), HttpStatus.OK);
        }
        if(goodsPackingVo.getTransportRouteId() == null){
            return new ResponseEntity<>(ResultModel.error(ROUTE_ID_NULL_ERROR), HttpStatus.OK);
        }
        if(goodsPackingVo.getGoodsIds() == null){
            return new ResponseEntity<>(ResultModel.error(GOODS_NULL_ERROR), HttpStatus.OK);
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        def.setName("???????????????????????????");
        try {
            // ?????????????????????
            // ??????????????????????????????
            goodsPackingVo.setGoodsIdList(packUtils.getGoodsIdList(goodsPackingVo.getGoodsIds()));
            List<GoodsVo> goodsVoList = goodsMapper.selectByIds(goodsPackingVo.getGoodsIdList());
            List<String> customerNoList = goodsVoList.stream().map(GoodsVo::getCustomerNo).distinct().collect(Collectors.toList());
            if (!customerNoList.isEmpty() && customerNoList.size() > 1) {
                return new ResponseEntity<>(ResultModel.error(ResultStatus.ONLY_CUSTOMER_PACKING_ERROR), HttpStatus.OK);
            }

            if(goodsPackingVo.getAddressId() == null){
                return new ResponseEntity<>(ResultModel.error(ResultStatus.ADDRESS_NULL_ERROR), HttpStatus.OK);
            }

            RouteVo routeVo = transportationRouteMapper.findById(goodsPackingVo.getTransportRouteId());
            if(routeVo == null){
                return new ResponseEntity<>(ResultModel.error(ResultStatus.TRANSPORTROUTE_ERROR), HttpStatus.OK);
            }

            double countWeight = 0;
            double countVol = 0;
            StringBuilder goodsNos = new StringBuilder();
            boolean isCalculateVolWeight = false;
            for (GoodsVo goodsVo : goodsVoList) {
                // ???????????????????????????
                if (!goodsVo.getGoodsType().equals(INTO_WARE.getStatus())) {
                    return new ResponseEntity<>(ResultModel.error(350,
                            "???" + goodsVo.getGoodsName() + "??? ????????????????????????????????????????????????"), HttpStatus.OK);
                }

                // ????????????????????????????????????????????????
                if (goodsVo.getVol() == null || goodsVo.getKg() == null) {
                    return new ResponseEntity<>(ResultModel.error(349,
                            "???"+goodsVo.getGoodsName() + "??? ????????????????????????????????????????????????????????????"), HttpStatus.OK);
                }

                // ???????????????
                countWeight += goodsVo.getKg();
                countVol += goodsVo.getVol();
                if(goodsNos.toString().isEmpty() || goodsNos.toString().equals("")){
                    goodsNos.append(goodsVo.getGoodsNo());
                }else {
                    goodsNos.append(",").append(goodsVo.getGoodsNo());
                }

                // ?????????????????????????????? ??????????????????????????????????????????????????????
                if(!isCalculateVolWeight){
                    isCalculateVolWeight = routeVo.getSideLengthLimit() == null ||
                            packUtils.checkIsCalculateVolWeight(routeVo.getSideLengthLimit(), goodsVo.getLength(),
                                    goodsVo.getWidth(), goodsVo.getHeight());
                }
            }

            // ??????????????????
//            TransportationRoute route = transportationRouteMapper.selectByPrimaryKey(goodsPackingVo.getTransportRouteId());

            Map<String, Object> resultMap;
            if (routeVo.getRouteType() == 1) {
                // ?????????????????????????????????
                resultMap = calculationUtils.countKGMoney(routeVo.getRoutePrices(), routeVo, countWeight, countVol, false, isCalculateVolWeight);
            } else {
                resultMap = calculationUtils.countVOLMoney(routeVo.getRoutePrices(), routeVo, countVol, countWeight, false);
            }

            if (resultMap == null || resultMap.isEmpty()) {
                return new ResponseEntity<>(ResultModel.error(348, "???????????????????????????????????????????????????????????????????????????"), HttpStatus.OK);
            }

            CustomerPack customerPack = new CustomerPack();
            if(resultMap.containsKey("KG_VOL") && routeVo.getRouteType() == 1){
                customerPack.setRemarks("????????????????????????????????????????????????????????????" + resultMap.get("KG_VOL") + "KG ?????????????????????????????????????????????");
            }else if(resultMap.containsKey("KG_VOL") && routeVo.getRouteType() == 2){
                customerPack.setRemarks("????????????????????????????????????????????????????????????" + resultMap.get("KG_VOL") + "??? ?????????????????????????????????????????????");
            }
            if(!resultMap.containsKey("finalPrice")){
                return new ResponseEntity<>(ResultModel.error(347, "??????????????????????????????????????????"), HttpStatus.OK);
            }
            BigDecimal prePrice = (BigDecimal) resultMap.get("finalPrice");

            // ?????????
            if (resultMap.containsKey("ForkliftFee")) {
                customerPack.setRemarks("?????????????????????????????????????????????" + resultMap.get("ForkliftFee"));
                prePrice = prePrice.add((BigDecimal) resultMap.get("ForkliftFee"));
            }

            // ???????????????????????? ??????????????????  ??????  ??????????????????
            customerPack.setActualWeight(countWeight);
            customerPack.setActualVol(countVol);

            customerPack.setPreQuotedPrice(prePrice.doubleValue());

            customerPack.setTransportationRouteId(goodsPackingVo.getTransportRouteId());
            customerPack.setRouteType(routeVo.getRouteType());
            customerPack.setCustomerId(goodsPackingVo.getCustomerId());

            customerPack.setBusinessNumber(SequenceCode.businessNumber());
            customerPack.setGoodsNo(goodsNos.toString());

            customerPack.setGoodsSum(goodsVoList.size());

            customerPack.setAddressId(goodsPackingVo.getAddressId());

            customerPackMapper.insertSelective(customerPack);

            // ????????????????????????
            // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????
            CustomerAddress customerAddress = customerAddressMapper.findById(goodsPackingVo.getAddressId());


            CustomerPackReceiverAddress customerPackReceiverAddress = new CustomerPackReceiverAddress();

            customerPackReceiverAddress.setStatus(1);
            customerPackReceiverAddress.setCustomerPackId(customerPack.getId());

            customerPackReceiverAddress.setAddressee(customerAddress.getAddressee());
            customerPackReceiverAddress.setReceiverAddress(customerAddress.getReceiverAddress());
            customerPackReceiverAddress.setProvinces(customerAddress.getProvinces());
            customerPackReceiverAddress.setPhoneNumber(customerAddress.getPhoneNumber());
            customerPackReceiverAddress.setCode(customerAddress.getCode());
            customerPackReceiverAddressDao.insert(customerPackReceiverAddress);

            List<Integer> goodsIdList = goodsVoList.stream().map(GoodsVo::getId).collect(Collectors.toList());
            // ???????????? ?????????????????????????????????
            goodsMapper.updateByIdsAndGoodsType(goodsIdList, GoodsStatus.WAIT_PACKING.getStatus());

            // ??????????????????????????????
            List<Goods2pack> goods2packList = new ArrayList<>();
            for (GoodsVo goodsVo : goodsVoList) {
                Goods2pack goods2pack = new Goods2pack();
                goods2pack.setStatus(1);
                goods2pack.setPackId(customerPack.getId());
                goods2pack.setGoodsId(goodsVo.getId());
                goods2packList.add(goods2pack);
            }
            goods2packMapper.insertList(goods2packList);

            // ?????????????????????
            SysNotice notice = new SysNotice();
            notice.setStatus(1);
            notice.setPublisher("sys");
            notice.setReadId(customerPack.getCustomerId().toString());
            notice.setNoticeType(1);
            notice.setNoticeTitle("????????????");
            notice.setCreateTime(DateUtil.timestamp2String(new Date()));
            notice.setNoticeContent("??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
//            sysNoticeMapper.insert(notice);

            sendMessageServer.sendMessageForApplyPack(customerPack);

            transactionManager.commit(status);
            return new ResponseEntity<>(ResultModel.ok(ResultStatus.SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            log.info("?????????????????????{}", e.getMessage());
            e.printStackTrace();
            transactionManager.rollback(status);
            return new ResponseEntity<>(ResultModel.error(ResultStatus.SYS_ERROR), HttpStatus.OK);
        }
    }

    /**
     * ????????????????????????????????????
     * @param routeQuotationVo
     * @param routeVo
     * @param goodsVoList
     */
    private String applyPackPriceCalculate(RouteQuotationVo routeQuotationVo, RouteVo routeVo, List<GoodsVo> goodsVoList) {

        boolean isCalculateVolWeight = false;

        for (GoodsVo goodsVo : goodsVoList) {
            if(goodsVo.getKg() == null){
                goodsVo.setKg(1.00);
            }
            if(goodsVo.getLength() == null){
                goodsVo.setLength(1.00);
            }
            if(goodsVo.getWidth() == null){
                goodsVo.setLength(1.00);
            }
            if(goodsVo.getHeight() == null){
                goodsVo.setLength(1.00);
            }

            if (goodsVo.getKg() == null) {
                if(routeVo.getRouteType() == 1){
                    goodsVo.setKg((goodsVo.getVol()*1000000)/ routeVo.getVolConversion());
                }else {
                    goodsVo.setKg(goodsVo.getVol() * routeVo.getVolConversion());
                }
            }

            if (goodsVo.getVol() == null) {
                if(routeVo.getRouteType() == 1){
                    goodsVo.setVol((goodsVo.getKg() * routeVo.getVolConversion()) / 1000000);
                }else {
                    goodsVo.setVol(goodsVo.getKg() / routeVo.getVolConversion());
                }
            }

            if(!isCalculateVolWeight){
                isCalculateVolWeight = routeVo.getSideLengthLimit() == null ||
                        packUtils.checkIsCalculateVolWeight(routeVo.getSideLengthLimit(), goodsVo.getLength(),
                                goodsVo.getWidth(), goodsVo.getHeight());
            }
        }
        double goodsWeight = goodsVoList.stream().mapToDouble(GoodsVo::getKg).sum();
        double goodsVol = goodsVoList.stream().mapToDouble(GoodsVo::getVol).sum();


        // ????????????
        Map<String, Object> priceMap;
        if (routeVo.getRouteType() == 1) {

            priceMap = calculationUtils.countKGMoney(routeVo.getRoutePrices(), routeVo, goodsWeight, goodsVol, false, isCalculateVolWeight);
            if(priceMap == null || priceMap.isEmpty()){
                return "??????????????????????????????";
            }
            if (priceMap.containsKey("KG_VOL")) {
                priceMap.put("kgOrVol", "??????????????????????????????????????????????????????????????????????????????????????????????????????" + String.valueOf(priceMap.get("KG_VOL")) + " Kg");
            }
        } else {
            priceMap = calculationUtils.countVOLMoney(routeVo.getRoutePrices(), routeVo, goodsVol, goodsWeight, false);
            if(priceMap == null || priceMap.isEmpty()){
                return "??????????????????????????????";
            }
            if (priceMap.containsKey("KG_VOL")) {
                priceMap.put("kgOrVol","???????????????????????????????????????????????????????????????" + String.valueOf(priceMap.get("KG_VOL")) + " m??");
            }
        }

        RoutePrice routePrice = null;
        for (RoutePrice eachPrice : routeVo.getRoutePrices()) {

            if(priceMap.get("route").equals(eachPrice.getId())){
                routePrice = eachPrice;
                break;
            }

        }
        if(routePrice == null){
            return "???????????????????????????????????????";
        }

        boolean boo = true;
        if (routeVo.getTransportType() == 1) {
            for (GoodsVo goods : goodsVoList) {
                if(routeVo.getFirstLength() != null && routeVo.getSecondLength() != null
                        && goods.getLength() != null && goods.getWidth() != null && goods.getHeight() != null){
                    boo = calculationUtils.upperLimitOfLength(routeVo.getFirstLength(), routeVo.getSecondLength(),
                            goods.getLength(), goods.getWidth(), goods.getHeight());
                }
                if (!boo) {
                    break;
                }
                if(routeVo.getUpperLimitWeight() != null && goods.getKg() != null){
                    boo = calculationUtils.upperLimitOfWeight(routeVo.getUpperLimitWeight(), goods.getKg());
                }
                if (!boo) {
                    break;
                }
            }
        }
        if (!boo) {
            return "??????/??????????????????";
        }

        // ????????????????????????
        routeQuotationVoSetting(routeQuotationVo, routeVo, routePrice, priceMap, goodsWeight, goodsVol);

        return null;
    }

    private void routeQuotationVoSetting(RouteQuotationVo routeQuotationVo, RouteVo routeVo, RoutePrice routePrice,
                         Map<String, Object> priceMap, double goodsWeight, double goodsVol){
        // ??????????????????double???????????????2?????????
        DecimalFormat dfWeight = new DecimalFormat("#.##");
        DecimalFormat dfVol = new DecimalFormat("#.####");

        // ????????????????????? ????????????/????????????
        if(priceMap.containsKey("finalKG")){
            routeQuotationVo.setSettleWeight((Double) priceMap.get("finalKG"));
        }else {
            routeQuotationVo.setSettleWeight(Double.valueOf(dfWeight.format(goodsWeight)));
        }
        if(priceMap.containsKey("finalVOL")){
            routeQuotationVo.setSettleVol((Double) priceMap.get("finalVOL"));
        }else {
            routeQuotationVo.setSettleVol(Double.valueOf(dfVol.format(goodsVol)));
        }

        if(routeVo.getRouteType() == 1 && priceMap.containsKey("KG_VOL")){
            routeQuotationVo.setSettleWeight((Double) priceMap.get("KG_VOL"));
        }

        if(routeVo.getRouteType() == 2 && priceMap.containsKey("KG_VOL")){
            routeQuotationVo.setSettleVol((Double) priceMap.get("KG_VOL"));
        }

        String remarks = "";
        if (routeVo.getRemarks() != null) {
            remarks = routeVo.getRemarks();
        }

        // ??????????????????????????????????????????????????????
        if (routePrice.getStartVolume() != null) {
            routeQuotationVo.setVol(routePrice.getStartVolume() + "-" + routePrice.getEndVolume());
        }
        if (routePrice.getStartWeight() != null) {
            routeQuotationVo.setWeight(routePrice.getStartWeight() + "-" + routePrice.getEndWeight());
        }

        // ???????????????
        String specialRemarks = "";
        if(routePrice.getRemarks() != null){
            specialRemarks = routePrice.getRemarks();
        }
        if (priceMap.containsKey("ForkliftFee")) {
            BigDecimal forkliftFee = BigDecimal.valueOf(Double.valueOf(priceMap.get("ForkliftFee").toString()));
            routeQuotationVo.setIncidental(forkliftFee);
            if(!specialRemarks.isEmpty() ){
                specialRemarks = specialRemarks + "; ????????????" + forkliftFee + "???";
            }else {
                specialRemarks = "????????????" + forkliftFee + "???";
            }
        }
        if (priceMap.containsKey("KG_VOL")) {
            if (!specialRemarks.isEmpty()) {
                specialRemarks = specialRemarks + ";" + priceMap.get("kgOrVol").toString();
            } else {
                specialRemarks = priceMap.get("kgOrVol").toString();
            }
        }
        routeQuotationVo.setSpecialRemarks(specialRemarks);

        // ???????????????/??????????????????
        routeQuotationVo.setActualWeight(Double.valueOf(dfWeight.format(goodsWeight)));
        routeQuotationVo.setActualVol(Double.valueOf(dfVol.format(goodsVol)));
        // ?????????????????????
        routeQuotationVo.setRouteName(routeVo.getRouteName());
        if (routeVo.getNoteType() != null) {
            if ("1".equals(routeVo.getNoteType())) {
                routeQuotationVo.setNoteType("?????????");
            }
        }
        routeQuotationVo.setRemarks(remarks);
        routeQuotationVo.setOriginatingPlace(routeVo.getOriginatingPlace());
        routeQuotationVo.setUnitPrice(routePrice.getPrice());
        routeQuotationVo.setPrice(BigDecimal.valueOf(Double.valueOf(priceMap.get("finalPrice").toString())));
        routeQuotationVo.setTransportationTime(routeVo.getTransportationTime());
        routeQuotationVo.setDestination(routeVo.getDestination());
        routeQuotationVo.setCountry(routeVo.getCountry());
        routeQuotationVo.setCountryId(routeVo.getCountryId());
        routeQuotationVo.setRoutePriceId(routePrice.getId());

        // ??????????????????
        routeQuotationVo.setId(routeVo.getId());
        routeQuotationVo.setCreateTime(routeVo.getCreateTime());
        routeQuotationVo.setCountryId(routeVo.getCountryId());
        routeQuotationVo.setCountry(routeVo.getCountry());
        routeQuotationVo.setUpdateTime(routeVo.getUpdateTime());
        routeQuotationVo.setPackAttention(routeVo.getPackAttention());
        routeQuotationVo.setOriginatingPlace(routeVo.getOriginatingPlace());
        routeQuotationVo.setRouteName(routeVo.getRouteName());
        routeQuotationVo.setRouteType(routeVo.getRouteType());  // ????????????
        routeQuotationVo.setTransportType(routeVo.getTransportType());  // ????????????
        routeQuotationVo.setRemoteFee(routeVo.getRemoteFee());
        routeQuotationVo.setEnglishName(routeVo.getEnglishName());
        routeQuotationVo.setIsAvailable(routeVo.getIsAvailable());

    }
}
