package com.example.warehouse.mapper.backlog;

import com.example.warehouse.entity.backlog.Backlog;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.backlogVo.BacklogReqVo;
import com.example.warehouse.vo.backlogVo.BacklogRespVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 待办任务表(Backlog)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/2/8 16:46
*/
@Repository
public interface BacklogMapper extends MyBatisBaseDao<Backlog, Integer> {

    @Override
    int insert(Backlog record);

    @Override
    int insertSelective(Backlog record);

    @Override
    Backlog selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(Backlog record);

    @Override
    int updateByPrimaryKey(Backlog record);
	
	@Override
    int deleteByPrimaryKey(@Param("id") Integer id);
	
	int removeByPrimaryKey(@Param("id") Integer id);

    int getBacklogCount(BacklogReqVo backlogReqVo);

    List<BacklogRespVo> getBacklogList(BacklogReqVo backlogReqVo);

    int upBacklogStatus(@Param("id") int id, @Param("backlogStatus") int backlogStatus);

    BacklogRespVo getBacklogDetailById(int id);

    /**
     * 获取当前登录人的待办列表（倒序前3条）
     * @param userId
     * @return
     */
    List<Backlog> getHomeBacklogList(Integer userId);
}