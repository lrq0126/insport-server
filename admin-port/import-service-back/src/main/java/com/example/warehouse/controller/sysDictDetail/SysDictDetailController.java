package com.example.warehouse.controller.sysDictDetail;

import com.example.warehouse.entity.SysDictDetail;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.SysDictDetail.SysDictDetailService;
import com.example.warehouse.vo.sys.SysDictReqVo;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.warehouse.enums.ResultStatus.ERROR;
import static com.example.warehouse.enums.ResultStatus.OPERATION_ERROR;


/**
 * @author lrq
 * 2019年11月14日18:44:36
 */
@RestController
@RequestMapping("/sysDictDetail")
public class SysDictDetailController {

    @Autowired
    private SysDictDetailService sysDictDetailService;

    @GetMapping("/find/routeType/list")
    public ResponseEntity<ResultModel> findOfTrans(){
        List<SysDictDetail> sysDictDetails =  sysDictDetailService.selectOfTrans();
        Map<String, Object> result = new HashMap<>(12);
        result.put("routeType",sysDictDetails);
        return new ResponseEntity<>(ResultModel.ok(result),HttpStatus.OK);
    }

    @GetMapping("/find/weightRule/list")
    public ResponseEntity<ResultModel> findOfWeightRule(){
        List<SysDictDetail> sysDictDetails =  sysDictDetailService.selectOfWeightRule();
        Map<String, Object> result = new HashMap<>(12);
        result.put("weightRule",sysDictDetails);
        return new ResponseEntity<>(ResultModel.ok(result),HttpStatus.OK);
    }

    /**
     * 获取 国家列表
     * @return
     */
    @GetMapping("/find/country/list")
    public ResponseEntity<ResultModel> findCountry(@RequestParam Map<String, Object> paramMap){
        paramMap.put("sdmCode","country");
        List<SysDictDetail> sysDictDetails =  sysDictDetailService.selectCountry(paramMap);
        Map<String, Object> result = new HashMap<>(12);
        result.put("country",sysDictDetails);
        return new ResponseEntity<>(ResultModel.ok(result),HttpStatus.OK);
    }

    /**
     * 获取 国家 分页列表
     * @return
     */
    @GetMapping("/getCountriesPageList")
    public ResponseEntity<PageResultModel> getCountriesPageList(SysDictReqVo sysDictReqVo){
        return sysDictDetailService.getCountriesPageList(sysDictReqVo);
    }

    /**
     * 创建 国家
     * @param paramMap
     * @return
     */
    @RequiresPermissions("country:create")
    @PostMapping("/create/country")
    public ResponseEntity<ResultModel> createCountry(@RequestParam Map<String, Object> paramMap){
        paramMap.put("sdmCode","country");
        int i =  sysDictDetailService.create(paramMap);
        if(i > 0){
            return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
    }

    /**
     * 修改 国家信息
     * @param sysDictDetail
     * @return
     */
    @RequiresPermissions("country:update")
    @PostMapping("/update/country")
    public ResponseEntity<ResultModel> updateCountry(SysDictDetail sysDictDetail){
        return sysDictDetailService.updateCountry(sysDictDetail);
    }

    /**
     * 获取 国家基础信息
     * @param id
     * @return
     */
    @PostMapping("/getCountryInfo")
    public ResponseEntity<ResultModel> getCountryInfo(Integer id){
        return sysDictDetailService.getCountryInfo(id);
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @RequiresPermissions("country:delete")
    @PostMapping("/delete/country")
    public ResponseEntity<ResultModel> deleteCountry(@RequestParam Integer id){
        int i =  sysDictDetailService.delete(id);
        if(i > 0){
            return new ResponseEntity<>(ResultModel.ok(),HttpStatus.OK);
        }
        if(i == -2){
            return new ResponseEntity<>(ResultModel.error(OPERATION_ERROR),HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultModel.error(ERROR),HttpStatus.OK);
    }


    @PostMapping("getDictList")
    public ResponseEntity<PageResultModel> getDictList(SysDictReqVo sysDictReqVo){
        return sysDictDetailService.getDictList(sysDictReqVo);
    }

    @PostMapping("/getDictDetail")
    public ResponseEntity<ResultModel> getDictDetail(int id){
        return sysDictDetailService.getDictDetail(id);
    }

    @PostMapping("/createDict")
    public ResponseEntity<ResultModel> createDict(SysDictDetail sysDictDetail){
        return sysDictDetailService.saveDict(sysDictDetail, false);
    }

    @PostMapping("/editDict")
    public ResponseEntity<ResultModel> editDict(SysDictDetail sysDictDetail){
        return sysDictDetailService.saveDict(sysDictDetail, true);
    }

    /**
     * 获取货币类型
     * @return
     */
    @GetMapping("/getCurrency")
    public ResponseEntity<ResultModel> getCurrency(){
        return sysDictDetailService.getCurrency();
    }

    /**
     * 获取待办类型
     * @return
     */
    @GetMapping("/getBacklogType")
    public ResponseEntity<ResultModel> getBacklogType(){
        return sysDictDetailService.getBacklogType();
    }
    /**
     * 获取待办类型
     * @return
     */
    @PostMapping("/addBacklogType")
    public ResponseEntity<ResultModel> addBacklogType(SysDictDetail sysDictDetail){
        return sysDictDetailService.addBacklogType(sysDictDetail);
    }

    /**
     * 获取相关类型的字典下拉列表
     * @return
     */
    @GetMapping("/getDictListBySdmCode")
    public ResponseEntity<ResultModel> getDictListBySdmCode(@Param("sdmCode") String sdmCode){
        return sysDictDetailService.getDictListBySdmCode(sdmCode);
    }

}
