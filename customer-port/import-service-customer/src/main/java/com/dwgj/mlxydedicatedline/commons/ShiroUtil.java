package com.dwgj.mlxydedicatedline.commons;

import com.dwgj.mlxydedicatedline.entity.Customer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class ShiroUtil {

    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    /**
     * 查询当前登陆者
     *
     * @return
     */
    public static Customer getCurrentUser(){
        Customer currentUser = (Customer)getSubject().getPrincipal();
        return currentUser;
    }

    private static Session getSession() {
        return getSubject().getSession();
    }

    public static void setAttribute(Object arg0, Object arg1) {
        getSession().setAttribute(arg0, arg1);
    }

    public static Object getAttribute(Object arg0) {
        return getSession().getAttribute(arg0);
    }

    /**
     * 查询当前登陆者 用户名
     *
     * @return
     */
    public static String getCurrentUserName() {
        return getCurrentUser().getCustomerName();
    }

    /**
     * 查询当前登陆者 用户Id
     *
     * @return
     */
    public static String getCurrentUserId() {
        return getCurrentUser().getCustomerNo();
    }


    /**
     * 查询当前登陆者 用户Id
     *
     * @return
     */
    public static String getCurrentUserLoginName() {
        return getCurrentUser().getLoginName();
    }

    /**
     * 退出登录
     */
    public static void logout() {
           getSubject().logout();
    }
}
