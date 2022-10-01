package com.example.warehouse.enums.weChat;

public enum WeChatMenuTypeEnum {

    USER_CLICK("click", "用户点击推送事件"),
    OPEN_VIEW("view", "跳转URL"),
    SCANCODE_PUSH("scancode_push", "扫码推事件"),
    SCANCODE_WAITMSG("scancode_waitmsg", "扫码推事件且弹出“消息接收中”提示框"),
    PIC_SYSPHOTO("pic_sysphoto", "弹出系统拍照发图"),
    PIC_PHOTO_OR_ALBUM("pic_photo_or_album", "弹出拍照或者相册发图"),
    PIC_WEIXIN("pic_weixin", "弹出微信相册发图器"),
    LOCATION_SELECT("location_select", "弹出地理位置选择器"),
    MEDIA_ID("media_id", "下发消息（除文本消息）"),
    VIEW_LIMITED("view_limited", "跳转图文消息URL"),
    ARTICLE_ID("article_id", "图文消息"),
    ARTICLE_VIEW_LIMITED("article_view_limited", "跳转图文消息URL"), //类似 view_limited，但不使用 media_id 而使用 article_id

    ;

    WeChatMenuTypeEnum(String menuType, String typeContent){
        this.menuType = menuType;
        this.typeContent = typeContent;
    }

    public String getMenuType(){
        return menuType;
    };

    private String menuType;
    private String typeContent;

}
