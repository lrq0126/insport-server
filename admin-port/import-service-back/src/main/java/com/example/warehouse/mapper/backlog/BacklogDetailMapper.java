package com.example.warehouse.mapper.backlog;

import com.example.warehouse.entity.backlog.BacklogDetail;
import com.example.warehouse.mapper.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 待办任务工作明细表(BacklogDetail)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/2/10 14:46
*/
@Repository
public interface BacklogDetailMapper extends MyBatisBaseDao<BacklogDetail, Integer> {

    @Override
    int insert(BacklogDetail record);

    @Override
    int insertSelective(BacklogDetail record);

    @Override
    BacklogDetail selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(BacklogDetail record);

    @Override
    int updateByPrimaryKey(BacklogDetail record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

	List<BacklogDetail> getBacklogDetailByBacklogId(int backlogId);
}