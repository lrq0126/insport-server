package com.dwgj.mlxydedicatedline.vo.customer;

import lombok.Data;

/**
 * 客户推广明细查询条件实体
 */
@Data
public class PopularizeDetailReqVo {
    private Integer pageNumber;
    private Integer pageSize;
    private Integer customerId;

}
