package com.example.warehouse.mapper.shelves;

import com.example.warehouse.entity.shelves.ShelvesArea;
import com.example.warehouse.entity.shelves.ShelvesGoodsCapacity;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.shelves.ShelvesReqVo;
import com.example.warehouse.vo.shelves.ShelvesRespVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 仓库货架-区(ShelvesArea)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/7/25 16:1
*/
@Repository
public interface ShelvesAreaMapper extends MyBatisBaseDao<ShelvesArea, Integer> {

    @Override
    int insert(ShelvesArea record);

    @Override
    int insertSelective(ShelvesArea record);

    @Override
    ShelvesArea selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(ShelvesArea record);

    @Override
    int updateByPrimaryKey(ShelvesArea record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

    int getShelvesAreaCount(ShelvesReqVo shelvesReqVo);

    List<ShelvesRespVo> getShelvesAreaList(ShelvesReqVo shelvesReqVo);

    // 获取货物区
    List<ShelvesRespVo> getShelvesAreaDropdownList(@Param("commercialAreaId") Integer commercialAreaId);

    int selectShelvesAreaGoodsSum(Integer id);

    int selectRepetitionArea(ShelvesArea shelvesArea);

    ShelvesArea selectArea(String shelvesArea);

    /**
     * 查询异常货物区
     * @return
     */
    ShelvesGoodsCapacity selectAbnormalArea();

    /**
     * 查询大货区
     * @return
     */
    ShelvesGoodsCapacity selectBigArea();


}