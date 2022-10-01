package com.example.warehouse.vo.backlogVo;

import com.example.warehouse.entity.backlog.Backlog;
import com.example.warehouse.entity.backlog.BacklogDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BacklogRespVo extends Backlog {

    @ApiModelProperty(value = "创建人")
    private String createName;

    @ApiModelProperty(value = "创建人")
    private String backlogTypeName;

    @ApiModelProperty(value = "工作内容集合")
    private List<BacklogDetail> backlogDetails;

}
