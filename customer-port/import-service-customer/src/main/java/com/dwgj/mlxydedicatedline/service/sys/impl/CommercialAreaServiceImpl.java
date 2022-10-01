package com.dwgj.mlxydedicatedline.service.sys.impl;

import com.dwgj.mlxydedicatedline.entity.Customer;
import com.dwgj.mlxydedicatedline.mapper.sys.CommercialAreaMapper;
import com.dwgj.mlxydedicatedline.mapper.sysDictDetail.SysDictDetailDao;
import com.dwgj.mlxydedicatedline.mapper.user.LoginTokenMapper;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.service.sys.CommercialAreaService;
import com.dwgj.mlxydedicatedline.vo.sys.CountryCommercialAreaRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.dwgj.mlxydedicatedline.enums.ResultStatus.NOT_LOGIN_ERROR;


@Service("CommercialAreaService")
public class CommercialAreaServiceImpl implements CommercialAreaService {

    @Autowired
    private CommercialAreaMapper commercialAreaMapper;
    @Autowired
    private SysDictDetailDao sysDictDetailDao;


    @Override
    public ResponseEntity<ResultModel> getCountryCommercialArea() {
        List<CountryCommercialAreaRespVo> countryCommercialAreaRespVoList = sysDictDetailDao.getCountryCommercialArea();
        return new ResponseEntity<>(ResultModel.ok(countryCommercialAreaRespVoList), HttpStatus.OK);
    }

}
