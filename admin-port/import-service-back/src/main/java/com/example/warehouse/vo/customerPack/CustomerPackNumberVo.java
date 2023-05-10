package com.example.warehouse.vo.customerPack;

import com.example.warehouse.entity.Goods;
import com.example.warehouse.vo.GoodsVo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author lrq
 * 2019年12月20日16:27:53
 */
@Data
public class CustomerPackNumberVo implements Serializable {

    private Integer id;
    private String packOrder;
    private Integer customerPackId;
    private Double length;
    private Double width;
    private Double height;
    private Double actualWeight;
    private Double settlementWeight;
    private Double actualVol;
    private Double settlementVol;
    private Double volWeight;
    private String outStorageTime;
    private String packTime;
    private BigDecimal actualPrice;
    private String goodsNos;
    private Integer goodsNum;
    private List<GoodsVo> goodsVoList;
    /**
     * 国际转运单号
     */
    private String internationalTransshipmentNo;
    private Integer isSensitive;

    private static final long serialVersionUID = 1L;

}
