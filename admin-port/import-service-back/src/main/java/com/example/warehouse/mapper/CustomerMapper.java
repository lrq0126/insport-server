package com.example.warehouse.mapper;

import com.example.warehouse.entity.Customer;
import com.example.warehouse.vo.customer.CustomerReqVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface CustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Customer record);

    Customer selectByPrimaryKey(Integer id);

    List<Customer> selectAll(CustomerReqVo customerReqVo);

    int countSelectAll(CustomerReqVo customerReqVo);

//    List<Customer> selectAll(Map<String,Object> map);
//
//    int countSelectAll(Map<String,Object> map);

    int updateByPrimaryKey(Customer record);

    int del(@Param("customerNo")String customerNo, @Param("userId")int userId);

    List<Customer> find(Map<String,Object> map);

    List<Customer> findByName(@Param("name") String name,@Param("phoneNumber") String phoneNumber,
                              @Param("loginName") String loginName);

    /**
     * 根据 id 查询用户
     * 2019年11月20日14:54:26
     * @param id
     * @return
     */
    Customer findById(int id);

    Customer findByCustomerNo(String customerNo);

    List<Customer> getAllCustomer();
    List<String> getAllCustomerLoginName();

    /**
     * 查找所有的用户会员id
     * @return
     */
    List<String> getUserLoginNameList();

    /**
     * 根据会员id获取用户编码
     * @param loginName
     * @return
     */
    String getCustomerNoByLoginName(String loginName);

    Customer getCustomerByLoginName(String loginName);
    /**
     * 获取没有下单的客户列表
     * @return
     */
    List<Customer> getNoOrderCustomer();

    /**
     * 获取一个月没有下过单的客户，并且之前下过单，然后仓库内没有包裹
     * @return
     */
    List<Customer> getMonthyNoOrderCustomers();

    /**
     * 获取所有从来没有下单的客户，包括之前发过消息的
     * @return
     */
    List<Customer> getNoOrderCustomerBefore();

    List<Customer> findByIdList(@Param("idList") List<Integer> idList);

    List<Customer> getCusomterToSend30();

    int selectCountByGroupId(@Param("groupId") int groupId);

    List<Customer> selectByGroupId(@Param("groupId") int groupId, @Param("pageNumber") Integer pageNumber,
                                   @Param("pageSize") Integer pageSize);

    int getNotInGroupCustomerCount(@Param("paramMap") Map<String, Object> paramMap,
                                   @Param("inGroupCustomerIdList") List<Integer> inGroupCustomerIdList);

    List<Customer> getNotInGroupCustomerList(@Param("paramMap") Map<String, Object> paramMap,
                                             @Param("inGroupCustomerIdList") List<Integer> inGroupCustomerIdList,
                                             @Param("pageNumber")int pageNumber, @Param("pageSize")int pageSize);

    List<Customer> selectByCustomerIds(@Param("customerIdList") List<String> customerIdList);


    Customer selectByOpenid(String openID);
}