package com.example.warehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.DO.CustomerPackExportDo;
import com.example.warehouse.common.*;
import com.example.warehouse.entity.*;
import com.example.warehouse.entity.coupons.CustomerCoupons;
import com.example.warehouse.entity.coupons.CustomerCouponsCenter;
import com.example.warehouse.entity.customer.CustomerIntegral;
import com.example.warehouse.entity.customer.CustomerIntegralRule;
import com.example.warehouse.entity.customerPack.CustomerPackPriceDetail;
import com.example.warehouse.entity.customerPack.PackInsurancePrice;
import com.example.warehouse.entity.customerPack.PackValuation;
import com.example.warehouse.entity.image.ImageType;
import com.example.warehouse.entity.image.Images;
import com.example.warehouse.entity.insurance.Insurance;
import com.example.warehouse.entity.insurance.InsurancePrice;
import com.example.warehouse.entity.ping.PingMainOrder;
import com.example.warehouse.entity.ping.PingMember;
import com.example.warehouse.entity.ping.PingPack;
import com.example.warehouse.entity.sys.LogOperation;
import com.example.warehouse.entity.sys.UserPacked;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.enums.customerPack.PackTypeEnums;
import com.example.warehouse.enums.route.YameiTransportTypeCode;
import com.example.warehouse.log.SysLog;
import com.example.warehouse.mapper.*;
import com.example.warehouse.mapper.coupons.CustomerCouponsCenterMapper;
import com.example.warehouse.mapper.coupons.CustomerCouponsMapper;
import com.example.warehouse.mapper.customer.CustomerIntegralMapper;
import com.example.warehouse.mapper.customer.CustomerIntegralRuleMapper;
import com.example.warehouse.mapper.customer2address.Customer2addressMapper;
import com.example.warehouse.mapper.customerMoney.CustomerMoneyMapper;
import com.example.warehouse.mapper.customerPack.*;
import com.example.warehouse.mapper.customerPackNumber.CustomerPackNumberMapper;
import com.example.warehouse.mapper.customerPackReceiverAddress.CustomerPackReceiverAddressDao;
import com.example.warehouse.mapper.goods2pack.Goods2packMapper;
import com.example.warehouse.mapper.image.ImagesMapper;
import com.example.warehouse.mapper.insurance.InsuranceMapper;
import com.example.warehouse.mapper.insurance.InsurancePriceMapper;
import com.example.warehouse.mapper.ping.*;
import com.example.warehouse.mapper.routePrice.RoutePriceMapper;
import com.example.warehouse.mapper.shelves.GoodsShelvesRowMapper;
import com.example.warehouse.mapper.shelves.ShelvesRowMapper;
import com.example.warehouse.mapper.sys.TrajectoryLogMapper;
import com.example.warehouse.mapper.sys.UserPackedMapper;
import com.example.warehouse.mapper.transportationRoute.TransportationRouteMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.CustomerPackService;
import com.example.warehouse.service.agentApplyPack.ApplyPackService;
import com.example.warehouse.service.sys.LogOperationService;
import com.example.warehouse.service.sys.SysTokenService;
import com.example.warehouse.service.sys.TrajectoryService;
import com.example.warehouse.service.wechat.SendMessageServer;
import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.applyPack.GoodsPackingVo;
import com.example.warehouse.vo.customer.*;
import com.example.warehouse.vo.customerPack.CustomerPackNumberVo;
import com.example.warehouse.vo.customerPack.CustomerPackVo;
import com.example.warehouse.vo.customerPack.RouteCustomerPackReqVo;
import com.example.warehouse.vo.mobile.PackSortReqVo;
import com.example.warehouse.vo.packVo.PackConditionsQueryReqVo;
import com.example.warehouse.vo.packVo.PackSonVo;
import com.example.warehouse.vo.packVo.PackedReqVo;
import com.example.warehouse.vo.ping.responseVo.PingMainOrderRespVo;
import com.example.warehouse.vo.ping.responseVo.PingMainRespVo;
import com.example.warehouse.vo.route.RouteVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.shiro.SecurityUtils;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.warehouse.enums.ResultStatus.*;
import static com.example.warehouse.enums.customerPack.PackTypeEnums.*;
import static com.example.warehouse.enums.goods.GoodsStatus.*;

/**
 * @author lrq
 * 2019年11月1日15:25:25
 */
@Service
@Slf4j
public class CustomerPackServiceImpl implements CustomerPackService {

    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private CustomerPackMapper customerPackMapper;
    @Autowired
    private CustomerPackIdentityMapper customerPackIdentityMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerAddressMapper customerAddressMapper;
    @Autowired
    private CustomerMoneyMapper customerMoneyMapper;
    @Autowired
    private CustomerAccountMapper customerAccountMapper;
    @Autowired
    private CustomerPackNumberMapper packNumberMapper;
    @Autowired
    private CustomerPackPriceDetailMapper customerPackPriceDetailMapper;
    @Autowired
    private PackValuationMapper packValuationMapper;
    @Autowired
    private PackInsurancePriceMapper packInsurancePriceMapper;
    @Autowired
    private CustomerPackReceiverAddressDao customerPackReceiverAddressMapper;
    @Autowired
    private Customer2addressMapper customer2addressMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private CalculationUtils calculationUtils;
    @Autowired
    private RoutePriceMapper routePriceMapper;
    @Autowired
    private TransportationRouteMapper transportationRouteMapper;
    @Resource
    private Goods2packMapper goods2packMapper;
    @Autowired
    private AgentOrderMapper agentOrderMapper;
    @Autowired
    private SendMessageServer sendMessageServer;
    @Autowired
    private LogOperationService logOperationService;
    @Autowired
    private PingMainOrderMapper pingMainOrderMapper;
    @Autowired
    private PingMainMapper pingMainMapper;
    @Autowired
    private PingPackMapper pingPackMapper;
    @Autowired
    private PingMemberMapper pingMemberMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CustomerFromMapper customerFromMapper;
    @Autowired
    private CustomerIntegralRuleMapper customerIntegralRuleMapper;
    @Autowired
    private CustomerIntegralMapper customerIntegralMapper;
    @Autowired
    private CustomerCouponsMapper customerCouponsMapper;
    @Autowired
    private CustomerCouponsCenterMapper customerCouponsCenterMapper;
    @Autowired
    private InsuranceMapper insuranceMapper;
    @Autowired
    private InsurancePriceMapper insurancePriceMapper;
    @Autowired
    private UserPackedMapper userPackedMapper;

    @Autowired
    private TrajectoryService trajectoryService;

    @Autowired
    private TrajectoryLogMapper trajectoryLogMapper;

    @Autowired
    private GoodsShelvesRowMapper goodsShelvesRowMapper;
    @Autowired
    private ShelvesRowMapper shelvesRowMapper;
    @Autowired
    private SysTokenService sysTokenService;

    @Autowired
    private PackUtils packUtils;
    @Resource
    private ApplyPackService applyPackService;
    /**
     * 申请打包url
     */
    @Value("${wechat.order_type}")
    private String ORDER_TYPE;
    /**
     * 系统标识
     */
    @Value("${wechat.system_code}")
    private String SYSTEM_CODE;

    @Value("${upload.location}")
    private String LOCATION;

    @Autowired
    private ImagesMapper imagesMapper;

    @Override
    public ResponseEntity<ResultModel> getWaitPackList(PackSortReqVo packSortReqVo) {
        List<CustomerPack> customerPackList = customerPackMapper.getWaitPackList(packSortReqVo);
        return new ResponseEntity<>(ResultModel.ok(customerPackList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getOrderDetail(String businessNumber) {
        CustomerPackVo customerPackVo = this.findPackByBusinessNumber(businessNumber);
        if(customerPackVo.getAccepted() != 2){
            customerPackVo.setAccepted(1); // 订单受理状态变更为 拣货中
            customerPackVo.setAcceptedTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
        }
        int i = customerPackMapper.updateByPrimaryKeySelective(customerPackVo);
        if(i == 1){
            return new ResponseEntity<>(ResultModel.ok(customerPackVo), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> overPicking(String businessNumber) {
        CustomerPack customerPack = customerPackMapper.findByBusinessNumber(businessNumber);
        customerPack.setAccepted(2); // 订单受理状态变更为 拣货完成
        customerPack.setAcceptedTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
        int i = customerPackMapper.updateByPrimaryKeySelective(customerPack);
        if(i == 1){
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getByOrderNumber(String orderNumber) {
        Map<String, Object> resultMap = new HashMap<>();
        CustomerPackVo customerPackVo1 = customerPackMapper.findByOrderNumber(orderNumber);
        if(customerPackVo1 != null){
            resultMap.put("customerPack", customerPackVo1);
        }
        List<CustomerPackVo> transhipmentPackList = customerPackMapper.selectByTransshipmentNo(orderNumber);
        if(!CollectionUtils.isEmpty(transhipmentPackList)){
            resultMap.put("transhipmentPack", transhipmentPackList);
        }
        List<GoodsVo> goodsList = goodsMapper.getGoodsDetailByOrder(orderNumber);
        if(!CollectionUtils.isEmpty(goodsList)){
            resultMap.put("goods", goodsList);
        }
        if(MapUtils.isEmpty(resultMap)){
            return new ResponseEntity<>(ResultModel.error(ERROR, "暂无当前订单的信息"), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ResultModel> orderOutWare(String orderNumber, HttpServletRequest request) {
        String userCode = request.getHeader("Authorization");
        if(userCode == null || userCode.isEmpty()){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }
        User user = userMapper.findByCode(userCode);

        CustomerPackVo customerPackVo1 = customerPackMapper.findByOrderNumber(orderNumber);
        List<CustomerPackVo> deliverPackIdList = new ArrayList<>();
        if(customerPackVo1 != null){
            if(PackTypeEnums.OUT_WARE_HOUSE.getValue() == customerPackVo1.getPackType()){
                return new ResponseEntity<>(ResultModel.error(ERROR, "订单"+customerPackVo1.getOrderNumber()+"已出库"), HttpStatus.OK);
            }

            if(PackTypeEnums.WAIT_DELIVERY.getValue() != customerPackVo1.getPackType()){
                return new ResponseEntity<>(ResultModel.error(ERROR,
                        "订单"+customerPackVo1.getOrderNumber()+"尚未付款，请通知客户付款后再发货"), HttpStatus.OK);
            }
            deliverPackIdList.add(customerPackVo1);
        }
        List<CustomerPackVo> transhipmentPackList = customerPackMapper.selectByTransshipmentNo(orderNumber);
        if(!CollectionUtils.isEmpty(transhipmentPackList)){
            deliverPackIdList.addAll(transhipmentPackList);
        }
        if(!CollectionUtils.isEmpty(deliverPackIdList)){
            try{
                for (CustomerPackVo customerPackVo : deliverPackIdList) {
                    if(PackTypeEnums.WAIT_DELIVERY.getValue() != customerPackVo.getPackType()
                            && PackTypeEnums.OUT_WARE_HOUSE.getValue() == customerPackVo.getPackType()){
                        return new ResponseEntity<>(ResultModel.error(ERROR,
                                "订单"+customerPackVo.getOrderNumber()+"尚未付款，请通知客户付款后再发货"), HttpStatus.OK);
                    }
                }
                List<Integer> packIdList = deliverPackIdList.stream().map(CustomerPackVo::getId).collect(Collectors.toList());
                customerPackMapper.updateDeliverBatch(packIdList, user.getId());
                return new ResponseEntity<>(ResultModel.ok(orderNumber + "：出库完成"), HttpStatus.OK);

            }catch (Exception e){
                log.info("出库执行报错：{}", e.getMessage());
                return new ResponseEntity<>(ResultModel.error(ERROR, e.getMessage()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getProductValueDetail(Integer packId) {
        List<PackValuation> packValuationList = packValuationMapper.selectByPackId(packId);
        PackInsurancePrice packInsurancePrice = packInsurancePriceMapper.selectByPackId(packId);
        List<PackInsurancePrice> insurancePriceList = new ArrayList<>();
        if (packInsurancePrice != null) {
            insurancePriceList.add(packInsurancePrice);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("packValuations", packValuationList);
        resultMap.put("packInsurancePrices", insurancePriceList);
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
    }

    @Override
    public int countSelectAll(Map<String, Object> paramMap) {
        return customerPackMapper.countSelectAll(paramMap);
    }

    @Override
    public ResponseEntity<ResultModel> findByPackType(Map<String, Object> paramMap) {
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            if(user == null){
                return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
            }
            if(user.getCommercialAreaId() == null){
                return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
            }
            // 当用户所在的区域是中国总部，则不限制查看
            if(user.getCommercialAreaId() != 1){
                paramMap.put("commercialAreaId", user.getCommercialAreaId());
//            customerCouponsReqVo.setCommercialAreaId(user.getCommercialAreaId());
            }

            Map<String, Object> resultMap = new HashMap<>();
            PageData data = (PageData) PageHelp.initPage(paramMap).get("page");

            if(paramMap.containsKey("deliveryOrderNo") && !StringUtils.isEmpty(paramMap.get("deliveryOrderNo").toString())){
                paramMap.put("deliveryOrderNo", paramMap.get("deliveryOrderNo").toString().trim());
            }
            if(paramMap.containsKey("orderNumber") && !StringUtils.isEmpty(paramMap.get("orderNumber").toString())){
                paramMap.put("orderNumber", paramMap.get("orderNumber").toString().trim());
            }
            if(paramMap.containsKey("businessNumber") && !StringUtils.isEmpty(paramMap.get("businessNumber").toString())){
                paramMap.put("businessNumber", paramMap.get("businessNumber").toString().trim());
            }

            int count = countSelectByPackType(paramMap);
            if (count < 1) {
                data.setTotal(0);
                resultMap.put("page", data);
                return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
            }

            Integer packType = 0;
            List<Integer> packTypes = new ArrayList<>();
            if (paramMap.containsKey("packType")) {
                packType = Integer.valueOf((String) paramMap.get("packType"));
            }
            if (packType != 0) {
                packTypes.add(packType);
                paramMap.put("packTypes", packTypes);
            } else {
                packTypes.add(1);
                packTypes.add(2);
                packTypes.add(3);
                paramMap.put("packTypes", packTypes);
            }

            List<CustomerPackVo> customerPackVoList = customerPackMapper.findByPackType(paramMap);
            data.setTotal(count);
            resultMap.put("page", data);
            resultMap.put("customerPack", customerPackVoList);
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        } catch (Exception e) {
            log.info("错误信息：{}" + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR, e.getMessage()), HttpStatus.OK);
        }

    }

    @Override
    public int countSelectByPackType(Map<String, Object> paramMap) {
        Integer packType = 0;
        List<Integer> packTypes = new ArrayList<>();
        if (paramMap.containsKey("packType")) {
            packType = Integer.valueOf((String) paramMap.get("packType"));
        }
        // 如果包裹状态为空，则查询所有状态的包裹
        if (packType == 0) {
            packTypes.add(1);
            packTypes.add(2);
            packTypes.add(3);
            paramMap.put("packTypes", packTypes);
        } else {
            packTypes.add(packType);
            paramMap.put("packTypes", packTypes);
        }
        return customerPackMapper.countSelectByPackType(paramMap);
    }

    @Override
    public ResponseEntity<ResultModel> findOutStorageList(PackConditionsQueryReqVo queryReqVo) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }
        // 当用户所在的区域是中国总部，则不限制查看
        if(user.getCommercialAreaId() != 1){
//            map.put("commercialAreaId", user.getCommercialAreaId());
            queryReqVo.setCommercialAreaId(user.getCommercialAreaId());
        }

        if(!StringUtils.isEmpty(queryReqVo.getDeliveryOrderNo())){
            queryReqVo.setDeliveryOrderNo(queryReqVo.getDeliveryOrderNo().trim());
        }
        if(!StringUtils.isEmpty(queryReqVo.getBusinessNumber())){
            queryReqVo.setBusinessNumber(queryReqVo.getBusinessNumber().trim());
        }
        if(!StringUtils.isEmpty(queryReqVo.getOrderNumber())){
            queryReqVo.setOrderNumber(queryReqVo.getOrderNumber().trim());
        }

        Map<String, Object> resultMap = new HashMap<>();
        PageData data = PageHelp.editPage(queryReqVo);
        int count = customerPackMapper.countOutStorageList(queryReqVo);
        if (count < 1) {
            data.setTotal(0);
            resultMap.put("page", data);
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        }

        queryReqVo.setPageNumber(data.getPageNumber());
        List<CustomerPackVo> customerPackVoList = customerPackMapper.findOutStorageList(queryReqVo);
        data.setTotal(count);
        resultMap.put("page", data);
        resultMap.put("customerPack", customerPackVoList);
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
    }

    @Override
    public int countOutStorageList(PackConditionsQueryReqVo queryReqVo) {
        return customerPackMapper.countOutStorageList(queryReqVo);
    }


    @Override
    public List<CustomerPackVo> findToBeShipped(Map<String, Object> paramMap) {
        List<CustomerPackVo> customerPackVoList = new ArrayList<>();
        paramMap.put("packType", 2);
        List<CustomerPackVo> packListF = customerPackMapper.findByPackType(paramMap);
        paramMap.put("packType", 3);
        List<CustomerPackVo> packListS = customerPackMapper.findByPackType(paramMap);
        packListF.forEach(pack -> customerPackVoList.add(pack));
        packListS.forEach(pack -> customerPackVoList.add(pack));
        return customerPackVoList;
    }

    @Override
    public List<CustomerPackVo> findShipped(Map<String, Object> paramMap) {
        paramMap.put("packType", 4);
        return customerPackMapper.findByPackType(paramMap);
    }

    @Override
    public List<GoodsVo> findByBusinessNumber(String businessNumber) {
        CustomerPackVo customerPackVo = customerPackMapper.findByBusinessNumber(businessNumber);
        List<Goods2pack> goods2packList = goods2packMapper.findByPackId(customerPackVo.getId());
        //        List<String> goodsNoList = Arrays.asList(customerPackVo.getGoodsNo().split(","));
        List<Integer> goodsIds = new ArrayList<>();
        for (Goods2pack goods2pack : goods2packList) {
            goodsIds.add(goods2pack.getGoodsId());
        }
        List<GoodsVo> goodsVoList = goodsMapper.selectByIds(goodsIds);
        return goodsVoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResultModel> updateDeliver(Map<String, Object> paramMap, HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("包裹出库操作");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {

            User user = (User) SecurityUtils.getSubject().getPrincipal();
            if(user == null){
                return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
            }

            int id = Integer.parseInt((String) paramMap.get("ids"));
            String transitNumber = (String) paramMap.get("transitNumber");
            CustomerPack customerPack = customerPackMapper.selectByPrimaryKey(id);
            if (customerPack.getPackType() != 3) {
                return new ResponseEntity<>(ResultModel.error(PACK_ERROR), HttpStatus.OK);
            }

            // 检验是否属于拼团订单，是则返回报错信息
            boolean isPin = checkPinOrder(customerPack);
            if (isPin) {
                return new ResponseEntity<>(ResultModel.error(THIS_PACK_IS_PIN_ERROR), HttpStatus.OK);
            }
            customerPackMapper.updateDeliver(id, transitNumber);

            // 如果转单号不为空，这注册转单号到17网
            if(!StringUtils.isEmpty(transitNumber)){
                customerPackRegisterTrack17(customerPack.getOrderNumber(), transitNumber, request);
            }

            List<String> goodsNos = Arrays.asList(customerPack.getGoodsNo().split(","));
            // 批量出库，更新状态
            goodsMapper.batchOutWare(goodsNos);

            // 成员下单上级客户获得积分方法
            childRebate(customerPack);

            // 日志操作，因为接口只传了ID，需要记录订单号
            LogOperation logOperation = new LogOperation();
            logOperation.setOpertor(user.getId());
            logOperation.setOpertorName(user.getUsername());
            logOperation.setCreateId(user.getId());
            logOperation.setContentId(customerPack.getOrderNumber());
            logOperation.setRespCode("100");
            logOperation.setContent("操作发货[订单号]");
            logOperation.setContentType(SysLog.Type.UPDATE);
            logOperationService.saveLogOperation(logOperation);

            transactionManager.commit(status);
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }

    }

    private void childRebate(CustomerPack customerPack) {
        CustomerIntegralRule integralRule = customerIntegralRuleMapper.getIntegralRuleByIntegralType("childOrder");
        // 没有积分返利功能，直接返回
        if(integralRule == null){
            return;
        }

        // 当前客户的上级
        Integer customerIdOne = customerFromMapper.selectFromIdByCustomerId(customerPack.getCustomerId());
        // 没有上级客户，直接返回
        if(customerIdOne == null){
            return;
        }
        List<Integer> customerIdList = new ArrayList<>();
        customerIdList.add(customerIdOne);
        // 当前客户的上上级
        Integer customerIdTwo = customerFromMapper.selectFromIdByCustomerId(customerIdOne);
        if(null != customerIdTwo){
            customerIdList.add(customerIdTwo);
        }

        BigDecimal actualPrice = customerPack.getActualPrice();

        Integer orderAmount = integralRule.getOrderAmount();
        Double givingIntegrals = integralRule.getIntegrals();
        BigDecimal integral = actualPrice.divide(new BigDecimal(orderAmount)).multiply(new BigDecimal(givingIntegrals));
        double integrals = integral.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        List<CustomerIntegral> customerIntegrals = new ArrayList<>();
        for (Integer customerId : customerIdList) {
            CustomerIntegral customerIntegral = new CustomerIntegral();
            customerIntegral.setStatus(1);
            customerIntegral.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));

            customerIntegral.setCustomerId(customerId);
            customerIntegral.setFromId(customerPack.getCustomerId());
            customerIntegral.setIntegrals(integrals);
            customerIntegral.setIntegralType("childOrder");

            customerIntegrals.add(customerIntegral);
        }
        customerIntegralMapper.insertList(customerIntegrals);


    }

    private boolean checkPinOrder(CustomerPack customerPack) {
        PingMainOrder pingMainOrder = pingMainOrderMapper.selectByOrderNumber(customerPack.getOrderNumber());
        return pingMainOrder != null;
    }

    /**
     * 查询待拆包
     *
     * @param paramMap
     * @return
     */
    @Override
    public List<CustomerPackVo> findByToUnpacking(Map<String, Object> paramMap) {
        return customerPackMapper.findByToUnpacking(paramMap);
    }

    /**
     * 仓储系统拆包操作
     * map 具体参数：
     * customerPackId：包裹id
     * goods：包裹集合，包括（  goodsId：快递包裹id；  location：仓库地址 ）
     * money：拆包价格
     *
     * @param map
     * @return
     */
    @Override
    @Transactional
    public int delete(Map<String, Object> map) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("包裹拆包操作");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            int packId = Integer.parseInt((String) map.get("customerPackId"));
            CustomerPack customerPack = customerPackMapper.selectByPrimaryKey(packId);
            if (customerPack.getPackType() != 7) {
                return -2;
            }
            customerPackMapper.unpacked(packId, customerPack.getVersion());
            // 子包裹删除
            packNumberMapper.deleteByPackId(packId);
            String goodsStr = (String) map.get("goods");
            List<Goods> goodsList = JSONArray.parseArray(goodsStr, Goods.class);
            List<String> goodsNoList = Arrays.asList(customerPack.getGoodsNo().split(","));
            List<GoodsVo> oldGoodsList = goodsMapper.findByGoodsNos(goodsNoList);
            for (Goods goods : goodsList) {
                for (GoodsVo oldGoods : oldGoodsList) {
                    // 添加相关 货物的版本号
                    if (goods.getId().equals(oldGoods.getId())) {
                        goods.setVersion(oldGoods.getVersion());
                        break;
                    }
                }
                goods.setGoodsType(1);
                goods.setLocation(goods.getLocation());
                goodsMapper.unpacked(goods);
            }
            // 删除 goods2pack 的相关内容
            goods2packMapper.deleteByPackId(customerPack.getId());

            Integer customerId = customerPack.getCustomerId();
            Customer customer = customerMapper.selectByPrimaryKey(customerId);

            // 余额 变更操作,0或者null，则不变更
            CustomerMoney customerMoney = customerMoneyMapper.selectByCustomerNo(customer.getCustomerNo());
            if (StringUtils.isNotBlank(map.get("money").toString())) {
                if (!"0".equals(map.get("money").toString())) {
                    double unPackMoney = Double.valueOf((String) map.get("money"));
                    if (customerMoney.getAmount().doubleValue() < unPackMoney) {
                        return 999;
                    }
                    BigDecimal newAmount = new BigDecimal(customerMoney.getAmount().doubleValue() - unPackMoney);
                    customerMoney.setAmount(newAmount);
                    customerMoneyMapper.updateByPrimaryKey(customerMoney);


                    //新增一条客户余额变更记录
                    CustomerAccount customerAccount = new CustomerAccount();
                    customerAccount.setAmount(BigDecimal.valueOf(unPackMoney).negate());
                    customerAccount.setCustomerNo(String.valueOf(customer.getId()));
                    customerAccount.setCustomerName(customer.getCustomerName());
                    customerAccount.setGoodsSum(goodsList.size());
                    customerAccount.setCostType("2");
                    customerAccount.setCurrency("人民币");
                    customerAccount.setMessage("包裹拆包费用");
                    customerAccountMapper.insertSelective(customerAccount);
                }
            }

            transactionManager.commit(status);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
            return -1;
        }
    }

    @Override
    public CustomerPackVo findPackByBusinessNumber(String businessNumber) {
        CustomerPackVo customerPackVo = customerPackMapper.findByBusinessNumber(businessNumber);
        if(!CollectionUtils.isEmpty(customerPackVo.getPackValuations())){
            SysDictDetail currencyData = customerPackVo.getRouteVo().getCurrencyData();
            BigDecimal goodsValue = new BigDecimal(0);
            for (PackValuation packValuation : customerPackVo.getPackValuations()) {
                goodsValue = goodsValue.add(packValuation.getGoodsValue());
            }
            customerPackVo.setProductValue(goodsValue.multiply(new BigDecimal(Double.valueOf(currencyData.getAlternateField()))));
        }
        List<GoodsVo> goodsVoList = goodsMapper.selectByIds(customerPackVo.getGoods2packs().stream().map(Goods2pack::getGoodsId).collect(Collectors.toList()));
        customerPackVo.setGoods(goodsVoList);
        return customerPackVo;
    }

    @Override
    public ResponseEntity<ResultModel> getWaitPackDetail(String businessNumber){
        CustomerPackVo customerPackVo = customerPackMapper.selectWaitPackDetailByBusinessNumber(businessNumber);
        if(!CollectionUtils.isEmpty(customerPackVo.getPackValuations())){
            SysDictDetail currencyData = customerPackVo.getRouteVo().getCurrencyData();
            BigDecimal goodsValue = new BigDecimal(0);
            for (PackValuation packValuation : customerPackVo.getPackValuations()) {
                goodsValue = goodsValue.add(packValuation.getGoodsValue());
            }
            customerPackVo.setProductValue(goodsValue.multiply(new BigDecimal(Double.valueOf(currencyData.getAlternateField()))));
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("customerPack", customerPackVo);
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
    }

    @Override
    public int cancelUnpack(Integer packId) {
        CustomerPack customerPack = customerPackMapper.selectByPrimaryKey(packId);
        customerPack.setPackType(2);
        return customerPackMapper.updateByPrimaryKeySelective(customerPack);
    }

    /**
     * 修改包裹信息操作
     *
     * @param paramMap
     * @return
     */
    @Override
    @Transactional
    public int updateCustomerPackMessage(Map<String, Object> paramMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("包裹信息修改操作");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {

            int customerPackId = Integer.parseInt(paramMap.get("customerPackId").toString());

            //            // 不确定子包裹是否为原来的，所以删除之前的子包裹，重新插入新数据
            //            packNumberMapper.deleteByPackId(customerPackId);

            // 转运单号
            StringBuilder internationalTransshipmentNo = null;
            // 备注信息
            StringBuilder remarks;
            CustomerPack customerPack = customerPackMapper.selectByPrimaryKey(customerPackId);

            List<CustomerPackNumber> customerPackNumbers =
                    JSONArray.parseArray(paramMap.get("customerPackNumbers").toString(), CustomerPackNumber.class);

            for (CustomerPackNumber customerPackNumber : customerPackNumbers) {
                if (customerPackNumber.getLength() == null || customerPackNumber.getLength() == 0) {
                    return -151;
                }
                if (customerPackNumber.getWidth() == null || customerPackNumber.getWidth() == 0) {
                    return -151;
                }
                if (customerPackNumber.getHeight() == null || customerPackNumber.getHeight() == 0) {
                    return -151;
                }
            }

            // 已选中的包裹数量
            List<String> customerPackNumberGoodsNosSum = new ArrayList<>();
            // 有效的快包裹递数量
            List<Goods2pack> goods2packList = goods2packMapper.findByPackId(customerPack.getId());

            for (CustomerPackNumber customerPackNumber : customerPackNumbers) {

                List<String> goodsNos = Arrays.asList(customerPackNumber.getGoodsNos().split(","));
                customerPackNumberGoodsNosSum.addAll(goodsNos);
            }
            if (goods2packList.size() != customerPackNumberGoodsNosSum.size()) {
                return -2;
            }
            // 修改转运单号
            if (paramMap.containsKey("internationalTransshipmentNo") && paramMap.get("internationalTransshipmentNo") != null
                    && paramMap.get("internationalTransshipmentNo") != "") {
                internationalTransshipmentNo = new StringBuilder(paramMap.get("internationalTransshipmentNo").toString());
                customerPack.setInternationalTransshipmentNo(internationalTransshipmentNo.toString().trim());
            }
            // 修改备注
            if (paramMap.containsKey("remarks") && paramMap.get("remarks") != null && paramMap.get("remarks") != "") {
                remarks = new StringBuilder(paramMap.get("remarks").toString());
                customerPack.setRemarks(remarks.toString().trim());
            }

            // 添加包裹尺寸信息
            if (customerPack.getRemarks() != null && customerPack.getRemarks().indexOf("尺寸") == -1) {
                StringBuilder packMessages = new StringBuilder("\n【尺寸：");
                int i = 1;
                for (CustomerPackNumber p : customerPackNumbers) {
                    packMessages.append(i + ": " + p.getActualWeight() + "kg (" + p.getLength() + "*" + p.getWidth() + "*" + p.getHeight() + ")cm ");
                    i++;
                }
                packMessages.append("】\n");
                customerPack.setRemarks(customerPack.getRemarks() + packMessages);
            }

            List<CustomerPackNumber> insertCustomerPackNumbers = new ArrayList<>();
            TransportationRoute transportationRoute = transportationRouteMapper.selectByPrimaryKey(customerPack.getTransportationRouteId());
            if (transportationRoute == null) {
                return -3;
            }
            List<RoutePrice> routePriceList = routePriceMapper.findByRouteId(customerPack.getTransportationRouteId(), transportationRoute.getRouteType());
            if (routePriceList.size() < 1) {
                return -4;
            }
            RoutePrice routePrice = routePriceList.get(0);
            // customerPackNumber 参数
            double length = 0;
            double width = 0;
            double height = 0;
            double actualWeight = 0;
            double actualVol = 0;
            double actualPackWeight = 0;
            double actualPackVol = 0;
            BigDecimal packActualPrice = new BigDecimal(0);

            int packSum = 0;
            for (CustomerPackNumber customerPackNumber : customerPackNumbers) {
                if (customerPackNumber.getLength() != null) {
                    length = customerPackNumber.getLength();
                }
                if (customerPackNumber.getWidth() != null) {
                    width = customerPackNumber.getWidth();
                }
                if (customerPackNumber.getHeight() != null) {
                    height = customerPackNumber.getHeight();
                }
                if (customerPackNumber.getActualWeight() != null) {
                    actualWeight = customerPackNumber.getActualWeight();
                }
                //体积计算
                double vol = (length / 100) * (width / 100) * (height / 100);
                if (transportationRoute.getRouteType() == 1) {
                    // 如果 体积为空，则赋予重量体积
                    if (vol == 0) {
                        vol = customerPackNumber.getActualWeight() * transportationRoute.getVolConversion() / 1000000;
                    }
                    //如果 重量为空，则赋予体积重量
                    if (actualWeight == 0) {
                        actualWeight = vol / transportationRoute.getVolConversion() * 1000000;
                    }
                } else {
                    // 如果 体积为空，则赋予重量体积
                    if (vol == 0) {
                        vol = customerPackNumber.getActualWeight() / transportationRoute.getVolConversion();
                    }
                    //如果 重量为空，则赋予体积重量
                    if (actualWeight == 0) {
                        actualWeight = vol * transportationRoute.getVolConversion();
                    }
                }

                BigDecimal b = new BigDecimal(vol);
                double d = b.setScale(4, BigDecimal.ROUND_UP).doubleValue();
                actualVol = d;
                customerPackNumber.setActualVol(actualVol);
                //  计算总订单包裹的重量
                actualPackWeight = actualPackWeight + actualWeight;
                // 计算总订单包裹的体积
                actualPackVol = actualPackVol + actualVol;

                customerPackNumber.setPackTime(new Date());
                customerPackNumber.setSettlementVol(actualVol);
                customerPackNumber.setSettlementWeight(actualWeight);
                customerPackNumber.setCustomerPackId(customerPack.getId());
                customerPackNumber.setStatus(1);
                customerPackNumber.setVersion(1);

                if (null != internationalTransshipmentNo) {
                    customerPackNumber.setInternationalTransshipmentNo(internationalTransshipmentNo.toString());
                }

                if (customerPackNumbers.size() > 1) {
                    packSum = packSum + 1;
                    customerPackNumber.setPackOrder(packSum + "~" + customerPack.getOrderNumber());
                } else {
                    customerPackNumber.setPackOrder(customerPack.getOrderNumber());
                }

                insertCustomerPackNumbers.add(customerPackNumber);
            }

            //            // 添加新的子包裹数据
            //            packNumberMapper.insertSelectiveList(insertCustomerPackNumbers);

            // 更新子包裹具体信息
            packNumberMapper.updatePackNumberList(insertCustomerPackNumbers);

            // 根据总订单包裹的重量 actualPackWeight 或者 体积 actualPackVol 计算包裹总价格
            Map<String, Object> utilsMap;
            if (customerPack.getRouteType() == 1) {

                // 非一票一件渠道， 默认计算体积重
                utilsMap = calculationUtils.countKGMoney(null, transportationRoute, actualPackWeight, actualPackVol, false, true);
                if (utilsMap == null) {
                    System.out.println("《路线计算KG的价格》超出限制，返回");
                    return -6;
                }
                if (utilsMap.containsKey("finalPrice")) {
                    packActualPrice = (BigDecimal) utilsMap.get("finalPrice");
                }
                if (utilsMap.containsKey("finalKG")) {
                    customerPack.setSettlementWeight((Double) utilsMap.get("finalKG"));
                }
                // 体积过大，转体积重
                if (utilsMap.containsKey("KG_VOL")) {
                    customerPack.setSettlementWeight((Double) utilsMap.get("KG_VOL"));
                    customerPack.setRemarks(customerPack.getRemarks() == null ? "" : customerPack.getRemarks() + ";修改包装后由于体积过大,转换为体积重:" + utilsMap.get("KG_VOL") + "KG");
                }
            } else {
                utilsMap = calculationUtils.countVOLMoney(null, transportationRoute, actualPackVol, actualPackWeight, false);
                if (utilsMap == null) {
                    System.out.println("《路线计算m³的价格》超出限制，返回");
                    return -6;
                }
                if (utilsMap.containsKey("finalPrice")) {
                    packActualPrice = (BigDecimal) utilsMap.get("finalPrice");
                }
                if (utilsMap.containsKey("finalVOL")) {
                    customerPack.setSettlementVol((Double) utilsMap.get("finalVOL"));
                }
                // 体积过大，转体积重
                if (utilsMap.containsKey("KG_VOL")) {
                    customerPack.setSettlementWeight((Double) utilsMap.get("KG_VOL"));
                    customerPack.setRemarks(customerPack.getRemarks() == null ? "" : customerPack.getRemarks() + ";修改包装后由于体积过大,转换为体积重:" + utilsMap.get("KG_VOL") + "KG");
                }
            }

            customerPack.setPackNum(insertCustomerPackNumbers.size());
            // 实际总价格
            //            customerPack.setActualPrice(packActualPrice.doubleValue());
            // 实际总重量
            customerPack.setActualWeight(actualPackWeight);
            // 实际总体积
            customerPack.setActualVol(actualPackVol);

            // 备注信息初始化
            if (paramMap.get("remarks") != null) {
                customerPack.setRemarks(paramMap.get("remarks").toString().trim());
            } else {
                customerPack.setRemarks("");
            }

            // 添加包裹尺寸信息
            if (customerPack.getRemarks() != null && customerPack.getRemarks().indexOf("尺寸") == -1) {
                StringBuilder packMessages = new StringBuilder("\n【尺寸：");
                int i = 1;
                for (CustomerPackNumber p : customerPackNumbers) {
                    packMessages.append(i + ": " + p.getActualWeight() + "kg (" + p.getLength() + "*" + p.getWidth() + "*" + p.getHeight() + ")cm ");
                    i++;
                }
                packMessages.append("】\n");
                customerPack.setRemarks(customerPack.getRemarks() + packMessages);
            }

            // 实际价格
            BigDecimal newActualPrice = packActualPrice;

            // 优惠价格  目前只能由管理员做修改
            BigDecimal discount;
            if (paramMap.containsKey("discount")) {
                if (StringUtils.isNotBlank(paramMap.get("discount").toString())) {
                    // 余额取2位小数
                    discount = new BigDecimal(Double.valueOf(paramMap.get("discount").toString())).setScale(2, BigDecimal.ROUND_HALF_UP);
                    customerPack.setDiscount(discount);
                    newActualPrice = newActualPrice.subtract(discount);
                    if (customerPack.getRemarks() != null && !"".equals(customerPack.getRemarks())) {
                        if (customerPack.getRemarks().indexOf("免的优惠") != -1) {
                            customerPack.setRemarks(customerPack.getRemarks());
                        } else {
                            customerPack.setRemarks(customerPack.getRemarks() + "；减免的优惠金额：" + discount + "元");
                        }
                    } else {
                        customerPack.setRemarks("减免的优惠金额：" + discount + "元");
                    }
                } else {
                    // 如果页面填写优惠金额，则为0
                    discount = new BigDecimal(0);
                    customerPack.setDiscount(discount);
                }
            }
            // 附加费
            BigDecimal incidental;
            if (paramMap.containsKey("incidental")) {
                if (StringUtils.isNotBlank(paramMap.get("incidental").toString())) {
                    // 取2位小数
                    incidental = new BigDecimal(Double.valueOf(paramMap.get("incidental").toString())).setScale(2, BigDecimal.ROUND_HALF_UP);
                    customerPack.setIncidental(incidental);
                    newActualPrice = newActualPrice.add(incidental);
                    if (customerPack.getRemarks() != null && customerPack.getRemarks() != "") {
                        if (customerPack.getRemarks().indexOf("包含附加费") != -1) {
                            customerPack.setRemarks(customerPack.getRemarks());
                        } else {
                            customerPack.setRemarks(customerPack.getRemarks() + "；包含附加费：" + incidental + "元");
                        }
                    } else {
                        customerPack.setRemarks("包含附加费：" + incidental + "元");
                    }
                } else {
                    // 如果页面每天写附加费，则按照原来的价格
                    incidental = new BigDecimal(0);
                    customerPack.setIncidental(incidental);
                }
            }

            // 计算出最终价格，如果是已经打包好，就不再重新计算价格
            if (customerPack.getPackType() == 1 || customerPack.getPackType() == 2) {
                customerPack.setActualPrice(newActualPrice);
            }

            String insideMessage = "";
            insideMessage = paramMap.get("insideMessage") == null ? "" : paramMap.get("insideMessage").toString().trim();
            customerPack.setInsideMessage(insideMessage);

            // 判断当前包裹是否是拼团包裹
            PingMainOrder pingMainOrder = pingMainOrderMapper.selectByOrderNumber(customerPack.getOrderNumber());
            if (pingMainOrder != null) {
                List<CustomerPackNumberVo> customerPackNumberList = packNumberMapper.findByPackId(customerPack.getId());
                // 拼团子订单数据更新
                this.pingMainOrderUpdate(customerPack, pingMainOrder, customerPackNumberList);
            }

            customerPackMapper.updateCustomerPackMessage(customerPack);
            transactionManager.commit(status);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("包裹状态不变更错误信息：{}", e.getMessage());
            transactionManager.rollback(status);
            return -1;
        }
    }

    @Override
    public int updateAgentNumber(CustomerPack customerPack, HttpServletRequest request) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        customerPack.setVersion(2);
        customerPack.setUpdateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        customerPack.setUserId(user == null ? 0 : user.getId());

        String internationalTransshipmentNo = customerPack.getInternationalTransshipmentNo() == null ? "" : customerPack.getInternationalTransshipmentNo().trim();
        customerPack.setInternationalTransshipmentNo(internationalTransshipmentNo);
        // 不用判断数据库是否有无这条记录，有就直接更新，没有则更新0条记录
        AgentOrder order = new AgentOrder();
        order.setOrderNumber(customerPack.getOrderNumber());
        order.setAgentNumber(internationalTransshipmentNo);
        order.setLastUpdateTime(DateUtil.timestamp2String(new Date()));
        order.setLastUpdateId(user == null ? "0" : user.getId().toString());
        agentOrderMapper.updateByOrderNumber(order);
        if(!internationalTransshipmentNo.isEmpty()){
            customerPackRegisterTrack17(customerPack.getOrderNumber(), internationalTransshipmentNo, request);
//            List<Map> orderDataList = new ArrayList<>();
//            Map<String, String> orderData = new HashMap<>();
//            orderData.put("orderNumber", customerPack.getOrderNumber());
//            orderData.put("transitNumber", internationalTransshipmentNo);
//            orderData.put("systemCode", SYSTEM_CODE);
//            orderDataList.add(orderData);
//            if("FLY".equals(SYSTEM_CODE)){
//                trajectoryService.registerTrack17OrderData(JSONObject.toJSONString(orderDataList), request);
//            }else{
//                HttpUtilManager httpUtilManager = HttpUtilManager.getInstance();
//                String registerUrl = "http://jiyun.flycloudstorage.com/api/AppDataService/otherSystemRegister";
////                String registerUrl = "http://www.lrqcloud.work/api/AppDataService/otherSystemRegister";
//                JSONObject requestData = new JSONObject();
//                requestData.put("orderData", orderDataList);
//                httpUtilManager.post(registerUrl, requestData.toJSONString());
//            }
        }
        // 判断当前包裹是否是拼团包裹
        PingMainOrder pingMainOrder = pingMainOrderMapper.selectByOrderNumber(customerPack.getOrderNumber());
        if (pingMainOrder != null) {
            pingMainOrder.setAgentNumber(customerPack.getInternationalTransshipmentNo());
            pingMainOrderMapper.updateByPrimaryKeySelective(pingMainOrder);
        }
        return customerPackMapper.updateByPrimaryKeySelective(customerPack);
    }

    @Override
    public ResponseEntity<ResultModel> updatePackMessage(CustomerPack customerPack, HttpServletRequest request) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }
        CustomerPack pack = new CustomerPack();
        pack.setInsideMessage(customerPack.getInsideMessage() == null ? "" : customerPack.getInsideMessage().trim());
        String internationalTransshipmentNo = ("").equals(customerPack.getInternationalTransshipmentNo()) ? "" : customerPack.getInternationalTransshipmentNo().trim();

        if(!internationalTransshipmentNo.isEmpty()){
            customerPackRegisterTrack17(customerPack.getOrderNumber(), internationalTransshipmentNo, request);
        }

        pack.setInternationalTransshipmentNo(internationalTransshipmentNo);
        pack.setRemarks(customerPack.getRemarks());
        pack.setBusinessNumber(customerPack.getBusinessNumber());
        pack.setUpdateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));

        // 判断当前包裹是否是拼团包裹
        PingMainOrder pingMainOrder = pingMainOrderMapper.selectByOrderNumber(customerPack.getOrderNumber());
        if (pingMainOrder != null) {
            pingMainOrder.setInsideMessage(customerPack.getInsideMessage());
            pingMainOrder.setAgentNumber(internationalTransshipmentNo);
            pingMainOrder.setRemarks(customerPack.getRemarks());
            pingMainOrderMapper.updateByPrimaryKeySelective(pingMainOrder);
        }
        int i = customerPackMapper.changePackMessage(pack);
        if (i < 0) {
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> updatePackValuations(@RequestParam int packId, @RequestParam String packValuations) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }
        CustomerPack customerPack = customerPackMapper.selectById(packId);
        if(customerPack == null){
            return new ResponseEntity<>(ResultModel.error(PACK_NULL_ERROR), HttpStatus.OK);
        }
        List<PackValuation> packValuationList = JSONObject.parseArray(packValuations, PackValuation.class);

        if(CollectionUtils.isEmpty(packValuationList)){
            return new ResponseEntity<>(ResultModel.error(PACK_VALUATION_NULL_ERROR), HttpStatus.OK);
        }

        // 删除以往的产品货值
        packValuationMapper.deleteByPackId(packId);
        for (PackValuation packValuation : packValuationList) {
            packValuation.setStatus(1);
            packValuation.setVersion(1);

            packValuation.setCreateId(user.getId());
            packValuation.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
            packValuation.setPackId(packId);

            packValuation.setOrderNumber(customerPack.getOrderNumber());
        }
        packValuationMapper.insertList(packValuationList);

        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> updateChannel(CustomerPack customerPack) {
        int routeId = customerPack.getTransportationRouteId();
        customerPack = customerPackMapper.selectPackVoById(customerPack.getId());
        // 检验是否属于拼团订单，是则返回报错信息
        boolean isPin = checkPinOrder(customerPack);
        if (isPin) {
            return new ResponseEntity<>(ResultModel.error(THIS_PACK_IS_PIN_ERROR), HttpStatus.OK);
        }
        CustomerPack pack = new CustomerPack();
        pack.setId(customerPack.getId());
        pack.setTransportationRouteId(routeId);
        pack.setUpdateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        int i = customerPackMapper.updateByPrimaryKeySelective(pack);
        if (i < 1) {
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> updateOrderVoided(CustomerPack customerPack) {
        // 检验是否属于拼团订单，是则返回报错信息
        boolean isPin = checkPinOrder(customerPack);
        if (isPin) {
            return new ResponseEntity<>(ResultModel.error(THIS_PACK_IS_PIN_ERROR), HttpStatus.OK);
        }

        CustomerPack pack = new CustomerPack();
        pack.setId(customerPack.getId());
        pack.setPackType(4); // 出库状态
        pack.setUpdateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        customerPack = customerPackMapper.selectByPrimaryKey(customerPack.getId());
        pack.setRemarks("此订单作废;" + customerPack.getRemarks());
        pack.setInsideMessage("此订单作废:" + DateUtil.timestamp2String(new Date()) + " " + customerPack.getInsideMessage() == null ? "" : customerPack.getInsideMessage());
        int i = customerPackMapper.updateByPrimaryKeySelective(pack);
        if (i < 1) {
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ResultModel> cancleApply(int packId) {

        CustomerPack customerPack = customerPackMapper.selectByPrimaryKey(packId);

        // 检验是否属于拼团订单，是则返回报错信息
        boolean isPin = checkPinOrder(customerPack);
        if (isPin) {
            return new ResponseEntity<>(ResultModel.error(THIS_PACK_IS_PIN_ERROR), HttpStatus.OK);
        }

        customerPack.setStatus(2);
        try {
            customerPackMapper.updateByPrimaryKeySelective(customerPack);
            customerPackMapper.cancleGoods2pack(packId);
            List<String> packIds = customerPackMapper.getAllGoodsIdByPackId(packId);
            for (String goodId : packIds) {
                customerPackMapper.cancleGoods(Integer.parseInt(goodId));
            }
        } catch (Exception e) {
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> handleOrder(int packId, int accepted) {
        CustomerPack customerPack = customerPackMapper.selectByPrimaryKey(packId);
        // 检验是否属于拼团订单，是则返回报错信息
        boolean isPin = checkPinOrder(customerPack);
        if (isPin) {
            return new ResponseEntity<>(ResultModel.error(THIS_PACK_IS_PIN_ERROR), HttpStatus.OK);
        }
        // 开始受理之后，不可变更受理人
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }
        // 当包裹的受理状态为未受理时，判断当前登录人和操作人员是否是同一人，不是同一人则返回提示
        if (customerPack.getAccepted() != 0 && !customerPack.getUserId().equals(user.getId())) {
            return new ResponseEntity<>(ResultModel.error(PACK_ACCEPTED), HttpStatus.OK);
        }

        if (accepted == 1) { // 受理时间
            customerPack.setUserId(user.getId());
            customerPack.setAcceptedTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        } else if (accepted == 2) { // 拣货完成时间
            customerPack.setPickedTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }

        try {
            // 受理状态变更
            customerPack.setAccepted(accepted);
            customerPackMapper.updateByPrimaryKeySelective(customerPack);
        } catch (Exception e) {
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    /**
     * @param packedReqVo
     * @param isEditPack  是否编辑包裹信息
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<ResultModel> packing(PackedReqVo packedReqVo, boolean isEditPack) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("包裹打包/编辑操作");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            if(user == null){
                return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
            }

            CustomerPackVo customerPackVo;
            // 判断是不是拼团打包页面的请求
            if (packedReqVo.getIsPinPacked() == 1) {
                PingMainOrderRespVo pingMainOrderRespVo = pingMainOrderMapper.selectById(packedReqVo.getId());
                customerPackVo = customerPackMapper.findByBusinessNumber(pingMainOrderRespVo.getBusinessNumber());
            } else {
                customerPackVo = customerPackMapper.selectPackVoById(packedReqVo.getId());
            }


            RouteVo routeVo = customerPackVo.getRouteVo();
            if (routeVo == null) {
                return new ResponseEntity<>(ResultModel.error(TRANSPORTROUTE_ERROR), HttpStatus.OK);
            }
            List<RoutePrice> routePriceList = routeVo.getRoutePrices();
            if (routePriceList == null || routePriceList.isEmpty()) {
                return new ResponseEntity<>(ResultModel.error(TRANSPORT_ROUTE_PRICE_ERROR), HttpStatus.OK);
            }

            List<PackSonVo> packSonVoList = JSONArray.parseArray(packedReqVo.getPackSonVos(), PackSonVo.class);
//        List<PackSonVo> packSonVoList = packedReqVo.getPackSonVos();
            // 无子包裹返回错误
            if (packSonVoList.isEmpty()) {
                return new ResponseEntity<>(ResultModel.error(SON_PACK_NULL_ERROR), HttpStatus.OK);
            }

            // 检查是否存在未拣货的货物
            List<GoodsVo> goodsVoList =
                    goodsMapper.selectByIds(customerPackVo.getGoods2packs().stream().map(Goods2pack::getGoodsId).collect(Collectors.toList()));
            for (GoodsVo goodsVo : goodsVoList) {
                if(goodsVo.getSortType() != 1){
                    return new ResponseEntity<>(ResultModel.error(PACK_SURPLUS_ERROR), HttpStatus.OK);
                }
            }

            if (isEditPack) {
                customerPackVo.setUpdateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            } else {
                // 如果是打包时候，包裹状态不是待打包，返回报错信息
                if (!customerPackVo.getPackType().equals(WAIT_PACK.getValue())) {
                    return new ResponseEntity<>(ResultModel.error(PACK_ACCEPTED), HttpStatus.OK);
                }

                if (customerPackVo.getOrderNumber() == null || customerPackVo.getOrderNumber().isEmpty()) {
                    customerPackVo.setOrderNumber(orderNumber(customerPackVo.getCustomerId().toString()));
                }
                customerPackVo.setPackType(PackTypeEnums.PACKED.getValue());
                customerPackVo.setPackTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            }

            BigDecimal packActualPrice = new BigDecimal(0);

            // 防止前端传空备注的时候，后端的备注无法删除的问题
//        packedReqVo.setRemarks(packedReqVo.getRemarks().isEmpty() ? "-" : packedReqVo.getRemarks());

            boolean isPerTicketPack = routeVo.getIsPerTicketPack() == 1;

            List<CustomerPackNumber> insertSonPackList = new ArrayList<>();
            int sort = 0;
            boolean isSensitive = ((packedReqVo.getIsSensitive() != null) && (packedReqVo.getIsSensitive() == 1)
                    && (routeVo.getSensitivePrice() != null) && (routeVo.getSensitivePrice().compareTo(new BigDecimal(0)) != 0));
            for (PackSonVo packSonVo : packSonVoList) {
                CustomerPackNumber customerPackNumber = new CustomerPackNumber();

                // 重量和体积的赋予， 防止出现空值
                packUtils.weightAndVolGive(packSonVo, routeVo, routePriceList.get(0));

                double length = packSonVo.getLength() != null ? packSonVo.getLength() : 0;
                double width = packSonVo.getWidth() != null ? packSonVo.getWidth() : 0;
                double height = packSonVo.getHeight() != null ? packSonVo.getHeight() : 0;
                double actualWeight = packSonVo.getActualWeight() != null ? packSonVo.getActualWeight() : 0;
                actualWeight = new BigDecimal(actualWeight).setScale(2, BigDecimal.ROUND_UP).doubleValue();// 实际重量保留2位小数
                //体积计算
                double actualVol = (length / 100) * (width / 100) * (height / 100);

                actualVol = new BigDecimal(actualVol).setScale(4, BigDecimal.ROUND_UP).doubleValue();// 实际体积保留4位小数

                customerPackNumber.setCustomerPackId(customerPackVo.getId());

                customerPackNumber.setLength(length);
                customerPackNumber.setWidth(width);
                customerPackNumber.setHeight(height);
                customerPackNumber.setActualVol(actualVol);
                customerPackNumber.setActualWeight(actualWeight);

                customerPackNumber.setPackTime(new Date());
                customerPackNumber.setStatus(1);
                customerPackNumber.setVersion(1);

                // 转单号
                if (null != packedReqVo.getInternationalTransshipmentNo()) {
                    customerPackNumber.setInternationalTransshipmentNo(packedReqVo.getInternationalTransshipmentNo());
                }

                if (packSonVoList.size() > 1) {
                    sort++;
                    customerPackNumber.setPackOrder(sort + "~" + customerPackVo.getOrderNumber());
                } else {
                    customerPackNumber.setPackOrder(customerPackVo.getOrderNumber());
                }

                packSonVo.setSonOrderNum(customerPackNumber.getPackOrder());

                insertSonPackList.add(customerPackNumber);
            }


            // 包裹实际重量和体积
            customerPackVo.setActualWeight(insertSonPackList.stream().mapToDouble(CustomerPackNumber::getActualWeight).sum());
            customerPackVo.setActualVol(insertSonPackList.stream().mapToDouble(CustomerPackNumber::getActualVol).sum());
            customerPackVo.setPackNum(insertSonPackList.size());


            BigDecimal sensitivePrice = new BigDecimal(0);
            BigDecimal forkliftFee = new BigDecimal(0);

            // 每票一件
            if (isPerTicketPack) {
                double sonPackSettlementVol = 0;
                StringBuilder KVConversionRemarks = new StringBuilder("箱子：");

                for (CustomerPackNumber customerPackNumber : insertSonPackList) {

                    BigDecimal sonPackPrice = new BigDecimal(0);
                    String remarks = "";
                    double settlementWeight = 0;
                    double settlementVol = 0;
                    // 一票一件渠道， 计算体积重
                    // 判断是否 计算体积重
                    boolean isCalculateVolWeight = routeVo.getSideLengthLimit() == null
                            || packUtils.checkIsCalculateVolWeight(routeVo.getSideLengthLimit(), customerPackNumber.getLength(),
                            customerPackNumber.getWidth(), customerPackNumber.getHeight());

                    // 价格计算、结算重量、体积计算
                    Map<String, Object> calculateMap = packUtils.actualPriceCalculate(routeVo, customerPackNumber.getActualWeight(),
                            customerPackNumber.getActualVol(), isSensitive, settlementWeight, settlementVol, sonPackPrice,
                            isCalculateVolWeight);

                    if (calculateMap.containsKey("errorMessage")) {
                        return new ResponseEntity<>(ResultModel.error(ERROR, calculateMap.get("errorMessage")), HttpStatus.OK);
                    }

                    // 子包裹结算重量
                    customerPackNumber.setSettlementWeight(Double.valueOf(calculateMap.get("settlementWeight").toString()));
                    customerPackNumber.setSettlementVol(Double.valueOf(calculateMap.get("settlementVol").toString()));

                    sonPackPrice = new BigDecimal(Double.valueOf(calculateMap.get("packActualPrice").toString()));
                    customerPackNumber.setActualPrice(sonPackPrice);
                    remarks = calculateMap.get("remarks").toString();

                    if (calculateMap.containsKey("KVremarks") && !calculateMap.get("KVremarks").toString().isEmpty()) {
                        KVConversionRemarks.append("【").
                                append(customerPackNumber.getPackOrder()).append("】").append(calculateMap.get("KVremarks").toString());

                    }

                    if (remarks != null && !remarks.isEmpty() && !packedReqVo.getRemarks().contains("叉车费")) {
                        packedReqVo.setRemarks(packedReqVo.getRemarks().isEmpty() ? remarks : packedReqVo.getRemarks() + "；" + remarks);
                    }

                    // 是否敏感货物
                    customerPackNumber.setIsSensitive(packedReqVo.getIsSensitive() == null ? 0 : packedReqVo.getIsSensitive());

                    // 子包裹结算体积，用于计算敏感货物
                    sonPackSettlementVol += new BigDecimal(customerPackNumber.getSettlementVol()).setScale(4, BigDecimal.ROUND_UP).doubleValue();

                    // 包裹的价格，累加子包裹的价格
                    packActualPrice = packActualPrice.add(sonPackPrice);

                    if(calculateMap.containsKey("sensitivePrice")){
                        sensitivePrice = sensitivePrice.add(new BigDecimal(Double.valueOf(calculateMap.get("sensitivePrice").toString())));
                    }

                    if(calculateMap.containsKey("forkliftFee")){
                        forkliftFee = forkliftFee.add(new BigDecimal(Double.valueOf(calculateMap.get("forkliftFee").toString())));
                    }

                }

                // 敏感费用备注
                if (isSensitive) {
                    String sensitiveRemark = packUtils.sensitiveRemark(routeVo, sonPackSettlementVol, customerPackVo.getPackNum());

                    packedReqVo.setRemarks(packedReqVo.getRemarks().isEmpty() ? sensitiveRemark : packedReqVo.getRemarks() + "；" + sensitiveRemark);
                }

                // 包裹结算重量和体积
                customerPackVo.setSettlementWeight(insertSonPackList.stream().mapToDouble(CustomerPackNumber::getSettlementWeight).sum());
                customerPackVo.setSettlementVol(insertSonPackList.stream().mapToDouble(CustomerPackNumber::getSettlementVol).sum());
                customerPackVo.setActualPrice(packActualPrice);

                if (!packedReqVo.getRemarks().contains("箱子：") && !KVConversionRemarks.toString().equals("箱子：")) {
                    packedReqVo.setRemarks(packedReqVo.getRemarks().isEmpty() ?
                            KVConversionRemarks.toString() : packedReqVo.getRemarks() + "；" + KVConversionRemarks.toString());
                }
                customerPackVo.setRemarks(packedReqVo.getRemarks());
            } else {
                double packSettlementWeight = 0;
                double packSettlementVol = 0;
                BigDecimal packPrice = new BigDecimal(0);
                String remarks = "";

                // 价格计算、结算重量、体积计算  非一票一件默认计算体积重(比较的是总体的体积和总体的重量)
                Map<String, Object> calculateMap = packUtils.actualPriceCalculate(routeVo, customerPackVo.getActualWeight(), customerPackVo.getActualVol(),
                        isSensitive, packSettlementWeight, packSettlementVol, packPrice, true);

                if (calculateMap.containsKey("errorMessage")) {
                    return new ResponseEntity<>(ResultModel.error(ERROR, calculateMap.get("errorMessage")), HttpStatus.OK);
                }

                customerPackVo.setSettlementWeight(Double.valueOf(calculateMap.get("settlementWeight").toString()));
                customerPackVo.setSettlementVol(Double.valueOf(calculateMap.get("settlementVol").toString()));
                customerPackVo.setActualPrice(new BigDecimal(Double.valueOf(calculateMap.get("packActualPrice").toString())));

                // 敏感货物加价
                if(calculateMap.containsKey("sensitivePrice")){
                    sensitivePrice = sensitivePrice.add(new BigDecimal(Double.valueOf(calculateMap.get("sensitivePrice").toString())));
                }
                // 叉车费加价
                if(calculateMap.containsKey("forkliftFee")){
                    forkliftFee = forkliftFee.add(new BigDecimal(Double.valueOf(calculateMap.get("forkliftFee").toString())));
                }
                if (!packedReqVo.getRemarks().contains("打包后，") && calculateMap.containsKey("KVremarks")
                        && !calculateMap.get("KVremarks").toString().equals("")) {
                    packedReqVo.setRemarks(packedReqVo.getRemarks() == null || packedReqVo.getRemarks().isEmpty()
                            ? calculateMap.get("KVremarks").toString() : packedReqVo.getRemarks() + "；" + calculateMap.get("KVremarks").toString());
                }

                remarks = calculateMap.get("remarks").toString();

                if (remarks != null && !remarks.isEmpty() && !packedReqVo.getRemarks().contains("叉车费")) {
                    packedReqVo.setRemarks(packedReqVo.getRemarks().isEmpty() ? remarks : packedReqVo.getRemarks() + "；" + remarks);
                }

                // 敏感费用备注
                if (isSensitive) {
                    if (routeVo.getSensitivePrice() != null
                            && routeVo.getSensitivePrice().compareTo(new BigDecimal(0)) != 0
                            && !packedReqVo.getRemarks().contains("货物中包含敏感货物")) {
                        String sensitiveRemark = packUtils.sensitiveRemark(routeVo, customerPackVo.getSettlementVol(), customerPackVo.getPackNum());
                        packedReqVo.setRemarks(packedReqVo.getRemarks().isEmpty() ? sensitiveRemark : packedReqVo.getRemarks() + "；" + sensitiveRemark);
                    }
                }
                customerPackVo.setRemarks(packedReqVo.getRemarks());
            }

            // 如果需要关税，且产品货值为空时返回报错信息
            if(routeVo.getIsTariffs() == 1){
                if(packedReqVo.getPackValuations() == null || packedReqVo.getPackValuations().isEmpty()){
                    return new ResponseEntity<>(ResultModel.error(ROUTE_IS_TARIFFS_PLEASE_INPUT_PACK_VALUATION), HttpStatus.OK);
                }
            }

            // 如果当前渠道没有保险，则去除保险费用
            if (isEditPack) {
                if(packedReqVo.getInsurancePriceId() == null){
                    customerPackVo.setInsurancePrice(new BigDecimal(0));
                    packInsurancePriceMapper.deleteByPackId(packedReqVo.getId());
                }else{

                    InsurancePrice insurancePrice = insurancePriceMapper.selectByPrimaryKey(packedReqVo.getInsurancePriceId());
                    PackInsurancePrice packInsurancePrice = packInsurancePriceMapper.selectByPackId(packedReqVo.getId());
                    if(packInsurancePrice == null){
                        Insurance insurance = insuranceMapper.selectByPrimaryKey(routeVo.getInsuranceId());

                        packInsurancePrice = new PackInsurancePrice();
                        packInsurancePrice.setStatus(1);
                        packInsurancePrice.setVersion(1);
                        packInsurancePrice.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));

                        packInsurancePrice.setClaimsDetail(insurance.getClaimsDetail());
                        packInsurancePrice.setGinsengPrice(insurancePrice.getGinsengPrice());
                        packInsurancePrice.setInsuranceName(insurance.getInsuranceName());
                        packInsurancePrice.setInsuranceNo(insurance.getInsuranceNo());
                        packInsurancePrice.setOrderNumber(customerPackVo.getOrderNumber());
                        packInsurancePrice.setPackId(customerPackVo.getId());
                        packInsurancePrice.setInsurancePrice(customerPackVo.getInsurancePrice());
                        packInsurancePrice.setInsurancePriceId(packedReqVo.getInsurancePriceId());
                        packInsurancePriceMapper.insertSelective(packInsurancePrice);
                    }else{
                        packInsurancePrice.setInsurancePrice(insurancePrice.getPrice());
                        packInsurancePrice.setInsurancePriceId(packedReqVo.getInsurancePriceId());
                        packInsurancePrice.setGinsengPrice(insurancePrice.getGinsengPrice());
                        packInsurancePriceMapper.updateByPrimaryKeySelective(packInsurancePrice);
                    }
                    customerPackVo.setInsurancePrice(insurancePrice.getPrice());
                }
            }

            if(packedReqVo.getPackValuations() != null && !packedReqVo.getPackValuations().isEmpty()){
                List<PackValuation> packValuationList = JSONArray.parseArray(packedReqVo.getPackValuations(), PackValuation.class);
                BigDecimal tariffsAmount;
                // 当更换成不需要关税的渠道时，跟随着一起把预付关税价格改成0；
                if(routeVo.getIsTariffs() == 1){
                    if(packValuationList == null || packValuationList.isEmpty()){
                        return new ResponseEntity<>(ResultModel.error(ROUTE_IS_TARIFFS_PLEASE_INPUT_PACK_VALUATION), HttpStatus.OK);
                    }
                    BigDecimal allGoodsValue = new BigDecimal(0);
                    for (PackValuation packValuation : packValuationList) {
                        allGoodsValue = allGoodsValue.add(packValuation.getGoodsValue());
                    }

                    // 计算关税
                    double exchangeRate = Double.valueOf(routeVo.getCurrencyData().getAlternateField());

                    // 先把价格取值4位小数
                    tariffsAmount = allGoodsValue.multiply(new BigDecimal(routeVo.getTariffs()/100)).multiply(new BigDecimal(exchangeRate));
                    tariffsAmount = tariffsAmount.setScale(4, BigDecimal.ROUND_UP);
                    // 获取小数点后第三位的数字
                    String lastThreeString = String.valueOf(tariffsAmount).
                            substring(String.valueOf(tariffsAmount).length()-2, String.valueOf(tariffsAmount).length()-1);
                    // 如果小数点后第三位的数字是0的话，直接取小数点后2位的值（删除多余的小数位）
                    // 如果小数点后第三位的数字不是0的话，直接取小数点后2位的进位处理的值
                    if(lastThreeString.equals("0")){
                        tariffsAmount = tariffsAmount.setScale(2, BigDecimal.ROUND_DOWN);
                    }else {
                        tariffsAmount = tariffsAmount.setScale(2, BigDecimal.ROUND_UP);
                    }
                }else {
                    tariffsAmount = new BigDecimal(0);
                }
                customerPackVo.setTariffsPrice(tariffsAmount);

                // 删除以前的产品货值信息
                packValuationMapper.deleteByPackId(customerPackVo.getId());

                if(packValuationList != null && !packValuationList.isEmpty()){
                    for (PackValuation packValuation : packValuationList) {
                        packValuation.setCreateId(customerPackVo.getCustomerId());
                        packValuation.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
                        packValuation.setStatus(1);
                        packValuation.setVersion(1);
                        packValuation.setPackId(customerPackVo.getId());
                        packValuation.setOrderNumber(customerPackVo.getOrderNumber());
                    }
                    packValuationMapper.insertList(packValuationList);
                }

            }

            // 费用明细表
            CustomerPackPriceDetail customerPackPriceDetail = customerPackPriceDetailMapper.selectPackPriceDetailByPackId(customerPackVo.getId());
            if(customerPackPriceDetail == null ){
                customerPackPriceDetail = new CustomerPackPriceDetail();
            }
            customerPackPriceDetail.setPackId(customerPackVo.getId());
            customerPackPriceDetail.setOrderNumber(customerPackVo.getOrderNumber());
            // 订单费用明细添加
            customerPackPriceDetail.setForkliftFee(forkliftFee);
            customerPackPriceDetail.setSensitivePrice(sensitivePrice);
            // 运费、优惠金额、附加费、关税、保险费用
            customerPackPriceDetail.setFreight(customerPackVo.getActualPrice());
            customerPackPriceDetail.setDiscount(packedReqVo.getDiscount() == null ? new BigDecimal(0) : packedReqVo.getDiscount());
            customerPackPriceDetail.setIncidental(packedReqVo.getIncidental() == null ? new BigDecimal(0) : packedReqVo.getIncidental());
            customerPackPriceDetail.setTariffsPrice(customerPackVo.getTariffsPrice() == null ? new BigDecimal(0) : customerPackVo.getTariffsPrice());
            customerPackPriceDetail.setInsurancePrice(customerPackVo.getInsurancePrice() == null ? new BigDecimal(0) : customerPackVo.getInsurancePrice());

            if(customerPackPriceDetail.getId() == null){
                customerPackPriceDetail.setCreateId(user.getId());
                customerPackPriceDetail.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
                customerPackPriceDetail.setStatus(1);
                customerPackPriceDetail.setVersion(1);
                customerPackPriceDetailMapper.insertSelective(customerPackPriceDetail);
            }else {
                customerPackPriceDetail.setUpdateId(user.getId());
                customerPackPriceDetail.setUpdateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
                customerPackPriceDetail.setVersion(customerPackPriceDetail.getVersion() + 1);
                customerPackPriceDetailMapper.updateByPrimaryKeySelective(customerPackPriceDetail);
            }

            // 价格明细费用计算实付金额
            packUtils.extraPriceCalculation(customerPackVo, customerPackPriceDetail);

            // 添加包裹尺寸信息
            if (customerPackVo.getRemarks() != null && customerPackVo.getRemarks().indexOf("尺寸") == -1) {
                StringBuilder packMessages = new StringBuilder("\n【尺寸：");
                int i = 1;
                for (CustomerPackNumber p : insertSonPackList) {
                    packMessages.append(i + ": " + p.getActualWeight() + "kg (" + p.getLength() + "*" + p.getWidth() + "*" + p.getHeight() + ")cm ");
                    i++;
                }
                packMessages.append("】\n");

                if (customerPackVo.getRemarks() == null || customerPackVo.getRemarks().isEmpty()) {
                    customerPackVo.setRemarks(packMessages.toString());
                } else {
                    customerPackVo.setRemarks(customerPackVo.getRemarks() + "；" + packMessages);
                }
            }

            customerPackVo.setIsSensitive(packedReqVo.getIsSensitive() == null ? 0 : packedReqVo.getIsSensitive());
            customerPackVo.setInternationalTransshipmentNo(packedReqVo.getInternationalTransshipmentNo());
            customerPackVo.setInsideMessage(packedReqVo.getInsideMessage());

            // 删除原子包裹数据,防止数据重复
            packNumberMapper.deleteByPackId(customerPackVo.getId());

            // 插入子包裹信息
            packNumberMapper.insertList(insertSonPackList);
            List<Integer> goodsIdList = new ArrayList<>();
            goodsVoList.forEach(goodsVo -> {
                goodsVo.setSortType(1);
                goodsVo.setInPacked(1);
                goodsVo.setGoodsType(GOODS_PACKED.getStatus());
                goodsVo.setPackTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                goodsIdList.add(goodsVo.getId());
            });

//            goodsMapper.updatePacked(goodsVoList);
            goodsMapper.updatePackedByGoodsIdList(goodsIdList);

            PingMainOrder pingMainOrder = pingMainOrderMapper.selectByOrderNumber(customerPackVo.getOrderNumber());
            if (pingMainOrder != null) {
                List<CustomerPackNumberVo> customerPackNumberList = packNumberMapper.findByPackId(customerPackVo.getId());
                // 拼团子订单数据更新
                this.pingMainOrderUpdate(customerPackVo, pingMainOrder, customerPackNumberList);
            }

            LogOperation logOperation = new LogOperation();
            logOperation.setOpertor(user.getId());
            logOperation.setOpertorName(user.getUsername());
            logOperation.setCreateId(user.getId());
            logOperation.setContentId(customerPackVo.getOrderNumber());
            logOperation.setRespCode("100");

            if (!isEditPack) {
                logOperation.setContent("打包确认[订单号]");
                logOperation.setContentType(SysLog.Type.ADD);

                if("speediness".equals(packedReqVo.getPackingType())){
                    customerPackVo.setPackType(PackTypeEnums.TO_BE_WEIGHED.getValue());
                }else {
                    sendMessageServer.sendMessageForPackage(customerPackVo, insertSonPackList.size());
                }

                // 添加打包记录
                UserPacked userPacked = new UserPacked();
                userPacked.setStatus(1);
                userPacked.setVersion(1);

                userPacked.setCreateId(user.getId());
                userPacked.setCreateName(user.getUsername());
                userPacked.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));

                userPacked.setPackId(customerPackVo.getId());
                userPacked.setActualWeight(customerPackVo.getActualWeight());
                userPacked.setActualVol(customerPackVo.getActualVol());
                userPacked.setOrderNumber(customerPackVo.getOrderNumber());
                userPacked.setPackNum(packSonVoList.size());

                userPackedMapper.insertSelective(userPacked);
            }else{
                logOperation.setContent("编辑打包信息[订单号]");
                logOperation.setContentType(SysLog.Type.UPDATE);
            }
            logOperationService.saveLogOperation(logOperation);

            // 数据更新
            customerPackMapper.updatePacking(customerPackVo);

            transactionManager.commit(status);
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        } catch (Exception e) {
            log.info("包裹打包/编辑出错：{}", e.getMessage());
            transactionManager.rollback(status);
            return new ResponseEntity<>(ResultModel.error(ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResultModel> sort(String businessNumber, String deliveryOrderNo) {
        // 第一次查，这个是初始化状态的 货物， 即未拣货状态
        List<GoodsVo> goodsList = goodsMapper.selectByPackBusinessNumber(businessNumber);
        if (goodsList.size() < 1) {
            return new ResponseEntity<>(ResultModel.error(PACK_GOODS_NULL_ERROR), HttpStatus.OK);
        }

        List<String> deliveryOrderNoList = goodsList.stream().map(Goods::getDeliveryOrderNo).collect(Collectors.toList());
        if (!deliveryOrderNoList.contains(deliveryOrderNo)) {
            return new ResponseEntity<>(ResultModel.error(CUSTOMER_GOODS_ERROR), HttpStatus.OK);
        }

        if (StringUtils.isNotBlank(deliveryOrderNo)) {
            deliveryOrderNo = deliveryOrderNo.toUpperCase();
        }

        // 执行拣货，状态改为已拣货
        int i = goodsMapper.scanPicking(deliveryOrderNo);

        if (i == 0) {
            return new ResponseEntity<>(ResultModel.error(CUSTOMER_GOODS_ERROR), HttpStatus.OK);
        }
        if (i > 0) {
            Map<String, Object> resultMap = new HashMap<>();
//            CustomerPackVo packVo = findPackByBusinessNumber(businessNumber);
//            resultMap.put("customerPack", packVo);

            Goods thisGoods = null;
            // 第二次查，这个查询到的是已拣货状态的货物
            for (Goods goods : goodsList) {
                if(deliveryOrderNo.equals(goods.getDeliveryOrderNo())){
                    thisGoods = goods;
                    goods.setSortType(1);
                }
            }
            if(thisGoods != null){
                goodsShelvesRowMapper.sortGoods(thisGoods.getId());
                shelvesRowMapper.updateRowNotFull(thisGoods.getId());
            }
            resultMap.put("goods", goodsList);

            // 检查是否全部拣货完成
            boolean isAllSort = false;
            for (Goods goodsVo : goodsList) {
                // 如果存在未拣货的，则直接跳出循环
                if (goodsVo.getSortType() == 0) {
                    isAllSort = false;
                    break;
                }
                isAllSort = true;
            }

            resultMap.put("isAllSort", isAllSort);

            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> updateAllSort(String businessNumber) {
//        CustomerPackVo customerPackVo = customerPackMapper.findByBusinessNumber(businessNumber);
//
//        List<Goods2pack> goods2packList = goods2packMapper.findByPackId(customerPackVo.getId());
//
//        List<Integer> goodsIds = new ArrayList<>();
//        goods2packList.forEach(goods2pack -> goodsIds.add(goods2pack.getGoodsId()));
//
//        List<GoodsVo> goodsVoList = goodsMapper.selectByIds(goodsIds);
        List<GoodsVo> goodsList = goodsMapper.selectByPackBusinessNumber(businessNumber);
        List<String> goodsNoList = new ArrayList<>();
        List<Integer> goodsIdList = new ArrayList<>();

//        goodsVoList.forEach(goodsVo -> goodsNoList.add(goodsVo.getGoodsNo()));
        goodsList.forEach(goods -> {
            goodsNoList.add(goods.getGoodsNo());
            goodsIdList.add(goods.getId());
            goods.setSortType(1);
        });
        int i = goodsMapper.updateAllSort(goodsNoList);
        if (i < 0) {
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        if(!CollectionUtils.isEmpty(goodsIdList)){
            goodsShelvesRowMapper.batchSortGoods(goodsIdList);
            shelvesRowMapper.batchUpdateRowNotFull(goodsIdList);
        }
        Map<String, Object> resultMap = new HashMap<>();
//        resultMap.put("customerPack", customerPackVo);
//        goodsVoList = goodsMapper.selectByIds(goodsIds);
//        resultMap.put("goods", goodsVoList);
        resultMap.put("goods", goodsList);
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> addGoodsInPack(int id, String goodsIds, int isPinPage) {
        // 检验当前登录人
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }
        // 检验货物是否勾选
        List<String> goodsIdList = Arrays.asList(goodsIds.split(","));
        if (goodsIdList.isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(PLEASE_ADD_GOODS_IN_PACK), HttpStatus.OK);
        }

        // 判断页面请求来源, 拼团页面需要添加pingMember表内容
        CustomerPackVo customerPack;
        if (isPinPage == 1) {
            PingMainOrder pingMainOrder = pingMainOrderMapper.selectByPrimaryKey(id);
            customerPack = customerPackMapper.findByOrderNumber(pingMainOrder.getOrderNumber());
            List<PingMember> pingMemberList = new ArrayList<>();
            for (String goodsId : goodsIdList) {
                PingMember pingMember = new PingMember();
                pingMember.setStatus(1);
                pingMember.setCustomerId(pingMainOrder.getCustomerId());
                pingMember.setpId(pingMainOrder.getPId());
                pingMember.setGoodsId(Integer.parseInt(goodsId));
                pingMember.setAddTime(new Date());
                pingMember.setCreateId(user.getId());
                pingMemberList.add(pingMember);
            }
            if (pingMemberList.isEmpty()) {
                return new ResponseEntity<>(ResultModel.error(PACK_ADD_GOODS_ERROR), HttpStatus.OK);
            }
            pingMemberMapper.insertList(pingMemberList);
        } else {
            customerPack = customerPackMapper.selectPackVoById(id);
        }

        if (customerPack == null) {
            return new ResponseEntity<>(ResultModel.error(PACK_NULL_ERROR), HttpStatus.OK);
        }

        List<Goods2pack> goods2packList = new ArrayList<>();
        List<Integer> goodsIdsInt = new ArrayList<>();
        for (String goodsId : goodsIdList) {
            Goods2pack goods2pack = new Goods2pack();
            goods2pack.setGoodsId(Integer.parseInt(goodsId));
            goods2pack.setPackId(customerPack.getId());
            goods2pack.setStatus(1);
            goods2packList.add(goods2pack);

            goodsIdsInt.add(Integer.parseInt(goodsId));
        }
        if (goods2packList.isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(PACK_ADD_GOODS_ERROR), HttpStatus.OK);
        }
        goods2packMapper.insertList(goods2packList);


        if (customerPack.getPackType() == PackTypeEnums.WAIT_PACK.getValue()) {
            goodsMapper.updateByIdsAndGoodsType(goodsIdsInt, WAIT_PACKING.getStatus());
        } else if (customerPack.getPackType() == PackTypeEnums.PACKED.getValue()) {
            goodsMapper.updateByIdsAndGoodsType(goodsIdsInt, GOODS_PACKED.getStatus());
        }

        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public void batchUpdateAgentNumberByOrderNumber(List<CustomerPack> customerPacks, HttpServletRequest request) {
        List<String> orderNumberList = customerPacks.stream().map(CustomerPack::getOrderNumber).collect(Collectors.toList());

        // 查询所有有效的订单数据(订单状态为《已出库》的订单)
        List<CustomerPack> effectivePackList = customerPackMapper.selectEffectivePackByOrderNumberList(orderNumberList);
        // 有效订单号集合
        List<String> effectiveOrderNumberList
                = effectivePackList.stream().map(CustomerPack::getOrderNumber).collect(Collectors.toList());

        // 需要更新的订单
        List<CustomerPack> updatePackList = new ArrayList<>();
        for (CustomerPack customerPack : customerPacks) {
            if(effectiveOrderNumberList.contains(customerPack.getOrderNumber())){
                CustomerPack updateCustomerPack = new CustomerPack();
                updateCustomerPack.setOrderNumber(customerPack.getOrderNumber());
                updateCustomerPack.setInternationalTransshipmentNo(customerPack.getInternationalTransshipmentNo());
                updateCustomerPack.setInsideMessage(customerPack.getInsideMessage());
                updatePackList.add(updateCustomerPack);
            }
        }
        customerPackMapper.updateListByOrderNumber(updatePackList);

        List<Map> orderDataList = new ArrayList<>();

        for (CustomerPack customerPack : updatePackList) {
            String orderNumber = customerPack.getOrderNumber();
            String transitNumber = customerPack.getInternationalTransshipmentNo();
            if(transitNumber.contains(",")){
                String transitNumbers [] = transitNumber.split(",");
                for (String tNumber : transitNumbers) {
                    Map<String, String> orderData = new HashMap<>();
                    orderData.put("transitNumber", tNumber);
                    orderData.put("orderNumber", orderNumber);
                    orderData.put("systemCode", SYSTEM_CODE);
                    orderDataList.add(orderData);

                }
            }else if(transitNumber.contains("，")){
                String transitNumbers [] = transitNumber.split("，");
                for (String tNumber : transitNumbers) {
                    Map<String, String> orderData = new HashMap<>();
                    orderData.put("orderNumber", orderNumber);
                    orderData.put("transitNumber", tNumber);
                    orderData.put("systemCode", SYSTEM_CODE);
                    orderDataList.add(orderData);

                }
            }else{

                Map<String, String> orderData = new HashMap<>();
                orderData.put("orderNumber", orderNumber);
                orderData.put("transitNumber", transitNumber);
                orderData.put("systemCode", SYSTEM_CODE);
                orderDataList.add(orderData);
            }
        }
        if(!CollectionUtils.isEmpty(orderDataList)){
            // 如果当前系统是飞轮的，则直接进行转单号注册
            if("FLY".equals(SYSTEM_CODE.toUpperCase())){
                trajectoryService.registerTrack17OrderData(JSONObject.toJSONString(orderDataList), request);
            }else {
                HttpUtilManager httpUtilManager = HttpUtilManager.getInstance();
                String registerUrl = "http://jiyun.flycloudstorage.com/api/AppDataService/otherSystemRegister";
//                String registerUrl = "http://www.lrqcloud.work/api/AppDataService/otherSystemRegister";
                JSONObject requestData = new JSONObject();
                requestData.put("orderData", orderDataList);
                httpUtilManager.post(registerUrl, requestData.toJSONString());
            }
        }
    }

    @Override
    public int checkOrderNumberIsOutWare(String orderNumber) {
        return customerPackMapper.checkOrderNumberIsOutWare(orderNumber);
    }

    @Override public ResponseEntity<ResultModel> buildOrderForYamei(String orderNumber) {
        CustomerPack customerPack = customerPackMapper.findByOrderNumber(orderNumber);
        if ( customerPack == null ){
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        int routeId = customerPack.getTransportationRouteId();
        TransportationRoute route = transportationRouteMapper.selectByPrimaryKey(routeId);
        if ( route != null && route.getRouteName().contains("新")  || route.getRouteName().contains("马") ){
            if ( route.getTransportType() == 2 ){
                // 空运才打印
                return new ResponseEntity<>(ResultModel.error(CHANNEL_NOT_FIXED), HttpStatus.OK);
            }
            /**
             * transporateTypeCode 亚美渠道代码
             * 西马普货MP-20(MP-MY-P20)
             * 西马特货MP-20(MP-MY-P20T)
             * 新加坡普货MP-20(MP-SG-20P)
             * 新加坡特货MP-20(MP-SG-20T)
             * 东马普货MP-20(PK0033)
             * 东马特货MP-20(PK0034)
             * 仰光空运专线普货(PK0051)
             * 马来西亚海运快递(SEA-F-MY)
             * 新加坡快船精品快递(SEA-F-SG)
             */
            String transporateTypeCode = YameiTransportTypeCode.SG_GENERAL.getCode(); // 新加坡空运普货
            String country = "SG";
            String goodsName = "衣服";
            if ( route.getRouteName().contains("敏感") ){
                // 敏感货
                if ( route.getDestination().contains("新") ){
                    country = "SG";
                    transporateTypeCode = YameiTransportTypeCode.SG_SENTITIVE.getCode(); // 新加坡空运敏感货
                }else {
                    country = "MY";
                    transporateTypeCode = YameiTransportTypeCode.MY_SENSITIVE.getCode(); // 马来空运敏感货
                }
                goodsName = "化妆品";
            }else {
                // 普货
                if ( route.getDestination().contains("新") ){
                    country = "SG";
                    transporateTypeCode = YameiTransportTypeCode.SG_GENERAL.getCode(); // 新加坡空运普货
                }else {
                    country = "MY";
                    transporateTypeCode = YameiTransportTypeCode.MY_GENERAL.getCode(); // 马来空运普货
                }
            }
            CustomerPackReceiverAddress address = customerPackReceiverAddressMapper.findByCustomerPackId(customerPack.getId());
            if ( StringUtils.isBlank(address.getCode()) ){
                // 邮编为空，不打印面单
                return new ResponseEntity<>(ResultModel.error(CODE_NULL), HttpStatus.OK);
            }
            JSONObject resultObject = BuildOrderApi.buildYameiApiPost(customerPack,address,transporateTypeCode,country,goodsName);
            if ( resultObject != null ){
                if ( resultObject.containsKey("success") ){
                    JSONObject jsonObject = (JSONObject)JSONObject.toJSON(resultObject.get("data"));
                    String agentNumber = jsonObject.get("shipping_method_no").toString();
                    customerPack.setInternationalTransshipmentNo(agentNumber);
                    customerPackMapper.updatePacking(customerPack);
                    return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(CHANNEL_NOT_FIXED), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getPayOrderByOrderNumber(String orderNumber) {
        CustomerPackVo customerPackVo = customerPackMapper.getPayOrderByOrderNumber(orderNumber);
        return new ResponseEntity<>(ResultModel.ok(customerPackVo), HttpStatus.OK);
    }


    @Override
    @Transactional
    public ResponseEntity<ResultModel> paymentOrder(OrderPayReqVo orderPayReqVo) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("customer_confirm_shipment");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            if(user == null){
                return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
            }
            CustomerPack customerPack = customerPackMapper.selectById(orderPayReqVo.getPackId());
            if(customerPack == null ){
                return new ResponseEntity<>(ResultModel.error(PACK_NULL_ERROR), HttpStatus.OK);
            }
            if(PackTypeEnums.PACKED.getValue() != customerPack.getPackType()){
                return new ResponseEntity<>(ResultModel.error(PACK_CAN_NOT_PAYMENT), HttpStatus.OK);
            }

            customerPack.setIncidental(orderPayReqVo.getIncidental());
            customerPack.setDiscount(orderPayReqVo.getDiscount());

            Customer customer = customerMapper.findById(orderPayReqVo.getCustomerId());
            // 判断余额是否充足
            CustomerMoney customerMoney = customerMoneyMapper.selectByCustomerNo(customer.getCustomerNo());
            if(customerMoney.getAmount().compareTo(orderPayReqVo.getPayAmount()) < 0){
                return new ResponseEntity<>(ResultModel.error(BALANCE_INSUFFICIENT_ERROR), HttpStatus.OK);
            }

            // 付款更新 保险价格、优惠价格、积分抵扣金额
            CustomerPackPriceDetail orderPriceDetail = customerPackPriceDetailMapper.selectPackPriceDetailByPackId(orderPayReqVo.getPackId());
            if(orderPriceDetail == null){
                orderPriceDetail = new CustomerPackPriceDetail();
                orderPriceDetail.setStatus(1);
                orderPriceDetail.setVersion(1);
                orderPriceDetail.setCreateId(orderPayReqVo.getCustomerId());
                orderPriceDetail.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
                orderPriceDetail.setOrderNumber(customerPack.getOrderNumber());
                orderPriceDetail.setPackId(customerPack.getId());
                orderPriceDetail.setFreight(customerPack.getActualPrice());
            }

            orderPriceDetail.setIncidental(orderPayReqVo.getIncidental());
            orderPriceDetail.setDiscount(orderPayReqVo.getDiscount());
            orderPriceDetail.setTariffsPrice(orderPayReqVo.getTariffsAmount());
            orderPriceDetail.setInsurancePrice(orderPayReqVo.getInsurancePrice());
            orderPriceDetail.setCouponsPrice(orderPayReqVo.getCouponsAmount());
            orderPriceDetail.setDeductionAmount(new BigDecimal(orderPayReqVo.getUsedIntegrals()));

            // 插入包裹对地址中间记录表 pack2address  start
            CustomerPackReceiverAddress receiverAddress = customerPackReceiverAddressMapper.findByCustomerPackId(customerPack.getId());
            Customer2address customer2address = new Customer2address();
            customer2address.setCustomerId(customer.getId());
            customer2address.setCustomerName(customer.getCustomerName());
            customer2address.setCustomerPackId(customerPack.getId());
            customer2address.setAddressee(receiverAddress.getAddressee());
            customer2address.setTelephone(receiverAddress.getPhoneNumber());
            customer2address.setAddress(receiverAddress.getReceiverAddress());
            customer2address.setCode(receiverAddress.getCode());
            customer2addressMapper.insertSelective(customer2address);
            // 插入地址中间记录表 end

            List<Goods2pack> goods2packList = goods2packMapper.findByPackId(customerPack.getId());
            //确认发货 修改 包裹状态
            List<Integer> goodsIdList = goods2packList.stream().map(Goods2pack::getGoodsId).collect(Collectors.toList());

//            goodsMapper.updateToConfirmShipment(goodsNoList);
            // 根据货物编码更新变更为根据货物ID更新
            goodsMapper.updateGoodsByIdListAndType(goodsIdList, GOODS_WAIT_DELIVERY.getStatus());

            // 关税金额计算
            if(orderPayReqVo.getTariffsAmount() != null){
                customerPack.setTariffsPrice(orderPayReqVo.getTariffsAmount());
                orderPriceDetail.setTariffsPrice(orderPayReqVo.getTariffsAmount());
            }

            PackInsurancePrice packInsurancePrice = packInsurancePriceMapper.selectByPackId(customerPack.getId());
            // 如果订单保险信息不为空
            if (orderPayReqVo.getInsurancePriceId() != null) {
                customerPack.setInsurancePrice(orderPayReqVo.getInsurancePrice());
                InsurancePrice insurancePrice = insurancePriceMapper.selectByPrimaryKey(orderPayReqVo.getInsurancePriceId());
                if(packInsurancePrice == null){
                    Insurance insurance = insuranceMapper.selectByPrimaryKey(orderPayReqVo.getInsuranceId());

                    packInsurancePrice = new PackInsurancePrice();
                    packInsurancePrice.setStatus(1);
                    packInsurancePrice.setVersion(1);
                    packInsurancePrice.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));

                    packInsurancePrice.setClaimsDetail(insurance.getClaimsDetail());
                    packInsurancePrice.setGinsengPrice(insurancePrice.getGinsengPrice());
                    packInsurancePrice.setInsuranceName(insurance.getInsuranceName());
                    packInsurancePrice.setInsuranceNo(insurance.getInsuranceNo());
                    packInsurancePrice.setOrderNumber(orderPayReqVo.getOrderNumber());
                    packInsurancePrice.setPackId(orderPayReqVo.getPackId());
                    packInsurancePrice.setInsurancePrice(orderPayReqVo.getInsurancePrice());
                    packInsurancePrice.setInsurancePriceId(orderPayReqVo.getInsurancePriceId());

                    packInsurancePriceMapper.insertSelective(packInsurancePrice);
                }else{
                    customerPack.setInsurancePrice(orderPayReqVo.getInsurancePrice());
                    packInsurancePrice.setInsurancePrice(orderPayReqVo.getInsurancePrice());
                    packInsurancePrice.setInsurancePriceId(orderPayReqVo.getInsurancePriceId());
                    packInsurancePrice.setGinsengPrice(insurancePrice.getGinsengPrice());
                    packInsurancePriceMapper.updateByPrimaryKeySelective(packInsurancePrice);
                }

            }else {
                packInsurancePriceMapper.deleteByPackId(customerPack.getId());
            }

            // 如果优惠券信息不为空
            if (orderPayReqVo.getCustomerCouponsId() != null) {
                CustomerCoupons customerCoupons = customerCouponsMapper.selectByPrimaryKey(orderPayReqVo.getCustomerCouponsId());
                customerCoupons.setUsedTime(DateUtil.FormatDate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
                customerCoupons.setUsedOrder(customerPack.getOrderNumber());
                customerCoupons.setCouponsStatus(1);
                customerCouponsMapper.updateByPrimaryKey(customerCoupons);

                // 优惠券使用人数+1
                CustomerCouponsCenter customerCouponsCenter
                        = customerCouponsCenterMapper.selectByPrimaryKey(customerCoupons.getCenterCouponsId());
                if (customerCouponsCenter != null) {
                    customerCouponsCenter.setCustomerUsedNum(customerCouponsCenter.getCustomerUsedNum() + 1);
                    customerCouponsCenterMapper.updateByPrimaryKey(customerCouponsCenter);
                }
            }

            // 更新积分使用情况
            if(orderPayReqVo.getUsedIntegrals() != null && orderPayReqVo.getUsedIntegrals() != 0){
                CustomerIntegral customerIntegral = new CustomerIntegral();
                customerIntegral.setStatus(1);
                customerIntegral.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));

                customerIntegral.setCustomerId(customer.getId());
                customerIntegral.setIntegralType("deductible");
                customerIntegral.setIntegrals(-orderPayReqVo.getUsedIntegrals());

                customerIntegralMapper.insertSelective(customerIntegral);
            }


            // 更新价格明细
            if(orderPriceDetail.getId() == null){
                customerPackPriceDetailMapper.insertSelective(orderPriceDetail);
            }else{
                customerPackPriceDetailMapper.updateByPrimaryKeySelective(orderPriceDetail);
            }

            RouteVo route = transportationRouteMapper.findById(customerPack.getTransportationRouteId());
            // 插入消费记录表 start
            CustomerAccount account = new CustomerAccount();
            account.setCustomerNo(customerPack.getCustomerId().toString());
            account.setCustomerName(customerMoney.getCustomerName());
            account.setLoginName(customer.getLoginName());
            account.setAmount(orderPayReqVo.getPayAmount().negate()); // 取费用的相反数，存负数
            account.setBusinessNumber(customerPack.getBusinessNumber());
            account.setOrderNumber(customerPack.getOrderNumber());
            account.setCostType("2"); // 扣费
            account.setCurrency("人民币");
            account.setCreateTime(DateUtil.timestamp2String(new Date()));
            account.setRouteName(route.getRouteName());
            account.setAddress(receiverAddress.getReceiverAddress());
            account.setGoodsSum(customerPack.getGoodsSum()); // 包裹总数
            account.setStatus(1);
            account.setVersion("1");
            customerAccountMapper.insert(account);
            // 插入消费记录表 end

            // 检验是否拼团包裹，如果是拼团包裹的话，会一同更新拼团子订单的信息
            updatePinOrder(customerPack);

            // 最后更新最终支付价格和customerPack表的数据
            customerPack.setActualPrice(orderPayReqVo.getPayAmount());
            customerPackMapper.confirmShipmentById(customerPack);

            log.info("更新客户【{}】余额表,扣除订单【{}】金额{}元,还剩:{}元", customer.getLoginName(),
                    customerPack.getOrderNumber(), orderPayReqVo.getPayAmount(),
                    customerMoney.getAmount().subtract(orderPayReqVo.getPayAmount()));
            // 扣除余额表的费用
            customerMoney.setAmount(customerMoney.getAmount().subtract(orderPayReqVo.getPayAmount()));
            customerMoneyMapper.updateByPrimaryKeySelective(customerMoney);

            transactionManager.commit(status);
            return new ResponseEntity<>(ResultModel.ok(SUCCESS), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("付款错误信息：{}" + e.getMessage());
            transactionManager.rollback(status);
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResultModel> updateOrderReceiverAddress(Map<String, Object> requestMap) {
        log.info("更改收件地址参数：{}", requestMap);

        if(String.valueOf(requestMap.get("customerPackId")).isEmpty()
                || String.valueOf(requestMap.get("addressId")).isEmpty() ){
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        Integer customerPackId = Integer.parseInt(String.valueOf(requestMap.get("customerPackId")));
        CustomerPack customerPack = customerPackMapper.selectById(customerPackId);
        if(customerPack == null){
            return new ResponseEntity<>(ResultModel.error(PACK_NULL_ERROR), HttpStatus.OK);
        }
        List<Integer> customerPackTypeList = new ArrayList<>();
        customerPackTypeList.add(WAIT_PACK.getValue());
        customerPackTypeList.add(PACKED.getValue());
        customerPackTypeList.add(WAIT_DELIVERY.getValue());
        if(!customerPackTypeList.contains(customerPack.getPackType())){
            return new ResponseEntity<>(ResultModel.error(PACK_TYPE_NOT_UPDATE_ADDRESS_ERROR), HttpStatus.OK);
        }
        Integer addressId = Integer.parseInt(String.valueOf(requestMap.get("addressId")));
        CustomerAddress customerAddress = customerAddressMapper.findById(addressId);
        if(customerAddress == null){
            return new ResponseEntity<>(ResultModel.error(CUSTOMER_ADDRESS_NULL_ERROR), HttpStatus.OK);
        }

        CustomerPackReceiverAddress customerPackReceiverAddress = new CustomerPackReceiverAddress();
        customerPackReceiverAddress.setCustomerPackId(customerPackId);
        customerPackReceiverAddress.setAddressee(customerAddress.getAddressee());
        customerPackReceiverAddress.setReceiverAddress(customerAddress.getReceiverAddress());
        customerPackReceiverAddress.setCode(customerAddress.getCode());
        customerPackReceiverAddress.setPhoneNumber(customerAddress.getPhoneNumber());
        customerPackReceiverAddress.setProvinces(customerAddress.getProvinces());
        customerPackReceiverAddressMapper.updateOrderReceiverAddress(customerPackReceiverAddress);

        Customer2address customer2address = new Customer2address();
        customer2address.setCustomerPackId(customerPack.getId());
        customer2address.setAddressee(customerAddress.getAddressee());
        customer2address.setTelephone(customerAddress.getPhoneNumber());
        customer2address.setAddress(customerAddress.getReceiverAddress());
        customer2address.setCode(customerAddress.getCode());
        customer2addressMapper.updateByCustomerPackId(customer2address);

        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public void exportCustomerPack(Map requestMap, HttpServletRequest request, HttpServletResponse response) {

        User user = userMapper.findByCode(String.valueOf(requestMap.get("usercode")));

        if(user == null){
            log.info("当前用户登录已过期");
            return;
        }
        // 当用户所在的区域是中国总部，则不限制查看
        if(user.getCommercialAreaId() != 1){
            requestMap.put("commercialAreaId", user.getCommercialAreaId());
//            queryReqVo.setCommercialAreaId(user.getCommercialAreaId());
        }

        if(StringUtils.isEmpty(String.valueOf(requestMap.get("startTime")))){
            String startTime = DateUtil.FormatDate(DateUtil.getOtherDay(-30), DateUtil.DEFAULT_DATE_FORMAT);
            String endTime = DateUtil.FormatDate(new Date(), DateUtil.DEFAULT_DATE_FORMAT);
            requestMap.put("startTime",startTime);
            requestMap.put("endTime",endTime);
        }

        List<CustomerPackExportDo> customerPackExportDoList = customerPackMapper.findExportCustomerPack(requestMap);

        try {

            // 创建一个新文件
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
            HSSFSheet sheet = hssfWorkbook.createSheet("订单详情");

            // 创建单元格样式
            HSSFCellStyle style = hssfWorkbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            // 设置第0行表头
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell = row.createCell(0);
            cell.setCellStyle(style);
            cell.setCellValue("序号");

            cell = row.createCell(1);
            cell.setCellStyle(style);
            cell.setCellValue("会员ID");

            cell = row.createCell(2);
            cell.setCellStyle(style);
            cell.setCellValue("客户名称");

            cell = row.createCell(3);
            cell.setCellStyle(style);
            cell.setCellValue("订单号");

            cell = row.createCell(4);
            cell.setCellStyle(style);
            cell.setCellValue("转单号");

            cell = row.createCell(5);
            cell.setCellStyle(style);
            cell.setCellValue("渠道");

            cell = row.createCell(6);
            cell.setCellStyle(style);
            cell.setCellValue("路线类型");

            cell = row.createCell(7);
            cell.setCellStyle(style);
            cell.setCellValue("国家");

            cell = row.createCell(8);
            cell.setCellStyle(style);
            cell.setCellValue("下单时间");

            cell = row.createCell(9);
            cell.setCellStyle(style);
            cell.setCellValue("打包时间");

            cell = row.createCell(10);
            cell.setCellStyle(style);
            cell.setCellValue("付款时间");

            cell = row.createCell(11);
            cell.setCellStyle(style);
            cell.setCellValue("出库时间");

            cell = row.createCell(12);
            cell.setCellStyle(style);
            cell.setCellValue("货物数量");

            cell = row.createCell(13);
            cell.setCellStyle(style);
            cell.setCellValue("箱子数量");

            cell = row.createCell(14);
            cell.setCellStyle(style);
            cell.setCellValue("实际体积");

            cell = row.createCell(15);
            cell.setCellStyle(style);
            cell.setCellValue("结算体积");

            cell = row.createCell(16);
            cell.setCellStyle(style);
            cell.setCellValue("实际重量");

            cell = row.createCell(17);
            cell.setCellStyle(style);
            cell.setCellValue("结算重量");

            cell = row.createCell(18);
            cell.setCellStyle(style);
            cell.setCellValue("预报价格");

            cell = row.createCell(19);
            cell.setCellStyle(style);
            cell.setCellValue("实际价格");

            cell = row.createCell(20);
            cell.setCellStyle(style);
            cell.setCellValue("运费");

            cell = row.createCell(21);
            cell.setCellStyle(style);
            cell.setCellValue("优惠金额");

            cell = row.createCell(22);
            cell.setCellStyle(style);
            cell.setCellValue("附加费");

            cell = row.createCell(23);
            cell.setCellStyle(style);
            cell.setCellValue("关税");

            cell = row.createCell(24);
            cell.setCellStyle(style);
            cell.setCellValue("保险金额");

            cell = row.createCell(25);
            cell.setCellStyle(style);
            cell.setCellValue("优惠券金额");

            cell = row.createCell(26);
            cell.setCellStyle(style);
            cell.setCellValue("积分抵扣");

            cell = row.createCell(27);
            cell.setCellStyle(style);
            cell.setCellValue("叉车费");

            cell = row.createCell(28);
            cell.setCellStyle(style);
            cell.setCellValue("敏感货物附加费");

            cell = row.createCell(29);
            cell.setCellStyle(style);
            cell.setCellValue("收件人");

            cell = row.createCell(30);
            cell.setCellStyle(style);
            cell.setCellValue("联系方式");

            cell = row.createCell(31);
            cell.setCellStyle(style);
            cell.setCellValue("邮编");

            cell = row.createCell(32);
            cell.setCellStyle(style);
            cell.setCellValue("收货地址");

            int rowNumber = 1;
            HSSFRow rowBody;
            for (CustomerPackExportDo customerPackExport : customerPackExportDoList) {
                rowBody = sheet.createRow(rowNumber);
                rowBody.createCell(0).setCellValue(rowNumber-1);
                rowBody.createCell(1).setCellValue(customerPackExport.getLoginName());
                rowBody.createCell(2).setCellValue(customerPackExport.getCustomerName());
                rowBody.createCell(3).setCellValue(customerPackExport.getOrderNumber());
                rowBody.createCell(4).setCellValue(customerPackExport.getTransitNumber());
                rowBody.createCell(5).setCellValue(customerPackExport.getRouteName());
                rowBody.createCell(6).setCellValue(customerPackExport.getRouteType() == 1 ? "空运" : "海运");
                rowBody.createCell(7).setCellValue(customerPackExport.getCountry());
                rowBody.createCell(8).setCellValue(customerPackExport.getCreateTime());
                rowBody.createCell(9).setCellValue(customerPackExport.getPackTime());
                rowBody.createCell(10).setCellValue(customerPackExport.getPaymentTime());
                rowBody.createCell(11).setCellValue(customerPackExport.getOutStorageTime());

                rowBody.createCell(12).setCellValue(String.valueOf(customerPackExport.getGoodsNum()));
                rowBody.createCell(13).setCellValue(String.valueOf(customerPackExport.getPackNum()));
                rowBody.createCell(14).setCellValue(customerPackExport.getActualVol() == null
                        ? "" :String.valueOf(customerPackExport.getActualVol()));
                rowBody.createCell(15).setCellValue(customerPackExport.getSettlementVol() == null
                        ? "" :String.valueOf(customerPackExport.getSettlementVol()));
                rowBody.createCell(16).setCellValue(customerPackExport.getActualWeight() == null
                        ? "" :String.valueOf(customerPackExport.getActualWeight()));
                rowBody.createCell(17).setCellValue(customerPackExport.getSettlementWeight() == null
                        ? "" :String.valueOf(customerPackExport.getSettlementWeight()));
                rowBody.createCell(18).setCellValue(customerPackExport.getPreQuotedPrice() == null
                        ? "" :String.valueOf(customerPackExport.getPreQuotedPrice()));
                rowBody.createCell(19).setCellValue(customerPackExport.getActualPrice() == null
                        ? "" :String.valueOf(customerPackExport.getActualPrice()));

                rowBody.createCell(20).setCellValue(customerPackExport.getFreight() == null
                        ? "" :String.valueOf(customerPackExport.getFreight()));
                rowBody.createCell(21).setCellValue(customerPackExport.getDiscount() == null
                        ? "" :String.valueOf(customerPackExport.getDiscount()));
                rowBody.createCell(22).setCellValue(customerPackExport.getIncidental() == null
                        ? "" :String.valueOf(customerPackExport.getIncidental()));
                rowBody.createCell(23).setCellValue(customerPackExport.getTariffsPrice() == null
                        ? "" :String.valueOf(customerPackExport.getTariffsPrice()));
                rowBody.createCell(24).setCellValue(customerPackExport.getInsurancePrice() == null
                        ? "" :String.valueOf(customerPackExport.getInsurancePrice()));
                rowBody.createCell(25).setCellValue(customerPackExport.getCouponsPrice() == null
                        ? "" :String.valueOf(customerPackExport.getCouponsPrice()));
                rowBody.createCell(26).setCellValue(customerPackExport.getDeductionAmount() == null
                        ? "" :String.valueOf(customerPackExport.getDeductionAmount()));
                rowBody.createCell(27).setCellValue(customerPackExport.getForkliftFee() == null
                        ? "" :String.valueOf(customerPackExport.getForkliftFee()));
                rowBody.createCell(28).setCellValue(customerPackExport.getSensitivePrice() == null
                        ? "" :String.valueOf(customerPackExport.getSensitivePrice()));

                rowBody.createCell(29).setCellValue(customerPackExport.getAddressee());
                rowBody.createCell(30).setCellValue(customerPackExport.getPhoneNumber());
                rowBody.createCell(31).setCellValue(customerPackExport.getCode());
                rowBody.createCell(32).setCellValue(customerPackExport.getAddress());

                // 执行完一次加一行
                rowNumber++
                ;
            }

            String filePath = LOCATION + "/export";
            String outPath = filePath + "/custPack_" + DateUtil.FormatDate(new Date(), "yyyyMMddHHmmss")+".xls";
            File file = new File(filePath);
            if(!file.exists()){
                file.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(outPath);
            hssfWorkbook.write(fileOutputStream);
            fileOutputStream.close();
            String excelName = "订单详情";
            int packType = Integer.parseInt(String.valueOf(requestMap.get("packType")));
            if(packType == WAIT_PACK.getValue()){
                excelName = WAIT_PACK.getName() + excelName;
            }else if(packType == PACKED.getValue()){
                excelName = PACKED.getName() + excelName;
            }else if(packType == WAIT_DELIVERY.getValue()){
                excelName = WAIT_DELIVERY.getName() + excelName;
            }else if(packType == OUT_WARE_HOUSE.getValue()){
                excelName = OUT_WARE_HOUSE.getName() + excelName;
            }
//            outPath = outPath.substring(4);
//            String url = DOMAIN + outPath;
            FileUtil.downloadPostExcel(excelName, outPath, request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResultModel> cancelDelivery(int packId) {
        CustomerPack customerPack = customerPackMapper.selectById(packId);

        Date outStorageTime = DateUtil.string2Date(customerPack.getOutStorageTime(), DateUtil.DEFAULT_TIMESTAMP_FORMAT);
        long nowDateLong = new Date().getTime();
        if(outStorageTime == null || nowDateLong - outStorageTime.getTime() > 3600000){
            return new ResponseEntity<>(ResultModel.error(PACK_OUT_STORAGE_LONG_ERROR), HttpStatus.OK);
        }
        // 更新包裹状态
        List<Goods2pack> goods2packList = goods2packMapper.findByPackId(packId);
        List<Integer> goodsIdList = goods2packList.stream().map(Goods2pack::getGoodsId).collect(Collectors.toList());
        goodsMapper.cancelGoodsDelivery(goodsIdList, GOODS_WAIT_DELIVERY.getStatus());
        // 更新订单状态
        customerPackMapper.cancelPackDelivery(packId, WAIT_DELIVERY.getValue());
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> ocrUploadOrderPicture(String orderNumber, MultipartFile file) {
        try {
            String systemName;
            if(orderNumber.contains("FL")){
                systemName = "flycloud";
            }else if(orderNumber.contains("TMA")){
                systemName = "taomao";
            }else if(orderNumber.contains("HW")){
                systemName = "huawei";
            }else if(orderNumber.contains("HY")){
                systemName = "huayou";
            }else if(orderNumber.contains("HS")){
                systemName = "huasu";
            }else {
                systemName = "unknow";
            }
            // systemName 系统名称，在对象存储器中指桶对象的名称
            Images images = TencentObjectMemory.uploadObject(systemName, ImageType.CustomerPackPicture.toString(), file);
//            Images images = ImgUtil.imagesUpload(ImageType.CustomerPackPicture.toString(), systemName, file);
            images.setContentId(orderNumber);
            imagesMapper.insertSelective(images);
            OTHER_SYSTEM_ERROR.setCode(200);
            OTHER_SYSTEM_ERROR.setMessage("订单图片上传成功");
            return new ResponseEntity<>(ResultModel.error(OTHER_SYSTEM_ERROR), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            OTHER_SYSTEM_ERROR.setCode(201);
            OTHER_SYSTEM_ERROR.setMessage("订单图片上传失败");
            return new ResponseEntity<>(ResultModel.error(OTHER_SYSTEM_ERROR), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResultModel> orcUpdateOrderRe(String body, HttpServletRequest request) {
        String token = request.getHeader("jiyun-token");
        if (StringUtils.isEmpty(token)) {
            return new ResponseEntity<>(ResultModel.error(SYS_TOKEN_OUT_OF_DATE_ERROR), HttpStatus.OK);
        }
        boolean checkResult = sysTokenService.checkToken(token);

        if (!checkResult) {
            return new ResponseEntity<>(ResultModel.error(SYS_TOKEN_OUT_OF_DATE_ERROR), HttpStatus.OK);
        }
        OrcOrderDataReqVo ocrData = JSONObject.parseObject(body, OrcOrderDataReqVo.class);
        if (ocrData.getOrderNumber().contains("FL")) {
            return orcUpdateOrder(body);
        }else if(ocrData.getOrderNumber().contains("HW")){
            log.info("------>>>>> 调用华威系统的订单尺寸更新 <<<<<------");
            return otherSystemOrcUpdateOrder("http://jiyun.huawei138.cn", body);
        }else if(ocrData.getOrderNumber().contains("TMA")){
            log.info("------>>>>> 调用淘猫系统的订单尺寸更新 <<<<<------");
            return otherSystemOrcUpdateOrder("http://gzdw.gdjiyun.com", body);
        }else if(ocrData.getOrderNumber().contains("HS")){
            log.info("------>>>>> 调用华速系统的订单尺寸更新 <<<<<------");
            return otherSystemOrcUpdateOrder("http://huasu.huawei138.cn", body);
        }else if(ocrData.getOrderNumber().contains("HY")){
            log.info("------>>>>> 调用华邮系统的订单尺寸更新 <<<<<------");
            return otherSystemOrcUpdateOrder("http://huayou.flycloudstorage.com", body);
        }else{
            log.info("------>>>>> 调用测试系统的订单尺寸更新 <<<<<------");
            return otherSystemOrcUpdateOrder("http://www.lrqcloud.work", body);
        }


    }

    @Override
    public ResponseEntity<ResultModel> orcUpdateOrder(String body) {
        OrcOrderDataReqVo orcOrderDataReqVo = JSONObject.parseObject(body, OrcOrderDataReqVo.class);
        // 判断是否是子单号
        String reqOrderNumber = orcOrderDataReqVo.getOrderNumber();
        CustomerPackVo customerPack;
        double length = orcOrderDataReqVo.getLength() != null ? orcOrderDataReqVo.getLength()/10 : 0.00;
        double width = orcOrderDataReqVo.getWidth() != null ? orcOrderDataReqVo.getWidth()/10 : 0.00;
        double height = orcOrderDataReqVo.getHeight() != null ? orcOrderDataReqVo.getHeight()/10 : 0.00;
        double actualWeight = orcOrderDataReqVo.getWeight() != null ? orcOrderDataReqVo.getWeight() : 0.00;
        // 实际重量保留2位小数
        actualWeight = new BigDecimal(actualWeight).setScale(2, BigDecimal.ROUND_UP).doubleValue();

        //体积计算
        double actualVol = (length / 100) * (width / 100) * (height / 100);
        // 实际体积保留4位小数
        actualVol = new BigDecimal(actualVol).setScale(4, BigDecimal.ROUND_UP).doubleValue();

        boolean isAllMeasurement = false; // 是否全部测量完成

        CustomerPackNumber customerPackNumber = packNumberMapper.findByOrderNumber(reqOrderNumber);
        if(customerPackNumber == null){
            return new ResponseEntity<>(ResultModel.error(PACK_NULL_ERROR), HttpStatus.OK);
        }
        customerPackNumber.setActualWeight(actualWeight);
        customerPackNumber.setLength(length);
        customerPackNumber.setWidth(width);
        customerPackNumber.setHeight(height);
        customerPackNumber.setActualVol(actualVol);
        packNumberMapper.updateByPrimaryKeySelective(customerPackNumber);

        if(reqOrderNumber.contains("~")){

            customerPack =  customerPackMapper.selectPackVoById(customerPackNumber.getCustomerPackId());
            for (CustomerPackNumberVo cpnvo : customerPack.getCustomerPackNumbers()) {
                if(cpnvo.getActualWeight() == 0){
                    isAllMeasurement = false;
                    break;
                }
                isAllMeasurement = true;
            }
        }else{
            customerPack = customerPackMapper.findByOrderNumber(reqOrderNumber);

            isAllMeasurement = true;
        }

        if(isAllMeasurement){

            // 如果全部测量完成，则开始进行对订单的总价格进行计算
            ResultStatus resultStatus = calculatePackPrice(customerPack);
            if(resultStatus.getCode() != 100){
                return new ResponseEntity<>(ResultModel.error(resultStatus), HttpStatus.OK);
            }

            customerPack.setPackType(PackTypeEnums.PACKED.getValue());

            StringBuilder remarksSB = new StringBuilder();
            remarksSB.append("【尺寸： ");
            List<CustomerPackNumberVo> customerPackNumberVoList = customerPack.getCustomerPackNumbers();
            int number = 1;
            String sizeStr;
            String remarksStart = customerPack.getRemarks();
            if(customerPack.getRemarks().contains("【")){
                remarksStart = remarksStart.split("【")[0];
            }
            for (CustomerPackNumberVo packNumber : customerPackNumberVoList) {
                sizeStr = number +"："+ packNumber.getActualWeight() + "kg（" + packNumber.getLength() +
                        "*" + packNumber.getWidth() + "*" + packNumber.getHeight() + "）cm ";
                remarksSB.append(sizeStr);
            }
            remarksStart = remarksStart + remarksSB.append("】").toString();
            customerPack.setRemarks(remarksStart);
            customerPackMapper.updateByPrimaryKeySelective(customerPack);
            sendMessageServer.sendMessageForPackage(customerPack, customerPack.getCustomerPackNumbers().size());


            PingMainOrder pingMainOrder = pingMainOrderMapper.selectByOrderNumber(customerPack.getOrderNumber());
            if (pingMainOrder != null) {
                List<CustomerPackNumberVo> customerPackNumberList = packNumberMapper.findByPackId(customerPack.getId());
                // 拼团子订单数据更新
                this.pingMainOrderUpdate(customerPack, pingMainOrder, customerPackNumberList);
            }
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    /**
     * 退件重发操作
     * @param packId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResultModel> rejectionDelivery(int packId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }
        CustomerPackVo customerPackVo = customerPackMapper.selectPackVoById(packId);
        List<CustomerPackNumberVo> customerPackNumbers = customerPackVo.getCustomerPackNumbers();
        List<Goods2pack> goods2packs = customerPackVo.getGoods2packs();
        CustomerPackPriceDetail customerPackPriceDetail = customerPackVo.getPriceDetail();
        CustomerPackReceiverAddress customerPackReceiverAddress = customerPackReceiverAddressMapper.findByCustomerPackId(packId);
        String message = "此订单系退件重发；";
        String remarks = customerPackVo.getRemarks();
        customerPackVo.setActualPrice(new BigDecimal(0));
        String orderNumber = customerPackVo.getOrderNumber();
        String businessNumber = customerPackVo.getBusinessNumber();

        orderNumber = orderNumber+"R";
        businessNumber = businessNumber+"R";

        // 查询最新的退单重发订单号
        Map<String, String> newestOrderNumberMap = customerPackMapper.selectNewestOrderNumber(orderNumber);
        // 如果没有退单重发的订单号，则说明此单没有重发过，订单号直接按当前orderNumber更新
        if(!MapUtils.isEmpty(newestOrderNumberMap)){
            orderNumber = newestOrderNumberMap.get("orderNumber")+"R";
            businessNumber = newestOrderNumberMap.get("businessNumber")+"R";
        }
        customerPackVo.setOrderNumber(orderNumber);
        if(!remarks.contains("此订单系退件重发")){
            customerPackVo.setRemarks(message + remarks);
        }
        customerPackVo.setBusinessNumber(businessNumber);
        customerPackVo.setOutStorageTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
        customerPackVo.setId(null);
        customerPackMapper.insertSelective(customerPackVo);

        CustomerPack customerPack = customerPackMapper.selectByOrderNumber(orderNumber);
        int i = 1;
        List<CustomerPackNumber> customerPackNumberList = new ArrayList<>();
        for (CustomerPackNumberVo customerPackNumber : customerPackNumbers) {
            CustomerPackNumber packNumber = new CustomerPackNumber();
            packNumber.setStatus(1);
            packNumber.setVersion(1);
            packNumber.setLength(customerPackNumber.getLength());
            packNumber.setWidth(customerPackNumber.getWidth());
            packNumber.setHeight(customerPackNumber.getHeight());
            packNumber.setActualWeight(customerPackNumber.getActualWeight());
            packNumber.setSettlementWeight(customerPackNumber.getSettlementWeight());
            packNumber.setActualVol(customerPackNumber.getActualVol());
            packNumber.setSettlementVol(customerPackNumber.getSettlementVol());
            packNumber.setActualPrice(new BigDecimal(0));
            packNumber.setIsSensitive(customerPackNumber.getIsSensitive());
            packNumber.setOutStorageTime(new Date());
            packNumber.setPackOrder(i + "~" + orderNumber);
            packNumber.setCustomerPackId(customerPack.getId());

            customerPackNumberList.add(packNumber);
            i ++;
        }
        packNumberMapper.insertList(customerPackNumberList);
        for (Goods2pack goods2pack : goods2packs) {
            goods2pack.setPackId(customerPack.getId());
        }
        goods2packMapper.insertList(goods2packs);

        customerPackPriceDetail.setPackId(customerPack.getId());
        customerPackPriceDetailMapper.insertSelective(customerPackPriceDetail);

        customerPackReceiverAddress.setCustomerPackId(customerPack.getId());
        customerPackReceiverAddressMapper.insert(customerPackReceiverAddress);

        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> updateWeighed(int packId) {
        CustomerPack customerPack = customerPackMapper.selectById(packId);
        if(TO_BE_WEIGHED.getValue() != customerPack.getPackType()){
            OTHER_SYSTEM_ERROR.setMessage("当前订单状态不能进行此操作，请刷新数据");
            return new ResponseEntity<>(ResultModel.error(OTHER_SYSTEM_ERROR), HttpStatus.OK);
        }
        customerPackMapper.updateByIdAndType(packId, PACKED.getValue());
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseEntity<ResultModel> orderAmountChange(OrderAmountChangeReqVo orderAmountChange) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }
        CustomerPack customerPack = customerPackMapper.selectById(orderAmountChange.getPackId());
        TransportationRoute route = transportationRouteMapper.findById(customerPack.getTransportationRouteId());
        CustomerPackReceiverAddress address = customerPackReceiverAddressMapper.findByCustomerPackId(customerPack.getId());
        Customer customer = customerMapper.findById(customerPack.getCustomerId());

        BigDecimal actualPrice = customerPack.getActualPrice();

        CustomerPackPriceDetail priceDetail
                = customerPackPriceDetailMapper.selectPackPriceDetailByPackId(orderAmountChange.getPackId());
        BigDecimal incidental = priceDetail.getIncidental() == null ? new BigDecimal(0) : priceDetail.getIncidental();
        BigDecimal additionalPrice = orderAmountChange.getAdditionalPrice().setScale(2, BigDecimal.ROUND_UP);


        // 处理账户余额
        CustomerMoney customerMoney = customerMoneyMapper.selectByCustomerNo(customer.getCustomerNo());
        if(customerMoney == null || customerMoney.getAmount().compareTo(additionalPrice) < 0){
            return new ResponseEntity<>(ResultModel.error(BALANCE_INSUFFICIENT_ERROR), HttpStatus.OK);
        }
        customerMoney.setAmount(customerMoney.getAmount().subtract(additionalPrice));
        customerMoneyMapper.updateByPrimaryKeySelective(customerMoney);

        // 添加金额使用记录
        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setStatus(1);
        customerAccount.setVersion("1");
        customerAccount.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
        customerAccount.setCreateId(String.valueOf(user.getId()));
        customerAccount.setCreateName(user.getUsername());

        customerAccount.setCustomerNo(String.valueOf(customer.getId()));
        customerAccount.setCustomerName(customer.getCustomerName());
        customerAccount.setLoginName(customer.getLoginName());

        customerAccount.setBusinessNumber(customerPack.getBusinessNumber());
        customerAccount.setOrderNumber(customerPack.getOrderNumber());
        customerAccount.setGoodsSum(customerPack.getGoodsSum());
        customerAccount.setRouteName(route.getRouteName());
        customerAccount.setAddress(address.getReceiverAddress());
        customerAccount.setCostType(String.valueOf(2));
        customerAccount.setMessage("--附加扣费--");
        customerAccount.setAmount(additionalPrice.negate());
        customerAccount.setCurrency("人民币");
        customerAccountMapper.insertSelective(customerAccount);

        // 更新订单价格明细(额外支付的金额添加到附加费里面)
        priceDetail.setIncidental(incidental.add(additionalPrice));
        customerPackPriceDetailMapper.updateByPrimaryKeySelective(priceDetail);

        // 更新订单金额和备注
        customerPack.setActualPrice(actualPrice.add(additionalPrice));
        customerPack.setIncidental(incidental.add(additionalPrice));
        String remarks = "额外扣款：" + additionalPrice + "元；";
        if(StringUtils.isEmpty(customerPack.getRemarks())){
            customerPack.setRemarks(remarks);
        }else if(customerPack.getRemarks().substring(customerPack.getRemarks().length()-1).equals(";") ||
                customerPack.getRemarks().substring(customerPack.getRemarks().length()-1).equals("；")){
            customerPack.setRemarks(customerPack.getRemarks() + remarks);
        }else {
            customerPack.setRemarks(customerPack.getRemarks() +"；"+ remarks);
        }
        String insideMessage = orderAmountChange.getAdditionalRemarks() + "-金额：" + additionalPrice + "元；";
        if(StringUtils.isEmpty(customerPack.getInsideMessage())){
            customerPack.setInsideMessage(insideMessage);
        }else if(customerPack.getInsideMessage().substring(customerPack.getInsideMessage().length()-1).equals(";") ||
                customerPack.getInsideMessage().substring(customerPack.getInsideMessage().length()-1).equals("；")){
            customerPack.setInsideMessage(customerPack.getInsideMessage() + insideMessage);
        }else {
            customerPack.setInsideMessage(customerPack.getInsideMessage() +"；"+ insideMessage);
        }
        customerPackMapper.updateByPrimaryKeySelective(customerPack);

        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ResultModel> getCustomerPackIdentity(Integer customerPackId) {
        CustomerIdentityRespVo customerIdentityRespVo = customerPackIdentityMapper.getCustomerPackIdentity(customerPackId);
        if(customerIdentityRespVo == null || customerIdentityRespVo.getImageIds() == null){
            return new ResponseEntity<>(ResultModel.error(CUSTOMER_PACK_IDENTITY_NULL), HttpStatus.OK);
        }
        List<Integer> imagesIdList = JSONObject.parseObject(customerIdentityRespVo.getImageIds(), List.class);
        List<Images> imagesList = imagesMapper.getCustomerPackIdentity(imagesIdList);
        customerIdentityRespVo.setImages(imagesList);
        return new ResponseEntity<>(ResultModel.ok(customerIdentityRespVo), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getGoodsList(Integer customerPackId) {
        List<GoodsVo> goodsVoList = goodsMapper.selectGoodsVoByPackId(customerPackId);
        return new ResponseEntity<>(ResultModel.ok(goodsVoList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageResultModel> getRouteCustomerPackList(RouteCustomerPackReqVo routeCustomerPackReqVo) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }
        // 当用户所在的区域是中国总部，则不限制查看
        if(user.getCommercialAreaId() != 1){
//            paramMap.put("commercialAreaId", user.getCommercialAreaId());
            routeCustomerPackReqVo.setCommercialAreaId(user.getCommercialAreaId());
        }

        PageData pageData = PageHelp.editPage(routeCustomerPackReqVo);

        List<String> customerPackIdList = null;
        if (!StringUtils.isEmpty(routeCustomerPackReqVo.getCustomerPackIds())){
            customerPackIdList = Arrays.asList(routeCustomerPackReqVo.getCustomerPackIds().split(","));
        }

        int count = customerPackMapper.getRouteCustomerPackCount(routeCustomerPackReqVo, customerPackIdList);
        pageData.setTotal(count);
        List<CustomerPackVo> customerPackVoList = new ArrayList<>();
        if(count > 0){
            routeCustomerPackReqVo.setPageNumber(pageData.getPageNumber());
            customerPackVoList = customerPackMapper.getRouteCustomerPackList(routeCustomerPackReqVo, customerPackIdList);
        }
        return new ResponseEntity<>(PageResultModel.ok(customerPackVoList, pageData), HttpStatus.OK);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResponseEntity<ResultModel> orderSplit(int id) {
        CustomerPackVo customerPack = customerPackMapper.selectPackVoById(id);
        CustomerPackReceiverAddress customerPackReceiverAddress = customerPackReceiverAddressMapper.findByCustomerPackId(id);
        List<Goods> goodsVoList = customerPack.getGoodsData();
        List<Goods> insertGoods = new ArrayList<>();
        List<String> goodsNos = new ArrayList<>();

        for (Goods goods : goodsVoList) {
            String code = SequenceCode.gainSerialNo("GOODS");
            goods.setId(null);
            goods.setGoodsNo(code);
            goods.setDeliveryOrderNo(goods.getDeliveryOrderNo()+"OSR");

            //设置入库时间
            goods.setInStorageTime(DateUtil.timestamp2String(new Date()));
            //入库
            goods.setGoodsType(1);
            insertGoods.add(goods);
            goodsNos.add(code);
        }
        goodsMapper.batchInsert(insertGoods);
        List<GoodsVo> goodsList = goodsMapper.findByGoodsNos(goodsNos);
        String goodsIds = "";
        for (Goods goods : goodsList) {
            goodsIds = StringUtils.isEmpty(goodsIds) ? String.valueOf(goods.getId()) : goodsIds + "," + goods.getId();
        }
        GoodsPackingVo goodsPackingVo = new GoodsPackingVo();
        goodsPackingVo.setAddressId(customerPackReceiverAddress.getAddressId());
        goodsPackingVo.setCustomerId(customerPack.getCustomerId());
        goodsPackingVo.setTransportRouteId(customerPack.getTransportationRouteId());
        goodsPackingVo.setGoodsIds(goodsIds);
        return applyPackService.goodsPacking(goodsPackingVo);
    }

    @Override
    public void printInvoice(Map<String, Object> requestMap, HttpServletRequest request, HttpServletResponse response) {
        log.info("请求参数: {}", requestMap);
        String invoiceType = requestMap.get("invoiceType").toString();
        if(StringUtils.isEmpty(invoiceType)){
            log.info("发票类型为空，请选择发票类型");
            return;
        }
        String orderIds = requestMap.get("orderIds").toString();
        List<Integer> orderIdList = JSONObject.parseArray(orderIds, Integer.class);
        List<CustomerPackVo> customerPackList = customerPackMapper.findPrintInvoiceCustomerPackByIds(orderIdList);
        String filePath;
        if("smallInvoice".equals(invoiceType)){
            // 德威小票申请报表打印
            filePath = smallInvoicePrint(customerPackList);
            try {
                FileUtil.downloadPostExcel("德威申请报表", filePath, request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            // 东际发票打印
            filePath = generalInvoicePrint(customerPackList);
            try {
                FileUtil.downloadPostExcel("东际发票", filePath, request, response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String generalInvoicePrint(List<CustomerPackVo> customerPackList) {
        // 创建一个新文件
        String templatePath = PdfUtils.getSystemPath()+"model/dongji-model.xls";
        File file = new File(templatePath);
        try {
            InputStream inputStream = new FileInputStream(file);
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);
            int sheetIndex = 1;
            for (CustomerPackVo customerPackVo : customerPackList) {
                RouteVo routeVo = customerPackVo.getRouteVo();
                if(1 == routeVo.getIsPerTicketPack() && customerPackVo.getCustomerPackNumbers().size() > 1){
                    for(CustomerPackNumberVo customerPackNumberVo : customerPackVo.getCustomerPackNumbers()){
                        HSSFSheet sheet = hssfWorkbook.cloneSheet(0);
                        hssfWorkbook.setSheetName(sheetIndex, customerPackNumberVo.getPackOrder());
                        generateExcel(sheet, customerPackVo, customerPackNumberVo.getPackOrder());
                        sheetIndex++;
                    }
                }else {
                    HSSFSheet sheet = hssfWorkbook.cloneSheet(0);
                    hssfWorkbook.setSheetName(sheetIndex, customerPackVo.getOrderNumber());
                    generateExcel(sheet, customerPackVo, customerPackVo.getOrderNumber());
                    sheetIndex++;
                }
            }

            hssfWorkbook.removeSheetAt(0);
            String filePath = LOCATION + "/export";
            String outPath = filePath + "/invoice_dongji_" + DateUtil.FormatDate(new Date(), "yyyyMMddHHmmss")+".xls";
            System.out.println("输出地址：" + outPath);
            File outFile = new File(filePath);
            if(!outFile.exists()){
                outFile.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(outPath);
            hssfWorkbook.write(fileOutputStream);
            fileOutputStream.close();
            hssfWorkbook.close();
            inputStream.close();

            return outPath;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private String smallInvoicePrint(List<CustomerPackVo> customerPackList) {
        // 创建一个新文件
        String templatePath = PdfUtils.getSystemPath()+"model/dowell-model.xls";
        File file = new File(templatePath);
        try {
            InputStream inputStream = new FileInputStream(file);
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = hssfWorkbook.getSheet("简易上传格式");
            int sheetRowIndex = 1;
            for (CustomerPackVo customerPackVo : customerPackList) {

                RouteVo routeVo = customerPackVo.getRouteVo();
                if(1 == routeVo.getIsPerTicketPack() && customerPackVo.getCustomerPackNumbers().size() > 1){
                    for(CustomerPackNumberVo customerPackNumberVo : customerPackVo.getCustomerPackNumbers()){
                        HSSFRow sheetRow = sheet.getRow(sheetRowIndex);
                        generateGeneralExcel(sheetRow, customerPackVo, true, customerPackNumberVo);
                        sheetRowIndex ++;
                    }
                }else {
                    HSSFRow sheetRow = sheet.getRow(sheetRowIndex);
                    generateGeneralExcel(sheetRow, customerPackVo, false, null);
                    sheetRowIndex ++;
                }
            }

            String filePath = LOCATION + "/export";
            String outPath = filePath + "/invoice_dowell_" + DateUtil.FormatDate(new Date(), "yyyyMMddHHmmss")+".xls";
            System.out.println("输出地址：" + outPath);
            File outFile = new File(filePath);
            if(!outFile.exists()){
                outFile.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(outPath);
            hssfWorkbook.write(fileOutputStream);
            fileOutputStream.close();
            hssfWorkbook.close();
            inputStream.close();

            return outPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void generateGeneralExcel(HSSFRow orderRow, CustomerPackVo customerPackVo,
                                     boolean isPerTicketPack, CustomerPackNumberVo customerPackNumberVo) {
        HSSFCell orderCell = orderRow.getCell(0);

        if(isPerTicketPack){
            orderCell.setCellValue(customerPackNumberVo.getPackOrder());
        }else {
            orderCell.setCellValue(customerPackVo.getOrderNumber());
        }

        orderCell = orderRow.getCell(1);
        orderCell.setCellValue(customerPackVo.getRouteVo().getRouteName());

        orderCell = orderRow.getCell(2);
        orderCell.setCellValue(customerPackVo.getRouteVo().getDestination());

        orderCell = orderRow.getCell(3);
        if(isPerTicketPack){
            orderCell.setCellValue(1);
        }else {
            orderCell.setCellValue(customerPackVo.getPackNum());
        }


        orderCell = orderRow.getCell(4);
        orderCell.setCellValue("");

        orderCell = orderRow.getCell(5);
        orderCell.setCellValue(customerPackVo.getAddressee());

        orderCell = orderRow.getCell(6);
        orderCell.setCellValue(customerPackVo.getProvinces());

        orderCell = orderRow.getCell(7);
        orderCell.setCellValue(customerPackVo.getProvinces());

        orderCell = orderRow.getCell(8);
        orderCell.setCellValue(customerPackVo.getAddress());

        orderCell = orderRow.getCell(9);
        orderCell.setCellValue("");

        orderCell = orderRow.getCell(10);
        orderCell.setCellValue(customerPackVo.getPhoneNumber());

        orderCell = orderRow.getCell(11);
        orderCell.setCellValue(customerPackVo.getCode());

        orderCell = orderRow.getCell(12); // 总重量
        double actualWeight = 0;;
        if(isPerTicketPack){
            actualWeight = customerPackNumberVo.getSettlementWeight() == null ?
                    customerPackNumberVo.getActualWeight() : customerPackNumberVo.getSettlementWeight();
        }else {
            actualWeight = customerPackVo.getSettlementWeight() == null ?
                    customerPackVo.getActualWeight() : customerPackVo.getSettlementWeight();

        }
        orderCell.setCellValue(actualWeight);

        orderCell = orderRow.getCell(13); // 产品名称
        String productName = "";
        double productNumber = 0;

        for (PackValuation packValuation : customerPackVo.getPackValuations()) {
            productName = StringUtils.isEmpty(productName) ? packValuation.getProductName() :
                    productName + "、" + packValuation.getProductName();
            productNumber = productNumber + packValuation.getNumber();
        }
        orderCell.setCellValue(StringUtils.isEmpty(productName) ? "" : productName);

        orderCell = orderRow.getCell(14); // 海关品名
        orderCell.setCellValue("");

        orderCell = orderRow.getCell(15); // 产品数量
        orderCell.setCellValue(productNumber);

        orderCell = orderRow.getCell(16); // 申报价格
        orderCell.setCellValue("");
    }

    private void generateExcel(HSSFSheet sheet, CustomerPackVo customerPackVo, String orderNumber) {

        // 设置唛头号/单号
        HSSFRow orderRow = sheet.getRow(5);
        HSSFCell orderCell1 = orderRow.getCell(1);
        orderCell1.setCellValue("");

        HSSFCell orderCell2 = orderRow.getCell(6);
        orderCell2.setCellValue(orderNumber);

        // 设置收货人
        HSSFRow addresseeRow = sheet.getRow(6);
        HSSFCell addresseeCell = addresseeRow.getCell(2);
        addresseeCell.setCellValue(customerPackVo.getAddressee());

        // 设置邮件地址
        HSSFRow emailRow = sheet.getRow(7);
        HSSFCell emailCell = emailRow.getCell(2);
        emailCell.setCellValue(customerPackVo.getEmail());

        // 设置联系电话
        HSSFRow phoneRow = sheet.getRow(8);
        HSSFCell phoneCell = phoneRow.getCell(2);
        phoneCell.setCellValue(customerPackVo.getPhoneNumber());

        // 收货人地址
        HSSFRow addressRow = sheet.getRow(9);
        HSSFCell addressCell = addressRow.getCell(2);
        addressCell.setCellValue(customerPackVo.getAddress());

        // 备注
        HSSFRow remarksRow = sheet.getRow(10);
        HSSFCell remarksCell = remarksRow.getCell(2);
        remarksCell.setCellValue("");


        int productValueRowNum = 12;
        int productValueCallIndex = 1;
        for (PackValuation packValuation : customerPackVo.getPackValuations()) {
            if(productValueCallIndex > 6){
                break;
            }
            HSSFRow productValueRow = sheet.getRow(productValueRowNum);

            HSSFCell productValueCall =  productValueRow.getCell(2);
            productValueCall.setCellValue(packValuation.getProductName());

            productValueCall =  productValueRow.getCell(5);
            productValueCall.setCellValue(packValuation.getNumber());

            productValueCall =  productValueRow.getCell(6);
            productValueCall.setCellValue(packValuation.getUnitPrice().doubleValue());

            productValueCall =  productValueRow.getCell(7);
            productValueCall.setCellValue(packValuation.getGoodsValue().doubleValue());

            productValueCallIndex ++ ;
            productValueRowNum ++;
        }
    }

    private ResultStatus calculatePackPrice(CustomerPackVo customerPack) {
        RouteVo routeVo = customerPack.getRouteVo();
        boolean isPerTicketPack = routeVo.getIsPerTicketPack() == 1;

        List<Integer> packIdList = new ArrayList<>();
        packIdList.add(customerPack.getId());
        List<CustomerPackNumber> customerPackNumbers = packNumberMapper.findByPackIdList(packIdList);
        CustomerPackPriceDetail priceDetail = customerPack.getPriceDetail();

        boolean isSensitive = false;
        // 如果价格明细中存在敏感货物费用的，则说明包裹中存敏感货物，需要加敏感货物费用
        if(priceDetail.getSensitivePrice() != null &&
                priceDetail.getSensitivePrice().compareTo(new BigDecimal(0)) > 0){
            isSensitive = true;
        }
        // 包裹实际重量和体积
        customerPack.setActualWeight(customerPackNumbers.stream().mapToDouble(CustomerPackNumber::getActualWeight).sum());
        customerPack.setActualVol(customerPackNumbers.stream().mapToDouble(CustomerPackNumber::getActualVol).sum());
        customerPack.setPackNum(customerPackNumbers.size());


        BigDecimal freight = new BigDecimal(0);
        BigDecimal sensitivePrice = new BigDecimal(0);
        BigDecimal forkliftFee = new BigDecimal(0);

        StringBuilder packRemarks;
        packRemarks = new StringBuilder(customerPack.getRemarks());

        if (isPerTicketPack) {
            double sonPackSettlementVol = 0;
            StringBuilder KVConversionRemarks = new StringBuilder("箱子：");

            for (CustomerPackNumber customerPackNumber : customerPackNumbers) {

                BigDecimal sonPackPrice = new BigDecimal(0);
                String remarks ;
                double settlementWeight = 0;
                double settlementVol = 0;
                // 一票一件渠道， 计算体积重
                // 判断是否 计算体积重
                boolean isCalculateVolWeight = routeVo.getSideLengthLimit() == null
                        || packUtils.checkIsCalculateVolWeight(routeVo.getSideLengthLimit(), customerPackNumber.getLength(),
                        customerPackNumber.getWidth(), customerPackNumber.getHeight());

                // 价格计算、结算重量、体积计算
                Map<String, Object> calculateMap =
                        packUtils.actualPriceCalculate(routeVo, customerPackNumber.getActualWeight(),
                                customerPackNumber.getActualVol(), isSensitive, settlementWeight, settlementVol,
                                sonPackPrice, isCalculateVolWeight);

                if (calculateMap.containsKey("errorMessage")) {
                    ROUTE_WEIGHT_OR_VOLUME_LIMIT_ERROR.setMessage(calculateMap.get("errorMessage").toString());
                    return ROUTE_WEIGHT_OR_VOLUME_LIMIT_ERROR;
                }

                // 子包裹结算重量
                customerPackNumber.setSettlementWeight(Double.valueOf(calculateMap.get("settlementWeight").toString()));
                customerPackNumber.setSettlementVol(Double.valueOf(calculateMap.get("settlementVol").toString()));

                sonPackPrice = new BigDecimal(Double.valueOf(calculateMap.get("packActualPrice").toString()));
                customerPackNumber.setActualPrice(sonPackPrice);
                remarks = calculateMap.get("remarks").toString();

                if (calculateMap.containsKey("KVremarks") && !calculateMap.get("KVremarks").toString().isEmpty()) {
                    KVConversionRemarks.append("【").
                            append(customerPackNumber.getPackOrder()).append("】").append(calculateMap.get("KVremarks").toString());

                }
                if (remarks != null && !remarks.isEmpty()) {
                    packRemarks.append(remarks);
                }

                // 子包裹结算体积，用于计算敏感货物
                sonPackSettlementVol += new BigDecimal(customerPackNumber.getSettlementVol()).setScale(4, BigDecimal.ROUND_UP).doubleValue();

                // 包裹的价格，累加子包裹的价格
                freight = freight.add(sonPackPrice);
                customerPackNumber.setActualPrice(sonPackPrice);

                if(calculateMap.containsKey("sensitivePrice")){
                    sensitivePrice = sensitivePrice.add(new BigDecimal(Double.valueOf(calculateMap.get("sensitivePrice").toString())));
                }

                if(calculateMap.containsKey("forkliftFee")){
                    forkliftFee = forkliftFee.add(new BigDecimal(Double.valueOf(calculateMap.get("forkliftFee").toString())));
                }

            }

            // 敏感费用备注
            if (isSensitive) {
                String sensitiveRemark = packUtils.sensitiveRemark(routeVo, sonPackSettlementVol, customerPack.getPackNum());
                packRemarks.append(sensitiveRemark);
            }

            // 包裹结算重量和体积
            customerPack.setSettlementWeight(customerPackNumbers.stream().
                    mapToDouble(CustomerPackNumber::getSettlementWeight).sum());
            customerPack.setSettlementVol(customerPackNumbers.stream().
                    mapToDouble(CustomerPackNumber::getSettlementVol).sum());


            if (!KVConversionRemarks.toString().equals("箱子：")) {
                packRemarks.append(KVConversionRemarks.toString());
            }
            customerPack.setRemarks(packRemarks.toString());
        } else {
            double packSettlementWeight = 0;
            double packSettlementVol = 0;
            BigDecimal packPrice = new BigDecimal(0);
            String remarks = "";

            // 价格计算、结算重量、体积计算  非一票一件默认计算体积重(比较的是总体的体积和总体的重量)
            Map<String, Object> calculateMap = packUtils.actualPriceCalculate(routeVo, customerPack.getActualWeight(),
                                                customerPack.getActualVol(), isSensitive, packSettlementWeight,
                                                packSettlementVol, packPrice, true);

            if (calculateMap.containsKey("errorMessage")) {
                ROUTE_WEIGHT_OR_VOLUME_LIMIT_ERROR.setMessage(calculateMap.get("errorMessage").toString());
                return ROUTE_WEIGHT_OR_VOLUME_LIMIT_ERROR;
            }

            customerPack.setSettlementWeight(Double.valueOf(calculateMap.get("settlementWeight").toString()));
            customerPack.setSettlementVol(Double.valueOf(calculateMap.get("settlementVol").toString()));
            customerPack.setActualPrice(new BigDecimal(Double.valueOf(calculateMap.get("packActualPrice").toString())));

            // 敏感货物加价
            if(calculateMap.containsKey("sensitivePrice")){
                sensitivePrice = sensitivePrice.add(new BigDecimal(Double.valueOf(calculateMap.get("sensitivePrice").toString())));
            }
            // 叉车费加价
            if(calculateMap.containsKey("forkliftFee")){
                forkliftFee = forkliftFee.add(new BigDecimal(Double.valueOf(calculateMap.get("forkliftFee").toString())));
            }
            if ( calculateMap.containsKey("KVremarks")
                    && !calculateMap.get("KVremarks").toString().equals("")) {
                packRemarks.append(calculateMap.get("KVremarks").toString());
            }

            remarks = calculateMap.get("remarks").toString();

            if (remarks != null && !remarks.isEmpty() ) {
                packRemarks.append(remarks);
            }

            // 敏感费用备注
            if (isSensitive) {
                if (routeVo.getSensitivePrice() != null &&
                        routeVo.getSensitivePrice().compareTo(new BigDecimal(0)) != 0) {
                    String sensitiveRemark = packUtils.sensitiveRemark(routeVo, customerPack.getSettlementVol(),
                                                                        customerPack.getPackNum());
                    packRemarks.append(sensitiveRemark);
                }
            }
            customerPack.setRemarks(packRemarks.toString());
            double packActualPrice = Double.valueOf(calculateMap.get("packActualPrice").toString());
            freight = new BigDecimal(packActualPrice);
        }


        customerPack.setActualPrice(freight);

        priceDetail.setFreight(freight);

        if(sensitivePrice.compareTo(new BigDecimal(0)) > 0){
            priceDetail.setSensitivePrice(sensitivePrice);
        }
        if(forkliftFee.compareTo(new BigDecimal(0)) > 0){
            priceDetail.setForkliftFee(forkliftFee);
        }

        packUtils.extraPriceCalculation(customerPack, priceDetail);

        // 更新主订单新
        customerPackMapper.updateByPrimaryKeySelective(customerPack);
        // 更新子订单信息
        packNumberMapper.updatePackNumberList(customerPackNumbers);
        // 更新价格明细
        customerPackPriceDetailMapper.updateByPrimaryKeySelective(priceDetail);
        return SUCCESS;
    }



    public ResponseEntity<ResultModel> otherSystemOrcUpdateOrder(String webMain, String body) {

        String url = webMain + "/api/customerPack/otherOrcUpdateOrder";
        HttpUtilManager httpUtilManager = HttpUtilManager.getInstance();
        String post = httpUtilManager.post(url, body);
        System.out.println("订单尺寸更新回传参数：" + post);
        JSONObject jsonObject = JSON.parseObject(post);
        ResponseEntity<ResultModel> responseEntity;
        String code = jsonObject.getString("code");
        String content = jsonObject.getString("content");
        String message = jsonObject.getString("message");
        JSONObject contentJSON = JSONObject.parseObject(content);
        if (code.equals(jsonObject.getString("content"))) {
            responseEntity = new ResponseEntity<>(ResultModel.ok(contentJSON), HttpStatus.OK);
        } else {
            OTHER_SYSTEM_ERROR.setCode(Integer.parseInt(code));
            OTHER_SYSTEM_ERROR.setMessage(message);
            responseEntity = new ResponseEntity<>(ResultModel.error(OTHER_SYSTEM_ERROR, contentJSON), HttpStatus.OK);
        }
        log.info("------>>>>> 调用结束 <<<<<------");
        return responseEntity;
    }



    private void updatePinOrder(CustomerPack customerPack) {
        PingMainOrder pingMainOrder = pingMainOrderMapper.selectByOrderNumber(customerPack.getOrderNumber());
        if (pingMainOrder == null) {
            return;
        }
        pingMainOrder.setPayTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
        pingMainOrder.setIsPaid(1);
        pingMainOrderMapper.updateByPrimaryKeySelective(pingMainOrder);// 先更新此订单的数据

        PingMainRespVo pingMainRespVo = pingMainMapper.selectPingMainDetailedById(pingMainOrder.getPId());
        for (PingMainOrder order : pingMainRespVo.getPingMainOrder()) {
            if (order.getIsPaid() != 1) {
                pingMainRespVo.setIsPaid(0);
                break;
            }
            pingMainRespVo.setIsPaid(1);
        }

        // 如果所有成员都支付了货款，则拼团主单状态变更为已付款
        if (pingMainRespVo.getIsPaid() == 1) {
            pingMainMapper.updateByPrimaryKeySelective(pingMainRespVo);
        }
    }


    // 拼团子订单数据更新操作
    private void pingMainOrderUpdate(CustomerPack customerPack, PingMainOrder pingMainOrder,
                                     List<CustomerPackNumberVo> customerPackNumberList) {
        packUtils.pinMainOrderSetting(customerPack, pingMainOrder);

        // 拼团子订单包裹更新
        pingMainOrderMapper.updateByPrimaryKeySelective(pingMainOrder);

        // 删除子订单箱子信息，防止重复数据
        pingPackMapper.deleteByOrderId(pingMainOrder.getId(), pingMainOrder.getCustomerId());

        // 重新插入拼团子订单的箱子
        List<PingPack> pingPackList = new ArrayList<>();
        packUtils.pinSonPackSetting(customerPackNumberList, pingPackList);
        pingPackList.forEach(pingPack -> {
            pingPack.setpId(pingMainOrder.getPId());
            pingPack.setPmId(pingMainOrder.getId());
            pingPack.setCustomerId(pingMainOrder.getCustomerId());
        });
        pingPackMapper.insertList(pingPackList);
//        现在不用做箱子和货物的关联关联，故屏蔽此段代码
//        List<PingPackRespVo> pingPackRespVoList = pingPackMapper.selectByPmIdAndCustomerId(pingMainOrder.getId(), pingMainOrder.getCustomerId());
//
//        List<PingPackGoods> insertPinPackGoods = new ArrayList<>();
//        customerPackNumberList.forEach(customerPackNumberVo -> {
//            pingPackRespVoList.forEach(pingPackRespVo -> {
//                if (pingPackRespVo.getPackOrder().equals(customerPackNumberVo.getPackOrder())) {
//                    customerPackNumberVo.getGoodsVoList().forEach(goodsVo -> {
//                        PingPackGoods pingPackGoods = new PingPackGoods();
//                        pingPackGoods.setpPackId(pingPackRespVo.getId());
//                        pingPackGoods.setGoodsId(goodsVo.getId());
//                        insertPinPackGoods.add(pingPackGoods);
//                    });
//
//                }
//            });
//        });
//
//        // 插入箱子货物关联表
//        pingPackGoodsMapper.insertList(insertPinPackGoods);
    }

    /**
     * 订单号生成
     *
     * @param customerId
     * @return
     */
    private String orderNumber(String customerId) {
        Long str = System.currentTimeMillis();
        String s = str.toString();
        /*while (customerId.length() < 4){
            // 如果客户id小于等于4位数，则自动在前面添加0
            customerId = "0" + customerId;
        }*/
        int round = (int) (Math.random() * 10);
        s = s.substring(2, s.length() - 3);
        // 订单号为：客户id + 时间戳截点 + 1位随机数
        return ORDER_TYPE + customerId + s + round;
    }

    /**
     * 订单注册转单号
     * @param orderNumber 订单号
     * @param transitNumber 转单号
     * @param request   请求域
     */
    private void customerPackRegisterTrack17(String orderNumber, String transitNumber, HttpServletRequest request) {
        log.info("------->>>>>转单号注册17轨迹网<<<<<------");
        List<Map> orderDataList = new ArrayList<>();
        Map<String, String> orderData = new HashMap<>();
        orderData.put("orderNumber", orderNumber);
        orderData.put("transitNumber", transitNumber);
        orderData.put("systemCode", SYSTEM_CODE);
        orderDataList.add(orderData);
        if("FLY".equals(SYSTEM_CODE)){
            trajectoryService.registerTrack17OrderData(JSONObject.toJSONString(orderDataList), request);
            System.out.println();
        }else{
            log.info("------->>>>>当前系统不是飞轮，转发到飞轮系统注册转单号<<<<<------");
            HttpUtilManager httpUtilManager = HttpUtilManager.getInstance();
            String registerUrl = "http://jiyun.flycloudstorage.com/api/AppDataService/otherSystemRegister";
//                String registerUrl = "http://www.lrqcloud.work/api/AppDataService/otherSystemRegister";
            JSONObject requestData = new JSONObject();
            requestData.put("orderData", orderDataList);
            String post = httpUtilManager.post(registerUrl, requestData.toJSONString());
            log.info("调用飞轮系统注册转单号结果：{}", post);
        }
    }
}
