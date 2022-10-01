package com.dwgj.mlxydedicatedline.vo;

import com.dwgj.mlxydedicatedline.entity.Goods2pack;
import com.dwgj.mlxydedicatedline.entity.TransportationRoute;
import com.dwgj.mlxydedicatedline.entity.coupons.CustomerCoupons;
import com.dwgj.mlxydedicatedline.vo.route.RouteVo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class GoodsPackVo implements Serializable {

    private Integer id;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 状态 1、未删除 0、已删除
     */
    private Integer status;

    /**
     * 数据版本，用于乐观锁，每次操作就增加 1
     */
    private Integer version;

    /**
     * 业务号码
     */
    private String businessNumber;
    private Integer commercialAreaId;
    private String commercialAreaName;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 操作员id
     */
    private Integer userId;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 包裹打包状态   1、待打包  2、已打包  3、确认发货  4、已出库
     */
    private Integer packType;

    /**
     * 是否拆包   1、不用拆包  2、需要拆包
     */
    private Integer unpacking;

    /**
     * 仓库中的包裹代码
     */
    private String goodsNo;

    /**
     * 路线id
     */
    private Integer transportationRouteId;

    /**
     * 预报价格
     */
    private Double preQuotedPrice;

    private Double length;

    private Double width;

    private Double height;

    /**
     * 实际重量
     */
    private Double actualWeight;
    /**
     * 结算重量
     */
    private Double settlementWeight;

    /**
     * 实际体积
     */
    private Double actualVol;
    /**
     * 结算体积
     */
    private Double settlementVol;

    /**
     * 实际价格
     */
    private Double actualPrice;

    private Integer addressId;

    private String address;

    private String code;

    private String addressee;

    private String phoneNumber;

    private Integer goodsNumber;

    /**
     * 出库时间
     */
    private String outStorageTime;

    private String packTime;

    private Integer routeType;

    private Integer goodsSum;

    private BigDecimal incidental;

    private String routeName;

    private String remarks;

    /**
     * 国际转运单号
     */
    private String internationalTransshipmentNo;

    /**
     * 优惠价格
     */
    private BigDecimal discount;

    /**
     * 运输类型
     */
    private Integer transportType;

    /**
     * 运输时效
     */
    private String transportationTime;

    /**
     * 目的地
     */
    private String destination;

    /**
     * 包裹数量/箱子数量
     */
    private Integer packNum;

    private Integer isPin;

    private Integer isPacked;

    private BigDecimal tariffsPrice;

    private BigDecimal insurancePrice;

    /**
     * 货物关联集合
     */
    private List<Goods2pack> goods2pack;

    private RouteVo route;

    private CustomerCoupons customerCoupons;

    private static final long serialVersionUID = 1L;

}
