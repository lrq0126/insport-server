package com.example.warehouse.service.shelves;

import com.example.warehouse.entity.shelves.ShelvesArea;
import com.example.warehouse.entity.shelves.ShelvesRow;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.shelves.ShelvesReqVo;
import com.example.warehouse.vo.shelves.ShelvesRespVo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ShelvesAreaService {

    ResponseEntity<PageResultModel> getShelvesAreaList(ShelvesReqVo shelvesReqVo);
    ResponseEntity<ResultModel> getShelvesAreaDropdownList();

    ResponseEntity<ResultModel> saveShelves(ShelvesRespVo shelves);

    ResponseEntity<ResultModel> saveShelvesRow(ShelvesRow shelvesRow);

    ResponseEntity<ResultModel> saveShelvesRowList(String shelvesRowList);

    ResponseEntity<ResultModel> deleteShelvesArea(int shelvesAreaId);

    ResponseEntity<ResultModel> deleteShelvesRow(int shelvesRowId);

    ResponseEntity<ResultModel> getShelvesArea(int shelvesAreaId);

    ResponseEntity<ResultModel> getShelvesRowList(int shelvesAreaId);

    ResponseEntity<ResultModel> getShelvesRow(int shelvesRowId);

    ResponseEntity<ResultModel> codePrint(String type, int id);

    /**
     * 更新最优货架信息
     */
    void updateOptimalShelves();

    String generateShelvesAreaRowSQL(String shelvesArea, Integer rowNumber, String sortingExport);

    void uploadGoodsShelvesRowData();

    ResponseEntity<ResultModel> beAlreadyFull(String shelvesArea, String shelvesRow);
}
