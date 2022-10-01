package com.example.warehouse.service.agentApplyPack;

import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.applyPack.GoodsPackingVo;
import com.example.warehouse.vo.applyPack.GoodsRequestVo;
import org.springframework.http.ResponseEntity;

/**
 * 代理客户申请打包相关接口
 */
public interface ApplyPackService {


    /**
     * 获取货物信息
     * @param goodsRequestVo
     * @return
     */
    ResponseEntity<ResultModel> getGoods(GoodsRequestVo goodsRequestVo);

    /**
     * 获取路线价格
     * @param goodsRequestVo
     * @return
     */
    ResponseEntity<ResultModel> getRoutePrice(GoodsRequestVo goodsRequestVo);

    /**
     * 申请打包
     * @param goodsPackingVo
     * @return
     */
    ResponseEntity<ResultModel> goodsPacking(GoodsPackingVo goodsPackingVo);

}
