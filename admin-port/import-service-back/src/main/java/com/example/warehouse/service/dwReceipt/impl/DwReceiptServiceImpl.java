package com.example.warehouse.service.dwReceipt.impl;

import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.ExcelUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.DwReceipt;
import com.example.warehouse.entity.DwReciptReject;
import com.example.warehouse.entity.User;
import com.example.warehouse.mapper.dwReceipt.DwReceiptDao;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.dwReceipt.DwReceiptService;
import com.example.warehouse.vo.receipt.DwReceiptVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static com.example.warehouse.enums.ResultStatus.*;

/**
 * (DwReceipt)表服务实现类
 *
 * @author makejava
 * @since 2020-05-22 13:31:56
 */
@Service("dwReceiptService")
@Slf4j
public class DwReceiptServiceImpl implements DwReceiptService {
    @Resource
    private DwReceiptDao dwReceiptDao;
    @Autowired
    private DataSourceTransactionManager transactionManager;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public DwReceiptVo queryById(Integer id) {
        return this.dwReceiptDao.findById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<DwReceipt> queryAllByLimit(int offset, int limit) {
        return this.dwReceiptDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param dwReceipt 实例对象
     * @return 实例对象
     */
    @Override
    public DwReceipt insert(DwReceipt dwReceipt) {
        this.dwReceiptDao.insert(dwReceipt);
        return dwReceipt;
    }

    /**
     * 修改数据
     *
     * @param dwReceipt 实例对象
     * @return 实例对象
     */
    @Override
    public int update(DwReceipt dwReceipt) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("修改签收快递操作");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            DwReceipt oldDwReceipt = dwReceiptDao.queryById(dwReceipt.getId());
            dwReceipt.setStatus(oldDwReceipt.getStatus());
            dwReceipt.setVersion(oldDwReceipt.getVersion());
            dwReceiptDao.update(dwReceipt);
            transactionManager.commit(status);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("修改签收错误信息：{}" + e.getMessage());
            transactionManager.rollback(status);
            return -1;
        }

    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.dwReceiptDao.deleteById(id) > 0;
    }

    @Override
    public int queryAllCount(Map<String, Object> map) {
        if (map.containsKey("receiptTime")){
            map = dateChange(map);
        }
        return dwReceiptDao.queryAllCount(map);
    }

    /**
     * 条件查询
     *
     * @param map
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> queryAll(Map<String, Object> map) {
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
        if (map.containsKey("receiptTime")){
            map = dateChange(map);
        }
        Map<String, Object> resultMap = new HashMap<>();
        PageData data = (PageData) PageHelp.initPage(map).get("page");

        int count = dwReceiptDao.queryAllCount(map);
        if (count < 1) {
            data.setTotal(0);
            resultMap.put("page", data);
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        }
        List<DwReceiptVo> dwReceiptList = dwReceiptDao.queryAll(map);
        data.setTotal(count);
        resultMap.put("page", data);
        resultMap.put("dwReceipt", dwReceiptList);
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);

    }

    @Override public int queryAllRejectCount(Map<String, Object> map) {
        return dwReceiptDao.countRejectList(map);
    }

    @Override
    public ResponseEntity<ResultModel> queryAllReject(Map<String, Object> map) {

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
//            customerCouponsReqVo.setCommercialAreaId(user.getCommercialAreaId());
        }
        Map<String, Object> resultMap = new HashMap<>();
        PageData data = (PageData) PageHelp.initPage(map).get("page");

        int count = dwReceiptDao.countRejectList(map);
        if (count < 1) {
            data.setTotal(0);
            resultMap.put("page", data);
            return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
        }
        List<DwReciptReject> dwReceiptList = dwReceiptDao.selectRejectList(map);
        data.setTotal(count);
        resultMap.put("page", data);
        resultMap.put("dwReceipt", dwReceiptList);
        return new ResponseEntity<>(ResultModel.ok(resultMap), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel>  insertReject(DwReciptReject reject) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN),HttpStatus.OK);
        }
        if ( StringUtils.isBlank(reject.getDeliveryOrderNo()) ){
            return new ResponseEntity<>(PageResultModel.error(NOT_NULL),HttpStatus.OK);
        }
        int i = dwReceiptDao.checkDeliveryOrderNo(reject.getDeliveryOrderNo());
        if ( i > 0 ){
            return new ResponseEntity<>(PageResultModel.error(REJECT_HAVEADD),HttpStatus.OK);
        }

        reject.setOperator(user.getUsername());
        reject.setCreateId(user.getId());
        reject.setCreateTime(DateUtil.timestamp2String(new Date()));
        reject.setCreateId(user.getId());
        reject.setStatus(1);
        dwReceiptDao.insertReject(reject);
        return new ResponseEntity<>(PageResultModel.ok(SUCCESS),HttpStatus.OK);
    }

    @Override public int updateReject(DwReciptReject reject) {
        if ( StringUtils.isBlank(reject.getDeliveryOrderNo()) ){
            return 1;
        }
        int i = dwReceiptDao.checkDeliveryOrderNo(reject.getDeliveryOrderNo());
        if ( i > 0 ){
            return 2;
        }
        reject.setLastUpdateTime(new Date());
        reject.setStatus(1);
        dwReceiptDao.updateReject(reject);
        return 3;
    }

    @Override public int deleteReject(DwReciptReject reject) {
        reject.setLastUpdateTime(new Date());
        reject.setDeliveryOrderNo(null);
        reject.setMessage(null);
        reject.setStatus(0);
        dwReceiptDao.updateReject(reject);
        return 3;
    }

    private Map<String, Object> dateChange(Map<String, Object> map){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            if (map.containsKey("receiptTime") && StringUtils.isNotBlank(map.get("receiptTime").toString())) {
                String receiptTime = (String) map.get("receiptTime");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(sdf.parse(receiptTime));
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                String endTimeStr = sdf.format(calendar.getTime());
                map.put("endTime", endTimeStr);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("时间转换错误:{}",e.getMessage());
        }
        return map;
    }

    @Override
    @Transactional
    public ResponseEntity<ResultModel> insertList(Map<String, Object> map) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("德威仓库签收快递操作");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            String deliveryOrderNoStr = "";
            if (map.containsKey("deliveryOrderNo")) {
                deliveryOrderNoStr = map.get("deliveryOrderNo").toString().toUpperCase().trim();
            }
            if (deliveryOrderNoStr.equals("")) {
                return new ResponseEntity<>(PageResultModel.error(DELIVERY_ORDER_NO_NULL),HttpStatus.OK);
            }
            String deliveryNo = "";
            if (map.containsKey("deliveryNo")) {
                deliveryNo = map.get("deliveryNo").toString();
            }
            int isReceipt = dwReceiptDao.queryByDeliveryOrderNo(deliveryOrderNoStr);
            int isReject = dwReceiptDao.checkDeliveryOrderNo(deliveryOrderNoStr);
            if ( isReject > 0 ){
                // 拒收
                return new ResponseEntity<>(PageResultModel.error(DELIVERY_ORDER_NO_REJECT),HttpStatus.OK);
            }
            if ( isReceipt > 0 ){
                // 已签收
                return new ResponseEntity<>(PageResultModel.error(DELIVERY_ORDER_NO_HAD_RECEIPT),HttpStatus.OK);
            }
            List<String> deliveryOrderNoList = Arrays.asList(deliveryOrderNoStr.split(","));
            List<DwReceipt> dwReceiptList = new ArrayList<>();
            DwReceipt dwReceipt;
            for (String deliveryOrderNo : deliveryOrderNoList) {
                dwReceipt = new DwReceipt();
                dwReceipt.setCreateId(user.getId());
                dwReceipt.setDeliveryOrderNo(deliveryOrderNo);
                if (null != deliveryNo && !deliveryNo.equals("")) {
                    dwReceipt.setDeliveryNo(deliveryNo);
                }
                dwReceiptList.add(dwReceipt);
            }
            if ( dwReceiptList.size() > 0 ){
                dwReceiptDao.insertList(dwReceiptList);
            }
            transactionManager.commit(status);
            return new ResponseEntity<>(PageResultModel.ok(SUCCESS),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("包裹签收错误信息：{}" + e.getMessage());
            transactionManager.rollback(status);
            return new ResponseEntity<>(PageResultModel.error(ERROR),HttpStatus.OK);
        }
    }

    @Override
    public void export(Map<String, Object> map, HttpServletResponse response, HttpServletRequest request) {
        try {
            // id集合
            List<String> idList = Arrays.asList(map.get("ids").toString().split(","));
            List<DwReceiptVo> dwReceiptList = dwReceiptDao.findByIdList(idList);

            // 表格列标题
            String[] title = {"序号", "快递单号/唛头", "签收时间", "快递公司", "备注"};
            String[][] arrs = new String[dwReceiptList.size()][];
            int i = 0;
            for ( DwReceiptVo dwReceiptVo : dwReceiptList ){
                String []sttrArr = new String[title.length];
                sttrArr[0] = ++i + "";
                sttrArr[1] = dwReceiptVo.getDeliveryOrderNo();
                sttrArr[2] = dwReceiptVo.getReceiptTime();
                sttrArr[3] = dwReceiptVo.getDeliveryName();
                sttrArr[4] = dwReceiptVo.getRemarks();
                arrs[i - 1] = sttrArr;
            }
            ExcelUtil.exportExcel1(response, request, arrs, "快递签收导出[" + DateUtil.getYmdhmsStr() + "].xls", title);

        } catch (Exception e) {
            e.printStackTrace();
            log.info("包裹签收打印错误信息：{}" + e.getMessage());
        }
    }
}
