package com.example.warehouse.controller.coupons;

import com.example.warehouse.entity.coupons.SysCoupons;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.coupons.SysCouponsService;
import com.example.warehouse.vo.coupons.request.SysCouponsReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/sysCoupons")
public class SysCouponsController {

    @Autowired
    private SysCouponsService sysCouponsService;

    /**
     * 查询优惠券列表
     * @param sysCouponsReqVo
     * @return
     */
    @PostMapping("getCouponsList")
    public ResponseEntity<PageResultModel> getCouponsList(SysCouponsReqVo sysCouponsReqVo){
        return sysCouponsService.getCouponsList(sysCouponsReqVo);
    }

    @PostMapping("getCouponsById")
    public ResponseEntity<ResultModel> getCouponsById(int id){
        return sysCouponsService.getCouponsById(id);
    }

    /**
     * 新增优惠券
     * @param sysCoupons
     * @return
     */
    @PostMapping("createCoupons")
    public ResponseEntity<ResultModel> createCoupons(@Valid SysCoupons sysCoupons){
        return sysCouponsService.saveCoupons(sysCoupons, true);
    }

    /**
     * 编辑优惠券
     * @param sysCoupons
     * @return
     */
    @PostMapping("editCoupons")
    public ResponseEntity<ResultModel> editCoupons(SysCoupons sysCoupons){
        return sysCouponsService.saveCoupons(sysCoupons, false);
    }

    /**
     * 删除优惠券
     * @param id
     * @return
     */
    @PostMapping("deleteCoupons")
    public ResponseEntity<ResultModel> deleteCoupons(@RequestParam("id") int id){
        return sysCouponsService.deleteCoupons(id);
    }

    /**
     * 停用/启用优惠券
     */
    @PostMapping("isEnable")
    public ResponseEntity<ResultModel> isEnable(@RequestParam("id") int id){
        return sysCouponsService.isEnable(id);
    }


    /**
     * 给分组分发优惠券
     * @param sysCouponsId
     * @param groupIds
     * @return
     */
    @PostMapping("batchGiveCoupons")
    public ResponseEntity<ResultModel> batchGiveCoupons(@RequestParam("sysCouponsId") int sysCouponsId, @RequestParam("groupIds") String groupIds){
        return sysCouponsService.batchGiveCoupons(sysCouponsId, groupIds);
    }



    /**
     * 给客户下发优惠券
     * @param sysCouponsId
     * @param customerIds
     * @return
     */
    @PostMapping("giveCustomerCoupons")
    public ResponseEntity<ResultModel> giveCustomerCoupons(@RequestParam("sysCouponsId") int sysCouponsId, @RequestParam("customerIds") String customerIds){
        return sysCouponsService.giveCustomerCoupons(sysCouponsId, customerIds);
    }
}
