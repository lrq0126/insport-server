package com.dwgj.mlxydedicatedline.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据字典明表(SysDictDetail)实体类
 *
 * @author makejava
 * @since 2020-03-23 14:36:46
 */
@Data
public class SysDictDetail implements Serializable {
    private static final long serialVersionUID = 425000609303599104L;
    /**
    * 主键ID
    */
    private Integer id;
    /**
    * 数据字典主表的主健ID
    */
    private Integer sdmId;
    /**
    * 数据字典主表的编码
    */
    private String sdmCode;
    /**
    * 编码
    */
    private String sddCode;
    /**
    * 名称
    */
    private String sddName;
    /**
    * 排序
    */
    private Integer sddSeq;
    /**
    * 备注
    */
    private String sddRemark;

    /**
    * 备用字段 这里先用来存汇率
    */
    private String alternateField;
    /**
    * 状态
    */
    private Integer status;
    /**
    * 创建人ID
    */
    private Integer createId;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 最后更新人ID
    */
    private Integer lastUpdateId;
    /**
    * 最后更新时间
    */
    private Date lastUpdateTime;
    /**
    * 启用标志---0:否;1:是
    */
    private Integer isEnable;

}