package com.example.warehouse.vo.role;

import java.io.Serializable;

/**
 * 2020年02月10日15:39
 *
 * @auther lrq
 */
public class RolePermissionVo implements Serializable {

    /**
     * 权限地址
     */
    private String url;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限名称描述
     */
    private String describe;

    /**
     * 是否拥有该权限
     * 1、拥有该权限   0、未拥有该权限
     */
    private Integer possessed;

    /**
     * 父id
     */
    private Integer parentId;

    private static final long serialVersionUID = 1L;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getPossessed() {
        return possessed;
    }

    public void setPossessed(Integer possessed) {
        this.possessed = possessed;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
