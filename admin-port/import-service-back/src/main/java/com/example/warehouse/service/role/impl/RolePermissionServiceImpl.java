package com.example.warehouse.service.role.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.entity.role.Permission;
import com.example.warehouse.entity.role.RolePermission;
import com.example.warehouse.entity.role.UpdateRolePermission;
import com.example.warehouse.mapper.role.PermissionMapper;
import com.example.warehouse.mapper.role.RolePermissionMapper;
import com.example.warehouse.mapper.role.UpdateRolePermissionMapper;
import com.example.warehouse.service.role.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import sun.misc.GC;

import java.util.*;

/**
 * 角色 权限 绑定操作实现类
 * @author lrq
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private UpdateRolePermissionMapper updateRolePermissionMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    /**
     * 参数：roleId             角色id
     *       permissionIds      权限id集合
     * @param paramMap
     * @return
     */
    @Override
    public int createRolePermission(Map<String,Object> paramMap) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("绑定相关权限操作");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try{
            String roleId = paramMap.get("roleId").toString();
            String permissionIds = paramMap.get("permissionIds").toString();

            List<String> permissionIdList = Arrays.asList(permissionIds.split(","));
            // 1、获取 菜单
            List<Permission> parentPermissions = permissionMapper.selectByLevel(1);
            // 2、获取 页面
            List<Permission> pagePermissions = permissionMapper.selectByLevel(2);

            List<Integer> newPermissionIdList = new ArrayList<>();
            RolePermission rolePermission;
            // 删除此角色所有权限
            rolePermissionMapper.deleteByRoleId(Integer.parseInt(roleId));
            // 重新插入权限
            List<Permission> buttonPermissionList = permissionMapper.selectByPermissionIdList(permissionIdList);
            for (Permission buttonPermission : buttonPermissionList) {
                Integer pagePermissionId = null;
                Integer menuPermissionId = null;
                boolean pagebool = true;
                boolean menubool = true;
                for (Permission pagePermission : pagePermissions) {
                    if(pagePermission.getId().equals(buttonPermission.getParentId())){
                        if(newPermissionIdList.size() > 0){
                            for (Integer pId : newPermissionIdList){
                                if(pId.equals(pagePermission.getId())){
                                    pagebool = false;
                                    break;
                                }
                            }
                        }
                        if(pagebool){
                            pagePermissionId = pagePermission.getId();
                        }
                        for (Permission parentPermission : parentPermissions) {
                            if(parentPermission.getId().equals(pagePermission.getParentId())){
                                if(newPermissionIdList.size() > 0){
                                    for (Integer pId : newPermissionIdList){
                                        if(pId.equals(parentPermission.getId())){
                                            menubool = false;
                                            break;
                                        }
                                    }
                                }
                                if(menubool){
                                    menuPermissionId = parentPermission.getId();
                                }
                            }
                        }
                    }
                }
                newPermissionIdList.add(buttonPermission.getId());
                if( pagePermissionId != null ){
                    newPermissionIdList.add(pagePermissionId);
                }
                if( menuPermissionId != null ){
                    newPermissionIdList.add(menuPermissionId);
                }
            }
            for (Integer permissionId : newPermissionIdList){
                rolePermission = new RolePermission();
                rolePermission.setRoleId(Integer.parseInt(roleId));
                rolePermission.setpId(permissionId);
                rolePermissionMapper.insertSelective(rolePermission);
            }

            String userId = paramMap.get("userId").toString();
            UpdateRolePermission updateRolePermission = updateRolePermissionMapper.selectAllByRoleId(Integer.parseInt(roleId));
            if(updateRolePermission == null){
                updateRolePermission = new UpdateRolePermission();
                updateRolePermission.setRoleId(Integer.parseInt(roleId));
                updateRolePermission.setPermissionIds(permissionIds);
                updateRolePermission.setUserId(Integer.parseInt(userId));
                updateRolePermissionMapper.insert(updateRolePermission);
            }else {
                updateRolePermission.setRoleId(Integer.parseInt(roleId));
                updateRolePermission.setPermissionIds(permissionIds);
                updateRolePermission.setUserId(Integer.parseInt(userId));
                updateRolePermissionMapper.update(updateRolePermission);
            }
            transactionManager.commit(status);
            return 1;
            // TODO 后续做垃圾回收
        }catch (Exception e){
        e.printStackTrace();
        transactionManager.rollback(status);
        return 0;
    }

    }

    @Override
    public int updateRolePermission(Map<String,Object> paramMap) {
        return 0;
    }

    @Override
    public int deleteRolePermission(int id) {

        return 0;
    }
}
