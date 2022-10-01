package com.example.warehouse.vo.applyPack;

import com.example.warehouse.vo.route.RouteVo;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiOperation(value = "路线价格计算返回实体类")
public class RouteQuotationVo extends RouteVo {

    /**
     * 货物数量
     */
    private Integer goodsSum;

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
     * 重量段
     */
    private String weight;
    /**
     * 体积段
     */
    private String vol;
    /**
     * 特殊备注
     */
    private String specialRemarks;
    /**
     * 路线对应的价格id
     */
    private Integer routePriceId;

}
