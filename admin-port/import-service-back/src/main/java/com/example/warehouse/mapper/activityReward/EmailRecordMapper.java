package com.example.warehouse.mapper.activityReward;

import com.example.warehouse.entity.activityReward.EmailRecord;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.activity.EmailRecordReqVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 邮件记录(EmailRecord)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/9/7 18:24
*/
@Repository
public interface EmailRecordMapper extends MyBatisBaseDao<EmailRecord, Integer> {

    @Override
    int insert(EmailRecord record);

    @Override
    int insertSelective(EmailRecord record);

    @Override
    EmailRecord selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(EmailRecord record);

    @Override
    int updateByPrimaryKey(EmailRecord record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    int selectEmailRecordCount(EmailRecordReqVo emailRecordReqVo);

    List<EmailRecord> selectEmailRecordList(EmailRecordReqVo emailRecordReqVo);
}