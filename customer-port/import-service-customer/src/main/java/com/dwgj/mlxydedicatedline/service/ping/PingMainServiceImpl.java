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
    private CustomerAddressMapper customerAddressMapper; // 客户地址表
    @Autowired
    private CustomerPackReceiverAddressDao customerPackReceiverAddressDao; // 包裹地址表
    @Autowired
    private CustomerPackMapper customerPackMapper;
    @Autowired
    private Goods2packMapper goods2packMapper; // 货物包裹关联表

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
     * 自己发起的拼团列表
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
     * 自己参加的拼团列表
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
     * 主页的拼团列表
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
     * 首页的置顶拼团列表，每次显示3条，但全部数据返回到前端
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
     * 创建拼团
     *
     * @param multipartFiles 多文件流，最多三个文件
     * @param pin            拼团实体类
     * @param isUpdate       是否更新
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
            return new ResponseEntity<>(ResultModel.error(ERROR, "图片上传错误"), HttpStatus.OK);
        }

        if (isUpdate) {
            // 如果是更新拼团数据，则对图片表和客户地址表做操作

            // 删除图片表、客户包裹地址表（因为并不知道这些数据是否是一样的数据）
            Images images = new Images();
            images.setContentId(pin.getOrderNumber());
            images.setPicType(ImageType.PinPicture.toString());
            List<Images> imagesByContentId = imagesMapper.getImagesByContentId(images);
            imagesMapper.deleteByIdList(imagesByContentId.stream().map(Images::getId).collect(Collectors.toList()));

            customerPackReceiverAddressDao.deleteById(pin.getAddressId());
        } else {
            String pOrderNumber = SequenceCode.pMainOrderNumber();
            pin.setOrderNumber(pOrderNumber);
            System.out.println("-------------新增");
        }

        // 插入拼团包裹地址信息（客户修改收货地址的时候，不会影响这个包裹的收货地址）
        CustomerAddress customerAddress = customerAddressMapper.selectByPrimaryKey(pin.getAddressId());

        CustomerPackReceiverAddress customerPackReceiverAddress = new CustomerPackReceiverAddress();
        customerPackReceiverAddress.setAddressee(customerAddress.getAddressee());
        customerPackReceiverAddress.setAddressId(customerAddress.getId());
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
            // 如果不是公开的团，先把团长加入到开放列表
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

            // 如果不是公开的团，先把团长加入到开放列表
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
     * 重新激活拼团
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
     * 根据拼团订单获取拼团详情
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

        // 价格渠道信息
        RouteVo transportationRoute = routeMapper.findRouteVoByRouteId(pin.getRouteId());

        // TODO 目前只根据目标重量查询详细路线
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
            route.setRouteType("空运");
        } else {
            route.setRouteType("海/铁运");
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
        // 自己的包裹
        List<PingMember> memberGoodsList = memberMapper.getMemberGoodsByPinId(member);

        Map<String, Object> result = new HashMap<>(3);


        if (!pin.getLeaderId().toString().equals(userId)) {
            // 如果是团长，则可以看到密码
            pin.setPassword("****");
        } else {
            // 成员的包裹汇总，团长可看
            List<PingMember> memberList = memberMapper.getAllMembersGoodsSumExceptLeaderByPinId(pin.getId().toString(), pin.getLeaderId().toString());
            result.put("memberList", memberList);
        }

        // 计算进度条
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
     * 添加包裹或者删除包裹
     *
     * @param orderNumber
     * @param goodsIds
     * @param takeIn      是添加包裹还是删除包裹
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

        // 已经添加过的包裹
        PingMember pingMember = new PingMember();
        pingMember.setPId(pin.getId());
        String userId = UserThreadContext.getUser() == null ? "0" : UserThreadContext.getUser().getId().toString();
        pingMember.setCustomerId(Integer.valueOf(userId));

        List<PingMember> oldList = memberMapper.getMemberGoodsByPinId(pingMember);

        if (takeIn == false) {
            // 改为入库
            goodsMapper.updateInWarehouseByGoodsIds(goods);

            memberMapper.deleteByGoodsIds(goods);

            // 查询删除货物后剩下的成员数据
            List<PingMember> pingMembers = memberMapper.getMembersByPinId(String.valueOf(pin.getId()));

            // 检验剩下的货物重量是否足够拼团的目标重量，不够则将拼团状态改成未成团
            this.updatePType(String.valueOf(pin.getId()));


            pingMainMapper.updateByPrimaryKeySelective(pin);

            // 发送消息通知，后续做可配置化的，团长可以选择不提示
            if (true) {
                pin = pingMainMapper.selectByOrderNumber(orderNumber,pin1.getId());
                sendMessageServer.sendMessageForPinLeader(pin, userId, goods.size(), "delete");
            }
            return new ResponseEntity<>(ResultModel.ok("已剔除" + goods.size() + "个包裹成功"), HttpStatus.OK);
        }

        // 判断人数是否超过限制人数
        List<PingMember> pingMemberList = memberMapper.getMembersByPinId(String.valueOf(pin.getId()));
        if (!pingMemberList.isEmpty()) {
            List<Integer> customerIdList = pingMemberList.stream().map(PingMember::getCustomerId).distinct().collect(Collectors.toList());
            if(pin.getTargetMember() == customerIdList.size()){
                return new ResponseEntity<>(ResultModel.error(IS_MAX_PEOPLE), HttpStatus.OK);
            }
        }




        for (PingMember p : oldList) {
            if (goods.contains(p.getGoodsId() + "") && takeIn) {
                goods.remove(p.getGoodsId() + "");// 因为是string类型，所以加 + ""
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


        // 修改货物状态为：拼团中
        if (goodsList.size() > 0) {
            goodsMapper.updatePinByGoodsIds(goodsList);
            // 保存到拼团列表
            memberMapper.insertPinMembers(memberList);

            List<PingMember> pingMembers = memberMapper.getMembersByPinId(String.valueOf(pin.getId()));
            pin.setCurrentPeopleNum(pingMembers.stream().map(PingMember::getCustomerId).distinct().collect(Collectors.toList()).size());
            pingMainMapper.updateByPrimaryKeySelective(pin);
        }

        // 发送消息通知，后续做可配置化的，团长可以选择不提示
        if (true) {
            pin = pingMainMapper.selectByOrderNumber(orderNumber,pin1.getId());
            sendMessageServer.sendMessageForPinLeader(pin, userId, i, "takePartIn");
        }

        return new ResponseEntity<>(ResultModel.ok("成功添加" + i + "个包裹"), HttpStatus.OK);
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
            // 修改货物状态为：入库，可以重新申请打包
            goodsMapper.updateInWarehouseByGoodsIds(goodsIdList);
            memberMapper.deleteByGoodsIds(goodsIdList);
        }

        this.updatePType(String.valueOf(pin.getId()));

        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    /**
     * 检查是否加入过拼团
     *
     * @param orderNumber
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> checkIsIn(String orderNumber, int pId) throws Exception {
//        PingMain pin = pingMainMapper.selectByOrderNumber(orderNumber);
        // 查找拼团详情
        PingMain pin = pingMainMapper.selectById(pId);
        String userId = UserThreadContext.getUser() == null ? "0" : UserThreadContext.getUser().getId().toString();
        PingMember member = new PingMember();
        member.setCustomerId(Integer.valueOf(userId));
        member.setPId(pId);

        // 团长，放行
        if (userId.equals(pin.getLeaderId() + "")) {
            return new ResponseEntity<>(ResultModel.error(IS_IN), HttpStatus.OK);
        }

        // 获取该拼团下，这个用户是否有包裹
        List<String> goodsList = memberMapper.getMemberGoodsListByOrderNumber(member);

        // 客户有添加过包裹，直接放行
        if (goodsList != null && goodsList.size() > 0) {
            return new ResponseEntity<>(ResultModel.error(IS_IN), HttpStatus.OK);
        }

        // 没添加过包裹，上限人数已到达，不能进入
        if (pin.getTargetMember() < pin.getCurrentPeopleNum()) {
            return new ResponseEntity<>(ResultModel.error(IS_MAX_PEOPLE), HttpStatus.OK);
        }

        // 截止时间已经到达，其他成员不得参与
        if (DateUtil.string2Timestamp(pin.getCutOffTime()).compareTo(new Date()) <= 0 && pin.getPType() == 1) {
            return new ResponseEntity<>(ResultModel.error(IS_TIME_OFF), HttpStatus.OK);
        }

        if (pin.getIsPublic() == 0) {
            // 没有密码，可以直接进入
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
     * 检查密码是否正确
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
        // 检查是否进入过该团
        int i = pingMemberPasswordMapper.checkIsIn(p);
        if (i > 0) {
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        // 没进过就添加记录
        if (pin.getPassword().equals(password)) {
            pingMemberPasswordMapper.insert(p);
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(PASSWORD_ERROR), HttpStatus.OK);
    }

    /**
     * 踢出某个成员
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
            // 修改货物状态为：入库，可以重新申请打包
            goodsMapper.updateInWarehouseByGoodsIds(goodsIdList);
            memberMapper.deleteByGoodsIds(goodsIdList);
        }

        // TODO 检验剩下的货物重量是否足够拼团的目标重量，不够则将拼团状态改成未成团
        this.updatePType(pId);

        return new ResponseEntity<>(ResultModel.ok("成功踢出一位成员"), HttpStatus.OK);
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
     * 删除
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
        return new ResponseEntity<>(ResultModel.ok("删除成功"), HttpStatus.OK);
    }

    /**
     * 提醒某个成员加快拼团速度
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
        return new ResponseEntity<>(ResultModel.ok("提醒团友成功"), HttpStatus.OK);
    }

    /**
     * 检查是否有成团资格
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
     * 确认成团
     *
     * @param pId
     * @param orderNumber
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<ResultModel> confirm(String pId, String orderNumber) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("客户端成团操作");
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
        // 当子订单数据为空的时候，则说明这订单是第一次成团，要添加子订单新信息
        // 二次成团的原因是成团后，有货物被踢，导致重量不足以成团，变成未成团状态继续拼团
        // 成团时候，需要检测哪些客户是已经成团了生成了包裹数据，未生成过包裹数据的客户要再添加
        List<String> customerPackOrderNumberList = new ArrayList<>();
        if (pingMainOrderList != null && !pingMainOrderList.isEmpty()) {
            customerPackOrderNumberList = pingMainOrderList.stream().map(PingMainOrder::getOrderNumber).collect(Collectors.toList());
        }
        // 给所有成员分配新的订单号
        List<PingMember> list = memberMapper.getMembersByPinId(pingMainRespVo.getId().toString());

        // 拼团订单的所有货物
        List<GoodsVo> goodsVoAllList = goodsMapper.selectGoodsByIds(list.stream().map(PingMember::getGoodsId).collect(Collectors.toList()));

        // 价格渠道信息
        RouteVo routeVo = routeMapper.findRouteVoByRouteId(pingMainRespVo.getRouteId());
        if (routeVo.getRoutePrices().isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(ROUTE_PRICE_NULL_ERROR), HttpStatus.OK);
        }

        RoutePrice routePrice = pingUtil.priceMatching(routeVo, goodsVoAllList.stream().mapToDouble(GoodsVo::getKg).sum());

        // 确定成员id
        List<Integer> customerIdList = list.stream().map(PingMember::getCustomerId).distinct().collect(Collectors.toList());

        pingMainRespVo.setCurrentPeopleNum(customerIdList.size()); // 确认最终成员数量
        // 已成团状态
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

            // 取得客户的对应包裹
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
            // 确认成团时，计算价格 计算体积重  isCalculateVolWeight
            Map<String, Object> map = calculationUtils.countKGMoney(routeVo.getRoutePrices(), routeVo, k, v, true, isCalculateVolWeight);

            // 如果 map2 为空，即没有获取到相关的渠道价格，跳过此操作
            if (map == null) {
                continue;
            }
            // 计算好预报价
            pingMainOrder.setPreQoutedPrice(routePrice.getPrice().multiply(BigDecimal.valueOf(k)));
            if (map.containsKey("finalPrice")) {
                pingMainOrder.setPreQoutedPrice(new BigDecimal(Double.valueOf(map.get("finalKG").toString())));
            }

            if (map.containsKey("finalKG")) {
                pingMainOrder.setPreQoutedPrice(new BigDecimal(Double.valueOf(map.get("finalKG").toString())));
            }

            String message = "";
            if (map.containsKey("KG_VOL")) {
                message = "由于货物体积过大，计算的重量转换为体积重量：" + String.valueOf(map.get("KG_VOL")) + " Kg";
            }

            pingMainOrder.setRemarks(message);
            pingMainOrder.setAddressId(pingMainRespVo.getAddressId());

            CustomerPack customerPack = new CustomerPack();

            // 客户包裹数据填装
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

        // 添加未打包客户包裹数据
        List<CustomerPack> customerPackList = customerPackMapper.findByOrderNumbers(customerPackOrderNumberList);
        if (customerPackList == null || customerPackList.isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(PACK_ABNORMAL_ERROR), HttpStatus.OK);
        }
        // 防止数据重复，先做一次删除操作
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
            // 插入货物包裹关联表
            goods2packMapper.insertList(insertGoods2packList);
        }

        // 添加包裹收货地址表的数据
        CustomerPackReceiverAddress customerPackReceiverAddress = customerPackReceiverAddressDao.queryById(pingMainRespVo.getAddressId());
        List<CustomerPackReceiverAddress> customerPackReceiverAddressList = new ArrayList<>();

        for (CustomerPack customerPack : customerPackList) {
            CustomerPackReceiverAddress insertAddress = cpraSetting(customerPack.getId(), customerPackReceiverAddress);
            customerPackReceiverAddressList.add(insertAddress);
        }

        if (!customerPackReceiverAddressList.isEmpty()) {
            // 先删除一遍数据，防止数据重复
            customerPackReceiverAddressDao.deleteByCustomerPackIdList(customerPackList.stream().map(CustomerPack::getId).collect(Collectors.toList()));
            customerPackReceiverAddressDao.insertList(customerPackReceiverAddressList);
        }

        sendMessageServer.sendMessageForConfirmPin(pingMainRespVo);
        transactionManager.commit(status);
        return new ResponseEntity<>(ResultModel.ok("成团成功"), HttpStatus.OK);
    }

    /**
     * 计算拼团百分比  返回格式：00.00
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
        insertAddress.setAddressId(customerPackReceiverAddress.getAddressId());
        insertAddress.setAddressee(customerPackReceiverAddress.getAddressee());
        insertAddress.setCode(customerPackReceiverAddress.getCode());
        insertAddress.setPhoneNumber(customerPackReceiverAddress.getPhoneNumber());
        insertAddress.setReceiverAddress(customerPackReceiverAddress.getReceiverAddress());
        insertAddress.setStatus(1);
        return insertAddress;
    }


//    public static void main(String[] args) throws Exception {
//        // 截止时间已经到达，其他成员不得参与
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
//        // 创建文件路径
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

    // 包裹的收货地址绑定（客户修改地址列表的地址不会影响这个包裹的收货地址，反过来也一样）
    private void customer2addressSetting(CustomerAddress customerAddress, Customer2address customer2address) {
        customer2address.setAddress(customerAddress.getReceiverAddress());
        customer2address.setAddressee(customerAddress.getAddressee());
        customer2address.setCustomerId(customerAddress.getCustomerId());
        customer2address.setCustomerName(UserThreadContext.getUser() == null ? "" : UserThreadContext.getUser().getCustomerName());
        customer2address.setTelephone(customerAddress.getPhoneNumber());
        customer2address.setStatus(1);
    }

    // 图片上传保存
    private ResponseEntity<ResultModel> imagesUpload(MultipartFile[] multipartFiles, List<Images> imagesList) throws IOException {
        System.out.println("上传图片");
        System.out.println("图片：" + multipartFiles);
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
            // 创建文件路径
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
        System.out.println("上传图片结束");
        return null;
    }


    /**
     * 拼装CustomerPack的数据
     *
     * @param customerPack 客户包裹数据
     */
    private void customerPackSetting(PingMainOrder pingMainOrder, CustomerPack customerPack, List<GoodsVo> customerGoods, int routeType) {
        customerPack.setStatus(1);//状态 1、未删除 0、已删除
        customerPack.setVersion(1);//数据版本，用于乐观锁，每次操作就增加 1
        customerPack.setOrderNumber(pingMainOrder.getOrderNumber());//订单号
        customerPack.setUserId(pingMainOrder.getCreateId());//操作员id
        customerPack.setCustomerId(pingMainOrder.getCustomerId());//客户id
        customerPack.setPackType(1);//packType：1、待打包；  2、已打包；  3、确认发货；  4、已出库； 7、待拆包 ； 9、已收货
        customerPack.setUnpacking(1);//是否拆包   1、不用拆包  2、需要拆包
        customerPack.setGoodsSum(pingMainOrder.getGoodsNumber());//包裹数量
        customerPack.setPreQuotedPrice(pingMainOrder.getPreQoutedPrice());//预计价格
        customerPack.setTransportationRouteId(pingMainOrder.getRouteId());//路线id
        customerPack.setRouteType(routeType);//路线类型  1、空运   2、海运   3、其他
        customerPack.setActualWeight(pingMainOrder.getActualWeight());//实际重量
        customerPack.setActualVol(pingMainOrder.getActualVol());//实际体积
        customerPack.setIncidental(pingMainOrder.getIncidental());
        customerPack.setAddressId(pingMainOrder.getAddressId());//收货地址
        customerPack.setRemarks(pingMainOrder.getRemarks());//备注
        customerPack.setIncidental(pingMainOrder.getAuctualPrice());//杂费；
        customerPack.setInternationalTransshipmentNo(pingMainOrder.getAgentNumber());//国际转运单号
        customerPack.setDiscount(pingMainOrder.getDiscount());//优惠价格
        customerPack.setIsSensitive(pingMainOrder.getIsSensitive() == null ? 0 : pingMainOrder.getIsSensitive());//是否敏感货物
        customerPack.setBusinessNumber(SequenceCode.businessNumber()); // 流水号
        customerPack.setAddressId(pingMainOrder.getAddressId());

//        customerPack.setPackTime(simpleDateFormat.format(pingMainOrder.getPackTime()));//打包时间
//        customerPack.setInsideMessage(pingMainOrder.getInsideMessage());//内部备注
//        customerPack.setPickedTime(pingMainOrder.getPickTime());//已拣货完成时间
//        customerPack.setSettlementWeight(pingMainOrder.getSettlementWeight());//结算重量
//        customerPack.setSettlementVol(pingMainOrder.getSettlementVol());//结算体积
//        customerPack.setPackNum(pingMainOrder.getPingPack().size());//打包包裹数量
//        customerPack.setActualPrice(pingMainOrder.getAuctualPrice());//实际价格
//        customerPack.setAcceptedTime(); // 受理时的触发时间
//        customerPack.setAccepted(); // 订单受理状态：0 未受理，1已受理，2已拣货
//        customerPack.setGoodsNo();//仓库中的包裹代码
//        customerPack.setLength();//长，单位：cm
//        customerPack.setWidth();//宽，单位：cm
//        customerPack.setHeight();//高，单位：cm
//        customerPack.setOutStorageTime();//出库时间/发货时间
//        customerPack.setPaymentTime();//付款时间
//        customerPack.setReceiptTime();//签收时间
//        customerPack.setPrintTime();//打印清单的时间
//        customerPack.setPrintOper();//打印操作员
//        customerPack.setPrintCount();//打印次数

    }


}
