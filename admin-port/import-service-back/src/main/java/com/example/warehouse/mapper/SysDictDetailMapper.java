package com.example.warehouse.mapper;

import com.example.warehouse.entity.SysDictDetail;
import com.example.warehouse.vo.backlogVo.BacklogTypeVo;
import com.example.warehouse.vo.sys.SysDictCountryRespVo;
import com.example.warehouse.vo.sys.SysDictReqVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * SysDictDetailMapper继承基类
 */
@Repository
public interface SysDictDetailMapper extends MyBatisBaseDao<SysDictDetail, Integer> {

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
     * 根据字典类型查
     * @return
     */
	List<SysDictDetail> selectDictByType(Map<String, Object> map);

    int selectCount(SysDictReqVo sysDictReqVo);

    List<SysDictDetail> selectDictList(SysDictReqVo sysDictReqVo);

    // 查询字典是否重复
    int selectDictRepeat(SysDictDetail sysDictDetail);

    int selectDictRemarkRepeat(SysDictDetail sysDictDetail);

    List<SysDictDetail> selectDictBySdmCode(String sdmCode);

    /**
     * 根据 字典编码 查询
     * @param sddCode
     * @return
     */
    SysDictDetail selectDictBySddCode(@Param("sddCode") String sddCode);

    List<BacklogTypeVo> getBacklogType();

    int getBacklogTypeCount();

    List<Map<String, Object>> getDictListBySdmCode(@Param("sdmCode") String sdmCode);

    // 查询 国家分页 列表接口
    int getCountryCount(SysDictReqVo sysDictReqVo);

    List<SysDictCountryRespVo> getCountryDictPageList(SysDictReqVo sysDictReqVo);

    SysDictDetail selectCountryExchangeRate(Integer id);

    int checkSddName(@Param("id") Integer id, @Param("sddName") String sddName);

    SysDictCountryRespVo getDictCountryInfo(Integer id);
    // 查询 国家分页 列表接口 end

    /**
     * 查询 当前国家使用的货币
     * @param countryId
     * @return
     */
    SysDictDetail selectCountryCurrency(Integer countryId);

}