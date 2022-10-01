package com.dwgj.mlxydedicatedline.vo.route;

import com.dwgj.mlxydedicatedline.entity.RoutePrice;
import com.dwgj.mlxydedicatedline.entity.SysDictDetail;
import com.dwgj.mlxydedicatedline.entity.TransportationRoute;
import com.dwgj.mlxydedicatedline.vo.insurance.InsuranceRespVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RouteVo extends TransportationRoute {


    private List<BigPackagePriceVo> bigPackagePrices;

    private InsuranceRespVo insurance;
    /**
     * 所属国家
     */
    private String country;

    /**
     * 货值货币类型
     */
    private SysDictDetail currencyData;

}
