package com.example.warehouse.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (DwReceipt)实体类
 *
 * @author lrq
 * @since 2020-05-22 13:31:53
 */
@Data
public class DwReceipt implements Serializable {
    private static final long serialVersionUID = -56030539944768205L;
    
    private Integer id;
    /**
    * 创建时间/签收时间
    */
    private Date createTime;
    private Integer createId;
    /**
    * 修改时间
    */
    private Date updateTime;
    /**
    * 数据状态   1、正常   0、已删除
    */
    private Integer status;
    /**
    * 版本号，每次修改+1
    */
    private Integer version;
    /**
    * 快递单号
    */
    private String deliveryOrderNo;
    /**
    * 快递公司编号
    */
    private String deliveryNo;
    /**
    * 备注信息
    */
    private String remarks;

}