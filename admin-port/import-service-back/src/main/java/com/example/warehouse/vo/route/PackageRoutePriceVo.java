package com.example.warehouse.vo.route;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PackageRoutePriceVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 路线主表id
     */
    private Integer routeId;
    /**
     * 路线类型
     */
    private Integer routeType;

    /**
     * 起始重量     startWeight
     */
    private Double startWeight;
    /**
     *  结尾重量       endWeight
     */
    private Double endWeight;
    /**
     * 起始体积 startVolume
     */
    private Double startVolume;
    /**
     * 结尾体积  endVolume
     */
    private Double endVolume;
    /**
     * 单位价格
     */
    private BigDecimal price;
    /**
     * 备注
     */
    private String remarks;

    /**
     * 价格代码
     */
    private String priceNo;

//    /**
//     * 小包裹价格计算类型
//     */
//    private Integer countType;
//
//    /**
//     * 包裹类型  packageType
//     */
//    private Integer packageType;
//    /**
//     * 叉车费重量 greaterWeight
//     */
//    private Double greaterWeight;
//    /**
//     * 叉车费  forkliftFee
//     */
//    private BigDecimal forkliftFee;
//    /**
//     * 重量进位规则
//     */
//    private Integer weightCarryType;
//    /**
//     * 第一边长 firstLength
//     */
//    private Integer firstLength;
//    /**
//     * 第二边长 secondLength
//     */
//    private Integer secondLength;
//    /**
//     * 重量上限 upperLimitWeight
//     */
//    private Integer upperLimitWeight;
//    /**
//     * 敏感货物加价
//     */
//    private BigDecimal sensitivePrice;
//    /**
//     * 单位立方上限
//     */
//    private Double cubeUpper;
//
//    /**
//     * 体积重量换算除
//     */
//    private Double volConversion;

}
