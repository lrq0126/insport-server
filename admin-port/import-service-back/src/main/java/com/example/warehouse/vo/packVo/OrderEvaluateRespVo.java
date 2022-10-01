package com.example.warehouse.vo.packVo;

import com.example.warehouse.entity.customerPack.OrderEvaluate;
import com.example.warehouse.entity.image.Images;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderEvaluateRespVo extends OrderEvaluate {

    private String loginName;

    private String customerNo;

    private String routeName;

    private String destination;

    private String orderTime;

    private String packTime;

    private String receiptTime;

    private List<Images> images;


}
