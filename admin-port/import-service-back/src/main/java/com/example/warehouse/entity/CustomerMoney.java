package com.example.warehouse.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * customer_money
 * @author
 */
public class CustomerMoney implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 客户id
     */
    private String customerId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 余额
     */
    private BigDecimal amount;

    /**
     * 最新更新时间
     */
    private String lastUpdateTime;

    /**
     * 版本号，每次修改+1
     */
    private Integer version;

    /**
     * 状态码  0、已删除 1、正常
     */
    private Integer status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
