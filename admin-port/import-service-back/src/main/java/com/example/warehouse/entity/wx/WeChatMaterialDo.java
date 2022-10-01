package com.example.warehouse.entity.wx;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 微信素材数据类
 */
@Data
public class WeChatMaterialDo {

    // 素材总数
    private int total_count;
    // 本次调用获取的素材的数量
    private int item_count;
    // 具体内容
    private List<Map<String, Object>> item;

}
