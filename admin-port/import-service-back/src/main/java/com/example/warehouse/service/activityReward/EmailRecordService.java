package com.example.warehouse.service.activityReward;

import com.example.warehouse.entity.activityReward.EmailRecord;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.activity.EmailRecordReqVo;
import org.springframework.http.ResponseEntity;

public interface EmailRecordService {
    ResponseEntity<PageResultModel> getEmailRecordList(EmailRecordReqVo emailRecordReqVo);

    ResponseEntity<ResultModel> saveEmail(EmailRecord emailRecord);

    void sendEmail(EmailRecord emailRecord);

    ResponseEntity<ResultModel> getEmail(int id);

    ResponseEntity<ResultModel> deleteEmailRecord(int id);
}
