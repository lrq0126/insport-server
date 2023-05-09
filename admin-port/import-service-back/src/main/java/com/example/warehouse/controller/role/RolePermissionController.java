package com.example.warehouse.controller.role;

import com.example.warehouse.entity.User;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.UserService;
import com.example.warehouse.service.role.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.example.warehouse.enums.ResultStatus.ERROR;
import static com.example.warehouse.enums.ResultStatus.SYS_ERROR;

/**
 * 角色绑定权限相关操作接口
 * 2020年2月8日21:37:27
 * @author lrq
 */
@RestController
@RequestMapping("/role/rolePermission")
@Slf4j
public class RolePermissionController {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private UserService userService;

    /**
     * 新增角色权限绑定
     * 参数：roleId            角色id
     *       permissionIds     权限id 集合
     * @param paramMap
     * @return
     */
    @RequiresPermissions("role:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<ResultModel> createRolePermission(@RequestParam Map<String,Object> paramMap, HttpSession session, HttpServletRequest request) {
        try {
            User user = (User) session.getAttribute("user");
            if ( null == user ){
                String authorization = request.getHeader("Authorization");
                if ( StringUtils.isNotBlank(authorization) ){
                    authorization = authorization.substring(authorization.indexOf("USER"));
                    user = userService.getUserByUserCode(authorization);
                }
            }
            paramMap.put("userId",user.getId());
            int i = rolePermissionService.createRolePermission(paramMap);
            if(i > 0){
                return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        } catch (Exception e) {
            log.info("错误信息：{}" + e.getMessage());
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
    }

    @RequiresPermissions("role:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<ResultModel> updateRolePermission(@RequestParam Map<String,Object> paramMap) {
        try {
            int i = rolePermissionService.updateRolePermission(paramMap);
            if(i > 0){
                return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        } catch (Exception e) {
            log.info("错误信息：{}" + e.getMessage());
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
    }

    @RequiresPermissions("role:delete")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<ResultModel> deletePermission(Integer rolePermissionId) {
        try {
            rolePermissionService.deleteRolePermission(rolePermissionId);
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        } catch (Exception e) {
            log.info("错误信息：{}" + e.getMessage());
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
    }
}






















