package com.dwgj.mlxydedicatedline.config;


import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {





    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager")SecurityManager securityManager){
        ShiroFilterFactoryBean filterBean = new ShiroFilterFactoryBean();
        filterBean.setSecurityManager(securityManager);
        LinkedHashMap<String , String> map = new LinkedHashMap<>();
        /**map.put("/logout","logout");
        map.put("/customer/**","user");
        map.put("/goods/**","user");
        //map.put("/customer/**","anon");
        map.put("/main","authc");
        map.put("/log/login","anon");
        map.put("/log/register","anon");
        filterBean.setLoginUrl("/log/to/login");
        filterBean.setSuccessUrl("/index");
        filterBean.setUnauthorizedUrl("/log/to/login");
         */
        //需要拦截的页面
        filterBean.setFilterChainDefinitionMap(map);
        return filterBean;
    }

    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("dwShiroRealm") ShiroRealm dwShiroRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(dwShiroRealm);
        return securityManager;
    }

    @Bean("dwShiroRealm")
    public ShiroRealm dwShiroRealm(){
        return new ShiroRealm();
    }
    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }



}
