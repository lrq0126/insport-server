package com.example.warehouse.service.customerGroup;

import com.example.warehouse.model.ResultModel;
import org.springframework.http.ResponseEntity;

public interface CustomerGroupLinkService {

    ResponseEntity<ResultModel> editGroupCustomer(int id, String customerIds, boolean isAdd);

    ResponseEntity<ResultModel> addGroupCustomerByRouteId(Integer groupId, String routeIds);
}
