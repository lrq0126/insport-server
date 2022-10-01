package com.example.warehouse.service.sys.impl;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.entity.Customer;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.customerGroup.CustomerGroup;
import com.example.warehouse.entity.sys.SysNotice;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.mapper.CustomerMapper;
import com.example.warehouse.mapper.UserMapper;
import com.example.warehouse.mapper.customerGroup.CustomerGroupMapper;
import com.example.warehouse.mapper.sys.SysNoticeCustomerMapper;
import com.example.warehouse.mapper.sys.SysNoticeMapper;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.sys.SysNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional
public class SysNoticeServiceImpl implements SysNoticeService {
    @Autowired
    private SysNoticeMapper sysNoticeMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SysNoticeCustomerMapper sysNoticeCustomerMapper;

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerGroupMapper customerGroupMapper;

    @Override
    public List<SysNotice> selectNoticeList(Map<String, Object> map) {
        return sysNoticeMapper.selectNoticeList(map);
    }

    @Override
    public int countSelectNoticeList(Map<String, Object> map) {
        return sysNoticeMapper.countSelectNoticeList(map);
    }

    @Override
    public int updateNoticeToCustomer(String customerId, String number,String goodsName,String type,String content) {
//        SysNotice notice = new SysNotice();
//        notice.setCreateTime(DateUtil.timestamp2String(new Date()));
//        notice.setNoticeTitle("订单通知");
//        notice.setStatus(1);
//        notice.setReadId(customerId);
//        notice.setNoticeType(2);
//        notice.setPublisher("sys");
//        notice.setNoticeContent("您的"+type+"【"+number+"】"+goodsName+"于"+DateUtil.date2String(new Date())+content);
//        sysNoticeMapper.insert(notice);
        return 0;
    }

    @Override
    public ResponseEntity<ResultModel> getNoticeDetail(int id){

        SysNotice notice = sysNoticeMapper.selectNoticeDetail(id);
        if(notice.getNoticeType() == 1){
            notice.setReadName("所有客户");
        }else if(notice.getNoticeType() == 2){
            Customer customer = customerMapper.selectByPrimaryKey(Integer.parseInt(notice.getReadId()));
            notice.setReadName(customer.getCustomerName());
        }else {
            CustomerGroup customerGroup = customerGroupMapper.selectByPrimaryKey(Integer.parseInt(notice.getReadId()));
            notice.setReadName(customerGroup.getGroupName());
        }
        return new ResponseEntity<>(ResultModel.ok(notice), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<ResultModel> publishNotice(SysNotice notice) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USERID_NULL), HttpStatus.OK);
        }

        notice.setCreateTime(DateUtil.timestamp2String(new Date()));
        notice.setStatus(1);
        notice.setIsInitiative(1); // 主动发布消息
        notice.setPublisher(user.getUsername());
        notice.setPublisherId(user.getId().toString());

        if(notice.getReadId() == null || notice.getReadId().isEmpty()){
            notice.setReadId("all");
        }

        try {
            sysNoticeMapper.insertSelective(notice);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok("发布成功"), HttpStatus.OK);
    }



    @Override
    public int deleteNotice(String noticeId){
        try {
            SysNotice notice = sysNoticeMapper.selectByPrimaryKey(Integer.valueOf(noticeId));
            notice.setStatus(0);
            sysNoticeMapper.updateByPrimaryKey(notice);
            sysNoticeCustomerMapper.deleteCustomerNotice(String.valueOf(notice.getId()));
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        return 1;
    }
}
