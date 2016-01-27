package com.hp.wx.until;

import com.github.sd4324530.fastweixin.api.config.ApiConfig;


public class APConfigUtil {
	private static String appid;
	private static String secret;
	private static ApiConfig config;
	
	static{
		PropertiesUtil properties=new PropertiesUtil("wxapi.propertites");
		if(properties!=null){
			appid =properties.readProperty("appid");
			secret = properties.readProperty("secret");
			config = new ApiConfig(appid, secret);
			ConfigChangeHandle configChangeHandle = new ConfigChangeHandle();
			config.addHandle(configChangeHandle);
		}
	}
	
	public static ApiConfig getApiConfig() {
		return config;
	}
}

