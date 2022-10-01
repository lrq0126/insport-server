package com.dwgj.mlxydedicatedline.mapper;

import com.dwgj.mlxydedicatedline.dto.CustomerDTO;
import com.dwgj.mlxydedicatedline.entity.Customer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CustomerMapper继承基类
 */
@Repository
public interface CustomerMapper extends MyBatisBaseDao<Customer, Integer> {

    Customer login(String nameOrPhone);

    int updatePassword(Customer customer);

    Customer selectByLoginName(String loginName);

    Customer selectByCustomerName(String customerName);

    List<Customer> findAll();

    Customer selectByPrimaryKey(int id);

    Customer selectByOpenid(String openid);

    List<Customer> findByIdList(@Param("idList") List<Integer> customerIdList);

    /**
     * 根据openid查看数据
     * @param openid
     * @return
     */
    List<CustomerDTO> findCustomerListByOpenid(String openid);

    int findCustomerCountByOpenid(String openid);

    void updateRepeatedCustomer(@Param("customerIdList") List<Integer> customerIdList,
                                @Param("customerId") Integer customerId);

    List<Customer> selectByEmail(String email);

    void updateEmail(@Param("id") Integer id, @Param("email") String email);

    void updateCustomerCommercialArea(Customer customer);
}
