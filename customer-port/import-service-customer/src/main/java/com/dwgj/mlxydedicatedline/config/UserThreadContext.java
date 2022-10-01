package com.dwgj.mlxydedicatedline.config;

import com.dwgj.mlxydedicatedline.entity.Customer;

/**
 * 保存当前用户信息
 */
public class UserThreadContext {
    private static final ThreadLocal<Customer> USER =new ThreadLocal<>();

    public static Customer getUser() {
        return USER.get();
    }

    public static void setUser(Customer customer) {
        USER.set(customer);
    }

    public static void remove() {
        USER.remove();
    }

}
