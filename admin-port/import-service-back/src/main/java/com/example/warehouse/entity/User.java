package com.example.warehouse.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 管理员表
 * @author lrq
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String username;
    private String password;
    private String usercode;
    private String lastLoginTime;
    private String lastLogoutTime;
    private String loginCount;
    private Integer status;
    private String createTime;
    private Integer createId;
    private String lastUpdateTime;
    private Integer lastUpdateId;
    private Integer level;
    private String loginName;
    private Integer commercialAreaId;//区域仓库ID
    private String commercialAreaName;//区域仓库名称
    private Integer roleId;

    private String pid;//父节点
    private String employeeFlag;//内部外部员工


}