/**
 * 公众号管理
 * @return {type} {description}
 */

 import request from "@/utils/request";

 /**
  * 公众号菜单列表
  * @return {type} {description}
  */
 export function getMenuList ({
     page,
     limit,
     menuName
 }) {
     return request({
         url: "/wechatMenu/getMenuList",
         method: "post",
         data: {
             pageNumber: page, // 当前页码
             pageSize: limit, //每页条数
             menuName: menuName
         }
     });
 }

 export function getMenuInfo (id) {
    return request({
        url: "/wechatMenu/getMenuInfo",
        method: "post",
        data: {
            id: id
        }
    });
}
 

 /**
  * 公众号菜单列表
  * @return {type} {description}
  */
  export function saveMenu ({
    id,
    menuLevel,
    menuName,
    menuEname,
    parentId,
    redirectUrl,
    menuType,
    wechatKey,
    mediaId,
    articleId,
    version
}) {
    return request({
        url: "/wechatMenu/saveMenu",
        method: "post",
        data: {
            id: id,
            menuLevel: menuLevel,
            menuName: menuName,
            menuEname: menuEname,
            parentId: parentId,
            redirectUrl: redirectUrl,
            menuType: menuType,
            wechatKey: wechatKey,
            mediaId: mediaId,
            articleId: articleId,
            version: version
        }
    });
}


export function getFirstMenu () {
    return request({
        url: "/wechatMenu/getFirstMenu",
        method: "get",
    });
}

export function getSettingMenu () {
    return request({
        url: "/wechatMenu/getSettingMenu",
        method: "get",
    });
}
export function enableMenu({ids}){
    return request({
        url:"/wechatMenu/enableMenu",
        method:"post",
        data:{
            ids: ids
        }
    })
}

export function deleteMenu(id){
    return request({
        url:"/wechatMenu/deleteMenu",
        method: "post",
        data: {
            id: id
        }
    })
}
export function getMaterialList({
    materialType,
    offset,
    limit
}){
    return request({
        url:"/wechatMenu/getMaterial",
        method: "post",
        data: {
            type: materialType,
            offset: offset,
            count: limit
        }
    })
}
 // 公众号菜单接口  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑








 // 自动回复规则接口  ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

 /**
  * 自动回复规则列表
  * @return {type} {description}
  */
export function getReplyList ({
    page,
    limit,
    replyRule,
    keyWord
}) {
    return request({
        url: "/wechatReply/getReplyList",
        method: "post",
        data: {
            pageNumber: page, // 当前页码
            pageSize: limit, //每页条数
            replyRule: replyRule,
            keyWord: keyWord
        }
    });
}
export function getReply (id) {
    return request({
        url: "/wechatReply/getReply",
        method: "get",
        params: {
            id: id
        }
    });
}

 /**
  * 保存自动回复规则
  * @return {type} {description}
  */
export function saveReply ({
    id,
    replyRule,
    replyType,
    replyContent,
    keyWord,
    redirectUrl,
    mediaId,
}) {
    return request({
        url: "/wechatReply/saveReply",
        method: "post",
        data: {
            id: id,
            replyRule: replyRule,
            replyType: replyType,
            replyContent: replyContent,
            keyWord: keyWord,
            redirectUrl: redirectUrl,
            mediaId: mediaId,
        }
    });
}

export function deleteReply (id) {
    return request({
        url: "/wechatReply/deleteReply",
        method: "post",
        data: {
            id: id
        }
    });
}


export function getReplyLogList ({
    page,
    limit,
    keyWord
}) {
    return request({
        url: "/wechatReply/getReplyLogList",
        method: "post",
        data: {
            pageNumber: page, // 当前页码
            pageSize: limit, //每页条数
            keyWord: keyWord
        }
    });
}