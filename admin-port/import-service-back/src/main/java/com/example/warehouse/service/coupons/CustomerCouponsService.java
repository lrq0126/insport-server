package com.example.warehouse.service.coupons;

import com.example.warehouse.entity.coupons.CustomerCoupons;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.coupons.request.CustomerCouponsReqVo;
import org.springframework.http.ResponseEntity;

public interface CustomerCouponsService {

    /**
     * 查询已发放的优惠券列表
     * @param customerCouponsReqVo
     * @return
     */
    ResponseEntity<PageResultModel> getIssuedCouponsList(CustomerCouponsReqVo customerCouponsReqVo);

    /**
     * 查询客户优惠券
     * @param customerCouponsReqVo
     * @return
     */
    ResponseEntity<PageResultModel> getCustomerCouponsList(CustomerCouponsReqVo customerCouponsReqVo);

    /**
     * 删除某个客户的优惠券
     * @param id
     * @return
     */
    ResponseEntity<ResultModel> removeCoupons(int id);

    ResponseEntity<ResultModel> removeCustomerCoupons(String customerCouponsIds);

    ResponseEntity<ResultModel> removeCouponsCenter(int couponsCenterId);

    ResponseEntity<ResultModel> getCustomerCoupons(int customerId);

}
