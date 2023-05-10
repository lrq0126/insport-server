package com.example.warehouse.service.SysDictDetail.impl;

import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.SysDictDetail;
import com.example.warehouse.entity.User;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.mapper.SysDictDetailMapper;
import com.example.warehouse.mapper.transportationRoute.TransportationRouteMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.SysDictDetail.SysDictDetailService;
import com.example.warehouse.vo.backlogVo.BacklogTypeVo;
import com.example.warehouse.vo.sys.SysDictCountryRespVo;
import com.example.warehouse.vo.sys.SysDictReqVo;
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

import java.util.*;

import static com.example.warehouse.enums.ResultStatus.ERROR;
import static com.example.warehouse.enums.ResultStatus.THIS_DICT_NAME_REPEAT_ERROR;

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
            // 指向 汇率的 数据字典ID
            sysDictDetail.setAlternateField(paramMap.get("currency").toString());
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
    public ResponseEntity<ResultModel> updateCountry(SysDictDetail sysDictDetail) {
        try{
            // 检验 重名
            int repeatCount = sysDictDetailMapper.checkSddName(sysDictDetail.getId(), sysDictDetail.getSddName());
            if(repeatCount > 0){
                return new ResponseEntity<>(ResultModel.error(THIS_DICT_NAME_REPEAT_ERROR),HttpStatus.OK);
            }
            int i = sysDictDetailMapper.updateByPrimaryKeySelective(sysDictDetail);
            if(i > 0){
                return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
            }
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
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
                return new ResponseEntity<>(ResultModel.error(THIS_DICT_NAME_REPEAT_ERROR), HttpStatus.OK);
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
    public ResponseEntity<PageResultModel> getDictList(SysDictReqVo sysDictReqVo) {
        PageData pageData = PageHelp.editPage(sysDictReqVo.getPageNumber().toString(), sysDictReqVo.getPageSize().toString());
        int count = sysDictDetailMapper.selectCount(sysDictReqVo);
        pageData.setTotal(count);
        if(count > 0){
            sysDictReqVo.setPageNumber(pageData.getPageNumber());
            List<SysDictDetail> sysDictDetailList = sysDictDetailMapper.selectDictList(sysDictReqVo);
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

    @Override
    public ResponseEntity<PageResultModel> getCountriesPageList(SysDictReqVo sysDictReqVo) {
        PageData pageData = PageHelp.editPage(sysDictReqVo);
        int total = sysDictDetailMapper.getCountryCount(sysDictReqVo);
        pageData.setTotal(total);
        List<SysDictCountryRespVo> sysDictCountryRespVoList = new ArrayList<>();
        if(total > 0){
            sysDictReqVo.setPageNumber(pageData.getPageNumber());
            sysDictCountryRespVoList = sysDictDetailMapper.getCountryDictPageList(sysDictReqVo);
            for (SysDictCountryRespVo country : sysDictCountryRespVoList) {
                if(country.getCurrencyData() != null){
                    country.setCurrencyId(country.getCurrencyData().getId());
                    country.setCurrency(country.getCurrencyData().getSddName());
                    country.setExchangeRate(Double.valueOf(country.getCurrencyData().getAlternateField()));
                }
            }
            return new ResponseEntity<>(PageResultModel.ok(sysDictCountryRespVoList, pageData), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(sysDictCountryRespVoList, pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getCountryInfo(Integer id) {
        SysDictCountryRespVo countryInfo = sysDictDetailMapper.getDictCountryInfo(id);
        if(countryInfo.getCurrencyData() != null){
            countryInfo.setCurrencyId(countryInfo.getCurrencyData().getId());
            countryInfo.setCurrency(countryInfo.getCurrencyData().getSddName());
            countryInfo.setExchangeRate(Double.valueOf(countryInfo.getCurrencyData().getAlternateField()));
        }
        return new ResponseEntity<>(ResultModel.ok(countryInfo), HttpStatus.OK);
    }
}
