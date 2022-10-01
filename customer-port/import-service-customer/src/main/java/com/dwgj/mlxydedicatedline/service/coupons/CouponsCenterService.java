package com.dwgj.mlxydedicatedline.service.coupons;

import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import org.springframework.http.ResponseEntity;

public interface CouponsCenterService {

    ResponseEntity<ResultModel> getCouponsCenterList(boolean isExchange);

}
