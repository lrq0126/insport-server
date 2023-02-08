package com.example.warehouse.vo.customerPack;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

@Data
public class CustomerOrderReqVo extends PageVo {

    private String customerNo;

    private Integer customerId;

    private String deliveryOrderNo;

    private String orderNumber;

    private String businessNumber;

}
