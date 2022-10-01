package com.example.warehouse.vo.packVo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.List;

@ApiOperation(value = "子包裹数据")
@Data
public class PackSonVo {

    @ApiModelProperty(value = "id")
    private int id;

    @ApiModelProperty(value = "序号")
    private int sortNo;

    @ApiModelProperty(value = "长")
    private Double length;

    @ApiModelProperty(value = "宽")
    private Double width;

    @ApiModelProperty(value = "高")
    private Double height;

    @ApiModelProperty(value = "重量")
    private Double actualWeight;

    @ApiModelProperty(value = "体积")
    private Double actualVol;

    @ApiModelProperty(value = "货物id")
    private String goodsIds;

    private String sonOrderNum;

    private List<Integer> goodsIdList;

}
