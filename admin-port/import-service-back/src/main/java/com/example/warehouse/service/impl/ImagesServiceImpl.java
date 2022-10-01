package com.example.warehouse.service.impl;

import com.example.warehouse.entity.image.ImageType;
import com.example.warehouse.entity.image.Images;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.mapper.image.ImagesMapper;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.warehouse.enums.ResultStatus.OTHER_SYSTEM_ERROR;

@Service("ImagesService")
public class ImagesServiceImpl implements ImagesService {

    @Autowired
    private ImagesMapper imagesMapper;

    @Override
    public ResponseEntity<ResultModel> getPicture(String imagesType, String contentId) {
        Images images = new Images();
        images.setContentId(contentId);
        images.setPicType(imagesType);
        List<Images> imagesList;
        if(ImageType.CustomerPackPicture.toString().equals(imagesType)){
            imagesList = imagesMapper.getCustomerPackImages(images);
        }else{
            imagesList = imagesMapper.getImagesByContentId(images);
        }

        if(!CollectionUtils.isEmpty(imagesList)){

            List<Map<String, Object>> resultList = new ArrayList<>();
            Map<String, Object> resultMap;
            List<String> picList = imagesList.stream().map(Images::getPicUrl).collect(Collectors.toList());
            for (Images image : imagesList) {
                resultMap = new HashMap<>();
                picList.add(image.getPicUrl());
                resultMap.put("url", image.getPicUrl());
                resultMap.put("srcList", picList);
                resultList.add(resultMap);
            }
            return new ResponseEntity<>(ResultModel.ok(resultList), HttpStatus.OK);
        }else {
            OTHER_SYSTEM_ERROR.setMessage("暂无此订单图片");
            return new ResponseEntity<>(ResultModel.error(OTHER_SYSTEM_ERROR), HttpStatus.OK);
        }
    }

}
