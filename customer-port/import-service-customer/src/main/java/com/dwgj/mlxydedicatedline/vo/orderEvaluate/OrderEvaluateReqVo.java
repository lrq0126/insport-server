package com.dwgj.mlxydedicatedline.vo.orderEvaluate;

import com.dwgj.mlxydedicatedline.vo.PageVo;
import lombok.Data;

@Data
public class OrderEvaluateReqVo extends PageVo {

    private Integer customerId;

    /**
     * evaluateStatus 评价类型 1、已评价 2、未评价
     */
    private Integer evaluateStatus;

    private String orderNumber;


}
