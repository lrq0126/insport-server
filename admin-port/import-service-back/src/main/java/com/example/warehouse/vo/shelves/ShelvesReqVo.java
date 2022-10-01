package com.example.warehouse.vo.shelves;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

@Data
public class ShelvesReqVo extends PageVo {

    private Integer goodsSum;

    private String shelvesType;



}
