package com.example.warehouse.common;

import com.example.warehouse.vo.PageVo;
import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @des 转换分页参数为Integer，并改变游标读取位置
 * @author wenguosheng
 * @date 2019年4月24日20:19:57
 */
@Slf4j
public class PageHelp {
    /**
     *
     * @param pageNumber  当前页
     * @param pageSize  每页显示数
     * @return
     */
    public static PageData editPage(String pageNumber,String pageSize){
        PageData data = new PageData();
        try {
            Object[] obj = {pageSize, pageNumber};
            log.info("参数：pageSize[{}],pageNumber[{}]", obj);
            int number = 0;
            int page = 0;
            int size = 0;
            if (StringUtil.isNotEmpty(pageNumber) && StringUtil.isNotEmpty(pageSize)) {
                number = Integer.parseInt(pageNumber);
                size = Integer.parseInt(pageSize);
                if (number > 0) {  //如果页码大于0，则是第2页开始
                    page = (number - 1) * size;
                }
            }
            data.setNumber(number == 0 ? 1 : number);
            data.setPageNumber(page);
            data.setPageSize(size);
        }catch (NumberFormatException e){
            log.error("【查询分页处理】分页参数处理过程中异常：{}", e);
            e.printStackTrace();
        }
        return data;
    }

    public static PageData editPage(PageVo pageVo) {
        PageData data = new PageData();
        try {
            Object[] obj = {pageVo.getPageSize(), pageVo.getPageNumber()};
            log.info("参数：pageSize[{}],pageNumber[{}]", obj);
            int number = pageVo.getPageNumber();
            int page = 0;
            int size = pageVo.getPageSize();
            if (number > 0 && size != 0) {
                number = pageVo.getPageNumber();
                size = pageVo.getPageSize();
                if (number > 0) {  //如果页码大于0，则是第2页开始
                    page = (number - 1) * size;
                }
            }
            data.setNumber(number == 0 ? 1 : number);
            data.setPageNumber(page);
            data.setPageSize(size);

        }catch (NumberFormatException e){
            log.error("【查询分页处理】分页参数处理过程中异常：{}", e);
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 分页码初始化
     * @param map
     * @return
     */
    public static Map<String,Object> initPage(Map<String,Object> map){
        if (!map.containsKey("pageNumber")) {
            map.put("pageNumber", 0);
        }
        if (!map.containsKey("pageSize")) {
            map.put("pageSize", 10);
        }
        PageData data = editPage(map.get("pageNumber").toString(),map.get("pageSize").toString());
        map.put("pageNumber",data.getPageNumber());
        map.put("pageSize",data.getPageSize());
        map.put("page",data);
        return map;
    }

    /**
     * 分页码初始化
     * @param pageNumber 页数
     * @param pageSize 每页总数
     * @return
     */
    public static PageData initPage(Integer pageNumber, Integer pageSize){
        if (pageNumber == null) {
            pageNumber = 0;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        return editPage(String.valueOf(pageNumber),String.valueOf(pageSize));
    }

}
