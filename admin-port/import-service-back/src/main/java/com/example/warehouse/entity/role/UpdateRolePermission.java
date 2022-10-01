package com.example.warehouse.entity.role;

import java.util.Date;
import java.io.Serializable;

/**
 * (UpdateRolePermission)实体类
 *
 * @author makejava
 * @since 2020-02-29 18:37:56
 */
public class UpdateRolePermission implements Serializable {
    private static final long serialVersionUID = -69856422750180901L;
    /**
    * 权限id
    */
    private Integer id;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 用户id
    */
    private Integer userId;
    /**
    * 角色id
    */
    private Integer roleId;
    /**
    * 权限id集合
    */
    private String permissionIds;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(String permissionIds) {
        this.permissionIds = permissionIds;
    }

}