package com.ucmed.model.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存配置文件
 * @author ucmed
 *
 */
@Configuration
@ConfigurationProperties(prefix="cache")
public class CacheConfig {
	/**
	 * redis缓存名,默认为default_cache
	 */
	private String cacheName;
	/**
	 * redis缓存过期时间,默认为5
	 */
	private Long cacheTimeOut;
	/**
	 * 图片缓存key前缀,默认为image_verify_code_cache_
	 */
	private String imageVerifyCodePrefix;
	/**
	 * 图片验证码缓存过期时间
	 */
	private Long imageVerifyCodeTimeOut;

	public String getCacheName() {
		return cacheName;
	}
	public void setCacheName(String cacheName) {
		this.cacheName = "".equals(cacheName)?"default_cache_":cacheName;
	}
	public Long getCacheTimeOut() {
		return cacheTimeOut;
	}
	public void setCacheTimeOut(Long cacheTimeOut) {
		this.cacheTimeOut = cacheTimeOut==0L?5L:cacheTimeOut;
	}
	public String getImageVerifyCodePrefix() {
		return imageVerifyCodePrefix;
	}
	public void setImageVerifyCodePrefix(String imageVerifyCodePrefix) {
		this.imageVerifyCodePrefix = "".equals(imageVerifyCodePrefix)?"image_verify_code_cache_":imageVerifyCodePrefix;
	}
	
	public Long getImageVerifyCodeTimeOut() {
		return imageVerifyCodeTimeOut;
	}
	public void setImageVerifyCodeTimeOut(Long imageVerifyCodeTimeOut) {
		this.imageVerifyCodeTimeOut = imageVerifyCodeTimeOut==0L?5L:imageVerifyCodeTimeOut;
	}
	@Override
	public String toString() {
		return "CacheConfig [cacheName=" + cacheName + ", cacheTimeOut=" + cacheTimeOut + ", imageVerifyCodePrefix="
				+ imageVerifyCodePrefix + ", imageVerifyCodeTimeOut=" + imageVerifyCodeTimeOut + "]";
	}
}
