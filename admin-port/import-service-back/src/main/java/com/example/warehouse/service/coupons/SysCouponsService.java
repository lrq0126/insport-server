package com.example.warehouse.service.coupons;

import com.example.warehouse.entity.coupons.SysCoupons;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.coupons.request.SysCouponsReqVo;
import org.springframework.http.ResponseEntity;

public interface SysCouponsService {

    /**
     * 查询优惠券
     * @param sysCouponsReqVo
     * @return
     */
    ResponseEntity<PageResultModel> getCouponsList(SysCouponsReqVo sysCouponsReqVo);

    ResponseEntity<ResultModel> getCouponsListNotPage();

    /**
     * 查询优惠券详情
     * @param id
     * @return
     */
    ResponseEntity<ResultModel> getCouponsById(int id);

    /**
     * 保存优惠券
     * @param sysCoupons 内容
     * @param isAdd      是否新增
     * @return
     */
    ResponseEntity<ResultModel> saveCoupons(SysCoupons sysCoupons, boolean isAdd);

    /**
     * 删除优惠券
     * @param id
     * @return
     */
    ResponseEntity<ResultModel> deleteCoupons(int id);

    /**
     * 启停用
     * @param id
     * @return
     */
    ResponseEntity<ResultModel> isEnable(int id);

    ResponseEntity<ResultModel> batchGiveCoupons(int sysCouponsId, String groupIds);

    ResponseEntity<ResultModel> giveCustomerCoupons(int sysCouponsId, String customerIds);

    void deleteOverdueCoupons();

}
