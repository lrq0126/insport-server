package com.example.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.role.UserRole;
import com.example.warehouse.log.SysLog;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.UserService;
import com.example.warehouse.service.role.UserRoleService;
import com.example.warehouse.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static com.example.warehouse.enums.ResultStatus.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @RequestMapping(value = "/findUserSelectList",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResultModel> findUserSelectList(){
        return userService.findUserSelectList();
    }

    /**
     * 查询所有管理员
     * @author lrq
     * @return
     */
    @RequestMapping(value = "/find/all",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> findAll(@RequestParam Map<String, Object> map, HttpSession session,HttpServletRequest request){
        log.info("查询所有管理员---->>>>find/all");
        //TODO 防止没有传参出错，初始化分页参数
        PageData data = (PageData) PageHelp.initPage(map).get("page");
        int count = userService.countSelectAll(map);
        User user = (User) session.getAttribute("user");
        if ( null == user ){
            String authorization = request.getHeader("Authorization");
            if ( StringUtils.isNotBlank(authorization) ){
                authorization = authorization.substring(authorization.indexOf("USER"));
                user = userService.getUserByUserCode(authorization);
            }
        }
        data.setTotal(count);
        Map<String,Object> result = new HashMap<>();
        result.put("page",data);
        List<UserVo> users = new ArrayList<>();
        try {
            users = userService.findAll(map,user.getId());
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(PageResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(users,result), HttpStatus.OK);

    }


    /**
     * 创建用户
     * @author lrq
     * @param data
     * @return
     */
    @RequiresPermissions("user:create")
    @PostMapping("/create")
    public ResponseEntity<ResultModel> create(@RequestParam Map data){
        User user = JSONObject.parseObject(JSON.toJSONString(data), User.class);
        int i = userService.save(user);
        if(i == 1002){
            return new ResponseEntity<>(ResultModel.error(USER_REPEAT_ERROR), HttpStatus.OK);
        }
        if(i == 1003){
            return new ResponseEntity<>(ResultModel.error(USERNAME_REPEAT_ERROR), HttpStatus.OK);
        }
        if(i < 1){
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(SUCCESS), HttpStatus.OK);
    }

    /**
     * 修改用户
     * @author lrq
     * @param
     * @return
     */
    @RequiresPermissions("user:update")
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<ResultModel> update(@RequestParam Map<String, Object> params){
        String str = JSON.toJSONString(params);
        User user = JSON.parseObject(str,User.class);
        user.setPassword(user.getPassword());
        user.setUsername(user.getUsername());
        user.setLoginName(user.getLoginName());
        user.setUsercode(user.getUsercode());
        user.setLevel(user.getLevel());
        int i = userService.update(user,"update");
        if(i < 1){
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    /**
     * @author lrq
     * @param usercode
     * @return
     */
    @RequiresPermissions("user:delete")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResultModel> del(@Param("usercode") String usercode){
        int i = userService.del(usercode);
        if(i < 1){
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(SUCCESS), HttpStatus.OK);
    }


    /**
     * 登录
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    @SysLog(value = "登录[账号]",type = SysLog.Type.UPDATE,contentId = "Map.params.loginName" )
    public ResponseEntity<ResultModel> login(@RequestParam Map<String,Object> params, HttpServletRequest request){
        log.info("登录开始----->>>login,参数{}",params);
        String loginName = params.get("loginName").toString();
        String password = params.get("password").toString();

        Subject subject = SecurityUtils.getSubject();

//        if (subject.isAuthenticated()) {
//            subject.logout();
//        }

        // 新增权限认证操作
        try {
            String host = request.getRemoteHost();
            log.info("当前登录ip主机：{}",host);
            AuthenticationToken authenticationToken = new UsernamePasswordToken(loginName,password,false,host);

            subject.login(authenticationToken);
            User user = (User) subject.getPrincipal();
            log.info("当前登录操作员信息：{}--id:{}",user.getLoginName(),user.getId());
            UserVo userVo = userService.findUserVoById(user.getId());
            log.info("userVo当前登录操作员信息：{}--id:{}",userVo.getLoginName(),userVo.getId());
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            // 登录后设置系统的 session 过期时间为24小时
            session.setMaxInactiveInterval(3600 * 24 * 1000);
            subject.getSession().setTimeout(3600 * 24 * 1000);
             /*
            设置登录时间和登录次数，wenguosheng，2019年4月20日21:47:11
            */
            user.setLastLoginTime(DateUtil.timestamp2String(new Date()));
            if(user.getLoginCount() == null){
                user.setLoginCount("1");
            }else {
                user.setLoginCount(String.valueOf(Integer.parseInt(user.getLoginCount()) + 1));
            }
            userService.updateLastLogin(user);
            log.info("登录成功");
            return new ResponseEntity<>(ResultModel.ok(userVo), HttpStatus.OK);
        } catch (UnknownAccountException e) {
            return new ResponseEntity<>(ResultModel.error(USERNAME_ERROR), HttpStatus.OK);
        } catch (IncorrectCredentialsException e) {
            return new ResponseEntity<>(ResultModel.error(PASSWORD_ERROR), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.ok(SYS_ERROR), HttpStatus.OK);
        }
    }

    /**
     * @author lrq
     * @param
     * @return
     */
    @PostMapping(value = "/register")
    @ResponseBody
    public ResponseEntity<ResultModel> register(User user){
//        Object object[] = {loginName,username,password,level};
        log.info("注册用户开始-------->>>>>>，参数{}",user);
        Map<String,Object> selectMap = new HashMap<>();
        selectMap.put("loginName",user.getLoginName());
        selectMap.put("userName",user.getUsername());
        List<User> users = userService.find(selectMap);
        if(users.size() > 0 ){
            log.error("注册失败------<<<<<<<");
            return new ResponseEntity<>(ResultModel.error(USER_REPEAT_ERROR), HttpStatus.OK);
        }
//        User user = new User();
//        user.setLoginName(loginName);
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setLevel(Integer.parseInt(level));
        int i = userService.save(user);
        if(i < 1){
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        user = userService.findName(user.getLoginName());
        log.info("注册用户成功--------<<<<<<");
        return new ResponseEntity<>(ResultModel.ok(user), HttpStatus.OK);
    }

    /**
     *
     */
    @PostMapping(value = "/logout")
    public void logout(@Param("loginName")String loginName){
        log.info("退出登录----->>>>");
        User user = userService.findName(loginName);
        //设置退出时间
        user.setLastLogoutTime(DateUtil.timestamp2String(new Date()));
        Subject subject= SecurityUtils.getSubject();
        if(subject != null){
            subject.logout();
        }
        userService.update(user,"loginOut");
    }

    @RequiresPermissions("user:update")
    @PostMapping(value = "/set/role")
    public ResponseEntity<ResultModel> setRole(@Param("userId") int userId, @Param("roleId") int roleId){
        int i = userService.setRole(userId,roleId);
        if(i<1){
            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @PostMapping(value = "/find/role")
    public ResponseEntity<ResultModel> findRole(HttpSession session,HttpServletRequest request){
        try{
            User user = (User) session.getAttribute("user");
            if ( null == user ){
                String authorization = request.getHeader("Authorization");
                if ( StringUtils.isNotBlank(authorization) ){
                    authorization = authorization.substring(authorization.indexOf("USER"));
                    user = userService.getUserByUserCode(authorization);
                }
            }
            List<UserRole> userRoleList = userRoleService.selectByUserId(user.getId());
            return new ResponseEntity<>(ResultModel.ok(userRoleList), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }

    }

    /**
     * 生成业务分享链接
     * @return
     */
    @GetMapping("/generateShareLink")
    @ResponseBody
    public ResponseEntity<ResultModel> generateShareLink(){
        return userService.generateShareLink();
    }

    /**
     * 为用户绑定区域仓库
     * @param userId
     * @param commercialAreaId
     * @return
     */
    @PostMapping("/bingCommercialArea")
    public ResponseEntity<ResultModel> bingCommercialArea(@RequestParam("userId") int userId,
                                                          @RequestParam("commercialAreaId") int commercialAreaId,
                                                          @RequestParam("commercialAreaName") String commercialAreaName){
        return userService.bingCommercialArea(userId, commercialAreaId, commercialAreaName);
    }
}
