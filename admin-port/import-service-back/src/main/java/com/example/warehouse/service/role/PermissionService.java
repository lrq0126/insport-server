package com.example.warehouse.service.role;

import com.example.warehouse.model.ResultModel;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PermissionService {

    /**
     * 创建 操作名称 和 路径
     * @param paramMap
     * @return
     */
    int createPermission(Map<String,Object> paramMap);

    /**
     * 修改权限信息
     * @param paramMap
     * @return
     */
    int updatePermission(Map<String,Object> paramMap);

    /**
     * 删除 操作名称 和 路径
     * @param permissionId
     * @return
     */
    int deletePermission(int permissionId);


    ResponseEntity<ResultModel> selectPermission();
}
