package com.example.warehouse.service.SysDictDetail;

import com.example.warehouse.entity.SysDictDetail;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.sys.DictReqVo;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * @author lrq
 */
public interface SysDictDetailService {

    /**
     * 查询路线类型
     * @return
     */
    List<SysDictDetail> selectOfTrans();

    /**
     * 查询进阶规则
     * @return
     */
    List<SysDictDetail> selectOfWeightRule();

    /**
     * 获取国家列表
     * @param map
     * @return
     */
    List<SysDictDetail> selectCountry(Map<String, Object> map);

    /**
     * 创建
     * @param paramMap
     * @return
     */
    int create(Map<String, Object> paramMap);

    /**
     * 更新
     * @param paramMap
     * @return
     */
    int update(Map<String,Object> paramMap);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(Integer id);

    ResponseEntity<ResultModel> saveDict(SysDictDetail sysDictDetail, boolean isUpdate);

    ResponseEntity<PageResultModel> getDictList(DictReqVo dictReqVo);

    ResponseEntity<ResultModel> getDictDetail(int id);

    ResponseEntity<ResultModel> getCurrency();

    ResponseEntity<ResultModel> getBacklogType();

    ResponseEntity<ResultModel> addBacklogType(SysDictDetail sysDictDetail);

    ResponseEntity<ResultModel> getDictListBySdmCode(String sdmCode);
}
