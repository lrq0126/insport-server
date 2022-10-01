package com.example.warehouse.mapper.role;

import com.example.warehouse.entity.role.RolePermission;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.role.RolePermissionVo;
import com.example.warehouse.vo.role.UserRolePermissionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * RolePermissionMapper继承基类
 */
@Repository
public interface RolePermissionMapper extends MyBatisBaseDao<RolePermission, Integer> {

    int deleteByRoleId(Integer roleId);

    /**
     * 根据权限id获取 角色-权限 关联信息
     * @param permissionId
     * @return
     */
    List<RolePermission> selectByPermissionId(int permissionId);

    /**
     * 根据 权限id 删除 角色-权限 数据
     * @param permissionIds
     * @return
     */
    int deleteByPermissionId(Integer permissionIds);
}