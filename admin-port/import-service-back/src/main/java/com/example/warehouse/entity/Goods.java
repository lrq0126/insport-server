package com.example.warehouse.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *  货物表
 * @author lrq
 */
@Data
public class Goods implements Serializable {
    private Integer id;
    private String goodsNo;
    private String goodsName;
    private String customerNo;
    private String deliveryOrderNo;
    private String deliveryNo;
    private String packageType;
    private String location;
    private String scanType;
    private Integer packageNum;
    private String inStorageTime;
    private String outStorageTime;
    private String operatorCode;
    private String operatorName;
    private String createTime;
    private Integer createId;
    private String lastUpdateTime;
    private Integer lastUpdateId;
    private Integer status;
    private String message;
    private Integer goodsType;
    private String receiptTime; // 签收时间
    private String upTime; // 上架时间
    private String uper; // 上架员
    /**
     * 重量--2019年8月18日21:20:19，非必填
     */
    private Double kg;

    /**
     * 体积，2019年10月11日23:40:26，非必填
     */
    private Double vol;

    /**
     * 版本号,每次修改+1
     */
    private Integer version;

    /**
     * 打包时间
     */
    private String packTime;

    /**
     * 分拣状态 1、已分拣  0、未分拣
     */
    private Integer sortType;

    /**
     * 长
     */
    private Double length;

    /**
     * 宽
     */
    private Double width;

    /**
     * 高
     */
    private Double height;

    /**
     * 收货地址id
     */
    private Integer addressId;

    private Integer inPacked;

    private Integer isSensitive; // 是否敏感货物

    private Integer commercialAreaId;//区域仓库ID
    private String commercialAreaName;//区域仓库名称

    private String customerName;
    private String loginName;


    private static final long serialVersionUID = 1L;

}