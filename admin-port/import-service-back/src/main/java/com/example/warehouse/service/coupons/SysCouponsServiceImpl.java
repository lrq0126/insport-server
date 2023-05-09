package com.example.warehouse.service.coupons;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.ImgUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.Customer;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.coupons.CustomerCoupons;
import com.example.warehouse.entity.coupons.CustomerCouponsCenter;
import com.example.warehouse.entity.coupons.SysCoupons;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.mapper.CustomerMapper;
import com.example.warehouse.mapper.coupons.CustomerCouponsCenterMapper;
import com.example.warehouse.mapper.coupons.CustomerCouponsMapper;
import com.example.warehouse.mapper.coupons.SysCouponsMapper;
import com.example.warehouse.mapper.customerGroup.CustomerGroupLinkMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.wechat.SendMessageServer;
import com.example.warehouse.vo.coupons.request.SysCouponsReqVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static com.example.warehouse.enums.ResultStatus.*;

@Service
@Slf4j
public class SysCouponsServiceImpl implements SysCouponsService {

    @Autowired
    private SysCouponsMapper sysCouponsMapper;
    @Autowired
    private CustomerCouponsMapper customerCouponsMapper;
    @Autowired
    private CustomerCouponsCenterMapper customerCouponsCenterMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private SendMessageServer sendMessageServer;
    @Autowired
    private ImgUtil imgUtil;
    @Autowired
    private CustomerGroupLinkMapper customerGroupLinkMapper;

    @Override
    public ResponseEntity<PageResultModel> getCouponsList(SysCouponsReqVo sysCouponsReqVo) {
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
            sysCouponsReqVo.setCommercialAreaId(user.getCommercialAreaId());
        }
        PageData pageData = PageHelp.editPage(sysCouponsReqVo);

        int count =  sysCouponsMapper.selectCount(sysCouponsReqVo);
        pageData.setTotal(count);
        if(count > 0){
            sysCouponsReqVo.setPageNumber(pageData.getPageNumber());
            List<SysCoupons> sysCouponsList = sysCouponsMapper.selectCouponsList(sysCouponsReqVo);
            return new ResponseEntity<>(PageResultModel.ok(sysCouponsList, pageData), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getCouponsListNotPage() {
        List<SysCoupons> sysCouponsList = sysCouponsMapper.getCouponsListNotPage();
        return new ResponseEntity<>(ResultModel.ok(sysCouponsList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getCouponsById(int id) {
        SysCoupons sysCoupons = sysCouponsMapper.selectByPrimaryKey(id);
        if(sysCoupons == null){
            return new ResponseEntity<>(ResultModel.error(THIS_COUPONS_IS_DELETE), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(sysCoupons), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> saveCoupons(SysCoupons sysCoupons, boolean isAdd) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }

        if(getStringLength(sysCoupons.getCouponsName()) > 12){
            return new ResponseEntity<>(ResultModel.error(COUPONS_NAME_LONGED_ERROR), HttpStatus.OK);
        }

        int count = sysCouponsMapper.selectCouponsByCouponsName(sysCoupons);
        if(count > 0){
            return new ResponseEntity<>(ResultModel.error(THIS_COUPONS_NAME_IS_EXISTING), HttpStatus.OK);
        }
        // 如果不存在优惠券编码则生成
        if(StringUtils.isEmpty(sysCoupons.getCouponsCode())){
            String couponsCode = generateCouponsCode();
            sysCoupons.setCouponsCode(couponsCode);
        }
        int i;
        if(isAdd){
            sysCoupons.setStatus(1);
            sysCoupons.setVersion(1);
            sysCoupons.setCreateId(user.getId());
            sysCoupons.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            i = sysCouponsMapper.insertSelective(sysCoupons);
        }else {
            sysCoupons.setUpdateId(user.getId());
            sysCoupons.setUpdateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            sysCoupons.setStatus(1);
            i = sysCouponsMapper.updateByPrimaryKeySelective(sysCoupons);
        }

        if(i == 1){
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
    }

    private String generateCouponsCode() {
        List<String> couponsCodeList = sysCouponsMapper.selectAllCouponsCode();
        String couponsCode = "sysc" + toSerialCode();

        while (!CollectionUtils.isEmpty(couponsCodeList) && couponsCodeList.contains(couponsCode)){
            couponsCode = "sysc" + toSerialCode();
        }
        return couponsCode;
    }

    public static String toSerialCode() {
        // 加上一些字母，就可以生成pc站的验证码了
        String sources = "0123456789";
        Random rand = new Random();
        StringBuffer flag = new StringBuffer();
        for (int j = 0; j < 4; j++) {
            flag.append(sources.charAt(rand.nextInt(9)) + "");
        }
        return flag.toString();
    }

    @Override
    public ResponseEntity<ResultModel> deleteCoupons(int id) {
        int i = sysCouponsMapper.deleteByPrimaryKey(id);

        if(i == 1){
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> isEnable(int id) {
        SysCoupons sysCoupons = sysCouponsMapper.selectByPrimaryKey(id);
        if(sysCoupons == null){
            return new ResponseEntity<>(ResultModel.error(THIS_COUPONS_IS_DELETE), HttpStatus.OK);
        }
        if(sysCoupons.getIsEnable() == 1){
            sysCoupons.setIsEnable(0);
        }else {
            sysCoupons.setIsEnable(1);
        }

        int i = sysCouponsMapper.updateByPrimaryKey(sysCoupons);

        if(i == 1){
            if(sysCoupons.getIsEnable() == 1){
                return new ResponseEntity<>(ResultModel.ok("优惠券启用完成"), HttpStatus.OK);
            }else {
                return new ResponseEntity<>(ResultModel.ok("优惠券禁用完成"), HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> batchGiveCoupons(int sysCouponsId, String groupIds) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }

        if(groupIds == null || groupIds.isEmpty()){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.PLEASE_SELECT_THE_CUSTOMER_GROUP), HttpStatus.OK);
        }

        List<String> groupIdList = Arrays.asList(groupIds.split(","));
        if(groupIdList.isEmpty()){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.PLEASE_SELECT_THE_CUSTOMER_GROUP), HttpStatus.OK);
        }

        List<CustomerCouponsCenter> customerCouponsCenterList = new ArrayList<>();
        SysCoupons sysCoupons = sysCouponsMapper.selectByPrimaryKey(sysCouponsId);
        List<Customer> customers = new ArrayList<>();


        for (String groupId : groupIdList) {
            CustomerCouponsCenter customerCouponsCenter = new CustomerCouponsCenter();
            customerCouponsCenter.setCreateId(user.getId());
            customerCouponsCenter.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            customerCouponsCenter.setStatus(1);
            customerCouponsCenter.setVersion(1);
            customerCouponsCenter.setCustomerGetNum(0);
            customerCouponsCenter.setCustomerUsedNum(0);
            customerCouponsCenter.setSysCouponsId(sysCouponsId);
            customerCouponsCenter.setCustomerGroupId(Integer.parseInt(groupId));

            customerCouponsCenter.setCouponsName(sysCoupons.getCouponsName());
            customerCouponsCenter.setCouponsType(sysCoupons.getCouponsType());
            customerCouponsCenter.setCouponsAmount(sysCoupons.getCouponsAmount());
            customerCouponsCenter.setMinimumAmount(sysCoupons.getMinimumAmount());
            customerCouponsCenter.setOverdueType(sysCoupons.getOverdueType());
            customerCouponsCenter.setTimeLimit(sysCoupons.getTimeLimit());
            customerCouponsCenter.setTimeOverdue(sysCoupons.getTimeOverdue());

            customerCouponsCenterList.add(customerCouponsCenter);
        }

        List<String> customerIdList = customerGroupLinkMapper.getCusetomerIdByGroupIds(groupIdList);
        for ( String customerId : customerIdList ){
            Customer customer = customerMapper.selectByPrimaryKey(Integer.valueOf(customerId));
            customers.add(customer);
        }
        customerCouponsCenterMapper.insertList(customerCouponsCenterList);

        // 校验用户是否存在openID，不存在openID的不发送微信信息
        List<Customer> sendMessageCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            if(!StringUtils.isEmpty(customer.getOpenid())){
                sendMessageCustomers.add(customer);
            }
        }


        if(!CollectionUtils.isEmpty(sendMessageCustomers)) {
            // 发送优惠券的消息通知
            sendMessageServer.sengMeesageForCustomerSendCoupons(customers, sysCoupons, 2);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> giveCustomerCoupons(int sysCouponsId, String customerIds) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }

        if(customerIds == null || customerIds.isEmpty()){
            return new ResponseEntity<>(ResultModel.error(PLEASE_SELECT_THE_CUSTOMER), HttpStatus.OK);
        }
        SysCoupons sysCoupons = sysCouponsMapper.selectByPrimaryKey(sysCouponsId);
        if(sysCoupons == null){
            return new ResponseEntity<>(ResultModel.error(THIS_COUPONS_IS_DELETE), HttpStatus.OK);
        }
        if(sysCoupons.getIsEnable() != 1){
            return new ResponseEntity<>(ResultModel.error(THIS_COUPONS_IS_DISABLED), HttpStatus.OK);
        }

        List<String> customerIdList = Arrays.asList(customerIds.split(","));
        List<CustomerCoupons> customerCouponsList = new ArrayList<>();


        List<Customer> customers = customerMapper.selectByCustomerIds(customerIdList);

        for (String customerId : customerIdList) {
            CustomerCoupons customerCoupons = new CustomerCoupons();
            customerCoupons.setStatus(1);
            customerCoupons.setVersion(1);

            customerCoupons.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
            customerCoupons.setGiveId(user.getId());


            customerCoupons.setCustomerId(Integer.parseInt(customerId));
            customerCoupons.setCenterCouponsId(-1);
            customerCoupons.setCustomerGroupId(-1); //防止数据出现问题，分组设置为-1
            customerCoupons.setSysCouponsId(sysCouponsId);

            customerCoupons.setCouponsName(sysCoupons.getCouponsName());
            customerCoupons.setCouponsStatus(0);
            customerCoupons.setCouponsType(sysCoupons.getCouponsType());
            customerCoupons.setCouponsAmount(sysCoupons.getCouponsAmount());
            customerCoupons.setMinimumAmount(sysCoupons.getMinimumAmount());

            if(sysCoupons.getOverdueType() == 1){
                customerCoupons.setTimeOverdue(sysCoupons.getTimeOverdue());
            }else {
                String overdueTime =
                    DateUtil.FormatDate(DateUtil.getSpecifiedDayAfter(new Date(), sysCoupons.getTimeLimit()), "yyyy-MM-dd");
                customerCoupons.setTimeOverdue(overdueTime);
            }

            customerCouponsList.add(customerCoupons);

//        Customer customer = customerMapper.selectByPrimaryKey(Integer.valueOf(customerId));
//        customers.add(customer);
        }
        if(customerCouponsList.isEmpty()){
            return new ResponseEntity<>(ResultModel.error(PLEASE_SELECT_THE_CUSTOMER), HttpStatus.OK);
        }
        customerCouponsMapper.insertList(customerCouponsList);

        // 校验用户是否存在openID，不存在openID的不发送微信信息
        List<Customer> sendMessageCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            if(!StringUtils.isEmpty(customer.getOpenid())){
                sendMessageCustomers.add(customer);
            }
        }
        if(!CollectionUtils.isEmpty(sendMessageCustomers)){
            // 发送优惠券的消息通知
            sendMessageServer.sengMeesageForCustomerSendCoupons(sendMessageCustomers,sysCoupons,1);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }


    /**
     * 定时任务，删除客户过期优惠券、删除领券中心的过期优惠券、停用 系统优惠券 已过期的优惠券
     */
    @Override
    public void deleteOverdueCoupons() {
        log.info("删除客户过期优惠券开始");
        customerCouponsMapper.deleteCustomerOverdueCoupons();
        log.info("删除客户过期优惠券完成");

        log.info("停用系统过期优惠券开始");
        sysCouponsMapper.disableOverdueCoupons();
        log.info("停用系统过期优惠券完成");

        log.info("删除领券中心过期优惠券开始");
        customerCouponsCenterMapper.deleteCenterOverdueCoupons();
        log.info("删除领券中心过期优惠券完成");
    }


    private int getStringLength(String str){
        String chinese = "[\u4e00-\u9fa5]";
        int valueLength = 0;
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < str.length(); i++) {
            // 获取一个字符
            String temp = str.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为2
                valueLength += 2;
            } else {
                // 其他字符长度为1
                valueLength += 1;
            }
        }

        return valueLength;

    }



}
