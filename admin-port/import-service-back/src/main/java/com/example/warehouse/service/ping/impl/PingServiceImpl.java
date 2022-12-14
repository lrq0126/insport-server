package com.example.warehouse.service.ping.impl;

import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.*;
import com.example.warehouse.entity.image.ImageType;
import com.example.warehouse.entity.image.Images;
import com.example.warehouse.entity.ping.*;
import com.example.warehouse.enums.goods.GoodsStatus;
import com.example.warehouse.enums.customerPack.PackTypeEnums;
import com.example.warehouse.enums.ping.PingPackType;
import com.example.warehouse.enums.ping.PingTypeEnums;
import com.example.warehouse.enums.route.SettleType;
import com.example.warehouse.enums.route.TransportType;
import com.example.warehouse.mapper.CustomerMapper;
import com.example.warehouse.mapper.GoodsMapper;
import com.example.warehouse.mapper.customerPack.CustomerPackMapper;
import com.example.warehouse.mapper.customerPackNumber.CustomerPackNumberMapper;
import com.example.warehouse.mapper.customerPackReceiverAddress.CustomerPackReceiverAddressDao;
import com.example.warehouse.mapper.goods2pack.Goods2packMapper;
import com.example.warehouse.mapper.image.ImagesMapper;
import com.example.warehouse.mapper.ping.PingMainAuditMapper;
import com.example.warehouse.mapper.ping.PingMainMapper;
import com.example.warehouse.mapper.ping.PingMainOrderMapper;
import com.example.warehouse.mapper.ping.PingMemberMapper;
import com.example.warehouse.mapper.routePrice.RoutePriceMapper;
import com.example.warehouse.mapper.transportationRoute.TransportationRouteMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.ping.PingService;
import com.example.warehouse.common.PingUtil;
import com.example.warehouse.service.wechat.SendMessageServer;
import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.customer.CustomerPackVo;
import com.example.warehouse.vo.ping.requestVo.AuditRequestVo;
import com.example.warehouse.vo.ping.requestVo.PingRequestVo;
import com.example.warehouse.vo.ping.responseVo.PingMainOrderRespVo;
import com.example.warehouse.vo.ping.responseVo.PingMainRespVo;
import com.example.warehouse.vo.ping.responseVo.PingPackRespVo;
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

import static com.example.warehouse.enums.customerPack.PackTypeEnums.PACKED;
import static com.example.warehouse.enums.customerPack.PackTypeEnums.WAIT_PACK;
import static com.example.warehouse.enums.ping.PingPackType.*;
import static com.example.warehouse.enums.ResultStatus.*;
import static com.example.warehouse.enums.ping.PingTypeEnums.*;
import static com.example.warehouse.enums.ping.PingAuditResultEnum.*;
import static com.example.warehouse.enums.goods.GoodsStatus.INTO_WARE;


@Service
@Transactional
@Slf4j
public class PingServiceImpl implements PingService {
    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private PingMainMapper pingMainMapper;
    @Autowired
    private PingMainOrderMapper pingMainOrderMapper;
    @Autowired
    private PingMemberMapper memberMapper;
    @Autowired
    private PingMainAuditMapper auditMapper;
    @Autowired
    private ImagesMapper imagesMapper;
    @Autowired
    private RoutePriceMapper priceMapper;
    @Autowired
    private TransportationRouteMapper routeMapper;
    @Autowired
    private SendMessageServer sendMessageServer;

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private CustomerPackMapper customerPackMapper;
    @Autowired
    private CustomerPackNumberMapper customerPackNumberMapper;
    @Autowired
    private CustomerPackReceiverAddressDao customerPackReceiverAddressDao;
    @Autowired
    private Goods2packMapper goods2packMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private PingUtil pingUtil;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public ResponseEntity<ResultModel> getPingMainList(PingRequestVo pingRequestVo) {
        // ???????????????????????????????????????
        int i = pingMainMapper.selectCount(pingRequestVo);
        PageData pageData = PageHelp.initPage(pingRequestVo.getPageNumber(), pingRequestVo.getPageSize());
        pageData.setTotal(i);

        pingRequestVo.setPageNumber(pageData.getPageNumber());
        pingRequestVo.setPageSize(pageData.getPageSize());

        List<PingMainRespVo> pingMainList;
        // PingType() == -1 ???????????????????????????
        if (pingRequestVo.getPingType() == -1) {
            pingMainList = pingMainMapper.selectDeletedList(pingRequestVo);
        } else {
            if (pingRequestVo.getIsPacked() != null) {
                if (pingRequestVo.getIsPacked() == 3) {
                    pingRequestVo.setIsPacked(1);
                    pingRequestVo.setIsShipped(0);
                } else if (pingRequestVo.getIsPacked() == 4) {
                    pingRequestVo.setIsPacked(1);
                    pingRequestVo.setIsShipped(1);
                }
            }
            pingMainList = pingMainMapper.getPingMainList(pingRequestVo);
            // ??????????????????/????????????
//            settingActualWeight(pingMainList);
        }
        return new ResponseEntity<>(PageResultModel.ok(pingMainList, pageData), HttpStatus.OK);
    }

//    // ?????????????????????????????????
//    private void settingActualWeight(List<PingMainRespVo> pingMainList) {
//        for (PingMainRespVo pingMainRespVo : pingMainList) {
//           if(pingMainRespVo.getPingMember() == null || pingMainRespVo.getPingMember().isEmpty()){
//               continue;
//           }
//           double actualWeight = goodsMapper
//
//        }
//    }

    /**
     * ??????????????????
     *
     * @param pid      ????????????
     * @param pingType ????????????
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<ResultModel> operationTeamType(int pid, int pingType) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("??????????????????");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        // ???????????????????????????????????????????????????;
        PingMain pingMain = pingMainMapper.selectById(pid);

        // ??????????????????
        ResponseEntity<ResultModel> responseEntity = pingTypeCheck(pingMain.getPType(), pingType);
        if (responseEntity != null) {
            return responseEntity;
        }

        // TODO ??????????????????
        // ?????????????????????
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }
        pingMain.setId(pid);
        pingMain.setPType(pingType);
        pingMain.setLastUpdateTime(sdf.format(new Date()));
        pingMain.setLastUpdateId(user.getId());

        // ??????????????????????????????????????????????????????
        if (TEAM_COMPLETE.matching(pingType)) {
            responseEntity = this.pinTeamComplete(pid);
            if (responseEntity != null) {
                return responseEntity;
            }
        } else

            // ?????????????????????????????????????????????
            //      ???????????????????????????????????????????????????
            //      ?????????????????????????????????
            if (TEAM_DISSOLUTION.matching(pingType)) {
                PingMainRespVo pingMainRespVo = pingMainMapper.selectPingMainDetailedById(pid);
                List<PingMember> pingMemberList = pingMainRespVo.getPingMember();
                if (!pingMemberList.isEmpty()) {
                    pingMemberList.forEach(pingMember -> {
                        //pingMember.setLastUpdateId(user.getId());
                        pingMember.setLastUpdateTime(new Date());
                        pingMember.setStatus(0);
                    });
                    List<Integer> goodsIds = pingMemberList.stream().map(PingMember::getGoodsId).collect(Collectors.toList());
                    goodsMapper.updateByIdsAndGoodsType(goodsIds, INTO_WARE.getStatus());
                    memberMapper.updateList(pingMemberList);
                }
                List<PingMainOrderRespVo> pingMainOrderList = pingMainRespVo.getPingMainOrder();
                if (!pingMainOrderList.isEmpty()) {
                    List<PingMainOrder> pingMainOrders = new ArrayList<>();
                    pingMainOrderList.forEach(pingMainOrder -> {
                        //pingMember.setLastUpdateId(user.getId());
                        pingMainOrder.setLastUpdateTime(sdf.format(new Date()));
                        pingMainOrder.setStatus(0);
                        pingMainOrders.add(pingMainOrder);
                    });
                    pingMainOrderMapper.updateList(pingMainOrders);
                }
            }

        int i = pingMainMapper.updateByPrimaryKeySelective(pingMain);
        if (i != 1) {
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        transactionManager.commit(status);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    private CustomerPackReceiverAddress cpraSetting(int packId, CustomerPackReceiverAddress customerPackReceiverAddress) {
        CustomerPackReceiverAddress insertAddress = new CustomerPackReceiverAddress();
        insertAddress.setCustomerPackId(packId);
        insertAddress.setAddressee(customerPackReceiverAddress.getAddressee());
        insertAddress.setCode(customerPackReceiverAddress.getCode());
        insertAddress.setPhoneNumber(customerPackReceiverAddress.getPhoneNumber());
        insertAddress.setReceiverAddress(customerPackReceiverAddress.getReceiverAddress());
        insertAddress.setStatus(1);
        return insertAddress;
    }

    /**
     * ???????????????
     *
     * @param transportationRoute
     * @param goodsVos
     * @return
     */
    private BigDecimal prePriceCalculate(RouteVo transportationRoute, List<GoodsVo> goodsVos) {
        List<RoutePrice> routePrices = transportationRoute.getRoutePrices();
        double weight = 0;
        for (GoodsVo goodsVo : goodsVos) {
            weight += goodsVo.getKg();
        }
        BigDecimal prePrice = new BigDecimal(0);

        for (RoutePrice routePrice : routePrices) {
            prePrice = prePrice.compareTo(routePrice.getPrice().multiply(BigDecimal.valueOf(weight))) > 0 ?
                    prePrice : routePrice.getPrice().multiply(BigDecimal.valueOf(weight));
        }
        return prePrice;
    }

    /**
     * ????????????
     *
     * @param currentType ????????????
     * @param pingType    ????????????
     * @return
     */
    private ResponseEntity<ResultModel> pingTypeCheck(int currentType, int pingType) {
        // ?????????????????????
        if (TEAM_COMPLETE.matching(pingType) && currentType == WAITING_FOR_AUDIT.getValue()) {
            return new ResponseEntity<>(ResultModel.error(ERROR, "???????????? ???" +
                    PingTypeEnums.getNameByValue(currentType) + "??????????????????????????????"), HttpStatus.OK);
        }
        // ??????????????????
        if (currentType == pingType) {
            return new ResponseEntity<>(ResultModel.error(ERROR, "????????????????????? ???" +
                    PingTypeEnums.getNameByValue(pingType) + "??? ???????????????????????????"), HttpStatus.OK);
        }
        // ???????????????????????????
        if (TEAM_COMPLETE.matching(pingType) && currentType == TEAM_DISSOLUTION.getValue()) {
            return new ResponseEntity<>(ResultModel.error(ERROR, "????????????????????? ???" +
                    PingTypeEnums.getNameByValue(currentType) + "??? ?????????????????????"), HttpStatus.OK);
        }
        // ?????????????????????
        if (TEAM_COMPLETE.matching(pingType) && currentType == NOT_THROUGH.getValue()) {
            return new ResponseEntity<>(ResultModel.error(ERROR, "????????????????????? ???" +
                    PingTypeEnums.getNameByValue(currentType) + "??? ?????????????????????"), HttpStatus.OK);
        }
        // ???????????????????????????
        if (TEAM_COMPLETE.matching(pingType) && currentType == OVERDUE_WAIT_CONFIRM.getValue()) {
            return new ResponseEntity<>(ResultModel.error(ERROR, "??????????????? ???" +
                    PingTypeEnums.getNameByValue(currentType) + "??? ??????????????????????????????????????????????????????"), HttpStatus.OK);
        }
        // ???????????????????????????
        if (WAITING_FOR_TEAM_COMPLETE.matching(pingType) && currentType != OVERDUE_WAIT_CONFIRM.getValue()) {
            return new ResponseEntity<>(ResultModel.error(ERROR, "????????????????????? ???" +
                    PingTypeEnums.getNameByValue(currentType) + "??? ????????????????????????" + PingTypeEnums.getNameByValue(pingType) + "?????????"), HttpStatus.OK);
        }
        return null;
    }

    /**
     * ????????????
     *
     * @param auditRequestVo
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<ResultModel> operationAudit(AuditRequestVo auditRequestVo) {
        // TODO ????????????
        // ?????????????????????
        /*User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }*/
        PingMain pin = pingMainMapper.selectById(auditRequestVo.getPid());
        if (!pin.getPType().equals(WAITING_FOR_AUDIT.getValue())) {
            return new ResponseEntity<>(ResultModel.error(ERROR, "????????????????????????????????????????????????????????????"), HttpStatus.OK);
        }
        PingMainAudit pingMainAudit = new PingMainAudit();
//        pingMainAudit.setAuditorId(user.getId());
//        pingMainAudit.setAuditorName(user.getLoginName());
//        pingMainAudit.setCreateId(user.getId());
        pingMainAudit.setCreateTime(new Date());
        pingMainAudit.setAuditResult(auditRequestVo.getAuditResult());
        pingMainAudit.setAuditOpinion(auditRequestVo.getMessage());
        pingMainAudit.setPId(auditRequestVo.getPid());
        pingMainAudit.setPOrder(auditRequestVo.getPOrder());
        auditMapper.insert(pingMainAudit);

        if (auditRequestVo.getAuditResult() == NOT_PASS.getValue()) {
            pin.setPType(NOT_THROUGH.getValue());
        } else if (auditRequestVo.getAuditResult() == REFUSE.getValue()) {
            pin.setPType(TEAM_DISSOLUTION.getValue());
            pin.setStatus(0);
        } else {
            pin.setPType(WAITING_FOR_TEAM_COMPLETE.getValue());
        }
        pin.setId(auditRequestVo.getPid());
        pin.setCheckMessage(auditRequestVo.getMessage());
        pin.setIsPassed(auditRequestVo.getAuditResult());
        pingMainMapper.updateByPrimaryKeySelective(pin);
        pin = pingMainMapper.selectById(auditRequestVo.getPid());
        // ??????????????????
        sendMessageServer.sendMessageForCheckPin(pin, auditRequestVo.getAuditResult());
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    /**
     * ??????????????????
     *
     * @param pId
     * @param customerId
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> kickedMember(int pId, int customerId) {

        // ?????????????????????
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }

        // 1???ping_main_order???????????????
        PingMainRespVo pingMainRespVo = pingMainMapper.selectPingMainDetailedById(pId);
        if (pingMainRespVo.getPType() == TEAM_COMPLETE.getValue()) {
            List<PingMainOrderRespVo> pingMainOrderList = pingMainRespVo.getPingMainOrder();
            PingMainOrder pingMainOrder = new PingMainOrder();
            for (PingMainOrderRespVo pingMainOrderRespVo : pingMainOrderList) {
                if (pingMainOrderRespVo.getCustomerId().equals(customerId)) {
                    pingMainOrder = pingMainOrderRespVo;
                    pingMainOrder.setLastUpdateId(user.getId());
                    pingMainOrder.setLastUpdateTime(sdf.format(new Date()));
                    break;
                }
            }

            if (pingMainOrder.getId() == null) {
                return new ResponseEntity<>(ResultModel.error(ERROR, "??????????????????????????????"), HttpStatus.OK);
            }
            // ?????????????????????????????????
            if (pingMainOrder.getPackType() == PACKED.getValue()) {
                return new ResponseEntity<>(ResultModel.error(ERROR, "????????????????????????????????????????????????????????????"), HttpStatus.OK);
            }
            pingMainOrderMapper.removeOrder(pingMainOrder);
            // ?????????????????????????????????
            CustomerPackVo customerPackVo = customerPackMapper.findByOrderNumber(pingMainOrder.getOrderNumber());
            if (customerPackVo != null) {
                // ?????????????????????????????????????????????????????????????????????
                customerPackMapper.delete(customerPackVo.getId());
                customerPackNumberMapper.deleteByPackId(customerPackVo.getId());
                goods2packMapper.deleteByPackId(customerPackVo.getId());
            }
        }

        // 2???ping_member????????????
        List<PingMember> pingMemberList = pingMainRespVo.getPingMember();
        List<PingMember> removeMemberList = new ArrayList<>();
        List<Integer> goodsIdList = new ArrayList<>();
        for (PingMember pingMember : pingMemberList) {
            if (pingMember.getCustomerId().equals(customerId)) {
                pingMember.setLastUpdateId(user.getId());
                pingMember.setLastUpdateTime(new Date());
                removeMemberList.add(pingMember);

                goodsIdList.add(pingMember.getGoodsId());
            }
        }
        memberMapper.removeList(removeMemberList);

        // 3???goods?????????????????????
        goodsMapper.updateByIdsAndGoodsType(goodsIdList, GoodsStatus.INTO_WARE.getStatus());

        // TODO ??????????????????????????????????????????????????????????????????????????????????????????????????????
        List<Integer> remainingGoodsIdList =
                pingMemberList.stream().filter(pingMember -> !pingMember.getCustomerId().equals(customerId))
                        .map(PingMember::getGoodsId).collect(Collectors.toList());

        List<GoodsVo> goodsVoList = goodsMapper.selectByIds(remainingGoodsIdList);
        double actualWeight = goodsVoList.stream().mapToDouble(GoodsVo::getKg).sum();
        if (actualWeight < pingMainRespVo.getTargetWeight() && pingMainRespVo.getPType() != WAITING_FOR_TEAM_COMPLETE.getValue()) {
            pingMainRespVo.setPType(WAITING_FOR_TEAM_COMPLETE.getValue());
            pingMainMapper.updateByPrimaryKey(pingMainRespVo);
        }

        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    /**
     * ??????????????????
     *
     * @param pId
     * @param customerId
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> kickedMemberGoods(int pId, int customerId, int goodsId) {
        // ?????????????????????
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }

        // TODO  ??????????????????????????????????????? 1???????????????????????????????????????  2?????????????????????????????????
        PingMainRespVo pingMainRespVo = pingMainMapper.selectPingMainDetailedById(pId);

        List<PingMember> pingMemberList = pingMainRespVo.getPingMember();// ??????????????????
        if (pingMainRespVo.getPType().equals(TEAM_COMPLETE.getValue())) {

            // ??????????????????????????????????????????
            List<PingMainOrderRespVo> pingMainOrderList = pingMainRespVo.getPingMainOrder();
            PingMainOrderRespVo pingMainOrderRespVo = new PingMainOrderRespVo();

            for (PingMainOrderRespVo mainOrderRespVo : pingMainOrderList) {
                if (mainOrderRespVo.getCustomerId().equals(customerId)) {
                    pingMainOrderRespVo = mainOrderRespVo;
                }
            }

            if (pingMainOrderRespVo.getPackType() == PACKED.getValue()) {
                return new ResponseEntity<>(ResultModel.error(ERROR, "????????????????????????????????????????????????????????????"), HttpStatus.OK);
            }

            // ?????????????????????????????? ????????????ping_main_order ?????????
            // ???????????????????????????
            List<PingMember> customerMemberList = pingMemberList.stream().
                    filter(pingMember -> pingMember.getCustomerId().equals(customerId)).collect(Collectors.toList());

            // ??????????????????????????????????????????????????????????????????????????????
            if (customerMemberList.size() == 1) {
                // TODO ??????????????????????????????????????????????????????????????????

                // ??????????????????????????????
                pingMainOrderRespVo.setStatus(0);
            } else {
                // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????

                List<GoodsVo> goodsList = goodsMapper.selectByIds(customerMemberList.stream().map(PingMember::getGoodsId).collect(Collectors.toList()));

                goodsList.removeIf(goodsVo -> goodsVo.getId().equals(goodsId));

                pingMainOrderRespVo.setGoodsNumber(goodsList.size());


                double auctualWeight = goodsList.stream().mapToDouble(GoodsVo::getKg).sum();
                double auctualVol = goodsList.stream().mapToDouble(GoodsVo::getVol).sum();
                // ??????????????????????????????
                ResponseEntity<ResultModel> responseEntity =
                        pingUtil.priceConversion(pingMainRespVo.getTransportationRoute(),
                                pingMainRespVo.getTransportationRoute().getRoutePrices(), auctualWeight, auctualVol, pingMainOrderRespVo);
                if (responseEntity != null) {
                    return responseEntity;
                }
                pingMainOrderRespVo.setPreQoutedPrice(pingMainOrderRespVo.getAuctualPrice());
                pingMainOrderRespVo.setAuctualPrice(null);
            }
            pingMainOrderRespVo.setGoodsNumber(pingMainOrderRespVo.getGoodsNumber() - 1);
            pingMainOrderMapper.updateByPrimaryKey(pingMainOrderRespVo);

            // ??????????????????????????????????????????,?????????????????????
            if (pingMemberList.size() == 1) {
                // TODO ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
//                pingMainRespVo.setStatus(0);

            }


        }

        PingMember removeMember = new PingMember();
        removeMember.setpId(pId);
        removeMember.setCustomerId(customerId);
        removeMember.setGoodsId(goodsId);
        removeMember.setLastUpdateTime(new Date());
        removeMember.setLastUpdateId(user.getId());
        memberMapper.removeMember(removeMember);

        // 2?????????goods??????????????????????????????
        List<Integer> goodsIds = new ArrayList<>();
        goodsIds.add(goodsId);
        goodsMapper.updateByIdsAndGoodsType(goodsIds, GoodsStatus.INTO_WARE.getStatus());

        // TODO ??????????????????????????????????????????????????????????????????????????????????????????????????????
        List<Integer> goodsIdList =
                pingMemberList.stream().map(PingMember::getGoodsId).filter(memberGoodsId -> !memberGoodsId.equals(goodsId)).collect(Collectors.toList());

        List<GoodsVo> goodsVoList = goodsMapper.selectByIds(goodsIdList);
        double actualWeight = goodsVoList.stream().mapToDouble(GoodsVo::getKg).sum();
        if (actualWeight < pingMainRespVo.getTargetWeight()) {
            pingMainRespVo.setPType(WAITING_FOR_TEAM_COMPLETE.getValue());

        }
        pingMainMapper.updateByPrimaryKey(pingMainRespVo);

        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> deletePin(List<Integer> pIds) {
        // ?????????????????????
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }

        List<PingMain> pingMainList = pingMainMapper.selectByIdList(pIds);
        for (PingMain pingMain : pingMainList) {
            if (!pingMain.getPType().equals(TEAM_DISSOLUTION.getValue()) && !pingMain.getPType().equals(WAITING_FOR_AUDIT.getValue())) {
                return new ResponseEntity<>(ResultModel.error(ERROR, "??????????????????????????????????????????"), HttpStatus.OK);
            }
        }

        pingMainMapper.deleteByIds(pIds, user.getId());
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    /**
     * ????????????/??????????????????
     *
     * @param id    id
     * @param isTop ??????????????????
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> topOperation(int id, int isTop) {
        if (isTop == 1) {
            int i = pingMainMapper.updateTop(id, 0);
            if (i == 1) {
                return new ResponseEntity<>(ResultModel.ok("??????????????????"), HttpStatus.OK);
            }
        } else {
            int i = pingMainMapper.updateTop(id, 1);
            if (i == 1) {
                return new ResponseEntity<>(ResultModel.ok("????????????"), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
    }

    /**
     * ????????????????????????
     *
     * @param pId
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> packComplete(int pId) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("????????????????????????");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        // ???????????? ?????????????????????
        PingMainRespVo pingMainRespVo = pingMainMapper.selectPingMainDetailedById(pId);
        List<PingMainOrderRespVo> pingMainOrderVoList = pingMainRespVo.getPingMainOrder();
        // ????????????????????????????????????
        for (PingMainOrderRespVo pingMainOrderRespVo : pingMainOrderVoList) {
            if (!pingMainOrderRespVo.getPackType().equals(PING_PACKED.getValue())) {
                return new ResponseEntity<>(ResultModel.error(PIN_ORDER_PACK_NULL_ERROR), HttpStatus.OK);
            }
        }
        RouteVo routeVo = pingMainRespVo.getTransportationRoute();
        if (routeVo.getRoutePrices() == null || routeVo.getRoutePrices().isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(TRANSPORT_ROUTE_PRICE_ERROR), HttpStatus.OK);
        }
        double weight = 0;
        // 1?????????????????????????????????????????????????????????????????????
        // 2????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        if (routeVo.getRouteType() == SettleType.WEIGHT_SETTLEMENT.getValue()) {
            weight = pingMainOrderVoList.stream().mapToDouble(PingMainOrderRespVo::getSettlementWeight).sum();
            if(weight < pingMainRespVo.getTargetWeight()){
                return new ResponseEntity<>(ResultModel.error(PIN_WEIGHT_LESS_THAN_TARGET), HttpStatus.OK);
            }
        } else {
            for (PingMainOrderRespVo pingMainOrderRespVo : pingMainOrderVoList) {
                weight += pingMainOrderRespVo.getSettlementVol() * routeVo.getVolConversion();
            }
            if(weight < pingMainRespVo.getTargetWeight()){
                return new ResponseEntity<>(ResultModel.error(PIN_WEIGHT_LESS_THAN_TARGET), HttpStatus.OK);
            }
        }

        if(weight < pingMainRespVo.getTargetWeight()){
            return new ResponseEntity<>(ResultModel.error(PIN_WEIGHT_LESS_THAN_TARGET), HttpStatus.OK);
        }

        // ?????????????????????????????????????????????
        routeVo = pingUtil.priceMatching(routeVo, weight);

        // TODO ???????????????????????????????????????
//        boolean isPerTicketPack = routeVo.getIsPerTicketPack() == 1;


        // ??????????????????????????????????????????????????????????????????????????????
        List<PingMainOrder> pingMainOrderList = new ArrayList<>();
        for (PingMainOrderRespVo pingMainOrderRespVo : pingMainOrderVoList) {
            List<PingPackRespVo> pingPackVoList = pingMainOrderRespVo.getPingPack();

            double actualWeight = pingPackVoList.stream().mapToDouble(PingPack::getActualWeight).sum();
            double actualVol = pingPackVoList.stream().mapToDouble(PingPack::getActualVol).sum();
            // ??????????????????????????????
            ResponseEntity<ResultModel> responseEntity =
                    pingUtil.priceConversion(routeVo, routeVo.getRoutePrices(), actualWeight, actualVol, pingMainOrderRespVo);
            if (responseEntity != null) {
                return responseEntity;
            }

            // ????????????????????????
            if (pingMainOrderRespVo.getRemarks() != null && pingMainOrderRespVo.getRemarks().indexOf("??????") == -1) {
                StringBuilder packMessages = new StringBuilder("\n????????????");
                int i = 1;
                for (PingPack p : pingPackVoList) {
                    packMessages.append(i + ": " + p.getActualWeight() + "kg (" + p.getActualL() + "*" + p.getActualW() + "*" + p.getActualH() + ")cm ");
                    i++;
                }
                packMessages.append("???\n");
                if (pingMainOrderRespVo.getRemarks() == null || pingMainOrderRespVo.getRemarks().isEmpty()) {
                    pingMainOrderRespVo.setRemarks(packMessages.toString());
                } else {
                    pingMainOrderRespVo.setRemarks(pingMainOrderRespVo.getRemarks() + "???" + packMessages);
                }

            }

            pingMainOrderList.add(pingMainOrderRespVo);
        }


        // ?????????????????????????????????
        List<CustomerPack> customerPackList = new ArrayList<>();

        List<GoodsVo> goodsVoList = goodsMapper.selectByIds(pingMainRespVo.getPingMember().stream().map(PingMember::getGoodsId).collect(Collectors.toList()));
        // customerPackList???customerPackNumberList????????????
        for (PingMainOrderRespVo pingMainOrderRespVo : pingMainOrderVoList) {
            List<GoodsVo> customerGoodsList = new ArrayList<>();
            pingMainOrderRespVo.setPackType(PingPackType.PING_PACKED.getValue());
            goodsVoList.forEach(goodsVo -> {
                if (pingMainOrderRespVo.getCustomerId().equals(goodsVo.getCustomerId())) {
                    customerGoodsList.add(goodsVo);
                }
            });
            CustomerPack customerPack = new CustomerPack();

            // ????????????????????????
            pingUtil.customerPackSetting(pingMainOrderRespVo, customerPack, customerGoodsList, pingMainOrderRespVo.getRouteVo());
            customerPack.setPackType(PackTypeEnums.PACKED.getValue());
            customerPackList.add(customerPack);

        }
        if (customerPackList.isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(ERROR, "?????????????????????????????????"), HttpStatus.OK);
        }

        List<CustomerPackVo> customerPackVoList =
                customerPackMapper.selectByOrderNumberList(pingMainOrderList.stream().map(PingMainOrder::getOrderNumber).collect(Collectors.toList()));

        for (CustomerPackVo customerPackVo : customerPackVoList) {
            if (customerPackVo.getCustomerPackNumbers().isEmpty()) {
                return new ResponseEntity<>(ResultModel.error(ERROR,
                        "?????????" + customerPackVo.getOrderNumber() + "???????????????????????????????????????"), HttpStatus.OK);
            }
        }

        pingMainOrderMapper.updateList(pingMainOrderList);
        customerPackMapper.updateList(customerPackList);

        pingMainRespVo.setIsPacked(1);
        pingMainRespVo.setIsShipped(0);
        pingMainMapper.updateByPrimaryKeySelective(pingMainRespVo);
        transactionManager.commit(status);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }


    /**
     * ????????????????????????
     *
     * @param pid
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> getPingMainDetailed(int pid) {
        PingMainRespVo pingMainRespVo = pingMainMapper.selectPingMainDetailedById(pid);

        if (pingMainRespVo == null) {
            return new ResponseEntity<>(ResultModel.error(PIN_NULL_ERROR), HttpStatus.OK);
        }

        if(pingMainRespVo.getTransportationRoute() == null){
            return new ResponseEntity<>(ResultModel.error(TRANSPORTROUTE_ERROR), HttpStatus.OK);
        }

        if(pingMainRespVo.getTransportationRoute().getRoutePrices() == null
                || pingMainRespVo.getTransportationRoute().getRoutePrices().isEmpty()){
            return new ResponseEntity<>(ResultModel.error(TRANSPORT_ROUTE_PRICE_ERROR), HttpStatus.OK);
        }

        pingMainRespVo = pingMainEnhance(pingMainRespVo);

        // ?????????????????????????????????????????????????????????????????????
        if (pingMainRespVo.getPType() == WAITING_FOR_AUDIT.getValue()) {
            return new ResponseEntity<>(ResultModel.ok(pingMainRespVo), HttpStatus.OK);
        }

        List<PingMainOrderRespVo> pingMainOrderList = pingMainRespVo.getPingMainOrder();

        // ??????member?????????goodsId???????????????
        List<PingMember> pingMemberList = pingMainRespVo.getPingMember();
        if (pingMemberList == null || pingMemberList.isEmpty()) {
            return new ResponseEntity<>(ResultModel.ok(pingMainRespVo), HttpStatus.OK);
        }

        // ?????????????????????????????????
        if (pingMainRespVo.getPType() == WAITING_FOR_TEAM_COMPLETE.getValue() || pingMainRespVo.getPType() == OVERDUE_WAIT_CONFIRM.getValue()) {
            pingMainOrderList = new ArrayList<>();

            for (Integer customerId : pingMemberList.stream().map(PingMember::getCustomerId).distinct().collect(Collectors.toList())) {
                PingMainOrderRespVo pingMainOrderResp = new PingMainOrderRespVo();
                pingMainOrderResp.setPId(pid);
                pingMainOrderResp.setCustomerId(customerId);
                pingMainOrderList.add(pingMainOrderResp);
            }
        }

        List<Integer> goodsIdList = pingMemberList.stream().map(PingMember::getGoodsId).collect(Collectors.toList());

        if (!goodsIdList.isEmpty()) {
            List<GoodsVo> goodsList = goodsMapper.selectByIds(goodsIdList);
            // ???goods?????????????????????
            for (PingMainOrderRespVo pingMainOrderRespVo : pingMainOrderList) {

                // ??????????????????
                for (PingMember pingMember : pingMemberList) {
                    if (pingMainOrderRespVo.getCustomerId().equals(pingMember.getCustomerId())) {
                        pingMainOrderRespVo.setPartTime(sdf.format(pingMember.getAddTime()));
                        break;
                    }
                }

                List<GoodsVo> goodsVos = new ArrayList<>();
                for (GoodsVo goodsVo : goodsList) {
                    // ??????????????????
                    for (PingMember pingMember : pingMemberList) {
                        if (pingMember.getGoodsId().equals(goodsVo.getId())) {
                            goodsVo.setGoodsPartTime(sdf.format(pingMember.getAddTime()));
                        }
                    }

                    if (pingMainOrderRespVo.getCustomerId().equals(goodsVo.getCustomerId())) {
                        pingMainOrderRespVo.setLoginName(goodsVo.getLoginName());
                        pingMainOrderRespVo.setCustomerName(goodsVo.getCustomerName());
                        goodsVos.add(goodsVo);
                    }
                }
                pingMainOrderRespVo.setGoods(goodsVos);
                pingMainOrderRespVo.setGoodsNumber(goodsVos.size());
                // ??????????????????
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                pingMainOrderRespVo.setForecastWeight(Double.valueOf(decimalFormat.format(goodsVos.stream().mapToDouble(GoodsVo::getKg).sum())));
            }
        }
        pingMainRespVo.setPingMainOrder(pingMainOrderList);
        return new ResponseEntity<>(ResultModel.ok(pingMainRespVo), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<ResultModel> shipped(int id) {
        PingMainRespVo pingMainRespVo = pingMainMapper.selectPingMainDetailedById(id);
        List<PingMainOrderRespVo> pingMainOrderRespVoList = pingMainRespVo.getPingMainOrder();
        // TODO ???????????????????????????????????????
        boolean isAllPay = cheekPingOrderIsPay(pingMainOrderRespVoList);
        if(!isAllPay){
            return new ResponseEntity<>(ResultModel.error(A_PIN_MEMBER_NOT_PAID), HttpStatus.OK);
        }
        List<String> orderNumberList = pingMainOrderRespVoList.stream().map(PingMainOrderRespVo::getOrderNumber).collect(Collectors.toList());

        // TODO ????????????????????????????????????
        pingMainOrderMapper.updateShippedByOrderNumber(orderNumberList);
        customerPackMapper.updateShippedByOrderNumber(orderNumberList);

        // TODO ???????????????????????????????????????
        List<PingMember> pingMemberList = pingMainRespVo.getPingMember();
        List<Integer> goodsIdList = pingMemberList.stream().map(PingMember::getGoodsId).collect(Collectors.toList());
        goodsMapper.updateOutWare(goodsIdList);

        pingMainMapper.updateByShipped(id);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    private boolean cheekPingOrderIsPay(List<PingMainOrderRespVo> pingMainOrderRespVoList) {
        for (PingMainOrderRespVo pingMainOrderRespVo : pingMainOrderRespVoList) {
            if(pingMainOrderRespVo.getIsPaid() != 1){
                return false;
            }
        }
        return true;
    }


    private ResponseEntity<ResultModel> pinTeamComplete(Integer pid) {

        PingMainRespVo pingMainRespVo = pingMainMapper.selectPingMainDetailedById(pid);

        List<PingMember> pingMembers = pingMainRespVo.getPingMember();
        if (pingMembers.isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(ERROR, "????????????????????????????????????????????????"), HttpStatus.OK);
        }

        List<Integer> customerIdList = pingMembers.stream().map(PingMember::getCustomerId).distinct().collect(Collectors.toList());
        // ???????????????????????????
        List<GoodsVo> goodsVoAllList = goodsMapper.selectByIds(pingMembers.stream().map(PingMember::getGoodsId).collect(Collectors.toList()));

        RouteVo routeVo = pingUtil.priceMatching(pingMainRespVo.getTransportationRoute(), goodsVoAllList.stream().mapToDouble(Goods::getKg).sum());

        List<Customer> customerList = customerMapper.findByIdList(customerIdList);

        List<PingMainOrderRespVo> pingMainOrderRespVoList = pingMainRespVo.getPingMainOrder();
        // ??????????????????????????????????????????????????????????????????????????????????????????????????????
        // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        List<String> customerPackOrderNumberList = new ArrayList<>();
        if (pingMainOrderRespVoList != null && !pingMainOrderRespVoList.isEmpty()) {
            customerPackOrderNumberList = pingMainOrderRespVoList.stream().map(PingMainOrder::getOrderNumber).collect(Collectors.toList());
        }
        List<CustomerPack> insertCustomerPackList = new ArrayList<>();
        List<CustomerPack> updateCustomerPackList = new ArrayList<>();
        List<PingMainOrder> insertPingMainOrderList = new ArrayList<>();
        List<PingMainOrder> updatePingMainOrderList = new ArrayList<>();
        // ????????????id?????? PingMainOrder ?????????????????????
        for (int customerId : customerIdList) {
            Customer customer = new Customer();
            for (Customer c : customerList) {
                if(c.getId() == customerId){
                    customer = c;
                    break;
                }
            }
            CustomerPack customerPack = new CustomerPack();

            // ???????????????????????????
            List<GoodsVo> customerGoods = new ArrayList<>();
            for (GoodsVo goodsVo : goodsVoAllList) {
                if (goodsVo.getCustomerId().equals(customerId)) {
                    customerGoods.add(goodsVo);
                }
            }
            PingMainOrder pingMainOrder = new PingMainOrder();
            pingMainOrder.setPId(pid);
            pingMainOrder.setCustomerId(customerId);
            pingMainOrder.setRouteId(pingMainRespVo.getRouteId());
            pingMainOrder.setGoodsNumber(customerGoods.size());
            pingMainOrder.setGroupTime(sdf.format(new Date()));
            //pingMainOrder.setCreateId(user.getId());
            pingMainOrder.setCreateTime(sdf.format(new Date()));
            pingMainOrder.setOrderNumber(pingMainRespVo.getOrderNumber() + "C" + customer.getLoginName());

            double actualWeight = customerGoods.stream().mapToDouble(GoodsVo::getKg).sum();
            double actualVol = customerGoods.stream().mapToDouble(GoodsVo::getVol).sum();

            // ??????????????????????????????
            ResponseEntity<ResultModel> responseEntity =
                    pingUtil.priceConversion(routeVo, routeVo.getRoutePrices(), actualWeight, actualVol, pingMainOrder);
            pingMainOrder.setPreQoutedPrice(pingMainOrder.getAuctualPrice());
            pingMainOrder.setAuctualPrice(null);
            if (responseEntity != null) {
                return responseEntity;
            }
            // ????????????????????????
            pingUtil.customerPackSetting(pingMainOrder, customerPack, customerGoods, routeVo);
            if (customerPackOrderNumberList.contains(customerPack.getOrderNumber())) {
                updateCustomerPackList.add(customerPack);
                updatePingMainOrderList.add(pingMainOrder);
            } else {
                insertCustomerPackList.add(customerPack);
                insertPingMainOrderList.add(pingMainOrder);
            }
        }
        if (!insertPingMainOrderList.isEmpty()) {
            insertPingMainOrderList.forEach(pingMainOrder -> {
                pingMainOrder.setPackType(PING_NOT_PACK.getValue());
                pingMainOrder.setIsPicked(0);
                pingMainOrder.setIsPrinted(0);
                pingMainOrder.setIsPaid(0);
                pingMainOrder.setStatus(1);
            });
            pingMainOrderMapper.insertList(insertPingMainOrderList);
        }
        if (!insertCustomerPackList.isEmpty()) {
            insertCustomerPackList.forEach(customerPack -> {
                customerPack.setPackType(WAIT_PACK.getValue());
                customerPack.setStatus(1);
            });
            // ?????????????????????????????????
            customerPackMapper.insertList(insertCustomerPackList);
            customerPackOrderNumberList.addAll(insertCustomerPackList.stream().map(CustomerPack::getOrderNumber).collect(Collectors.toList()));
        }
        if (!updatePingMainOrderList.isEmpty()) {
            pingMainOrderMapper.updateListByOrderNumber(updatePingMainOrderList);
        }
        if (!updateCustomerPackList.isEmpty()) {
            // ?????????????????????????????????
            customerPackMapper.updateListByOrderNumber(updateCustomerPackList);
        }

        List<CustomerPackVo> customerPackVoList = customerPackMapper.selectByOrderNumberList(customerPackOrderNumberList);
        goods2packMapper.deleteByPackIdList(customerPackVoList.stream().map(CustomerPackVo::getId).collect(Collectors.toList()));
        List<Goods2pack> insertGoods2packList = new ArrayList<>();
        for (CustomerPack customerPack : customerPackVoList) {
            for (PingMember pingMember : pingMainRespVo.getPingMember()) {
                if (customerPack.getCustomerId().equals(pingMember.getCustomerId())) {
                    Goods2pack goods2pack = new Goods2pack();
                    goods2pack.setPackId(customerPack.getId());
                    goods2pack.setGoodsId(pingMember.getGoodsId());
                    goods2pack.setStatus(1);
                    insertGoods2packList.add(goods2pack);
                }
            }
        }

        if (!insertGoods2packList.isEmpty()) {
            // ???????????????????????????
            goods2packMapper.insertList(insertGoods2packList);
        }
        // ????????????????????????????????????
        CustomerPackReceiverAddress customerPackReceiverAddress = customerPackReceiverAddressDao.queryById(pingMainRespVo.getAddressId());
        List<CustomerPackReceiverAddress> customerPackReceiverAddressList = new ArrayList<>();
        for (CustomerPack customerPack : customerPackVoList) {
            CustomerPackReceiverAddress insertAddress = cpraSetting(customerPack.getId(), customerPackReceiverAddress);
            customerPackReceiverAddressList.add(insertAddress);
        }
        if (!customerPackReceiverAddressList.isEmpty()) {
            // ?????????????????????????????????????????????????????????
            customerPackReceiverAddressDao.deleteByCustomerPackIdList(customerPackReceiverAddressList.stream().
                    map(CustomerPackReceiverAddress::getCustomerPackId).collect(Collectors.toList()));
            customerPackReceiverAddressDao.insertList(customerPackReceiverAddressList);
        }

        return null;
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????
     *
     * @param pingMainRespVo
     * @return
     */
    private PingMainRespVo pingMainEnhance(PingMainRespVo pingMainRespVo) {
        // ??????????????????
        Images image = new Images();
        image.setContentId(pingMainRespVo.getOrderNumber());
        image.setPicType(ImageType.PinPicture.toString());
        List<Images> images = imagesMapper.getImagesByContentId(image).isEmpty() ? new ArrayList<>() : imagesMapper.getImagesByContentId(image);
        pingMainRespVo.setImages(images);
        // ?????????????????? end

        if(pingMainRespVo.getTransportationRoute() != null){
            // ??????????????????
            pingMainRespVo.setTransportationRoute(pingUtil.priceMatching(pingMainRespVo.getTransportationRoute(), pingMainRespVo.getTargetWeight()));
        }

        return pingMainRespVo;
    }


}
