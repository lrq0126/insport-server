package com.example.warehouse.vo.goods;

import lombok.Data;

@Data
public class OcrInWareSortingReqVo {

    private String systemName;

    private String loginName;

    private String deliveryOrderNo;

    private Double weight;

    private Double length;
    private Double width;
    private Double height;

    private String packType;

    private String message;
}
