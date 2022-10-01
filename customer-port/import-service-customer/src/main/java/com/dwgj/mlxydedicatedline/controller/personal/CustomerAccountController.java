package com.dwgj.mlxydedicatedline.controller.personal;

import com.dwgj.mlxydedicatedline.commons.ShiroUtil;
import com.dwgj.mlxydedicatedline.config.UserThreadContext;
import com.dwgj.mlxydedicatedline.entity.Customer;
import com.dwgj.mlxydedicatedline.entity.CustomerAccount;
import com.dwgj.mlxydedicatedline.resultType.PageData;
import com.dwgj.mlxydedicatedline.resultType.PageHelp;
import com.dwgj.mlxydedicatedline.resultType.PageResultModel;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.CustomerAccountService;
import com.dwgj.mlxydedicatedline.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dwgj.mlxydedicatedline.enums.ResultStatus.ERROR;

/**
 * @desc 个人中心 -- 消费记录
 * @author guoshengwen
 * @date 2019年11月4日23:01:40
 */
@Controller
@Slf4j
@RequestMapping("/personal/customerAccount")
public class CustomerAccountController {
    @Autowired
    private CustomerAccountService customerAccountService;
    @Autowired
    private CustomerService customerService;

    /**
     * 分页查询消费记录列表
     * @param paramMap pageSize pageNumber businessNumber:业务号  orderNumber:订单号
     *                 routeName:渠道名称 address:目的地  costType：费用类型
     *                 channel：充值渠道
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResultModel> list(@RequestParam Map<String,Object> paramMap, HttpSession session){
        if(StringUtils.isEmpty(paramMap.get("pageSize"))){
            paramMap.put("pageSize",10);
        }
        if(StringUtils.isEmpty(paramMap.get("pageNumber"))){
            paramMap.put("pageNumber",0);
        }
        Customer customer = UserThreadContext.getUser();
        log.info("customer:",customer);
        PageData data = PageHelp.editPage(paramMap.get("pageNumber").toString(),paramMap.get("pageSize").toString());
        paramMap.put("pageNumber",data.getPageNumber());
        paramMap.put("pageSize",data.getPageSize());
        customer = customerService.selectByPrimaryKey(customer.getId());
        paramMap.put("customerNo",customer.getId());
        int count = 0;
        float totalYmt = 0;
        List<CustomerAccount> list = new ArrayList<>();
        try {
            count = customerAccountService.countSelectCustomerAccountList(paramMap);
            if(count > 0 ){
                list = customerAccountService.selectCustomerAccountList(paramMap);
                totalYmt = customerAccountService.sumSingleCustomerYmt(paramMap);
            }
        }catch (Exception e){
            log.error("分页查询消费记录列表出错，原因：{}",e.getMessage());
            return new ResponseEntity<>(PageResultModel.error(ERROR),HttpStatus.OK);
        }
        data.setTotal(count);
        Map<String,Object> result = new HashMap<>();
        result.put("page",data);
        result.put("ymt",totalYmt);
        return new ResponseEntity<>(PageResultModel.ok(list,result),HttpStatus.OK);
    }
}
