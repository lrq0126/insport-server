package com.example.warehouse.controller.role;

import com.example.warehouse.entity.User;
import com.example.warehouse.entity.role.UserRole;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.UserService;
import com.example.warehouse.service.role.UserRoleService;
import com.example.warehouse.vo.UserVo;
import com.example.warehouse.vo.role.UserRolePermissionVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static com.example.warehouse.enums.ResultStatus.*;

/**
 * 角色创建，修改，赋值表
 * @Author lrq
 *
 */
@RestController
@RequestMapping("/role/userRole")
@Slf4j
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserService userService;

    /**
     * 获取全部角色信息
     * @return
     */
    @RequestMapping(value = "/select/userRole", method = RequestMethod.POST)
    public ResponseEntity<ResultModel> selectUserRole(@RequestParam Map<String, Object> paramMap, HttpSession session, HttpServletRequest request) {
        try {
            User user = (User) session.getAttribute("user");
            if ( null == user ){
                String authorization = request.getHeader("Authorization");
                if ( StringUtils.isNotBlank(authorization) ){
                    authorization = authorization.substring(authorization.indexOf("USER"));
                    user = userService.getUserByUserCode(authorization);
                }
            }
            paramMap.put("roleId",user.getRoleId());
            List<UserRole> userRoleList = userRoleService.selectUserRole(paramMap);
            return new ResponseEntity<>(ResultModel.ok(userRoleList), HttpStatus.OK);
        } catch (Exception e) {
            log.info("错误信息：{}" + e.getMessage());
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
    }

    /**
     * 获取 角色-权限 信息
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/select", method = RequestMethod.POST)
        public ResponseEntity<ResultModel> selectRolePermission(@RequestParam("roleId") Integer roleId) {
        try {
            UserRolePermissionVo userRole = userRoleService.selectUserRolePermission(roleId);
            return new ResponseEntity<>(ResultModel.ok(userRole), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("错误信息：{}",e.getMessage());
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
    }

    /**
     * 创建新角色
     * @param paramMap
     * @return
     * 检测暂未有问题
     */
    @RequiresPermissions("role:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<ResultModel> createRole(@RequestParam Map<String,Object> paramMap) {
        try {
            int i = userRoleService.createUserRole(paramMap);
            if(i > 0){
                return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
            }
            if(i == -1){
                return new ResponseEntity<>(ResultModel.error(ROLE_REPEAT_ERROR), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        } catch (Exception e) {
            log.info("错误信息：{}" + e.getMessage());
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
    }

    /**
     * 修改角色信息
     * @param paramMap
     * @return
     * 检测暂未有问题
     */
    @RequiresPermissions("role:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<ResultModel> updateRole(@RequestParam Map<String,Object> paramMap) {
        try {
            int i = userRoleService.updateUserRole(paramMap);
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
    @RequestMapping(value = "/useOrStop", method = RequestMethod.POST)
    public ResponseEntity<ResultModel> useOrStop(@RequestParam Map<String,Object> paramMap) {
        try {
            int i = userRoleService.useOrStop(paramMap);
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
     * 删除角色
     * @param roleId
     * @return
     * 检测暂未有问题
     */
    @RequiresPermissions("role:delete")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<ResultModel> deleteRole(@Param("roleId")Integer roleId) {
        try {
            int i = userRoleService.deleteRoleByRoleId(roleId);
            if(i > 0){
                return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        } catch (Exception e) {
            log.info("错误信息：{}" + e.getMessage());
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
    }
}
