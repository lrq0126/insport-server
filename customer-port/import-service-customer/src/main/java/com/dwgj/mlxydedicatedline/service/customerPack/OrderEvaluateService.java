package com.dwgj.mlxydedicatedline.service.customerPack;

import com.dwgj.mlxydedicatedline.entity.customerPack.OrderEvaluate;
import com.dwgj.mlxydedicatedline.resultType.PageResultModel;
import com.dwgj.mlxydedicatedline.resultType.ResultModel;
import com.dwgj.mlxydedicatedline.vo.orderEvaluate.OrderEvaluateReqVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface OrderEvaluateService {

    ResponseEntity<PageResultModel> getEvaluateList(OrderEvaluateReqVo orderEvaluateReqVo);

    ResponseEntity<ResultModel> saveEvaluate(OrderEvaluate orderEvaluate, MultipartFile[] multipartFiles);

    ResponseEntity<ResultModel> getEvaluateByPackId(int packId);
}
