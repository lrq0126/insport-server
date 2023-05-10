package com.example.warehouse.service.impl;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.SequenceCode;
import com.example.warehouse.entity.SysDictDetail;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.role.Permission;
import com.example.warehouse.entity.role.UserRole;
import com.example.warehouse.entity.sys.CommercialArea;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.mapper.SysDictDetailMapper;
import com.example.warehouse.mapper.UserMapper;
import com.example.warehouse.mapper.role.PermissionMapper;
import com.example.warehouse.mapper.role.UserRoleMapper;
import com.example.warehouse.mapper.sys.CommercialAreaMapper;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.UserService;
import com.example.warehouse.vo.UserVo;
import com.example.warehouse.vo.role.PermissionVo;
import com.example.warehouse.vo.role.UserRolePermissionVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.*;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private CommercialAreaMapper commercialAreaMapper;
    @Autowired
    private SysDictDetailMapper sysDictDetailMapper;

    @Override
    public int save(User user) {
        // TODO 添加 user_code
        String code = SequenceCode.gainSerialNo("USER");
        user.setUsercode(code);
        User user1 = userMapper.login(user.getLoginName());

        if (user1 != null) {
            return 1002;
        }
        User user2 = userMapper.findByUserName(user.getUsername());
        if (user2 != null) {
            return 1003;
        }
        if(user.getRoleId() == null){
            // 如果没有设置用户角色，则默认用户为普通用户
            user.setRoleId(4);
        }

        return userMapper.insert(user);
    }

    @Override
    public int update(User newUser, String type) {
        if ("loingOut".equals(type)) {
            User user = userMapper.login(newUser.getLoginName());
            user.setLoginCount(newUser.getLoginCount());
            return userMapper.updateLastLoginTime(user);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userCode", newUser.getUsercode());
        List<User> userList = userMapper.find(map);
        User user = new User();
        if (!userList.isEmpty()) {
            user = userList.get(0);
            user.setUsername(newUser.getUsername());
            user.setLoginName(newUser.getLoginName());
            user.setLevel(newUser.getLevel());
            user.setLastUpdateTime(DateUtil.timestamp2String(new Date()));
        }
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public int del(String usercode) {
        User user = userMapper.findByCode(usercode);
        return userMapper.del(user.getId());
    }

    @Override
    public ResponseEntity<ResultModel> findUserSelectList() {
        List<User> userList = userMapper.findUserSelectList();
        return new ResponseEntity<>(ResultModel.ok(userList), HttpStatus.OK);
    }

    @Override
    public User findById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getUserByUserCode(String userCode) {
        return userMapper.selectByUserCode(userCode);
    }

    @Override
    public List<UserVo> findAll(Map<String, Object> map,Integer loginId) {
        List<User> userList = userMapper.selectAll(map);
        UserVo userVo;
        UserRole userRole;
        List<UserVo> resultUserList = new ArrayList<>();
        User loginUser = userMapper.selectByPrimaryKey(loginId);
        for (User user : userList) {
            if(!loginId.equals(user.getId()) && user.getLevel() <= loginUser.getLevel()){
                userVo = new UserVo();
                if(user.getRoleId() != null){
                    userRole = userRoleMapper.selectByPrimaryKey(user.getRoleId());
                    userVo.setRoleId(userRole.getId());
                    userVo.setRoleName(userRole.getRoleName());
                }
                userVo.setUsername(user.getUsername());
                userVo.setUsercode(user.getUsercode());
                userVo.setLoginName(user.getLoginName());
                userVo.setPassword("******");
                userVo.setLevel(user.getLevel());
                userVo.setId(user.getId());
                userVo.setCreateTime(user.getCreateTime());
                userVo.setCommercialAreaId(user.getCommercialAreaId());
                userVo.setCommercialAreaName(user.getCommercialAreaName());
                resultUserList.add(userVo);
            }
        }


        return resultUserList;
    }

    @Override
    public int countSelectAll(Map<String, Object> map) {
        return userMapper.countSelectAll(map);
    }

    @Override
    public List<User> find(Map<String, Object> map) {
        List<User> lists = userMapper.find(map);
        List<User> newLists = new ArrayList<>();
        for (User user :
                lists) {
            if (user.getStatus() == 0) {
                newLists.add(user);
            }
        }
        return newLists;
    }

    @Override
    public User findName(String username) {
        return userMapper.login(username);
    }

    @Override
    public User login(String loginName, String password) {
        User user = userMapper.login(loginName);
        if (!user.getPassword().equals(password) || user.getStatus() == 0) {
            user = null;
        }
        return user;
    }

    @Override
    public int updateLastLogin(User user) {

        return userMapper.updateLastLoginTime(user);
    }

    /**
     * 根据函数获取子节点用户
     *
     * @param userId
     * @return
     * @date 2019年7月29日23:04:22
     */
    @Override
    public List<User> getChild(String userId) {
        return userMapper.getChild(userId);
    }


    @Override
    public int updateUserToRole(int userId, int roleId) {
        return 0;
    }

    @Override
    @Transactional
    public int setRole(int userId, int roleId) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("角色变更操作");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            UserRole userRole = userRoleMapper.selectByPrimaryKey(roleId);
            userMapper.setRole(userId, roleId,userRole.getLevel());
            transactionManager.commit(status);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
            return -1;
        }
    }

    @Override
    public UserVo findUserVoById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        UserVo userVo = new UserVo();
        userVo.setCreateTime(user.getCreateTime());
        userVo.setId(user.getId());
        userVo.setLevel(user.getLevel());
        userVo.setLoginName(user.getLoginName());
        userVo.setPassword(user.getPassword());
        userVo.setUsername(user.getUsername());
        userVo.setUsercode(user.getUsercode());
        userVo.setCommercialAreaId(user.getCommercialAreaId());
        userVo.setCommercialAreaName(user.getCommercialAreaName());
        userVo.setRoleId(user.getRoleId());
        userVo.setCommercialAreaId(user.getCommercialAreaId());
        userVo.setCommercialAreaName(user.getCommercialAreaName());

        // 查询用户所在的国家
        CommercialArea commercialArea = commercialAreaMapper.selectByPrimaryKey(user.getCommercialAreaId());
        if(commercialArea != null){
            userVo.setCountry(commercialArea.getCountry());
            userVo.setCountryId(commercialArea.getCountryId());
            SysDictDetail currency = sysDictDetailMapper.selectCountryCurrency(commercialArea.getCountryId());
            if(currency != null){
                userVo.setCurrencyId(currency.getId());
                userVo.setCurrency(currency.getSddName());
                userVo.setExchangeRate(currency.getAlternateField());
            }

        }

        UserRole userRole = userRoleMapper.selectByPrimaryKey(user.getRoleId());
        userVo.setRoleName(userRole.getRoleName());
        // 获取这个用户的角色和菜单/页面的展示权限
        //获取角色信息
        UserRolePermissionVo userRolePermissionVo = userRoleMapper.selectUserRolePermission(user.getRoleId());
        //获取该角色权限
        List<Permission> rolePermissions = permissionMapper.selectByRoleId(userRolePermissionVo.getId());

        // 1、获取 菜单
        List<Permission> parentPermissions = permissionMapper.selectByLevel(1);
        PermissionVo parentPermissionVo;
        PermissionVo pagePermissionVo;
        PermissionVo buttonPermissionVo;
        List<PermissionVo> permissionVos;
        // 3、获取所有的所有的权限信息
        permissionVos = new ArrayList<>();
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
            //4、判断此角色是否有这个菜单权限
            boolean parentPermissionBoolean = false;
            for (Permission rolePermission : rolePermissions) {
                if (permission.getId().equals(rolePermission.getId())) {
                    parentPermissionBoolean = true;
                    break;
                }
            }
            if (parentPermissionBoolean) {
                parentPermissionVo.setPossessed(1);
                permissionVos.add(parentPermissionVo);
            }
        }
        // 2、获取 页面
        List<Permission> pagePermissions = permissionMapper.selectByLevel(2);
        for (Permission pagePermission : pagePermissions) {
            pagePermissionVo = new PermissionVo();
            // 页面id
            pagePermissionVo.setId(pagePermission.getId());
            // 输入页面名
            pagePermissionVo.setLable(pagePermission.getDescribe());
            // 页面排序
            pagePermissionVo.setSortNo(pagePermission.getSortNo());
            // 页面等级
            pagePermissionVo.setLevel(pagePermission.getLevel());
            //4、判断此角色是否有这个页面权限
            boolean pagePermissionBoolean = false;
            for (Permission rolePermission : rolePermissions) {
                if (pagePermission.getId().equals(rolePermission.getId())) {
                    pagePermissionBoolean = true;
                    break;
                }
            }
            if (pagePermissionBoolean) {
                pagePermissionVo.setPossessed(1);
                permissionVos.add(pagePermissionVo);
            }
        }
        // 3、获取按钮权限
        List<Permission> buttonPermissions = permissionMapper.selectByLevel(3);
        for (Permission buttonPermission : buttonPermissions) {
            buttonPermissionVo = new PermissionVo();
            // 页面id
            buttonPermissionVo.setId(buttonPermission.getId());
            // 输入页面名
            buttonPermissionVo.setLable(buttonPermission.getDescribe());
            // 页面排序
            buttonPermissionVo.setSortNo(buttonPermission.getSortNo());
            // 页面等级
            buttonPermissionVo.setLevel(buttonPermission.getLevel());
            //4、判断此角色是否有这个页面权限
            boolean pagePermissionBoolean = false;
            for (Permission rolePermission : rolePermissions) {
                if (buttonPermission.getId().equals(rolePermission.getId())) {
                    pagePermissionBoolean = true;
                    break;
                }
            }
            if (pagePermissionBoolean) {
                permissionVos.add(buttonPermissionVo);
                buttonPermissionVo.setPossessed(1);
            }
        }
        //添加权限信息数据
        userVo.setPermissionVos(permissionVos);
        return userVo;
    }

    @Value("${server.client-url}")
    private String SERVER_URL;
    @Override
    public ResponseEntity<ResultModel> generateShareLink() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_NOT_LOGIN), HttpStatus.OK);
        }
        String url = "http://" + SERVER_URL + "/client/jiyun/#/pages/login/register?ServerID="+user.getId()+"&FromType=shareLink";
        return new ResponseEntity<>(ResultModel.ok(url), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> bingCommercialArea(int userId, int commercialAreaId, String commercialAreaName) {
        userMapper.bingCommercialArea(userId, commercialAreaId, commercialAreaName);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }


}
