package com.example.warehouse.service.account.impl;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.FileUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.*;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.mapper.AgentOrderMapper;
import com.example.warehouse.mapper.CustomerAccountMapper;
import com.example.warehouse.mapper.CustomerMapper;
import com.example.warehouse.mapper.UserMapper;
import com.example.warehouse.mapper.customerMoney.CustomerMoneyMapper;
import com.example.warehouse.mapper.customerPack.CustomerPackMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.account.CustomerAccountService;
import com.example.warehouse.service.wechat.SendMessageServer;
import com.example.warehouse.vo.account.customerAccount.AccountDeductionVo;
import com.example.warehouse.vo.account.customerAccount.AccountTopVo;
import com.example.warehouse.vo.packVo.OrderEvaluateRespVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.warehouse.enums.ResultStatus.ERROR;
import static com.example.warehouse.enums.ResultStatus.USER_NOT_COMMERCIAL_AREA;
import static com.example.warehouse.enums.ResultStatus.USER_NOT_LOGIN;

@Service
@Transactional
@Slf4j
public class CustomerAccountServiceImpl implements CustomerAccountService {

    @Value("${server.domain}")
    private String DOMAIN;
    @Value("${upload.location}")
    private String LOCATION;

    @Autowired
    private CustomerAccountMapper customerAccountMapper;
    @Autowired
    private AgentOrderMapper agentOrderMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerMoneyMapper customerMoneyMapper;
    @Autowired
    private CustomerPackMapper customerPackMapper;
    @Autowired
    private SendMessageServer sendMessageServer;
    @Autowired
    private UserMapper userMapper;
    private FileUtil fileUtil;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return customerAccountMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(CustomerAccount record) {
        return customerAccountMapper.insert(record);
    }

    @Override
    public int insertSelective(CustomerAccount record) {
        return customerAccountMapper.insertSelective(record);
    }

    @Override
    public CustomerAccount selectByPrimaryKey(Integer id) {
        return customerAccountMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CustomerAccount record) {
        return customerAccountMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CustomerAccount record) {
        return customerAccountMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<CustomerAccount> selectCustomerAccountList(Map<String, Object> map) {
        return customerAccountMapper.selectCustomerAccountList(map);
    }

    @Override
    public int countSelectCustomerAccountList(Map<String, Object> map) {
        return customerAccountMapper.countSelectCustomerAccountList(map);
    }

    @Override
    public ResponseEntity<PageResultModel> accountStatisticList(Map<String, Object> map) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }
        // 当用户所在的区域是中国总部，则不限制查看
        if(user.getCommercialAreaId() != 1){
            map.put("commercialAreaId", user.getCommercialAreaId());
//            queryReqVo.setCommercialAreaId(user.getCommercialAreaId());
        }

        if(map.containsKey("customerName")){
            String customerName = (String) map.get("customerName");
            if(customerName.indexOf("*") == 0){
                customerName = customerName.substring(1);
                map.put("customerName", customerName);
            }
        }
        PageData data = (PageData) PageHelp.initPage(map).get("page");

        int total = customerAccountMapper.countAccountStatisticList(map);

        float amount = customerAccountMapper.countCustomerAmount(map);

        data.setTotal(total);
        Map<String,Object> result = new HashMap<>();
        result.put("page",data);
        result.put("amount",amount);

        List<CustomerAccountVo> customerAccountVoList = customerAccountMapper.accountStatisticList(map);

        for (CustomerAccountVo customerAccountVo : customerAccountVoList) {
            AccountTopVo accountTopVo = customerAccountVo.getAccountTopVo();
            if (accountTopVo != null) {
                customerAccountVo.setRechargeTotal(accountTopVo.getRechargeTotal());
                customerAccountVo.setRechargeTime(accountTopVo.getRechargeTime());
                customerAccountVo.setAmountAll(accountTopVo.getAmountAll());
            }
            AccountDeductionVo accountDeductionVo = customerAccountVo.getAccountDeductionVo();
            if (accountDeductionVo != null) {
                customerAccountVo.setOrderTotal(accountDeductionVo.getOrderTotal());
                customerAccountVo.setOrderTime(accountDeductionVo.getOrderTime());
            }
        }

//        List<CustomerAccountVo> newCustomerAccountVoList = new ArrayList<>();
//        for (CustomerAccountVo customerAccountVo : customerAccountVoList) {
//            if(!StringUtils.isEmpty(orderStartTime) && !StringUtils.isEmpty(orderEndTime) && !StringUtils.isEmpty(customerAccountVo.getRechargeTime())){
//                try {
//                    long rechargeTime = sdf.parse(customerAccountVo.getRechargeTime()).getTime();
//                    long orderStartTimeL = sdf.parse(orderStartTime).getTime();
//                    long orderEndTimeL = sdf.parse(orderEndTime).getTime();
//                    if(orderStartTimeL > rechargeTime || orderEndTimeL < rechargeTime){
//                        continue;
//                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//            newCustomerAccountVoList.add(customerAccountVo);
//        }
//        return customerAccountVoList;
        return new ResponseEntity<>(PageResultModel.ok(customerAccountVoList, result), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageResultModel> selectSingleCustomerAccountList(Map<String, Object> map) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }
        // 当用户所在的区域是中国总部，则不限制查看
        if(user.getCommercialAreaId() != 1){
            map.put("commercialAreaId", user.getCommercialAreaId());
//            queryReqVo.setCommercialAreaId(user.getCommercialAreaId());
        }
        PageData data = (PageData) PageHelp.initPage(map).get("page");
        int total = customerAccountMapper.countSelectSingleCustomerAccountList(map);
        BigDecimal ymt = customerAccountMapper.sumSingleYmt(map);
        List<CustomerAccount> list = customerAccountMapper.selectSingleCustomerAccountList(map);
        data.setTotal(total);
        Map<String,Object> result = new HashMap<>();
        result.put("page",data);
        result.put("ymt",ymt);
        return new ResponseEntity<>(PageResultModel.ok(list,result), HttpStatus.OK);
    }

//    @Override
//    public int countSelectSingleCustomerAccountList(Map<String, Object> map) {
//        return customerAccountMapper.countSelectSingleCustomerAccountList(map);
//    }
//
//    @Override
//    public BigDecimal sumSingleYmt(Map<String, Object> map) {
//        return customerAccountMapper.sumSingleYmt(map);
//    }

    @Transactional
    @Override
    public int recharge(Map<String, Object> map) {
        int i = 0;
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();

            // 账单明细表
            Customer customer = customerMapper.selectByPrimaryKey(Integer.valueOf(map.get("customerNo").toString()));
            CustomerAccount customerAccount = new CustomerAccount();
            customerAccount.setCustomerNo(customer.getId().toString());
            customerAccount.setCustomerName(customer.getCustomerName());
            customerAccount.setCostType("1");//充值
            customerAccount.setCreateId(user.getUsercode());
            customerAccount.setCreateName(user.getUsername());
            customerAccount.setAmount(new BigDecimal(map.get("amount").toString()));
            customerAccount.setCreateTime(DateUtil.timestamp2String(new Date()));
            customerAccount.setStatus(1);
            customerAccount.setLoginName(customer.getLoginName());
            //充值渠道  （充值才需填入） 1：微信   2：支付宝   3：银行转账   4：现金  5：淘宝 6：其他 10：天猫
            customerAccount.setChannel(map.get("channel").toString());
            String message = map.get("message") == null ? "" : map.get("message").toString();
            if (map.get("channel") != null) {
                if ("1".equals(map.get("channel").toString()))
                    message = message + "--微信支付--";
                else if ("2".equals(map.get("channel").toString()))
                    message = message + "--支付宝支付--";
                else if ("3".equals(map.get("channel").toString()))
                    message = message + "--银行转账--";
                else if ("4".equals(map.get("channel").toString()))
                    message = message + "--现金支付--";
                else if ("5".equals(map.get("channel").toString()))
                    message = message + "--淘宝支付--";
                else if ("10".equals(map.get("channel").toString()))
                    message = message + "--天猫支付--";
                else if ("6".equals(map.get("channel").toString()))
                    message = message + "--优惠券--";
                else if ("7".equals(map.get("channel").toString()))
                    message = message + "--补运费--";
                else if ("8".equals(map.get("channel").toString()))
                    message = message + "--其他--";
            }
            customerAccount.setMessage(message);
            customerAccount.setCurrency("人民币");//先写死
            customerAccount.setVersion("1");

            i = customerAccountMapper.insert(customerAccount);

            // 账户余额表
            CustomerMoney customerMoney = customerMoneyMapper.selectByCustomerNo(customer.getCustomerNo());
            if(customerMoney == null){
                customerMoney = new CustomerMoney();
                customerMoney.setStatus(1);
                customerMoney.setAmount(new BigDecimal(0));
                customerMoney.setCustomerId(customer.getCustomerNo());
                customerMoney.setVersion(1);
            }

            customerMoney.setAmount(customerMoney.getAmount().add(new BigDecimal(map.get("amount").toString())));
            customerMoney.setLastUpdateTime((DateUtil.timestamp2String(new Date())));

            if(customerMoney.getId() == null){
                customerMoneyMapper.insertSelective(customerMoney);
            }else {
                customerMoneyMapper.updateByPrimaryKeySelective(customerMoney);
            }

            // 如果客户备注不为空并且客户备注跟数据库不相同时，对数据进行更改
            String customerRemarks = String.valueOf(map.get("customerRemarks"));
            if(!StringUtils.isEmpty(customerRemarks) && !customerRemarks.equals(customer.getCustomerRemarks())){
                System.out.println("更改客户备注");
                customer.setCustomerRemarks(customerRemarks);
                customerMapper.updateByPrimaryKey(customer);
            }
            // 是否发送消息给客户
            String ifsend = map.get("ifsend") == null ? "1" : map.get("ifsend").toString();
            if ("1".equals(ifsend)) {
                sendMessageServer.sendMessageForTopUp(customerAccount, map.get("noticeMessage").toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("充值失败。原因:{}", e.getMessage());
        }
        return i;
    }

    @Override
    public int rechargeSave(Map<String, Object> map) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        try {
            Customer customer = customerMapper.selectByPrimaryKey(Integer.valueOf(map.get("customerNo").toString()));

            CustomerAccount customerAccount = customerAccountMapper.selectByPrimaryKey(Integer.valueOf(map.get("id").toString()));

            // 账户余额表
            CustomerMoney customerMoney = customerMoneyMapper.selectByCustomerNo(customer.getCustomerNo());

            // 上次充值的钱
            BigDecimal amount = customerAccount.getAmount();
            // 上次的余额
            BigDecimal ymt = customerMoney.getAmount();
            // 逻辑是：先用余额减去上次充的钱，得到的是原先的余额，然后再加上这次修改后的钱，得到最新的余额
            ymt = ymt.divide(amount);
            customerMoney.setAmount(ymt.add(new BigDecimal(map.get("amount").toString())));

            customerMoney.setLastUpdateTime((DateUtil.timestamp2String(new Date())));
            // 更新余额
            customerMoneyMapper.updateByPrimaryKeySelective(customerMoney);

            // 更新充值费用
            customerAccount.setAmount(new BigDecimal(map.get("amount").toString()));
            customerAccount.setLastUpdateId(user.getId());
            customerAccount.setLastUpdateTime(new Date());
            customerAccount.setChannel("1");//充值渠道  （充值才需填入） 1：线下   2：微信   3：支付宝   4：银行转账  5：其他

            customerAccountMapper.updateByPrimaryKey(customerAccount);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改错误：{}", e.getMessage());
            return -1;
        }
        return 1;
    }

    @Override
    @Transactional
    public int rollbackRacharge(CustomerAccount account) {
        customerAccountMapper.deleteByPrimaryKey(account.getId());
        Customer customer = customerMapper.findById(Integer.valueOf(account.getCustomerNo()));
        CustomerMoney customerMoney = customerMoneyMapper.selectByCustomerNo(customer.getCustomerNo());
        BigDecimal finalMoney = new BigDecimal("0");
        finalMoney = customerMoney.getAmount().subtract(account.getAmount());
        customerMoney.setAmount(finalMoney);
        customerMoney.setLastUpdateTime(DateUtil.timestamp2String(new Date()));
        customerMoneyMapper.updateByPrimaryKey(customerMoney);
        return 0;
    }

    @Override
    public ResponseEntity<PageResultModel> selectHavePaidList(Map<String, Object> map) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }
        // 当用户所在的区域是中国总部，则不限制查看
        if(user.getCommercialAreaId() != 1){
            map.put("commercialAreaId", user.getCommercialAreaId());
//            queryReqVo.setCommercialAreaId(user.getCommercialAreaId());
        }
        PageData data = (PageData) PageHelp.initPage(map).get("page");
        int total = customerAccountMapper.countHavePaidList(map);
        List<CustomerAccount> list = customerAccountMapper.selectHavePaidList(map);
        data.setTotal(total);
        Map<String,Object> result = new HashMap<>();
        result.put("page",data);
        return new ResponseEntity<>(PageResultModel.ok(list,result), HttpStatus.OK);
    }

    @Override
    public int countHavePaidList(Map<String, Object> map) {
        return customerAccountMapper.countHavePaidList(map);
    }

    @Override
    public AgentOrderAccount getSingleSettleMentOrder(String orderNumber) {
        return customerAccountMapper.getSingleSettleMentOrder(orderNumber);
    }

    @Override
    @Transactional
    public AgentOrderAccount saveSingleSettleMentOrder(AgentOrderAccount agentOrderAccount) {
        AgentOrder agentOrder = agentOrderMapper.selectByOrderNumber(agentOrderAccount.getOrderNumber());
        Map<String, Object> map = new HashMap<>(4);
        map.put("agentNumber", agentOrderAccount.getAgentNumber());
        map.put("orderNumber", agentOrderAccount.getOrderNumber());
        // 如果不为空，则是编辑保存的结算订单
        if (agentOrder != null) {
            map.put("agentId", agentOrderAccount.getAgentId());
            map.put("money", agentOrderAccount.getMoney());
            // 更新转单号
            agentOrderMapper.updateAgentNumber(map);
        } else {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            // 如果是空的，则是新增的结算订单
            agentOrder = new AgentOrder();
            agentOrder.setAgentId(agentOrderAccount.getAgentId());
            agentOrder.setMoney(agentOrderAccount.getMoney());
            agentOrder.setAgentNumber(agentOrderAccount.getAgentNumber());
            agentOrder.setOrderNumber(agentOrderAccount.getOrderNumber());
            agentOrder.setCreateTime(DateUtil.timestamp2String(new Date()));
            agentOrder.setCreateId(user.getId().toString());
            agentOrder.setStatus(1);
            agentOrderMapper.insert(agentOrder);
        }
        customerPackMapper.updateAgentNumber(map);
        log.info("结算完成");
        return null;
    }

    @Override
    public int accountReset(String customerId) {
        Customer customer = customerMapper.selectByPrimaryKey(Integer.valueOf(customerId));
        float amount = customerAccountMapper.getAmountByLoginName(customer.getLoginName());

        User user = (User) SecurityUtils.getSubject().getPrincipal();

        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setCustomerNo(customer.getId().toString());
        customerAccount.setCustomerName(customer.getCustomerName());
        customerAccount.setCostType("3");//退款
        customerAccount.setCreateId(user == null ? "25" : user.getUsercode());
        customerAccount.setCreateName(user == null ? "管理员" : user.getUsername());

        customerAccount.setCreateTime(DateUtil.timestamp2String(new Date()));
        customerAccount.setStatus(1);
        customerAccount.setLoginName(customer.getLoginName());
        customerAccount.setChannel("5");//充值渠道  （充值才需填入） 1：线下   2：微信   3：支付宝   4：银行转账  5：其他
        String message = "--账户清零--";
        customerAccount.setMessage(message);
        customerAccount.setCurrency("人民币");//先写死
        customerAccount.setVersion("1");
        if (amount == 0) {
            return 2;
        }
        customerAccount.setAmount(new BigDecimal(-amount));

        customerAccountMapper.insert(customerAccount);
        customerMoneyMapper.amountReset(customer.getCustomerNo());
        return 1;
    }

    /**
     * costType     费用类型：1充值、2扣费、3退款
     * startTime    开始时间
     * endTime      结束时间
     * loginName    会员ID
     * customerName 用户名称
     * @param map
     * @param request
     * @param response
     * @return
     */
    @Override
    public void exportCustomerRecharge(Map<String,Object> map,
                                      HttpServletRequest request, HttpServletResponse response) {
        log.info("请求参数：{}", map);
        User user = userMapper.findByCode(String.valueOf(map.get("usercode")));

        if(user == null){
            log.info("当前用户登录已过期");
            return;
        }
        // 当用户所在的区域是中国总部，则不限制查看
        if(user.getCommercialAreaId() != 1){
            map.put("commercialAreaId", user.getCommercialAreaId());
//            queryReqVo.setCommercialAreaId(user.getCommercialAreaId());
        }
        if(org.apache.commons.lang.StringUtils.isEmpty(String.valueOf(map.get("startTime")))){
            String startTime = DateUtil.FormatDate(DateUtil.getOtherDay(-30), DateUtil.DEFAULT_DATE_FORMAT);
            String endTime = DateUtil.FormatDate(new Date(), DateUtil.DEFAULT_DATE_FORMAT);
            map.put("startTime",startTime);
            map.put("endTime",endTime);
        }
        List<CustomerAccount> customerAccountList = customerAccountMapper.selectSingleCustomerAccountList(map);
        // 创建一个新文件
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

        // 创建一个工作sheet
        HSSFSheet sheet = hssfWorkbook.createSheet("客户充值记录");
        // 设置第0行表头
        HSSFRow row = sheet.createRow(0);
        // 创建单元格样式
        HSSFCellStyle style = hssfWorkbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(style);
        cell.setCellValue("序号");

        cell = row.createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("会员ID");

        cell = row.createCell(2);
        cell.setCellStyle(style);
        cell.setCellValue("客户名称");

        cell = row.createCell(3);
        cell.setCellStyle(style);
        cell.setCellValue("经办人");

        cell = row.createCell(4);
        cell.setCellStyle(style);
        cell.setCellValue("费用类型");

        cell = row.createCell(5);
        cell.setCellStyle(style);
        cell.setCellValue("充值费用");

        cell = row.createCell(6);
        cell.setCellStyle(style);
        cell.setCellValue("时间");

        cell = row.createCell(7);
        cell.setCellStyle(style);
        cell.setCellValue("备注信息");

        int rowNumber = 1;
        HSSFRow rowBody;
        for (CustomerAccount customerAccount : customerAccountList) {
            rowBody = sheet.createRow(rowNumber);
//            row.createCell(rowNumber);
            rowBody.createCell(0).setCellValue(rowNumber);

            rowBody.createCell(1).setCellValue(customerAccount.getLoginName());

            rowBody.createCell(2).setCellValue(customerAccount.getCustomerName());
            rowBody.createCell(3).setCellValue(customerAccount.getCreateName());
            // 1：充值   2：扣费   3：退款
            switch (customerAccount.getCostType()){
                case "1" :
                    rowBody.createCell(4).setCellValue("充值");
                    break;
                case  "2" :
                    rowBody.createCell(4).setCellValue("扣费");
                    break;
                case  "3" :
                    rowBody.createCell(4).setCellValue("退款");
                    break;
            }
            rowBody.createCell(5).setCellValue(customerAccount.getAmount().doubleValue());
            rowBody.createCell(6).setCellValue(customerAccount.getCreateTime());
            rowBody.createCell(7).setCellValue(customerAccount.getMessage());
            // 执行完一次加一行
            rowNumber++
            ;
        }
        try {
            String filePath = LOCATION + "/export";
            String outPath = filePath + "/custAccount_" + DateUtil.FormatDate(new Date(), "yyyyMMddHHmmss")+".xls";
            File file = new File(filePath);
            if(!file.exists()){
                file.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(outPath);
            hssfWorkbook.write(fileOutputStream);
            fileOutputStream.close();

//            outPath = outPath.substring(4);
//            String url = DOMAIN + outPath;
            fileUtil.downloadPostExcel("财务充值记录", outPath, request, response);

//            return new ResponseEntity<>(ResultModel.ok(url), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
//            return new ResponseEntity<>(ResultModel.error(ERROR), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<PageResultModel> getCustomerTopUpHistory(Map<String, Object> requestMap) {
        String pageNumber = String.valueOf(requestMap.get("pageNumber"));
        String pageSize = String.valueOf(requestMap.get("pageSize"));
        PageData pageData = PageHelp.editPage(pageNumber, pageSize);
        int count = customerAccountMapper.countSelectCustomerAccountList(requestMap);
        pageData.setTotal(count);
        Map<String, Object> resultMap = new HashMap<>();
        List<CustomerAccount> customerAccountList = new ArrayList<>();
        BigDecimal allAmount = new BigDecimal(0);
        if(count > 0){
            requestMap.put("pageNumber", pageData.getPageNumber());
            requestMap.put("pageSize", pageData.getPageSize());
            customerAccountList = customerAccountMapper.selectCustomerAccountList(requestMap);
            allAmount = customerAccountMapper.sumSingleYmt(requestMap);
        }
        resultMap.put("customerAccountData", customerAccountList);
        resultMap.put("allAmount", allAmount.setScale(2, BigDecimal.ROUND_DOWN));

        return new ResponseEntity<>(PageResultModel.ok(resultMap, pageData), HttpStatus.OK);
    }
}
