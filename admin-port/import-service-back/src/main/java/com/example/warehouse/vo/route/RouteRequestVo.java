package com.example.warehouse.vo.route;

import com.example.warehouse.vo.PageVo;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@ApiOperation(value = "路线查询请求实体")
@Data
public class RouteRequestVo extends PageVo {

    private Integer countryId;

    private String country;

    private String routeName;

    private String englishName;

    private Integer routeType;

    private Integer transportType;

    private String originatingPlace;

    private String destination;


}
