package com.dwgj.mlxydedicatedline.vo.sys;

import com.dwgj.mlxydedicatedline.entity.sys.CommercialArea;
import lombok.Data;

import java.util.List;

@Data
public class CountryCommercialAreaRespVo {
    private String country;

    private List<CommercialArea> commercialArea;

}
