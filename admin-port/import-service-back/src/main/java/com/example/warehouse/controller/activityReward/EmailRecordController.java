package com.example.warehouse.controller.activityReward;

import com.example.warehouse.entity.activityReward.EmailRecord;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.activityReward.EmailRecordService;
import com.example.warehouse.vo.activity.EmailRecordReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emailRecord")
public class EmailRecordController {

    @Autowired
    private EmailRecordService emailRecordService;

    @PostMapping("/getEmailRecordList")
    public ResponseEntity<PageResultModel> getEmailRecordList(EmailRecordReqVo emailRecordReqVo){
        return emailRecordService.getEmailRecordList(emailRecordReqVo);
    }

    @PostMapping("/saveEmail")
    public ResponseEntity<ResultModel> saveEmail(EmailRecord emailRecord){
        return emailRecordService.saveEmail(emailRecord);
    }

    @PostMapping("/sendEmail")
    public void sendEmail(EmailRecord emailRecord){
        emailRecordService.sendEmail(emailRecord);
    }

    @PostMapping("/getEmail")
    public ResponseEntity<ResultModel> getEmail(int id){
        return emailRecordService.getEmail(id);
    }
    @PostMapping("/deleteEmailRecord")
    public ResponseEntity<ResultModel> deleteEmailRecord(int id){
        return emailRecordService.deleteEmailRecord(id);
    }
}
