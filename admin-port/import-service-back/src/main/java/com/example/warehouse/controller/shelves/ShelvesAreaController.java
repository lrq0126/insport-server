package com.example.warehouse.controller.shelves;

import com.example.warehouse.entity.shelves.ShelvesArea;
import com.example.warehouse.entity.shelves.ShelvesRow;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.shelves.ShelvesAreaService;
import com.example.warehouse.vo.shelves.ShelvesReqVo;
import com.example.warehouse.vo.shelves.ShelvesRespVo;
import com.example.warehouse.vo.storage.StorageCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shelvesArea")
public class ShelvesAreaController {

    @Autowired
    private ShelvesAreaService shelvesAreaService;

    @PostMapping("getShelvesAreaList")
    public ResponseEntity<PageResultModel> getShelvesAreaList(ShelvesReqVo shelvesReqVo){
        return shelvesAreaService.getShelvesAreaList(shelvesReqVo);
    }

    @PostMapping("saveShelves")
    public ResponseEntity<ResultModel> saveShelves(ShelvesRespVo shelvesRespVo){
        return shelvesAreaService.saveShelves(shelvesRespVo);
    }

    @PostMapping("saveShelvesRow")
    public ResponseEntity<ResultModel> saveShelvesRow(ShelvesRow shelvesRow){
        return shelvesAreaService.saveShelvesRow(shelvesRow);
    }

    @PostMapping("saveShelvesRowList")
    public ResponseEntity<ResultModel> saveShelvesRowList(String shelvesRows){
        return shelvesAreaService.saveShelvesRowList(shelvesRows);
    }

    @PostMapping("deleteShelvesArea")
    public ResponseEntity<ResultModel> deleteShelvesArea(int shelvesAreaId){
        return shelvesAreaService.deleteShelvesArea(shelvesAreaId);
    }

    @PostMapping("deleteShelvesRow")
    public ResponseEntity<ResultModel> deleteShelvesRow(int shelvesRowId){
        return shelvesAreaService.deleteShelvesRow(shelvesRowId);
    }

    @PostMapping("getShelvesArea")
    public ResponseEntity<ResultModel> getShelvesArea(int shelvesAreaId){
        return shelvesAreaService.getShelvesArea(shelvesAreaId);
    }

    @PostMapping("getShelvesRowList")
    public ResponseEntity<ResultModel> getShelvesRowList(int shelvesAreaId){
        return shelvesAreaService.getShelvesRowList(shelvesAreaId);
    }

    @PostMapping("getShelvesRow")
    public ResponseEntity<ResultModel> getShelvesRow(int shelvesRowId){
        return shelvesAreaService.getShelvesRow(shelvesRowId);
    }

    /**
     * ????????????
     * @param type  ???????????? area???????????????????????? row?????????????????????
     * @param id    ????????????ID
     * @return
     */
    @PostMapping(value = "/codePrint")
    public ResponseEntity<ResultModel> codePrint(String type, int id){
        return shelvesAreaService.codePrint(type, id);
    }

    /**
     * ???????????????????????????
     */
    @GetMapping("/uploadGoodsShelvesRowData")
    public void uploadGoodsShelvesRowData(){
        shelvesAreaService.uploadGoodsShelvesRowData();
    }

    /**
     * ??????????????????
     * @param shelvesArea   ?????????
     * @param shelvesRow    ?????????
     * @return
     */
    @PostMapping("/beAlreadyFull")
    public ResponseEntity<ResultModel> beAlreadyFull(String shelvesArea, String shelvesRow){
        return shelvesAreaService.beAlreadyFull(shelvesArea, shelvesRow);
    }

    /**
     * ????????????SQL??????
     * @param shelvesArea ?????????
     * @param rowNumber   ????????????
     * @param sortingExport ????????????
     * @return
     */
    @GetMapping("/generateShelvesAreaRowSQL")
    @ResponseBody
    public String generateShelvesAreaRowSQL(@RequestParam("shelvesArea") String shelvesArea,
                                            @RequestParam("rowNumber") Integer rowNumber,
                                            @RequestParam("sortingExport") String sortingExport){
        return shelvesAreaService.generateShelvesAreaRowSQL(shelvesArea, rowNumber, sortingExport);
    }
}
