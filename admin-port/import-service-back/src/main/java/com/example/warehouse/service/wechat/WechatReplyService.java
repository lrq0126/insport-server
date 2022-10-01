package com.example.warehouse.service.wechat;

import com.example.warehouse.entity.wx.WechatReply;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.wechat.WechatReplyReqVo;
import org.springframework.http.ResponseEntity;

public interface WechatReplyService {
    ResponseEntity<PageResultModel> getReplyList(WechatReplyReqVo wechatReplyReqVo);

    ResponseEntity<ResultModel> getReply(int id);

    ResponseEntity<ResultModel> saveReply(WechatReply wechatReply);

    ResponseEntity<ResultModel> deleteReply(int id);
}
