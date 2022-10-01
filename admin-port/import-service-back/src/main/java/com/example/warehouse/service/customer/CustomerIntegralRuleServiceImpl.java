package com.example.warehouse.service.customer;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.customer.CustomerIntegralRule;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.mapper.customer.CustomerIntegralRuleMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.integralRule.CustomerIntegralRuleReqVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerIntegralRuleServiceImpl implements CustomerIntegralRuleService{

    @Autowired
    private CustomerIntegralRuleMapper customerIntegralRuleMapper;

    @Override
    public ResponseEntity<PageResultModel> getIntegralRuleList(CustomerIntegralRuleReqVo integralRuleReqVo) {
        PageData pageData = PageHelp.editPage(integralRuleReqVo);
        int count = customerIntegralRuleMapper.selectIntegralRuleCount(integralRuleReqVo);
        pageData.setTotal(count);
        if(count > 0){
            integralRuleReqVo.setPageNumber(pageData.getPageNumber());
            List<CustomerIntegralRule> customerIntegralRuleList = customerIntegralRuleMapper.getIntegralRuleList(integralRuleReqVo);
            return new ResponseEntity<>(PageResultModel.ok(customerIntegralRuleList, pageData), HttpStatus.OK);
        }

        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getIntegralRuleById(int id) {
        CustomerIntegralRule customerIntegralRule = customerIntegralRuleMapper.selectByPrimaryKey(id);
        return new ResponseEntity<>(ResultModel.ok(customerIntegralRule), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> saveIntegralRule(CustomerIntegralRule customerIntegralRule) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();

        if(user == null){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_NOT_LOGIN), HttpStatus.OK);
        }
        List<CustomerIntegralRule> customerIntegralRuleList = customerIntegralRuleMapper.getAllIntegralRule();

        if(customerIntegralRule.getId() == null){
            List<String> integralTypeList = customerIntegralRuleList.stream().map(CustomerIntegralRule::getIntegralType).collect(Collectors.toList());
            if(integralTypeList.contains(customerIntegralRule.getIntegralType())){
                return new ResponseEntity<>(ResultModel.error(ResultStatus.INTEGRAL_RULE_TYPE_REPEAT_ERROR), HttpStatus.OK);
            }
            customerIntegralRule.setCreateId(user.getId());
            customerIntegralRule.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            customerIntegralRule.setStatus(1);
            customerIntegralRule.setVersion(1);
            int i = customerIntegralRuleMapper.insertSelective(customerIntegralRule);
            if(i < 1){
                return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR, "新增积分规则失败"), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        } else {

            for (CustomerIntegralRule integralRule : customerIntegralRuleList) {
                if(integralRule.getIntegralType().equals(customerIntegralRule.getCreateTime())
                        && !integralRule.getId().equals(customerIntegralRule.getCreateId())){
                    return new ResponseEntity<>(ResultModel.error(ResultStatus.INTEGRAL_RULE_TYPE_REPEAT_ERROR), HttpStatus.OK);
                }
            }
            customerIntegralRule.setUpdateId(user.getId());
            customerIntegralRule.setUpdateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            customerIntegralRule.setStatus(1);
            customerIntegralRule.setVersion(customerIntegralRule.getVersion() + 1);
            int i = customerIntegralRuleMapper.updateByPrimaryKey(customerIntegralRule);
            if(i < 1){
                return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR, "编辑积分规则失败"), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<ResultModel> deleteIntegralRule(int id) {
        customerIntegralRuleMapper.deleteByPrimaryKey(id);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

}
