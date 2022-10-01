package com.example.warehouse.entity.sys;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 系统统计表(SysStatistical)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2021/9/16 16:5
 */
@ApiOperation(value = "系统统计表")
@Data
public class SysStatistical {

    @ApiModelProperty(value = "Id")
    private Integer id;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "使用状态")
    private Integer status;
    @ApiModelProperty(value = "版本号")
    private Integer version;
    @ApiModelProperty(value = "新增客户数量")
    private Integer newCustomerNum;
    @ApiModelProperty(value = "充值次数")
    private Integer rechargeNum;
    @ApiModelProperty(value = "充值总金额")
    private BigDecimal rechargeAmountSum;
    @ApiModelProperty(value = "仓库内的包裹客户数量")
    private Integer wareCustomerSum;
    @ApiModelProperty(value = "已出库客户数量")
    private Integer outWareCustomerSum;
    @ApiModelProperty(value = "货物入库数量")
    private Integer inWareNum;
    @ApiModelProperty(value = "预录入数量")
    private Integer reInWareNum;
    @ApiModelProperty(value = "申请打包数量")
    private Integer applyPackNum;
    @ApiModelProperty(value = "已打包数量")
    private Integer packedNum;
    @ApiModelProperty(value = "仓库内货物数量(入库)")
    private Integer goodsNum;
    @ApiModelProperty(value = "仓库内货物立方数(入库)")
    private Double goodsWeightSum;
    @ApiModelProperty(value = "仓库内货物重量(入库)")
    private Double goodsVolSum;
    @ApiModelProperty(value = "入库实际重量")
    private Double inWareWeightSum;
    @ApiModelProperty(value = "出库实际重量")
    private Double outWareWeightSum;
    @ApiModelProperty(value = "出库数量")
    private Integer outWareNum;
    @ApiModelProperty(value = "出库货物数量")
    private Integer outWareGoodsNum;
    @ApiModelProperty(value = "签收数量")
    private Integer receiptNum;
    @ApiModelProperty(value = "支付订单金额")
    private BigDecimal payOrderAmountSum;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SysStatistical other = (SysStatistical) that;
        return
                (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                        && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                        && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                        && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                        && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
                        && (this.getNewCustomerNum() == null ? other.getNewCustomerNum() == null : this.getNewCustomerNum().equals(other.getNewCustomerNum()))
                        && (this.getRechargeNum() == null ? other.getRechargeNum() == null : this.getRechargeNum().equals(other.getRechargeNum()))
                        && (this.getRechargeAmountSum() == null ? other.getRechargeAmountSum() == null : this.getRechargeAmountSum().equals(other.getRechargeAmountSum()))
                        && (this.getWareCustomerSum() == null ? other.getWareCustomerSum() == null : this.getWareCustomerSum().equals(other.getWareCustomerSum()))
                        && (this.getInWareNum() == null ? other.getInWareNum() == null : this.getInWareNum().equals(other.getInWareNum()))
                        && (this.getReInWareNum() == null ? other.getReInWareNum() == null : this.getReInWareNum().equals(other.getReInWareNum()))
                        && (this.getApplyPackNum() == null ? other.getApplyPackNum() == null : this.getApplyPackNum().equals(other.getApplyPackNum()))
                        && (this.getGoodsNum() == null ? other.getGoodsNum() == null : this.getGoodsNum().equals(other.getGoodsNum()))
                        && (this.getGoodsWeightSum() == null ? other.getGoodsWeightSum() == null : this.getGoodsWeightSum().equals(other.getGoodsWeightSum()))
                        && (this.getGoodsVolSum() == null ? other.getGoodsVolSum() == null : this.getGoodsVolSum().equals(other.getGoodsVolSum()))
                        && (this.getOutWareWeightSum() == null ? other.getOutWareWeightSum() == null : this.getOutWareWeightSum().equals(other.getOutWareWeightSum()))
                        && (this.getOutWareNum() == null ? other.getOutWareNum() == null : this.getOutWareNum().equals(other.getOutWareNum()))
                        && (this.getReceiptNum() == null ? other.getReceiptNum() == null : this.getReceiptNum().equals(other.getReceiptNum()))
                        && (this.getPayOrderAmountSum() == null ? other.getPayOrderAmountSum() == null : this.getPayOrderAmountSum().equals(other.getPayOrderAmountSum()))
                ;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getNewCustomerNum() == null) ? 0 : getNewCustomerNum().hashCode());
        result = prime * result + ((getRechargeNum() == null) ? 0 : getRechargeNum().hashCode());
        result = prime * result + ((getRechargeAmountSum() == null) ? 0 : getRechargeAmountSum().hashCode());
        result = prime * result + ((getWareCustomerSum() == null) ? 0 : getWareCustomerSum().hashCode());
        result = prime * result + ((getInWareNum() == null) ? 0 : getInWareNum().hashCode());
        result = prime * result + ((getReInWareNum() == null) ? 0 : getReInWareNum().hashCode());
        result = prime * result + ((getApplyPackNum() == null) ? 0 : getApplyPackNum().hashCode());
        result = prime * result + ((getGoodsNum() == null) ? 0 : getGoodsNum().hashCode());
        result = prime * result + ((getGoodsWeightSum() == null) ? 0 : getGoodsWeightSum().hashCode());
        result = prime * result + ((getGoodsVolSum() == null) ? 0 : getGoodsVolSum().hashCode());
        result = prime * result + ((getOutWareWeightSum() == null) ? 0 : getOutWareWeightSum().hashCode());
        result = prime * result + ((getOutWareNum() == null) ? 0 : getOutWareNum().hashCode());
        result = prime * result + ((getReceiptNum() == null) ? 0 : getReceiptNum().hashCode());
        result = prime * result + ((getReceiptNum() == null) ? 0 : getReceiptNum().hashCode());
        result = prime * result + ((getPayOrderAmountSum() == null) ? 0 : getPayOrderAmountSum().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", status=").append(status);
        sb.append(", version=").append(version);
        sb.append(", newCustomerNum=").append(newCustomerNum);
        sb.append(", rechargeNum=").append(rechargeNum);
        sb.append(", rechargeAmountSum=").append(rechargeAmountSum);
        sb.append(", wareCustomerSum=").append(wareCustomerSum);
        sb.append(", inWareNum=").append(inWareNum);
        sb.append(", reInWareNum=").append(reInWareNum);
        sb.append(", applyPackNum=").append(applyPackNum);
        sb.append(", goodsNum=").append(goodsNum);
        sb.append(", goodsWeightSum=").append(goodsWeightSum);
        sb.append(", goodsVolSum=").append(goodsVolSum);
        sb.append(", outWareWeightSum=").append(outWareWeightSum);
        sb.append(", outWareNum=").append(outWareNum);
        sb.append(", receiptNum=").append(receiptNum);
        sb.append(", payOrderAmountSum=").append(payOrderAmountSum);
        sb.append("]");
        return sb.toString();
    }
}