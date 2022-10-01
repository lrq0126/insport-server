package com.example.warehouse.vo.receipt;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DwReceiptVo implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 创建时间/签收时间
     */
    private String receiptTime;
    private String createId;
    /**
     * 快递单号
     */
    private String deliveryOrderNo;
    /**
     * 快递公司编号
     */
    private String deliveryNo;
    /**
     * 快递公司
     */
    private String deliveryName;
    /**
     * 备注信息
     */
    private String remarks;

}
