package com.ucmed.model.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by MoLei on 2018/6/28.
 */
@Component
public class CommonConfig {
	
	@Value("${hnsrmyy.weixin_applet_appid}")
    private String appId;
	@Value("${hnsrmyy.weixin_applet_secret}")
    private String appSecret;
	public String getWeixinAppletAppid() {
		return appId;
	}
	public void setWeixinAppletAppid(String appId) {
		this.appId = appId;
	}
	public String getWeixinAppletSecret() {
		return appSecret;
	}
	public void setWeixinAppletSecret(String appSecret) {
		this.appSecret = appSecret;
	}
}
