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
        // 条件查询总数，用于页码计算
        int i = pingMainMapper.selectCount(pingRequestVo);
        PageData pageData = PageHelp.initPage(pingRequestVo.getPageNumber(), pingRequestVo.getPageSize());
        pageData.setTotal(i);

        pingRequestVo.setPageNumber(pageData.getPageNumber());
        pingRequestVo.setPageSize(pageData.getPageSize());

        List<PingMainRespVo> pingMainList;
        // PingType() == -1 为查询已删除的订单
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
            // 添加当前重量/实际重量
//            settingActualWeight(pingMainList);
        }
        return new ResponseEntity<>(PageResultModel.ok(pingMainList, pageData), HttpStatus.OK);
    }

//    // 获取当前订单的实际重量
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
     * 更改拼团状态
     *
     * @param pid      拼团主表
     * @param pingType 拼团状态
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<ResultModel> operationTeamType(int pid, int pingType) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("更改拼团状态");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        // 等待成团和未通过只改变拼团主表信息;
        PingMain pingMain = pingMainMapper.selectById(pid);

        // 拼团状态检验
        ResponseEntity<ResultModel> responseEntity = pingTypeCheck(pingMain.getPType(), pingType);
        if (responseEntity != null) {
            return responseEntity;
        }

        // TODO 上线时候放行
        // 获取当前登录人
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }
        pingMain.setId(pid);
        pingMain.setPType(pingType);
        pingMain.setLastUpdateTime(sdf.format(new Date()));
        pingMain.setLastUpdateId(user.getId());

        // 成团改变拼团主表信息、生成拼团子订单
        if (TEAM_COMPLETE.matching(pingType)) {
            responseEntity = this.pinTeamComplete(pid);
            if (responseEntity != null) {
                return responseEntity;
            }
        } else

            // 解散（拆团）改变拼团主表信息、
            //      └已成团：删除子订单、修改货物状态
            //      └未成团：修改货物状态
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
     * 预报价计算
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
     * 状态校验
     *
     * @param currentType 当前状态
     * @param pingType    变更状态
     * @return
     */
    private ResponseEntity<ResultModel> pingTypeCheck(int currentType, int pingType) {
        // 未审核状态成团
        if (TEAM_COMPLETE.matching(pingType) && currentType == WAITING_FOR_AUDIT.getValue()) {
            return new ResponseEntity<>(ResultModel.error(ERROR, "拼团订单 【" +
                    PingTypeEnums.getNameByValue(currentType) + "】，请先审核后再操作"), HttpStatus.OK);
        }
        // 状态重复操作
        if (currentType == pingType) {
            return new ResponseEntity<>(ResultModel.error(ERROR, "拼团订单已经是 【" +
                    PingTypeEnums.getNameByValue(pingType) + "】 状态，无需重复操作"), HttpStatus.OK);
        }
        // 订单已取消状态成团
        if (TEAM_COMPLETE.matching(pingType) && currentType == TEAM_DISSOLUTION.getValue()) {
            return new ResponseEntity<>(ResultModel.error(ERROR, "拼团订单已经是 【" +
                    PingTypeEnums.getNameByValue(currentType) + "】 状态，不可操作"), HttpStatus.OK);
        }
        // 未通过状态成团
        if (TEAM_COMPLETE.matching(pingType) && currentType == NOT_THROUGH.getValue()) {
            return new ResponseEntity<>(ResultModel.error(ERROR, "拼团订单已经是 【" +
                    PingTypeEnums.getNameByValue(currentType) + "】 状态，不可操作"), HttpStatus.OK);
        }
        // 过期待确认状态成团
        if (TEAM_COMPLETE.matching(pingType) && currentType == OVERDUE_WAIT_CONFIRM.getValue()) {
            return new ResponseEntity<>(ResultModel.error(ERROR, "拼团订单是 【" +
                    PingTypeEnums.getNameByValue(currentType) + "】 状态，请先通知团长确认重新激活再操作"), HttpStatus.OK);
        }
        // 过期待确认状态成团
        if (WAITING_FOR_TEAM_COMPLETE.matching(pingType) && currentType != OVERDUE_WAIT_CONFIRM.getValue()) {
            return new ResponseEntity<>(ResultModel.error(ERROR, "拼团订单只有在 【" +
                    PingTypeEnums.getNameByValue(currentType) + "】 状态才能变成为【" + PingTypeEnums.getNameByValue(pingType) + "】状态"), HttpStatus.OK);
        }
        return null;
    }

    /**
     * 拼团审核
     *
     * @param auditRequestVo
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<ResultModel> operationAudit(AuditRequestVo auditRequestVo) {
        // TODO 线上启用
        // 获取当前登录人
        /*User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }*/
        PingMain pin = pingMainMapper.selectById(auditRequestVo.getPid());
        if (!pin.getPType().equals(WAITING_FOR_AUDIT.getValue())) {
            return new ResponseEntity<>(ResultModel.error(ERROR, "只有待审核状态的拼团订单才能进行审核操作"), HttpStatus.OK);
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
        // 发送消息通知
        sendMessageServer.sendMessageForCheckPin(pin, auditRequestVo.getAuditResult());
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    /**
     * 踢出某个成员
     *
     * @param pId
     * @param customerId
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> kickedMember(int pId, int customerId) {

        // 获取当前登录人
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }

        // 1、ping_main_order表状态改变
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
                return new ResponseEntity<>(ResultModel.error(ERROR, "当前子包裹已经被删除"), HttpStatus.OK);
            }
            // 已打包好的团员不能踢出
            if (pingMainOrder.getPackType() == PACKED.getValue()) {
                return new ResponseEntity<>(ResultModel.error(ERROR, "当前子包裹已经打包好，不能做货物踢出操作"), HttpStatus.OK);
            }
            pingMainOrderMapper.removeOrder(pingMainOrder);
            // 如果踢出的客户存在包裹
            CustomerPackVo customerPackVo = customerPackMapper.findByOrderNumber(pingMainOrder.getOrderNumber());
            if (customerPackVo != null) {
                // 删除客户包裹表、包裹子表、包裹关联表的相关数据
                customerPackMapper.delete(customerPackVo.getId());
                customerPackNumberMapper.deleteByPackId(customerPackVo.getId());
                goods2packMapper.deleteByPackId(customerPackVo.getId());
            }
        }

        // 2、ping_member状态变更
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

        // 3、goods表货物状态变更
        goodsMapper.updateByIdsAndGoodsType(goodsIdList, GoodsStatus.INTO_WARE.getStatus());

        // TODO 检验剩下的货物重量是否足够拼团的目标重量，不够则将拼团状态改成未成团
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
     * 踢出某个货物
     *
     * @param pId
     * @param customerId
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> kickedMemberGoods(int pId, int customerId, int goodsId) {
        // 获取当前登录人
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }

        // TODO  如果是已成团状态，需要考虑 1、子订单包裹是不是最后一个  2、是不是订单的最后一个
        PingMainRespVo pingMainRespVo = pingMainMapper.selectPingMainDetailedById(pId);

        List<PingMember> pingMemberList = pingMainRespVo.getPingMember();// 拼团成员数据
        if (pingMainRespVo.getPType().equals(TEAM_COMPLETE.getValue())) {

            // 筛选属于这个客户的子订单数据
            List<PingMainOrderRespVo> pingMainOrderList = pingMainRespVo.getPingMainOrder();
            PingMainOrderRespVo pingMainOrderRespVo = new PingMainOrderRespVo();

            for (PingMainOrderRespVo mainOrderRespVo : pingMainOrderList) {
                if (mainOrderRespVo.getCustomerId().equals(customerId)) {
                    pingMainOrderRespVo = mainOrderRespVo;
                }
            }

            if (pingMainOrderRespVo.getPackType() == PACKED.getValue()) {
                return new ResponseEntity<>(ResultModel.error(ERROR, "当前子包裹已经打包好，不能做货物踢出操作"), HttpStatus.OK);
            }

            // 已成团状态，需要修改 子订单表ping_main_order 的数据
            // 当前客户的拼团货物
            List<PingMember> customerMemberList = pingMemberList.stream().
                    filter(pingMember -> pingMember.getCustomerId().equals(customerId)).collect(Collectors.toList());

            // 如果该客户的包裹只剩下一个，删除这个客户的子订单数据
            if (customerMemberList.size() == 1) {
                // TODO 客户最后一个包裹需要发送订单被删除的消息提醒

                // 订单状态设置为已删除
                pingMainOrderRespVo.setStatus(0);
            } else {
                // 计算剩下货物的实际价格（第一次实际价格，最终实际价格在拼团打包完成后再总体计算一次）

                List<GoodsVo> goodsList = goodsMapper.selectByIds(customerMemberList.stream().map(PingMember::getGoodsId).collect(Collectors.toList()));

                goodsList.removeIf(goodsVo -> goodsVo.getId().equals(goodsId));

                pingMainOrderRespVo.setGoodsNumber(goodsList.size());


                double auctualWeight = goodsList.stream().mapToDouble(GoodsVo::getKg).sum();
                double auctualVol = goodsList.stream().mapToDouble(GoodsVo::getVol).sum();
                // 重量和体积的价格转换
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

            // 如果该拼团订单只剩下一个包裹,则整个订单删除
            if (pingMemberList.size() == 1) {
                // TODO 最后一个包裹需要发送订单被删除的消息提醒（如果是团长自己的，则只发送一条，如果是其他团员的，则要发给团长一条、客户一条）
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

        // 2、修改goods表的货物状态（入库）
        List<Integer> goodsIds = new ArrayList<>();
        goodsIds.add(goodsId);
        goodsMapper.updateByIdsAndGoodsType(goodsIds, GoodsStatus.INTO_WARE.getStatus());

        // TODO 检验剩下的货物重量是否足够拼团的目标重量，不够则将拼团状态改成未成团
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
        // 获取当前登录人
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }

        List<PingMain> pingMainList = pingMainMapper.selectByIdList(pIds);
        for (PingMain pingMain : pingMainList) {
            if (!pingMain.getPType().equals(TEAM_DISSOLUTION.getValue()) && !pingMain.getPType().equals(WAITING_FOR_AUDIT.getValue())) {
                return new ResponseEntity<>(ResultModel.error(ERROR, "只有已解散的拼团订单才能删除"), HttpStatus.OK);
            }
        }

        pingMainMapper.deleteByIds(pIds, user.getId());
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    /**
     * 置顶操作/取消置顶操作
     *
     * @param id    id
     * @param isTop 是否置顶状态
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> topOperation(int id, int isTop) {
        if (isTop == 1) {
            int i = pingMainMapper.updateTop(id, 0);
            if (i == 1) {
                return new ResponseEntity<>(ResultModel.ok("取消置顶完成"), HttpStatus.OK);
            }
        } else {
            int i = pingMainMapper.updateTop(id, 1);
            if (i == 1) {
                return new ResponseEntity<>(ResultModel.ok("置顶完成"), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
    }

    /**
     * 拼团打包确认完成
     *
     * @param pId
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> packComplete(int pId) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("包裹信息修改操作");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        // 完成打包 主订单状态变更
        PingMainRespVo pingMainRespVo = pingMainMapper.selectPingMainDetailedById(pId);
        List<PingMainOrderRespVo> pingMainOrderVoList = pingMainRespVo.getPingMainOrder();
        // 判断所有客户是否打包完成
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
        // 1、结算方式按照体积计算的需要把体积转换成体积重
        // 2、计算货物的总重量或者总体积是否符合拼团订单的目标重量或者体积，如果小于则返回提示信息；
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

        // 获取最终拼团订单结算的价格区间
        routeVo = pingUtil.priceMatching(routeVo, weight);

        // TODO 每票一件在拼团订单中不适用
//        boolean isPerTicketPack = routeVo.getIsPerTicketPack() == 1;


        // 子订单包裹状态不变更，结算价格重新计算、备注重新修改
        List<PingMainOrder> pingMainOrderList = new ArrayList<>();
        for (PingMainOrderRespVo pingMainOrderRespVo : pingMainOrderVoList) {
            List<PingPackRespVo> pingPackVoList = pingMainOrderRespVo.getPingPack();

            double actualWeight = pingPackVoList.stream().mapToDouble(PingPack::getActualWeight).sum();
            double actualVol = pingPackVoList.stream().mapToDouble(PingPack::getActualVol).sum();
            // 重量和体积的价格转换
            ResponseEntity<ResultModel> responseEntity =
                    pingUtil.priceConversion(routeVo, routeVo.getRoutePrices(), actualWeight, actualVol, pingMainOrderRespVo);
            if (responseEntity != null) {
                return responseEntity;
            }

            // 添加包裹尺寸信息
            if (pingMainOrderRespVo.getRemarks() != null && pingMainOrderRespVo.getRemarks().indexOf("尺寸") == -1) {
                StringBuilder packMessages = new StringBuilder("\n【尺寸：");
                int i = 1;
                for (PingPack p : pingPackVoList) {
                    packMessages.append(i + ": " + p.getActualWeight() + "kg (" + p.getActualL() + "*" + p.getActualW() + "*" + p.getActualH() + ")cm ");
                    i++;
                }
                packMessages.append("】\n");
                if (pingMainOrderRespVo.getRemarks() == null || pingMainOrderRespVo.getRemarks().isEmpty()) {
                    pingMainOrderRespVo.setRemarks(packMessages.toString());
                } else {
                    pingMainOrderRespVo.setRemarks(pingMainOrderRespVo.getRemarks() + "；" + packMessages);
                }

            }

            pingMainOrderList.add(pingMainOrderRespVo);
        }


        // 客户包裹表更新对应数据
        List<CustomerPack> customerPackList = new ArrayList<>();

        List<GoodsVo> goodsVoList = goodsMapper.selectByIds(pingMainRespVo.getPingMember().stream().map(PingMember::getGoodsId).collect(Collectors.toList()));
        // customerPackList、customerPackNumberList数据装载
        for (PingMainOrderRespVo pingMainOrderRespVo : pingMainOrderVoList) {
            List<GoodsVo> customerGoodsList = new ArrayList<>();
            pingMainOrderRespVo.setPackType(PingPackType.PING_PACKED.getValue());
            goodsVoList.forEach(goodsVo -> {
                if (pingMainOrderRespVo.getCustomerId().equals(goodsVo.getCustomerId())) {
                    customerGoodsList.add(goodsVo);
                }
            });
            CustomerPack customerPack = new CustomerPack();

            // 客户包裹数据填充
            pingUtil.customerPackSetting(pingMainOrderRespVo, customerPack, customerGoodsList, pingMainOrderRespVo.getRouteVo());
            customerPack.setPackType(PackTypeEnums.PACKED.getValue());
            customerPackList.add(customerPack);

        }
        if (customerPackList.isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(ERROR, "总包裹数据为空，请检查"), HttpStatus.OK);
        }

        List<CustomerPackVo> customerPackVoList =
                customerPackMapper.selectByOrderNumberList(pingMainOrderList.stream().map(PingMainOrder::getOrderNumber).collect(Collectors.toList()));

        for (CustomerPackVo customerPackVo : customerPackVoList) {
            if (customerPackVo.getCustomerPackNumbers().isEmpty()) {
                return new ResponseEntity<>(ResultModel.error(ERROR,
                        "订单【" + customerPackVo.getOrderNumber() + "】的子包裹数据为空，请检查"), HttpStatus.OK);
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
     * 查询拼单主表详情
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

        // 如果是待审核状态的，直接返回（只需要基础信息）
        if (pingMainRespVo.getPType() == WAITING_FOR_AUDIT.getValue()) {
            return new ResponseEntity<>(ResultModel.ok(pingMainRespVo), HttpStatus.OK);
        }

        List<PingMainOrderRespVo> pingMainOrderList = pingMainRespVo.getPingMainOrder();

        // 根据member表中的goodsId查询货物表
        List<PingMember> pingMemberList = pingMainRespVo.getPingMember();
        if (pingMemberList == null || pingMemberList.isEmpty()) {
            return new ResponseEntity<>(ResultModel.ok(pingMainRespVo), HttpStatus.OK);
        }

        // 以下是拼团中状态的信息
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
            // 把goods存入子订单实体
            for (PingMainOrderRespVo pingMainOrderRespVo : pingMainOrderList) {

                // 筛选参团时间
                for (PingMember pingMember : pingMemberList) {
                    if (pingMainOrderRespVo.getCustomerId().equals(pingMember.getCustomerId())) {
                        pingMainOrderRespVo.setPartTime(sdf.format(pingMember.getAddTime()));
                        break;
                    }
                }

                List<GoodsVo> goodsVos = new ArrayList<>();
                for (GoodsVo goodsVo : goodsList) {
                    // 包裹参团时间
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
                // 包裹预估重量
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
        // TODO 检验是否所有客户都已经付款
        boolean isAllPay = cheekPingOrderIsPay(pingMainOrderRespVoList);
        if(!isAllPay){
            return new ResponseEntity<>(ResultModel.error(A_PIN_MEMBER_NOT_PAID), HttpStatus.OK);
        }
        List<String> orderNumberList = pingMainOrderRespVoList.stream().map(PingMainOrderRespVo::getOrderNumber).collect(Collectors.toList());

        // TODO 更改所有子订单的发货状态
        pingMainOrderMapper.updateShippedByOrderNumber(orderNumberList);
        customerPackMapper.updateShippedByOrderNumber(orderNumberList);

        // TODO 更改所有客户包裹的发货状态
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
            return new ResponseEntity<>(ResultModel.error(ERROR, "拼团成员为空，请添加成员后再成团"), HttpStatus.OK);
        }

        List<Integer> customerIdList = pingMembers.stream().map(PingMember::getCustomerId).distinct().collect(Collectors.toList());
        // 拼团订单的所有货物
        List<GoodsVo> goodsVoAllList = goodsMapper.selectByIds(pingMembers.stream().map(PingMember::getGoodsId).collect(Collectors.toList()));

        RouteVo routeVo = pingUtil.priceMatching(pingMainRespVo.getTransportationRoute(), goodsVoAllList.stream().mapToDouble(Goods::getKg).sum());

        List<Customer> customerList = customerMapper.findByIdList(customerIdList);

        List<PingMainOrderRespVo> pingMainOrderRespVoList = pingMainRespVo.getPingMainOrder();
        // 当子订单数据为空的时候，则说明这订单是第一次成团，要添加子订单新信息
        // 二次成团的原因是成团后，有货物被踢，导致重量不足以成团，变成未成团状态继续拼团
        // 成团时候，需要检测哪些客户是已经成团了生成了包裹数据，未生成过包裹数据的客户要再添加
        List<String> customerPackOrderNumberList = new ArrayList<>();
        if (pingMainOrderRespVoList != null && !pingMainOrderRespVoList.isEmpty()) {
            customerPackOrderNumberList = pingMainOrderRespVoList.stream().map(PingMainOrder::getOrderNumber).collect(Collectors.toList());
        }
        List<CustomerPack> insertCustomerPackList = new ArrayList<>();
        List<CustomerPack> updateCustomerPackList = new ArrayList<>();
        List<PingMainOrder> insertPingMainOrderList = new ArrayList<>();
        List<PingMainOrder> updatePingMainOrderList = new ArrayList<>();
        // 根据客户id创建 PingMainOrder 拼单子订单对象
        for (int customerId : customerIdList) {
            Customer customer = new Customer();
            for (Customer c : customerList) {
                if(c.getId() == customerId){
                    customer = c;
                    break;
                }
            }
            CustomerPack customerPack = new CustomerPack();

            // 取得客户的对应包裹
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

            // 重量和体积的价格转换
            ResponseEntity<ResultModel> responseEntity =
                    pingUtil.priceConversion(routeVo, routeVo.getRoutePrices(), actualWeight, actualVol, pingMainOrder);
            pingMainOrder.setPreQoutedPrice(pingMainOrder.getAuctualPrice());
            pingMainOrder.setAuctualPrice(null);
            if (responseEntity != null) {
                return responseEntity;
            }
            // 客户包裹数据填装
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
            // 添加未打包客户包裹数据
            customerPackMapper.insertList(insertCustomerPackList);
            customerPackOrderNumberList.addAll(insertCustomerPackList.stream().map(CustomerPack::getOrderNumber).collect(Collectors.toList()));
        }
        if (!updatePingMainOrderList.isEmpty()) {
            pingMainOrderMapper.updateListByOrderNumber(updatePingMainOrderList);
        }
        if (!updateCustomerPackList.isEmpty()) {
            // 更新未打包客户包裹数据
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
            // 插入货物包裹关联表
            goods2packMapper.insertList(insertGoods2packList);
        }
        // 添加包裹收货地址表的数据
        CustomerPackReceiverAddress customerPackReceiverAddress = customerPackReceiverAddressDao.queryById(pingMainRespVo.getAddressId());
        List<CustomerPackReceiverAddress> customerPackReceiverAddressList = new ArrayList<>();
        for (CustomerPack customerPack : customerPackVoList) {
            CustomerPackReceiverAddress insertAddress = cpraSetting(customerPack.getId(), customerPackReceiverAddress);
            customerPackReceiverAddressList.add(insertAddress);
        }
        if (!customerPackReceiverAddressList.isEmpty()) {
            // 先删除之前的地址数据，重新插入地址数据
            customerPackReceiverAddressDao.deleteByCustomerPackIdList(customerPackReceiverAddressList.stream().
                    map(CustomerPackReceiverAddress::getCustomerPackId).collect(Collectors.toList()));
            customerPackReceiverAddressDao.insertList(customerPackReceiverAddressList);
        }

        return null;
    }

    /**
     * 订单实体查询优化方法（驿站图片查询、价格区间匹配）
     *
     * @param pingMainRespVo
     * @return
     */
    private PingMainRespVo pingMainEnhance(PingMainRespVo pingMainRespVo) {
        // 驿站图片查询
        Images image = new Images();
        image.setContentId(pingMainRespVo.getOrderNumber());
        image.setPicType(ImageType.PinPicture.toString());
        List<Images> images = imagesMapper.getImagesByContentId(image).isEmpty() ? new ArrayList<>() : imagesMapper.getImagesByContentId(image);
        pingMainRespVo.setImages(images);
        // 驿站图片查询 end

        if(pingMainRespVo.getTransportationRoute() != null){
            // 价格区间匹配
            pingMainRespVo.setTransportationRoute(pingUtil.priceMatching(pingMainRespVo.getTransportationRoute(), pingMainRespVo.getTargetWeight()));
        }

        return pingMainRespVo;
    }


}
