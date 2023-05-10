package com.example.warehouse.vo.sys;

import com.example.warehouse.entity.SysDictDetail;
import lombok.Data;

@Data
public class SysDictCountryRespVo {

    private Integer id;

    private String sddCode;
    private String sddName;
    private Integer sddSeq;

    private Integer currencyId;
    private String currency;
    private Double exchangeRate;
    private String sddRemark;

    private SysDictDetail currencyData;

}
