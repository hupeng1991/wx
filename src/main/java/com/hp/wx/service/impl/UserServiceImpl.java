package com.hp.wx.service.impl;

import org.junit.Test;
import org.springframework.stereotype.Service;

import com.github.sd4324530.fastweixin.api.UserAPI;
import com.github.sd4324530.fastweixin.api.response.GetUsersResponse;
import com.hp.wx.until.APConfigUtil;

@Service("UserServiceImpl")
public class UserServiceImpl extends BaseServiceImpl {
	/**
     * 获取关注者列表
     *
     * @param config API配置
     */
	@Test
    public void getUserList() {
		try{
			UserAPI userAPI = new UserAPI(APConfigUtil.getApiConfig());
			GetUsersResponse users = userAPI.getUsers(null);

			logger.debug("user count:{}", users.getCount());
			logger.debug("user total:{}", users.getTotal());
			String[] openids = users.getData().getOpenid();
			for (String id : openids) {
				logger.debug("id:{}", id);
			}
		}catch(Exception e){
			logger.error(e.toString(),e);
		}
    }
	
}
