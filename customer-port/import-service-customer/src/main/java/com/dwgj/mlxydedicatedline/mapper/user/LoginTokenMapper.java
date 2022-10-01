package com.dwgj.mlxydedicatedline.mapper.user;

import com.dwgj.mlxydedicatedline.entity.Customer;
import com.dwgj.mlxydedicatedline.entity.user.LoginToken;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface LoginTokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoginToken record);

    int insertSelective(LoginToken record);

    LoginToken selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoginToken record);

    int updateByPrimaryKey(LoginToken record);

    /**
     * 根据用户token查询用户信息
     * @param userToken
     * @return
     */
    Customer getUser(String userToken);

    /**
     * 根据 customerToken，customerNo，customerId查询用户信息
     * @param map
     * @return
     */
    LoginToken getLoginTokenByParam(Map<String,Object> map);
}
