package com.example.warehouse.vo.shelves;

import com.example.warehouse.entity.shelves.ShelvesArea;
import com.example.warehouse.entity.shelves.ShelvesRow;
import lombok.Data;

import java.util.List;

@Data
public class ShelvesRespVo extends ShelvesArea {

    private Integer goodsSum;
    private String shelvesRows;

    private List<ShelvesRow> shelvesRowList;

}
