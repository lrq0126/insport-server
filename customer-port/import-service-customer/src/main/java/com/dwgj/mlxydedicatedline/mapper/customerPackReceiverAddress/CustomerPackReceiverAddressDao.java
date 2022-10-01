package com.dwgj.mlxydedicatedline.mapper.customerPackReceiverAddress;

import com.dwgj.mlxydedicatedline.entity.CustomerPackReceiverAddress;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (CustomerPackReceiverAddress)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-09 17:15:08
 */
@Repository
public interface CustomerPackReceiverAddressDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    CustomerPackReceiverAddress queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CustomerPackReceiverAddress> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param customerPackReceiverAddress 实例对象
     * @return 对象列表
     */
    List<CustomerPackReceiverAddress> queryAll(CustomerPackReceiverAddress customerPackReceiverAddress);

    /**
     * 新增数据
     *
     * @param customerPackReceiverAddress 实例对象
     * @return 影响行数
     */
    int insert(CustomerPackReceiverAddress customerPackReceiverAddress);

    /**
     * 修改数据
     *
     * @param customerPackReceiverAddress 实例对象
     * @return 影响行数
     */
    int update(CustomerPackReceiverAddress customerPackReceiverAddress);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    CustomerPackReceiverAddress findByCustomerPackId(@Param("customerPackId") int customerPackId);

    int deleteByCustomerPackId(@Param("customerPackId") int customerPackId);

    int deleteByCustomerPackIdList(@Param("customerPackIdList") List<Integer> customerPackIdList);

    void insertList(@Param("list") List<CustomerPackReceiverAddress> customerPackReceiverAddressList);

    CustomerPackReceiverAddress findByOrderNumber(String orderNumber);
}