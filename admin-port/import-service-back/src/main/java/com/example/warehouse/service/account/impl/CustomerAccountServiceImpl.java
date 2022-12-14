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
        // ????????????????????????????????????????????????????????????
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
        // ????????????????????????????????????????????????????????????
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

            // ???????????????
            Customer customer = customerMapper.selectByPrimaryKey(Integer.valueOf(map.get("customerNo").toString()));
            CustomerAccount customerAccount = new CustomerAccount();
            customerAccount.setCustomerNo(customer.getId().toString());
            customerAccount.setCustomerName(customer.getCustomerName());
            customerAccount.setCostType("1");//??????
            customerAccount.setCreateId(user.getUsercode());
            customerAccount.setCreateName(user.getUsername());
            customerAccount.setAmount(new BigDecimal(map.get("amount").toString()));
            customerAccount.setCreateTime(DateUtil.timestamp2String(new Date()));
            customerAccount.setStatus(1);
            customerAccount.setLoginName(customer.getLoginName());
            //????????????  ???????????????????????? 1?????????   2????????????   3???????????????   4?????????  5????????? 6????????? 10?????????
            customerAccount.setChannel(map.get("channel").toString());
            String message = map.get("message") == null ? "" : map.get("message").toString();
            if (map.get("channel") != null) {
                if ("1".equals(map.get("channel").toString()))
                    message = message + "--????????????--";
                else if ("2".equals(map.get("channel").toString()))
                    message = message + "--???????????????--";
                else if ("3".equals(map.get("channel").toString()))
                    message = message + "--????????????--";
                else if ("4".equals(map.get("channel").toString()))
                    message = message + "--????????????--";
                else if ("5".equals(map.get("channel").toString()))
                    message = message + "--????????????--";
                else if ("10".equals(map.get("channel").toString()))
                    message = message + "--????????????--";
                else if ("6".equals(map.get("channel").toString()))
                    message = message + "--?????????--";
                else if ("7".equals(map.get("channel").toString()))
                    message = message + "--?????????--";
                else if ("8".equals(map.get("channel").toString()))
                    message = message + "--??????--";
            }
            customerAccount.setMessage(message);
            customerAccount.setCurrency("?????????");//?????????
            customerAccount.setVersion("1");

            i = customerAccountMapper.insert(customerAccount);

            // ???????????????
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

            // ?????????????????????????????????????????????????????????????????????????????????????????????
            String customerRemarks = String.valueOf(map.get("customerRemarks"));
            if(!StringUtils.isEmpty(customerRemarks) && !customerRemarks.equals(customer.getCustomerRemarks())){
                System.out.println("??????????????????");
                customer.setCustomerRemarks(customerRemarks);
                customerMapper.updateByPrimaryKey(customer);
            }
            // ???????????????????????????
            String ifsend = map.get("ifsend") == null ? "1" : map.get("ifsend").toString();
            if ("1".equals(ifsend)) {
                sendMessageServer.sendMessageForTopUp(customerAccount, map.get("noticeMessage").toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("?????????????????????:{}", e.getMessage());
        }
        return i;
    }

    @Override
    public int rechargeSave(Map<String, Object> map) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        try {
            Customer customer = customerMapper.selectByPrimaryKey(Integer.valueOf(map.get("customerNo").toString()));

            CustomerAccount customerAccount = customerAccountMapper.selectByPrimaryKey(Integer.valueOf(map.get("id").toString()));

            // ???????????????
            CustomerMoney customerMoney = customerMoneyMapper.selectByCustomerNo(customer.getCustomerNo());

            // ??????????????????
            BigDecimal amount = customerAccount.getAmount();
            // ???????????????
            BigDecimal ymt = customerMoney.getAmount();
            // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            ymt = ymt.divide(amount);
            customerMoney.setAmount(ymt.add(new BigDecimal(map.get("amount").toString())));

            customerMoney.setLastUpdateTime((DateUtil.timestamp2String(new Date())));
            // ????????????
            customerMoneyMapper.updateByPrimaryKeySelective(customerMoney);

            // ??????????????????
            customerAccount.setAmount(new BigDecimal(map.get("amount").toString()));
            customerAccount.setLastUpdateId(user.getId());
            customerAccount.setLastUpdateTime(new Date());
            customerAccount.setChannel("1");//????????????  ???????????????????????? 1?????????   2?????????   3????????????   4???????????????  5?????????

            customerAccountMapper.updateByPrimaryKey(customerAccount);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("???????????????{}", e.getMessage());
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
        // ????????????????????????????????????????????????????????????
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
        // ???????????????????????????????????????????????????
        if (agentOrder != null) {
            map.put("agentId", agentOrderAccount.getAgentId());
            map.put("money", agentOrderAccount.getMoney());
            // ???????????????
            agentOrderMapper.updateAgentNumber(map);
        } else {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            // ?????????????????????????????????????????????
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
        log.info("????????????");
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
        customerAccount.setCostType("3");//??????
        customerAccount.setCreateId(user == null ? "25" : user.getUsercode());
        customerAccount.setCreateName(user == null ? "?????????" : user.getUsername());

        customerAccount.setCreateTime(DateUtil.timestamp2String(new Date()));
        customerAccount.setStatus(1);
        customerAccount.setLoginName(customer.getLoginName());
        customerAccount.setChannel("5");//????????????  ???????????????????????? 1?????????   2?????????   3????????????   4???????????????  5?????????
        String message = "--????????????--";
        customerAccount.setMessage(message);
        customerAccount.setCurrency("?????????");//?????????
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
     * costType     ???????????????1?????????2?????????3??????
     * startTime    ????????????
     * endTime      ????????????
     * loginName    ??????ID
     * customerName ????????????
     * @param map
     * @param request
     * @param response
     * @return
     */
    @Override
    public void exportCustomerRecharge(Map<String,Object> map,
                                      HttpServletRequest request, HttpServletResponse response) {
        log.info("???????????????{}", map);
        User user = userMapper.findByCode(String.valueOf(map.get("usercode")));

        if(user == null){
            log.info("???????????????????????????");
            return;
        }
        // ????????????????????????????????????????????????????????????
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
        // ?????????????????????
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

        // ??????????????????sheet
        HSSFSheet sheet = hssfWorkbook.createSheet("??????????????????");
        // ?????????0?????????
        HSSFRow row = sheet.createRow(0);
        // ?????????????????????
        HSSFCellStyle style = hssfWorkbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(style);
        cell.setCellValue("??????");

        cell = row.createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("??????ID");

        cell = row.createCell(2);
        cell.setCellStyle(style);
        cell.setCellValue("????????????");

        cell = row.createCell(3);
        cell.setCellStyle(style);
        cell.setCellValue("?????????");

        cell = row.createCell(4);
        cell.setCellStyle(style);
        cell.setCellValue("????????????");

        cell = row.createCell(5);
        cell.setCellStyle(style);
        cell.setCellValue("????????????");

        cell = row.createCell(6);
        cell.setCellStyle(style);
        cell.setCellValue("??????");

        cell = row.createCell(7);
        cell.setCellStyle(style);
        cell.setCellValue("????????????");

        int rowNumber = 1;
        HSSFRow rowBody;
        for (CustomerAccount customerAccount : customerAccountList) {
            rowBody = sheet.createRow(rowNumber);
//            row.createCell(rowNumber);
            rowBody.createCell(0).setCellValue(rowNumber);

            rowBody.createCell(1).setCellValue(customerAccount.getLoginName());

            rowBody.createCell(2).setCellValue(customerAccount.getCustomerName());
            rowBody.createCell(3).setCellValue(customerAccount.getCreateName());
            // 1?????????   2?????????   3?????????
            switch (customerAccount.getCostType()){
                case "1" :
                    rowBody.createCell(4).setCellValue("??????");
                    break;
                case  "2" :
                    rowBody.createCell(4).setCellValue("??????");
                    break;
                case  "3" :
                    rowBody.createCell(4).setCellValue("??????");
                    break;
            }
            rowBody.createCell(5).setCellValue(customerAccount.getAmount().doubleValue());
            rowBody.createCell(6).setCellValue(customerAccount.getCreateTime());
            rowBody.createCell(7).setCellValue(customerAccount.getMessage());
            // ????????????????????????
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
            fileUtil.downloadPostExcel("??????????????????", outPath, request, response);

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
