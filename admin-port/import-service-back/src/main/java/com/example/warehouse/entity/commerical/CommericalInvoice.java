package com.example.warehouse.entity.commerical;

import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.detailedListVo.GoodsDetailedListVo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (CommericalInvoice)实体类
 *
 * @author makejava
 * @since 2020-07-29 19:10:38
 */
@Data
public class CommericalInvoice implements Serializable {
    private static final long serialVersionUID = 729009202453274470L;
    /**
     * id
     */
    private Long id;
    /**
     * 订单号
     */
    private String orderNumber;
    /**
     * 转运单号
     */
    private String agentNumber;
    /**
     * 发货人_公司名
     */
    private String conCompanyName;
    /**
     * 发件人名
     */
    private String conSenderName;
    /**
     * 发件人国家
     */
    private String conContry;
    /**
     * 发件人电话
     */
    private String conTel;
    /**
     * 发件人地址
     */
    private String conAddress;
    /**
     * 发件人邮编
     */
    private String conCode;
    /**
     * 收货人公司名
     */
    private String recCompanyName;
    /**
     * 收件人名
     */
    private String recReceiverName;
    /**
     * 收件人电话
     */
    private String recTel;
    /**
     * 收件人国家
     */
    private String recContry;
    /**
     * 收件人地址
     */
    private String recAddress;
    /**
     * 收件人邮编
     */
    private String recCode;
    /**
     * 出口日期
     */
    private Date exportDate;
    /**
     * 金额
     */
    private Double usd;
    /**
     * 总件数
     */
    private Integer totalCtns;
    /**
     * 签名
     */
    private String signature;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;

    private Integer createId;
    /**
     * 更新时间
     */
    private Date lastUpdateTime;

    private Integer lastUpdateId;

    private Integer packId;

    private List<GoodsDetailedListVo> goodsList;

    private List<CommericalInvoiceItems> commericalInvoiceItemsList;

}
