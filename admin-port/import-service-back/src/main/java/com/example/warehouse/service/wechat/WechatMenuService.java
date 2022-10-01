package com.example.warehouse.service.wechat;


import com.example.warehouse.entity.wx.WechatMenu;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.vo.wechat.WechatMenuReqVo;
import org.springframework.http.ResponseEntity;

/**
* 微信公众号菜单(WechatMenu)表服务类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/2/25 10:2
*/
public interface WechatMenuService {

    ResponseEntity<PageResultModel> getMenuList(WechatMenuReqVo wechatMenuReqVo);

    ResponseEntity<ResultModel> getMenuInfo(int id);

    ResponseEntity<ResultModel> getFirstMenu();

    ResponseEntity<ResultModel> getSettingMenu();

    ResponseEntity<ResultModel> saveWechatMenu(WechatMenu wechatMenu);

    ResponseEntity<ResultModel> deleteWechatMenu(int id);

    ResponseEntity<ResultModel> enableMenu(String ids);
}