package com.ucmed.model.utils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ucmed.model.exception.BusinessException;
import com.ucmed.model.exception.ErrorCode;
import com.ucmed.model.exception.ErrorInfo;

import net.sf.json.JSONObject;

/**
 * 请求管理类
 * @author ucmed
 *
 */
public class RequestUtils {
	private final static Logger LOG = LoggerFactory.getLogger(RequestUtils.class);
	@Autowired
	private RedisUtils redis;
	@Autowired
	private ErrorInfo errorInfo;
    private String url;
    private String accesskey;
    private String yydm;
    public void setUrl(String url) {
    	this.url = url;
    }
    public void setAccesskey(String accesskey) {
    	this.accesskey = accesskey;
    }
    public void setYydm(String yydm) {
    	this.yydm = yydm;
    }
    
	
	/**
	 * 请求his数据
	 * @param params
	 * @return
	 */
	public JSONObject requestHisData(JSONObject params) throws BusinessException {
		String res = "";
		try {
			setCommonParams(params);
			LOG.info("-----[syzxyy-render-requester<>"+params.optString("randnum")+"]request-->>"+params.toString());
			res = HttpConnectionManager.nameAndValuePairPost(url, params.toString());
		} catch (UnsupportedEncodingException e) {
			LOG.info("请求错误，错误信息："+res);
		}
		JSONObject resObj = JSONObject.fromObject(res);
		LOG.info("-----[syzxyy-render-requester<>"+params.optString("randnum")+"]response<<--"+res);
		checkSuccessStatus(resObj);
		return resObj;
	}
	
	
	/**
	 * 请求his数据，专用于查询，不抛出任何异常
	 * 默认进行缓存，若不需要缓存请传入false
	 * @param params
	 * @return
	 */
	public JSONObject requestDataAndNoException(JSONObject params){
		return requestDataAndNoException(params, true);
	}
	
	
	/**
	 * 请求his数据，专用于查询，不抛出任何异常
	 * @param params
	 * @param useCache 是否使用缓存
	 * @return
	 */
	public JSONObject requestDataAndNoException(JSONObject params, boolean useCache){
		String res = "";
		JSONObject resObj = new JSONObject();
		try {
			String cachedReportData = null;
			if(useCache) {
				cachedReportData = (String) redis.get(redis.getCacheConfig().getCacheName()+params.optString("cacheKey"));
			}
			if(null!=cachedReportData) {
				resObj = JSONObject.fromObject(cachedReportData);
			}else {
				setCommonParams(params);
				LOG.info("-----[syzxyy-render-requester<>"+params.optString("randnum")+"]request-->>"+params.toString());
				res = HttpConnectionManager.nameAndValuePairPost(url, params.toString());
				if(useCache&&null!=res&&!"".equals(res)) {
					redis.put(redis.getCacheConfig().getCacheName()+params.optString("cacheKey"), res, redis.getCacheConfig().getCacheTimeOut(), TimeUnit.MINUTES);
				}
				resObj = JSONObject.fromObject(res);
			}
		} catch (UnsupportedEncodingException e) {
			LOG.info("请求错误，错误信息："+res);
		}
		LOG.info("-----[syzxyy-render-requester<>"+params.optString("randnum")+"]response<<--"+res);
		return resObj;
	}
	
	
	
	/**
	 * 设置请求公共参数
	 * @param params
	 */
	private void setCommonParams(JSONObject params) {
		params.put("accesskey", accesskey);
		params.put("yydm", yydm);
		params.put("randnum", UUID.randomUUID().toString());
		params.put("timestamp", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
	}
	
	/**
	 * 检查返回数据的状态，空或his返回异常则抛出
	 * @param res
	 * @throws BusinessException 
	 */
	private void checkSuccessStatus(JSONObject res) throws BusinessException {
		if(null==res||res.isEmpty()) {
			throw new BusinessException(ErrorCode.CODE_NULL, errorInfo.getNullInfo());
		}else {
			if(!res.optBoolean("success")) {
				String errorMsg = res.optString("message");
				if(errorMsg.contains("\\|")) {
					errorMsg = errorMsg.split("\\|")[1];
				}
				throw new BusinessException(ErrorCode.CODE_500, errorMsg);
			}
		}
	}
	
}
