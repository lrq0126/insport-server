package com.dwgj.mlxydedicatedline.service.coupons;

import com.dwgj.mlxydedicatedline.entity.coupons.CustomerCoupons;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import org.springframework.http.ResponseEntity;

public interface CustomerCouponsService {

    ResponseEntity<ResultModel> getMyCouponsList();

    ResponseEntity<ResultModel> receiveCoupons(CustomerCoupons customerCoupons);
}
