package com.example.warehouse.service.coupons;

import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.coupons.CustomerCoupons;
import com.example.warehouse.mapper.coupons.CustomerCouponsCenterMapper;
import com.example.warehouse.mapper.coupons.CustomerCouponsMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.coupons.request.CustomerCouponsReqVo;
import com.example.warehouse.vo.coupons.response.CustomerCouponsCenterRespVo;
import com.example.warehouse.vo.coupons.response.CustomerCouponsRespVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.warehouse.enums.ResultStatus.PLEASE_DELETE_THE_CUSTOMER;
import static com.example.warehouse.enums.ResultStatus.USER_NOT_COMMERCIAL_AREA;
import static com.example.warehouse.enums.ResultStatus.USER_NOT_LOGIN;

@Service
public class CustomerCouponsServiceImpl implements CustomerCouponsService{

    @Autowired
    private CustomerCouponsMapper customerCouponsMapper;

    @Autowired
    private CustomerCouponsCenterMapper customerCouponsCenterMapper;


    @Override
    public ResponseEntity<PageResultModel> getIssuedCouponsList(CustomerCouponsReqVo customerCouponsReqVo) {
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
            customerCouponsReqVo.setCommercialAreaId(user.getCommercialAreaId());
        }
        PageData pageData = PageHelp.editPage(customerCouponsReqVo);
        int count = customerCouponsCenterMapper.selectIssuedCouponsCount(customerCouponsReqVo);
        pageData.setTotal(count);
        if(count > 0){
            customerCouponsReqVo.setPageNumber(pageData.getPageNumber());
            List<CustomerCouponsCenterRespVo> customerCouponsCenterRespVoList = customerCouponsCenterMapper.getIssuedCouponsList(customerCouponsReqVo);
            return new ResponseEntity<>(PageResultModel.ok(customerCouponsCenterRespVoList, pageData), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageResultModel> getCustomerCouponsList(CustomerCouponsReqVo customerCouponsReqVo) {

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
            customerCouponsReqVo.setCommercialAreaId(user.getCommercialAreaId());
        }
        PageData pageData = PageHelp.editPage(customerCouponsReqVo);
        int count = customerCouponsMapper.selectCount(customerCouponsReqVo);
        pageData.setTotal(count);
        if(count > 0){
            customerCouponsReqVo.setPageNumber(pageData.getPageNumber());
            List<CustomerCouponsRespVo> customerCouponsRespVoList = customerCouponsMapper.selectCustomerCouponsList(customerCouponsReqVo);
            return new ResponseEntity<>(PageResultModel.ok(customerCouponsRespVoList, pageData), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> removeCoupons(int id) {
        customerCouponsMapper.deleteByPrimaryKey(id);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> removeCustomerCoupons(String customerCouponsIds) {
        if(customerCouponsIds == null || customerCouponsIds.isEmpty()){
            return new ResponseEntity<>(ResultModel.error(PLEASE_DELETE_THE_CUSTOMER), HttpStatus.OK);
        }

        List<String> idList = Arrays.asList(customerCouponsIds.split(","));
        customerCouponsMapper.deleteByIdList(idList);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> removeCouponsCenter(int couponsCenterId) {
        customerCouponsCenterMapper.deleteByPrimaryKey(couponsCenterId);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getCustomerCoupons(int customerId) {
        List<CustomerCoupons> customerCouponsList = customerCouponsMapper.selectCustomerCouponsByCustomerId(customerId);
        return new ResponseEntity<>(ResultModel.ok(customerCouponsList), HttpStatus.OK);
    }
}
