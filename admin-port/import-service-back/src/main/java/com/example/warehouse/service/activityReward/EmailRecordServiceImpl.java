package com.example.warehouse.service.activityReward;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.MailSendUtils;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.Customer;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.activityReward.EmailRecord;
import com.example.warehouse.entity.wx.WechatMessage;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.mapper.CustomerMapper;
import com.example.warehouse.mapper.activityReward.EmailRecordMapper;
import com.example.warehouse.mapper.customerGroup.CustomerGroupLinkMapper;
import com.example.warehouse.mapper.wx.WechatMessageMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.activity.EmailRecordReqVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("emailRecordService")
@Slf4j
public class EmailRecordServiceImpl implements EmailRecordService {

    @Autowired
    private EmailRecordMapper emailRecordMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private WechatMessageMapper wechatMessageMapper;
    @Override
    public ResponseEntity<PageResultModel> getEmailRecordList(EmailRecordReqVo emailRecordReqVo) {
        PageData pageData = PageHelp.editPage(emailRecordReqVo);
        int count = emailRecordMapper.selectEmailRecordCount(emailRecordReqVo);
        pageData.setTotal(count);
        List<EmailRecord> emailRecordList = new ArrayList<>();
        if(count > 0){
            emailRecordReqVo.setPageNumber(pageData.getPageNumber());
            emailRecordList = emailRecordMapper.selectEmailRecordList(emailRecordReqVo);
        }
        return new ResponseEntity<>(PageResultModel.ok(emailRecordList, pageData), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ResultModel> saveEmail(EmailRecord emailRecord) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_NOT_LOGIN), HttpStatus.OK);
        }
        int i;
        if(emailRecord.getId() == null){
            emailRecord.setCreateId(user.getId());
            emailRecord.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
            emailRecord.setCreateName(user.getUsername());

            emailRecord.setStatus(1);
            emailRecord.setVersion(1);
            i = emailRecordMapper.insertSelective(emailRecord);
        } else {
            emailRecord.setUpdateId(user.getId());
            emailRecord.setUpdateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
            emailRecord.setUpdateName(user.getUsername());
            i = emailRecordMapper.updateByPrimaryKeySelective(emailRecord);
        }

        if(i == 0){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public void sendEmail(EmailRecord emailRecord) {
        long startTime = new Date().getTime();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return;
        }
        if(emailRecord.getId() != null){
            emailRecord.setId(null);
        }
        // ?????????????????????????????????
        emailRecord.setCreateId(user.getId());
        emailRecord.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
        emailRecord.setCreateName(user.getUsername());
        emailRecord.setSenderId(user.getId());
        emailRecord.setSenderName(user.getUsername());
        emailRecord.setSendTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
        emailRecord.setStatus(1);
        emailRecord.setVersion(1);
        int i = emailRecordMapper.insertSelective(emailRecord);
        if(i == 0){
            System.out.println("????????????????????????");
            return;
        }

        // ??????????????????
        if("personal".equals(emailRecord.getSendObjectType())){
            personalSendEmail(emailRecord);
        }else {
            groupSendEmail(emailRecord);
        }
        long endTime = new Date().getTime();
        System.out.println("????????????"+(endTime - startTime) + "??????");
    }

    /**
     * ??????????????????
     * @param emailRecord
     */
    private void personalSendEmail(EmailRecord emailRecord) {
        Customer customer = customerMapper.findById(emailRecord.getSendObjectId());
        if(StringUtils.isEmpty(customer.getEmail())){
            log.info("{}?????????????????????????????????????????????", customer.getLoginName());
            return;
        }

        if("html".equals(emailRecord.getContentType())){
            // TODO ????????????HTML???????????????
        }else {
            MailSendUtils.sendMail(customer.getEmail(), emailRecord.getContent(), emailRecord.getTitle(), emailRecord.getContentType());
            WechatMessage wechatMessage = new WechatMessage();
            wechatMessage.setCreateTime(new Date());
            wechatMessage.setStatus(1);
            wechatMessage.setCustomerNo(customer.getCustomerNo());
            wechatMessage.setType("send_personal_email");
            wechatMessage.setMessage(emailRecord.getContent());
            wechatMessageMapper.insert(wechatMessage);
        }
    }

    /**
     * ????????????????????????
     * @param emailRecord
     */
    private void groupSendEmail(EmailRecord emailRecord) {
        List<Customer> customerList =
                customerMapper.selectByGroupId(emailRecord.getSendObjectId(), null, null);
        if(CollectionUtils.isEmpty(customerList)){
            return;
        }
        if("html".equals(emailRecord.getContentType())){
            // TODO ????????????HTML???????????????
        }else {
            List<WechatMessage> wechatMessageList = new ArrayList<>();
            for (Customer customer : customerList) {
                if(StringUtils.isEmpty(customer.getEmail())){
                    log.info("{}?????????????????????????????????????????????", customer.getLoginName());
                    continue;
                }
                MailSendUtils.sendMail(customer.getEmail(), emailRecord.getContent(), emailRecord.getTitle(),
                        emailRecord.getContentType());
                WechatMessage wechatMessage = new WechatMessage();
                wechatMessage.setCreateTime(new Date());
                wechatMessage.setStatus(1);
                wechatMessage.setCustomerNo(customer.getCustomerNo());
                wechatMessage.setType("send_group_email");
                wechatMessage.setMessage(emailRecord.getContent());
                wechatMessageList.add(wechatMessage);
            }
            if(!CollectionUtils.isEmpty(wechatMessageList)){
                wechatMessageMapper.insertList(wechatMessageList);
            }
        }
    }




    @Override
    public ResponseEntity<ResultModel> getEmail(int id) {
        EmailRecord emailRecord = emailRecordMapper.selectByPrimaryKey(id);
        if(emailRecord == null){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.EMAIL_IS_DELETED), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(emailRecord), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> deleteEmailRecord(int id) {
        emailRecordMapper.deleteByPrimaryKey(id);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }
}
