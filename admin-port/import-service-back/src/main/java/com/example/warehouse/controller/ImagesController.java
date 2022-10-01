package com.example.warehouse.controller;

import com.example.warehouse.entity.image.ImageType;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images")
public class ImagesController {

    @Autowired
    private ImagesService imagesService;

    @GetMapping("getGoodsPicture")
    @ResponseBody
    public ResponseEntity<ResultModel> getGoodsPicture(String deliveryOrderNo){
        return imagesService.getPicture(ImageType.GoodsInWarePicture.toString(), deliveryOrderNo);
    }

    @GetMapping("getOrderPicture")
    @ResponseBody
    public ResponseEntity<ResultModel> getOrderPicture(String orderNumber){
        return imagesService.getPicture(ImageType.CustomerPackPicture.toString(), orderNumber);
    }

}
