package com.example.warehouse.controller.weChat;

import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.service.wechat.WechatOfficialAccountLogService;
import com.example.warehouse.vo.wechat.WechatLogReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/WechatOfficialAccountLog")
public class WechatOfficialAccountLogController {

    @Autowired
    private WechatOfficialAccountLogService wechatOfficialAccountLogService;

    @PostMapping("getWeChatLog")
    public ResponseEntity<PageResultModel> getWeChatLog(WechatLogReqVo wechatLogReqVo){
        return wechatOfficialAccountLogService.getWeChatLog(wechatLogReqVo);
    }


}
