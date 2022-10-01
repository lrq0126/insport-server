package com.dwgj.mlxydedicatedline.vo.pack;

import com.dwgj.mlxydedicatedline.entity.CustomerPack;
import com.dwgj.mlxydedicatedline.entity.Goods;
import com.dwgj.mlxydedicatedline.entity.SysDictDetail;
import com.dwgj.mlxydedicatedline.entity.coupons.CustomerCoupons;
import com.dwgj.mlxydedicatedline.entity.customerPack.CustomerPackPriceDetail;
import com.dwgj.mlxydedicatedline.entity.customerPack.PackInsurancePrice;
import com.dwgj.mlxydedicatedline.entity.customerPack.PackValuation;
import com.dwgj.mlxydedicatedline.entity.insurance.Insurance;
import com.dwgj.mlxydedicatedline.vo.insurance.InsuranceRespVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CustomerPackRespVo extends CustomerPack {

    private String customerName;

    private String destination;
    private String routeName;
    private Integer transportType;
    private String transportationTime;

    // 是否有关税
    private Integer isTariffs;
    // 关税比例
    private Double tariffs;
    // 是否有保险
    private Integer isInsurance;
    // 保险ID
    private Integer insuranceId;

    private String address;
    private String phoneNumber;
    private String addressee;
    private String code;

    private SysDictDetail currencyData;

    @ApiModelProperty(value = "渠道的保险内容")
    private InsuranceRespVo insurance;

    @ApiModelProperty(value = "打包的箱子内容")
    private List<CustomerPackNumberVo> customerPackNumberVos;

    @ApiModelProperty(value = "所有货物对象")
    private List<Goods> goods;

    @ApiModelProperty(value = "已使用的优惠券对象")
    private CustomerCoupons customerCoupons;

    @ApiModelProperty(value = "产品货值内容")
    private List<PackValuation> packValuations;

    @ApiModelProperty(value = "当前包裹的保险内容")
    private PackInsurancePrice packInsurancePrice;

    @ApiModelProperty(value = "价格详情")
    private CustomerPackPriceDetail priceDetail;

    @ApiModelProperty(value = "当前积分")
    private Double integrals;

}
