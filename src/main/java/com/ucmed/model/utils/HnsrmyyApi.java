package com.ucmed.model.utils;

import java.util.Date;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HnsrmyyApi {
	private static final Logger log = LoggerFactory.getLogger(HnsrmyyApi.class);
	
//	public static final String TARGET_URL = "http://192.168.10.35:8082/api/exec.htm";
//	public static final String TARGET_URL = "http://127.0.0.1:8082/api/exec.htm";
//	public static final String TARGET_URL = "http://127.0.0.1:8080/api/exec.htm";
	public static final String TARGET_URL = "http://61.187.87.30:8082/api/exec.htm";
//	public static final String TARGET_URL = "http://61.187.87.22:8085/api/exec.htm";
//	public static final String TARGET_URL = "http://192.168.10.35:8088/api/exec.htm";
	private static HnsrmyyApi mInstance;
	/**
	 * 渠道标志
	 * 空 android
	 * 1 android
	 * 2 android pad
	 * 3 apple
	 * 4 apple ipad
	 * 5 wap
	 * 6 web
	 */


	public static HnsrmyyApi getInstance() {
		if (mInstance == null)
			mInstance = new HnsrmyyApi();
		return mInstance;
	}

	private HnsrmyyApi() {
	}
	
	/**
	 * for new uri's request
	 * 
	 * @param url
	 */
	public HnsrmyyApi(String url) {
	}
	
	public JSONObject requestActionParams(String apiName, JSONObject params, HttpSession session) {
		JSONObject res = null;
	
		try {
			Date date1=new Date();
		     log.info("开始时间======"+DateUtil.dateToString4(date1));
			JSONObject obj = new JSONObject();
			obj.put(Constants.API_CHANNEL, "1");//web
			obj.put(Constants.API_NAME, apiName);
			obj.put(Constants.API_APP_ID, "zsyy_android");
			obj.put(Constants.API_APP_KEY, "ZW5sNWVWOWhibVJ5YjJsaw==");
			obj.put(Constants.CLIENT_MOBILE_VERSION, "1.0.0");
			obj.put(Constants.PARAMS, params);
			res = request(obj);
			Date date2=new Date();
			log.info("开始时间======"+DateUtil.dateToString4(date2));
			log.info("时间差=========="+(date2.getTime()-date1.getTime()));
		} catch (Exception e) {
			log.info("请求后台接口发生了异常："+e);
			e.printStackTrace();
		}
		return res;
	}
	
	public JSONObject request(JSONObject request) {
		JSONObject res = null;
		String response = null;
		try {
			log.info("后台接口请求入参："+request.toString());
			request.put("requestData", request.toString());
			response = HttpConnectionManager.nameAndValuePairPost(TARGET_URL, request.toString());
			res=JSONObject.fromObject(response);
			log.info("后台接口请求出参："+res);
		} catch (Exception e) {
			log.info("请求后台接口发生了异常："+e);
			e.printStackTrace();
		}
		return res;
	}	
}
