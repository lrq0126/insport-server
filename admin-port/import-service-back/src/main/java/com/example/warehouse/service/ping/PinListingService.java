package com.example.warehouse.service.ping;

import com.example.warehouse.model.ResultModel;
import org.springframework.http.ResponseEntity;

public interface PinListingService {
    ResponseEntity<ResultModel> printPackTa(int pmoId);

    ResponseEntity<ResultModel> printAllGoods(int pId);

    ResponseEntity<ResultModel> printPingMain(int pid);
}
