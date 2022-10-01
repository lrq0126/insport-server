package com.example.warehouse.mapper.sys;

import com.example.warehouse.DO.sys.SysStatisticalDo;
import com.example.warehouse.entity.sys.CommercialArea;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.sys.CommercialAreaReqVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 商户区域表(CommercialArea)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/9/20 11:10
*/
@Repository
public interface CommercialAreaMapper extends MyBatisBaseDao<CommercialArea, Integer> {

    @Override
    int insert(CommercialArea record);

    @Override
    int insertSelective(CommercialArea record);

    @Override
    CommercialArea selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(CommercialArea record);

    @Override
    int updateByPrimaryKey(CommercialArea record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    int getCommercialAreaCount(CommercialAreaReqVo commercialAreaReqVo);

    List<CommercialArea> getCommercialAreaList(CommercialAreaReqVo commercialAreaReqVo);

    void enableCommercialArea(@Param("id") int id, @Param("isEnable") int isEnable);

    List<Integer> getCommercialAreaIdList();

    /**
     * 查询昨日数据统计
     * @return
     */
    List<SysStatisticalDo> getSysStatisticalDoList();

}