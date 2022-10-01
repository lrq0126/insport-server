package com.dwgj.mlxydedicatedline.service.coupons;

import com.dwgj.mlxydedicatedline.commons.DateUtil;
import com.dwgj.mlxydedicatedline.config.UserThreadContext;
import com.dwgj.mlxydedicatedline.entity.Customer;
import com.dwgj.mlxydedicatedline.entity.coupons.CustomerCoupons;
import com.dwgj.mlxydedicatedline.entity.coupons.CustomerCouponsCenter;
import com.dwgj.mlxydedicatedline.entity.coupons.SysCoupons;
import com.dwgj.mlxydedicatedline.mapper.coupons.CustomerCouponsCenterMapper;
import com.dwgj.mlxydedicatedline.mapper.coupons.CustomerCouponsMapper;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.dwgj.mlxydedicatedline.enums.ResultStatus.*;

@Service
public class CustomerCouponsServiceImpl implements CustomerCouponsService {

    @Autowired
    private CustomerCouponsMapper customerCouponsMapper;
    @Autowired
    private CustomerCouponsCenterMapper customerCouponsCenterMapper;

    @Override
    public ResponseEntity<ResultModel> getMyCouponsList() {
        Customer customer = UserThreadContext.getUser();
        if(customer == null){
            return new ResponseEntity<>(ResultModel.error(NOT_LOGIN_ERROR), HttpStatus.OK);
        }
        List<CustomerCoupons> customerCouponsList = customerCouponsMapper.getMyCouponsList(customer.getId());
        return new ResponseEntity<>(ResultModel.ok(customerCouponsList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> receiveCoupons(CustomerCoupons customerCoupons) {
        Customer customer = UserThreadContext.getUser();
        if(customer == null){
            return new ResponseEntity<>(ResultModel.error(NOT_LOGIN_ERROR), HttpStatus.OK);
        }

        customerCoupons.setCustomerId(customer.getId());
        if(customerCoupons.getCustomerId() == null){
            return new ResponseEntity<>(ResultModel.error(THIS_CUSTOMER_LOGIN_ERROR), HttpStatus.OK);
        }

        if(customerCoupons.getCenterCouponsId() == null){
            return new ResponseEntity<>(ResultModel.error(THIS_COUPONS_RECEIVED_ERROR), HttpStatus.OK);
        }

        CustomerCoupons oldCustomerCoupons =
                customerCouponsMapper.selectByCenterId(customerCoupons.getCustomerId(), customerCoupons.getCenterCouponsId());

        if(oldCustomerCoupons != null){
            return new ResponseEntity<>(ResultModel.error(HAVE_TO_RECEIVED), HttpStatus.OK);
        }

        customerCoupons.setStatus(1);
        customerCoupons.setVersion(1);

        CustomerCouponsCenter customerCouponsCenter = customerCouponsCenterMapper.selectByPrimaryKey(customerCoupons.getCenterCouponsId());

        customerCoupons.setCouponsName(customerCouponsCenter.getCouponsName());
        customerCoupons.setCouponsType(customerCouponsCenter.getCouponsType());
        customerCoupons.setCouponsAmount(customerCouponsCenter.getCouponsAmount());
        customerCoupons.setMinimumAmount(customerCouponsCenter.getMinimumAmount());

        if(customerCouponsCenter.getOverdueType() == 1){
            customerCoupons.setTimeOverdue(customerCouponsCenter.getTimeOverdue());
        }else {
            String overdueTime =
                    DateUtil.FormatDate(DateUtil.getSpecifiedDayAfter(new Date(), customerCouponsCenter.getTimeLimit()), "yyyy-MM-dd");
            customerCoupons.setTimeOverdue(overdueTime);
        }
        customerCoupons.setCouponsStatus(0);
        customerCoupons.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));


        customerCouponsCenter.setCustomerGetNum(customerCouponsCenter.getCustomerGetNum() + 1);
        customerCouponsCenterMapper.updateByPrimaryKeySelective(customerCouponsCenter);

        customerCoupons.setGiveId(customerCouponsCenter.getCreateId());

        int i = customerCouponsMapper.insert(customerCoupons);
        if(i > 0) return new ResponseEntity<>(ResultModel.ok("优惠券领取成功"), HttpStatus.OK);
        return new ResponseEntity<>(ResultModel.error(ERROR,"优惠券领取失败"), HttpStatus.OK);
    }

}
