package com.example.warehouse.service.insurance;

import com.alibaba.fastjson.JSONArray;
import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.common.SequenceCode;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.insurance.InsurancePrice;
import com.example.warehouse.mapper.insurance.InsuranceMapper;
import com.example.warehouse.mapper.insurance.InsurancePriceMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.insurance.InsuranceEntityVo;
import com.example.warehouse.vo.insurance.InsuranceReqVo;
import com.example.warehouse.vo.insurance.InsuranceRespVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.warehouse.enums.ResultStatus.USER_NOT_COMMERCIAL_AREA;
import static com.example.warehouse.enums.ResultStatus.USER_NOT_LOGIN;

@Service
public class InsuranceServiceImpl implements InsuranceService {

    @Autowired
    private InsuranceMapper insuranceMapper;
    @Autowired
    private InsurancePriceMapper insurancePriceMapper;

    @Override
    public ResponseEntity<PageResultModel> getInsuranceList(InsuranceReqVo insuranceReqVo) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_LOGIN, null), HttpStatus.OK);
        }
        if(user.getCommercialAreaId() == null){
            return new ResponseEntity<>(PageResultModel.error(USER_NOT_COMMERCIAL_AREA, null), HttpStatus.OK);
        }
        // 当用户所在的区域是中国总部，则不限制查看
        if(user.getCommercialAreaId() != 1){
//            map.put("commercialAreaId", user.getCommercialAreaId());
            insuranceReqVo.setCommercialAreaId(user.getCommercialAreaId());
        }
        // 条件查询总数，用于页码计算
        int i = insuranceMapper.selectCount(insuranceReqVo);
        PageData pageData = PageHelp.initPage(insuranceReqVo.getPageNumber(), insuranceReqVo.getPageSize());
        pageData.setTotal(i);
        if(i > 0){
            insuranceReqVo.setPageNumber(pageData.getPageNumber());
            insuranceReqVo.setPageSize(pageData.getPageSize());
            List<InsuranceRespVo> insuranceRespVoList = insuranceMapper.selectInsurance(insuranceReqVo);
            return new ResponseEntity<>(PageResultModel.ok(insuranceRespVoList, pageData), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getInsuranceDetail(int id) {
        InsuranceRespVo insuranceRespVo = insuranceMapper.selectInsuranceById(id);
        return new ResponseEntity<>(ResultModel.ok(insuranceRespVo), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> save(InsuranceEntityVo insuranceEntityVo, boolean isEdit) {

        // 获取当前登录人
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(USER_NOT_LOGIN), HttpStatus.OK);
        }

        if(isEdit){
            insuranceEntityVo.setUpdateId(user.getLastUpdateId());
            insuranceEntityVo.setUpdateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
            insuranceMapper.updateByPrimaryKeySelective(insuranceEntityVo);

            insurancePriceMapper.deleteByInsuranceId(insuranceEntityVo.getId());
        }else {
            // 查询所有保险数量
            int count = insuranceMapper.selectAllNumber();
            // 生成保险编号 BX-日期+序号
            String insuranceNo = SequenceCode.insuranceNo(count+1);
            insuranceEntityVo.setInsuranceNo(insuranceNo);

            insuranceEntityVo.setStatus(1);
            insuranceEntityVo.setVersion(1);
            insuranceEntityVo.setCreateId(user.getId());
            insuranceEntityVo.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
            insuranceMapper.insertSelective(insuranceEntityVo);
        }

        List<InsurancePrice> insurancePrices = JSONArray.parseArray(insuranceEntityVo.getInsurancePrices(), InsurancePrice.class);
        for (InsurancePrice insurancePrice : insurancePrices) {
            insurancePrice.setInsuranceId(insuranceEntityVo.getId());
            insurancePrice.setStatus(1);
            insurancePrice.setVersion(1);
            insurancePrice.setCreateId(user.getId());
            insurancePrice.setCreateTime(DateUtil.getDateFormate(new Date(), DateUtil.DEFAULT_TIMESTAMP_FORMAT));
        }
        insurancePriceMapper.insertList(insurancePrices);

        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> deleteInsurance(int id) {
        insuranceMapper.deleteByPrimaryKey(id);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getInsuranceByOrderNumber(String orderNumber) {
        InsuranceRespVo insuranceRespVo = insuranceMapper.selectInsuranceByOrderNumber(orderNumber);
        return new ResponseEntity<>(ResultModel.ok(insuranceRespVo), HttpStatus.OK);
    }
}
