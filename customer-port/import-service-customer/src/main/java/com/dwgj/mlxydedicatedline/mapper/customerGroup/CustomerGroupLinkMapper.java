package com.dwgj.mlxydedicatedline.mapper.customerGroup;

import com.dwgj.mlxydedicatedline.entity.customerGroup.CustomerGroupLink;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 客户分组关联表(CustomerGroupLink)表Mapper类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2021/10/19 16:24
 */
@Repository
public interface CustomerGroupLinkMapper extends MyBatisBaseDao<CustomerGroupLink, Integer> {

    @Override
    int insert(CustomerGroupLink record);

    @Override
    int insertSelective(CustomerGroupLink record);

    @Override
    CustomerGroupLink selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(CustomerGroupLink record);

    @Override
    int updateByPrimaryKey(CustomerGroupLink record);

    @Override
    int deleteByPrimaryKey(Integer id);

    int removeByPrimaryKey(Integer id);



    int selectCustomerNumByGroupId(int customerGroupId);

    void insertList(@Param("customerGroupLinkList") List<CustomerGroupLink> customerGroupLinkList);

    void deleteByGroupIdAndCustomerId(@Param("groupId") int groupId, @Param("customerIdList") List<String> customerIdList);

    List<CustomerGroupLink> selectGroupIdByCustomerId(Integer customerId);

    List<CustomerGroupLink> selectByGroupId(String groupId);

    List<String> getCustomerIdByGroupIds(@Param("groupIds") List<String> groupIds);
}