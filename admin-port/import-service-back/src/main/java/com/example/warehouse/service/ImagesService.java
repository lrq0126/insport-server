package com.example.warehouse.service;

import com.example.warehouse.model.ResultModel;
import org.springframework.http.ResponseEntity;

public interface ImagesService {

    ResponseEntity<ResultModel> getPicture(String imagesType, String contentId);
}
