package com.example.warehouse.controller.sys;

import com.example.warehouse.entity.sys.CommercialArea;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.sys.CommercialAreaService;
import com.example.warehouse.vo.sys.CommercialAreaReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commercialArea")
public class CommercialAreaController {

    @Autowired
    private CommercialAreaService commercialAreaService;

    @PostMapping("/getCommercialAreaList")
    public ResponseEntity<PageResultModel> getCommercialAreaList(CommercialAreaReqVo commercialAreaReqVo){
        return commercialAreaService.getCommercialAreaList(commercialAreaReqVo);
    }

    @PostMapping("/getCommercialAreaInfo")
    public ResponseEntity<ResultModel> getCommercialAreaInfo(int id){
        return commercialAreaService.getCommercialAreaInfo(id);
    }

    @PostMapping("/saveCommercialArea")
    public ResponseEntity<ResultModel> saveCommercialArea(CommercialArea commercialArea){
        return commercialAreaService.saveCommercialArea(commercialArea);
    }

    @PostMapping("/deleteCommercialArea")
    public ResponseEntity<ResultModel> deleteCommercialArea(int id){
        return commercialAreaService.deleteCommercialArea(id);
    }

    @PostMapping("/enableCommercialArea")
    public ResponseEntity<ResultModel> enableCommercialArea(int id, int isEnable){
        return commercialAreaService.enableCommercialArea(id, isEnable);
    }

}
