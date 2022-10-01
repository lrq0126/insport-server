package com.example.warehouse.service.role.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.entity.role.Permission;
import com.example.warehouse.entity.role.RolePermission;
import com.example.warehouse.mapper.role.PermissionMapper;
import com.example.warehouse.mapper.role.RolePermissionMapper;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.role.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 权限操作实现类
 * @author lrq
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;


    @Override
    @Transactional
    public int createPermission(Map<String,Object> paramMap) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("新增权限操作");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try{
            Permission permission = JSONObject.parseObject(JSON.toJSONString(paramMap),Permission.class);
            permissionMapper.insertSelective(permission);
            transactionManager.commit(status);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            transactionManager.rollback(status);
            return 0;
        }
    }

    @Override
    public int updatePermission(Map<String, Object> paramMap) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("修改权限信息操作");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try{
            Permission newPermission = JSONObject.parseObject(JSON.toJSONString(paramMap),Permission.class);
            Permission permission = permissionMapper.selectByPrimaryKey(newPermission.getId());
            permission.setDescribe(newPermission.getDescribe());
            permission.setName(newPermission.getName());
            permission.setUrl(newPermission.getUrl());
            permissionMapper.updatePermission(permission);
            transactionManager.commit(status);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            transactionManager.rollback(status);
            return 0;
        }
    }

    @Override
    @Transactional
    public int deletePermission(int permissionId) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("删除权限操作");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try{
            List<RolePermission> rolePermissionList =  rolePermissionMapper.selectByPermissionId(permissionId);
            if(rolePermissionList.size() > 0){
                rolePermissionMapper.deleteByPermissionId(permissionId);
            }
            permissionMapper.deleteByPermissionId(permissionId);
            transactionManager.commit(status);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            transactionManager.rollback(status);
            return 0;
        }

    }

    @Override
    public ResponseEntity<ResultModel> selectPermission() {
        return new ResponseEntity<>(ResultModel.ok(permissionMapper.selectAll()), HttpStatus.OK);
    }


}
