package com.example.warehouse.vo.storage;

import lombok.Data;

import java.util.List;

@Data
public class StorageLocationsRespVo {

    private String storageArea;

    private Integer storageRow;

    private List<String> storageRows;

}
