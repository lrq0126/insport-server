package com.example.warehouse.service;

import com.example.warehouse.entity.CustomerPackNumber;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.customerPack.CustomerPackNumberVo;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CustomerPackNumberService {

    List<CustomerPackNumber> findToBeShipped(Map<String, Object> map);

    int countSelectAll(Map<String, Object> map);

    /**
     * 根据 包裹 id 获取 其中的子包裹
     * @param customerPackId
     * @return
     */
    List<CustomerPackNumberVo> findByCustomerPackId(Integer customerPackId);

    CustomerPackNumberVo findByCustomerPackNumberPackOrder(Map<String,Object> paramMap);

    // 根据订单号查询货物
    List<GoodsVo> findGoodsByPackOrder(Map<String,Object> paramMap);

    ResponseEntity<ResultModel> findSonPackListByBusinessNumber(String businessNumber);

}
