package com.example.warehouse.config.shiro;

import com.example.warehouse.entity.User;
import com.example.warehouse.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    UserMapper userMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();
        int userId = user.getId();
        Set<String> roles = userMapper.findRoleByUserId(userId);
        Set<String> permissions = userMapper.findByUserId(userId);
        Set<String> userPermissions = new HashSet<>();
        System.out.println("角色名称："+roles+",  权限："+permissions);
        for (String str : permissions) {
            if(!StringUtils.isEmpty(str)){
                userPermissions.add(str);
            }
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        roles.forEach(roleName ->
            simpleAuthorizationInfo.addRole(roleName)
        );
        userPermissions.forEach(permissionName ->
            simpleAuthorizationInfo.addStringPermission(permissionName)
        );
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getUsername();
        String password = String.valueOf(token.getPassword());
        User user = userMapper.findByUserName(userName);
        if (user == null){
            //没有返回登录用户名对应的SimpleAuthenticationInfo对象时,就会在LoginController中抛出UnknownAccountException异常
            throw new UnknownAccountException("用户名不存在!");
        }
        if(!user.getPassword().equals(password)){
            throw new IncorrectCredentialsException("用户密码不匹配!");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,password,this.getName());
        return info;
    }

}
