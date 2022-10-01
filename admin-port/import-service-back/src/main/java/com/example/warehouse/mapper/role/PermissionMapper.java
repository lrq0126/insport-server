package com.example.warehouse.mapper.role;

import com.example.warehouse.entity.role.Permission;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.role.PermissionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * PermissionMapper继承基类
 */
@Repository
public interface PermissionMapper extends MyBatisBaseDao<Permission, Integer> {
    /**
     * 获取所有权限信息
     * @return
     */
    List<PermissionVo> selectAll();

    /**
     * 根据 角色id查询权限
     * @param roleId
     *@return
     */
    List<Permission> selectByRoleId(Integer roleId);

    /**
     * 修改权限信息
     * @param permission
     * @return
     */
    int updatePermission(Permission permission);

    /**
     * 根据 权限id 删除
     * @param permissionId
     * @return
     */
    int deleteByPermissionId(int permissionId);

    /**
     * 获取 父id 分组集合
     * @return
     */
    List<Integer> selectGroupByParentId();

    /**
     * 根据 父ID 获取所属页面的权限
     * @param parentId
     * @return
     */
    List<Permission> selectByParentId(@Param("parentId") Integer parentId);

    /**
     * 根据等级查询
     * @param level
     * @return
     */
    List<Permission> selectByLevel(@Param("level") Integer level);

    List<Permission> selectByPermissionIdList(@Param("permissionIdList") List<String> permissionIdList);
}