package com.example.warehouse.vo.goods;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

@Data
public class ShelvesReqVo extends PageVo {

    private String deliveryOrderNo;

    private String storageArea;

    private String storageRow;

}
