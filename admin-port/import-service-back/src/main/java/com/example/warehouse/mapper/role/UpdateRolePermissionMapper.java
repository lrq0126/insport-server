package com.example.warehouse.mapper.role;

import com.example.warehouse.entity.role.UpdateRolePermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (UpdateRolePermission)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-29 18:37:56
 */
@Repository
public interface UpdateRolePermissionMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UpdateRolePermission queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UpdateRolePermission> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param updateRolePermission 实例对象
     * @return 对象列表
     */
    List<UpdateRolePermission> queryAll(UpdateRolePermission updateRolePermission);

    /**
     * 新增数据
     *
     * @param updateRolePermission 实例对象
     * @return 影响行数
     */
    int insert(UpdateRolePermission updateRolePermission);

    /**
     * 修改数据
     *
     * @param updateRolePermission 实例对象
     * @return 影响行数
     */
    int update(UpdateRolePermission updateRolePermission);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 根据角色id 获取
     * @param roleId
     * @return
     */
    UpdateRolePermission selectAllByRoleId(@Param("roleId") Integer roleId);

}