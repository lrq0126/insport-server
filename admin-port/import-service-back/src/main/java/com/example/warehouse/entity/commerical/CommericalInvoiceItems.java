package com.example.warehouse.entity.commerical;

import java.io.Serializable;
import java.util.Date;

/**
 * (CommericalInvoiceItems)实体类
 *
 * @author makejava
 * @since 2020-07-29 19:10:44
 */
public class CommericalInvoiceItems implements Serializable {
    private static final long serialVersionUID = -86483159472886735L;
    /**
     * id
     */
    private Long id;
    /**
     * 发票信息id
     */
    private Long commericalId;

    private String descriptionOfGoods;
    /**
     * 海关编码
     */
    private String hsCode;
    /**
     * 材质
     */
    private String material;
    /**
     * 用途
     */
    private String purpose;
    /**
     * 数量
     */
    private String qty;
    /**
     * 单价
     */
    private String unitValue;
    /**
     * 总价
     */
    private String totalValue;
    /**
     * 排序
     */
    private Integer sortNo;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;

    private Integer createId;
    /**
     * 更新时间
     */
    private Date lastUpdateTime;

    private Integer lastUpdateId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommericalId() {
        return commericalId;
    }

    public void setCommericalId(Long commericalId) {
        this.commericalId = commericalId;
    }

    public String getDescriptionOfGoods() {
        return descriptionOfGoods;
    }

    public void setDescriptionOfGoods(String descriptionOfGoods) {
        this.descriptionOfGoods = descriptionOfGoods;
    }

    public String getHsCode() {
        return hsCode;
    }

    public void setHsCode(String hsCode) {
        this.hsCode = hsCode;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
    }

    public String getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(String totalValue) {
        this.totalValue = totalValue;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getLastUpdateId() {
        return lastUpdateId;
    }

    public void setLastUpdateId(Integer lastUpdateId) {
        this.lastUpdateId = lastUpdateId;
    }

}