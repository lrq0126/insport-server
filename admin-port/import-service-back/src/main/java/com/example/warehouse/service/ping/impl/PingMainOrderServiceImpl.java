package com.example.warehouse.service.ping.impl;

import com.alibaba.fastjson.JSONArray;
import com.example.warehouse.common.CalculationUtils;
import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PackUtils;
import com.example.warehouse.entity.*;
import com.example.warehouse.entity.ping.*;
import com.example.warehouse.enums.customerPack.PackTypeEnums;
import com.example.warehouse.enums.ping.PingPackType;
import com.example.warehouse.mapper.CustomerMapper;
import com.example.warehouse.mapper.GoodsMapper;
import com.example.warehouse.mapper.customerPack.CustomerPackMapper;
import com.example.warehouse.mapper.customerPackNumber.CustomerPackNumberMapper;
import com.example.warehouse.mapper.goods2pack.Goods2packMapper;
import com.example.warehouse.mapper.ping.*;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.ping.PingMainOrderService;
import com.example.warehouse.common.PingUtil;
import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.customer.CustomerPackNumberVo;
import com.example.warehouse.vo.packVo.PackSonVo;
import com.example.warehouse.vo.packVo.PackedReqVo;
import com.example.warehouse.vo.ping.responseVo.PingMainOrderRespVo;
import com.example.warehouse.vo.ping.responseVo.PingMainRespVo;
import com.example.warehouse.vo.ping.responseVo.PingPackRespVo;
import com.example.warehouse.vo.route.RouteVo;
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

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.warehouse.enums.ResultStatus.*;
import static com.example.warehouse.enums.goods.GoodsStatus.*;

@Service
@Slf4j
public class PingMainOrderServiceImpl implements PingMainOrderService {
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private PingMainMapper pingMainMapper;
    @Autowired
    private PingMainOrderMapper pingMainOrderMapper;
    @Autowired
    private PingMemberMapper pingMemberMapper;
    @Autowired
    private PingPackMapper pingPackMapper;
    @Autowired
    private PingPackGoodsMapper pingPackGoodsMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerPackMapper customerPackMapper;
    @Autowired
    private CustomerPackNumberMapper customerPackNumberMapper;
    @Autowired
    private Goods2packMapper goods2packMapper;

    @Autowired
    private CalculationUtils calculationUtils;
    @Autowired
    private PingUtil pingUtil;
    @Autowired
    private PackUtils packUtils;


    @Override
    public ResponseEntity<ResultModel> printGoods(int id) {
        return null;
    }

    @Override
    public ResponseEntity<ResultModel> getPingMainOrderDetailed(int id) {
        PingMainOrderRespVo pingMainOrderRespVo = pingMainOrderMapper.selectById(id);
        if(pingMainOrderRespVo == null){
            return new ResponseEntity<>(ResultModel.error(PIN_ORDER_NULL_ERROR), HttpStatus.OK);
        }

        PingMain pingMain = pingMainMapper.selectById(pingMainOrderRespVo.getPId());
        List<PingMember> pingMembers = pingMemberMapper.selectPingMemberByPidAndCustomerId(pingMainOrderRespVo.getPId(), pingMainOrderRespVo.getCustomerId());

        // ??????????????????
        pingMainOrderRespVo.setRouteVo(pingUtil.priceMatching(pingMainOrderRespVo.getRouteVo(), pingMain.getTargetWeight()));

        // ??????????????????
        List<Integer> goodsIds = pingMembers.stream().map(PingMember::getGoodsId).collect(Collectors.toList());

        // ?????????????????????????????????????????????????????????????????????
        if(pingMainOrderRespVo.getPackType() == PingPackType.PING_NOT_PACK.getValue()){
            goodsMapper.initPicking(goodsIds);
        }

        List<GoodsVo> goodsVoList = goodsMapper.selectByIds(goodsIds);
        pingMainOrderRespVo.setGoods(goodsVoList);

        // ??????????????????
        List<PingPackRespVo> pingPackList = pingMainOrderRespVo.getPingPack();
        if(pingPackList != null && !pingPackList.isEmpty()){
            pingPackList.forEach(pingPackRespVo -> {
                List<GoodsVo> packGoods = new ArrayList<>();
                List<PingPackGoods> pingPackGoodsList = pingPackRespVo.getPingPackGoods();
                pingPackGoodsList.forEach(pingPackGoods -> {
                    goodsVoList.forEach(goodsVo -> {
                        if(goodsVo.getId().equals(pingPackGoods.getGoodsId())){
                            packGoods.add(goodsVo);
                        }
                    });
                });
                pingPackRespVo.setGoodsVoList(packGoods);
            });
        }

        return new ResponseEntity<>(ResultModel.ok(pingMainOrderRespVo), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ResultModel> pingMainOrderPacked(PackedReqVo packedReqVo) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("????????????????????????");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        // ?????????????????????
//        User user = (User) SecurityUtils.getSubject().getPrincipal();
//        if(user == null){
//            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
//        }

        PingMainRespVo pingMainRespVo = pingMainMapper.selectPingMainDetailedById(packedReqVo.getPId());
        PingMainOrderRespVo pingMainOrder = new PingMainOrderRespVo();
        for (PingMainOrderRespVo pingMainOrderRespVo : pingMainRespVo.getPingMainOrder()) {
            if (pingMainOrderRespVo.getId().equals(packedReqVo.getId())) {
                pingMainOrder = pingMainOrderRespVo;
                // ???????????????????????????????????????
                if(pingMainOrder.getOrderNumber().isEmpty()){
                    pingMainOrder.setOrderNumber(pingMainRespVo.getOrderNumber() + "C" + pingMainOrderRespVo.getCustomerId());
                }
            }
        }
        RouteVo routeVo = pingMainRespVo.getTransportationRoute();

        List<RoutePrice> routePriceList = routeVo.getRoutePrices();
        if (routePriceList.isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(TRANSPORT_ROUTE_PRICE_ERROR), HttpStatus.OK);
        }

        List<PackSonVo> packSonVoList = JSONArray.parseArray(packedReqVo.getPackSonVos(), PackSonVo.class);
//        List<PackSonVo> packSonVoList = packedReqVo.getPackSonVos();

        // ????????????
//        Integer firstLength = routePriceList.get(0).getFirstLength();
//        Integer secondLength = routePriceList.get(0).getSecondLength();
        Double firstLength = routeVo.getFirstLength();
        Double secondLength = routeVo.getSecondLength();
        if (firstLength != null && secondLength != null) {
            for (PackSonVo packSonVo : packSonVoList) {
                double oneLength = 0;
                double twoLength = 0;
                lengthSorting(packSonVo.getLength(), packSonVo.getWidth(), packSonVo.getHeight(), oneLength, twoLength);
                if (oneLength > firstLength) {
                    return new ResponseEntity<>(ResultModel.error(ROUTE_WEIGHT_OR_VOLUME_LIMIT_ERROR), HttpStatus.OK);
                } else if (twoLength > secondLength) {
                    return new ResponseEntity<>(ResultModel.error(ROUTE_WEIGHT_OR_VOLUME_LIMIT_ERROR), HttpStatus.OK);
                }
            }
        }
        // ????????????end

        // ?????????????????????
        List<Integer> goodsIds = new ArrayList<>();
        for (PackSonVo packSonVo : packSonVoList) {
            if(packSonVo.getGoodsIds() == null || packSonVo.getGoodsIds().equals("")|| packSonVo.getGoodsIds().isEmpty()){
                return new ResponseEntity<>(ResultModel.error(SON_PACK_GOODS_NULL_ERROR), HttpStatus.OK);
            }
            packSonVo.setGoodsIdList(packUtils.getGoodsIdList(packSonVo.getGoodsIds()));

            goodsIds.addAll(packSonVo.getGoodsIdList());
        }
        List<GoodsVo> goodsVoList = goodsMapper.selectByIds(goodsIds);
        // ??????????????????????????????
        int isSensitive = 0;
        for (GoodsVo goodsVo : goodsVoList) {
            if (goodsVo.getIsSensitive() != null && goodsVo.getIsSensitive() == 1) {
                isSensitive = 1;
            }
        }
        goodsMapper.updateByIdsAndGoodsType(goodsIds, GOODS_PACKED.getStatus());

        // ??????????????????
        double auctualWeight = 0;
        double auctualVol = 0;
        // ?????????????????????
        List<PingPack> pingPackList = new ArrayList<>();
        int goodsSize = 0;
        for (PackSonVo packSonVo : packSonVoList) {
            PingPack pingPack = new PingPack();
            pingPack.setpId(packedReqVo.getPId());
            pingPack.setPmId(packedReqVo.getId());
            pingPack.setCustomerId(packedReqVo.getCustomerId());

            pingPack.setActualL(packSonVo.getLength());
            pingPack.setActualW(packSonVo.getWidth());
            pingPack.setActualH(packSonVo.getHeight());

            pingPack.setSortNo(packSonVo.getSortNo());
            pingPack.setStatus(1);
            pingPack.setCreateTime(new Date());
//            pingPack.setCreateId(user.getId());

            pingPack.setActualWeight(packSonVo.getActualWeight());
            pingPack.setActualVol(packSonVo.getActualVol());
            // ????????????????????????
            pingPack.setPackOrder(packSonVo.getSortNo()+1 + "~" + pingMainOrder.getOrderNumber());

            // ????????????????????????????????????????????????
            for (GoodsVo goodsVo : goodsVoList) {
                if (goodsVo.getIsSensitive() != null && goodsVo.getIsSensitive() == 1 && packSonVo.getGoodsIdList().contains(goodsVo.getId())) {
                    pingPack.setIsSensitive(1);
                } else {
                    pingPack.setIsSensitive(0);
                }
            }

            pingPackList.add(pingPack);

            auctualWeight += pingPack.getActualWeight();
            auctualVol += pingPack.getActualVol();
            goodsSize += packSonVo.getGoodsIdList().size();
        }
        // ??????????????????????????????
        pingMainOrder.setGoodsNumber(goodsSize);
        pingMainOrder.setIsSensitive(isSensitive);
//        pingMainOrder.setAddressId(pingMainRespVo.getAddressId());
        // TODO ??????????????????
        if (false) {

        } else {
            // ??????????????????????????????
            ResponseEntity<ResultModel> responseEntity = pingUtil.priceConversion(routeVo, routePriceList, auctualWeight, auctualVol, pingMainOrder);
            if (responseEntity != null) {
                return responseEntity;
            }

            // ????????????
            if (packedReqVo.getIncidental() != null && packedReqVo.getIncidental().intValue() != 0) {
                pingMainOrder.setRemarks(pingMainOrder.getRemarks() == null || pingMainOrder.getRemarks().isEmpty() ?
                        "????????????" + packedReqVo.getIncidental() + "???" : pingMainOrder.getRemarks() + "; ????????????" + packedReqVo.getIncidental() + "???");
                pingMainOrder.setAuctualPrice(pingMainOrder.getAuctualPrice().add(packedReqVo.getIncidental()));
            }

            pingMainOrder.setSettlementWeight(auctualWeight);
            pingMainOrder.setSettlementVol(auctualVol);


            pingMainOrder.setPackType(PackTypeEnums.PACKED.getValue());
            pingMainOrder.setPackTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));

            // ??????????????????
            if (isSensitive == 1) {
                BigDecimal sensitivePrice = routeVo.getSensitivePrice();
                pingMainOrder.setAuctualPrice(pingMainOrder.getAuctualPrice().add(sensitivePrice));
            }
        }
        // ???????????????
        pingPackMapper.deleteByOrderId(pingMainOrder.getId(), pingMainOrder.getCustomerId());
        pingPackMapper.insertList(pingPackList);

        // ???????????????id?????????id???????????????????????????????????????
        List<PingPackRespVo> pingPackVoList = pingPackMapper.selectByPmIdAndCustomerId(packedReqVo.getId(), packedReqVo.getCustomerId());

        pingPackVoList.forEach(pingPackVo -> packSonVoList.forEach(packSonVo -> {
            if (pingPackVo.getSortNo() == packSonVo.getSortNo()) {
                packSonVo.setId(pingPackVo.getId());
            }
        }));

        // ??????????????????????????????
        List<PingPackGoods> pingPackGoodsList = new ArrayList<>();
        for (PackSonVo packSonVo : packSonVoList) {
            List<Integer> goodsIdList = packSonVo.getGoodsIdList();
            goodsIdList.forEach(goodsId -> {
                PingPackGoods pingPackGoods = new PingPackGoods();
                pingPackGoods.setGoodsId(goodsId);
                pingPackGoods.setpPackId(packSonVo.getId());
                pingPackGoodsList.add(pingPackGoods);
            });
        }
        pingPackGoodsMapper.insertList(pingPackGoodsList);

        // ???????????????????????????????????????????????????????????????
        pingPackVoList = pingPackMapper.selectByPmIdAndCustomerId(packedReqVo.getId(), packedReqVo.getCustomerId());

        // ????????????????????????????????????????????????????????????
        pingMainOrder.setRemarks(null);
        pingMainOrderMapper.updateByPrimaryKeySelective(pingMainOrder);

        // ???????????????????????????-??????????????????????????????
        CustomerPack customerPack = customerPackMapper.findByOrderNumber(pingMainOrder.getOrderNumber());
        pingUtil.customerPackSetting(pingMainOrder, customerPack, goodsVoList, pingMainRespVo.getTransportationRoute());
        customerPack.setPackNum(pingPackList.size());
        customerPackMapper.updateByPrimaryKeySelective(customerPack);

        // ???????????????????????????
        List<CustomerPackNumber> customerPackNumberList = new ArrayList<>();
        // pingPackVoList ???????????????????????????????????????????????????-?????????????????????
        pingUtil.customerPackNumberSetting(pingPackVoList, customerPackNumberList);
        customerPackNumberList.forEach(customerPackNumber -> customerPackNumber.setCustomerPackId(customerPack.getId()));

        if (customerPackNumberList.isEmpty()) {
            transactionManager.rollback(status);
            return new ResponseEntity<>(ResultModel.error(ERROR, "?????????????????????????????????"), HttpStatus.OK);
        }

        customerPackNumberMapper.deleteByPackId(customerPack.getId());
        customerPackNumberMapper.insertList(customerPackNumberList);

        // ?????????????????????????????????????????????
        List<CustomerPackNumberVo> customerPackNumberVoList = customerPackNumberMapper.findByPackId(customerPack.getId());

        // goods2pack????????????
        List<Goods2pack> goods2packList = pingUtil.goods2packSetting(customerPackNumberVoList, pingPackVoList);

        if (!goods2packList.isEmpty()) {
            goods2packMapper.insertList(goods2packList);
            // ?????????????????????????????????
            goodsMapper.updateByIdsAndGoodsType(goods2packList.stream().map(Goods2pack::getGoodsId)
                    .collect(Collectors.toList()), GOODS_PACKED.getStatus());
        }

        pingMainMapper.updateByPrimaryKeySelective(pingMainRespVo);
        transactionManager.commit(status);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }


    /**
     * ????????????
     *
     * @param length
     * @param width
     * @param height
     * @param oneLength
     * @param twoLength
     */
    private static void lengthSorting(double length, double width, double height, double oneLength, double twoLength) {
        double[] lengthList = new double[]{length, width, height};
        for (int i = 0; i < lengthList.length - 1; i++) {
            for (int j = 0; j < lengthList.length - 1; j++) {
                if (lengthList[i] < lengthList[j + 1]) {
                    double t;
                    t = lengthList[j + 1];
                    lengthList[j + 1] = lengthList[i];
                    lengthList[i] = t;
                }
            }
        }
        oneLength = lengthList[0];
        twoLength = lengthList[1];

    }
}
