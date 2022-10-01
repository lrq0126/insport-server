package com.example.warehouse.mapper.shelves;

import com.example.warehouse.entity.shelves.ShelvesGoodsCapacity;
import com.example.warehouse.entity.shelves.ShelvesRow;
import com.example.warehouse.mapper.MyBatisBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 仓库货架-排(ShelvesRow)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/7/25 16:1
*/
@Repository
public interface ShelvesRowMapper extends MyBatisBaseDao<ShelvesRow, Integer> {

    @Override
    int insert(ShelvesRow record);

    @Override
    int insertSelective(ShelvesRow record);

    @Override
    ShelvesRow selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(ShelvesRow record);

    @Override
    int updateByPrimaryKey(ShelvesRow record);
	
	@Override
    int deleteByPrimaryKey(Integer id);
	
	int removeByPrimaryKey(Integer id);

	List<ShelvesRow> selectByParentId(Integer parentId);

    void insertList(@Param("shelvesRowList") List<ShelvesRow> shelvesRowList);

    void updateList(@Param("shelvesRowList") List<ShelvesRow> updateShelvesRows);

    int selectShelvesRowGoodsSum(Integer shelvesRowId);

    List<ShelvesGoodsCapacity> selectFreeShelvesRow();

    ShelvesGoodsCapacity selectGoodsShelvesRow(Integer goodsId);

    ShelvesRow selectByAreaRowId(Integer areaRowId);

    ShelvesRow selectAreaRow(@Param("storageArea") String storageArea,@Param("storageRow") String storageRow);

    void deleteByParentId(int shelvesAreaId);

    /**
     * 查看当前货架区有多少排
     * @param id
     * @param parentId
     * @param shelvesRow
     * @return
     */
    int selectCountShelvesAreaRowByRow(@Param("id") Integer id, @Param("parentId") Integer parentId,
                                       @Param("shelvesRow") String shelvesRow);

    /**
     * 货架设置为满货
     * @param id
     */
    void updateRowFull(Integer id);

    /**
     * 根据货物ID将货架更新为未满货架
     * @param goodsId
     */
    void updateRowNotFull(Integer goodsId);
    /**
     * 根据货物ID将货架更新为未满货架(批量)
     * @param goodsIdList
     */
    void batchUpdateRowNotFull(@Param("goodsIdList") List<Integer> goodsIdList);
}