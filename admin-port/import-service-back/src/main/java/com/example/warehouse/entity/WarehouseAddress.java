package com.example.warehouse.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 仓库地址(WarehouseAddress)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2022/7/5 16:24
 */
@Data
public class WarehouseAddress {

    private Integer id;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 创建人
     */
    private Integer createId;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 修改人
     */
    private Integer updateId;

    /**
     * 状态 1、正常 -1、删除
     */
    private Integer status;

    /**
     * 版本号 每次修改+1
     */
    private Integer version;

    /**
     * 收件人
     */
    private String addressee;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话
     */
    private String phoneNumber;
    /**
     * 异常联系电话
     */
    private String abnormalRelation;
    /**
     * 邮编
     */
    private String postcode;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 是否启用 1、启用；-1、不启用
     */
    private Integer isEnable;

    private Integer commercialAreaId;

    private String commercialAreaName;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}