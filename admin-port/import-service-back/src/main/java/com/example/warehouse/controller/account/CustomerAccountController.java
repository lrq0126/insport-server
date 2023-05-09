package com.example.warehouse.controller.account;

import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.common.DateUtil;
import com.example.warehouse.entity.CustomerAccount;
import com.example.warehouse.log.SysLog;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.account.CustomerAccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.example.warehouse.enums.ResultStatus.*;


@Controller
@RequestMapping("/account/customerAccount")
@Slf4j
public class CustomerAccountController {
    @Autowired
    private CustomerAccountService accountService;

    /**
     * 统计客户订单列表
     * @param param
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity list(@RequestParam Map<String,Object> param){
        return accountService.accountStatisticList(param);
    }

    /**
     * 查询客户充值、扣费历史
     * @param param pageNumber  pageSize  customerNo 客户id
     *              costType 费用类型：1充值、2扣费、3退款  routeName 渠道名称（模糊查询）
     *              startTime 开始时间  endTime  结束时间
     * @return
     */
    @RequiresPermissions("customerAccount:getSingleCustomerList")
    @RequestMapping(value = "/getSingleCustomerList",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity getSingleCustomerList(@RequestParam Map<String,Object> param){
//        PageData data = (PageData) PageHelp.initPage(param).get("page");
//        int total = accountService.countSelectSingleCustomerAccountList(param);
//        BigDecimal ymt = accountService.sumSingleYmt(param);
//        List<CustomerAccount> list = accountService.selectSingleCustomerAccountList(param);
//        data.setTotal(total);
//        Map<String,Object> result = new HashMap<>();
//        result.put("page",data);
//        result.put("ymt",ymt);
//        return new ResponseEntity<>(PageResultModel.ok(list,result), HttpStatus.OK);
        return accountService.selectSingleCustomerAccountList(param);
    }

//    @RequiresPermissions("customerAccount:getSingleCustomerList")
    @RequestMapping(value = "/accountReset",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity accountReset(String customerNo){

        int i = 0;
        try {
            i = accountService.accountReset(customerNo);
        }catch (Exception e){
            return new ResponseEntity<>(PageResultModel.error(ERROR), HttpStatus.OK);
        }
        if ( i == 1 ){
            return new ResponseEntity<>(PageResultModel.ok(), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.error(ACCOUNT_RESERFAIL), HttpStatus.OK);
    }

    /**
     * 查询所有客户充值历史
     * @param param pageNumber  pageSize  customerNo 客户id
     *              costType 费用类型：1充值、2扣费、3退款  routeName 渠道名称（模糊查询）
     *              startTime 开始时间  endTime  结束时间
     * @return
     */
    @RequestMapping(value = "/getCustomerRechargeList",method = RequestMethod.POST)
    @ResponseBody
    @SysLog(value = "查询客户充值历史",type = SysLog.Type.SELECT,contentId = "Map.param.loginName")
    public ResponseEntity<PageResultModel> getCustomerRechargeList(@RequestParam Map<String,Object> param){
        return accountService.selectSingleCustomerAccountList(param);

    }

    /**
     * 给某个客户充值
     * @param param operatorName 操作员名称  customerNo客户id amount 充值金钱   id：customer_account的id
     * @return
     */
    @RequiresPermissions("customerAccount:recharge")
    @RequestMapping(value = "/recharge",method = RequestMethod.POST)
    @ResponseBody
    @SysLog(value = "充值[会员ID]",type = SysLog.Type.ADD,contentId = "Map.param.loginName")
    public ResponseEntity recharge(@RequestParam Map<String,Object> param){
        try{
            int i = accountService.recharge(param);
            if(i > 0){
                return new ResponseEntity<>(ResultModel.ok("充值成功"), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResultModel.error(ERROR,"充值失败，请联系管理员"), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
    }

    /**
     * 进入修改客户充值修改页面
     * @param param id:修改记录id
     * @return
     */
    @RequiresPermissions("customerAccount:getRechargeRecord")
    @RequestMapping(value = "/getRechargeRecord",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity getRechargeRecord(@RequestParam Map<String,Object> param){
        try{
            if (!param.containsKey("id")){
                return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
            }
            int id = Integer.valueOf(param.get("id").toString());
            CustomerAccount account = accountService.selectByPrimaryKey(id);
            return new ResponseEntity<>(ResultModel.ok(account), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
    }

    /**
     * 保存客户充值金额
     * @param param operatorName 操作员名称  customerNo客户id amount 充值金钱 channel 充值渠道
     * @return
     */
//    @RequiresPermissions("customerAccount:rechargeSave")
    @RequestMapping(value = "/rechargeSave",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity rechargeSave(@RequestParam Map<String,Object> param){
        try{
            int i = accountService.rechargeSave(param);
            if(i > 0){
                return new ResponseEntity<>(ResultModel.ok("修改成功"), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResultModel.error(ERROR,"修改失败，请联系管理员"), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
    }

    /**
     * 撤单
     * @param param id:列表的记录id
     * @return
     */
//    @RequiresPermissions("customerAccount:rollbackRacharge")
    @RequestMapping(value = "/rollbackRacharge",method = RequestMethod.POST)
    @ResponseBody
    @SysLog(value = "撤销充值操作[会员ID]",type = SysLog.Type.DELETE,contentId = "Map.param.loginName")
    public ResponseEntity rollbackRacharge(@RequestParam Map<String,Object> param){
        try{
            if (!param.containsKey("id")){
                return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
            }
            int id = Integer.valueOf(param.get("id").toString());
            CustomerAccount account = accountService.selectByPrimaryKey(id);
            String createTime = account.getCreateTime();
            if ( !DateUtil.compare_date_rollback(createTime)) {
                // 判断是否超级管理员，如果是，则可以进行强制撤单
                // 业务逻辑---

                return new ResponseEntity<>(ResultModel.error(ORDER_OUT_OF_DATE), HttpStatus.OK);
            }
            accountService.rollbackRacharge(account);
            return new ResponseEntity<>(ResultModel.ok(account), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(SYS_ERROR), HttpStatus.OK);
        }
    }

    /**
     * 查询已支付的订单
     * @param param
     * @return
     */
    @RequestMapping(value = "/selectHavePaidList",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<PageResultModel> selectHavePaidList(@RequestParam Map<String,Object> param){
        return accountService.selectHavePaidList(param);

    }

    /**
     * 根据条件导出Excel文件
     * @param map
     * @return
     */
    @RequestMapping(value = "/exportCustomerRecharge",method = RequestMethod.POST)
    @ResponseBody
    public void exportCustomerRecharge(@RequestBody String map,
                                                 HttpServletRequest request, HttpServletResponse response){
        Map map1 = JSONObject.parseObject(map, Map.class);
        accountService.exportCustomerRecharge(map1, request, response);
    }


    /**
     * 历史充值记录
     * @param requestMap
     * @return
     */
    @RequestMapping(value = "/getCustomerTopUpHistory",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<PageResultModel> getCustomerTopUpHistory(@RequestParam Map<String,Object> requestMap){
        return accountService.getCustomerTopUpHistory(requestMap);
    }

}
