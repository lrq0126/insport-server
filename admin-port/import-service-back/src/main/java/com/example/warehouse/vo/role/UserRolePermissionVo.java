package com.example.warehouse.vo.role;

import java.io.Serializable;
import java.util.List;

/**
 * 2020年02月10日15:36
 *  角色权限返回实体类
 * @auther lrq
 */
public class UserRolePermissionVo implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 角色名称（英文名称）
     */
    private String roleName;

    /**
     * 角色中文名称
     */
    private String roleComment;

    /**
     * 角色 所有的权限集合
     */
    private List<PermissionVo> permissionVos;

    /**
     * roleIds
     */
    private String permissiomIds;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleComment() {
        return roleComment;
    }

    public void setRoleComment(String roleComment) {
        this.roleComment = roleComment;
    }

    public List<PermissionVo> getPermissionVos() {
        return permissionVos;
    }

    public void setPermissionVos(List<PermissionVo> permissionVos) {
        this.permissionVos = permissionVos;
    }

    public String getPermissiomIds() {
        return permissiomIds;
    }

    public void setPermissiomIds(String permissiomIds) {
        this.permissiomIds = permissiomIds;
    }
}
