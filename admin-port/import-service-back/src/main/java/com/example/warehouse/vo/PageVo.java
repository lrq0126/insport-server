package com.example.warehouse.vo;

import lombok.Data;

@Data
public class PageVo {

    private Integer pageNumber;

    private Integer pageSize;

    private Integer total;

    private Integer commercialAreaId;

}
