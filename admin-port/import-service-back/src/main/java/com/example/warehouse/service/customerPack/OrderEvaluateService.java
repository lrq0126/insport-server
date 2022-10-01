package com.example.warehouse.service.customerPack;

import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.packVo.OrderEvaluateReqVo;
import org.springframework.http.ResponseEntity;

public interface OrderEvaluateService {

    ResponseEntity<PageResultModel> getEvaluateList(OrderEvaluateReqVo orderEvaluateReqVo);

    ResponseEntity<ResultModel> exportEvaluate(OrderEvaluateReqVo orderEvaluateReqVo);

    ResponseEntity<ResultModel> payReturn(String orderMessage);

}
