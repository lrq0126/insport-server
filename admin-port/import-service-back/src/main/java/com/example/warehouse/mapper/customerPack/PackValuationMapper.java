package com.example.warehouse.mapper.customerPack;

import com.example.warehouse.entity.customerPack.PackValuation;
import com.example.warehouse.mapper.MyBatisBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 保价产品明细(PackValuation)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2021/11/5 11:2
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

	List<PackValuation> selectByPackId(Integer packId);

    void insertList(@Param("packValuationList") List<PackValuation> packValuationList);

    int deleteByPackId(int packId);
}