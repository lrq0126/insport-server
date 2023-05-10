package com.example.warehouse.vo.appDataService;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

@Data
public class Track17ReqVo extends PageVo {

    private String number;

    // 注册的起始时间
    private String register_time_from;
    // 注册的结束时间
    private String register_time_to;

    private String package_status;

    private Integer page_no;

    private String order_by = "RegisterTimeDesc";

}
