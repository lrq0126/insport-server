package com.example.warehouse.service.role.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.entity.role.Permission;
import com.example.warehouse.entity.role.UpdateRolePermission;
import com.example.warehouse.entity.role.UserRole;
import com.example.warehouse.mapper.role.PermissionMapper;
import com.example.warehouse.mapper.role.RolePermissionMapper;
import com.example.warehouse.mapper.role.UpdateRolePermissionMapper;
import com.example.warehouse.mapper.role.UserRoleMapper;
import com.example.warehouse.service.role.UserRoleService;
import com.example.warehouse.vo.role.PermissionVo;
import com.example.warehouse.vo.role.UserRolePermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 2020年2月8日22:19:33
 *
 * @author lrq
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private UpdateRolePermissionMapper updateRolePermissionMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 创建角色
     *
     * @param paramMap
     * @return
     */
    @Override
    public int createUserRole(Map<String, Object> paramMap) {
        UserRole userRole = JSONObject.parseObject(JSON.toJSONString(paramMap), UserRole.class);
        List<UserRole> userRoleCheck = userRoleMapper.selectAllByRoleName(userRole.getRoleName(),"");
        if(userRoleCheck.size() > 0){
           return  -1;
        }
        return userRoleMapper.insertSelective(userRole);
    }

    /**
     * 修改角色信息
     *
     * @param paramMap
     * @return
     */
    @Override
    public int updateUserRole(Map<String, Object> paramMap) {
        UserRole newUserRole = JSONObject.parseObject(JSON.toJSONString(paramMap), UserRole.class);
        UserRole userRole = userRoleMapper.selectByPrimaryKey(newUserRole.getId());
        userRole.setRoleName(newUserRole.getRoleName());
        userRole.setRoleComment(newUserRole.getRoleComment());
        return userRoleMapper.updateByPrimaryKeySelective(userRole);
    }

    /**
     * 修改角色等级
     *
     * @param paramMap
     * @return
     */
    @Override
    public int updateUser2Role(Map<String, Object> paramMap) {
        return 0;
    }

    /**
     * 获取角色 所有的权限
     *
     * @param roleId
     * @return
     */
    @Override
    public UserRolePermissionVo selectUserRolePermission(Integer roleId) {
        try {
            //获取角色信息
            UserRolePermissionVo userRolePermissionVo = userRoleMapper.selectUserRolePermission(roleId);
            //获取该角色权限
            List<Permission> rolePermissions = permissionMapper.selectByRoleId(userRolePermissionVo.getId());

            // 1、获取 菜单
            List<Permission> parentPermissions = permissionMapper.selectByLevel(1);
            // 2、获取 页面
            List<Permission> pagePermissions = permissionMapper.selectByLevel(2);
            //3、 获取 按钮
            List<Permission> buttonPermissions = permissionMapper.selectByLevel(3);

            PermissionVo parentPermissionVo;
            PermissionVo pagePermissionVo;
            PermissionVo buttonPermissionVo;
            List<PermissionVo> parentPermissionVos;
            List<PermissionVo> pagePermissionVos;
            List<PermissionVo> buttonPermissionVos;
            // 3、获取所有的所有的权限信息
            parentPermissionVos = new ArrayList<>();
            for (Permission permission : parentPermissions) {
                parentPermissionVo = new PermissionVo();
                // 菜单id
                parentPermissionVo.setId(permission.getId());
                // 输入菜单名
                parentPermissionVo.setLable(permission.getDescribe());
                // 菜单排序
                parentPermissionVo.setSortNo(permission.getSortNo());
                // 菜单等级
                parentPermissionVo.setLevel(permission.getLevel());
                //4、判断此角色是否有这个权限
                boolean parentPermissionBoolean = false;
                for (Permission rolePermission : rolePermissions) {
                    if (permission.getId().equals(rolePermission.getId())) {
                        parentPermissionBoolean = true;
                        break;
                    }
                }
                if (parentPermissionBoolean) {
                    parentPermissionVo.setPossessed(1);
                } else {
                    parentPermissionVo.setPossessed(0);
                }
                pagePermissionVos = new ArrayList<>();
                for (Permission pagePermission : pagePermissions) {
                    if (pagePermission.getParentId().equals(permission.getId())) {
                        pagePermissionVo = new PermissionVo();
                        // 页面id
                        pagePermissionVo.setId(pagePermission.getId());
                        // 输入页面名
                        pagePermissionVo.setLable(pagePermission.getDescribe());
                        // 页面排序
                        pagePermissionVo.setSortNo(pagePermission.getSortNo());
                        // 页面等级
                        pagePermissionVo.setLevel(pagePermission.getLevel());
                        //4、判断此角色是否有这个权限
                        boolean pagePermissionBoolean = false;
                        for (Permission rolePermission : rolePermissions) {
                            if (pagePermission.getId().equals(rolePermission.getId())) {
                                pagePermissionBoolean = true;
                                break;
                            }
                        }
                        if (pagePermissionBoolean) {
                            pagePermissionVo.setPossessed(1);
                        } else {
                            pagePermissionVo.setPossessed(0);
                        }
                        buttonPermissionVos = new ArrayList<>();
                        for (Permission buttonPermission : buttonPermissions) {
                            if (buttonPermission.getParentId().equals(pagePermission.getId())) {
                                buttonPermissionVo = new PermissionVo();
                                // 页面id
                                buttonPermissionVo.setId(buttonPermission.getId());
                                // 输入页面名
                                buttonPermissionVo.setLable(buttonPermission.getDescribe());
                                // 页面排序
                                buttonPermissionVo.setSortNo(buttonPermission.getSortNo());
                                // 页面等级
                                buttonPermissionVo.setLevel(buttonPermission.getLevel());

                                //4、判断此角色是否有这个权限
                                boolean permissionBoolean = false;
                                for (Permission rolePermission : rolePermissions) {
                                    if (buttonPermission.getId().equals(rolePermission.getId())) {
                                        permissionBoolean = true;
                                        break;
                                    }
                                }
                                if (permissionBoolean) {
                                    buttonPermissionVo.setPossessed(1);
                                } else {
                                    buttonPermissionVo.setPossessed(0);
                                }
                                buttonPermissionVos.add(buttonPermissionVo);
                            }
                        }
                        pagePermissionVo.setChildren(buttonPermissionVos);
                        pagePermissionVos.add(pagePermissionVo);
                    }

                }
                parentPermissionVo.setChildren(pagePermissionVos);
                parentPermissionVos.add(parentPermissionVo);
                // 输入所属页面的按钮
            }

            //添加权限信息数据
            userRolePermissionVo.setPermissionVos(parentPermissionVos);
            UpdateRolePermission updateRolePermission = updateRolePermissionMapper.selectAllByRoleId(roleId);

            if (updateRolePermission != null) {
                String stringPIds = updateRolePermission.getPermissionIds();
                userRolePermissionVo.setPermissiomIds(stringPIds);
            }
            return userRolePermissionVo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO 垃圾回收
        //rolePermissionVo = null;
        return null;

    }

    @Override
    public List<UserRole> selectUserRole(Map<String, Object> paramMap) {
        String roleName = null;
        String roleComment = null;
        if (paramMap.containsKey("roleName")) {
            roleName = (String) paramMap.get("roleName");
        }
        if (paramMap.containsKey("roleComment")) {
            roleComment = (String) paramMap.get("roleComment");
        }
        Object str = paramMap.get("roleId");
        int roleId = (int) str;
        UserRole loginUserRole = userRoleMapper.selectByPrimaryKey(roleId);
        List<UserRole> userRoleList = userRoleMapper.selectAllByRoleName(roleName, roleComment);
        List<UserRole> showUserRoleList = new ArrayList<>();
        if(loginUserRole.getRoleComment().equals("superAdmin")){
            return userRoleList;
        }else {
            for (UserRole userRole : userRoleList) {
                if (loginUserRole.getLevel() > userRole.getLevel()) {
                    showUserRoleList.add(userRole);
                }
            }
            return showUserRoleList;
        }
    }

    @Override
    public List<UserRole> selectByUserId(Integer userId) {
        UserRole userUserRoles = userRoleMapper.selectByUserId(userId);
        List<UserRole> userRoleList = new ArrayList<>();
        List<UserRole> allUserRole = userRoleMapper.selectAll();
        for (UserRole userRole : allUserRole) {
            if (userRole.getIsAvailable() == 1 && userRole.getLevel() <= userUserRoles.getLevel()) {
                userRoleList.add(userRole);
            }
        }
        return userRoleList;
    }

    @Override
    public int useOrStop(Map<String, Object> paramMap) {
        String roleId = (String) paramMap.get("roleId");
        String isAvailable = (String) paramMap.get("isAvailable");
        return userRoleMapper.useOrStop(Integer.valueOf(roleId), Integer.valueOf(isAvailable));
    }

    @Override
    public int deleteRoleByRoleId(Integer roleId) {
        return userRoleMapper.deleteRoleByRoleId(roleId);
    }
}
