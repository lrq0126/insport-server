package com.dwgj.mlxydedicatedline.vo.pack;

import com.dwgj.mlxydedicatedline.entity.Goods;
import com.dwgj.mlxydedicatedline.entity.customerPack.CustomerPackNumber;
import com.dwgj.mlxydedicatedline.vo.GoodsVo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author lrq
 * 2019年12月20日16:27:53
 */
@Data
public class CustomerPackNumberVo extends CustomerPackNumber implements Serializable {


    private List<Goods> goods;

    private static final long serialVersionUID = 1L;

}
