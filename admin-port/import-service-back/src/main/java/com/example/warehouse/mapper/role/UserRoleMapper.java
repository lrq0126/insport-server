package com.example.warehouse.mapper.role;

import com.example.warehouse.entity.role.UserRole;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.role.UserRolePermissionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserRoleMapper继承基类
 */
@Repository
public interface UserRoleMapper extends MyBatisBaseDao<UserRole, Integer> {

    /**
     * 根据 角色id 获信息
     * @param roleId
     * @return
     */
    UserRolePermissionVo selectUserRolePermission(Integer roleId);

    /**
     * 获取所有角色信息
     * @return
     */
    List<UserRole> selectAll();

    /**
     * 根据 角色名 获取角色信息
     * @param roleName
     * @return
     */
    List<UserRole> selectAllByRoleName(@Param("roleName") String roleName,@Param("roleComment") String roleComment);

    UserRole selectByUserId(Integer userId);

    /**
     * 起停用
     * @param roleId
     * @param isAvailable
     * @return
     */
    int useOrStop(@Param("roleId") Integer roleId, @Param("isAvailable")Integer isAvailable);

    /**
     * 删除角色信息
     * @param roleId
     * @return
     */
    int deleteRoleByRoleId(@Param("roleId")Integer roleId);
}