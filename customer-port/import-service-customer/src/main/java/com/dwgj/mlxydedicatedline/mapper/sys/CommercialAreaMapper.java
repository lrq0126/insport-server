package com.dwgj.mlxydedicatedline.mapper.sys;

import com.dwgj.mlxydedicatedline.entity.sys.CommercialArea;
import com.dwgj.mlxydedicatedline.mapper.MyBatisBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
* 商户区域表(CommercialArea)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/9/20 11:10
*/
@Repository
public interface CommercialAreaMapper extends MyBatisBaseDao<CommercialArea, Integer> {

    @Override
    int insert(CommercialArea record);

    @Override
    int insertSelective(CommercialArea record);

    @Override
    CommercialArea selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(CommercialArea record);

    @Override
    int updateByPrimaryKey(CommercialArea record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

	List<CommercialArea> getCommercialAreaByCountry(@Param("country") String country);
}