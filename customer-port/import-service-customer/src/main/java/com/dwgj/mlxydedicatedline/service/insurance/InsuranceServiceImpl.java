package com.dwgj.mlxydedicatedline.service.insurance;

import com.dwgj.mlxydedicatedline.entity.TransportationRoute;
import com.dwgj.mlxydedicatedline.mapper.TransportationRouteMapper;
import com.dwgj.mlxydedicatedline.mapper.insurance.InsuranceMapper;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.vo.insurance.InsuranceRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class InsuranceServiceImpl implements InsuranceService {

    @Autowired
    private InsuranceMapper insuranceMapper;


    @Override
    public ResponseEntity<ResultModel> getInsuranceDetailById(int id) {
//        TransportationRoute route = transportationRouteMapper.selectByPrimaryKey(routeId);
        InsuranceRespVo insuranceRespVo = insuranceMapper.selectInsuranceById(id);
        return new ResponseEntity<>(ResultModel.ok(insuranceRespVo), HttpStatus.OK);
    }

}
