package com.example.warehouse.service.wechat.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.common.DateUtil;
import com.example.warehouse.common.HttpUtilManager;
import com.example.warehouse.common.PageData;
import com.example.warehouse.common.PageHelp;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.wx.WeChatMenuDo;
import com.example.warehouse.entity.wx.WechatMenu;
import com.example.warehouse.enums.ResultStatus;
import com.example.warehouse.mapper.user.WxAccessTokenMapper;
import com.example.warehouse.mapper.wx.WechatMenuMapper;
import com.example.warehouse.model.PageResultModel;
import com.example.warehouse.model.ResultModel;
import com.example.warehouse.service.wechat.WechatMenuService;
import com.example.warehouse.vo.wechat.WechatMenuReqVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpException;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.warehouse.enums.ResultStatus.*;
import static com.example.warehouse.enums.weChat.WeChatMenuTypeEnum.*;


/**
* 微信公众号菜单(WechatMenu)表服务实现类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/2/25 10:2
*/
@Service("wechatMenuService")
@Slf4j
public class WechatMenuServiceImpl implements WechatMenuService {
    /**
     * 微信公众号菜单Mapper
     */
    @Resource
    private WechatMenuMapper wechatMenuMapper;


    @Override
    public ResponseEntity<PageResultModel> getMenuList(WechatMenuReqVo wechatMenuReqVo) {
        PageData pageData = PageHelp.editPage(wechatMenuReqVo);
        int count = wechatMenuMapper.getMenuListCount(wechatMenuReqVo);
        pageData.setTotal(count);
        if(count > 0){
            wechatMenuReqVo.setPageNumber(pageData.getPageNumber());
            List<WeChatMenuDo> wechatMenuList = wechatMenuMapper.getMenuList(wechatMenuReqVo);
            return new ResponseEntity<>(PageResultModel.ok(wechatMenuList, pageData), HttpStatus.OK);
        }

        return new ResponseEntity<>(PageResultModel.ok(new ArrayList<>(), pageData), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getMenuInfo(int id) {

        WechatMenu wechatMenu = wechatMenuMapper.selectByPrimaryKey(id);

        return new ResponseEntity<>(ResultModel.ok(wechatMenu), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getFirstMenu() {
        List<WechatMenu> wechatMenuList = wechatMenuMapper.getFirstMenu();
        return new ResponseEntity<>(ResultModel.ok(wechatMenuList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> getSettingMenu() {
        List<WeChatMenuDo> wechatMenuList = wechatMenuMapper.getSettingMenu();
        return new ResponseEntity<>(ResultModel.ok(wechatMenuList), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResultModel> saveWechatMenu(WechatMenu wechatMenu) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_NOT_LOGIN), HttpStatus.OK);
        }
        if(wechatMenu.getId() == null){
            wechatMenu.setCreateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            wechatMenu.setCreateId(user.getId());
            wechatMenu.setStatus(1);
            wechatMenu.setVersion(1);
            int i = wechatMenuMapper.insertSelective(wechatMenu);
            if(i != 1){
                return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }else {
            List<String> ids = new ArrayList<>();
            ids.add(String.valueOf(wechatMenu.getId()));
            List<WeChatMenuDo> weChatMenuDos = wechatMenuMapper.selectReleaseByMenuIds(ids);
            if(!CollectionUtils.isEmpty(weChatMenuDos) && wechatMenu.getMenuLevel() != 1){
                return new ResponseEntity<>(ResultModel.error(ResultStatus.WECHAT_MENU_CAN_NOT_SETTING_SECONDARY), HttpStatus.OK);
            }
            wechatMenu.setUpdateTime(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            wechatMenu.setUpdateId(user.getId());
            wechatMenu.setStatus(1);
            int i = wechatMenuMapper.updateByPrimaryKey(wechatMenu);if(i != 1){
                return new ResponseEntity<>(ResultModel.error(ResultStatus.ERROR), HttpStatus.OK);
            }
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<ResultModel> deleteWechatMenu(int id) {
        wechatMenuMapper.deleteByPrimaryKey(id);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }


    private String RELEASE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

    @Autowired
    private WxAccessTokenMapper wxAccessTokenMapper;

    @Override
    public ResponseEntity<ResultModel> enableMenu(String ids) {
        JSONArray jsonArray = JSON.parseArray(ids);
        List<String> menuIds = new ArrayList<>();
        for (Object obj : jsonArray) {
            menuIds.add(String.valueOf(obj));
        }

        List<WeChatMenuDo> menuList = wechatMenuMapper.selectReleaseByMenuIds(menuIds);

        if(CollectionUtils.isEmpty(menuList)){
            return new ResponseEntity<>(ResultModel.error(WECHAT_MENU_SETTING_NULL), HttpStatus.OK);
        }

        // 有些情况会勾选了一级菜单，而二级菜单会没有传递值的情况，所以一级菜单勾选了的情况下，二级菜单会自动勾选
        // 填装二级菜单
        List<WechatMenu> secondaryMenuList = new ArrayList<>();
        for (WeChatMenuDo weChatMenuDo : menuList) {
            secondaryMenuList.addAll(weChatMenuDo.getSecondaryMenu());
        }

        if(!CollectionUtils.isEmpty(secondaryMenuList)){
            List<Integer> secondaryMenuIdList = secondaryMenuList.stream().map(WechatMenu::getId).collect(Collectors.toList());
            // 这里是选了部分二级菜单的情况，当有一个二级菜单没有勾选的情况下，一级菜单等于没有勾选
            // 填装二级菜单
            List<WechatMenu> getSecondaryMenuList = wechatMenuMapper.selectSecondaryMenuByIds(menuIds);
            for (WechatMenu wechatMenu : getSecondaryMenuList) {
                if(!secondaryMenuIdList.contains(wechatMenu.getId())){
                    secondaryMenuList.add(wechatMenu);
                }
            }
            // 查询那些需要的一级菜单
            List<Integer> first = secondaryMenuList.stream().map(WechatMenu::getParentId).collect(Collectors.toList());
            for (Integer integer : first) {
                if(!menuIds.contains(String.valueOf(integer))){
                    menuIds.add(String.valueOf(integer));
                }
            }
        }

        menuList = wechatMenuMapper.selectReleaseByMenuIds(menuIds);
        if(menuList.size() > 3){
            return new ResponseEntity<>(ResultModel.error(WECHAT_MENU_EXCESS), HttpStatus.OK);
        }
        String menuStr = "";
        // 一级菜单拼接
        for (WeChatMenuDo wechatMenu : menuList) {

            String fMenuStr = menuJoining(wechatMenu);

            // 二级菜单处理
            if(!CollectionUtils.isEmpty(secondaryMenuList)){
                String secondaryMenuStr =  "" ;
                for (WechatMenu secondaryMenu : secondaryMenuList) {
                    if(secondaryMenu.getParentId().equals(wechatMenu.getId())){
                        // smsmj = secondaryMenuStrMenuJoining
                        String smsmj = menuJoining(secondaryMenu);
                        secondaryMenuStr = StringUtils.isEmpty(secondaryMenuStr) ?
                                "{" + smsmj.substring(0, smsmj.length() - 1) + "}," :
                                secondaryMenuStr + "{" + smsmj.substring(0, smsmj.length() - 1) + "},";
                    }
                }
                if(!StringUtils.isEmpty(secondaryMenuStr)){
                    fMenuStr = fMenuStr + "\"sub_button\": [" +
                            secondaryMenuStr.substring(0,secondaryMenuStr.length()-1) + "]";
                }
            }else{
                // 如果没有二级菜单，则删除一级菜单最后的 ，
                fMenuStr = fMenuStr.substring(0,fMenuStr.length()-1);
            }
            menuStr = StringUtils.isEmpty(menuStr) ? "{" + fMenuStr + "}," : menuStr + "{" + fMenuStr + "},";
        }

        menuStr = "{\"button\": [" + menuStr.substring(0,menuStr.length()-1) + "]}";
        JSONObject jsonObject = JSONObject.parseObject(menuStr);
        String accessToken = wxAccessTokenMapper.getAccessToken();
//        String accessToken = "54_dhig_XhGARRPjioF0cR7wvxopqRewr66YOm9yiXxy4F0zjHocGPyTIhk2fSA89D0ibJ0JI5zbXmxhuoyIS4CH_2mpGHAFLWQ46ivIxoEzDvxXqXb-r2wJswK-9OgxqbSxBKxopgH10SqRZGBILMaAHACFG";
        log.info("当前公众号菜单内容：{}", jsonObject.toJSONString());
        HttpUtilManager httpUtilManager = HttpUtilManager.getInstance();
        String result = httpUtilManager.post(RELEASE_MENU_URL + accessToken, jsonObject.toJSONString());
        System.out.println("设置公众号菜单结果：" + result);
        Map<String, Object> resultMap = (Map<String, Object>) JSON.parse(result);
        if(resultMap.containsKey("errcode") && "0".equals(resultMap.get("errcode").toString())){
            return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(ResultModel.error(ERROR, "微信菜单设置失败，微信返回错误代码：" + resultMap.get("errcode")), HttpStatus.OK);
        }

    }

    private String menuJoining(WechatMenu wechatMenu) {
        String name = "";
        if(!StringUtils.isEmpty(wechatMenu.getMenuName())){
            name = "\"name\": \""+wechatMenu.getMenuName()+"\",";
        }

        String type = "";
        if(!StringUtils.isEmpty(wechatMenu.getMenuType())){
            type = "\"type\" : \""+wechatMenu.getMenuType()+"\",";
        }

        String key = "";
        if(!StringUtils.isEmpty(wechatMenu.getWechatKey())){
            key = "\"key\" : \""+wechatMenu.getWechatKey()+ "\",";
        }

        String url = "";
        if(!StringUtils.isEmpty(wechatMenu.getRedirectUrl())){
            url = "\"url\" : \""+wechatMenu.getRedirectUrl()+ "\",";
        }

        String media_id = "";
        if(!StringUtils.isEmpty(type) && !StringUtils.isEmpty(wechatMenu.getMediaId())){
            switch (wechatMenu.getMenuType()){
                case "media_id":
                    media_id = "\"media_id\" : \""+wechatMenu.getMediaId()+ "\",";
                    break;
                case "view_limited" :
                    media_id = "\"media_id\" : \""+wechatMenu.getMediaId()+ "\",";
                    break;
                case "article_id" :
                    media_id = "\"article_id\" : \""+wechatMenu.getMediaId()+ "\",";
                    break;
                case "article_view_limited" :
                    media_id = "\"article_id\" : \""+wechatMenu.getMediaId()+ "\",";
                    break;
                default: break;
            }
        }


        String fMenuStr = name + type + key + url + media_id;

        return fMenuStr;
    }

}