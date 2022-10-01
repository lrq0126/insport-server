package com.example.warehouse.service.role;

import com.example.warehouse.entity.role.Permission;
import com.example.warehouse.entity.role.RolePermission;
import com.example.warehouse.entity.role.UserRole;
import com.example.warehouse.vo.role.UserRolePermissionVo;

import java.util.List;
import java.util.Map;

/**
 * 角色权限 查询 操作
 */
public interface UserRoleService {

    /**
     * 创建 角色
     * @param paramMap
     * @return
     */
    int createUserRole(Map<String,Object> paramMap);

    /**
     * 修改 角色 信息
     * @param paramMap
     * @return
     */
    int updateUserRole(Map<String,Object> paramMap);

    /**
     * 修改用户的角色等级
     * @param paramMap
     * @return
     */
    int updateUser2Role(Map<String,Object> paramMap);

    /**
     * 根据id查询角色权限
     * @param roleId
     * @return
     */
    UserRolePermissionVo selectUserRolePermission(Integer roleId);

    /**
     * 获取角色信息
     * @return
     */
    List<UserRole> selectUserRole(Map<String, Object> paramMap);

    /**
     * 根据用户 id 获取 角色集合
     * @param id
     * @return
     */
    List<UserRole> selectByUserId(Integer id);

    int useOrStop(Map<String,Object> paramMap);

    int deleteRoleByRoleId(Integer roleId);
}
