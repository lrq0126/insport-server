package com.example.warehouse.entity.ping;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ping_main_order
 * @author 
 */
@Data
public class PingMainOrder implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 拼团id
     */
    private Integer pId;

    /**
     * 客户id
     */
    private Integer customerId;
    /**
     * 渠道id
     */
    private Integer routeId;

    /**
     * 成团时间
     */
    private String groupTime;

    /**
     * 打包时间
     */
    private String packTime;

    /**
     * 打包状态：0否，1打包好，2付款完毕，3已发货
     */
    private Integer packType;

    /**
     * 状态
     */
    private Integer status;

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
     * 拣货时间
     */
    private String pickTime;

    /**
     * 拣货员
     */
    private String picker;

    /**
     * 是否拣货，0否，1是
     */
    private Integer isPicked;

    /**
     * 实际价格
     */
    private BigDecimal auctualPrice;

    /**
     * 优惠价
     */
    private BigDecimal discount;

    /**
     * 预报价
     */
    private BigDecimal preQoutedPrice;

    /**
     * 内部备注
     */
    private String insideMessage;

    /**
     * 受理状态
     */
    private Integer accepted;

    /**
     * 受理时间
     */
    private String acceptedTime;

    /**
     * 受理员
     */
    private String accepter;

    /**
     * 支付时间
     */
    private String payTime;

    /**
     * 是否支付，0否1是
     */
    private Integer isPaid;

    /**
     * 是否打印清单，0否，1是
     */
    private Integer isPrinted;

    /**
     * 收货地址，暂时为空，使用团长的地址
     */
    private Integer addressId;

    /**
     * 结算重量
     */
    private Double settlementWeight;

    private Double actualWeight;

    /**
     * 结算体积
     */
    private Double settlementVol;

    private Double actualVol;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 转单号
     */
    private String agentNumber;

    private BigDecimal incidental;

    /**
     * 是否敏感货，0否1是
     */
    private Integer isSensitive;

    private Integer goodsNumber;

    private Long serialVersionUID = 1L;

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
        PingMainOrder other = (PingMainOrder) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPId() == null ? other.getPId() == null : this.getPId().equals(other.getPId()))
            && (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
            && (this.getGroupTime() == null ? other.getGroupTime() == null : this.getGroupTime().equals(other.getGroupTime()))
            && (this.getPackTime() == null ? other.getPackTime() == null : this.getPackTime().equals(other.getPackTime()))
            && (this.getPackType() == null ? other.getPackType() == null : this.getPackType().equals(other.getPackType()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
            && (this.getLastUpdateTime() == null ? other.getLastUpdateTime() == null : this.getLastUpdateTime().equals(other.getLastUpdateTime()))
            && (this.getLastUpdateId() == null ? other.getLastUpdateId() == null : this.getLastUpdateId().equals(other.getLastUpdateId()))
            && (this.getPickTime() == null ? other.getPickTime() == null : this.getPickTime().equals(other.getPickTime()))
            && (this.getPicker() == null ? other.getPicker() == null : this.getPicker().equals(other.getPicker()))
            && (this.getIsPicked() == null ? other.getIsPicked() == null : this.getIsPicked().equals(other.getIsPicked()))
            && (this.getAuctualPrice() == null ? other.getAuctualPrice() == null : this.getAuctualPrice().equals(other.getAuctualPrice()))
            && (this.getDiscount() == null ? other.getDiscount() == null : this.getDiscount().equals(other.getDiscount()))
            && (this.getPreQoutedPrice() == null ? other.getPreQoutedPrice() == null : this.getPreQoutedPrice().equals(other.getPreQoutedPrice()))
            && (this.getInsideMessage() == null ? other.getInsideMessage() == null : this.getInsideMessage().equals(other.getInsideMessage()))
            && (this.getAccepted() == null ? other.getAccepted() == null : this.getAccepted().equals(other.getAccepted()))
            && (this.getAcceptedTime() == null ? other.getAcceptedTime() == null : this.getAcceptedTime().equals(other.getAcceptedTime()))
            && (this.getAccepter() == null ? other.getAccepter() == null : this.getAccepter().equals(other.getAccepter()))
            && (this.getPayTime() == null ? other.getPayTime() == null : this.getPayTime().equals(other.getPayTime()))
            && (this.getIsPaid() == null ? other.getIsPaid() == null : this.getIsPaid().equals(other.getIsPaid()))
            && (this.getIsPrinted() == null ? other.getIsPrinted() == null : this.getIsPrinted().equals(other.getIsPrinted()))
            && (this.getAddressId() == null ? other.getAddressId() == null : this.getAddressId().equals(other.getAddressId()))
            && (this.getSettlementWeight() == null ? other.getSettlementWeight() == null : this.getSettlementWeight().equals(other.getSettlementWeight()))
            && (this.getSettlementVol() == null ? other.getSettlementVol() == null : this.getSettlementVol().equals(other.getSettlementVol()))
            && (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
            && (this.getOrderNumber() == null ? other.getOrderNumber() == null : this.getOrderNumber().equals(other.getOrderNumber()))
            && (this.getAgentNumber() == null ? other.getAgentNumber() == null : this.getAgentNumber().equals(other.getAgentNumber()))
            && (this.getIsSensitive() == null ? other.getIsSensitive() == null : this.getIsSensitive().equals(other.getIsSensitive()))
            && (this.getRouteId() == null ? other.getRouteId() == null : this.getRouteId().equals(other.getRouteId()))
            && (this.getGoodsNumber() == null ? other.getGoodsNumber() == null : this.getGoodsNumber().equals(other.getGoodsNumber()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPId() == null) ? 0 : getPId().hashCode());
        result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
        result = prime * result + ((getGroupTime() == null) ? 0 : getGroupTime().hashCode());
        result = prime * result + ((getPackTime() == null) ? 0 : getPackTime().hashCode());
        result = prime * result + ((getPackType() == null) ? 0 : getPackType().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateId() == null) ? 0 : getCreateId().hashCode());
        result = prime * result + ((getLastUpdateTime() == null) ? 0 : getLastUpdateTime().hashCode());
        result = prime * result + ((getLastUpdateId() == null) ? 0 : getLastUpdateId().hashCode());
        result = prime * result + ((getPickTime() == null) ? 0 : getPickTime().hashCode());
        result = prime * result + ((getPicker() == null) ? 0 : getPicker().hashCode());
        result = prime * result + ((getIsPicked() == null) ? 0 : getIsPicked().hashCode());
        result = prime * result + ((getAuctualPrice() == null) ? 0 : getAuctualPrice().hashCode());
        result = prime * result + ((getDiscount() == null) ? 0 : getDiscount().hashCode());
        result = prime * result + ((getPreQoutedPrice() == null) ? 0 : getPreQoutedPrice().hashCode());
        result = prime * result + ((getInsideMessage() == null) ? 0 : getInsideMessage().hashCode());
        result = prime * result + ((getAccepted() == null) ? 0 : getAccepted().hashCode());
        result = prime * result + ((getAcceptedTime() == null) ? 0 : getAcceptedTime().hashCode());
        result = prime * result + ((getAccepter() == null) ? 0 : getAccepter().hashCode());
        result = prime * result + ((getPayTime() == null) ? 0 : getPayTime().hashCode());
        result = prime * result + ((getIsPaid() == null) ? 0 : getIsPaid().hashCode());
        result = prime * result + ((getIsPrinted() == null) ? 0 : getIsPrinted().hashCode());
        result = prime * result + ((getAddressId() == null) ? 0 : getAddressId().hashCode());
        result = prime * result + ((getSettlementWeight() == null) ? 0 : getSettlementWeight().hashCode());
        result = prime * result + ((getSettlementVol() == null) ? 0 : getSettlementVol().hashCode());
        result = prime * result + ((getRemarks() == null) ? 0 : getRemarks().hashCode());
        result = prime * result + ((getOrderNumber() == null) ? 0 : getOrderNumber().hashCode());
        result = prime * result + ((getAgentNumber() == null) ? 0 : getAgentNumber().hashCode());
        result = prime * result + ((getIsSensitive() == null) ? 0 : getIsSensitive().hashCode());
        result = prime * result + ((getRouteId() == null) ? 0 : getRouteId().hashCode());
        result = prime * result + ((getGoodsNumber() == null) ? 0 : getGoodsNumber().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pId=").append(pId);
        sb.append(", customerId=").append(customerId);
        sb.append(", groupTime=").append(groupTime);
        sb.append(", packTime=").append(packTime);
        sb.append(", packType=").append(packType);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", createId=").append(createId);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", lastUpdateId=").append(lastUpdateId);
        sb.append(", pickTime=").append(pickTime);
        sb.append(", picker=").append(picker);
        sb.append(", isPicked=").append(isPicked);
        sb.append(", auctualPrice=").append(auctualPrice);
        sb.append(", discount=").append(discount);
        sb.append(", preQoutedPrice=").append(preQoutedPrice);
        sb.append(", insideMessage=").append(insideMessage);
        sb.append(", accepted=").append(accepted);
        sb.append(", acceptedTime=").append(acceptedTime);
        sb.append(", accepter=").append(accepter);
        sb.append(", payTime=").append(payTime);
        sb.append(", isPaid=").append(isPaid);
        sb.append(", isPrinted=").append(isPrinted);
        sb.append(", addressId=").append(addressId);
        sb.append(", settlementWeight=").append(settlementWeight);
        sb.append(", settlementVol=").append(settlementVol);
        sb.append(", remarks=").append(remarks);
        sb.append(", orderNumber=").append(orderNumber);
        sb.append(", agentNumber=").append(agentNumber);
        sb.append(", isSensitive=").append(isSensitive);
        sb.append(", routeId=").append(routeId);
        sb.append(", goodsNumber=").append(goodsNumber);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}