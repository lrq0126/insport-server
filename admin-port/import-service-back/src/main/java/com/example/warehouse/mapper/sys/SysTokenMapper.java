package com.example.warehouse.mapper.sys;

import com.example.warehouse.entity.sys.SysToken;
import com.example.warehouse.mapper.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

/**
* 系统密匙表(SysToken)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/7/25 16:2
*/
@Repository
public interface SysTokenMapper extends MyBatisBaseDao<SysToken, Integer> {

    @Override
    int insert(SysToken record);

    @Override
    int insertSelective(SysToken record);

    @Override
    SysToken selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(SysToken record);

    @Override
    int updateByPrimaryKey(SysToken record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    SysToken getUltramodernToken();


    SysToken getSysTokenByToken(String token);
}