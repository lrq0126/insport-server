package com.example.warehouse.mapper.wx;

import com.example.warehouse.entity.wx.WeChatMenuDo;
import com.example.warehouse.entity.wx.WechatMenu;
import com.example.warehouse.mapper.MyBatisBaseDao;
import com.example.warehouse.vo.wechat.WechatMenuReqVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 微信公众号菜单(WechatMenu)表Mapper类
*
* @author Mr.Wang
* @version 1.0
* @since 2022/2/25 10:2
*/
@Repository
public interface WechatMenuMapper extends MyBatisBaseDao<WechatMenu, Integer> {

    @Override
    int insert(WechatMenu record);

    @Override
    int insertSelective(WechatMenu record);

    @Override
    WechatMenu selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(WechatMenu record);

    @Override
    int updateByPrimaryKey(WechatMenu record);
	
	@Override
    int deleteByPrimaryKey(Integer id);

	int removeByPrimaryKey(Integer id);

    /**
     * 查询要发布的公众号菜单
     * @param menuIds
     * @return
     */
    List<WeChatMenuDo> selectReleaseByMenuIds(@Param("menuIds") List<String> menuIds);

    List<WechatMenu> selectSecondaryMenuByIds(@Param("menuIds") List<String> menuIds);

    List<WechatMenu> selectSecondaryMenu(int id);

    int getMenuListCount(WechatMenuReqVo wechatMenuReqVo);

    List<WeChatMenuDo> getMenuList(WechatMenuReqVo wechatMenuReqVo);

    List<WechatMenu> getFirstMenu();

    List<WeChatMenuDo> getSettingMenu();
}