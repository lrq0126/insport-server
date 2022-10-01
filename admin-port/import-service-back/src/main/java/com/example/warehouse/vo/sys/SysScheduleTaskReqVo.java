package com.example.warehouse.vo.sys;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

@Data
public class SysScheduleTaskReqVo extends PageVo {

    private String taskName;

    private Integer isEnable;

}
