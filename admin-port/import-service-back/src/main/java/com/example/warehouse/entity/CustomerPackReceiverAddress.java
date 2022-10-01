package com.example.warehouse.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (CustomerPackReceiverAddress)实体类
 *
 * @author makejava
 * @since 2020-03-09 17:15:07
 */
@Data
public class CustomerPackReceiverAddress implements Serializable {
    private static final long serialVersionUID = 252287902600885962L;

    private Integer id;
    /**
    * 打包id
    */
    private Integer customerPackId;
    /**
    * 收货地址
    */
    private String receiverAddress;
    /**
    * 状态 1、正常  0、已删除
    */
    private Integer status;
    /**
    * 联系电话
    */
    private String phoneNumber;
    /**
    * 收件人姓名
    */
    private String addressee;

    private String code;

    private String provinces;

}