package com.example.warehouse.controller.coupons;

import com.example.warehouse.entity.coupons.CustomerCoupons;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.coupons.CustomerCouponsService;
import com.example.warehouse.vo.coupons.request.CustomerCouponsReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customerCoupons")
public class CustomerCouponsController {

    @Autowired
    private CustomerCouponsService customerCouponsService;

    /**
     * 已发放的优惠券列表
     * @param customerCouponsReqVo
     * @return
     */
    @PostMapping("getIssuedCouponsList")
    public ResponseEntity<PageResultModel> getIssuedCouponsList(CustomerCouponsReqVo customerCouponsReqVo){
        return customerCouponsService.getIssuedCouponsList(customerCouponsReqVo);
    }

    /**
     * 查询客户优惠券列表
     * @param customerCouponsReqVo
     * @return
     */
    @PostMapping("getCustomerCouponsList")
    public ResponseEntity<PageResultModel> getCustomerCouponsList(CustomerCouponsReqVo customerCouponsReqVo){
        return customerCouponsService.getCustomerCouponsList(customerCouponsReqVo);
    }

    /**
     * 删除某张已领取的优惠券
     * @param id
     * @return
     */
    @PostMapping("removeCoupons")
    public ResponseEntity<ResultModel> removeCoupons(@RequestParam("id") Integer id){
        return customerCouponsService.removeCoupons(id);
    }

    /**
     * 删除某张已领取的优惠券（批量）
     * @param customerCouponsIds
     * @return
     */
    @PostMapping("removeCustomerCoupons")
    public ResponseEntity<ResultModel> removeCustomerCoupons(@RequestParam("customerCouponsIds") String customerCouponsIds){
        return customerCouponsService.removeCustomerCoupons(customerCouponsIds);
    }

    /**
     * 删除某个领券中心的优惠券
     * @param couponsCenterId
     * @return
     */
    @PostMapping("removeCouponsCenter")
    public ResponseEntity<ResultModel> removeCouponsCenter(@RequestParam("couponsCenterId") int couponsCenterId){
        return customerCouponsService.removeCouponsCenter(couponsCenterId);
    }

    @PostMapping("getCustomerCoupons")
    public ResponseEntity<ResultModel> getCustomerCoupons(int customerId){
        return customerCouponsService.getCustomerCoupons(customerId);
    }
}
