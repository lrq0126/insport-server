package com.dwgj.mlxydedicatedline.service.ping;

import com.dwgj.mlxydedicatedline.commons.DateUtil;
import com.dwgj.mlxydedicatedline.commons.SequenceCode;
import com.dwgj.mlxydedicatedline.config.UserThreadContext;
import com.dwgj.mlxydedicatedline.entity.*;
import com.dwgj.mlxydedicatedline.entity.image.ImageType;
import com.dwgj.mlxydedicatedline.entity.image.Images;
import com.dwgj.mlxydedicatedline.entity.ping.PingMain;
import com.dwgj.mlxydedicatedline.entity.ping.PingMainOrder;
import com.dwgj.mlxydedicatedline.entity.ping.PingMember;
import com.dwgj.mlxydedicatedline.entity.ping.PingMemberPassword;
import com.dwgj.mlxydedicatedline.enums.pin.PingTypeEnum;
import com.dwgj.mlxydedicatedline.mapper.*;
import com.dwgj.mlxydedicatedline.mapper.customerPackReceiverAddress.CustomerPackReceiverAddressDao;
import com.dwgj.mlxydedicatedline.mapper.goods2pack.Goods2packMapper;
import com.dwgj.mlxydedicatedline.mapper.image.ImagesMapper;
import com.dwgj.mlxydedicatedline.mapper.ping.PingMainMapper;
import com.dwgj.mlxydedicatedline.mapper.ping.PingMainOrderMapper;
import com.dwgj.mlxydedicatedline.mapper.ping.PingMemberMapper;
import com.dwgj.mlxydedicatedline.mapper.ping.PingMemberPasswordMapper;
import com.dwgj.mlxydedicatedline.resultType.PageData;
import com.dwgj.mlxydedicatedline.resultType.PageHelp;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.utils.PingUtil;
import com.dwgj.mlxydedicatedline.service.wechat.SendMessageServer;
import com.dwgj.mlxydedicatedline.utils.CalculationUtils;
import com.dwgj.mlxydedicatedline.utils.FileUtil;
import com.dwgj.mlxydedicatedline.utils.GoodsUtil;
import com.dwgj.mlxydedicatedline.vo.GoodsVo;
import com.dwgj.mlxydedicatedline.vo.pin.PingMainRespVo;
import com.dwgj.mlxydedicatedline.vo.route.RouteQuotationVo;
import com.dwgj.mlxydedicatedline.vo.route.RouteVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.dwgj.mlxydedicatedline.enums.ResultStatus.*;
import static com.dwgj.mlxydedicatedline.enums.customerPack.PackTypeEnums.WAIT_PACK;
import static com.dwgj.mlxydedicatedline.enums.pin.PingPackType.PING_NOT_PACK;
import static com.dwgj.mlxydedicatedline.enums.pin.PingTypeEnum.TEAM_COMPLETE;
import static com.dwgj.mlxydedicatedline.enums.pin.PingTypeEnum.WAITING_FOR_TEAM_COMPLETE;

@Service
@Transactional
public class PingMainServiceImpl implements PingMainService {
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private PingMainMapper pingMainMapper;

    @Autowired
    private ImagesMapper imagesMapper;

    @Autowired
    private CustomerAddressMapper customerAddressMapper; // ???????????????
    @Autowired
    private CustomerPackReceiverAddressDao customerPackReceiverAddressDao; // ???????????????
    @Autowired
    private CustomerPackMapper customerPackMapper;
    @Autowired
    private Goods2packMapper goods2packMapper; // ?????????????????????

    @Autowired
    private TransportationRouteMapper routeMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private PingMemberMapper memberMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private PingMemberPasswordMapper pingMemberPasswordMapper;
    @Autowired
    private PingMainOrderMapper orderMapper;

    @Autowired
    private SendMessageServer sendMessageServer;

    @Autowired
    private GoodsUtil goodsUtil;
    @Autowired
    private PingUtil pingUtil;

    @Resource
    private CalculationUtils calculationUtils;

    @Value("${server.domain}")
    private String domain;

    @Value("${upload.root}")
    private String Upload_Root;
    /**
     * ???????????????????????????
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> ownList(Map<String, Object> paramMap) {
        PageData data = PageHelp.editPage(paramMap.get("pageNumber").toString(), paramMap.get("pageSize").toString());
        paramMap.put("pageNumber", data.getPageNumber());
        paramMap.put("pageSize", data.getPageSize());
        String leaderId = UserThreadContext.getUser() == null ? "0" : UserThreadContext.getUser().getId().toString();
        paramMap.put("leaderId", leaderId);
        int total = pingMainMapper.countOwnPinList(paramMap);
        List<PingMain> list = new ArrayList<>();
        data.setTotal(0);
        if (total > 0) {
            list = pingMainMapper.getOwnPinList(paramMap);
            percentagePin(list);
            data.setTotal(total);
        }
        paramMap.put("list", list);
        paramMap.put("page", data);
        return new ResponseEntity<>(ResultModel.ok(paramMap), HttpStatus.OK);
    }

    /**
     * ???????????????????????????
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> takePartInList(Map<String, Object> paramMap) {
        PageData data = PageHelp.editPage(paramMap.get("pageNumber").toString(), paramMap.get("pageSize").toString());
        paramMap.put("pageNumber", data.getPageNumber());
        paramMap.put("pageSize", data.getPageSize());
        String customerId = UserThreadContext.getUser() == null ? "0" : UserThreadContext.getUser().getId().toString();
        paramMap.put("customerId", customerId);
        int total = pingMainMapper.countTakePartInList(paramMap);
        List<PingMain> list = new ArrayList<>();
        data.setTotal(0);
        if (total > 0) {
            list = pingMainMapper.getTakePartInList(paramMap);
            percentagePin(list);
            data.setTotal(total);
        }
        paramMap.put("list", list);
        paramMap.put("page", data);
        return new ResponseEntity<>(ResultModel.ok(paramMap), HttpStatus.OK);
    }

    /**
     * ?????????????????????
     *
     * @param paramMap
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> pinList(Map<String, Object> paramMap) {
        PageData data = PageHelp.editPage(paramMap.get("pageNumber").toString(), paramMap.get("pageSize").toString());
        paramMap.put("pageNumber", data.getPageNumber());
        paramMap.put("pageSize", data.getPageSize());
        int total = pingMainMapper.countPinList(paramMap);
        List<PingMain> list = new ArrayList<>();
        data.setTotal(0);
        if (total > 0) {
            list = pingMainMapper.getPinList(paramMap);
            percentagePin(list);
            data.setTotal(total);
        }
        paramMap.put("list", list);
        paramMap.put("page", data);
        return new ResponseEntity<>(ResultModel.ok(paramMap), HttpStatus.OK);
    }

    /**
     * ??????????????????????????????????????????3????????????????????????????????????
     *
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> homeList() {
        List<PingMain> list = pingMainMapper.getHomePinList();
        percentagePin(list);
        int total = list.size();
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("total", total);
        resultMap.put("data", list);
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
    }

    /**
     * ????????????
     *
     * @param multipartFiles ?????????????????????????????????
     * @param pin            ???????????????
     * @param isUpdate       ????????????
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> savePingtuan(MultipartFile[] multipartFiles, PingMain pin, boolean isUpdate) {

        String userId = UserThreadContext.getUser() == null ? "0" : UserThreadContext.getUser().getId().toString();

        List<Images> imagesList = new ArrayList<>(3);
        try {
            ResponseEntity<ResultModel> responseEntity = imagesUpload(multipartFiles, imagesList);
            if (responseEntity != null) {
                return responseEntity;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR, "??????????????????"), HttpStatus.OK);
        }

        if (isUpdate) {
            // ????????????????????????????????????????????????????????????????????????

            // ???????????????????????????????????????????????????????????????????????????????????????????????????
            Images images = new Images();
            images.setContentId(pin.getOrderNumber());
            images.setPicType(ImageType.PinPicture.toString());
            List<Images> imagesByContentId = imagesMapper.getImagesByContentId(images);
            imagesMapper.deleteByIdList(imagesByContentId.stream().map(Images::getId).collect(Collectors.toList()));

            customerPackReceiverAddressDao.deleteById(pin.getAddressId());
        } else {
            String pOrderNumber = SequenceCode.pMainOrderNumber();
            pin.setOrderNumber(pOrderNumber);
            System.out.println("-------------??????");
        }

        // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????
        CustomerAddress customerAddress = customerAddressMapper.selectByPrimaryKey(pin.getAddressId());

        CustomerPackReceiverAddress customerPackReceiverAddress = new CustomerPackReceiverAddress();
        customerPackReceiverAddress.setAddressee(customerAddress.getAddressee());
        customerPackReceiverAddress.setReceiverAddress(customerAddress.getReceiverAddress());
        customerPackReceiverAddress.setPhoneNumber(customerAddress.getPhoneNumber());
        customerPackReceiverAddress.setCode(customerAddress.getCode());
        customerPackReceiverAddress.setStatus(1);
        customerPackReceiverAddressDao.insert(customerPackReceiverAddress);

        pin.setAddressId(customerPackReceiverAddress.getId());

        if (isUpdate) {
            pin.setLastUpdateTime(DateUtil.timestamp2String(new Date()));
            pin.setLastUpdateId(Integer.valueOf(userId));
            pingMainMapper.updateByPrimaryKeySelective(pin);
            // ????????????????????????????????????????????????????????????
            if (pin.getIsPublic() == 0) {
                PingMemberPassword pingMemberPassword = pingMemberPasswordMapper.selectByPingId(pin.getId());
                if (pingMemberPassword == null) {
                    pingMemberPassword = new PingMemberPassword();
                    pingMemberPassword.setCreateTime(new Date());
                    pingMemberPassword.setPassword(pin.getPassword());
                    pingMemberPassword.setpId(pin.getOrderNumber());
                    pingMemberPassword.setCustomerId(Integer.valueOf(userId));
                    pingMemberPasswordMapper.insert(pingMemberPassword);
                } else {
                    pingMemberPasswordMapper.updateByPrimaryKeySelective(pingMemberPassword);
                }
            }

        } else {
            pin.setStatus(1);
            pin.setPType(PingTypeEnum.WAITING_FOR_AUDIT.getValue());
            pin.setIsCutOff(0);
            pin.setIsPacked(0);
            pin.setIsActived(0);
            pin.setIsPrint(0);
            pin.setIsPassed(0);
            pin.setIsPaid(0);
            pin.setCreateTime(DateUtil.timestamp2String(new Date()));
            pin.setCreateId(Integer.valueOf(userId));
            pin.setLeaderId(Integer.valueOf(userId));
            pingMainMapper.insert(pin);

            // ????????????????????????????????????????????????????????????
            if (pin.getIsPublic() == 1) {
                PingMemberPassword p = new PingMemberPassword();
                p.setCreateTime(new Date());
                p.setPassword(pin.getPassword());
                p.setpId(pin.getOrderNumber());
                p.setCustomerId(Integer.valueOf(userId));
                pingMemberPasswordMapper.insert(p);
            }
        }

        if (!imagesList.isEmpty()) {
            imagesList.forEach(images -> {
                images.setContentId(pin.getOrderNumber());
                images.setPicType(ImageType.PinPicture.toString());
            });
            imagesMapper.insertImagesList(imagesList);
            pin.setImagesList(imagesList);
        }

        return new ResponseEntity<>(ResultModel.ok(pin), HttpStatus.OK);
    }

    /**
     * ??????????????????
     *
     * @param pin
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> reactivatePin(PingMain pin) {
        PingMain oldPin = pingMainMapper.selectById(pin.getId());
        String userId = UserThreadContext.getUser() == null ? "0" : UserThreadContext.getUser().getId().toString();
        if(userId.isEmpty() || !oldPin.getLeaderId().equals(Integer.valueOf(userId))){
            return new ResponseEntity<>(ResultModel.error(PIN_LEADER_NOT_THIS_USERID), HttpStatus.OK);
        }
        pin.setIsActived(1);
        pin.setActivedId(oldPin.getLeaderId().toString());
        pin.setPType(TEAM_COMPLETE.getValue());
        oldPin.setCutOffTime(pin.getCutOffTime());
        pingMainMapper.updateByPrimaryKeySelective(pin);
        return new ResponseEntity<>(ResultModel.ok(oldPin), HttpStatus.OK);
    }

    /**
     * ????????????????????????????????????
     *
     * @param orderNumber
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> getPingtuan(String orderNumber) {
        PingMain pin1 = pingMainMapper.getByOrderNumber(orderNumber);
        PingMain pin = pingMainMapper.selectByOrderNumber(orderNumber,pin1.getId());
        if (pin == null) {
            return new ResponseEntity<>(ResultModel.error(ORDERNUMBER_NOT_EXSIT), HttpStatus.OK);
        }
//        pin = pingMainMapper.selectById(pin.getId());

        Images images = new Images();
        images.setContentId(pin.getOrderNumber());
        images.setPicType(ImageType.PinPicture.toString());
        List<Images> imagesList = imagesMapper.getImagesByContentId(images);
        CustomerPackReceiverAddress address = customerPackReceiverAddressDao.queryById(pin.getAddressId());

        // ??????????????????
        RouteVo transportationRoute = routeMapper.findRouteVoByRouteId(pin.getRouteId());

        // TODO ?????????????????????????????????????????????
        if (transportationRoute == null) {
            return new ResponseEntity<>(ResultModel.error(ROUTE_NULL_ERROR), HttpStatus.OK);
        }

        List<RoutePrice> routePrices = transportationRoute.getRoutePrices();
        if (routePrices == null || routePrices.isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(ROUTE_PRICE_NULL_ERROR), HttpStatus.OK);
        }
        RoutePrice routePrice = pingUtil.priceMatching(transportationRoute, pin.getTargetWeight());

        if (routePrice == null) {
            return new ResponseEntity<>(ResultModel.error(ROUTE_PRICE_BEYOND_THR_LIMIT_ERROR), HttpStatus.OK);
        }

        pin.setRouteName(transportationRoute.getRouteName());
        //
        RouteQuotationVo route = new RouteQuotationVo();
        route.setRoutePriceId(routePrice.getId());
        route.setRouteId(transportationRoute.getId());
        route.setSpecialRemarks(routePrice.getRemarks());
        route.setRouteName(transportationRoute.getRouteName());
        route.setSettleWeight(pin.getTargetWeight());
        route.setActualWeight(routePrice.getStartWeight());
        route.setCountry(pin.getCountryName());
        route.setDestination(transportationRoute.getDestination());
        route.setUnitPrice(routePrice.getPrice());
        route.setPrice(routePrice.getPrice().multiply(BigDecimal.valueOf(pin.getTargetWeight())));
        if (transportationRoute.getTransportType() == 1) {
            route.setRouteType("??????");
        } else {
            route.setRouteType("???/??????");
        }
        route.setNoteType("1");
        route.setTransportationTime(transportationRoute.getTransportationTime());
        route.setRemarks(transportationRoute.getRemarks());
        route.setWeight(routePrice.getStartWeight() + "-" + routePrice.getEndWeight());

        pin.setImagesList(imagesList);
        pin.setAddress(address);
        pin.setRoute(route);

        PingMember member = new PingMember();
        member.setPId(pin.getId());
        String userId = UserThreadContext.getUser() == null ? "20" : UserThreadContext.getUser().getId().toString();
        member.setCustomerId(Integer.valueOf(userId));
        // ???????????????
        List<PingMember> memberGoodsList = memberMapper.getMemberGoodsByPinId(member);

        Map<String, Object> result = new HashMap<>(3);


        if (!pin.getLeaderId().toString().equals(userId)) {
            // ???????????????????????????????????????
            pin.setPassword("****");
        } else {
            // ????????????????????????????????????
            List<PingMember> memberList = memberMapper.getAllMembersGoodsSumExceptLeaderByPinId(pin.getId().toString(), pin.getLeaderId().toString());
            result.put("memberList", memberList);
        }

        // ???????????????
        DecimalFormat df = new DecimalFormat("0.00");
        pin.setProgress(0);
        if (pin.getTargetWeight() > 0) {
            double per = new BigDecimal(df.format((pin.getPackageWeight() / (pin.getTargetWeight() + (pin.getTargetWeight() * 0.1))) * 100)).doubleValue();
            pin.setProgress(per > 100.00 ? 100.00 : per);
        }

        result.put("pin", pin);
        result.put("selfGoodsList", memberGoodsList);

        return new ResponseEntity<>(ResultModel.ok(result), HttpStatus.OK);
    }

    /**
     * ??????????????????????????????
     *
     * @param orderNumber
     * @param goodsIds
     * @param takeIn      ?????????????????????????????????
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<ResultModel> takePartIn(String orderNumber, String goodsIds, boolean takeIn) {
        PingMain pin1 = pingMainMapper.getByOrderNumber(orderNumber);
        PingMain pin = pingMainMapper.selectByOrderNumber(orderNumber,pin1.getId());

        if (pin == null) {
            return new ResponseEntity<>(ResultModel.error(ORDERNUMBER_NOT_EXSIT), HttpStatus.OK);
        }

        if (pin.getPType() == 0) {
            return new ResponseEntity<>(ResultModel.error(PIN_IS_CHECKING), HttpStatus.OK);
        }

        if (StringUtils.isBlank(goodsIds)) {
            return new ResponseEntity<>(ResultModel.error(GOODS_IDS_NULL), HttpStatus.OK);
        }

        List<String> goodsL = Arrays.asList(goodsIds.split(","));
        List<String> goods = new ArrayList<>(goodsL);
        List<PingMember> memberList = new ArrayList<>(goods.size());
        List<String> goodsList = new ArrayList<>(goods.size());

        // ????????????????????????
        PingMember pingMember = new PingMember();
        pingMember.setPId(pin.getId());
        String userId = UserThreadContext.getUser() == null ? "0" : UserThreadContext.getUser().getId().toString();
        pingMember.setCustomerId(Integer.valueOf(userId));

        List<PingMember> oldList = memberMapper.getMemberGoodsByPinId(pingMember);

        if (takeIn == false) {
            // ????????????
            goodsMapper.updateInWarehouseByGoodsIds(goods);

            memberMapper.deleteByGoodsIds(goods);

            // ??????????????????????????????????????????
            List<PingMember> pingMembers = memberMapper.getMembersByPinId(String.valueOf(pin.getId()));

            // ??????????????????????????????????????????????????????????????????????????????????????????????????????
            this.updatePType(String.valueOf(pin.getId()));


            pingMainMapper.updateByPrimaryKeySelective(pin);

            // ???????????????????????????????????????????????????????????????????????????
            if (true) {
                pin = pingMainMapper.selectByOrderNumber(orderNumber,pin1.getId());
                sendMessageServer.sendMessageForPinLeader(pin, userId, goods.size(), "delete");
            }
            return new ResponseEntity<>(ResultModel.ok("?????????" + goods.size() + "???????????????"), HttpStatus.OK);
        }

        // ????????????????????????????????????
        List<PingMember> pingMemberList = memberMapper.getMembersByPinId(String.valueOf(pin.getId()));
        if (!pingMemberList.isEmpty()) {
            List<Integer> customerIdList = pingMemberList.stream().map(PingMember::getCustomerId).distinct().collect(Collectors.toList());
            if(pin.getTargetMember() == customerIdList.size()){
                return new ResponseEntity<>(ResultModel.error(IS_MAX_PEOPLE), HttpStatus.OK);
            }
        }




        for (PingMember p : oldList) {
            if (goods.contains(p.getGoodsId() + "") && takeIn) {
                goods.remove(p.getGoodsId() + "");// ?????????string?????????????????? + ""
            }
            if (goods.isEmpty() || goods.size() == 0) {
                break;
            }
        }

        int i = 0;
        for (String goodsId : goods) {
            PingMember member = new PingMember();
            member.setAddTime(DateUtil.timestamp2String(new Date()));
            member.setSortNo(i);
            member.setStatus(1);
            member.setIsPicked(0);
            member.setPId(pin.getId());
            member.setGoodsId(Integer.valueOf(goodsId));
            member.setCustomerId(Integer.valueOf(userId));
            memberList.add(member);
            goodsList.add(goodsId);
            i++;
        }


        // ?????????????????????????????????
        if (goodsList.size() > 0) {
            goodsMapper.updatePinByGoodsIds(goodsList);
            // ?????????????????????
            memberMapper.insertPinMembers(memberList);

            List<PingMember> pingMembers = memberMapper.getMembersByPinId(String.valueOf(pin.getId()));
            pin.setCurrentPeopleNum(pingMembers.stream().map(PingMember::getCustomerId).distinct().collect(Collectors.toList()).size());
            pingMainMapper.updateByPrimaryKeySelective(pin);
        }

        // ???????????????????????????????????????????????????????????????????????????
        if (true) {
            pin = pingMainMapper.selectByOrderNumber(orderNumber,pin1.getId());
            sendMessageServer.sendMessageForPinLeader(pin, userId, i, "takePartIn");
        }

        return new ResponseEntity<>(ResultModel.ok("????????????" + i + "?????????"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> quitPin(String orderNumber) {
        PingMain pin1 = pingMainMapper.getByOrderNumber(orderNumber);
        PingMain pin = pingMainMapper.selectByOrderNumber(orderNumber,pin1.getId());
        PingMember member = new PingMember();
        member.setPId(pin.getId());
        String userId = UserThreadContext.getUser() == null ? "25" : UserThreadContext.getUser().getId().toString();
        member.setCustomerId(Integer.valueOf(userId));

        List<String> goodsIdList = memberMapper.getMemberGoodsListByOrderNumber(member);
        if (goodsIdList.size() > 0) {
            // ?????????????????????????????????????????????????????????
            goodsMapper.updateInWarehouseByGoodsIds(goodsIdList);
            memberMapper.deleteByGoodsIds(goodsIdList);
        }

        this.updatePType(String.valueOf(pin.getId()));

        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    /**
     * ???????????????????????????
     *
     * @param orderNumber
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> checkIsIn(String orderNumber, int pId) throws Exception {
//        PingMain pin = pingMainMapper.selectByOrderNumber(orderNumber);
        // ??????????????????
        PingMain pin = pingMainMapper.selectById(pId);
        String userId = UserThreadContext.getUser() == null ? "0" : UserThreadContext.getUser().getId().toString();
        PingMember member = new PingMember();
        member.setCustomerId(Integer.valueOf(userId));
        member.setPId(pId);

        // ???????????????
        if (userId.equals(pin.getLeaderId() + "")) {
            return new ResponseEntity<>(ResultModel.error(IS_IN), HttpStatus.OK);
        }

        // ????????????????????????????????????????????????
        List<String> goodsList = memberMapper.getMemberGoodsListByOrderNumber(member);

        // ???????????????????????????????????????
        if (goodsList != null && goodsList.size() > 0) {
            return new ResponseEntity<>(ResultModel.error(IS_IN), HttpStatus.OK);
        }

        // ?????????????????????????????????????????????????????????
        if (pin.getTargetMember() < pin.getCurrentPeopleNum()) {
            return new ResponseEntity<>(ResultModel.error(IS_MAX_PEOPLE), HttpStatus.OK);
        }

        // ???????????????????????????????????????????????????
        if (DateUtil.string2Timestamp(pin.getCutOffTime()).compareTo(new Date()) <= 0 && pin.getPType() == 1) {
            return new ResponseEntity<>(ResultModel.error(IS_TIME_OFF), HttpStatus.OK);
        }

        if (pin.getIsPublic() == 0) {
            // ?????????????????????????????????
            return new ResponseEntity<>(ResultModel.error(IS_IN), HttpStatus.OK);
        }

        PingMemberPassword pingMemberPassword = new PingMemberPassword();
        pingMemberPassword.setCustomerId(Integer.valueOf(userId));
        pingMemberPassword.setpId(orderNumber);
        int i = pingMemberPasswordMapper.checkIsIn(pingMemberPassword);
        if (i == 0) {
            return new ResponseEntity<>(ResultModel.error(IS_NOT_IN), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(IS_IN), HttpStatus.OK);
    }

    /**
     * ????????????????????????
     *
     * @param orderNumber
     * @param password
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> checkPassword(String orderNumber, String password) {
        if (StringUtils.isBlank(password)) {
            return new ResponseEntity<>(ResultModel.error(INPUT_NULL), HttpStatus.OK);
        }
        PingMain pin1 = pingMainMapper.getByOrderNumber(orderNumber);
        PingMain pin = pingMainMapper.selectByOrderNumber(orderNumber,pin1.getId());
        if (pin == null) {
            return new ResponseEntity<>(ResultModel.error(ORDERNUMBER_NOT_EXSIT), HttpStatus.OK);
        }
        if (pin.getPassword() == null || pin.getPassword().equals("")) {
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        PingMemberPassword p = new PingMemberPassword();
        p.setCreateTime(new Date());
        p.setPassword(pin.getPassword());
        p.setpId(pin.getOrderNumber());
        String userId = UserThreadContext.getUser() == null ? "0" : UserThreadContext.getUser().getId().toString();
        p.setCustomerId(Integer.valueOf(userId));
        // ???????????????????????????
        int i = pingMemberPasswordMapper.checkIsIn(p);
        if (i > 0) {
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        // ????????????????????????
        if (pin.getPassword().equals(password)) {
            pingMemberPasswordMapper.insert(p);
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(PASSWORD_ERROR), HttpStatus.OK);
    }

    /**
     * ??????????????????
     *
     * @param pId
     * @param memberId
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> kickMember(String pId, String memberId) {
        PingMember member = new PingMember();
        member.setPId(Integer.valueOf(pId));
        member.setCustomerId(Integer.valueOf(memberId));

        List<String> goodsIdList = memberMapper.getMemberGoodsListByOrderNumber(member);
        if (goodsIdList.size() > 0) {
            // ?????????????????????????????????????????????????????????
            goodsMapper.updateInWarehouseByGoodsIds(goodsIdList);
            memberMapper.deleteByGoodsIds(goodsIdList);
        }

        // TODO ??????????????????????????????????????????????????????????????????????????????????????????????????????
        this.updatePType(pId);

        return new ResponseEntity<>(ResultModel.ok("????????????????????????"), HttpStatus.OK);
    }


    private void updatePType(String pId) {
        PingMain pingMain = pingMainMapper.selectById(Integer.valueOf(pId));
        List<PingMember> pingMemberList = memberMapper.getMembersByPinId(pId);
        if(pingMemberList == null || pingMemberList.isEmpty()){
            if (pingMain.getPType() != WAITING_FOR_TEAM_COMPLETE.getValue()) {
                pingMain.setPType(WAITING_FOR_TEAM_COMPLETE.getValue());
                pingMainMapper.updateByPrimaryKeySelective(pingMain);
            }
        }else {
            List<Integer> remainingGoodsIdList =
                    pingMemberList.stream().map(PingMember::getGoodsId).collect(Collectors.toList());
            List<GoodsVo> goodsVoList = goodsMapper.selectGoodsByIds(remainingGoodsIdList);

            double actualWeight = goodsVoList.stream().mapToDouble(GoodsVo::getKg).sum();
            if (actualWeight < pingMain.getTargetWeight() && pingMain.getPType() != WAITING_FOR_TEAM_COMPLETE.getValue()) {
                pingMain.setPType(WAITING_FOR_TEAM_COMPLETE.getValue());
                pingMainMapper.updateByPrimaryKeySelective(pingMain);
            }

        }
    }

    /**
     * ??????
     *
     * @param pId
     * @param orderNumber
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> deletePin(String pId, String orderNumber) {
        PingMain pin = pingMainMapper.selectById(Integer.parseInt(pId));
        if (pin == null) {
            return new ResponseEntity<>(ResultModel.error(ORDERNUMBER_NOT_EXSIT), HttpStatus.OK);
        }
        PingMember member = new PingMember();
        member.setPId(pin.getId());
        member.setCustomerId(pin.getLeaderId());
        int i = memberMapper.countGoodsNumByPinIdExceptLeader(member);
        if (i > 0) {
            return new ResponseEntity<>(ResultModel.error(ORDER_HAVE_MEMBER), HttpStatus.OK);
        }
        pin.setStatus(0);
        pin.setLastUpdateTime(DateUtil.timestamp2String(new Date()));
        pingMainMapper.updateByPrimaryKey(pin);
        return new ResponseEntity<>(ResultModel.ok("????????????"), HttpStatus.OK);
    }

    /**
     * ????????????????????????????????????
     *
     * @param pId
     * @param orderNumber
     * @param memberId
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> messgeMember(String pId, String orderNumber, String memberId) {
        PingMain pin = pingMainMapper.selectById(Integer.parseInt(pId));
        if (pin == null) {
            return new ResponseEntity<>(ResultModel.error(ORDERNUMBER_NOT_EXSIT), HttpStatus.OK);
        }
        sendMessageServer.sendMessageForPinMember(pin, memberId);
        return new ResponseEntity<>(ResultModel.ok("??????????????????"), HttpStatus.OK);
    }

    /**
     * ???????????????????????????
     *
     * @param pId
     * @param orderNumber
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> checkConfirm(String pId, String orderNumber) {
        PingMain pin = pingMainMapper.selectById(Integer.parseInt(pId));
        if (pin == null) {
            return new ResponseEntity<>(ResultModel.error(ORDERNUMBER_NOT_EXSIT), HttpStatus.OK);
        }
        double a = pin.getPackageWeight() - (pin.getTargetWeight() + (pin.getTargetWeight() * 0.1));
        if ((pin.getTargetWeight() + (pin.getTargetWeight() * 0.1)) > pin.getPackageWeight()) {
            return new ResponseEntity<>(ResultModel.error(WEIGHT_NOT_ENOUGH), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(WEIGHT_ENOUGH), HttpStatus.OK);
    }

    /**
     * ????????????
     *
     * @param pId
     * @param orderNumber
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<ResultModel> confirm(String pId, String orderNumber) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("?????????????????????");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        PingMainRespVo pingMainRespVo = pingMainMapper.selectVoById(Integer.parseInt(pId));
        if (pingMainRespVo == null) {
            return new ResponseEntity<>(ResultModel.error(ORDERNUMBER_NOT_EXSIT), HttpStatus.OK);
        }
        if (pingMainRespVo.getPType() == TEAM_COMPLETE.getValue()) {
            return new ResponseEntity<>(ResultModel.error(TEAM_COMPLETED_ERROR), HttpStatus.OK);
        }


        List<PingMainOrder> pingMainOrderList = pingMainRespVo.getPingMainOrderList();
        // ??????????????????????????????????????????????????????????????????????????????????????????????????????
        // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        List<String> customerPackOrderNumberList = new ArrayList<>();
        if (pingMainOrderList != null && !pingMainOrderList.isEmpty()) {
            customerPackOrderNumberList = pingMainOrderList.stream().map(PingMainOrder::getOrderNumber).collect(Collectors.toList());
        }
        // ????????????????????????????????????
        List<PingMember> list = memberMapper.getMembersByPinId(pingMainRespVo.getId().toString());

        // ???????????????????????????
        List<GoodsVo> goodsVoAllList = goodsMapper.selectGoodsByIds(list.stream().map(PingMember::getGoodsId).collect(Collectors.toList()));

        // ??????????????????
        RouteVo routeVo = routeMapper.findRouteVoByRouteId(pingMainRespVo.getRouteId());
        if (routeVo.getRoutePrices().isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(ROUTE_PRICE_NULL_ERROR), HttpStatus.OK);
        }

        RoutePrice routePrice = pingUtil.priceMatching(routeVo, goodsVoAllList.stream().mapToDouble(GoodsVo::getKg).sum());

        // ????????????id
        List<Integer> customerIdList = list.stream().map(PingMember::getCustomerId).distinct().collect(Collectors.toList());

        pingMainRespVo.setCurrentPeopleNum(customerIdList.size()); // ????????????????????????
        // ???????????????
        pingMainRespVo.setPType(TEAM_COMPLETE.getValue());
        pingMainMapper.updateByPrimaryKeySelective(pingMainRespVo);

        List<Customer> customerList = customerMapper.findByIdList(customerIdList);

        String userId = UserThreadContext.getUser() == null ? "0" : UserThreadContext.getUser().getId().toString();
        List<PingMainOrder> insertOrderList = new ArrayList<>();
        List<CustomerPack> insertCustomerPackList = new ArrayList<>();
        List<PingMainOrder> updateOrderList = new ArrayList<>();
        List<CustomerPack> updateCustomerPackList = new ArrayList<>();
        for (int customerId : customerIdList) {

            Customer customer = new Customer();
            for (Customer c : customerList) {
                if(c.getId() == customerId){
                    customer = c;
                    break;
                }
            }

            // ???????????????????????????
            List<GoodsVo> customerGoods = new ArrayList<>();
            for (GoodsVo goodsVo : goodsVoAllList) {
                if (Integer.valueOf(goodsVo.getCustomerId()) == customerId) {
                    customerGoods.add(goodsVo);
                }
            }

            PingMainOrder pingMainOrder = new PingMainOrder();
            pingMainOrder.setPId(pingMainRespVo.getId());
            pingMainOrder.setCustomerId(customerId);
            pingMainOrder.setRouteId(pingMainRespVo.getRouteId());
            pingMainOrder.setGoodsNumber(customerGoods.size());
            pingMainOrder.setGroupTime(new Date());
            pingMainOrder.setPackType(PING_NOT_PACK.getValue());
            pingMainOrder.setStatus(1);
            pingMainOrder.setCreateId(Integer.valueOf(userId));
            pingMainOrder.setCreateTime(new Date());
            pingMainOrder.setIsPicked(0);
            pingMainOrder.setIsPaid(0);
            pingMainOrder.setOrderNumber(pingMainRespVo.getOrderNumber() + "C" + customer.getLoginName());


            Map<String, Object> m = new HashMap<>(2);
            m.put("pId", pingMainRespVo.getId());
            m.put("customerId", customerId);


            double k = 0, v = 0;
            boolean isCalculateVolWeight = false;
            for (GoodsVo goodsVo : customerGoods) {
                k += goodsVo.getKg() == null ? (goodsVo.getVol() * 1000000) / routeVo.getVolConversion() : goodsVo.getKg();
                v += goodsVo.getVol() == null ? (goodsVo.getKg() * routeVo.getVolConversion()) / 1000000 : goodsVo.getVol();
                if(!isCalculateVolWeight){
                    isCalculateVolWeight = routeVo.getSideLengthLimit() == null || goodsUtil.checkIsCalculateVolWeight(routeVo.getSideLengthLimit(),
                            goodsVo.getLength(), goodsVo.getWidth(), goodsVo.getHeight());
                }
            }
            // ?????????????????????????????? ???????????????  isCalculateVolWeight
            Map<String, Object> map = calculationUtils.countKGMoney(routeVo.getRoutePrices(), routeVo, k, v, true, isCalculateVolWeight);

            // ?????? map2 ??????????????????????????????????????????????????????????????????
            if (map == null) {
                continue;
            }
            // ??????????????????
            pingMainOrder.setPreQoutedPrice(routePrice.getPrice().multiply(BigDecimal.valueOf(k)));
            if (map.containsKey("finalPrice")) {
                pingMainOrder.setPreQoutedPrice(new BigDecimal(Double.valueOf(map.get("finalKG").toString())));
            }

            if (map.containsKey("finalKG")) {
                pingMainOrder.setPreQoutedPrice(new BigDecimal(Double.valueOf(map.get("finalKG").toString())));
            }

            String message = "";
            if (map.containsKey("KG_VOL")) {
                message = "??????????????????????????????????????????????????????????????????" + String.valueOf(map.get("KG_VOL")) + " Kg";
            }

            pingMainOrder.setRemarks(message);
            pingMainOrder.setAddressId(pingMainRespVo.getAddressId());

            CustomerPack customerPack = new CustomerPack();

            // ????????????????????????
            customerPackSetting(pingMainOrder, customerPack, customerGoods, routeVo.getRouteType());

            if (!customerPackOrderNumberList.contains(customerPack.getOrderNumber())) {
                insertOrderList.add(pingMainOrder);
                insertCustomerPackList.add(customerPack);
                customerPackOrderNumberList.add(customerPack.getOrderNumber());
            } else {
                updateOrderList.add(pingMainOrder);
                updateCustomerPackList.add(customerPack);
            }

        }

        if (!insertOrderList.isEmpty()) {
            insertOrderList.forEach(pingMainOrder -> {
                pingMainOrder.setPackType(PING_NOT_PACK.getValue());
                pingMainOrder.setIsPicked(0);
                pingMainOrder.setIsPrinted(0);
                pingMainOrder.setIsPaid(0);
                pingMainOrder.setStatus(1);
            });
            orderMapper.insertOrderList(insertOrderList);
        }
        if (!insertCustomerPackList.isEmpty()) {
            insertCustomerPackList.forEach(customerPack -> {
                customerPack.setPackType(WAIT_PACK.getValue());
                customerPack.setStatus(1);
            });
            customerPackMapper.insertList(insertCustomerPackList);
        }
        if (!updateOrderList.isEmpty()) {
            orderMapper.updateListByOrderNumber(updateOrderList);
        }
        if (!updateCustomerPackList.isEmpty()) {
            customerPackMapper.updateListByOrderNumber(updateCustomerPackList);
        }

        // ?????????????????????????????????
        List<CustomerPack> customerPackList = customerPackMapper.findByOrderNumbers(customerPackOrderNumberList);
        if (customerPackList == null || customerPackList.isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(PACK_ABNORMAL_ERROR), HttpStatus.OK);
        }
        // ?????????????????????????????????????????????
        goods2packMapper.deleteByPackIdList(customerPackList.stream().map(CustomerPack::getId).collect(Collectors.toList()));

        List<Goods2pack> insertGoods2packList = new ArrayList<>();
        for (CustomerPack customerPack : customerPackList) {
            for (PingMember pingMember : list) {
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

        for (CustomerPack customerPack : customerPackList) {
            CustomerPackReceiverAddress insertAddress = cpraSetting(customerPack.getId(), customerPackReceiverAddress);
            customerPackReceiverAddressList.add(insertAddress);
        }

        if (!customerPackReceiverAddressList.isEmpty()) {
            // ??????????????????????????????????????????
            customerPackReceiverAddressDao.deleteByCustomerPackIdList(customerPackList.stream().map(CustomerPack::getId).collect(Collectors.toList()));
            customerPackReceiverAddressDao.insertList(customerPackReceiverAddressList);
        }

        sendMessageServer.sendMessageForConfirmPin(pingMainRespVo);
        transactionManager.commit(status);
        return new ResponseEntity<>(ResultModel.ok("????????????"), HttpStatus.OK);
    }

    /**
     * ?????????????????????  ???????????????00.00
     *
     * @param list
     */
    public static void percentagePin(List<PingMain> list) {
        DecimalFormat df = new DecimalFormat("0.00");
        for (PingMain pin : list) {
            pin.setProgress(0);
            if (pin.getTargetWeight() > 0) {
                double per = new BigDecimal(df.format((pin.getPackageWeight() / (pin.getTargetWeight() + (pin.getTargetWeight() * 0.1))) * 100)).doubleValue();
                pin.setProgress(per > 100.00 ? 100.00 : per);
            }
        }
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


//    public static void main(String[] args) throws Exception {
//        // ???????????????????????????????????????????????????
//        if ( DateUtil.string2Timestamp("2021-01-05 13:38:09").compareTo(new Date()) <= 0 ){
//            System.out.println(123123);
//        }
//    }


//    int i = 0;
//        for ( MultipartFile f : multipartFiles ){
//        System.out.println("f.getName():"+f.getName());
//        String originalFilename = f.getOriginalFilename();
//        System.out.println("originalFilename:"+originalFilename);
//        String suffix = FileUtil.getSuffix2(originalFilename);
//        System.out.println("suffix:"+suffix);
//        String fileSize = f.getSize() + "";
//
//        if ( f.getSize() > FileUtil.IMAGE_MAX_SIZE ){
//            return new ResponseEntity<>(ResultModel.error(FILE_MAX), HttpStatus.OK);
//        }
//        System.out.println("fileSize:"+fileSize);
//        String imageName = SequenceCode.gainSerialNo("IMG_") + suffix;
//        // ??????????????????
//        File file = FileUtil.buildFileName(ImageType.PinPicture.toString(),imageName);
//        try {
//            f.transferTo(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
//        }
//        Images images = new Images();
//        images.setImageName(originalFilename);
//        images.setPicType(ImageType.PinPicture.toString());
//        String picurl = domain + "/" + file.getPath().substring(file.getPath().indexOf("upload"));
//        picurl = picurl.replaceAll("\\\\", "/");
//        images.setPicUrl(picurl);
//        images.setImageSize(fileSize);
//        images.setContentId(pId);
//        images.setSortNo(i);
//        images.setPath(file.getPath());
//        images.setCreateTime(new Date());
//        images.setStatus(1);
//        imagesList.add(images);
//        i++;
//    }

    // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
    private void customer2addressSetting(CustomerAddress customerAddress, Customer2address customer2address) {
        customer2address.setAddress(customerAddress.getReceiverAddress());
        customer2address.setAddressee(customerAddress.getAddressee());
        customer2address.setCustomerId(customerAddress.getCustomerId());
        customer2address.setCustomerName(UserThreadContext.getUser() == null ? "" : UserThreadContext.getUser().getCustomerName());
        customer2address.setTelephone(customerAddress.getPhoneNumber());
        customer2address.setStatus(1);
    }

    // ??????????????????
    private ResponseEntity<ResultModel> imagesUpload(MultipartFile[] multipartFiles, List<Images> imagesList) throws IOException {
        System.out.println("????????????");
        System.out.println("?????????" + multipartFiles);
        int i = 0;
        byte[] bytes = new byte[1024];
        for (MultipartFile f : multipartFiles) {
            System.out.println("f.getName():" + f.getName());
            String originalFilename = f.getOriginalFilename();
            System.out.println("originalFilename:" + originalFilename);
            String suffix = FileUtil.getSuffix2(originalFilename);
            System.out.println("suffix:" + suffix);
            String fileSize = f.getSize() + "";

            if (f.getSize() > FileUtil.IMAGE_MAX_SIZE) {
                return new ResponseEntity<>(ResultModel.error(FILE_MAX), HttpStatus.OK);
            }
            System.out.println("fileSize:" + fileSize);
            String imageName = SequenceCode.gainSerialNo("IMG_") + suffix;
            // ??????????????????
            File file = FileUtil.buildFileName(ImageType.PinPicture.toString(), imageName);
            try {
                f.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();

                return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
            }
            Images images = new Images();
            images.setImageName(originalFilename);
            images.setPicType(ImageType.PinPicture.toString());
            String picurl = domain + "/" + file.getPath().substring(file.getPath().indexOf("upload"));
            picurl = picurl.replaceAll("\\\\", "/");


            images.setPicUrl(picurl);
            images.setImageSize(fileSize);
            images.setSortNo(i);
            images.setPath(file.getPath());
            images.setCreateTime(new Date());
            images.setStatus(1);
            imagesList.add(images);
            i++;
        }
        System.out.println("??????????????????");
        return null;
    }


    /**
     * ??????CustomerPack?????????
     *
     * @param customerPack ??????????????????
     */
    private void customerPackSetting(PingMainOrder pingMainOrder, CustomerPack customerPack, List<GoodsVo> customerGoods, int routeType) {
        customerPack.setStatus(1);//?????? 1???????????? 0????????????
        customerPack.setVersion(1);//?????????????????????????????????????????????????????? 1
        customerPack.setOrderNumber(pingMainOrder.getOrderNumber());//?????????
        customerPack.setUserId(pingMainOrder.getCreateId());//?????????id
        customerPack.setCustomerId(pingMainOrder.getCustomerId());//??????id
        customerPack.setPackType(1);//packType???1???????????????  2???????????????  3??????????????????  4??????????????? 7???????????? ??? 9????????????
        customerPack.setUnpacking(1);//????????????   1???????????????  2???????????????
        customerPack.setGoodsSum(pingMainOrder.getGoodsNumber());//????????????
        customerPack.setPreQuotedPrice(pingMainOrder.getPreQoutedPrice());//????????????
        customerPack.setTransportationRouteId(pingMainOrder.getRouteId());//??????id
        customerPack.setRouteType(routeType);//????????????  1?????????   2?????????   3?????????
        customerPack.setActualWeight(pingMainOrder.getActualWeight());//????????????
        customerPack.setActualVol(pingMainOrder.getActualVol());//????????????
        customerPack.setIncidental(pingMainOrder.getIncidental());
        customerPack.setAddressId(pingMainOrder.getAddressId());//????????????
        customerPack.setRemarks(pingMainOrder.getRemarks());//??????
        customerPack.setIncidental(pingMainOrder.getAuctualPrice());//?????????
        customerPack.setInternationalTransshipmentNo(pingMainOrder.getAgentNumber());//??????????????????
        customerPack.setDiscount(pingMainOrder.getDiscount());//????????????
        customerPack.setIsSensitive(pingMainOrder.getIsSensitive() == null ? 0 : pingMainOrder.getIsSensitive());//??????????????????
        customerPack.setBusinessNumber(SequenceCode.businessNumber()); // ?????????
        customerPack.setAddressId(pingMainOrder.getAddressId());

//        customerPack.setPackTime(simpleDateFormat.format(pingMainOrder.getPackTime()));//????????????
//        customerPack.setInsideMessage(pingMainOrder.getInsideMessage());//????????????
//        customerPack.setPickedTime(pingMainOrder.getPickTime());//?????????????????????
//        customerPack.setSettlementWeight(pingMainOrder.getSettlementWeight());//????????????
//        customerPack.setSettlementVol(pingMainOrder.getSettlementVol());//????????????
//        customerPack.setPackNum(pingMainOrder.getPingPack().size());//??????????????????
//        customerPack.setActualPrice(pingMainOrder.getAuctualPrice());//????????????
//        customerPack.setAcceptedTime(); // ????????????????????????
//        customerPack.setAccepted(); // ?????????????????????0 ????????????1????????????2?????????
//        customerPack.setGoodsNo();//????????????????????????
//        customerPack.setLength();//???????????????cm
//        customerPack.setWidth();//???????????????cm
//        customerPack.setHeight();//???????????????cm
//        customerPack.setOutStorageTime();//????????????/????????????
//        customerPack.setPaymentTime();//????????????
//        customerPack.setReceiptTime();//????????????
//        customerPack.setPrintTime();//?????????????????????
//        customerPack.setPrintOper();//???????????????
//        customerPack.setPrintCount();//????????????

    }


}
