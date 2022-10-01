package com.example.warehouse.vo.activity;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

@Data
public class ActivityRewardReqVo extends PageVo {
    private String activityName;
    private Integer activityTimeType;
    private Integer isEnable;
}
