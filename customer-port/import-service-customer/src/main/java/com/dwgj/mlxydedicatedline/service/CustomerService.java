package com.dwgj.mlxydedicatedline.service;

import com.dwgj.mlxydedicatedline.entity.Customer;
import com.dwgj.mlxydedicatedline.entity.Goods;
import com.dwgj.mlxydedicatedline.resultType.PageResultModel;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.vo.customer.CustomerReqVo;
import com.dwgj.mlxydedicatedline.vo.customer.PopularizeDetailReqVo;
import com.dwgj.mlxydedicatedline.vo.customer.RegisterDataVo;
import org.apache.http.HttpException;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CustomerService {

    ResponseEntity<ResultModel> login(String name,String password);


//    Customer login(String name,String password);
    List<Customer> findAll();

    ResponseEntity<ResultModel> register(RegisterDataVo customer);

//    int update(Customer customer);
    ResponseEntity<ResultModel> update(Customer customer);

    ResponseEntity<ResultModel> updatePassword(Map<String,Object> paramMap);

    List<Goods> matchingOrder(Map map, String customerNo);

    int downOrder(Map map,int customerId);

    Customer selectByPrimaryKey(int id);
    /**
     * 根据openid获取用户信息
     * @param openid
     * @return
     */
    public Customer getUserInfoByOpenid(String openid);

    ResponseEntity<ResultModel> generateCustomerPoster(int id, HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<ResultModel> generateShareLink(String loginName);

    ResponseEntity<PageResultModel> getPopularizeDetailByCustomerId(PopularizeDetailReqVo popularizeDetailReqVo);

    ResponseEntity<ResultModel> getIntegralsSum(int id);

    ResponseEntity<PageResultModel> getIntegralsDetail(CustomerReqVo customerReqVo);

    ResponseEntity<ResultModel> authorizationLogin(Map<String,Object> param);

    ResponseEntity<ResultModel> checkLoginStatus(HttpServletRequest request);

    ResponseEntity<ResultModel> getCustomerWareAddress(int commercialAreaId, HttpServletRequest request);

    ResponseEntity<ResultModel> updateCustomerEmail(Integer id, String email);


}
