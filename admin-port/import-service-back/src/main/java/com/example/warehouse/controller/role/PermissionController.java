package com.example.warehouse.controller.role;

import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.role.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.warehouse.enums.ResultStatus.*;

/**
 * 权限 创建 修改
 *
 * @Author lrq
 */
@RestController
@RequestMapping("/role/permission")
@Slf4j
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> selectPermission() {
        return permissionService.selectPermission();
    }

    /**
     * 新增权限信息
     * 参数：
     *      链接描述：describe
     *      相关链接：url
     *      权限名称：name
     *
     * @param paramMap
     * @return
     */
    @RequiresPermissions("permission:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<ResultModel> createPermission(@RequestParam Map<String,Object> paramMap) {
        try {
            int i = permissionService.createPermission(paramMap);
            if(i > 0){
                return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        } catch (Exception e) {
            log.info("错误信息：{}" + e.getMessage());
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
    }

    /**
     * 修改权限信息
     * 参数：
     *      链接描述：describe
     *      相关链接：url
     *      权限名称：name
     * @param paramMap
     * @return
     */
    @RequiresPermissions("permission:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<ResultModel> updatePermission(@RequestParam Map<String,Object> paramMap) {
        try {
            int i = permissionService.updatePermission(paramMap);
            if(i > 0){
                return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        } catch (Exception e) {
            log.info("错误信息：{}" + e.getMessage());
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
    }

    @RequiresPermissions("permission:delete")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<ResultModel> deletePermission(Integer permissionId) {
        try {
            permissionService.deletePermission(permissionId);
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        } catch (Exception e) {
            log.info("错误信息：{}" + e.getMessage());
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
    }

}
