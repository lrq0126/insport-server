package com.example.warehouse.controller.weChat;

import com.example.warehouse.entity.wx.WechatReply;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.wechat.WechatReplyService;
import com.example.warehouse.vo.wechat.WechatReplyReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("wechatReply")
public class WeChatReplyController {

    @Autowired
    private WechatReplyService wechatReplyService;

    @PostMapping("getReplyList")
    private ResponseEntity<PageResultModel> getReplyList(WechatReplyReqVo wechatReplyReqVo){
        return wechatReplyService.getReplyList(wechatReplyReqVo);
    }

    @GetMapping("getReply")
    private ResponseEntity<ResultModel> getReply(int id){
        return wechatReplyService.getReply(id);
    }

    @PostMapping("saveReply")
    private ResponseEntity<ResultModel> saveReply(WechatReply wechatReply){
        return wechatReplyService.saveReply(wechatReply);
    }

    @PostMapping("deleteReply")
    private ResponseEntity<ResultModel> deleteReply(int id){
        return wechatReplyService.deleteReply(id);
    }

}
