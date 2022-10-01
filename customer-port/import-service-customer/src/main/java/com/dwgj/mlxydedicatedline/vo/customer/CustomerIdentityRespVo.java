package com.dwgj.mlxydedicatedline.vo.customer;

import com.dwgj.mlxydedicatedline.entity.customer.CustomerIdentity;
import com.dwgj.mlxydedicatedline.entity.image.Images;
import lombok.Data;

import java.util.List;

@Data
public class CustomerIdentityRespVo extends CustomerIdentity {

    private List<Images> images;
}
