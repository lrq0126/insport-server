package com.example.warehouse.service.wechat.impl;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.wx.WechatReply;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.mapper.wx.WechatReplyMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.wechat.WechatReplyService;
import com.example.warehouse.vo.wechat.WechatReplyReqVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WechatReplyServiceImpl implements WechatReplyService {

    @Autowired
    private WechatReplyMapper wechatReplyMapper;

    @Override
    public ResponseEntity<PageResultModel> getReplyList(WechatReplyReqVo wechatReplyReqVo) {
        PageData pageData = PageHelp.editPage(wechatReplyReqVo);

        int count = wechatReplyMapper.getReplyListCount(wechatReplyReqVo);
        pageData.setTotal(count);
        if(count > 0){
            wechatReplyReqVo.setPageNumber(pageData.getPageNumber());
            List<WechatReply> wechatReplyList = wechatReplyMapper.getReplyList(wechatReplyReqVo);
            return new ResponseEntity<>(PageResultModel.ok(wechatReplyList, pageData), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getReply(int id) {
        WechatReply wechatReply = wechatReplyMapper.selectByPrimaryKey(id);
        return new ResponseEntity<>(ResultModel.ok(wechatReply), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> saveReply(WechatReply wechatReply) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_NOT_LOGIN), HttpStatus.OK);
        }
        if(wechatReply.getId() == null){

            List<WechatReply> wechatReplyList = wechatReplyMapper.checkRuleAndKeyWord(wechatReply.getReplyRule(), wechatReply.getKeyWord(), null);
            if(!CollectionUtils.isEmpty(wechatReplyList)){
                List<String> replyRuleList = wechatReplyList.stream().map(WechatReply::getReplyRule).collect(Collectors.toList());
                List<String> keyWordList = wechatReplyList.stream().map(WechatReply::getKeyWord).collect(Collectors.toList());
                if(replyRuleList.contains(wechatReply.getReplyRule())){
                    return new ResponseEntity<>(ResultModel.error(ResultStatus.WECHAT_REPLY_RULE_REPEAT_ERROR), HttpStatus.OK);
                }
                if(keyWordList.contains(wechatReply.getKeyWord())) {
                    return new ResponseEntity<>(ResultModel.error(ResultStatus.WECHAT_KEY_WORD_REPEAT_ERROR), HttpStatus.OK);
                }
            }
            wechatReply.setCreateId(user.getId());
            wechatReply.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            wechatReply.setStatus(1);
            wechatReply.setVersion(1);
            int i = wechatReplyMapper.insertSelective(wechatReply);
            if(i != 1){
                return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }else {
            List<WechatReply> wechatReplyList = wechatReplyMapper.checkRuleAndKeyWord(wechatReply.getReplyRule(), wechatReply.getKeyWord(), wechatReply.getId());
            if(!CollectionUtils.isEmpty(wechatReplyList)){
                List<String> replyRuleList = wechatReplyList.stream().map(WechatReply::getReplyRule).collect(Collectors.toList());
                if(replyRuleList.contains(wechatReply.getReplyRule())){
                    return new ResponseEntity<>(ResultModel.error(ResultStatus.WECHAT_REPLY_RULE_REPEAT_ERROR), HttpStatus.OK);
                }
                List<String> keyWordList = wechatReplyList.stream().map(WechatReply::getKeyWord).collect(Collectors.toList());
                if(keyWordList.contains(wechatReply.getKeyWord())) {
                    return new ResponseEntity<>(ResultModel.error(ResultStatus.WECHAT_KEY_WORD_REPEAT_ERROR), HttpStatus.OK);
                }
            }
            wechatReply.setUpdateId(user.getId());
            wechatReply.setUpdateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            wechatReply.setStatus(1);
            int i = wechatReplyMapper.updateByPrimaryKey(wechatReply);
            if(i != 1){
                return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResultModel> deleteReply(int id) {
        wechatReplyMapper.deleteByPrimaryKey(id);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }
}
