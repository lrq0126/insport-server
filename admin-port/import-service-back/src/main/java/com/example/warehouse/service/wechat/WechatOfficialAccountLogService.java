package com.example.warehouse.service.wechat;

import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.vo.wechat.WechatLogReqVo;
import org.springframework.http.ResponseEntity;

public interface WechatOfficialAccountLogService {

    ResponseEntity<PageResultModel> getWeChatLog(WechatLogReqVo wechatLogReqVo);
}
