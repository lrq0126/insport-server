package com.example.warehouse.config.shiro;

import com.example.warehouse.config.CorsFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ShiroConfig {


    //3、设置拦截页面
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager")SecurityManager securityManager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        LinkedHashMap<String , String> map = new LinkedHashMap<>();
        map.put("/user/login","anon");
        map.put("/user/register","anon");
        map.put("/user/logout","logout");
        map.put("/user/create","authc");
        map.put("/user/update","authc");
        map.put("/user/delete","authc");
        // 角色管理接口拦截
        map.put("/role/permission/*","authc");
        map.put("/role/rolePermission/*","authc");
        map.put("/role/userRole/*","authc");
        // 客户管理 接口拦截
        map.put("/account/customerAccount/*","authc");
        map.put("/customer/*","authc");
        // 渠道管理 接口拦截
        map.put("/route/transportationRoute/*","authc");
        // 消息通知 接口拦截
        map.put("/sys/sysNotice/*","authc");
        // 快递管理 接口拦截
        map.put("/company/*","authc");
        // 包裹 接口拦截 （入库，登记等）
        map.put("/goods/*","authc");
        // 仓库管理 接口拦截
        map.put("/storage/*","authc");
        // 包裹管理 接口拦截
        map.put("/customerPack/*","authc");
        // 用户接口拦截
        map.put("/user/*","authc");
        // 签收接口拦截
        //map.put("/dwReceipt/*","authc");
        map.put("/dwReceipt/*","anon");
        // 更新轨迹信息、获取轨迹信息不用拦截
        map.put("/AppDataService/updateTracking","anon");
        map.put("/AppDataService/getOrderData","anon");

        // 机器入库、分拣
        map.put("/goods/ocr/inWareSorting","anon");
        map.put("/goods/ocr/inWareSortingPicture","anon");
        map.put("/goods/ocr/otherSystemOcrInWareSorting","anon");
        // 订单拍照、更新
        map.put("/customerPack/ocr/updateOrder","anon");
        map.put("/customerPack/ocr/uploadOrderPicture","anon");
        map.put("/customerPack/ocr/otherOrcUpdateOrder","anon");

        factoryBean.setFilterChainDefinitionMap(map);

        Map<String, Filter> filters = factoryBean.getFilters();
        filters.put("authc",new CorsFilter());
        factoryBean.setFilters(filters);

        return factoryBean;
    }

    //2、将realm的信息存入securityMessager
    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("dwShiroRealm") ShiroRealm dwShiroRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(dwShiroRealm);
        return securityManager;
    }

    //1、注册自定义的realm
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
