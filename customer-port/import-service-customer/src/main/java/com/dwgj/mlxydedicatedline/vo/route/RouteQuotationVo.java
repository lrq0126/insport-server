package com.dwgj.mlxydedicatedline.vo.route;

import com.dwgj.mlxydedicatedline.entity.SysDictDetail;
import com.dwgj.mlxydedicatedline.vo.insurance.InsuranceRespVo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author LRQ
 * 2019年11月27日15:57:04
 * 报价系统返回实体
 */
@Data
public class RouteQuotationVo implements Serializable,Comparable<RouteQuotationVo>{

    /**
     * 路线id
     */
    private Integer routeId;
    /**
     * 渠道名称
     */
    private String routeName;
    /**
     * 始发地
     */
    private String originatingPlace;
    /**
     * 目的地
     */
    private String destination;
    /**
     * 实际重量
     */
    private Double actualWeight;
    /**
     * 结算重量
     */
    private Double settleWeight;
    /**
     * 体积
     */
    private Double actualVol;
    /**
     * 结算体积
     */
    private Double settleVol;
    /**
     *  单位价格
     */
    private BigDecimal unitPrice;
    /**
     * 杂费
     */
    private BigDecimal incidental;
    /**
     * 总运费
     */
    private BigDecimal price;
    /**
     * 币别
     */
    private String noteType;
    /**
     * 预计送达时间
     */
    private String transportationTime;
    /**
     * 重量段
     */
    private String weight;
    /**
     * 体积段
     */
    private String vol;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 特殊备注
     */
    private String specialRemarks;
    /**
     * 路线类型
     */
    private String routeType;

    /**
     * 所属国家id
     */
    private Integer countryId;

    /**
     * 所属国家
     */
    private String country;

    /**
     * 路线对应的价格id
     */
    private Integer routePriceId;

    private Integer isTariffs;

    private Double tariffs;

    private Integer isInsurance;
    private Integer insuranceId;
    private InsuranceRespVo insurance;

    private SysDictDetail currencyData;

    private static final long serialVersionUID = 1L;

    @Override
    public int compareTo(RouteQuotationVo o) {
        return this.getPrice().compareTo(o.getPrice());
    }
}
