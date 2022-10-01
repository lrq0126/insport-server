package com.example.warehouse.vo.route;

import com.example.warehouse.entity.RoutePrice;
import com.example.warehouse.entity.SysDictDetail;
import com.example.warehouse.entity.TransportationRoute;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RouteVo extends TransportationRoute {

    /**
     * 所属国家
     */
    private String country;

    private String countryCode;

    //TODO  这里添加新的属性
    private String insuranceName;

    /**
     * 货值货币类型
     */
    private SysDictDetail currencyData;

    private List<RoutePrice> routePrices;



}
