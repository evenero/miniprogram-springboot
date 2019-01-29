/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2010 All Rights Reserved.
 */
package com.ucmed.model.utils;


/**
 * 
 * @author youmeng
 * @version $Id: Constants.java,v 1.13 2012/02/09 05:36:46 chenc Exp $
 */
public class Constants {
	/**
	 * 渠道标志 空 wap 1 wap
	 */
	public static final String API_CHANNEL = "api_Channel";

	public static final String API_URL = "/api/x.htm";

	public static final String PARAMS2 = "data";
	
	public static final String PARAMS = "params";
	
	public static final String USER_VERIFY_CODE = "user_verify_code";
	
	public static final long getAccessTokenTime = 60 * 60;// 60分钟缓存
	
	/**
	 * 视频url
	 */
	public static final String url = "http://218.108.172.226";
	/**
     * api constants
     */
    public static final String API_NAME = "api_name";
    public static final String API_REQUEST_PARAMS = "params";
	public static final String SESSION_ID = "SessionID";
	public static final String API_SESSION_ID = "session_id";
	public static final String API_APP_ID = "app_id";
	public static final String API_APP_KEY = "app_key";
	public static final String API_HUATUO_APP_ID = "appId";
	public static final String API_HUATUO_APP_KEY = "passwd";
	public static final String API_CLIENT_SESSION = "client_session";
	public static final String API_CLIENT_SESSION_USER_ID = "user_id";

	public static final String PAGE_NO = "pageNo";
	public static final String PAGE_SIZE = "pageSize";
	public static final String PAGE_KEY = "searchName"; //搜索的名字

	public static final String MSG_RESULT = "Result";
	public static final String MSG_CONTENT = "Msg";
	public static final String MSG_DELIVER_URL = "/msgDeliver.htm";
	


	/**
	 * 管理员Session
	 */
	public static final String ADMIN_SESSION = "admin.session";
	public static final String VERIFY_CODE_SESSION = "verify.code.session";
	public static final String VERIFY_TOKEN = "verify.token";
	public static final int VERIFY_TOKEN_LEN = 16;
	public static final String PHONE = "phone";
	/**
	 * 软件版本
	 */
	public static final String SITE_VSERSION = "1.0.0";
	
	/**
     * file upload root directory  文件上传地址 
     */
    public static final String API_UPLOAD_ROOT = "c:/api_upload/";
    
    public static final String WD_IMG_URL = "http://zyyyimg.ucmed.cn/";
    
    /**
     * api flow 
     */
    
    public static final String CLIENT_CHANNEL = "api_Channel";
	public static final String CLIENT_MOBILE = "client_mobile";
	public static final String CLIENT_MOBILE_TYPE = "client_mobile_type";
	public static final String CLIENT_MOBILE_SDK = "client_mobile_sdk";
	public static final String CLIENT_MOBILE_VERSION = "client_version";
	public static final String CLIENT_DATE = "client_date";
	public static final String CLIENT_TRANSNAME = "api_name";
	public static final String IP = "ip";
	public static final String PARAM_ID = "param_id";
	public static final String TMP1 = "tmp1";
	public static final String TMP2 = "tmp2";
	public static final String TMP3 = "tmp3";
	public static final String TMP4 = "tmp4";
	public static final String TMP5 = "tmp5";
	
	/**
	 * push message
	 */
	public static final String PUSH_SENDER_ID = "sender_id";
	public static final String PUSH_ACCEPT_ID = "accept_id";
	public static final String PUSH_TYPE = "type";
	public static final String PUSH_CONTENT = "content";
	public static final String PUSH_TARGET_ID = "target_id";
	public static final String PUSH_TMP1 = "tmp1";
	public static final String PUSH_TMP2 = "tmp2";
	public static final String PUSH_TMP3 = "tmp3";
	public static final String PUSH_TMP4 = "tmp4";
	

	public static final int DOCTOR_QUESTION_COUNT = 10;

	public static final int PRICE_CHANGE = 10;
	
	public static final String USER_SESSION = "user_session";
	public static final String URL_SESSION = "url_session";
	/**
	 * 健康资讯图片路径
	 */
	public static final String IMAGE_URL = "http://21.254.47.18:9000";// "/images/bendi";//"http://218.108.175.10:9000";//"http://202.91.232.182:9000";//"http://api.ucmed.cn";
	
	public static final String stoken = "stoken";
	
	
	  /**
     * weixin调用
     */
    public static final String APP_KEY="aHVhdHVvMi4w";
    public static final String APP_ID="huatuo2.0";
    public static final String CLIENT_VER="2.0";
    public static final String APP_CHANNEL="0";
   
    public static final String APP_ZYYY_KEY="ZW5sNWVWOWhibVJ5YjJsaw==";
    public static final String APP_ZYYY__ID="zyyy_android";
    public static final String NODE_CONTENT="node.content";
    public static final String USERSTATUS="userStatus";
    
    public static final String WX_WAP_URL="http://weixin.ucmed.cn/api/ZDYY_DETAIL.htm?userId=";
    //weixin图片生成地址/home/ucmed/weixin/images
    public static final String WX_IMAGES_DIR="/home/ucmed/weixin/images";//
   
    
    //weixin 视频上传地址 
//   public static final String WX_VIDEO_DIR="c:/weixin/video_upload/hnzlyy";
//    public static final String WX_VIDEO_DIR="E:/workspace/hnzlxwht/WebContent/upload";//
    public static final String WX_VIDEO_DIR="/home/ucmed/upload/hnzlyy/upload";
    //weixin图片生成do
    public static final String WX_IMAGES_DO="/api/ZDYY_IMAGE.htm?Id=";
    //weixin图片生成URL
    public static final String WX_IMAGES_URL="/images";
	
	 
		/**
		 * 微信图片路径，wap路径
		 */
		public static final String HNZLYY_IMG_URL = "http://yyapptest.ucmed.cn";
		public static final String HNZLYY_URL = "http://111.1.55.5:8080";
		public static final String YONGKANGYY_IMG_URL = "http://hnzlwxht.ucmed.cn";
		
		/**
		 * 阿里相关配置
		 */
		public static final String zwjkurl = "http://wap.zwjk.cn";
		public static final String SESSION_USER = "ali-sessin-user";
		public static final String SESSION_USER_ID = "zyyy-user-id";
		public static final String RETURN_DATA = "returnData";
		public static final String AGREEMENT_ID = "agreementId";
		public static final String AUTH_CODE = "alipay_auth_code";
//		public static final String alurl = "http://hnsrmyytest.ucmed.cn";
		public static final String alurl = "http://zfb.hnsrmyyydzf.com";
		public static final String aliurl = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?" +
											"app_id=2014112100017287&auth_skip=false&scope=auth_userinfo&" +
											"redirect_uri="+alurl+"/loginAuth.html?agreementId=";

		public static String SOURCETYPE="sourceType";
		public static String HOSPITALNAME="hospitalName";
		public static String NEWCENTER="新闻公告";
		/**
		 * 查询身份证次数
		 */
		public static String QUERY_IDCARD_CACHE="query_idcard_cache_";
		
		/**
		 * 号源缓存
		 */
		public static String HAOYUAN_CACHE="haoyuan_cache_";
		
		public static final String ADMIN_ID = "adminid";
		//图片缓存配置
		public static final String IMAGE_VERIFY_CODE = "image_verify_code_";
		
}
