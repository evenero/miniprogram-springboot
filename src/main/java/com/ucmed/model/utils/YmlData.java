package com.ucmed.model.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by MoLei on 2018/6/28.
 */
@Component
public class YmlData {
	
	@Value("${hnsrmyy.weixin_applet_appid}")
    private String appId;
	@Value("${hnsrmyy.weixin_applet_secret}")
    private String appSecret;
	@Value("${hnsrmyy.img_cache_time}")
    private Long imgCacheTime;
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
	public Long getImgCacheTime() {
		return imgCacheTime;
	}
	public void setImgCacheTime(Long imgCacheTime) {
		this.imgCacheTime = imgCacheTime;
	}
}
