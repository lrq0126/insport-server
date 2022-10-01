package com.example.warehouse.mapper.sys;

import com.example.warehouse.entity.sys.UserPacked;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.sys.UserPackedReqVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 用户打包明细表(UserPacked)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/7/22 16:51
*/
@Repository
public interface UserPackedMapper extends MyBatisBaseDao<UserPacked, Integer> {

    @Override
    int insert(UserPacked record);

    @Override
    int insertSelective(UserPacked record);

    @Override
    UserPacked selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(UserPacked record);

    @Override
    int updateByPrimaryKey(UserPacked record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    int getUserPackedCount(UserPackedReqVo userPackedReqVo);

    List<UserPacked> getUserPackedList(UserPackedReqVo userPackedReqVo);
}