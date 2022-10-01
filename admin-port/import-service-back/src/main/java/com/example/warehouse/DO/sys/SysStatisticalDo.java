package com.example.warehouse.DO.sys;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SysStatisticalDo {

    private Integer commercialAreaId;

    // 货架上存在货物的客户(CustomerNo)
    private List<String> inShelvesCustomerNoList;
    // 出库客户数量(CustomerId)
    private List<Integer> outWareCustomerIdList;
    // 入仓票数(GoodsId)
    private List<Integer> inWareGoodsIdList;
    // 新增人数(CustomerId)
    private List<Integer> newAddCustomerIdList;
    // 预报包裹数(GoodsId)
    private List<Integer> reInWareGoodsIdList;
    // 申请打包数(PackId)
    private List<Integer> applyPackIdList;
    // 打包数(PackId)
    private List<Integer> packedPackIdList;
    // 充值数(customerAccountId)
    private List<Integer> rechargeAccountIdList;
    // 充值金额
    private List<BigDecimal> accountSumList;
    // 仓库内包裹总数
    private List<Integer> wareGoodsIdList;
    // 仓库内总重量
    private List<Double> wareGoodsWeightList;
    // 仓库内总立方数
    private List<Double> wareGoodsVolList;
    // 昨日入库实际重量
    private List<Double> inWareGoodsWeightList;
    // 昨日出库实际重量
    private List<Double> outWarePackWeightList;
    // 昨天已发货订单数量
    private List<Integer> deliverPackIdList;
    // 昨天已发货的货物数量
    private List<Integer> deliverGoodsIdList;

}
