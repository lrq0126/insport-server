package com.example.warehouse.entity.ping;

import java.util.Date;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
/**
 * 拼团审核表(PingMainAudit)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2021/8/30 18:20
 */
@ApiOperation(value = "拼团审核表")
@Data
public class PingMainAudit {

        
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 创建时间
     */    
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 创建人
     */    
    @ApiModelProperty(value = "创建人")
    private Integer createId;

    /**
     * 更新时间
     */    
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 更新人
     */    
    @ApiModelProperty(value = "更新人")
    private Integer updateId;

    /**
     * 状态 1、正常 0、已删除
     */    
    @ApiModelProperty(value = "状态 1、正常 0、已删除")
    private Integer status;

    /**
     * 版本号
     */    
    @ApiModelProperty(value = "版本号")
    private Integer version;

    /**
     * 审核人id
     */    
    @ApiModelProperty(value = "审核人id")
    private Integer auditorId;

    /**
     * 审核人名称
     */    
    @ApiModelProperty(value = "审核人名称")
    private String auditorName;

    /**
     * 拼团id
     */    
    @ApiModelProperty(value = "拼团id")
    private Integer pId;

    /**
     * 拼团订单号
     */    
    @ApiModelProperty(value = "拼团订单号")
    private String pOrder;

    /**
     * 审核结果
     */    
    @ApiModelProperty(value = "审核结果")
    private Integer auditResult;

    /**
     * 审核意见
     */    
    @ApiModelProperty(value = "审核意见")
    private String auditOpinion;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}