package com.dwgj.mlxydedicatedline.entity.ping;

import com.dwgj.mlxydedicatedline.entity.Customer2address;
import com.dwgj.mlxydedicatedline.entity.CustomerPackReceiverAddress;
import com.dwgj.mlxydedicatedline.entity.TransportationRoute;
import com.dwgj.mlxydedicatedline.entity.image.Images;
import com.dwgj.mlxydedicatedline.vo.route.RouteQuotationVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * ping_main
 * @author
 */
@ApiModel(value = "PingMain",description = "拼团对象")
@Data
public class PingMain implements Serializable {
    /**
     * id
     */
    @ApiModelProperty(value = "拼团id",name = "id",example = "111")
    private Integer id;

    /**
     * 拼团名称
     */
    @ApiModelProperty(value = "拼团名称",name = "pinName",example = "Never的拼团")
    private String pinName;

    /**
     * 拼团订单号
     */
    @ApiModelProperty(value = "拼团订单号",name = "orderNumber",example = "P-084821610")
    private String orderNumber;

    /**
     * 转单号
     */
    @ApiModelProperty(value = "国际转单号",name = "agentNumber",example = "21084821610")
    private String agentNumber;

    /**
     * 路线对应的价格id
     */
    @ApiModelProperty(value = "渠道id",name = "routeId",example = "1026")
    private Integer routeId;

    /**
     * 地址id
     */
    @ApiModelProperty(value = "拼团的具体地址id",name = "addressId",example = "1026")
    private Integer addressId;

    /**
     * 公开的地址
     */
    @ApiModelProperty(value = "拼团公开的地址",name = "publicAddress",example = "美国洛杉矶吾皇街道")
    private String publicAddress;

    /**
     * 是否公开：0是，1否
     */
    @ApiModelProperty(value = "拼团是否公开：0是，1否；是公开就不用密码",name = "isPublic",example = "1")
    private Integer isPublic;

    /**
     * 拼团密码
     */
    @ApiModelProperty(value = "拼团密码，isPublic为1才需要填写",name = "password",example = "0000")
    private String password;

    /**
     * 目标重量
     */
    @ApiModelProperty(value = "目标重量",name = "targetWeight",example = "100.00")
    private Double targetWeight;

    /**
     * 目标单价
     */
    @ApiModelProperty(value = "目标单价",name = "targetUnitPrice",example = "12")
    private BigDecimal targetUnitPrice;

    /**
     * 目标上限人数
     */
    @ApiModelProperty(value = "目标上限人数",name = "targetMember",example = "20")
    private Integer targetMember;

    /**
     * 目标体积（暂时弃用）
     */
    @ApiModelProperty(value = "目标体积（暂时弃用）",name = "targetVol",example = "10.0000")
    private Double targetVol;

    /**
     * 截止日期
     */
    @ApiModelProperty(value = "拼团截止日期",name = "cutOffTime",example = "时间")
    private String cutOffTime;

    /**
     * 团长id
     */
    @ApiModelProperty(value = "团长id",name = "leaderId",example = "1025")
    private Integer leaderId;

    /**
     * 拼团状态（0 未审核、1 未成团、2 已成团、3 已解散、4未通过、5 未成团待确认、6 已完成）
     */
    @ApiModelProperty(value = "拼团状态（0 未审核、1 未成团、2 已成团、3 已解散、4 未通过、5 未成团待确认、6 已完成）",name = "pType",example = "0")
    private Integer pType;

    /**
     * 是否重新激活的：0否 1是
     */
    @ApiModelProperty(value = "是否重新激活的：0否 1是",name = "isActived",example = "0")
    private Integer isActived;

    /**
     * 激活人的id
     */
    @ApiModelProperty(value = "激活人的id",name = "activedId",example = "激活人的id")
    private String activedId;

    /**
     * 团长备注信息
     */
    @ApiModelProperty(value = "团长备注信息",name = "message",example = "团长备注信息")
    private String message;

    /**
     * 审核信息
     */
    @ApiModelProperty(value = "审核信息",name = "checkMessage",example = "审核通过")
    private String checkMessage;

    /**
     * 打包时间
     */
    @ApiModelProperty(value = "打包时间",name = "packTime",example = "时间")
    private String packTime;

    /**
     * 打印清单的时间
     */
    @ApiModelProperty(value = "打印清单的时间",name = "printTime",example = "时间")
    private String printTime;

    /**
     * 是否已打印清单 0否1是
     */
    @ApiModelProperty(value = "是否已打印清单 0否1是",name = "isPrint",example = "0")
    private Integer isPrint;

    /**
     * 打包备注信息
     */
    @ApiModelProperty(value = "拼团打包备注信息",name = "packMessage",example = "需要添加附加费")
    private String packMessage;

    /**
     * 实际重量
     */
    @ApiModelProperty(value = "实际重量",name = "actualWeight",example = "120.36")
    private Double actualWeight;

    /**
     * 长
     */
    @ApiModelProperty(value = "长",name = "actualL",example = "10.25")
    private Double actualL;

    /**
     * 宽
     */
    @ApiModelProperty(value = "宽",name = "actualW",example = "41.15")
    private Double actualW;

    /**
     * 高
     */
    @ApiModelProperty(value = "高",name = "actualH",example = "52.24")
    private Double actualH;

    /**
     * 实际体积/m3
     */
    @ApiModelProperty(value = "实际体积/m3",name = "actualVol",example = "0.2563")
    private Double actualVol;

    /**
     * 是否已过期：0否1是
     */
    @ApiModelProperty(value = "是否已过期：0否1是",name = "isCutOff",example = "0")
    private Integer isCutOff;

    /**
     * 是否已打包
     */
    @ApiModelProperty(value = "是否已打包",name = "isPacked",example = "0")
    private Integer isPacked;

    /**
     * 是否已发货：0否1是
     */
    @ApiModelProperty(value = "是否已发货：0否1是",name = "isShipped",example = "0")
    private Integer isShipped;

    /**
     * 是否已发货：0否1是
     */
    @ApiModelProperty(value = "是否已支付：0否1是",name = "isPaid",example = "0")
    private Integer isPaid;

    /**
     * 是否通过
     */
    @ApiModelProperty(value = "是否通过",name = "isPassed",example = "1")
    private Integer isPassed;

    /**
     * 总价
     */
    @ApiModelProperty(value = "拼团成功，最后的总价",name = "totalPrice",example = "5402.35")
    private BigDecimal totalPrice;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态：0已删除，1正常",name = "status",example = "1")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间",name = "createTime",example = "2021年1月1日22:20:06")
    private String createTime;

    @ApiModelProperty(value = "创建人的id",name = "createId",example = "1026")
    private Integer createId;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间",name = "lastUpdateTime",example = "2021年1月1日22:20:06")
    private String lastUpdateTime;

    @ApiModelProperty(value = "最后更新人的id",name = "lastUpdateId",example = "1023")
    private Integer lastUpdateId;

    /**
     * 驿站图片组
     */
    @ApiModelProperty(value = "驿站图片组",name = "imagesList")
    private List<Images> imagesList;

    /**
     * 驿站具体地址
     */
    @ApiModelProperty(value = "驿站具体地址的实体类，获取详情的时候用到",name = "address")
    private CustomerPackReceiverAddress address;

    /**
     * 渠道
     */
    @ApiModelProperty(value = "渠道的实体类，获取详情的时候需要用到",name = "route")
    private RouteQuotationVo route;

    /**
     * 当前包裹总重量
     */
    @ApiModelProperty(value = "当前包裹总重量",name = "packageWeight",example = "120.24")
    private double packageWeight;

    /**
     * 当前包裹数
     */
    @ApiModelProperty(value = "当前包裹数",name = "packageSum",example = "152")
    private int packageSum;

    /**
     * 当前包裹总重量
     */
    @ApiModelProperty(value = "我的包裹重量",name = "myPackageWeight",example = "120.24")
    private double myPackageWeight;

    /**
     * 当前包裹数
     */
    @ApiModelProperty(value = "我的包裹数",name = "myPackageSum",example = "152")
    private int myPackageSum;

    /**
     * 进度
     */
    @ApiModelProperty(value = "进度",name = "progress",example = "0.8")
    private double progress;

    /**
     * 当前拼团人数
     */
    @ApiModelProperty(value = "当前拼团人数",name = "currentPeopleNum",example = "10")
    private int currentPeopleNum;

    /**
     * 是否置顶  0 否  1是
     */
    @ApiModelProperty(value = "是否置顶  0 否  1是",name = "isTop",example = "0")
    private int isTop;

    /**
     * 置顶时间
     */
    @ApiModelProperty(value = "置顶时间",name = "topTime")
    private String topTime;

    /**
     * 团长名称
     */
    @ApiModelProperty(value = "团长名称",name = "customerName")
    private String customerName;

    /**
     * 团长头像路径
     */
    @ApiModelProperty(value = "团长头像路径，如果为空，就默认给它一个图片",name = "headImaUrl")
    private String headImaUrl;

    /**
     * 运输类型：1 空运  2海运
     */
    @ApiModelProperty(value = "运输类型：1 空运  2海运",name = "transportType")
    private int transportType;

    /**
     * 渠道名称
     */
    @ApiModelProperty(value = "渠道名称",name = "routeName")
    private String routeName;

    /**
     * 实际地址，成团后显示
     */
    @ApiModelProperty(value = "实际地址，成团后显示",name = "topTime")
    private String actualAddress;

    /**
     * 国家名称
     */
    @ApiModelProperty(value = "国家名称",name = "countryName")
    private String countryName;

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
            && (this.getTotalPrice() == null ? other.getTotalPrice() == null : this.getTotalPrice().equals(other.getTotalPrice()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
            && (this.getLastUpdateTime() == null ? other.getLastUpdateTime() == null : this.getLastUpdateTime().equals(other.getLastUpdateTime()))
            && (this.getIsPaid() == null ? other.getIsPaid() == null : this.getIsPaid().equals(other.getIsPaid()))
            && (this.getLastUpdateId() == null ? other.getLastUpdateId() == null : this.getLastUpdateId().equals(other.getLastUpdateId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
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