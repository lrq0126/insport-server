package com.example.warehouse.service.sys.impl;



import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.DO.sys.SysStatisticalDo;
import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.*;
import com.example.warehouse.entity.coupons.CustomerCoupons;
import com.example.warehouse.entity.coupons.SysCoupons;
import com.example.warehouse.entity.sys.SysStatistical;
import com.example.warehouse.mapper.coupons.CustomerCouponsMapper;
import com.example.warehouse.mapper.coupons.SysCouponsMapper;
import com.example.warehouse.mapper.sys.CommercialAreaMapper;
import com.example.warehouse.mapper.sys.SysStatisticalMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.sys.StatisticService;
import com.example.warehouse.service.wechat.SendMessageServer;
import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.customer.CustomerPackVo;
import com.example.warehouse.vo.sys.SysStatisticalReqVo;

import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.warehouse.enums.ResultStatus.USER_NOT_COMMERCIAL_AREA;
import static com.example.warehouse.enums.ResultStatus.USER_NOT_LOGIN;

@Service
@Slf4j
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private SysStatisticalMapper sysStatisticalMapper;
    @Autowired
    private SysCouponsMapper sysCouponsMapper;
    @Autowired
    private CustomerCouponsMapper customerCouponsMapper;
    @Autowired
    private SendMessageServer sendMessageServer;
    @Autowired
    private CommercialAreaMapper commercialAreaMapper;


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public int countGoodsInOfCustomerNum() {
        return sysStatisticalMapper.countGoodsInCustomerNum();
    }

    @Override
    public ResponseEntity<ResultModel> getAll(SysStatisticalReqVo sysStatisticalReqVo) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }
        int commercialAreaId = 0;
        // ????????????????????????????????????????????????????????????
        if(user.getCommercialAreaId() != 1){
//            map.put("commercialAreaId", user.getCommercialAreaId());
            commercialAreaId = user.getCommercialAreaId();
        }



        SysStatistical todayStatistical = new SysStatistical();
        // ???????????????
        int todayAccountNum = sysStatisticalMapper.countTodayAccountNum(commercialAreaId);
        todayStatistical.setRechargeNum(todayAccountNum);
        // ??????????????????
        BigDecimal todayAccountAmount = sysStatisticalMapper.countTodayAccountAmount(commercialAreaId);
        todayStatistical.setRechargeAmountSum(todayAccountAmount);
        // ??????????????????
        int todayAddCustomerNum = sysStatisticalMapper.countTodayAddCustomerNum(commercialAreaId);
        todayStatistical.setNewCustomerNum(todayAddCustomerNum);
        // ?????????????????????
        int todayAddPackageNum = sysStatisticalMapper.countTodayAddPackageNum(commercialAreaId);
        todayStatistical.setReInWareNum(todayAddPackageNum);
        // ????????????????????????
        int goodsInWareTodayNum = sysStatisticalMapper.countGoodsInWareTodayNum(commercialAreaId);
        todayStatistical.setInWareNum(goodsInWareTodayNum);
        // ????????????????????????
        double goodsInWareWeightTodaySum = sysStatisticalMapper.countGoodsInWareWeightTodaySum(commercialAreaId);
        goodsInWareWeightTodaySum = new BigDecimal(goodsInWareWeightTodaySum).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        todayStatistical.setInWareWeightSum(goodsInWareWeightTodaySum);

        // ?????????????????????
        int todayApplyPackNum = sysStatisticalMapper.countTodayApplyPackNum(commercialAreaId);
        todayStatistical.setApplyPackNum(todayApplyPackNum);
        // ???????????????
        int todayPackedNum = sysStatisticalMapper.countTodayPackedNum(commercialAreaId);
        todayStatistical.setPackedNum(todayPackedNum);

        // ????????????????????????
        double outWareWeightToday = sysStatisticalMapper.countOutWareWeightToday(commercialAreaId);
        todayStatistical.setOutWareWeightSum(outWareWeightToday);

        Map<String, Object> result = new HashMap<>();
        result.put("todayStatistical", todayStatistical);

        sysStatisticalReqVo.setCommercialAreaId(commercialAreaId);
        // ?????????????????????
        if (sysStatisticalReqVo.getTimeStart() == null || sysStatisticalReqVo.getTimeStart().isEmpty()) {
            Date yesterday = DateUtil.getOtherDay(-1);
            sysStatisticalReqVo.setTimeStart(DateUtil.dayStartTime(yesterday));
            sysStatisticalReqVo.setTimeEnd(DateUtil.dayEndTime(yesterday));
        }
        List<SysStatistical> sysStatisticalList = sysStatisticalMapper.selectByConditions(sysStatisticalReqVo);
//        List<SysStatistical> sysStatisticalList = sysStatisticalMapper.getAll();

        SysStatistical sysStatistical = new SysStatistical();
        if (sysStatisticalList != null && !sysStatisticalList.isEmpty()) {
            // ??????????????????????????????
            sysStatistical.setWareCustomerSum(sysStatisticalList.stream().mapToInt(SysStatistical::getWareCustomerSum).sum());
            // ??????????????????
            sysStatistical.setInWareNum(sysStatisticalList.stream().mapToInt(SysStatistical::getInWareNum).sum());
            // ??????????????????
            sysStatistical.setInWareWeightSum(sysStatisticalList.stream().mapToDouble(SysStatistical::getInWareWeightSum).sum());
            // ????????????
            sysStatistical.setNewCustomerNum(sysStatisticalList.stream().mapToInt(SysStatistical::getNewCustomerNum).sum());
            // ???????????????
            sysStatistical.setReInWareNum(sysStatisticalList.stream().mapToInt(SysStatistical::getReInWareNum).sum());
            // ???????????????
            sysStatistical.setApplyPackNum(sysStatisticalList.stream().mapToInt(SysStatistical::getApplyPackNum).sum());
            // ????????????
            sysStatistical.setPackedNum(sysStatisticalList.stream().mapToInt(SysStatistical::getPackedNum).sum());
            // ?????????
            sysStatistical.setRechargeNum(sysStatisticalList.stream().mapToInt(SysStatistical::getRechargeNum).sum());
            // ????????????
            BigDecimal rechargeAmountSum = new BigDecimal(0);
            for (SysStatistical statistical : sysStatisticalList) {
                rechargeAmountSum = rechargeAmountSum.add(statistical.getRechargeAmountSum());
            }
            sysStatistical.setRechargeAmountSum(rechargeAmountSum);
            // ?????????????????????
            sysStatistical.setGoodsNum(sysStatisticalList.stream().mapToInt(SysStatistical::getGoodsNum).sum());
            // ??????????????????
            sysStatistical.setGoodsWeightSum(sysStatisticalList.stream().mapToDouble(SysStatistical::getGoodsWeightSum).sum());
            // ?????????????????????
            sysStatistical.setGoodsVolSum(sysStatisticalList.stream().mapToDouble(SysStatistical::getGoodsVolSum).sum());
            // ????????????????????????
            sysStatistical.setOutWareWeightSum(sysStatisticalList.stream().mapToDouble(SysStatistical::getOutWareWeightSum).sum());
            // ??????????????????
            sysStatistical.setOutWareNum(sysStatisticalList.stream().mapToInt(SysStatistical::getOutWareNum).sum());
            // ?????????????????????
            sysStatistical.setOutWareGoodsNum(sysStatisticalList.stream().mapToInt(SysStatistical::getOutWareGoodsNum).sum());
            // ???????????????????????????
            sysStatistical.setOutWareCustomerSum(sysStatisticalList.stream().mapToInt(SysStatistical::getOutWareCustomerSum).sum());
        } else {
            // ??????????????????????????????
            sysStatistical.setWareCustomerSum(0);
            // ??????????????????
            sysStatistical.setInWareNum(0);
            // ????????????
            sysStatistical.setNewCustomerNum(0);
            // ???????????????
            sysStatistical.setReInWareNum(0);
            // ???????????????
            sysStatistical.setApplyPackNum(0);
            // ?????????
            sysStatistical.setRechargeNum(0);
            // ????????????
            sysStatistical.setRechargeAmountSum(new BigDecimal(0));
            // ?????????????????????
            sysStatistical.setGoodsNum(0);
            // ??????????????????
            sysStatistical.setGoodsWeightSum(0.00);
            // ?????????????????????
            sysStatistical.setGoodsVolSum(0.00);
            // ????????????????????????
            sysStatistical.setOutWareWeightSum(0.00);
            // ??????????????????
            sysStatistical.setOutWareNum(0);
            // ?????????????????????
            sysStatistical.setOutWareGoodsNum(0);
            // ???????????????????????????
            sysStatistical.setOutWareCustomerSum(0);
        }
        result.put("lastStatistical", sysStatistical);
        return new ResponseEntity<>(ResultModel.ok(result), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getChart(SysStatisticalReqVo sysStatisticalReqVo) {
        // ???????????????????????????????????????
        if (sysStatisticalReqVo.getTimeStart() == null || sysStatisticalReqVo.getTimeStart().isEmpty()) {
            sysStatisticalReqVo.setTimeStart(DateUtil.dayStartTime(DateUtil.getOtherDay(-8)));
            sysStatisticalReqVo.setTimeEnd(DateUtil.dayEndTime(DateUtil.getOtherDay(-1)));
        } else {
            if (sysStatisticalReqVo.getTimeStart().contains("T")) {
                sysStatisticalReqVo.setTimeStart(sysStatisticalReqVo.getTimeStart().split("T")[0]);
                sysStatisticalReqVo.setTimeEnd(sysStatisticalReqVo.getTimeEnd().split("T")[0]);
            }
            sysStatisticalReqVo.setTimeStart(sysStatisticalReqVo.getTimeStart() + " 00:00:00");
            sysStatisticalReqVo.setTimeEnd(sysStatisticalReqVo.getTimeEnd() + " 23:59:59");
        }

        List<SysStatistical> sysStatisticalList = sysStatisticalMapper.selectByConditions(sysStatisticalReqVo);

        Map<String, Object> result = new HashMap<>();
        List<String> chartTimeData = new ArrayList<>();
        List<Integer> outWareData = new ArrayList<>();
        List<BigDecimal> rechargeAmountData = new ArrayList<>();

        List<Integer> inWareData = new ArrayList<>();
        List<Integer> newCustomerData = new ArrayList<>();
        List<Integer> reInWareData = new ArrayList<>();
        List<Integer> applyPackData = new ArrayList<>();
        List<Double> inWareWeightData = new ArrayList<>();
        List<Double> outWareWeightData = new ArrayList<>();

        // ????????????????????????????????????????????????
        int newCustomerGoodsOrdersNum;

        // ???????????????????????????????????????????????????
        int newCustomerNotGoodsOrdersNum;

        // ????????????????????????????????????????????????
        int newCustomerOrdersNum;
        // ???????????????????????????????????????????????????
        int newCustomerNotOrdersNum;

        if (StringUtils.isEmpty(sysStatisticalReqVo.getRegisterTimeStart())) {
            sysStatisticalReqVo.setRegisterTimeStart(sysStatisticalReqVo.getTimeStart());
            sysStatisticalReqVo.setRegisterTimeEnd(sysStatisticalReqVo.getTimeEnd());

            // ??????????????????????????????????????????????????????
            newCustomerGoodsOrdersNum = sysStatisticalMapper.selectNewCustomerGoodsOrdersNum(sysStatisticalReqVo);
            newCustomerNotGoodsOrdersNum = sysStatisticalMapper.selectNewCustomerNotGoodsOrdersNum(sysStatisticalReqVo);

            newCustomerOrdersNum = sysStatisticalMapper.selectNewCustomerOrdersNum(sysStatisticalReqVo);
            newCustomerNotOrdersNum = sysStatisticalMapper.selectNewCustomerNotOrdersNum(sysStatisticalReqVo);

        } else {

            sysStatisticalReqVo.setRegisterTimeStart(sysStatisticalReqVo.getRegisterTimeStart() + " 00:00:00");
            sysStatisticalReqVo.setRegisterTimeEnd(sysStatisticalReqVo.getRegisterTimeEnd() + " 23:59:59");

            // ???????????????????????????????????????????????????????????????????????????????????????????????????
            newCustomerGoodsOrdersNum =
                    sysStatisticalMapper.selectNewCustomerGoodsOrdersNumInCustomerId(sysStatisticalReqVo);
            newCustomerNotGoodsOrdersNum =
                    sysStatisticalMapper.selectNewCustomerNotGoodsOrdersNumInCustomerId(sysStatisticalReqVo);

            newCustomerOrdersNum = sysStatisticalMapper.selectNewCustomerOrdersNum(sysStatisticalReqVo);
            newCustomerNotOrdersNum = sysStatisticalMapper.selectNewCustomerNotOrdersNum(sysStatisticalReqVo);
        }

        // ?????????????????????????????????
        int newNumber = sysStatisticalMapper.selectNewNumber(sysStatisticalReqVo);
        // ??????????????????????????????????????????
        int ordersNum = sysStatisticalMapper.selectOrdersNum(sysStatisticalReqVo);

        // ????????????????????????????????????
        double goodsWeightSum = sysStatisticalMapper.selectGoodsWeightSum(sysStatisticalReqVo);

        // ??????????????????
        int outWareSum = 0;
        // ??????????????????
        int inWareSum = 0;
        // ??????????????????
        int newCustomerSum = 0;
        // ?????????????????????
        int reInWareSum = 0;
        // ????????????????????????
        int applyPackSum = 0;
        // ??????????????????
        double inWareWeightSum = 0;
        // ??????????????????
        double outWareWeightSum = 0;

        // ????????????
        BigDecimal rechargeAmountSum = new BigDecimal(0);

        if (sysStatisticalList != null && !sysStatisticalList.isEmpty()) {
            chartTimeData = sysStatisticalList.stream().map(SysStatistical::getCreateTime).collect(Collectors.toList());

            outWareData = sysStatisticalList.stream().map(SysStatistical::getOutWareNum).collect(Collectors.toList());
            rechargeAmountData = sysStatisticalList.stream().map(SysStatistical::getRechargeAmountSum).collect(Collectors.toList());
            inWareData = sysStatisticalList.stream().map(SysStatistical::getInWareNum).collect(Collectors.toList());
            newCustomerData = sysStatisticalList.stream().map(SysStatistical::getNewCustomerNum).collect(Collectors.toList());
            reInWareData = sysStatisticalList.stream().map(SysStatistical::getReInWareNum).collect(Collectors.toList());
            applyPackData = sysStatisticalList.stream().map(SysStatistical::getApplyPackNum).collect(Collectors.toList());
            inWareWeightData = sysStatisticalList.stream().map(SysStatistical::getInWareWeightSum).collect(Collectors.toList());
            outWareWeightData = sysStatisticalList.stream().map(SysStatistical::getOutWareWeightSum).collect(Collectors.toList());

            for (SysStatistical sysStatistical : sysStatisticalList) {
                outWareSum = outWareSum + sysStatistical.getOutWareNum();
                inWareSum = inWareSum + sysStatistical.getInWareNum();
                newCustomerSum = newCustomerSum + sysStatistical.getNewCustomerNum();
                reInWareSum = reInWareSum + sysStatistical.getReInWareNum();
                applyPackSum = applyPackSum + sysStatistical.getApplyPackNum();
                inWareWeightSum = inWareWeightSum + sysStatistical.getInWareWeightSum();
                outWareWeightSum = outWareWeightSum + sysStatistical.getOutWareWeightSum();
                rechargeAmountSum = rechargeAmountSum.add(sysStatistical.getRechargeAmountSum());
            }

        }
        result.put("newNumber", newNumber);
        result.put("ordersNum", ordersNum);

        result.put("newCustomerGoodsOrdersNum", newCustomerGoodsOrdersNum);
        result.put("newCustomerNotGoodsOrdersNum", newCustomerNotGoodsOrdersNum);

        result.put("newCustomerOrdersNum", newCustomerOrdersNum);
        result.put("newCustomerNotOrdersNum", newCustomerNotOrdersNum);

        BigDecimal bigDecimal = new BigDecimal(goodsWeightSum);
        result.put("goodsWeightSum", bigDecimal.setScale(2, BigDecimal.ROUND_UP));

        result.put("chartTimeData", chartTimeData);
        result.put("outWareData", outWareData);
        result.put("rechargeAmountData", rechargeAmountData);

        result.put("inWareData", inWareData);
        result.put("newCustomerData", newCustomerData);
        result.put("reInWareData", reInWareData);
        result.put("applyPackData", applyPackData);
        result.put("inWareWeightData", inWareWeightData);
        result.put("outWareWeightData", outWareWeightData);


        result.put("outWareSum", outWareSum);
        result.put("inWareSum", inWareSum);
        result.put("newCustomerSum", newCustomerSum);
        result.put("reInWareSum", reInWareSum);
        result.put("applyPackSum", applyPackSum);
        result.put("inWareWeightSum", new BigDecimal(inWareWeightSum).setScale(2, BigDecimal.ROUND_UP));
        result.put("outWareWeightSum", new BigDecimal(outWareWeightSum).setScale(2, BigDecimal.ROUND_UP));
        result.put("rechargeAmountSum", rechargeAmountSum);

        return new ResponseEntity<>(ResultModel.ok(result), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ResultModel> everyDayAdd() {
//        List<SysStatistical> sysStatisticalList = new ArrayList<>();
        List<SysStatisticalDo> sysStatisticalDoList = commercialAreaMapper.getSysStatisticalDoList();
        SysStatistical sysStatistical = new SysStatistical();
        // ??????????????????????????????
        int customerNum = sysStatisticalMapper.countGoodsInCustomerNum();
        sysStatistical.setWareCustomerSum(customerNum);

        // ??????????????????
        int outWareCustomerNum = sysStatisticalMapper.countAllOutWarehouseCustomer();
        sysStatistical.setOutWareCustomerSum(outWareCustomerNum);
        String startTime = DateUtil.dayStartTime(DateUtil.getOtherDay(-1));
        String endTime = DateUtil.dayEndTime(DateUtil.getOtherDay(-1));
        // ??????????????????
        int goodsInWareTodayNum = sysStatisticalMapper.countGoodsInWareLastNum(startTime, endTime);
        sysStatistical.setInWareNum(goodsInWareTodayNum);

        // ????????????
        int todayAddCustomerNum = sysStatisticalMapper.countLastAddCustomerNum(startTime, endTime);
        sysStatistical.setNewCustomerNum(todayAddCustomerNum);

        // ???????????????
        int todayAddPackageNum = sysStatisticalMapper.countLastAddPackageNum(startTime, endTime);
        sysStatistical.setReInWareNum(todayAddPackageNum);

        // ???????????????
        int todayApplyPackedNum = sysStatisticalMapper.countLastApplyPackNum(startTime, endTime);
        sysStatistical.setApplyPackNum(todayApplyPackedNum);

        // ?????????
        int todayPackedNum = sysStatisticalMapper.countLastPackedNum(startTime, endTime);
        sysStatistical.setPackedNum(todayPackedNum);

        // ?????????
        int todayAccountNum = sysStatisticalMapper.countLastAccountNum(startTime, endTime);
        sysStatistical.setRechargeNum(todayAccountNum);

        // ????????????
        BigDecimal todayAccountAmount = sysStatisticalMapper.countLastAccountAmount(startTime, endTime);
        sysStatistical.setRechargeAmountSum(todayAccountAmount);

        // ?????????????????????
        int warePackagesNum = sysStatisticalMapper.countWarePackagesNum();
        sysStatistical.setGoodsNum(warePackagesNum);

        // ??????????????????
        double warePackagesWeight = sysStatisticalMapper.countWarePackagesWeight();
        sysStatistical.setGoodsWeightSum(warePackagesWeight);

        // ?????????????????????
        double warePackagesVol = sysStatisticalMapper.countWarePackagesVol();
        sysStatistical.setGoodsVolSum(warePackagesVol);

        // ????????????????????????
        double inWareWeightToday = sysStatisticalMapper.countInWareWeightLastDay(startTime, endTime);
        sysStatistical.setInWareWeightSum(inWareWeightToday);

        // ????????????????????????
        double outWareWeightToday = sysStatisticalMapper.countOutWareWeightLastDay(startTime, endTime);
        sysStatistical.setOutWareWeightSum(outWareWeightToday);

        // ???????????????????????????
        int outWarehouseCustomer = sysStatisticalMapper.countOutWareNumLastDay(startTime, endTime);
        sysStatistical.setOutWareNum(outWarehouseCustomer);

        // ??????????????????????????????
        int outWareGoodsNum = sysStatisticalMapper.countOutWareGoodsNumLastDay(startTime, endTime);
        sysStatistical.setOutWareGoodsNum(outWareGoodsNum);

        sysStatistical.setStatus(1);
        sysStatistical.setVersion(1);
        sysStatistical.setCreateTime(sdf.format(DateUtil.getOtherDay(-1)));// ?????????????????????????????????????????????

        sysStatistical.setUpdateTime(sdf.format(new Date()));

        SysStatisticalReqVo sysStatisticalReqVo = new SysStatisticalReqVo();
        sysStatisticalReqVo.setTimeStart(DateUtil.dayStartTime(DateUtil.getOtherDay(-1)));
        sysStatisticalReqVo.setTimeEnd(DateUtil.dayEndTime(DateUtil.getOtherDay(-1)));
        List<SysStatistical> sysStatisticalList = sysStatisticalMapper.selectByConditions(sysStatisticalReqVo);
        // ???????????????????????????
        if (sysStatisticalList != null && !sysStatisticalList.isEmpty()) {
            sysStatisticalMapper.deleteByKeyList(sysStatisticalList.stream().map(SysStatistical::getId).collect(Collectors.toList()));
        }
        sysStatisticalMapper.insertSelective(sysStatistical);
        return null;
    }

    @Override
    public ResponseEntity<PageResultModel> getCustomerList(SysStatisticalReqVo sysStatisticalReqVo) {
        PageData pageData = PageHelp.editPage(sysStatisticalReqVo);
        int count = sysStatisticalReqVo.getTotal();
        pageData.setTotal(count);
        if (count > 0) {
            sysStatisticalReqVo.setPageNumber(pageData.getPageNumber());
            updateReqVo(sysStatisticalReqVo);

            List<Customer> customerList = getStatisticalCustomerList(sysStatisticalReqVo);

            return new ResponseEntity<>(PageResultModel.ok(customerList, pageData), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }

    @Override
    public void issueCoupons(SysStatisticalReqVo sysStatisticalReqVo) {
        log.info("?????????{}", sysStatisticalReqVo);
//

        List<Customer> customerList = JSONObject.parseArray(sysStatisticalReqVo.getCustomerData(), Customer.class);
        if (CollectionUtils.isEmpty(customerList)){
            if(!StringUtils.isEmpty(sysStatisticalReqVo.getDateType())){
                String startTime = DateUtil.dayStartTime(DateUtil.getOtherDay(-1));
                String endTime = DateUtil.dayEndTime(DateUtil.getOtherDay(-1));

                if("today".equals(sysStatisticalReqVo.getDateType())){
                    startTime = DateUtil.dayStartTime(new Date());
                    endTime = DateUtil.dayEndTime(new Date());
                }
                sysStatisticalReqVo.setTimeStart(startTime);
                sysStatisticalReqVo.setTimeEnd(endTime);

                if (StringUtils.isEmpty(sysStatisticalReqVo.getRegisterTimeStart())) {
                    sysStatisticalReqVo.setRegisterTimeStart(sysStatisticalReqVo.getTimeStart());
                    sysStatisticalReqVo.setRegisterTimeEnd(sysStatisticalReqVo.getTimeEnd());
                }
                sysStatisticalReqVo.setType("newCustNumber");
            }else {
                updateReqVo(sysStatisticalReqVo);
            }

            customerList = getStatisticalCustomerList(sysStatisticalReqVo);
        }

        if(!CollectionUtils.isEmpty(customerList)){
            SysCoupons sysCoupons = sysCouponsMapper.selectByPrimaryKey(sysStatisticalReqVo.getCouponsId());
            // TODO ???????????????????????????
            List<CustomerCoupons> customerCouponsList = new ArrayList<>();
            for (Customer customer : customerList) {
                CustomerCoupons customerCoupons = new CustomerCoupons();
                customerCoupons.setStatus(1);
                customerCoupons.setVersion(1);
                customerCoupons.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));

                customerCoupons.setSysCouponsId(sysCoupons.getId());
                customerCoupons.setCustomerId(customer.getId());

                customerCoupons.setCouponsName(sysCoupons.getCouponsName());
                customerCoupons.setCouponsType(sysCoupons.getCouponsType());
                customerCoupons.setCouponsAmount(sysCoupons.getCouponsAmount());
                customerCoupons.setMinimumAmount(sysCoupons.getMinimumAmount());

                customerCoupons.setGiveId(-1);
                customerCoupons.setCustomerGroupId(-1);
                customerCoupons.setCenterCouponsId(-1);

                customerCoupons.setCouponsStatus(0);
                customerCouponsList.add(customerCoupons);
            }
            customerCouponsMapper.insertList(customerCouponsList);

            System.out.println("???????????????????????????????????????"+customerList.size()+"???????????????????????????????????????????????????");
            // ??????????????????
            sendMessageServer.sengMeesageForCustomerSendCoupons(customerList, sysCoupons, 1);

        }

    }

    @Override
    public ResponseEntity<PageResultModel> getStatisticGoodsDetail(Map<String, Object> requestMap) {
        requestMap = updateMap(requestMap);

        String pageNumber = String.valueOf(requestMap.get("pageNumber"));
        String pageSize = String.valueOf(requestMap.get("pageSize"));
        PageData pageData = PageHelp.editPage(pageNumber, pageSize);
        int count = sysStatisticalMapper.getStatisticGoodsCount(requestMap);
        pageData.setTotal(count);
        List<GoodsVo> goodsList = new ArrayList<>();
        if(count > 0){
            requestMap.put("pageNumber", pageData.getPageNumber());
            requestMap.put("pageSize", pageData.getPageSize());
            goodsList = sysStatisticalMapper.getStatisticGoodsDetail(requestMap);
        }
        return new ResponseEntity<>(PageResultModel.ok(goodsList, pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageResultModel> getStatisticCustomerPackDetail(Map<String, Object> requestMap) {
        requestMap = updateMap(requestMap);

        String pageNumber = String.valueOf(requestMap.get("pageNumber"));
        String pageSize = String.valueOf(requestMap.get("pageSize"));

        PageData pageData = PageHelp.editPage(pageNumber, pageSize);
        int count = sysStatisticalMapper.getStatisticCustomerPackCount(requestMap);
        pageData.setTotal(count);
        List<CustomerPackVo> customerPackList = new ArrayList<>();
        if(count > 0){
            requestMap.put("pageNumber", pageData.getPageNumber());
            requestMap.put("pageSize", pageData.getPageSize());
            customerPackList = sysStatisticalMapper.getStatisticCustomerPackDetail(requestMap);
        }
        return new ResponseEntity<>(PageResultModel.ok(customerPackList, pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageResultModel> getStatisticCustomerDetail(Map<String, Object> requestMap) {
        requestMap = updateMap(requestMap);
        String pageNumber = String.valueOf(requestMap.get("pageNumber"));
        String pageSize = String.valueOf(requestMap.get("pageSize"));

        PageData pageData = PageHelp.editPage(pageNumber, pageSize);
        int count = sysStatisticalMapper.getStatisticCustomerCount(requestMap);
        pageData.setTotal(count);
        List<Customer> customerList = new ArrayList<>();
        if(count > 0){
            requestMap.put("pageNumber", pageData.getPageNumber());
            requestMap.put("pageSize", pageData.getPageSize());
            customerList = sysStatisticalMapper.getStatisticCustomerDetail(requestMap);
        }
        return new ResponseEntity<>(PageResultModel.ok(customerList, pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageResultModel> getStatisticTopUpDetail(Map<String, Object> requestMap) {
        requestMap = updateMap(requestMap);
        String pageNumber = String.valueOf(requestMap.get("pageNumber"));
        String pageSize = String.valueOf(requestMap.get("pageSize"));
        Map<String, Object> resultMap = new HashMap<>();
        PageData pageData = PageHelp.editPage(pageNumber, pageSize);
        int count = sysStatisticalMapper.getStatisticTopUpCount(requestMap);
        pageData.setTotal(count);
        List<CustomerAccount> customerAccountList = new ArrayList<>();
        BigDecimal allAmount = new BigDecimal(0);
        if(count > 0){
            requestMap.put("pageNumber", pageData.getPageNumber());
            requestMap.put("pageSize", pageData.getPageSize());
            customerAccountList = sysStatisticalMapper.getStatisticTopUpDetail(requestMap);
            allAmount = sysStatisticalMapper.getStatisticTopUpAmount(requestMap);
        }
        resultMap.put("customerAccountData", customerAccountList);
        resultMap.put("allAmount", allAmount.setScale(2, BigDecimal.ROUND_DOWN));
        return new ResponseEntity<>(PageResultModel.ok(resultMap, pageData), HttpStatus.OK);
    }

    /**
     * ????????????
     * @param sysStatisticalReqVo
     * @return
     */
    private List<Customer> getStatisticalCustomerList(SysStatisticalReqVo sysStatisticalReqVo){
        List<Customer> customerList = new ArrayList<>();
        switch (sysStatisticalReqVo.getType()) {
            case "newCustNumber":
                customerList = sysStatisticalMapper.findNewCustNumber(sysStatisticalReqVo);
                break;
            case "newCustGoodsOrdersNum":
                customerList = sysStatisticalMapper.findNewCustGoodsOrdersNum(sysStatisticalReqVo);
                break;
            case "newCustNotGoodsOrdersNum":
                customerList = sysStatisticalMapper.findNewCustNotGoodsOrdersNum(sysStatisticalReqVo);
                break;
            case "newCustOrdersNum":
                customerList = sysStatisticalMapper.findNewCustOrdersNum(sysStatisticalReqVo);
                break;
            case "newCustNotOrdersNum":
                customerList = sysStatisticalMapper.findNewCustNotOrdersNum(sysStatisticalReqVo);
                break;
        }
        return customerList;
    }

    /**
     * ???????????????????????????
     * @param sysStatisticalReqVo
     */
    private void updateReqVo(SysStatisticalReqVo sysStatisticalReqVo){
        if (sysStatisticalReqVo.getTimeStart().contains("T")) {
            sysStatisticalReqVo.setTimeStart(sysStatisticalReqVo.getTimeStart().split("T")[0]);
            sysStatisticalReqVo.setTimeEnd(sysStatisticalReqVo.getTimeEnd().split("T")[0]);

        }
        sysStatisticalReqVo.setTimeStart(sysStatisticalReqVo.getTimeStart() + " 00:00:00");
        sysStatisticalReqVo.setTimeEnd(sysStatisticalReqVo.getTimeEnd() + " 23:59:59");

        if (StringUtils.isEmpty(sysStatisticalReqVo.getRegisterTimeStart())) {
            sysStatisticalReqVo.setRegisterTimeStart(sysStatisticalReqVo.getTimeStart());
            sysStatisticalReqVo.setRegisterTimeEnd(sysStatisticalReqVo.getTimeEnd());
        } else {
            if (sysStatisticalReqVo.getRegisterTimeStart().contains("T")) {
                sysStatisticalReqVo.setRegisterTimeStart(sysStatisticalReqVo.getRegisterTimeStart().split("T")[0]);
                sysStatisticalReqVo.setRegisterTimeEnd(sysStatisticalReqVo.getRegisterTimeEnd().split("T")[0]);
            }
            sysStatisticalReqVo.setRegisterTimeStart(sysStatisticalReqVo.getRegisterTimeStart() + " 00:00:00");
            sysStatisticalReqVo.setRegisterTimeEnd(sysStatisticalReqVo.getRegisterTimeEnd() + " 23:59:59");
        }
    }

    private Map<String, Object> updateMap(Map<String, Object> requestMap){
        String startTime = DateUtil.dayStartTime(DateUtil.getOtherDay(-1));
        String endTime = DateUtil.dayEndTime(DateUtil.getOtherDay(-1));
        if(requestMap.containsKey("dateType") && "today".equals(requestMap.get("dateType"))){
            startTime = DateUtil.dayStartTime(new Date());
            endTime = DateUtil.dayEndTime(new Date());
        }
        requestMap.put("startTime", startTime);
        requestMap.put("endTime", endTime);
        if(requestMap.containsKey("customerName")){
            String customerName = String.valueOf(requestMap.get("customerName"));
            // ?????? ????????????*??????
            if(customerName.indexOf("*") == 0){
                customerName = customerName.substring(1, customerName.length()-1);
                requestMap.put("customerName", customerName);
            }
        }
        return requestMap;
    }
}
