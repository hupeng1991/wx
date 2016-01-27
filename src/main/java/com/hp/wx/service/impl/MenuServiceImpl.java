package com.hp.wx.service.impl;

import com.github.sd4324530.fastweixin.api.MenuAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.entity.Menu;
import com.github.sd4324530.fastweixin.api.enums.ResultType;
import com.hp.wx.until.APConfigUtil;

public class MenuServiceImpl {
   
	/**
	 * 新增菜单
	 * @param menu
	 */
	public void createMenu(Menu menu){
		ApiConfig config=APConfigUtil.getApiConfig();
		MenuAPI menuAPI = new MenuAPI(config);
		ResultType resultType = menuAPI.createMenu(menu);
	}
}
