package com.example.warehouse.mapper.image;

import com.example.warehouse.entity.image.Images;
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

    int deleteImagesByContentId(Images record);

    /**
     * 根据资源id获取图片
     * @param images
     * @return
     */
    List<Images> getImagesByContentId(Images images);

    /**
     * 根据资源id获取评价图片
     * @param contentId
     * @return
     */
    List<Images> getEvaluateImagesByContentId(String contentId);

    void insertImagesList(List<Images> list);

    /**
     * 更改图片信息
     * @param images
     * @return
     */
    int updateImagesByContentId(Images images);

    List<Images> getCustomerPackImages(Images images);

    List<Images> getCustomerIdentityPicture(int customerIdentityId);

    void deleteImages(@Param("imageIdList") List<Integer> imageIdList);
}