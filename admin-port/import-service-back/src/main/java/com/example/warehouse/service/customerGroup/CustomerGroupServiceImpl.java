package com.example.warehouse.service.customerGroup;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.Customer;
import com.example.warehouse.entity.User;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.entity.customerGroup.CustomerGroup;
import com.example.warehouse.mapper.CustomerMapper;
import com.example.warehouse.mapper.customerGroup.CustomerGroupMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.customerGroup.CustomerGroupReqVo;
import com.example.warehouse.vo.customerGroup.CustomerGroupRespVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.warehouse.enums.ResultStatus.THIS_CUSTOMER_GROUP_IS_EXISTING;
import static com.example.warehouse.enums.ResultStatus.USER_NOT_COMMERCIAL_AREA;
import static com.example.warehouse.enums.ResultStatus.USER_NOT_LOGIN;

@Service
public class CustomerGroupServiceImpl implements CustomerGroupService {

    @Autowired
    private CustomerGroupMapper customerGroupMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public ResponseEntity<PageResultModel> getGroupList(CustomerGroupReqVo customerGroupReqVo) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }
        // 当用户所在的区域是中国总部，则不限制查看
        if(user.getCommercialAreaId() != 1){
            customerGroupReqVo.setCommercialAreaId(user.getCommercialAreaId());
        }
        PageData pageData = PageHelp.editPage(customerGroupReqVo);
        List<Integer> idList = customerGroupMapper.selectCustomerGroupCount(customerGroupReqVo);
        int count = idList.size();
        pageData.setTotal(count);
        if(count > 0){
            customerGroupReqVo.setPageNumber(pageData.getPageNumber());
            List<CustomerGroupRespVo> customerGroupRespVoList = customerGroupMapper.selectCustomerGroupList(customerGroupReqVo);
            customerGroupRespVoList = customerGroupRespVoList.stream().distinct().collect(Collectors.toList());
//            pageData.setTotal(customerGroupRespVoList == null || customerGroupRespVoList.isEmpty() ? 0 : customerGroupRespVoList.size());
            return new ResponseEntity<>(PageResultModel.ok(customerGroupRespVoList, pageData), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageResultModel> getGroupDetailById(int id, Integer pageNumber, Integer pageSize) {

        CustomerGroupRespVo customerGroupRespVo = customerGroupMapper.selectCustomerGroupById(id);
        PageData pageData = PageHelp.editPage(String.valueOf(pageNumber), String.valueOf(pageSize));
        int customerCount = customerMapper.selectCountByGroupId(id);
        pageData.setTotal(customerCount);
        if (customerCount > 0){
            List<Customer> customerList = customerMapper.selectByGroupId(id, pageData.getPageNumber(), pageSize);
            customerGroupRespVo.setCustomer(customerList);
        }
        return new ResponseEntity<>(PageResultModel.ok(customerGroupRespVo, pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> saveGroup(CustomerGroup customerGroup, boolean isEdit) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }

        int count = customerGroupMapper.selectGroupByGroupName(customerGroup);
        if(count > 0){
            return new ResponseEntity<>(ResultModel.error(THIS_CUSTOMER_GROUP_IS_EXISTING), HttpStatus.OK);
        }
        int i;
        if(isEdit){
            customerGroup.setUpdateId(user.getLastUpdateId());
            customerGroup.setUpdateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            i = customerGroupMapper.updateByPrimaryKeySelective(customerGroup);
        }else {
            customerGroup.setCreateId(user.getId());
            customerGroup.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            customerGroup.setStatus(1);
            customerGroup.setVersion(1);
            i = customerGroupMapper.insertSelective(customerGroup);
        }
        if(i == 1){
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> deleteGroup(int id) {
        int i = customerGroupMapper.deleteByPrimaryKey(id);
        if(i == 1){
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
    }
}
