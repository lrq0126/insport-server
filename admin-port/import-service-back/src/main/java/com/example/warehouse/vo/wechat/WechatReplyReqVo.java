package com.example.warehouse.vo.wechat;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

@Data
public class WechatReplyReqVo extends PageVo {

    private String replyRule;

    private String keyWord;

}
