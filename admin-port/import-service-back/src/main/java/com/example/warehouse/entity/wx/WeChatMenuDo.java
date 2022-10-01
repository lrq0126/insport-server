package com.example.warehouse.entity.wx;

import lombok.Data;

import java.util.List;

@Data
public class WeChatMenuDo extends WechatMenu {

    private List<WechatMenu> secondaryMenu;

}
