package com.dwgj.mlxydedicatedline.vo.customer;

import lombok.Data;

@Data
public class RegisterDataVo {

    private String customerName;
    private String phoneNumber;
    private String password;
    private String email;

    private String ServerID; // 客服人员ID
    private String CustID;   // 用户会员ID
    private String FromType; // 注册来源
    private String SysCC;    // 系统优惠券代码

}
