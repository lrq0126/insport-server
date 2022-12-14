package com.example.warehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.common.*;
import com.example.warehouse.entity.*;
import com.example.warehouse.entity.image.ImageType;
import com.example.warehouse.entity.image.Images;
import com.example.warehouse.entity.shelves.GoodsShelvesRow;
import com.example.warehouse.entity.shelves.ShelvesGoodsCapacity;
import com.example.warehouse.entity.shelves.ShelvesRow;
import com.example.warehouse.entity.sys.LogOperation;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.log.SysLog;
import com.example.warehouse.enums.goods.GoodsStatus;
import com.example.warehouse.mapper.CustomerMapper;
import com.example.warehouse.mapper.GoodsMapper;
import com.example.warehouse.mapper.UserMapper;
import com.example.warehouse.mapper.WarehouseAddressMapper;
import com.example.warehouse.mapper.customerPack.CustomerPackMapper;
import com.example.warehouse.mapper.customerPackNumber.CustomerPackNumberMapper;
import com.example.warehouse.mapper.goods2pack.Goods2packMapper;
import com.example.warehouse.mapper.image.ImagesMapper;
import com.example.warehouse.mapper.shelves.GoodsShelvesRowMapper;
import com.example.warehouse.mapper.shelves.ShelvesAreaMapper;
import com.example.warehouse.mapper.shelves.ShelvesGoodsCapacityMapper;
import com.example.warehouse.mapper.shelves.ShelvesRowMapper;
import com.example.warehouse.mapper.sys.LogOperationMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.GoodsService;
import com.example.warehouse.service.detailedList.DetailedListService;
import com.example.warehouse.service.sys.SysNoticeService;
import com.example.warehouse.service.sys.SysTokenService;
import com.example.warehouse.service.wechat.SendMessageServer;
import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.applyPack.GoodsRequestVo;
import com.example.warehouse.vo.customer.CustomerPackNumberVo;
import com.example.warehouse.vo.customer.CustomerPackVo;
import com.example.warehouse.vo.detailedListVo.PrintOrderReqVo;
import com.example.warehouse.vo.goods.GoodsLocalhostVo;
import com.example.warehouse.vo.goods.GoodsReqVo;
import com.example.warehouse.vo.goods.OcrInWareSortingReqVo;
import com.example.warehouse.vo.goods.ShelvesReqVo;
import com.qcloud.cos.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.warehouse.enums.ResultStatus.*;
import static com.example.warehouse.enums.goods.GoodsStatus.*;

@Service("goodsService")
@Slf4j
@Transactional
public class GoodsServiceImpl implements GoodsService {

    // ?????????IP??????
    @Value("${server.domain}")
    private String DOMAIN;
    @Value("${upload.location}")
    private String UPLOAD_LOCATION;

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CustomerPackMapper customerPackMapper;
    @Autowired
    private Goods2packMapper goods2packMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private SysNoticeService sysNoticeService;

    @Autowired
    private SendMessageServer sendMessageServer;

    @Autowired
    private LogOperationMapper logOperationMapper;

    @Autowired
    private ShelvesRowMapper shelvesRowMapper;
    @Autowired
    private ShelvesAreaMapper shelvesAreaMapper;
    @Autowired
    private GoodsShelvesRowMapper goodsShelvesRowMapper;
    @Autowired
    private SysTokenService sysTokenService;
    @Autowired
    private ImagesMapper imagesMapper;
    @Autowired
    private WarehouseAddressMapper warehouseAddressMapper;
    @Autowired
    private ShelvesGoodsCapacityMapper shelvesGoodsCapacityMapper;

    // ??????????????????????????????
    @Value("${wechat.system_code}")
    private String SYSTEMCODE;

    @Override
    public ResponseEntity<ResultModel> selectGoodsInfoByDeliveryOrderNo(String deliveryOrderNo) {
        if (deliveryOrderNo == null || deliveryOrderNo.isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(ERROR, "??????????????????"), HttpStatus.OK);
        }
        // ??????????????????????????????????????????
        List<Goods> goodsList = goodsMapper.selectCountByDeliveryOrderNo(deliveryOrderNo);
        if (!CollectionUtils.isEmpty(goodsList) && goodsList.size() > 1) {
            for (Goods goods : goodsList) {
                if (goods.getLocation() == null || ",".equals(goods.getLocation())) {
                    goods.setLocation("?????????");
                } else if (goods.getLocation().split(",").length > 0) {
                    String area = goods.getLocation().split(",")[0];
                    String row = goods.getLocation().split(",")[1];
                    goods.setLocation(area + "???" + row + "???");
                }
            }
            return new ResponseEntity<>(ResultModel.error(THIS_GOODS_IS_REPEAT, goodsList), HttpStatus.OK);
        }

        Goods goods = goodsMapper.findWaitShelvesDeliveryOrderNo(deliveryOrderNo);

        if (goods == null) {
            return new ResponseEntity<>(ResultModel.error(ERROR, "?????????????????????"), HttpStatus.OK);
        }

        List<Map<String, Object>> goodsLocationList = new ArrayList<>();

        List<Map<String, Object>> goodsVoList = goodsMapper.selectGoodsLocationByCustomerNo(goods.getCustomerNo());

        if(goodsVoList.size() > 1){
            Goods inShelvesGoods = goodsMapper.findLatestInShelvesByCustomerNo(goods.getCustomerNo());

            if(inShelvesGoods != null){
                Map<String, Object> map = new HashMap<>();
                map.put("goodsSum", 1);
                map.put("location", inShelvesGoods.getLocation());
                goodsLocationList.add(map);
            }
        }
        if(!CollectionUtils.isEmpty(goodsVoList)){
            goodsLocationList.addAll(goodsVoList);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("goods", goods);
        resultMap.put("locations", goodsLocationList);

        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> updateShelves(ShelvesReqVo shelvesReqVo, HttpServletRequest request) {
        String userCode = request.getHeader("Authorization");
        if (userCode == null || userCode.isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }
        User user = userMapper.findByCode(userCode);
        if (user == null) {
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }
        if (shelvesReqVo.getStorageArea() == null || shelvesReqVo.getStorageArea().isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(LOCATION_NULL_ERROR), HttpStatus.OK);
        }
        if (shelvesReqVo.getStorageRow() == null || shelvesReqVo.getStorageRow().isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(LOCATION_NULL_ERROR), HttpStatus.OK);
        }
        if (shelvesReqVo.getDeliveryOrderNo() == null || shelvesReqVo.getDeliveryOrderNo().isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(GOODS_DELIVERY_ORDER_NO_ERROR), HttpStatus.OK);
        }

        ShelvesRow shelvesRow = shelvesRowMapper.selectAreaRow(shelvesReqVo.getStorageArea(), shelvesReqVo.getStorageRow());
        if(shelvesRow == null){
            return new ResponseEntity<>(ResultModel.error(SHELVES_AREA_ROW_NULL_ERROR), HttpStatus.OK);
        }

        Goods goods = goodsMapper.findWaitShelvesDeliveryOrderNo(shelvesReqVo.getDeliveryOrderNo());
        if (goods == null) {
            return new ResponseEntity<>(ResultModel.error(THIS_GOODS_NULL_ERROR), HttpStatus.OK);
        }

        if (goods.getGoodsType().equals(GoodsStatus.INTO_WARE.getStatus()) ||
                goods.getGoodsType().equals(GoodsStatus.WAIT_PACKING.getStatus())) {
            String location = shelvesReqVo.getStorageArea() + "," + shelvesReqVo.getStorageRow();
            goods.setLocation(location);
            goods.setUpTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
            goods.setUper(user.getUsername());
            int i = goodsMapper.updateShelvesById(goods);
            if (i == 1) {
                GoodsShelvesRow goodsShelvesRow = goodsShelvesRowMapper.selectByGoodsId(goods.getId());

                if(goodsShelvesRow == null){
                    goodsShelvesRow = new GoodsShelvesRow();
                }

                goodsShelvesRow.setIsPicking(2);
                goodsShelvesRow.setShelvesAreaId(shelvesRow.getParentId());
                goodsShelvesRow.setShelvesRowId(shelvesRow.getId());
                goodsShelvesRow.setGoodsId(goods.getId());

                if(goodsShelvesRow.getId() == null){
                    goodsShelvesRow.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
                    goodsShelvesRow.setCreateId(user.getId());
                    goodsShelvesRow.setVersion(1);
                    goodsShelvesRow.setStatus(1);
                    goodsShelvesRowMapper.insertSelective(goodsShelvesRow);

                }else{
                    goodsShelvesRow.setUpdateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
                    goodsShelvesRow.setUpdateId(user.getId());
                    goodsShelvesRowMapper.updateByPrimaryKeySelective(goodsShelvesRow);
                }
                return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(ResultModel.error(GOODS_TYPE_NOT_INTO_WARE_ERROR), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResultModel> getCustomerGoodsDetail(String deliveryOrderNo) {
        Goods goods = goodsMapper.findDeliveryOrderNo(deliveryOrderNo);
        if (goods == null) {
            return new ResponseEntity<>(ResultModel.error(THIS_GOODS_NULL_ERROR), HttpStatus.OK);
        }
        Customer customer = customerMapper.findByCustomerNo(goods.getCustomerNo());
        if (customer == null) {
            return new ResponseEntity<>(ResultModel.error(CUSTOMER_NULL_ERROR), HttpStatus.OK);
        }
        List<Goods> goodsList = goodsMapper.selectInShelvesCustomerGoods(customer.getCustomerNo());

        if (!CollectionUtils.isEmpty(goodsList)) {
            for (Goods g : goodsList) {
                if (StringUtils.isEmpty(g.getLocation()) || ",".equals(g.getLocation()) || "???".equals(g.getLocation())) {
                    g.setLocation("????????????");
                } else {
                    g.setLocation(g.getLocation().split(",")[0] + "???" + g.getLocation().split(",")[1] + "???");
                }
            }
        }

        goodsShelvesRowMapper.sortGoods(goods.getId());

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("customer", customer);
        resultMap.put("goods", goodsList);
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getCustomerGoods(String deliveryOrderNo, String customerNo) {
        Goods goods = goodsMapper.sortGoods(customerNo, deliveryOrderNo);
        if (goods != null) {
            return new ResponseEntity<>(ResultModel.ok(goods), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(CUSTOMER_GOODS_ERROR), HttpStatus.OK);
    }

    @Override
    public int judgeGoods(Goods goods, String type) {
        Map<String, Object> map = new HashMap<>();
        map.put("deliveryOrderNo", goods.getDeliveryOrderNo());
        if ("1".equals(type)) {
            goods.setGoodsType(1);
        } else {
            goods.setGoodsType(3);
        }
        List<GoodsVo> goodsVos = goodsMapper.findByDeliveryOrderNo(map);
        int i = 1;
        // ???????????????????????????????????????
        if (goodsVos.size() < 1) {
            return i;
        }
        for (GoodsVo goodsVo : goodsVos) {
            if (goodsVo.getGoodsType() == 2) {
                // ??????????????????
                // ???????????????  ??? ?????????
                if (goodsVo.getCustomerNo().equals(goods.getCustomerNo())) {
                    // ???????????????  ??? ????????????
                    i = -101;
                    break;
                }
            } else if (goodsVo.getGoodsType() == 3) {
                // ?????????????????????????????????????????? ??? ????????????????????????  ????????????
                if (goods.getGoodsType() == 3) {
                    i = -101;
                    break;
                }
                if (!goodsVo.getCustomerNo().equals(goods.getCustomerNo())) {
                    // ?????????????????? ????????????
                    i = -101;
                    break;
                }
            } else {
                // ???????????????????????????????????????  ??????  ????????????????????????????????????????????????????????????
                System.out.println("???????????????????????????????????????  ??????  ????????????????????????????????????????????????????????????");
                i = -101;
                break;
            }
        }
        System.out.println("------> i =" + i);
        return i;
    }

    @Override
    public int save(Goods goods, String type) {
        try {
            // ????????????????????????&??????
            goodsNameCheck(goods);
            // ???????????????????????????
            goods.setDeliveryOrderNo(goods.getDeliveryOrderNo().toUpperCase().replace(" ", ""));

            if (goods.getGoodsNo() == null || "".equals(goods.getGoodsNo())) {
                // ?????? GoodsNo ????????????????????????????????????
                String code = SequenceCode.gainSerialNo("GOODS");
                goods.setGoodsNo(code);
                Map<String, Object> map = new HashMap<>(2);
                map.put("deliveryOrderNo", goods.getDeliveryOrderNo());
                map.put("goodsType", 1);

                if ("1".equals(type)) {
                    // ????????????????????????????????????????????????????????????????????????
                    List<GoodsVo> goodsVos = goodsMapper.findByDeliveryOrderNo(map);
                    for (GoodsVo g : goodsVos) {
                        if (g.getCustomerNo().equals(goods.getCustomerNo())) {
                            return 100;
                        }
                    }
                    if (goods.getLength() != null && goods.getWidth() != null && goods.getHeight() != null) {
                        double length = goods.getLength();
                        double width = goods.getWidth();
                        double height = goods.getHeight();
                        // ????????????
                        goods.setVol(CalculationUtils.countVol(length, width, height));
                    }
                    //??????????????????
                    goods.setInStorageTime(DateUtil.timestamp2String(new Date()));
                    //??????
                    goods.setGoodsType(1);
                    // ?????????????????????
                    sendMessageServer.sendMessageForWareIn(goods);

                    // ?????????????????????????????????
                    map.put("goodsType", 3);
                    goodsVos = goodsMapper.findByDeliveryOrderNo(map);
                    if(!CollectionUtils.isEmpty(goodsVos)){
                        if(goodsVos.size() > 1){
                            return 102;
                        }else if (goodsVos.size() == 1){
                            goods.setGoodsName(goodsVos.get(0).getGoodsName());
                            goods.setGoodsNo(goodsVos.get(0).getGoodsNo());
                            return update(goods);
                        }
                    }
                    return goodsMapper.insert(goods);
                } else {
                    //?????????
                    // ???????????????????????????????????????????????????????????????????????????
                    map.put("goodsType", 3);
                    List<GoodsVo> goodsVos = goodsMapper.findByDeliveryOrderNo(map);
                    for (GoodsVo g : goodsVos) {
                        if (g.getCustomerNo().equals(goods.getCustomerNo())) {
                            return 101;
                        }
                    }
                    goods.setGoodsType(3);
                    return goodsMapper.insert(goods);
                }

            } else {
                // ??????????????????????????????????????????
                // ?????? goodsNo ???????????? ??????????????? ???????????????
                Goods reGoods = goodsMapper.findByCode(goods.getGoodsNo());
                reGoods.setGoodsName(goods.getGoodsName());
                reGoods.setLocation(goods.getLocation());

                reGoods.setKg(goods.getKg());

                reGoods.setPackageNum(goods.getPackageNum());
                reGoods.setPackageType(goods.getPackageType());
                reGoods.setMessage(goods.getMessage());
                if (goods.getLength() != null && goods.getWidth() != null && goods.getHeight() != null) {
                    double length = goods.getLength();
                    double width = goods.getWidth();
                    double height = goods.getHeight();
                    reGoods.setLength(length);
                    reGoods.setWidth(width);
                    reGoods.setHeight(height);
                    // ????????????
                    reGoods.setVol(CalculationUtils.countVol(length, width, height));
                }
                if (goods.getOperatorCode() != null) {
                    reGoods.setOperatorCode(goods.getOperatorCode());
                }
                if (goods.getOperatorName() != null) {
                    reGoods.setOperatorName(goods.getOperatorName());
                }
                reGoods.setInStorageTime(DateUtil.timestamp2String(new Date()));
                reGoods.setGoodsType(1);
                if (!"1".equals(type)) {
                    reGoods.setGoodsType(3);
                } else {
                    // ?????????????????????
                    sendMessageServer.sendMessageForWareIn(goods);
                }
                int resultInt = update(reGoods);
                if (resultInt < 1) {
                    return 0;
                }
//                Customer customer = customerMapper.findByCustomerNo(reGoods.getCustomerNo());
                // ?????????????????????
//                sysNoticeService.updateNoticeToCustomer(customer.getId().toString(), reGoods.getDeliveryOrderNo(), reGoods.getGoodsName(), "??????", "??????");
                return resultInt;
            }
        } catch (Exception e) {
            log.error("---????????????????????????{}---", e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(Goods goods) {
        Map<String, Object> map = new HashMap<>();
        map.put("goodsNo", goods.getGoodsNo());
        List<GoodsVo> goodsList = goodsMapper.find(map);
        // ????????????????????????&??????
        goodsNameCheck(goods);
        if (!goodsList.isEmpty()) {
            if (null != goodsList.get(0).getInStorageTime()) {
                //???????????????????????????
                goods.setInStorageTime(goodsList.get(0).getInStorageTime());
            }
            if (null != goodsList.get(0).getOutStorageTime()) {
                //???????????????????????????
                goods.setOutStorageTime(goodsList.get(0).getOutStorageTime());
            }
            goods.setVersion(goodsList.get(0).getVersion());
            if (goods.getGoodsType() == null || goods.getGoodsType() == 0) {
                goods.setGoodsType(goodsList.get(0).getGoodsType());
            }
            if (null != goodsList.get(0).getLocation()) {

            }
        }
        // ????????????
        if (goods.getLength() != null && goods.getWidth() != null && goods.getHeight() != null) {
            double length = goods.getLength();
            double width = goods.getWidth();
            double height = goods.getHeight();
            double d = CalculationUtils.countVol(length, width, height);
            goods.setVol(d);
        }

        return goodsMapper.updateByPrimaryKey(goods);
    }

    @Override
    public int del(String goodsNo, String usercode) {
        Goods goods = goodsMapper.findByCode(goodsNo);
        Goods2pack goods2pack = goods2packMapper.findByGoodsId(goods.getId());
        if (goods2pack != null) {
            CustomerPack customerPack = customerPackMapper.selectById(goods2pack.getPackId());
            if (customerPack != null) {
                return -100;
            }
        }
        return goodsMapper.del(goodsNo, usercode);
    }

    /**
     * ????????????
     *
     * @param goodsNo
     * @param usercode
     * @return
     */
    @Override
    public int batchDelete(String goodsNo, String usercode) {
        List<String> goodNosList = Arrays.asList(goodsNo.split(","));
        if (goodNosList != null) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("list", goodNosList);
            map.put("usercode", usercode);
            goodsMapper.batchDelete(map);
            return 1;
        }
        return 0;
    }

    @Override
    public ResponseEntity<PageResultModel> findAll(GoodsReqVo goodsReqVo) {

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
            goodsReqVo.setCommercialAreaId(user.getCommercialAreaId());
        }
        PageData data = PageHelp.editPage(goodsReqVo);

        int count = goodsMapper.countSelectAll(goodsReqVo);
        data.setTotal(count);
        Map<String, Object> result = new HashMap<>();
        result.put("page", data);
        List<GoodsVo> goodsVoList = new ArrayList<>();
        if (count > 0) {
            goodsReqVo.setPageNumber(data.getPageNumber());
            goodsVoList = goodsMapper.selectAll(goodsReqVo);
        }
        return new ResponseEntity<>(PageResultModel.ok(goodsVoList, result), HttpStatus.OK);
    }

//    @Override
//    public int countFindAll(Map<String, Object> map) {
//        return goodsMapper.countSelectAll(map);
//    }

    @Override
    public ResponseEntity<ResultModel> findAllRe(Map<String, Object> map) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }
        // ????????????????????????????????????????????????????????????
        if(user.getCommercialAreaId() != 1){
            map.put("commercialAreaId", user.getCommercialAreaId());
//            customerCouponsReqVo.setCommercialAreaId(user.getCommercialAreaId());
        }
        PageHelp.initPage(map);
        PageData data = (PageData) map.get("page");
        int count = goodsMapper.countSelectAllRe(map);
        data.setTotal(count);
        Map<String, Object> result = new HashMap<>();
        result.put("page", data);
        List<GoodsVo> goodsVoList = goodsMapper.selectAllRe(map);
        return new ResponseEntity<>(PageResultModel.ok(goodsVoList, result), HttpStatus.OK);
//        return ;
    }

    @Override
    public int countFindAllRe(Map<String, Object> map) {
        return goodsMapper.countSelectAllRe(map);
    }

    @Override
    public List<GoodsVo> find(Map<String, Object> map) {
        return goodsMapper.find(map);
    }

    @Override
    public List<GoodsVo> findByCustomerNo(String CustomerNo) {
        return goodsMapper.findByCustomerNo(CustomerNo);
    }


    /**
     * ????????????
     * / customerName --????????????   customer??? ?????????goods??????????????????
     * / phoneNumber --????????????    customer??? ?????????goods??????????????????
     * / deliveryName --????????????
     * / deliveryOrderNo --?????????
     * / operatorName --?????????     operator??? ?????????goods?????????????????????
     * / goodsType --????????????
     */
    @Override
    public List<GoodsVo> conditionQuery(Map<String, Object> map) {
        String customerName = null;
        String phoneNumber = null;
        String deliveryName = null;
        String deliveryOrderNo = null;
        String operatorName = null;
        String goodsType = null;
        if (map.get("customerName") != null) {
            customerName = map.get("customerName").toString();
        }
        if (map.get("phoneNumber") != null) {
            phoneNumber = map.get("phoneNumber").toString();
        }
        if (map.get("deliveryName") != null) {
            deliveryName = map.get("deliveryName").toString();
        }
        if (map.get("deliveryOrderNo") != null) {
            deliveryOrderNo = map.get("deliveryOrderNo").toString();
        }
        if (map.get("operatorName") != null) {
            operatorName = map.get("operatorName").toString();
        }
        if (map.get("goodsType") != null) {
            goodsType = map.get("goodsType").toString();
        }


        Map<String, Object> goodsMap = new HashMap<>();
        goodsMap.put("deliveryName", deliveryName);
        goodsMap.put("deliveryOrderNo", deliveryOrderNo);
        goodsMap.put("goodsType", goodsType);

        // ?????????????????????
        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("phoneNumber", phoneNumber);
        customerMap.put("customerName", customerName);
        List<Customer> customerList = customerMapper.find(customerMap);

        //???????????????
        User user = userMapper.login(operatorName);
        if (customerList.size() > 1) {
            List<GoodsVo> goodsList = new ArrayList<>();
            for (Customer customer : customerList) {
                goodsMap.put("customerNo", customer.getCustomerNo());
                if (user != null) {
                    goodsMap.put("usercode", user.getUsercode());
                }
                List<GoodsVo> goods = goodsMapper.find(goodsMap);
                for (GoodsVo goods1 : goods) {
                    goodsList.add(goods1);
                }
            }
            return goodsList;
        } else if (customerList.size() == 1) {
            Customer customer = customerList.get(0);
            goodsMap.put("customerNo", customer.getCustomerNo());
            if (user != null) {
                goodsMap.put("usercode", user.getUsercode());
            }
        } else {
            if (user != null) {
                goodsMap.put("usercode", user.getUsercode());
            }
        }
        return goodsMapper.find(goodsMap);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int outWarehouse(String goodsNo, String operatorName) {
        User user = userMapper.findByUserName(operatorName);
        String usercode = user.getUsercode();
        Goods goods = goodsMapper.findByCode(goodsNo);
        goods.setOperatorCode(usercode);
        goods.setOperatorName(operatorName);
        goods.setGoodsType(2);
        goods.setOutStorageTime(DateUtil.timestamp2String(new Date()));//??????????????????

        log.info("????????????????????????", goods);
        int i = 0;
        try {
            i = goodsMapper.outWarehouse(goods);
            Customer customer = customerMapper.findByCustomerNo(goods.getCustomerNo());
            // ?????????????????????
//            sysNoticeService.updateNoticeToCustomer(customer.getId().toString(), goods.getDeliveryOrderNo(), goods.getGoodsName(), "??????", "?????????????????????????????????????????????");
        } catch (Exception e) {
            log.error("???????????????{}", e.getMessage());
        }
        return i;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int inWarehouse(String goodsNo, String operatorName) {
        User user = userMapper.findByUserName(operatorName);
        String usercode = user.getUsercode();
        Goods goods = goodsMapper.findByCode(goodsNo);
        goods.setDeliveryOrderNo(goods.getDeliveryOrderNo().toUpperCase().replace(" ", ""));
        goods.setOperatorCode(usercode);
        goods.setOperatorName(operatorName);
        goods.setGoodsType(1);  //???????????????????????????
        //        goods.setLastUpdateTime(DateUtil.timestamp2String(new Date())); //??????????????????
        goods.setInStorageTime(DateUtil.timestamp2String(new Date()));//?????????????????????
        log.info("????????????????????????", goods.toString());
        // ????????????????????????&??????
        goodsNameCheck(goods);
        int i = goodsMapper.outWarehouse(goods);
        if (i < 1) {
            return 0;
        }
        return 1;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int outAllWarehouse(String goodNos, String operatorName) {
        User user = userMapper.findByUserName(operatorName);
        String usercode = user.getUsercode();
        String[] goodsNos1 = goodNos.split(",");
        Goods goods;
        for (String goodsNo : goodsNos1) {
            goods = goodsMapper.findByCode(goodsNo);
            goods.setOperatorCode(usercode);
            goods.setOperatorName(operatorName);
            goods.setOutStorageTime(DateUtil.timestamp2String(new Date()));//??????????????????
            goods.setGoodsType(2);
            int i = 0;
            try {
                i = goodsMapper.outWarehouse(goods);
                Customer customer = customerMapper.findByCustomerNo(goods.getCustomerNo());
                // ?????????????????????
//                sysNoticeService.updateNoticeToCustomer(customer.getId().toString(), goods.getDeliveryOrderNo(), goods.getGoodsName(), "??????", "?????????????????????????????????????????????");
            } catch (Exception e) {
                log.info("?????????????????????" + e.getMessage());
            }
            log.info("");
            if (i < 1) {
                return 0;
            }
        }
        return 1;
    }

    @Override
    public int scanOutWare(Map<String, Object> map) {
        int i = goodsMapper.findGoodsByNo(map);
        if (i == 0) {
            // ?????????????????????
            return 110;
        }
        int j = goodsMapper.scanOutWare(map);
        if (j > 0) {
            // ????????????
            return 100;
        }
        // ????????????
        return 120;
    }

    @Override
    public ResponseEntity<ResultModel> findByDeliveryOrderNo(String deliveryOrderNo) {

        // ??????????????????
        List<GoodsVo> goodsVoList = goodsMapper.getGoodsInWare(deliveryOrderNo);
        if (goodsVoList == null || goodsVoList.isEmpty()) {
            return new ResponseEntity<>(ResultModel.error(ERROR, "?????????????????????????????????"), HttpStatus.OK);
        }
//        List<Map<String, Object>> locationVoLis = goodsMapper.selectGoodsLocationByCustomerNo(goods.getCustomerNo());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("goods", goodsVoList);

//        List<GoodsLocalhostVo> goodsLocalhostVoList = getGoodsLocalhost(goodsVoList);
//        // ???????????????????????????
//        resultMap.put("goodsLocalhost", goodsLocalhostVoList);
//        resultMap.put("goodsLocalhost", locationVoLis);
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<ResultModel> getCustomerOrderLocation(String customerNo) {
        List<Map<String, Object>> goodsLocationList = new ArrayList<>();

        List<Map<String, Object>> goodsVoList = goodsMapper.selectGoodsLocationByCustomerNo(customerNo);

        if(!CollectionUtils.isEmpty(goodsVoList) && goodsVoList.size() > 1){
            Map<String, Object> lastInShareRowGoods = getLastGoodsInShareRow(customerNo);
            if(lastInShareRowGoods != null){
                goodsLocationList.add(lastInShareRowGoods);
            }
        }
        for (Map<String, Object> map : goodsVoList) {
            String location = String.valueOf(map.get("location"));
            if (StringUtils.isEmpty(location) || ",".equals(location) || "???".equals(location)) {
                map.put("location", "?????????");
            } else if (location.contains(",") && location.indexOf(",") > 0) {
                String area = location.split(",")[0];
                String row = location.split(",")[1];
                map.put("location", area + "???" + row + "???");
            } else if (location.contains("???") && location.indexOf("???") > 0) {
                String area = location.split("???")[0];
                String row = location.split("???")[1];
                map.put("location", area + "???" + row + "???");
            }
            goodsLocationList.add(map);
        }
        return new ResponseEntity<>(ResultModel.ok(goodsLocationList), HttpStatus.OK);
    }

    private Map<String,Object> getLastGoodsInShareRow(String customerNo) {
        Goods inShelvesGoods = goodsMapper.findLatestInShelvesByCustomerNo(customerNo);
        if(inShelvesGoods != null){
            Map<String, Object> inShelvesGoodsMap = new HashMap<>();
            String location = String.valueOf(inShelvesGoods.getLocation());
            if (StringUtils.isEmpty(location) || ",".equals(location) || "???".equals(location)) {
                inShelvesGoodsMap.put("location", "?????????");
            } else if (location.contains(",") && location.indexOf(",") > 0) {
                String area = location.split(",")[0];
                String row = location.split(",")[1];
                inShelvesGoodsMap.put("location", area + "???" + row + "???");
            } else if (location.contains("???") && location.indexOf("???") > 0) {
                String area = location.split("???")[0];
                String row = location.split("???")[1];
                inShelvesGoodsMap.put("location", area + "???" + row + "???");
            }
            inShelvesGoodsMap.put("goodsSum", "1");
            return inShelvesGoodsMap;
        }
        return null;
    }


    /**
     * ???????????????????????????????????????
     *
     * @param goodsVoList
     * @return
     */
    private List<GoodsLocalhostVo> getGoodsLocalhost(List<GoodsVo> goodsVoList) {
        // ???????????????????????????????????????
        GoodsRequestVo goodsRequestVo = new GoodsRequestVo();
        goodsRequestVo.setCustomerId(goodsVoList.get(0).getCustomerId());
        List<Goods> customerGoodsList = goodsMapper.selectInWareByCustomerId(goodsRequestVo.getCustomerId());

        List<String> localhostList = customerGoodsList.stream().map(Goods::getLocation).distinct().collect(Collectors.toList());
        List<GoodsLocalhostVo> goodsLocalhostVoList = new ArrayList<>();
        for (String localhost : localhostList) {
            if (localhost == null || localhost.isEmpty()) {
                continue;
            }
            GoodsLocalhostVo goodsLocalhostVo = new GoodsLocalhostVo();
            int goodsSum = 1;

            for (Goods goods : customerGoodsList) {
                if (goods.getLocation() == null || goods.getLocation().isEmpty()) {
                    continue;
                }
                if (localhost.equals(goods.getLocation())) {
                    goodsLocalhostVo.setUpTime(goods.getUpTime());
                    String newLocalhost;
                    // ????????????????????????
                    String[] localhosts;
                    if (localhost.contains(",") && !localhost.equals(",")) {
                        localhosts = localhost.split(",");
                        if (localhosts.length > 1) {
                            newLocalhost = localhosts[0] + "???" + localhosts[1] + "???";
                        } else {
                            newLocalhost = localhosts[0] + "???";
                        }
                    } else if (localhost.contains("???") && !localhost.equals("???")) {
                        localhosts = localhost.split("???");
                        if (localhosts.length > 1) {
                            newLocalhost = localhosts[0] + "???" + localhosts[1] + "???";
                        } else {
                            newLocalhost = localhosts[0] + "???";
                        }
                    } else {
                        newLocalhost = localhost + "???";
                    }

                    goodsLocalhostVo.setGoodsSum(goodsSum);
                    goodsLocalhostVo.setLocalhost(newLocalhost);
                    goodsSum++;
                }
            }
            goodsLocalhostVoList.add(goodsLocalhostVo);
        }

        // ???????????????????????????????????????????????????3????????????
        List<Integer> goodsSumList = goodsLocalhostVoList.stream().map(GoodsLocalhostVo::getGoodsSum).distinct().collect(Collectors.toList());
        for (int i = 0; i < goodsSumList.size(); i++) {
            for (int j = i + 1; j < goodsSumList.size(); j++) {
                if (goodsSumList.get(j) > goodsSumList.get(i)) {
                    int sum = goodsSumList.get(i);
                    goodsSumList.set(i, goodsSumList.get(j));
                    goodsSumList.set(j, sum);
                }
            }
        }

        List<GoodsLocalhostVo> goodsLocalhostVoList1 = new ArrayList<>();
        for (Integer sum : goodsSumList) {
            for (GoodsLocalhostVo goodsLocalhostVo : goodsLocalhostVoList) {
                if (goodsLocalhostVoList1.size() >= 3) {
                    break;
                }
                if (goodsLocalhostVo.getGoodsSum().equals(sum)) {
                    goodsLocalhostVoList1.add(goodsLocalhostVo);
                }
            }
        }

        return goodsLocalhostVoList1;

    }

    @Override
    public GoodsVo searchDeliveryOrderNo(String deliveryOrderNo) {
        Map<String, Object> map = new HashMap<>();
        map.put("deliveryOrderNo", deliveryOrderNo);
        map.put("goodsType", 3);
        return goodsMapper.findByDeliveryOrderNo(map).get(0);
    }

    @Override
    public Goods findById(int id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public int scanPicking(List<GoodsVo> goodsVoList, String deliverOrderNo) {
        int result = 0;
        for (GoodsVo goods : goodsVoList) {
            if (deliverOrderNo.equals(goods.getDeliveryOrderNo())) {
                result = goodsMapper.scanPicking(deliverOrderNo);
                break;
            }
        }
        return result;
    }

    @Override
    public List<GoodsVo> selectPack(Map<String, Object> paramMap) {
        // ????????????????????????id
        List<Integer> selectedGoodsId = new ArrayList<>();
        // ?????????????????????????????????
        List<GoodsVo> selectedGoodsVoList = new ArrayList<>();
        // ?????? ??????id
        int customerPackId = Integer.parseInt((String) paramMap.get("customerPackId"));
        // ???????????? ?????????id customerPackNumberId
        if (paramMap.containsKey("customerPackNumberId") && paramMap.get("customerPackNumberId") != null && !"".equals(paramMap.get("customerPackNumberId"))) {
            customerPackMapper.selectByPrimaryKey(customerPackId);
            List<CustomerPackNumberVo> customerPackNumberVoList = customerPackNumberMapper.findByPackId(customerPackId);
            List<Integer> SonCustomerPackIdList = new ArrayList<>();
            if (customerPackNumberVoList.size() > 0) {
                customerPackNumberVoList.forEach(customerPackNumberVo -> {
                    SonCustomerPackIdList.add(customerPackNumberVo.getId());
                });
            }
            // ???????????????????????? ????????????id
            selectedGoodsId = goods2packMapper.findGoodsIdBySonPackIdList(SonCustomerPackIdList);
            // ???????????????id
            int sonPackId = Integer.parseInt(paramMap.get("customerPackNumberId").toString());
            // ??????????????????????????????????????????
            selectedGoodsVoList = goodsMapper.selectGoodsVoBySonPackId(sonPackId);

        }

        // ????????????????????????
        List<GoodsVo> goodsVoList;
        if (selectedGoodsId.size() > 0) {
            goodsVoList = goodsMapper.selectGoodsByRemoveSelectedGoodsIds(customerPackId, selectedGoodsId);
        } else {
            goodsVoList = goodsMapper.selectGoodsVoByPackId(customerPackId);
        }

        // ?????????????????????
        List<GoodsVo> resultGoodsVoList = new ArrayList<>();

        // ???????????????????????????????????????????????????????????????
        if (selectedGoodsVoList.size() > 0) {
            resultGoodsVoList.addAll(selectedGoodsVoList);
        }
        resultGoodsVoList.addAll(goodsVoList);

        return resultGoodsVoList;
    }

    @Autowired
    private CustomerPackNumberMapper customerPackNumberMapper;

    /**
     * ?????????????????????
     *
     * @param paramMap
     * @return
     */
    @Override
    @Transactional
    public CustomerPackNumber inPacked(Map<String, Object> paramMap) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        def.setName("??????????????????");
        try {

            // ?????????????????????
            CustomerPackNumber customerPackNumber;
            String goodsNos = paramMap.get("goodsNos").toString();
            // ????????? GoodsNo??? ??????????????????
            List<String> selectGoodsNoList = Arrays.asList(goodsNos.split(","));
            List<GoodsVo> goodsVoList = goodsMapper.findByGoodsNos(selectGoodsNoList);

            if (selectGoodsNoList.size() > 0) {
                Goods2pack goods2pack = goods2packMapper.findByGoodsId(goodsVoList.get(0).getId());
                // ????????????????????????????????????
                List<Goods2pack> goods2packList = goods2packMapper.selectEntityByGoodsNoList(selectGoodsNoList);

                Integer packId = goods2pack.getPackId();
                if (paramMap.containsKey("customerPackNumberId") && !paramMap.get("customerPackNumberId").equals("")) {

                    int customerPackNumberId = Integer.parseInt(paramMap.get("customerPackNumberId").toString());
                    customerPackNumber = customerPackNumberMapper.findById(customerPackNumberId);
                    // ???????????????????????????????????????
                    List<Integer> selectedGoodsIdList = new ArrayList<>();

                    // ???????????????????????????????????????
                    goods2packList.forEach(goods2pack1 -> {
                        goods2pack1.setSonPackId(customerPackNumberId);
                        selectedGoodsIdList.add(goods2pack1.getGoodsId());
                    });

                    List<Integer> outPackGIds = goods2packMapper.findRemoveGoodsIds(customerPackNumberId, goods2packList.stream().map(Goods2pack::getGoodsId).collect(
                            Collectors.toList()));

                    // ??????????????????????????????????????????????????????
                    goods2packMapper.resetBySonPackId(customerPackNumberId);
                    // ???????????????????????????????????????????????????
                    goodsMapper.resetInPacked(selectedGoodsIdList);

                    if (!outPackGIds.isEmpty()) {
                        // ??????????????????????????????????????????
                        goodsMapper.resetInPacked(outPackGIds);
                        goods2packMapper.resetInPacked(outPackGIds);
                    }
                } else {
                    // ?????????????????????????????????
                    customerPackNumber = new CustomerPackNumber();
                    customerPackNumber.setGoodsNos(goodsNos);
                    customerPackNumber.setCustomerPackId(packId);
                    customerPackNumber.setStatus(1);
                    customerPackNumber.setCreateTime(new Date());
                    customerPackNumberMapper.insert(customerPackNumber);

                    // ???????????????????????????????????????
                    goods2packList.forEach(goods2pack1 -> goods2pack1.setSonPackId(customerPackNumber.getId()));
                }
                // ????????????????????????????????????
                goods2packMapper.updateGoods2PackList(goods2packList);

                // ?????????????????? ???????????? ??????
                goodsMapper.inPacked(selectGoodsNoList);
                transactionManager.commit(status);
                return customerPackNumber;
            } else {
                // ????????????????????????kong
                transactionManager.rollback(status);
                return null;
            }
        } catch (Exception e) {
            log.info("???????????????{}", e.getMessage());
            e.printStackTrace();
            transactionManager.rollback(status);
            return null;
        }

    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @param businessNumber
     */
    @Override
    public void initInPacked(String businessNumber) {
        CustomerPackVo customerPack = customerPackMapper.findByBusinessNumber(businessNumber);
        // ?????????????????? == 1??????????????????????????????????????????
        if (customerPack != null && customerPack.getPackType() == 1) {
            int packId = customerPack.getId();
            List<Goods2pack> goods2packList = goods2packMapper.findByPackId(packId);
            if (goods2packList.size() < 1) {
                return;
            }
            List<Integer> goodsIds = new ArrayList<>();
            goods2packList.forEach(goods2pack -> {
                goodsIds.add(goods2pack.getGoodsId());
            });
//            // ????????????????????? ??????????????????????????????????????????
//            goodsMapper.initInPacked(goodsIds);
            // ?????????????????????
            goodsMapper.initPicking(goodsIds);
            // ?????????????????????????????????
//            goods2packMapper.resetAllByPackId(packId);
            // ????????????????????????
//            customerPackNumberMapper.deleteByPackId(packId);
        }
    }

    @Override
    public List<GoodsVo> findGoodsByDeliveryOrderNo(String deliveryOrderNo) {
        Map<String, Object> map = new HashMap<>();
        map.put("deliveryOrderNo", deliveryOrderNo);
        return goodsMapper.findByDeliveryOrderNo(map);
    }

    @Override
    public int deletePacked(Map<String, Object> paramMap) {
        try {
            int sonPackId = Integer.parseInt(paramMap.get("customerPackNumberId").toString());
            // ???????????????????????????????????????????????????
            goodsMapper.initInPackedBySonPackId(sonPackId);
            goods2packMapper.initByCustomerPackNumberId(sonPackId);
            // ?????????????????????
            customerPackNumberMapper.deleteById(sonPackId);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public String getLocationByCustomerNo(String customerNo) {
        return goodsMapper.getLocationByCustomerNo(customerNo);
    }

    @Override
    public List<Goods> getGoodsBydeliveryOrderNo(String deliveryOrderNo) {
        return goodsMapper.getGoodsBydeliveryOrderNo(deliveryOrderNo);
    }

    @Override
    public int updateByPrimaryKey(Goods goods, User user) {
        goods.setUper(user.getUsername());
        // ????????????????????????&??????
        goodsNameCheck(goods);
        if (StringUtils.isNotBlank(goods.getLocation())) {
            if (goods.getLocation().startsWith("DW_") || goods.getLocation().startsWith("dw_")) {
                String location = goods.getLocation().substring(3);
                goods.setLocation(location.toUpperCase());
            }
        }

        return goodsMapper.updateShelvesById(goods);
    }

    @Override
    public int batchInsertPreOrder(List<Goods> list, User user) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        for (Goods g : list) {
            g.setCreateId(user.getId());
        }
        return goodsMapper.batchInsert(list);
    }

    @Override
    public ResponseEntity<ResultModel> findGoodsLocalhost(int customerId) {

        GoodsRequestVo goodsRequestVo = new GoodsRequestVo();
        goodsRequestVo.setCustomerId(customerId);
        List<GoodsVo> goodsVoList = goodsMapper.selectByVo(goodsRequestVo, INTO_WARE.getStatus());
        if (goodsVoList == null || goodsVoList.isEmpty()) {
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        List<GoodsLocalhostVo> goodsLocalhostVoList = getGoodsLocalhost(goodsVoList);

        return new ResponseEntity<>(ResultModel.ok(goodsLocalhostVoList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getGoodsByCustomerId(GoodsRequestVo goodsRequestVo) {

        if (goodsRequestVo.getCustomerId() == null) {
            return new ResponseEntity<>(ResultModel.error(ERROR, "?????????????????????"), HttpStatus.OK);
        }

        List<GoodsVo> goodsList = goodsMapper.selectByVo(goodsRequestVo, INTO_WARE.getStatus());

        return new ResponseEntity<>(ResultModel.ok(goodsList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> findByPackId(int packId) {
        List<Goods2pack> goods2packList = goods2packMapper.findByPackId(packId);
        if (goods2packList == null || goods2packList.isEmpty()) {
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        List<GoodsVo> goodsVoList = goodsMapper.selectByIds(goods2packList.stream().map(Goods2pack::getGoodsId).collect(Collectors.toList()));
        return new ResponseEntity<>(ResultModel.ok(goodsVoList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> abnormalInStorage(Goods goods) {
        // ????????????????????????&??????
        goodsNameCheck(goods);
        // ???????????????????????????
        goods.setDeliveryOrderNo(goods.getDeliveryOrderNo().toUpperCase().replace(" ", ""));
        // ?????? GoodsNo ????????????????????????????????????
        String code = SequenceCode.gainSerialNo("GOODS");
        goods.setGoodsNo(code);
        Customer customer = customerMapper.getCustomerByLoginName("000000");
        goods.setCustomerNo(customer.getCustomerNo());
        goods.setCustomerName(customer.getCustomerName());
        goods.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
        goods.setInStorageTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
        goods.setVersion(1);
        goods.setStatus(1);
        goods.setGoodsType(INTO_WARE.getStatus());
        if (goods.getLength() != null && goods.getWidth() != null && goods.getHeight() != null) {
            // ????????????
            goods.setVol(CalculationUtils.countVol(goods.getLength(), goods.getWidth(), goods.getHeight()));
        }

        int i = goodsMapper.insert(goods);
        if (i > 0) {
            LogOperation logOperation = new LogOperation();
            logOperation.setReqType("POST");
            logOperation.setOpertorName(SYSTEMCODE + "-" + goods.getOperatorName());
            logOperation.setContentType(SysLog.Type.ADD);
            logOperation.setContent("???????????????[????????????]");
            logOperation.setContentId(goods.getDeliveryOrderNo());
            logOperation.setReqParam(goods.toString());
            logOperation.setStatus(1);
            logOperation.setCreateTime(DateUtil.timestamp2String(new Date()));
            logOperation.setRespCode("100");
            logOperation.setRespMessage("??????");
            logOperationMapper.insert(logOperation);
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> warehouseSorting(Map<String, Object> params) {

        String location = "";
        String tempA = "";
        String tempB = "";
        if (params.containsKey("storageArea")) {
            tempA = params.get("storageArea").toString() + ",";
        }
        if (params.containsKey("storageRow")) {
            tempB = params.get("storageRow").toString();
        }
        location = tempA + tempB;
        log.info("?????????????????????{}", location);
        try {
            String str = JSON.toJSON(params).toString();
            Goods newGoods = JSON.parseObject(str, Goods.class);
            //????????????????????????
            newGoods.setLocation(location);

            int i = this.save(newGoods, params.get("type").toString());
            if (i < 1) {
                return new ResponseEntity<>(new ResultModel(ERROR), HttpStatus.OK);
            }
            if (100 == i) {// ??????????????????
                return new ResponseEntity<>(new ResultModel(GOODS_HAVE_IN_ERROR), HttpStatus.OK);
            }
            if (101 == i) { // ?????????????????????RE
                return new ResponseEntity<>(new ResultModel(GOODS_HAVE_IN_ERROR), HttpStatus.OK);
            }
//            SYSTEMCODE
            LogOperation logOperation = new LogOperation();
            logOperation.setReqType("POST");
            logOperation.setOpertorName(SYSTEMCODE + "-" + String.valueOf(params.get("operatorName")));
            logOperation.setContentType(SysLog.Type.ADD);
            logOperation.setContent("????????????[????????????]");
            logOperation.setContentId(String.valueOf(params.get("deliveryOrderNo")));
            logOperation.setReqParam(params.toString());
            logOperation.setStatus(1);
            logOperation.setCreateTime(DateUtil.timestamp2String(new Date()));
            logOperation.setRespCode("100");
            logOperation.setRespMessage("??????");
            logOperationMapper.insert(logOperation);
            log.info("????????????-------------<<<<<<<<<<<<<<<<<<");
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("????????????-------------,?????????{}", e.getMessage());
            return new ResponseEntity<>(new ResultModel(ERROR, e.getMessage()), HttpStatus.OK);
        }
    }


    @Override
    public ResponseEntity<ResultModel> ocrInWareSorting(OcrInWareSortingReqVo ocrData, HttpServletRequest request) {
        log.info("OCR?????????????????????{}", ocrData);
        String token = request.getHeader("jiyun-token");
        if (StringUtils.isEmpty(token)) {
            return new ResponseEntity<>(ResultModel.error(SYS_TOKEN_OUT_OF_DATE_ERROR), HttpStatus.OK);
        }
        boolean checkResult = sysTokenService.checkToken(token);
        if (!checkResult) {
            return new ResponseEntity<>(ResultModel.error(SYS_TOKEN_OUT_OF_DATE_ERROR), HttpStatus.OK);
        }
        if (!"??????".equals(ocrData.getSystemName())) {
            System.out.println("??????????????????");
            String api = "/api/goods/otherSystemOcrInWareSorting";
            switch (ocrData.getSystemName()) {
                case "??????":
                    log.info("------>>>>> ????????????????????????????????? <<<<<------");
                    return otherSystemOrcInWareSorting("http://jiyun.huawei138.cn"+api, ocrData);
                case "??????":
                    log.info("------>>>>> ????????????????????????????????? <<<<<------");
                    return otherSystemOrcInWareSorting("http://gzdw.gdjiyun.com"+api, ocrData);
                case "??????":
                    log.info("------>>>>> ????????????????????????????????? <<<<<------");
                    return otherSystemOrcInWareSorting("http://huayou.flycloudstorage.com"+api, ocrData);
                case "??????":
                    log.info("------>>>>> ????????????????????????????????? <<<<<------");
                    return otherSystemOrcInWareSorting("http://huasu.huawei138.cn"+api, ocrData);
                case "?????????":
                    log.info("------>>>>> ???????????????????????????????????????????????? <<<<<------");
                    return otherSystemOrcInWareSorting("http://huasu.huawei138.cn"+api, ocrData);
                case "FLY":
                    log.info("------>>>>> ????????????????????????????????? <<<<<------");
                    return otherSystemOrcInWareSorting("http://www.lrqcloud.work"+api, ocrData);
            }
//            ShelvesGoodsCapacity shelvesAreaRow = shelvesAreaMapper.selectAbnormalArea();
            log.info("------>>>>> ????????????????????????????????????????????? <<<<<------");
            String requestUrl = "http://gzdw.gdjiyun.com/api/goods/ocrAbnormalInWareSorting";
//                    String requestUrl = "http://www.lrqcloud.work/api/goods/ocrAbnormalInWareSorting";
            ocrData.setMessage("???????????????????????????????????????");
            otherSystemOrcInWareSorting(requestUrl, ocrData);

            OTHER_SYSTEM_ERROR.setCode(6003);
            OTHER_SYSTEM_ERROR.setMessage("???????????????????????????????????????");
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("shelvesArea", "");
            resultMap.put("shelvesRow", "");
            resultMap.put("sortingExport", "H");
            resultMap.put("location", "");
            return new ResponseEntity<>(ResultModel.error(OTHER_SYSTEM_ERROR, resultMap), HttpStatus.OK);
        }

        return inWareSorting(ocrData);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResultModel> inWareSorting(OcrInWareSortingReqVo ocrData) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("shelvesArea", "");
        resultMap.put("shelvesRow", "");
        resultMap.put("sortingExport", "H");
        resultMap.put("location", "");
        try {
            String deliveryOrderNo =
                    ocrData.getDeliveryOrderNo().toUpperCase().replace(" ", "");
            String systemName = ocrData.getSystemName();
            String loginName = ocrData.getLoginName();
            if(StringUtils.isEmpty(deliveryOrderNo) || StringUtils.isEmpty(systemName)
                    || StringUtils.isEmpty(loginName)){
                ocrData.setMessage("???????????????/????????????/???????????????????????????");
                abnormalInWareSorting(ocrData);

                // ?????????????????????
//                if("TAOMAO".equals(SYSTEMCODE)){
//                    abnormalInWareSorting(ocrData);
//                }else {
//                    log.info("------>>>>> ????????????????????????????????????????????? <<<<<------");
//                    String requestUrl = "http://gzdw.gdjiyun.com/api/goods/ocrAbnormalInWareSorting";
////                    String requestUrl = "http://www.lrqcloud.work/api/goods/ocrAbnormalInWareSorting";
//                    otherSystemOrcInWareSorting(requestUrl, ocrData);
//                }
                OTHER_SYSTEM_ERROR.setCode(6003);
                OTHER_SYSTEM_ERROR.setMessage("??????????????????/????????????/???????????????????????????");
                return new ResponseEntity<>(ResultModel.error(OTHER_SYSTEM_ERROR, resultMap), HttpStatus.OK);
            }

            // ?????????????????????????????????
            Goods goods = goodsMapper.findReInWareByDeliveryOrderNo(deliveryOrderNo);

            Customer customer = customerMapper.getCustomerByLoginName(loginName);
            if("510000".equals(loginName) || "510440".equals(loginName) || "000000".equals(loginName) || customer == null){
                ShelvesGoodsCapacity shelvesAreaRow = shelvesAreaMapper.selectAbnormalArea();
                if (shelvesAreaRow != null) {
                    resultMap.put("shelvesArea", shelvesAreaRow.getShelvesArea());
                    resultMap.put("sortingExport", shelvesAreaRow.getSortingExport());
                }
                ocrData.setMessage("???????????????/??????????????????");
                abnormalInWareSorting(ocrData);
                // ?????????????????????
//                if("TAOMAO".equals(SYSTEMCODE)){
//                    abnormalInWareSorting(ocrData);
//                }else {
//                    log.info("------>>>>> ????????????????????????????????????????????? <<<<<------");
//                    String requestUrl = "http://gzdw.gdjiyun.com/api/goods/ocrAbnormalInWareSorting";
////                    String requestUrl = "http://www.lrqcloud.work/api/goods/ocrAbnormalInWareSorting";
//                    otherSystemOrcInWareSorting(requestUrl, ocrData);
//                }
                OTHER_SYSTEM_ERROR.setCode(6002);
                OTHER_SYSTEM_ERROR.setMessage("?????????????????????????????????");
                return new ResponseEntity<>(ResultModel.error(OTHER_SYSTEM_ERROR, resultMap), HttpStatus.OK);
            }
            if (goods == null) {
                goods = new Goods();
                goods.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));

                goods.setStatus(1);
                goods.setVersion(1);
                goods.setGoodsName("?????????????????????");
                goods.setDeliveryOrderNo(deliveryOrderNo);
                goods.setCustomerNo(customer.getCustomerNo());
                goods.setCustomerName(customer.getCustomerName());

                // ????????????????????????????????????
                List<Goods> goodsList = goodsMapper.selectCountByDeliveryOrderNo(deliveryOrderNo);
                // ???????????????????????????????????????????????????????????????????????????
                if (goodsList.size() > 0) {
                    goods = goodsList.get(0);
                }
            }else if("CUM2022010100000000000".equals(goods.getCustomerNo())){
                // ????????????????????????????????????????????????????????????????????????????????????????????????????????????
                goods.setCustomerNo(customer.getCustomerNo());
                goods.setCustomerName(customer.getCustomerName());
            }

            double weight = ocrData.getWeight() == null ? 0.1 : ocrData.getWeight();
            goods.setKg(weight);
            double length = ocrData.getLength() == null ? 1.0 : ocrData.getLength()/10;
            double width = ocrData.getWidth() == null ? 1.0 : ocrData.getWidth()/10;
            double height = ocrData.getHeight() == null ? 1.0 : ocrData.getHeight()/10;
            goods.setLength(length);
            goods.setWidth(width);
            goods.setHeight(height);
            // ????????????
            goods.setVol(CalculationUtils.countVol(length, width, height));

            goods.setInStorageTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
            goods.setOperatorCode("orcSorting");
            goods.setOperatorName("orcSorting");

            goods.setPackageType(StringUtils.isEmpty(ocrData.getPackType()) ? "????????????" : ocrData.getPackType());
            goods.setPackageNum(1);
            goods.setLocation(",");

            if (goods.getId() == null) {
                goods.setGoodsType(GoodsStatus.INTO_WARE.getStatus());
                goods.setGoodsNo(SequenceCode.gainSerialNo("GOODS"));
                goodsMapper.insert(goods);
                // ?????????????????????
                if(!StringUtils.isEmpty(customer.getOpenid())){
                    sendMessageServer.sendMessageForWareIn(goods);
                }
                // ??????????????????
                LogOperation logOperation = new LogOperation();
                logOperation.setReqType("POST");
                logOperation.setOpertorName(goods.getOperatorName());
                logOperation.setContentType(SysLog.Type.ADD);
                logOperation.setContent("OCR??????[????????????]");
                logOperation.setContentId(goods.getDeliveryOrderNo());
                logOperation.setReqParam(goods.toString());
                logOperation.setStatus(1);
                logOperation.setCreateTime(DateUtil.timestamp2String(new Date()));
                logOperation.setRespCode("100");
                logOperation.setRespMessage("??????");
                logOperationMapper.insert(logOperation);
            } else if(goods.getGoodsType() == 3){
                goods.setGoodsType(GoodsStatus.INTO_WARE.getStatus());
                goodsMapper.updateByPrimaryKey(goods);
                // ?????????????????????
                if(!StringUtils.isEmpty(customer.getOpenid())){
                    sendMessageServer.sendMessageForWareIn(goods);
                }
                // ??????????????????
                LogOperation logOperation = new LogOperation();
                logOperation.setReqType("POST");
                logOperation.setOpertorName(goods.getOperatorName());
                logOperation.setContentType(SysLog.Type.ADD);
                logOperation.setContent("OCR??????[????????????]");
                logOperation.setContentId(goods.getDeliveryOrderNo());
                logOperation.setReqParam(goods.toString());
                logOperation.setStatus(1);
                logOperation.setCreateTime(DateUtil.timestamp2String(new Date()));
                logOperation.setRespCode("100");
                logOperation.setRespMessage("??????");
                logOperationMapper.insert(logOperation);
            }

            ShelvesGoodsCapacity shelvesAreaRow;
            // ????????????????????????60KG???????????????60????????????
            if (weight > 60 || length > 60 || width > 60 || height > 60) {
                shelvesAreaRow = shelvesAreaMapper.selectBigArea();
                if (shelvesAreaRow == null) {
                    shelvesAreaRow = shelvesGoodsCapacityMapper.getLatestShelvesAreaRow();
                }
            } else {

//                List<Map<String, Object>> goodsVoList = goodsMapper.selectGoodsLocationByCustomerNo(customer.getCustomerNo());

                // ????????????????????????????????????????????????
                Goods inShelvesGoods = goodsMapper.findLatestInShelvesByCustomerNo(goods.getCustomerNo());

                // ????????????????????????????????????????????????????????????
                if (inShelvesGoods == null) {
                    shelvesAreaRow = shelvesGoodsCapacityMapper.getLatestShelvesAreaRow();
                } else {
                    // ?????????????????????????????????????????????????????????
                    shelvesAreaRow = shelvesRowMapper.selectGoodsShelvesRow(inShelvesGoods.getId());
                    if (shelvesAreaRow == null) {
                        shelvesAreaRow = shelvesGoodsCapacityMapper.getLatestShelvesAreaRow();
                    }
                }
            }

            // ????????????????????????????????????????????????A??????
            if (shelvesAreaRow != null) {
                String row = StringUtils.isEmpty(shelvesAreaRow.getShelvesRow()) ? "-" : shelvesAreaRow.getShelvesRow();
                resultMap.put("shelvesArea", shelvesAreaRow.getShelvesArea());
                resultMap.put("shelvesRow", shelvesAreaRow.getShelvesRow());
                resultMap.put("sortingExport", shelvesAreaRow.getSortingExport());
                resultMap.put("location", shelvesAreaRow.getShelvesArea() + "???" + row + "???");
            }
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            ShelvesGoodsCapacity shelvesAreaRow = shelvesAreaMapper.selectAbnormalArea();
            if (shelvesAreaRow != null) {
                resultMap.put("shelvesArea", shelvesAreaRow.getShelvesArea());
                resultMap.put("shelvesRow", shelvesAreaRow.getShelvesRow());
                resultMap.put("sortingExport", shelvesAreaRow.getSortingExport());
            }
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR, resultMap), HttpStatus.OK);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResultModel> abnormalInWareSorting(OcrInWareSortingReqVo ocrData) {
        log.info("--------->>>>OCR???????????????<<<<---------");
        try {
            // ???????????????
            Goods goods = goodsMapper.findReInWareByDeliveryOrderNo(ocrData.getDeliveryOrderNo());
            if(goods == null){
                goods = new Goods();
                goods.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
                String code = SequenceCode.gainSerialNo("GOODS");
                goods.setGoodsNo(code);
                goods.setStatus(1);
                goods.setVersion(1);

            }
            goods.setInStorageTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));

            // ???????????????????????????
            goods.setDeliveryOrderNo(ocrData.getDeliveryOrderNo().toUpperCase().replace(" ", ""));

            Customer customer = customerMapper.getCustomerByLoginName("000000");
            goods.setGoodsName("?????????????????????");
            goods.setCustomerNo(customer.getCustomerNo());
            goods.setCustomerName(customer.getCustomerName());

            double weight = ocrData.getWeight();
            goods.setKg(weight);
            double length = ocrData.getLength() == null ? 0.0 : ocrData.getLength()/10;
            double width = ocrData.getWidth() == null ? 0.0 : ocrData.getWidth()/10;
            double height = ocrData.getHeight() == null ? 0.0 : ocrData.getHeight()/10;
            goods.setLength(length);
            goods.setWidth(width);
            goods.setHeight(height);
            // ????????????
            goods.setVol(CalculationUtils.countVol(length, width, height));

            goods.setOperatorCode("orcSorting");
            goods.setOperatorName("orcSorting");

            // ??????????????????????????????
            goods.setGoodsType(GoodsStatus.INTO_WARE.getStatus());
            goods.setPackageType(StringUtils.isEmpty(ocrData.getPackType()) ? "????????????" : ocrData.getPackType());
            goods.setPackageNum(1);
            goods.setLocation(",");
//            goods.setMessage(ocrData.getMessage());

            if(goods.getId() != null){
                goodsMapper.updateByPrimaryKey(goods);
            }else {
                goodsMapper.insert(goods);
            }

            LogOperation logOperation = new LogOperation();
            logOperation.setReqType("POST");
            logOperation.setOpertorName(SYSTEMCODE + "-" + goods.getOperatorName());
            logOperation.setContentType(SysLog.Type.ADD);
            logOperation.setContent("OCR???????????????[????????????]");
            logOperation.setContentId(goods.getDeliveryOrderNo());
            logOperation.setReqParam(goods.toString());
            logOperation.setStatus(1);
            logOperation.setCreateTime(DateUtil.timestamp2String(new Date()));
            logOperation.setRespCode("100");
            logOperation.setRespMessage("??????");
            logOperationMapper.insert(logOperation);

            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResultModel> ocrInWareSortingPicture(String deliveryOrderNo, String systemName, MultipartFile file) {

        if(file == null || (file.getSize() <= 0)){
            FILE_DATA_ERROR.setMessage("??????????????????????????????");
            return new ResponseEntity<>(ResultModel.error(FILE_DATA_ERROR), HttpStatus.OK);
        }

        try {
            if(StringUtils.isEmpty(systemName)){
                systemName = "????????????";
            }
            switch (systemName){
                case "??????":
                    systemName = "flycloud";
                    break;
                case "??????":
                    systemName = "taomao";
                    break;
                case "??????":
                    systemName = "huawei";
                    break;
                case "??????":
                    systemName = "huayou";
                    break;
                case "??????":
                    systemName = "huasu";
                    break;
                case "?????????":
                    systemName = "huasu";
                    break;
                default:
                    systemName = "nuknow";
                    break;
            }

            // systemName ?????????????????????????????????????????????????????????
            Images images = TencentObjectMemory.uploadObject(systemName, ImageType.GoodsInWarePicture.toString(), file);
            
            images.setContentId(deliveryOrderNo);
            imagesMapper.insertSelective(images);

            OTHER_SYSTEM_ERROR.setCode(200);
            OTHER_SYSTEM_ERROR.setMessage("????????????????????????");
            return new ResponseEntity<>(ResultModel.error(OTHER_SYSTEM_ERROR), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            OTHER_SYSTEM_ERROR.setCode(201);
            OTHER_SYSTEM_ERROR.setMessage("????????????????????????");
            return new ResponseEntity<>(ResultModel.error(OTHER_SYSTEM_ERROR), HttpStatus.OK);
        }
    }

    @Autowired
    private DetailedListService detailedListService;

    @Override
    public ResponseEntity<ResultModel> printDeliveryOrder(String deliveryOrderNo, String loginName) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }
        Integer commercialAreaId = user.getCommercialAreaId();

        PrintOrderReqVo printOrderReqVo = new PrintOrderReqVo();
        printOrderReqVo.setNumber(1);
        printOrderReqVo.setOrderNumber(deliveryOrderNo);
        String addressee;
        String address;
        WarehouseAddress isEnableAddress = warehouseAddressMapper.getIsEnableAddress(commercialAreaId);

        if(isEnableAddress != null){
            addressee = isEnableAddress.getAddressee().replace("LOGIN_NAME", loginName);
            address = isEnableAddress.getAddress().replace("LOGIN_NAME", loginName);
        } else {
            addressee = loginName;
            address = "";
        }
        printOrderReqVo.setAddressee(addressee);
        printOrderReqVo.setAddress(address);
        return detailedListService.orderNumberPrint(printOrderReqVo);
    }

    private ResponseEntity<ResultModel> otherSystemOrcInWareSorting(String requestUrl, OcrInWareSortingReqVo ocrData) {

        HttpUtilManager httpUtilManager = HttpUtilManager.getInstance();

        String requestJSON = JSONObject.toJSONString(ocrData);
        String post = httpUtilManager.post(requestUrl, requestJSON);
        System.out.println("???????????????????????????" + post);
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
        log.info("------>>>>> ???????????? <<<<<------");
        return responseEntity;
    }

    private void goodsNameCheck(Goods goods) {
        if (goods.getGoodsName() == null || goods.getGoodsName().isEmpty()) {
            return;
        }
        if (goods.getGoodsName().contains("&")) {
            String goodsNames[] = goods.getGoodsName().split("&");
            StringBuilder goodsName = new StringBuilder();
            for (String str : goodsNames) {
                goodsName.append(str);
            }
            goods.setGoodsName(goodsName.toString());
        }
    }


    /**
     * @param imageFile   ????????????
     * @param outPath     ????????????????????????
     * @param outFileName ??????????????????
     */
    private void pictureZip(MultipartFile imageFile, String outPath, String outFileName) {

        // ????????????????????????.png?????????
        if (outFileName.contains(".png")) {
            outFileName = outFileName.replace(".png", ".jpg");
        }

        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        // ????????????????????????
        String outPathFile = outPath + "/" + outFileName;

        File file = new File(outPathFile);
        if(file.exists()){
            file.delete();
        }
        try {
            inputStream = imageFile.getInputStream();
            fileOutputStream = new FileOutputStream(outPathFile);

            IOUtils.copyLarge(inputStream, fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        long size = imageFile.getSize();
        double scale = 1.0d;
        // ??????
        if (size > 300 * 1024) {
            scale = (300 * 1024f) / size;
        }
        try {
            if (size > 300 * 1024) {
                Thumbnails.of(outPathFile).scale(1f).outputQuality(scale).outputFormat("jpg").toFile(outPathFile);
            } else {
                Thumbnails.of(outPathFile).scale(1f).outputFormat("jpg").toFile(outPathFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        String systemName = "??????";


        System.out.println(systemName);
    }



}
