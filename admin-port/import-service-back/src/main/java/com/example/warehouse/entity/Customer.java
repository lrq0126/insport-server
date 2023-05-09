package com.example.warehouse.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *  顾客表
 * @author lrq
 */
@Data
public class Customer implements Serializable {
    private Long serialVersionUID = 1L;
    private Integer id;
    private String customerName;
    private String customerRemarks;
    private String customerNo;
    private Integer age;
    private String gender;
    private String phoneNumber;
    private String callNumber;
    private String zipCode;
    private String fullAddress;
    private String englishName;
    private String email;
    private String message;
    private Integer status;
    private String createTime;
    private Integer createId;
    private String lastUpdateTime;
    private Integer lastUpdateId;
    private String loginName;
    private String password;

    private String country;
    private String province;
    private String city;
    private String headimgurl;
    private String openid;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", customerName=").append(customerName);
        sb.append(", customerNo=").append(customerNo);
        sb.append(", age=").append(age);
        sb.append(", gender=").append(gender);
        sb.append(", phoneNumber=").append(phoneNumber);
        sb.append(", callNumber=").append(callNumber);
        sb.append(", zipCode=").append(zipCode);
        sb.append(", fullAddress=").append(fullAddress);
        sb.append(", englishName=").append(englishName);
        sb.append(", email=").append(email);
        sb.append(", message=").append(message);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", createId=").append(createId);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", lastUpdateId=").append(lastUpdateId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
