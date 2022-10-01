package com.example.warehouse.entity.ping;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * ping_main
 * @author
 */
@Data
public class PingMain implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 拼团名称
     */
    private String pinName;

    /**
     * 拼团订单号
     */
    private String orderNumber;

    /**
     * 转单号
     */
    private String agentNumber;

    /**
     * 路线id
     */
    private Integer routeId;

    /**
     * 地址id
     */
    private Integer addressId;

    /**
     * 公开的地址
     */
    private String publicAddress;

    /**
     * 是否公开：0是，1否
     */
    private Integer isPublic;

    /**
     * 拼团密码
     */
    private String password;

    /**
     * 目标重量
     */
    private Double targetWeight;

    /**
     * 目标单价
     */
    private BigDecimal targetUnitPrice;

    /**
     * 目标上限人数
     */
    private Integer targetMember;

    /**
     * 目标体积（暂时弃用）
     */
    private Double targetVol;

    /**
     * 截止日期
     */
    private String cutOffTime;

    /**
     * 团长id
     */
    private Integer leaderId;

    /**
     * 拼团状态（0 未审核、1 未成团、2 已成团、3 已解散、4未通过）
     */
    private Integer pType;

    /**
     * 是否重新激活的：0否 1是
     */
    private Integer isActived;

    /**
     * 激活人的号码
     */
    private String activedId;

    /**
     * 团长备注信息
     */
    private String message;

    /**
     * 审核信息
     */
    private String checkMessage;

    /**
     * 打包时间
     */
    private String packTime;

    /**
     * 打印清单的时间
     */
    private String printTime;

    /**
     * 是否已打印清单 0否1是
     */
    private Integer isPrint;

    /**
     * 打包备注信息
     */
    private String packMessage;

    /**
     * 实际重量
     */
    private Double actualWeight;

    /**
     * 长
     */
    private Double actualL;

    /**
     * 宽
     */
    private Double actualW;

    /**
     * 高
     */
    private Double actualH;

    /**
     * 实际体积/m3
     */
    private Double actualVol;

    /**
     * 是否已过期：0否1是
     */
    private Integer isCutOff;

    /**
     * 是否已打包
     */
    private Integer isPacked;

    /**
     * 是否已发货：0否1是
     */
    private Integer isShipped;

    /**
     * 是否通过
     */
    private Integer isPassed;

    /**
     * 置顶时间
     */
    private String topTime;

    /**
     * 是否置顶 0 否 1是
     */
    private Integer isTop;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

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
     * 客服备注
     */
    private String insideMessage;

    /**
     * 出库时间
     */
    private String outOfTime;

    /**
     * 出库时间
     */
    private Integer isPaid;

    private static final long serialVersionUID = 1L;

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
        PingMain other = (PingMain) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPinName() == null ? other.getPinName() == null : this.getPinName().equals(other.getPinName()))
            && (this.getOrderNumber() == null ? other.getOrderNumber() == null : this.getOrderNumber().equals(other.getOrderNumber()))
            && (this.getAgentNumber() == null ? other.getAgentNumber() == null : this.getAgentNumber().equals(other.getAgentNumber()))
            && (this.getRouteId() == null ? other.getRouteId() == null : this.getRouteId().equals(other.getRouteId()))
            && (this.getAddressId() == null ? other.getAddressId() == null : this.getAddressId().equals(other.getAddressId()))
            && (this.getPublicAddress() == null ? other.getPublicAddress() == null : this.getPublicAddress().equals(other.getPublicAddress()))
            && (this.getIsPublic() == null ? other.getIsPublic() == null : this.getIsPublic().equals(other.getIsPublic()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getTargetWeight() == null ? other.getTargetWeight() == null : this.getTargetWeight().equals(other.getTargetWeight()))
            && (this.getTargetUnitPrice() == null ? other.getTargetUnitPrice() == null : this.getTargetUnitPrice().equals(other.getTargetUnitPrice()))
            && (this.getTargetMember() == null ? other.getTargetMember() == null : this.getTargetMember().equals(other.getTargetMember()))
            && (this.getTargetVol() == null ? other.getTargetVol() == null : this.getTargetVol().equals(other.getTargetVol()))
            && (this.getCutOffTime() == null ? other.getCutOffTime() == null : this.getCutOffTime().equals(other.getCutOffTime()))
            && (this.getLeaderId() == null ? other.getLeaderId() == null : this.getLeaderId().equals(other.getLeaderId()))
            && (this.getPType() == null ? other.getPType() == null : this.getPType().equals(other.getPType()))
            && (this.getIsActived() == null ? other.getIsActived() == null : this.getIsActived().equals(other.getIsActived()))
            && (this.getActivedId() == null ? other.getActivedId() == null : this.getActivedId().equals(other.getActivedId()))
            && (this.getMessage() == null ? other.getMessage() == null : this.getMessage().equals(other.getMessage()))
            && (this.getCheckMessage() == null ? other.getCheckMessage() == null : this.getCheckMessage().equals(other.getCheckMessage()))
            && (this.getPackTime() == null ? other.getPackTime() == null : this.getPackTime().equals(other.getPackTime()))
            && (this.getPrintTime() == null ? other.getPrintTime() == null : this.getPrintTime().equals(other.getPrintTime()))
            && (this.getIsPrint() == null ? other.getIsPrint() == null : this.getIsPrint().equals(other.getIsPrint()))
            && (this.getPackMessage() == null ? other.getPackMessage() == null : this.getPackMessage().equals(other.getPackMessage()))
            && (this.getActualWeight() == null ? other.getActualWeight() == null : this.getActualWeight().equals(other.getActualWeight()))
            && (this.getActualL() == null ? other.getActualL() == null : this.getActualL().equals(other.getActualL()))
            && (this.getActualW() == null ? other.getActualW() == null : this.getActualW().equals(other.getActualW()))
            && (this.getActualH() == null ? other.getActualH() == null : this.getActualH().equals(other.getActualH()))
            && (this.getActualVol() == null ? other.getActualVol() == null : this.getActualVol().equals(other.getActualVol()))
            && (this.getIsCutOff() == null ? other.getIsCutOff() == null : this.getIsCutOff().equals(other.getIsCutOff()))
            && (this.getIsPacked() == null ? other.getIsPacked() == null : this.getIsPacked().equals(other.getIsPacked()))
            && (this.getIsShipped() == null ? other.getIsShipped() == null : this.getIsShipped().equals(other.getIsShipped()))
            && (this.getIsPassed() == null ? other.getIsPassed() == null : this.getIsPassed().equals(other.getIsPassed()))
            && (this.getTopTime() == null ? other.getTopTime() == null : this.getTopTime().equals(other.getTopTime()))
            && (this.getIsTop() == null ? other.getIsTop() == null : this.getIsTop().equals(other.getIsTop()))
            && (this.getTotalPrice() == null ? other.getTotalPrice() == null : this.getTotalPrice().equals(other.getTotalPrice()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
            && (this.getLastUpdateTime() == null ? other.getLastUpdateTime() == null : this.getLastUpdateTime().equals(other.getLastUpdateTime()))
            && (this.getLastUpdateId() == null ? other.getLastUpdateId() == null : this.getLastUpdateId().equals(other.getLastUpdateId()))
            && (this.getIsPaid() == null ? other.getIsPaid() == null : this.getIsPaid().equals(other.getIsPaid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPinName() == null) ? 0 : getPinName().hashCode());
        result = prime * result + ((getOrderNumber() == null) ? 0 : getOrderNumber().hashCode());
        result = prime * result + ((getAgentNumber() == null) ? 0 : getAgentNumber().hashCode());
        result = prime * result + ((getRouteId() == null) ? 0 : getRouteId().hashCode());
        result = prime * result + ((getAddressId() == null) ? 0 : getAddressId().hashCode());
        result = prime * result + ((getPublicAddress() == null) ? 0 : getPublicAddress().hashCode());
        result = prime * result + ((getIsPublic() == null) ? 0 : getIsPublic().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getTargetWeight() == null) ? 0 : getTargetWeight().hashCode());
        result = prime * result + ((getTargetUnitPrice() == null) ? 0 : getTargetUnitPrice().hashCode());
        result = prime * result + ((getTargetMember() == null) ? 0 : getTargetMember().hashCode());
        result = prime * result + ((getTargetVol() == null) ? 0 : getTargetVol().hashCode());
        result = prime * result + ((getCutOffTime() == null) ? 0 : getCutOffTime().hashCode());
        result = prime * result + ((getLeaderId() == null) ? 0 : getLeaderId().hashCode());
        result = prime * result + ((getPType() == null) ? 0 : getPType().hashCode());
        result = prime * result + ((getIsActived() == null) ? 0 : getIsActived().hashCode());
        result = prime * result + ((getActivedId() == null) ? 0 : getActivedId().hashCode());
        result = prime * result + ((getMessage() == null) ? 0 : getMessage().hashCode());
        result = prime * result + ((getCheckMessage() == null) ? 0 : getCheckMessage().hashCode());
        result = prime * result + ((getPackTime() == null) ? 0 : getPackTime().hashCode());
        result = prime * result + ((getPrintTime() == null) ? 0 : getPrintTime().hashCode());
        result = prime * result + ((getIsPrint() == null) ? 0 : getIsPrint().hashCode());
        result = prime * result + ((getPackMessage() == null) ? 0 : getPackMessage().hashCode());
        result = prime * result + ((getActualWeight() == null) ? 0 : getActualWeight().hashCode());
        result = prime * result + ((getActualL() == null) ? 0 : getActualL().hashCode());
        result = prime * result + ((getActualW() == null) ? 0 : getActualW().hashCode());
        result = prime * result + ((getActualH() == null) ? 0 : getActualH().hashCode());
        result = prime * result + ((getActualVol() == null) ? 0 : getActualVol().hashCode());
        result = prime * result + ((getIsCutOff() == null) ? 0 : getIsCutOff().hashCode());
        result = prime * result + ((getIsPacked() == null) ? 0 : getIsPacked().hashCode());
        result = prime * result + ((getIsShipped() == null) ? 0 : getIsShipped().hashCode());
        result = prime * result + ((getIsPassed() == null) ? 0 : getIsPassed().hashCode());
        result = prime * result + ((getTopTime() == null) ? 0 : getTopTime().hashCode());
        result = prime * result + ((getIsTop() == null) ? 0 : getIsTop().hashCode());
        result = prime * result + ((getTotalPrice() == null) ? 0 : getTotalPrice().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateId() == null) ? 0 : getCreateId().hashCode());
        result = prime * result + ((getLastUpdateTime() == null) ? 0 : getLastUpdateTime().hashCode());
        result = prime * result + ((getLastUpdateId() == null) ? 0 : getLastUpdateId().hashCode());
        result = prime * result + ((getIsPaid() == null) ? 0 : getIsPaid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pinName=").append(pinName);
        sb.append(", orderNumber=").append(orderNumber);
        sb.append(", agentNumber=").append(agentNumber);
        sb.append(", routeId=").append(routeId);
        sb.append(", addressId=").append(addressId);
        sb.append(", publicAddress=").append(publicAddress);
        sb.append(", isPublic=").append(isPublic);
        sb.append(", password=").append(password);
        sb.append(", targetWeight=").append(targetWeight);
        sb.append(", targetUnitPrice=").append(targetUnitPrice);
        sb.append(", targetMember=").append(targetMember);
        sb.append(", targetVol=").append(targetVol);
        sb.append(", cutOffTime=").append(cutOffTime);
        sb.append(", leaderId=").append(leaderId);
        sb.append(", pType=").append(pType);
        sb.append(", isActived=").append(isActived);
        sb.append(", activedId=").append(activedId);
        sb.append(", message=").append(message);
        sb.append(", checkMessage=").append(checkMessage);
        sb.append(", packTime=").append(packTime);
        sb.append(", printTime=").append(printTime);
        sb.append(", isPrint=").append(isPrint);
        sb.append(", packMessage=").append(packMessage);
        sb.append(", actualWeight=").append(actualWeight);
        sb.append(", actualL=").append(actualL);
        sb.append(", actualW=").append(actualW);
        sb.append(", actualH=").append(actualH);
        sb.append(", actualVol=").append(actualVol);
        sb.append(", isCutOff=").append(isCutOff);
        sb.append(", isPacked=").append(isPacked);
        sb.append(", isShipped=").append(isShipped);
        sb.append(", isPassed=").append(isPassed);
        sb.append(", topTime=").append(topTime);
        sb.append(", isTop=").append(isTop);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", createId=").append(createId);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", lastUpdateId=").append(lastUpdateId);
        sb.append(", isPaid=").append(isPaid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}