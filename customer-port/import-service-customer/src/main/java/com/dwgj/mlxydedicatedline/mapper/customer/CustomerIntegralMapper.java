package com.dwgj.mlxydedicatedline.mapper.customer;

import com.dwgj.mlxydedicatedline.entity.customer.CustomerIntegral;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import com.dwgj.mlxydedicatedline.vo.customer.CustomerReqVo;
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

    /**
     * 查询客户当前积分总和
     * @param customerId
     * @return
     */
	double selectIntegralSum(@Param("customerId") int customerId);

	int selectIntegralCount(CustomerReqVo customerReqVo);

    List<CustomerIntegral> getIntegralsDetail(CustomerReqVo customerReqVo);

    double selectPopularizeIntegralSum(@Param("fromCustomerId")int fromCustomerId, @Param("customerId") int customerId);

    int insertList(@Param("customerIntegralList") List<CustomerIntegral> customerIntegralList);
}