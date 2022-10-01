package com.example.warehouse.service.SysDictDetail.impl;

import com.alibaba.fastjson.JSONArray;
import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.CustomerPackNumber;
import com.example.warehouse.entity.SysDictDetail;
import com.example.warehouse.entity.TransportationRoute;
import com.example.warehouse.entity.User;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.mapper.SysDictDetailMapper;
import com.example.warehouse.mapper.transportationRoute.TransportationRouteMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.SysDictDetail.SysDictDetailService;
import com.example.warehouse.vo.backlogVo.BacklogTypeVo;
import com.example.warehouse.vo.route.RouteVo;
import com.example.warehouse.vo.sys.DictReqVo;
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

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author lrq
 * 2019年11月14日18:39:56
 */
@Service
public class SysDictDetailServiceImpl implements SysDictDetailService {

    @Autowired
    private SysDictDetailMapper sysDictDetailMapper;
    @Autowired
    private TransportationRouteMapper transportationRouteMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Override
    public List<SysDictDetail> selectOfTrans() {
        return sysDictDetailMapper.selectOfTrans();
    }

    @Override
    public List<SysDictDetail> selectOfWeightRule() {
        return sysDictDetailMapper.selectOfWeightRule();
    }

    @Override
    public List<SysDictDetail> selectCountry(Map<String, Object> map) {

        return sysDictDetailMapper.selectDictByType(map);
    }

    @Override
    @Transactional
    public int create(Map<String, Object> paramMap) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("新增国家操作");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try{
            Map<String, Object> map = new HashMap<>();
            map.put("sdmCode","country");
            List<SysDictDetail> sysDictDetails =  sysDictDetailMapper.selectDictByType(map);
            SysDictDetail sysDictDetail = new SysDictDetail();
            // 数据字典主表的编码
            sysDictDetail.setSdmCode(paramMap.get("sdmCode").toString());
            // 编码
            sysDictDetail.setSddCode("CTRY"+ System.currentTimeMillis());
            // 名称
            sysDictDetail.setSddName(paramMap.get("sddName").toString());
            sysDictDetail.setIsEnable(1);
            sysDictDetail.setStatus(1);
            // 排序
            sysDictDetail.setSddSeq(sysDictDetails.size() + 1);
            // 备注
            sysDictDetail.setSddRemark(paramMap.get("sddRemark").toString());
            sysDictDetailMapper.insertSelective(sysDictDetail);
            transactionManager.commit(status);
            return 1;
        }catch (Exception e){
            transactionManager.rollback(status);
            return 0;
        }
    }

    @Override
    @Transactional
    public int update(Map<String, Object> paramMap) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("修改操作");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try{
            SysDictDetail sysDictDetail = sysDictDetailMapper.selectByPrimaryKey(Integer.valueOf(paramMap.get("id").toString()));
            // 名称
            sysDictDetail.setSddName(paramMap.get("sddName").toString());
            // 备注
            sysDictDetail.setSddRemark(paramMap.get("sddRemark").toString());
            int i = sysDictDetailMapper.updateByPrimaryKey(sysDictDetail);
            transactionManager.commit(status);
            return i;
        }catch (Exception e){
            transactionManager.rollback(status);
            return -1;
        }

    }

    @Override
    @Transactional
    public int delete(Integer id) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("删除操作");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try{
            Map<String, Object> selectMap = new HashMap<>();
            selectMap.put("countryId",id);
            int resultInt = transportationRouteMapper.countSelectAll(selectMap);
            if(resultInt > 0){
                return -2;
            }
            SysDictDetail sysDictDetail = sysDictDetailMapper.selectByPrimaryKey(id);
            sysDictDetail.setIsEnable(0);
            sysDictDetail.setStatus(0);
            int i = sysDictDetailMapper.updateByPrimaryKey(sysDictDetail);
            transactionManager.commit(status);
            return i;
        }catch (Exception e){
            transactionManager.rollback(status);
            return -1;
        }
    }

    /**
     *
     * @param sysDictDetail
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> saveDict(SysDictDetail sysDictDetail, boolean isUpdate) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_NOT_LOGIN), HttpStatus.OK);
        }


        if(isUpdate){
            // 查询字典是否重复;根据字典类型和字典名称匹配
            int count = sysDictDetailMapper.selectDictRepeat(sysDictDetail);
            if(count > 0){
                return new ResponseEntity<>(ResultModel.error(ResultStatus.THIS_DICT_NAME_REPEAT_ERROR), HttpStatus.OK);
            }
            count = sysDictDetailMapper.selectDictRemarkRepeat(sysDictDetail);
            if(count > 0){
                return new ResponseEntity<>(ResultModel.error(ResultStatus.THIS_DICT_REMARKS_REPEAT_ERROR), HttpStatus.OK);
            }
            sysDictDetail.setLastUpdateId(user.getId());
            sysDictDetail.setLastUpdateTime(new Date());
            sysDictDetailMapper.updateByPrimaryKeySelective(sysDictDetail);
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }else {
            // 查询字典是否重复;根据字典类型和字典名称匹配
            int count = sysDictDetailMapper.selectDictRepeat(sysDictDetail);
            if(count > 0){
                return new ResponseEntity<>(ResultModel.error(ResultStatus.THIS_DICT_REPEAT_ERROR), HttpStatus.OK);
            }
            count = sysDictDetailMapper.selectDictRemarkRepeat(sysDictDetail);
            if(count > 0){
                return new ResponseEntity<>(ResultModel.error(ResultStatus.THIS_DICT_REMARKS_REPEAT_ERROR), HttpStatus.OK);
            }
            sysDictDetail.setSddCode(sysDictDetail.getSddCode() + System.currentTimeMillis());
            sysDictDetail.setIsEnable(1);
            sysDictDetail.setStatus(1);
            sysDictDetail.setCreateId(user.getId());
            sysDictDetail.setCreateTime(new Date());
            sysDictDetailMapper.insertSelective(sysDictDetail);
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<PageResultModel> getDictList(DictReqVo dictReqVo) {
        PageData pageData = PageHelp.editPage(dictReqVo.getPageNumber().toString(), dictReqVo.getPageSize().toString());
        int count = sysDictDetailMapper.selectCount(dictReqVo);
        pageData.setTotal(count);
        if(count > 0){
            dictReqVo.setPageNumber(pageData.getPageNumber());
            List<SysDictDetail> sysDictDetailList = sysDictDetailMapper.selectDictList(dictReqVo);
            return new ResponseEntity<>(PageResultModel.ok(sysDictDetailList, pageData), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getDictDetail(int id) {
        SysDictDetail sysDictDetail = sysDictDetailMapper.selectByPrimaryKey(id);
        return new ResponseEntity<>(ResultModel.ok(sysDictDetail), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getCurrency() {
        List<SysDictDetail> sysDictDetailList = sysDictDetailMapper.selectDictBySdmCode("currency");
        return new ResponseEntity<>(ResultModel.ok(sysDictDetailList), HttpStatus.OK);
    }

    /**
     * 获取待办类型
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> getBacklogType() {
        List<BacklogTypeVo> backlogTypes = sysDictDetailMapper.getBacklogType();
        return new ResponseEntity<>(ResultModel.ok(backlogTypes), HttpStatus.OK);
    }

    /**
     * 新增待办类型
     * @param sysDictDetail
     * @return
     */
    @Override
    public ResponseEntity<ResultModel> addBacklogType(SysDictDetail sysDictDetail) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_NOT_LOGIN), HttpStatus.OK);
        }
        // 查询字典是否重复
        int count = sysDictDetailMapper.selectDictRepeat(sysDictDetail);
        if(count > 0){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.THIS_DICT_REPEAT_ERROR), HttpStatus.OK);
        }
        int backlogTypeCount = sysDictDetailMapper.getBacklogTypeCount(); // 查询数据库中已有的待办类型(包括已删除的)
        sysDictDetail.setAlternateField(String.valueOf(backlogTypeCount+1));
        sysDictDetail.setSddCode(sysDictDetail.getSddCode() + System.currentTimeMillis());
        sysDictDetail.setIsEnable(1);
        sysDictDetail.setStatus(1);
        sysDictDetail.setCreateId(user.getId());
        sysDictDetail.setCreateTime(new Date());
        sysDictDetailMapper.insertSelective(sysDictDetail);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getDictListBySdmCode(String sdmCode) {
        List<Map<String, Object>> dictList = sysDictDetailMapper.getDictListBySdmCode(sdmCode);
        return new ResponseEntity<>(ResultModel.ok(dictList), HttpStatus.OK);
    }
}
