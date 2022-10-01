package com.dwgj.mlxydedicatedline.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (TimeLimitActivity)实体类
 *
 * @author makejava
 * @since 2020-04-05 18:28:13
 */
public class TimeLimitActivity implements Serializable {
    private static final long serialVersionUID = -29811468159661991L;
    
    private Long id;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 状态
    */
    private Integer status;
    /**
    * 活动名称
    */
    private String activityName;
    /**
    * 参与客户id
    */
    private Long customerId;
    /**
    * 货物代码
    */
    private String goodsNo;
    /**
    * 备注
    */
    private String remarks;
    /**
    * 路线id
    */
    private Long transportRouteId;
    
    private String goodsName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getTransportRouteId() {
        return transportRouteId;
    }

    public void setTransportRouteId(Long transportRouteId) {
        this.transportRouteId = transportRouteId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

}