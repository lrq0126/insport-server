package com.dwgj.mlxydedicatedline.service;

import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.vo.GoodsPackVo;
import com.dwgj.mlxydedicatedline.vo.GoodsVo;
import com.dwgj.mlxydedicatedline.vo.pack.ConfirmShipmentReqVo;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CustomerPackService {

    List<GoodsPackVo> findPacking(Map<String, Object> paramMap);

    List<GoodsPackVo> findPacked(Map<String, Object> paramMap);

    ResponseEntity<ResultModel> confirmShipment(ConfirmShipmentReqVo confirmShipmentReqVo);

    int countFindPageNumber(Map<String, Object> paramMap, boolean isOutWare);

    //TODO 查询打包包裹的内容
    List<GoodsVo> findByBusinessNumberToGoodsNos(String businessNumber);

    /**
     * 拆包/重新打包
     * @param businessNumber
     * @param customerId
     * @return
     */
    int unpack(String businessNumber,int customerId);

    /**
     * 取消拆包
     * @param packId
     * @return
     */
    int cancelUnpack(Integer packId);

    /**
     * 修改收货地址
     * @param paramMap
     * @return
     */
    int updateAddress(Map<String,Object> paramMap);

    ResponseEntity<ResultModel> receipt(Integer packId);

    /**
     * 查看包裹明细
     * @param businessNumber
     * @return
     */
    ResponseEntity<ResultModel> getPackDetail(String businessNumber);

    ResponseEntity<ResultModel> getOrderReceivesAddress(String orderNumber);

    ResponseEntity<ResultModel> getTrajectoryAdvertising();
}
