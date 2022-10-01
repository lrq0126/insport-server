package com.example.warehouse.mapper;

import com.example.warehouse.entity.AgentChannel;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.account.AgentChannelReqVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 供应商渠道(AgentChannel)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/1/13 15:27
*/
@Repository
public interface AgentChannelMapper extends MyBatisBaseDao<AgentChannel, Integer> {

    @Override
    int insert(AgentChannel record);

    @Override
    int insertSelective(AgentChannel record);

    @Override
    AgentChannel selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(AgentChannel record);

    @Override
    int updateByPrimaryKey(AgentChannel record);
	
	@Override
    int deleteByPrimaryKey(Integer id);

    int enableAgentChannel(@Param("id") Integer id, @Param("isEnable") Integer isEnable);

	int removeByPrimaryKey(Integer id);

    int selectDataCount();

    int selectCount(AgentChannelReqVo channelReqVo);

    List<AgentChannel> getAgentChannelListByAgentCode(AgentChannelReqVo channelReqVo);

}