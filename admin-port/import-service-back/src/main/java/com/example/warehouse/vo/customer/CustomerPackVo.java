package com.example.warehouse.vo.customer;

import com.example.warehouse.entity.Customer2address;
import com.example.warehouse.entity.CustomerPack;
import com.example.warehouse.entity.Goods;
import com.example.warehouse.entity.Goods2pack;
import com.example.warehouse.entity.customerPack.CustomerPackPriceDetail;
import com.example.warehouse.entity.customerPack.PackInsurancePrice;
import com.example.warehouse.entity.customerPack.PackValuation;
import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.route.RouteVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author lrq
 * 实体类返回
 */
@Data
public class CustomerPackVo extends CustomerPack implements Serializable {

    @ApiModelProperty(value = "收货人")
    private String address;
    @ApiModelProperty(value = "收货地址")
    private String addressee;
    @ApiModelProperty(value = "城市")
    private String provinces;
    @ApiModelProperty(value = "联系方式")
    private String phoneNumber;
    @ApiModelProperty(value = "邮政编码")
    private String code;
    @ApiModelProperty(value = "邮政编码")
    private String email;

    @ApiModelProperty(value = "路线名称")
    private String routeName;

    @ApiModelProperty(value = "客户编码")
    private String customerNo;
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    @ApiModelProperty(value = "登录名")
    private String loginName;

    @ApiModelProperty(value = "目的地的")
    private String destination;

    @ApiModelProperty(value = "是否拼团订单")
    private Integer isPin;

    @ApiModelProperty(value = "优惠券价格")
    private BigDecimal couponsAmount;

    @ApiModelProperty(value = "产品货值")
    private BigDecimal productValue;

//    @ApiModelProperty(value = "保险金额")
//    private BigDecimal insurancePrice;

    @ApiModelProperty(value = "费用明细")
    private CustomerPackPriceDetail priceDetail;

    private PackInsurancePrice packInsurancePrice;

    private List<PackValuation> packValuations;

    private List<CustomerPackNumberVo> customerPackNumbers;

    private List<Goods2pack> goods2packs;

    private List<Goods> goodsData;

    private RouteVo routeVo;

    private Customer2address customer2address;

    private List<GoodsVo> goods;

    private static final long serialVersionUID = 1L;

}
