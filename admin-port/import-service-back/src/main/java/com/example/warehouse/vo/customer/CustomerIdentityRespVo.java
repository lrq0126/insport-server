package com.example.warehouse.vo.customer;

import com.example.warehouse.entity.customer.CustomerIdentity;
import com.example.warehouse.entity.image.Images;
import lombok.Data;

import java.util.List;

@Data
public class CustomerIdentityRespVo extends CustomerIdentity {

    private String imageIds;

    private List<Images> images;
}
