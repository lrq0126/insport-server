package com.example.warehouse.vo.customer;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

/**
 * 客户推广明细查询条件实体
 */
@Data
public class PopularizeDetailReqVo extends PageVo {

    private Integer customerId;

}
