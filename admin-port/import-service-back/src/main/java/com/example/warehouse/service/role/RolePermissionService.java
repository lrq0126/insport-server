package com.example.warehouse.service.role;

import com.example.warehouse.entity.role.Permission;
import com.example.warehouse.entity.role.RolePermission;

import java.util.Map;

public interface RolePermissionService {





    /**
     * 创建 角色 操作权限
     * @param paramMap
     * @return
     */
    int createRolePermission(Map<String,Object> paramMap);

    /**
     * 修改 角色 操作权限
     * @param paramMap
     * @return
     */
    int updateRolePermission(Map<String,Object> paramMap);

    /**
     * 删除 角色 操作权限
     * @param id
     * @return
     */
    int deleteRolePermission(int id);

}
