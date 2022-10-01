package com.dwgj.mlxydedicatedline.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsVo implements Serializable {
    private Integer id;

    /**
     * 货物代码
     */
    private String goodsNo;

    /**
     * 货物名称
     */
    private String goodsName;

    /**
     * 客户代码
     */
    private String customerNo;
    /**
     * 客户id
     */
    private String customerId;

    /**
     * 快递订单号
     */
    private String deliveryOrderNo;

    /**
     * 快递来源
     */
    private String deliveryNo;

    /**
     * 包装类型
     */
    private String packageType;

    /**
     * 仓库位置
     */
    private String location;

    /**
     * 货物扫描方式--1手动输入，2扫码枪
     */
    private String scanType;

    /**
     * 包裹数量
     */
    private Integer packageNum;

    /**
     * 入仓时间
     */
    private String inStorageTime;

    /**
     * 出仓时间
     */
    private String outStorageTime;

    /**
     * 录入员代码
     */
    private String operatorCode;

    /**
     * 录入员名称
     */
    private String operatorName;

    /**
     * 创建时间
     */
    private String createTime;

    private Integer createId;

    /**
     * 更新时间
     */
    private String lastUpdateTime;

    private Integer lastUpdateId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 备注信息
     */
    private String message;

    /**
     * 货物状态--1入库(待打包)，2已出库，3预录入（客户下单），6、待打包， 7、已打包，8、待出库
     */
    private String goodsType;

    /**
     * 重量
     */
    private Double kg;

    /**
     * 体积
     */
    private Double vol;

    private Integer version;

    /**
     * 打包时间
     */
    private String packTime;

    /**
     * 分拣状态 0、未分拣  1、已分拣
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

    private String address;

    private String addressee;

    private String deliveryName;
    private Integer commercialAreaId;
    private String commercialAreaName;

    private static final long serialVersionUID = 1L;

}
