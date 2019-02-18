/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2010 All Rights Reserved.
 */
package com.ucmed.model.utils;

public class Constants {
	public static final String USER_VERIFY_CODE = "user_verify_code";
	
	public static final long getAccessTokenTime = 60 * 60;// 60分钟缓存
	
	/**
	 * 管理员Session
	 */
	public static final String ADMIN_SESSION = "admin.session";
	public static final String VERIFY_CODE_SESSION = "verify.code.session";
	public static final String VERIFY_TOKEN = "verify.token";
	public static final int VERIFY_TOKEN_LEN = 16;
	public static final String PHONE = "phone";
	
	public static final String USER_SESSION = "user_session";
	public static final String URL_SESSION = "url_session";
    
	//使用到的配置
	public static final String ADMIN_ID = "adminid";
}
