package com.example.warehouse.vo.customerPack;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

@Data
public class PinSpellMailReqVo extends PageVo {
    private String orderName;
    private String orderNumber;
    private Integer pinType;
    private String routeName;
    private String country;
}
