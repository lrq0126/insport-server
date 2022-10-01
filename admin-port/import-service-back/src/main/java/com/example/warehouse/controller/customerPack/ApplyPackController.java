package com.example.warehouse.controller.customerPack;

import com.example.warehouse.log.SysLog;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.GoodsService;
import com.example.warehouse.service.agentApplyPack.ApplyPackService;
import com.example.warehouse.vo.applyPack.GoodsPackingVo;
import com.example.warehouse.vo.applyPack.GoodsRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/applyPack")
@Slf4j
public class ApplyPackController {

    @Autowired
    private ApplyPackService applyPackService;

    // TODO 根据客户id查询该客户的货物信息
    @PostMapping("/getGoods")
    public ResponseEntity<ResultModel> getGoods(GoodsRequestVo goodsRequestVo) {
        return applyPackService.getGoods(goodsRequestVo);
    }

    // TODO 路线选择（可根据国家、路线类型、路线名称进行筛选）    预计价格
    @PostMapping("/getRoutePrice")
    public  ResponseEntity<ResultModel> getRoutePrice(GoodsRequestVo goodsRequestVo) {
        return applyPackService.getRoutePrice(goodsRequestVo);
    }

    // TODO 申请打包
    @PostMapping("/agentApplyPacking")
    @SysLog(value = "后台代理申请打包",type = SysLog.Type.ADD,contentId = "goodsPackingVo.goodsIdList")
    public ResponseEntity<ResultModel> agentApplyPacking(GoodsPackingVo goodsPackingVo){
        return applyPackService.goodsPacking(goodsPackingVo);
    }

}
