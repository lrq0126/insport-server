package com.dwgj.mlxydedicatedline.service.sys.impl;

import com.dwgj.mlxydedicatedline.config.UserThreadContext;
import com.dwgj.mlxydedicatedline.entity.Customer;
import com.dwgj.mlxydedicatedline.entity.sys.SysNotice;
import com.dwgj.mlxydedicatedline.entity.sys.SysNoticeCustomer;
import com.dwgj.mlxydedicatedline.mapper.customerGroup.CustomerGroupMapper;
import com.dwgj.mlxydedicatedline.mapper.sys.SysNoticeCustomerMapper;
import com.dwgj.mlxydedicatedline.mapper.sys.SysNoticeMapper;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.sys.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.dwgj.mlxydedicatedline.enums.ResultStatus.NOT_LOGIN_ERROR;

@Service
public class SysNoticeServiceImpl implements SysNoticeService {
    @Autowired
    private SysNoticeMapper sysNoticeMapper;

    @Autowired
    private SysNoticeCustomerMapper sysNoticeCustomerMapper;

    @Autowired
    private CustomerGroupMapper customerGroupMapper;

    @Override
    public List<SysNotice> selectNoticeList(Map<String, Object> param) {
        List<SysNotice> list = sysNoticeMapper.selectNoticeListByCustomerId(param);
        List<Integer> groupIdList = customerGroupMapper.selectGroupIdByCustomerId(Integer.parseInt(param.get("customerId").toString()));

        if(groupIdList != null && !groupIdList.isEmpty()){
            List<SysNotice> groupNoticeList = sysNoticeMapper.selectNoticeListByGroupId(groupIdList);
            if(param.containsKey("isRead") && param.get("isRead").toString() != null && !param.get("isRead").toString().isEmpty()){
                int isRead = Integer.parseInt(param.get("isRead").toString());
                for (SysNotice sysNotice : groupNoticeList) {
                    if(sysNotice.getIsRead() == isRead){
                        list.add(sysNotice);
                    }
                }
            }
        }
        return list;

    }
    @Override
    public int countSelectNoticeList(Map<String, Object> param) {
        return sysNoticeMapper.countSelectNoticeListByCustomerId(param);
    }

    @Override
    public int updateNoticeRead(Map<String, Object> param) {
        int isRead = sysNoticeCustomerMapper.checkIsRead(param);
        if(isRead > 0){
            // 已读
            return 0;
        }
        SysNoticeCustomer noticeCustomer = new SysNoticeCustomer();
        noticeCustomer.setCustomerId(Integer.valueOf(param.get("customerId").toString()));
        noticeCustomer.setNoticeId(Integer.valueOf(param.get("noticeId").toString()));
        noticeCustomer.setIsRead(2);
        noticeCustomer.setStatus(1);
        sysNoticeCustomerMapper.insert(noticeCustomer);
        return 1;
    }

    @Override
    public ResponseEntity<ResultModel> getNotReadMessage() {
        Customer customer = UserThreadContext.getUser();
        if (customer == null) {
            return new ResponseEntity<>(ResultModel.error(NOT_LOGIN_ERROR), HttpStatus.OK);
        }

        // 获取到已读取
        List<SysNoticeCustomer> sysNoticeCustomerList = sysNoticeCustomerMapper.selectNoticeMessageByCustomerId(customer.getId());
        // 获取到已读取
        List<Integer> isReadNoticeIdList;
        if(sysNoticeCustomerList != null && !sysNoticeCustomerList.isEmpty()){
            isReadNoticeIdList = sysNoticeCustomerList.stream().map(SysNoticeCustomer::getNoticeId).collect(Collectors.toList());
        }else {
            isReadNoticeIdList = null;
        }

        // 查询未读的客户通知 和 未读的系统通知
        List<SysNotice> sysNoticeList = sysNoticeMapper.selectNotReadNoticeListByCustomerId(customer.getId(), isReadNoticeIdList);

        // 查询客户所在的分组
        List<Integer> groupIdList = customerGroupMapper.selectGroupIdByCustomerId(customer.getId());
        // 查询未读的分组通知
        if(groupIdList != null && !groupIdList.isEmpty()){
            List<SysNotice> sysNoticeListGroup = sysNoticeMapper.selectNotReadNoticeListByGroupId(groupIdList, isReadNoticeIdList);
            if(sysNoticeListGroup != null && !sysNoticeListGroup.isEmpty()){
                sysNoticeList.addAll(sysNoticeListGroup);
            }
        }
        return new ResponseEntity<>(ResultModel.ok(sysNoticeList), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ResultModel> updateRead(String noticeIds) {
        Customer customer = UserThreadContext.getUser();
        if (customer == null) {
            return new ResponseEntity<>(ResultModel.error(NOT_LOGIN_ERROR), HttpStatus.OK);
        }
        if(noticeIds == null || noticeIds.isEmpty()){
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        String[] noticeIdsStr = noticeIds.split(",");

        // 客户的未读消息
        List<SysNoticeCustomer> sysNoticeCustomerList = sysNoticeCustomerMapper.selectNotReadNoticeMessageByCustomerId(customer.getId());

        List<SysNoticeCustomer> insertNoticeCustomerList = new ArrayList<>();
        List<Integer> updateNoticeIdList = new ArrayList<>();

//        List<Integer> notReadNoticeIdList = null;
        if(sysNoticeCustomerList != null && !sysNoticeCustomerList.isEmpty()) {
            List<Integer> notReadNoticeIdList = sysNoticeCustomerList.stream().map(SysNoticeCustomer::getNoticeId).collect(Collectors.toList());
            for (String noticeId : noticeIdsStr) {
                if(notReadNoticeIdList.contains(Integer.parseInt(noticeId))){
                    updateNoticeIdList.add(Integer.parseInt(noticeId));
                } else {
                    SysNoticeCustomer insertData = new SysNoticeCustomer();
                    insertData.setStatus(1);
                    insertData.setCustomerId(customer.getId());
                    insertData.setNoticeId(Integer.parseInt(noticeId));
                    insertData.setIsRead(1);
                    insertNoticeCustomerList.add(insertData);
                }
            }
        }else{
            for (String noticeId : noticeIdsStr) {
                SysNoticeCustomer insertData = new SysNoticeCustomer();
                insertData.setCustomerId(customer.getId());
                insertData.setNoticeId(Integer.parseInt(noticeId));
                insertData.setIsRead(1);
                insertData.setStatus(1);
                insertNoticeCustomerList.add(insertData);
            }
        }

//        for (String noticeId : noticeIdsStr) {
//            if(notReadNoticeIdList != null && !notReadNoticeIdList.isEmpty() && notReadNoticeIdList.contains(Integer.parseInt(noticeId))){
//                updateNoticeIdList.add(Integer.parseInt(noticeId));
//            } else {
//                SysNoticeCustomer insertData = new SysNoticeCustomer();
//                insertData.setCustomerId(customer.getId());
//                insertData.setNoticeId(Integer.parseInt(noticeId));
//                insertData.setIsRead(1);
//                insertData.setStatus(1);
//                insertNoticeCustomerList.add(insertData);
//            }
//        }


        if(!insertNoticeCustomerList.isEmpty()){
            sysNoticeCustomerMapper.insertList(insertNoticeCustomerList);
        }

        if(!updateNoticeIdList.isEmpty()){
            sysNoticeCustomerMapper.updateByCustomerIdAndNoticeIds(customer.getId(), updateNoticeIdList);
        }

        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }


}
