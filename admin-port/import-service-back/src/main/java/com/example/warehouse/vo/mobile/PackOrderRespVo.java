package com.example.warehouse.vo.mobile;

import com.example.warehouse.entity.CustomerPack;
import lombok.Data;

@Data
public class PackOrderRespVo extends CustomerPack {

    private String loginName;

    private String customerName;

    private String routeName;

}
