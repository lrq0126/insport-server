package com.dwgj.mlxydedicatedline.config;

import com.dwgj.mlxydedicatedline.entity.Customer;
import com.dwgj.mlxydedicatedline.mapper.CustomerMapper;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sun.misc.BASE64Decoder;

import java.io.IOException;


@Configuration
public class ShiroRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(ShiroRealm.class);
    private static final Long SESSION_KEY_TIME_OUT=3600000L;

    @Autowired
    CustomerMapper customerMapper;

    /**
     * 获取权限，授权   此系统角色暂未权限信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 登录验证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName=token.getUsername();
        String password=String.valueOf(token.getPassword());
        Customer customer = customerMapper.login(userName);
        String decoderPassword = null;
        BASE64Decoder decoder = new BASE64Decoder();

        try {
            log.info("当前登录的用户名={} 密码={} ",userName,new String(decoder.decodeBuffer(password)),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        if (customer == null){
            //没有返回登录用户名对应的SimpleAuthenticationInfo对象时,就会在LoginController中抛出UnknownAccountException异常
            throw new UnknownAccountException("用户名不存在!");
        }
        try{
            byte[] b= decoder.decodeBuffer(customer.getPassword());
            decoderPassword = new String(b,"utf-8");
        }catch (IOException e){
            e.printStackTrace();
        }
        if(!decoderPassword.equals(password)){
            throw new IncorrectCredentialsException("用户密码不匹配!");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(customer,password,getName());

        // 设置sessionKeyTimeout  shiro 的时间单位是 ms
        SecurityUtils.getSubject().getSession().setTimeout(SESSION_KEY_TIME_OUT);
        //System.out.println(" 账户 剩余 有效 时间 "+SecurityUtils.getSubject().getSession().getTimeout());

        return info;
    }

}
