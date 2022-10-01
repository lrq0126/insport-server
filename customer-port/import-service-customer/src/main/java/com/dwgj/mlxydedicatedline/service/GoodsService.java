package com.dwgj.mlxydedicatedline.service;

import com.dwgj.mlxydedicatedline.entity.Goods;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.vo.GoodsVo;
import com.dwgj.mlxydedicatedline.vo.goods.ConfirmPackReqVo;
import com.dwgj.mlxydedicatedline.vo.route.RouteQuotationVo;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface GoodsService {
    ResponseEntity<ResultModel> getGoodsCommercialArea(HttpServletRequest httpServletRequest);

    ResponseEntity<ResultModel> selectGoodsInfoByDeliveryOrderNo(String deliveryOrderNo);

    List<GoodsVo> findGoods(Map<String,Object> map);

    int del(Map<String, Object> map);

    int update(Goods goods);

    int countFindAll(Map<String, Object> map);

//    List<RouteQuotationVo> applyPack(Map<String, Object> map,List<GoodsVo> goodsVoList);
    ResponseEntity<ResultModel> applyPack(Map<String, Object> map);

    ResponseEntity<ResultModel> confirmPacking(ConfirmPackReqVo confirmPackReqVo);

    /**
     * 根据 货物代码 获取货物信息
     * @param goodsNos
     * @return
     */
    List<GoodsVo> findByGoodsNos(List<String> goodsNos);

    ResponseEntity<ResultModel> checkPicture(String imageType, String deliveryOrderNo);

}
