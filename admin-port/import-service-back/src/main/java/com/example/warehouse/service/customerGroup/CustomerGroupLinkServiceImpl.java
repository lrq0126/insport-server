package com.example.warehouse.service.customerGroup;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.entity.CustomerPack;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.customerGroup.CustomerGroupLink;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.mapper.customerGroup.CustomerGroupLinkMapper;
import com.example.warehouse.mapper.customerPack.CustomerPackMapper;
import com.example.warehouse.model.ResultModel;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.warehouse.enums.ResultStatus.USER_NOT_LOGIN;

@Service
public class CustomerGroupLinkServiceImpl implements CustomerGroupLinkService {

    @Autowired
    private CustomerGroupLinkMapper customerGroupLinkMapper;
    @Autowired
    private CustomerPackMapper customerPackMapper;

    @Override
    public ResponseEntity<ResultModel> editGroupCustomer(int groupId, String customerIds, boolean isAdd) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }

        List<String> customerIdList = Arrays.asList(customerIds.split(","));
        if(customerIdList.isEmpty()){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.PLEASE_SELECT_THE_CUSTOMER_TO_OPERATE), HttpStatus.OK);
        }

        if(isAdd){
            List<CustomerGroupLink> customerGroupLinkList = new ArrayList<>();
            for (String customerId : customerIdList) {
                CustomerGroupLink customerGroupLink = new CustomerGroupLink();
                customerGroupLink.setStatus(1);
                customerGroupLink.setVersion(1);
                customerGroupLink.setCreateId(user.getId());
                customerGroupLink.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                customerGroupLink.setCustomerGroupId(groupId);
                customerGroupLink.setCustomerId(Integer.parseInt(customerId));
                customerGroupLinkList.add(customerGroupLink);
            }
            customerGroupLinkMapper.insertList(customerGroupLinkList);
            return new ResponseEntity<>(ResultModel.ok("分组添加客户完成"), HttpStatus.OK);
        }else {
            customerGroupLinkMapper.deleteByGroupIdAndCustomerId(groupId, customerIdList);
            return new ResponseEntity<>(ResultModel.ok("分组移除客户完成"), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResultModel> addGroupCustomerByRouteId(Integer groupId, String routeIds) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }
        List<String> routeIdList = Arrays.asList(routeIds.split(","));
        List<CustomerGroupLink> customerGroupLinks = customerGroupLinkMapper.selectByGroupId(String.valueOf(groupId));
        List<Integer> groupCustomerIdList = null;
        if(!CollectionUtils.isEmpty(customerGroupLinks)){
            groupCustomerIdList = customerGroupLinks.stream().map(CustomerGroupLink::getCustomerId).collect(Collectors.toList());
        }
        // 根据路线id选择客户（剔除当前分组中已存在的客户）
        List<Integer> customerIdList = customerPackMapper.selectCustomerByRouteId(routeIdList, groupCustomerIdList);
        if(!CollectionUtils.isEmpty(customerIdList)){
            List<CustomerGroupLink> customerGroupLinkList = new ArrayList<>();
            for (Integer customerId : customerIdList) {
                CustomerGroupLink customerGroupLink = new CustomerGroupLink();
                customerGroupLink.setStatus(1);
                customerGroupLink.setVersion(1);
                customerGroupLink.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
                customerGroupLink.setCustomerId(user.getId());
                customerGroupLink.setCustomerGroupId(groupId);
                customerGroupLink.setCustomerId(customerId);
                customerGroupLinkList.add(customerGroupLink);
            }
            customerGroupLinkMapper.insertList(customerGroupLinkList);
            return new ResponseEntity<>(ResultModel.ok("分组添加客户完成"), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok("暂无可添加的客户"), HttpStatus.OK);
    }

}
