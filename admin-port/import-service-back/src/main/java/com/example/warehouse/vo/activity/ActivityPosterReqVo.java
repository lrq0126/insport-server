package com.example.warehouse.vo.activity;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

@Data
public class ActivityPosterReqVo extends PageVo {

    private String posterName;
    private String posterType;
    private Integer isEnable;

}
