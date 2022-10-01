package com.dwgj.mlxydedicatedline.mapper.image;

import com.dwgj.mlxydedicatedline.entity.image.Images;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Images record);

    int insertSelective(Images record);

    Images selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Images record);

    int updateByPrimaryKey(Images record);
    /**
     * 批量插入
     * @param imagesList
     * @return
     */
    int insertImagesList(List<Images> imagesList);

    /**
     * 根据资源id获取图片
     * @param images
     * @return
     */
    List<Images> getImagesByContentId(Images images);

    /**
     * 根基资源id和图片类型删除图片
     * @param images
     * @return
     */
    int deleteImagesByContentId(Images images);


    int deleteByIdList(@Param("idList") List<Integer> idList);

    List<Images> getEvaluateImagesByContentId(String contentId);
    List<Images> getImagesByTypeAndContentId(@Param("picType") String picType, @Param("contentId") String contentId);
}