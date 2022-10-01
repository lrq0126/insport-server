package com.example.warehouse.mapper.customer;

import com.example.warehouse.entity.customer.CustomerIntegral;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.customer.CustomerReqVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 客户积分(CustomerIntegral)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/3/10 11:55
*/
@Repository
public interface CustomerIntegralMapper extends MyBatisBaseDao<CustomerIntegral, Integer> {

    @Override
    int insert(CustomerIntegral record);

    @Override
    int insertSelective(CustomerIntegral record);

    @Override
    CustomerIntegral selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(CustomerIntegral record);

    @Override
    int updateByPrimaryKey(CustomerIntegral record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    void insertList(@Param("customerIntegralList") List<CustomerIntegral> customerIntegralList);

    /**
     * 查询客户的积分
     * @param customerId
     * @return
     */
	double selectIntegralCount(@Param("customerId") int customerId);

    /**
     * 查询客户的下级提供的积分
     * @param fromCustomerId
     * @param customerId
     * @return
     */
    double selectPopularizeIntegralCount(@Param("fromCustomerId")int fromCustomerId, @Param("customerId") int customerId);

    int getIntegralsCountByCustomerId(CustomerReqVo customerReqVo);

    List<CustomerIntegral> getIntegralsDetailByCustomerId(CustomerReqVo customerReqVo);

    double getIntegralsHaveObtainedByCustomerId(CustomerReqVo customerReqVo);

    double getIntegralsUsedByCustomerId(CustomerReqVo customerReqVo);
}