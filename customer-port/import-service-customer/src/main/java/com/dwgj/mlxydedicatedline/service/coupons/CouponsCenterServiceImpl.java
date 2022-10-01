package com.dwgj.mlxydedicatedline.service.coupons;

import com.dwgj.mlxydedicatedline.commons.DateUtil;
import com.dwgj.mlxydedicatedline.config.UserThreadContext;
import com.dwgj.mlxydedicatedline.entity.Customer;
import com.dwgj.mlxydedicatedline.entity.coupons.CustomerCoupons;
import com.dwgj.mlxydedicatedline.entity.coupons.CustomerCouponsCenter;
import com.dwgj.mlxydedicatedline.entity.customerGroup.CustomerGroupLink;
import com.dwgj.mlxydedicatedline.mapper.customerGroup.CustomerGroupLinkMapper;
import com.dwgj.mlxydedicatedline.mapper.customerGroup.CustomerGroupMapper;
import com.dwgj.mlxydedicatedline.mapper.coupons.CustomerCouponsCenterMapper;
import com.dwgj.mlxydedicatedline.mapper.coupons.CustomerCouponsMapper;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.dwgj.mlxydedicatedline.enums.ResultStatus.NOT_LOGIN_ERROR;

@Service
public class CouponsCenterServiceImpl implements CouponsCenterService {

    @Autowired
    private CustomerCouponsCenterMapper customerCouponsCenterMapper;
    @Autowired
    private CustomerCouponsMapper customerCouponsMapper;
    @Autowired
    private CustomerGroupMapper customerGroupMapper;
    @Autowired
    private CustomerGroupLinkMapper customerGroupLinkMapper;


    @Override
    public ResponseEntity<ResultModel> getCouponsCenterList(boolean isExchange) {
        Customer customer = UserThreadContext.getUser();
        if(customer == null){
            return new ResponseEntity<>(ResultModel.error(NOT_LOGIN_ERROR), HttpStatus.OK);
        }
        List<CustomerCouponsCenter> customerCouponsCenterList = new ArrayList<>();
        if(isExchange){

//            customerCouponsCenterList = customerCouponsCenterMapper.getCouponsCenterExchangeList();
//            if(customerCouponsCenterList == null || customerCouponsCenterList.isEmpty()){
//                customerCouponsCenterList = new ArrayList<>();
//            }
            return new ResponseEntity<>(ResultModel.ok(customerCouponsCenterList), HttpStatus.OK);

        }else {
            // 查询客户所在的分组
//            List<Integer> groupIdList = customerGroupMapper.selectGroupIdByCustomerId(customer.getId());
            List<CustomerGroupLink> customerGroupLinkList =  customerGroupLinkMapper.selectGroupIdByCustomerId(customer.getId());

            // 如果客户没有分组，则直接返回空列表
            if(CollectionUtils.isEmpty(customerGroupLinkList)){
                return new ResponseEntity<>(ResultModel.ok(customerCouponsCenterList), HttpStatus.OK);
            }
            List<Integer> groupIdList = customerGroupLinkList.stream().map(CustomerGroupLink::getCustomerGroupId).collect(Collectors.toList());


            // 查询客户已经领取的优惠券
            List<CustomerCoupons> customerCouponsList = customerCouponsMapper.getHaveToReceiveCouponsList(customer.getId());
            List<Integer> couponsIdList =
                    customerCouponsList.stream().map(CustomerCoupons::getCenterCouponsId).collect(Collectors.toList());

            List<CustomerCouponsCenter> customerCouponsCenterAllList = customerCouponsCenterMapper.getCouponsCenterList(couponsIdList, groupIdList);

            if(CollectionUtils.isEmpty(customerCouponsCenterAllList)){
                return new ResponseEntity<>(ResultModel.ok(new ArrayList<>()), HttpStatus.OK);
            }

            for (CustomerCouponsCenter center : customerCouponsCenterAllList) {
                for (CustomerGroupLink link : customerGroupLinkList) {
                    try {
                        // 同一个分组并且在加入分组的时间大于分发优惠券的时间
                        if(center.getCustomerGroupId().equals(link.getCustomerGroupId()) &&
                                DateUtil.string2Timestamp(link.getCreateTime()).getTime()
                                        < DateUtil.string2Timestamp(center.getCreateTime()).getTime() ){
                            customerCouponsCenterList.add(center);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        return new ResponseEntity<>(ResultModel.ok(customerCouponsCenterList), HttpStatus.OK);
    }
}
