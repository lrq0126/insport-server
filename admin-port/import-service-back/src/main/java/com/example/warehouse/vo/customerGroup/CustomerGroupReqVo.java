package com.example.warehouse.vo.customerGroup;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

@Data
public class CustomerGroupReqVo extends PageVo {

    private String customerName;

    private String loginName;

    private String groupName;

    private String createName;

}
