package com.dwgj.mlxydedicatedline.controller.coupons;

import com.dwgj.mlxydedicatedline.config.UserThreadContext;
import com.dwgj.mlxydedicatedline.entity.Customer;
import com.dwgj.mlxydedicatedline.entity.coupons.CustomerCoupons;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.coupons.CouponsCenterService;
import com.dwgj.mlxydedicatedline.service.coupons.CustomerCouponsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("customerCoupons")
public class CustomerCouponsController {

    @Autowired
    private CustomerCouponsService customerCouponsService;

    @Autowired
    private CouponsCenterService couponsCenterService;



    // 查询我的优惠券
    @GetMapping(value = "getMyCouponsList")
    public ResponseEntity<ResultModel> getMyCouponsList(){
        return customerCouponsService.getMyCouponsList();
    }

    // 查询领券中心的可领取的优惠券
    @GetMapping(value = "getCouponsCenterList")
    public ResponseEntity<ResultModel> getCouponsCenterList(){
        return couponsCenterService.getCouponsCenterList(false);
    }

    // 查询领券中心的可兑换的优惠券
    @GetMapping(value = "getCouponsCenterExchangeList")
    public ResponseEntity<ResultModel> getCouponsCenterExchangeList(){
        return couponsCenterService.getCouponsCenterList(true);
    }

    // 领取优惠券
    @PostMapping(value = "receiveCoupons")
    public ResponseEntity<ResultModel> receiveCoupons(@RequestBody CustomerCoupons customerCoupons){
        return customerCouponsService.receiveCoupons(customerCoupons);
    }

}
