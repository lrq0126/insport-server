package com.dwgj.mlxydedicatedline.mapper.customerPack;

import com.dwgj.mlxydedicatedline.entity.customerPack.PackValuation;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 保价产品明细(PackValuation)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2021/11/10 16:27
*/
@Repository
public interface PackValuationMapper extends MyBatisBaseDao<PackValuation, Integer> {

    @Override
    int insert(PackValuation record);

    @Override
    int insertSelective(PackValuation record);

    @Override
    PackValuation selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(PackValuation record);

    @Override
    int updateByPrimaryKey(PackValuation record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    void insertList(@Param("packValuationList") List<PackValuation> packValuationList);

    List<PackValuation> selectPackValuationByPackId(int packId);

    /**
     * 删除当前订单的产品货值信息
     * @param packId
     */
    void deleteByPackId(Integer packId);
}