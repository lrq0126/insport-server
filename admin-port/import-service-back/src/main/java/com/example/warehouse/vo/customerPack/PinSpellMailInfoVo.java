package com.example.warehouse.vo.customerPack;

import com.example.warehouse.entity.CustomerPack;
import com.example.warehouse.entity.TransportationRoute;
import com.example.warehouse.entity.customerPack.PinSpellMail;
import lombok.Data;

import java.util.List;

@Data
public class PinSpellMailInfoVo extends PinSpellMail {


    private List<CustomerPackVo> customerPacks;

    private TransportationRoute route;
}
