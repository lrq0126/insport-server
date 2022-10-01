package com.example.warehouse.service.sys;

import com.example.warehouse.entity.sys.CommercialArea;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.sys.CommercialAreaReqVo;
import org.springframework.http.ResponseEntity;

public interface CommercialAreaService {

    ResponseEntity<PageResultModel> getCommercialAreaList(CommercialAreaReqVo commercialAreaReqVo);

    ResponseEntity<ResultModel> getCommercialAreaInfo(int id);

    ResponseEntity<ResultModel> saveCommercialArea(CommercialArea commercialArea);

    ResponseEntity<ResultModel> deleteCommercialArea(int id);

    ResponseEntity<ResultModel> enableCommercialArea(int id, int isEnable);
}
