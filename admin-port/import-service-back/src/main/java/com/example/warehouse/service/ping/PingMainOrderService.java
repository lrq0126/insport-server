package com.example.warehouse.service.ping;

import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.packVo.PackedReqVo;
import org.springframework.http.ResponseEntity;

public interface PingMainOrderService {

    ResponseEntity<ResultModel> printGoods(int id);

    /**
     * 查询子订单详情
     * @param id
     * @return
     */
    ResponseEntity<ResultModel> getPingMainOrderDetailed(int id);

    /**
     * 子订单打包
     * @param packedReqVo
     * @return
     */
    ResponseEntity<ResultModel> pingMainOrderPacked(PackedReqVo packedReqVo);
}
