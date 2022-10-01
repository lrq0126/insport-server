package com.example.warehouse.service.deliveryTask;

import com.alibaba.fastjson.JSONArray;
import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.common.SequenceCode;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.deliveryTask.DeliveryTask;
import com.example.warehouse.entity.deliveryTask.DeliveryTaskDetail;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.enums.customerPack.PackTypeEnums;
import com.example.warehouse.mapper.customerPack.CustomerPackMapper;
import com.example.warehouse.mapper.deliveryTask.DeliveryTaskDetailMapper;
import com.example.warehouse.mapper.deliveryTask.DeliveryTaskMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.customer.CustomerPackVo;
import com.example.warehouse.vo.deliveryTask.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class DeliveryTaskServiceImpl implements DeliveryTaskService {

    @Autowired
    private DeliveryTaskMapper deliveryTaskMapper;
    @Autowired
    private DeliveryTaskDetailMapper deliveryTaskDetailMapper;
    @Autowired
    private CustomerPackMapper customerPackMapper;

    @Override
    public ResponseEntity<PageResultModel> getDeliveryTask(DeliveryTaskReqVo deliveryTaskReqVo) {
        PageData pageData = PageHelp.editPage(String.valueOf(deliveryTaskReqVo.getPageNumber()), String.valueOf(deliveryTaskReqVo.getPageSize()));
        int count = deliveryTaskMapper.selectCount(deliveryTaskReqVo);
        pageData.setTotal(count);
        if (count > 0){
            deliveryTaskReqVo.setPageNumber(pageData.getPageNumber());
            List<DeliveryTask> deliveryTaskList = deliveryTaskMapper.selectDeliveryTaskList(deliveryTaskReqVo);
            return new ResponseEntity<>(PageResultModel.ok(deliveryTaskList, pageData), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getDeliveryTaskAll(DeliveryTaskReqVo deliveryTaskReqVo) {
        List<DeliveryTask> deliveryTaskList = deliveryTaskMapper.selectDeliveryTaskAllList(deliveryTaskReqVo);
        return new ResponseEntity<>(ResultModel.ok(deliveryTaskList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getDeliveryTaskById(Integer id) {
        DeliveryTaskRespVo deliveryTaskRespVo = deliveryTaskMapper.selectDeliveryTaskById(id);
        if(deliveryTaskRespVo == null){
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.ok(deliveryTaskRespVo), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> padGetDeliveryTaskById(Integer id) {
        DeliveryTaskRespVo deliveryTaskRespVo = deliveryTaskMapper.selectDeliveryTaskById(id);
        if(deliveryTaskRespVo == null){
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        if(!CollectionUtils.isEmpty(deliveryTaskRespVo.getDeliveryTaskDetails())){
            List<DeliveryTaskDetailRespVo> deliveryTaskDetails = deliveryTaskRespVo.getDeliveryTaskDetails();
            List<DeliveryTaskDetailRespVo> sonDeliveryTaskDetails = new ArrayList<>();
            for (DeliveryTaskDetailRespVo deliveryTaskDetailRespVo : deliveryTaskDetails) {
                if(deliveryTaskDetailRespVo.getPackNumber() > 1){
                    String sonOrderNumber;
                    int i = 1;
                    while (i <= deliveryTaskDetailRespVo.getPackNumber()){
                        DeliveryTaskDetailRespVo deliveryTaskDetail = new DeliveryTaskDetailRespVo();
                        sonOrderNumber = i + "~" +deliveryTaskDetailRespVo.getOrderNumber();
                        deliveryTaskDetail.setOrderNumber(sonOrderNumber);
                        deliveryTaskDetail.setTaskOrder(deliveryTaskDetailRespVo.getTaskOrder());
                        deliveryTaskDetail.setParentOrder(deliveryTaskDetailRespVo.getOrderNumber());
                        i++ ;
                        sonDeliveryTaskDetails.add(deliveryTaskDetail);
                    }

                }
            }

            if(!CollectionUtils.isEmpty(sonDeliveryTaskDetails)){
                deliveryTaskRespVo.getDeliveryTaskDetails().addAll(sonDeliveryTaskDetails);
            }

        }

        return new ResponseEntity<>(ResultModel.ok(deliveryTaskRespVo), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ResultModel> saveDeliveryTask(DeliveryTaskSaveReqVo saveReqVo) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_NOT_LOGIN), HttpStatus.OK);
        }
        List<DeliveryTaskDetail> deliveryTaskDetailList = JSONArray.parseArray(saveReqVo.getDeliveryTaskDetails(), DeliveryTaskDetail.class);
        if(CollectionUtils.isEmpty(deliveryTaskDetailList)){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.TASK_DETAIL_IS_NULL_ERROR), HttpStatus.OK);
        }
        if(saveReqVo.getId() == null){
            saveReqVo.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
            saveReqVo.setCreateId(user.getId());
            saveReqVo.setStatus(1);
            saveReqVo.setVersion(1);
            saveReqVo.setTaskStatus(1);
            int todayCount = deliveryTaskMapper.selectTodayCount(DateUtil.dayStartTime(new Date()), DateUtil.dayEndTime(new Date()));
            String taskOrder = SequenceCode.deliveryTaskOrder(todayCount+1);
            saveReqVo.setTaskOrder(taskOrder);
            deliveryTaskMapper.insert(saveReqVo);
            saveReqVo = deliveryTaskMapper.selectByTaskOrder(taskOrder);
            for (DeliveryTaskDetail deliveryTaskDetail : deliveryTaskDetailList) {
                deliveryTaskDetail.setDeliveryTaskId(saveReqVo.getId());
                deliveryTaskDetail.setTaskOrder(saveReqVo.getTaskOrder());
            }
            deliveryTaskDetailMapper.insertList(deliveryTaskDetailList);
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }else {
            saveReqVo.setUpdateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
            saveReqVo.setUpdateId(user.getId());
            deliveryTaskMapper.updateByPrimaryKeySelective(saveReqVo);

            deliveryTaskDetailMapper.deleteByTaskId(saveReqVo.getId());

            deliveryTaskDetailMapper.insertList(deliveryTaskDetailList);

            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ResultModel> deleteDeliveryTask(Integer id) {
        deliveryTaskMapper.deleteByPrimaryKey(id);
        deliveryTaskDetailMapper.deleteByTaskId(id);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> orderDelivery(String orderNumber) {
        CustomerPackVo customerPackVo = customerPackMapper.findByOrderNumber(orderNumber);
        if(customerPackVo == null){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR, orderNumber+": 订单不存在"), HttpStatus.OK);
        }
        if(customerPackVo.getPackType() == PackTypeEnums.OUT_WARE_HOUSE.getValue()){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR, orderNumber+": 订单已经出库"), HttpStatus.OK);
        }
        if(customerPackVo.getPackType() != PackTypeEnums.WAIT_DELIVERY.getValue()){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR, orderNumber+": 订单暂未付款，不能出库"), HttpStatus.OK);
        }
        customerPackMapper.updateDeliver(customerPackVo.getId(), customerPackVo.getInternationalTransshipmentNo());
        return new ResponseEntity<>(ResultModel.ok(orderNumber+": 出库完成"), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<ResultModel> overTask(Integer taskId) {
        DeliveryTask deliveryTask = deliveryTaskMapper.selectByPrimaryKey(taskId);
        deliveryTask.setTaskStatus(2);
        int i = deliveryTaskMapper.updateByPrimaryKeySelective(deliveryTask);
        if(i == 1){
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
    }

}
