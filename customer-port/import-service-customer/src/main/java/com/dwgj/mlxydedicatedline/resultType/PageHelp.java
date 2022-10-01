package com.dwgj.mlxydedicatedline.resultType;

import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

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
            Object[] obj = {pageSize, pageSize};
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
            data.setNumber(number);
            data.setPageNumber(page);
            data.setPageSize(size);
        }catch (NumberFormatException e){
            log.error("【查询分页处理】分页参数处理过程中异常：{}", e);
            e.printStackTrace();
        }
        return data;
    }

}
