package com.example.warehouse.service.wechat.impl;

import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.wx.WechatOfficialAccountLog;
import com.example.warehouse.mapper.wx.WechatOfficialAccountLogMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.service.wechat.WechatOfficialAccountLogService;
import com.example.warehouse.vo.wechat.WechatLogReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("wechatOfficialAccountLog")
public class WechatOfficialAccountLogServiceImpl implements WechatOfficialAccountLogService {

    @Autowired
    private WechatOfficialAccountLogMapper wechatOfficialAccountLogMapper;

    @Override
    public ResponseEntity<PageResultModel> getWeChatLog(WechatLogReqVo wechatLogReqVo) {
        PageData pageData = PageHelp.editPage(wechatLogReqVo);
        int count = wechatOfficialAccountLogMapper.getWeChatLogCount(wechatLogReqVo);
        pageData.setTotal(count);
        if(count > 0){
            wechatLogReqVo.setPageNumber(pageData.getPageNumber());
            List<WechatOfficialAccountLog> logList = wechatOfficialAccountLogMapper.getWeChatLog(wechatLogReqVo);
            return new ResponseEntity<>(PageResultModel.ok(logList, pageData), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }



}
