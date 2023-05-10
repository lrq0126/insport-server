package com.example.warehouse.service;

import com.example.warehouse.controller.weChat.WXMessageBean;
import com.example.warehouse.entity.Customer;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.customerPack.CustomerOrderReqVo;
import com.example.warehouse.vo.customer.CustomerReqVo;
import com.example.warehouse.vo.customer.PopularizeDetailReqVo;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface CustomerService {

    ResponseEntity<PageResultModel> getGoodsListByCustomerNo(CustomerOrderReqVo customerOrderReqVo);

    ResponseEntity<PageResultModel> getOrderListByCustomerId(CustomerOrderReqVo customerOrderReqVo);

    ResponseEntity<ResultModel> save(Map<String, Object> params);

    int update(Customer customer);

    int del(String goodsNo, String usercode);

//    List<Customer> findAll(Map<String,Object> map);
    ResponseEntity<PageResultModel> findAll(CustomerReqVo customerReqVo);

//    int countFindAll(Map<String,Object> map);

    List<Customer> find(Map<String, Object> map);

    /**
     * 根据id 获取用户实体
     * @param id
     * @return
     */
    Customer findById(int id);

    /**
     * 重置客户密码
     * @param customerId
     * @return
     */
    int resetPassword(int customerId);

    Customer getCustomerByLoginName(String loginName);

    ResponseEntity<PageResultModel> getNotInGroupCustomerList(Map<String,Object> map);

    void generateCustomerPoster(int id, String posterType, HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<PageResultModel> getPopularizeDetailByCustomerId(PopularizeDetailReqVo popularizeDetailReqVo);

    Customer subscribe(WXMessageBean wxMessageBean);

    Customer bindingCustomer(WXMessageBean wxMessageBean);

    ResponseEntity<PageResultModel> getIntegralsDetailByCustomerId(CustomerReqVo customerReqVo);

    ResponseEntity<ResultModel> getCustomerIntegrals(int customerId);

    ResponseEntity<ResultModel> copyWareAddress(int customerId);


}
