package com.example.warehouse.service.customerPack;

import com.example.warehouse.entity.customerPack.PinSpellMail;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.customerPack.PinSpellMailReqVo;
import org.springframework.http.ResponseEntity;

public interface PinSpellMailService {
    ResponseEntity<ResultModel> savePinSpellMail(PinSpellMail pinSpellMail);

    ResponseEntity<PageResultModel> getPinSpellMailList(PinSpellMailReqVo pinSpellMailReqVo);

    ResponseEntity<ResultModel> getPinSpellMailInfo(int id);

    ResponseEntity<ResultModel> completeTeamComplete(int id, String customerPackIds);

    ResponseEntity<ResultModel> deliver(int id);

    ResponseEntity<ResultModel> arrival(int id);

    ResponseEntity<ResultModel> delete(int id);
}
