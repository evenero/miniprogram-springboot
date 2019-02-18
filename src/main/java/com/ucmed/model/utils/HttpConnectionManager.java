package com.ucmed.model.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpConnectionManager {
	
	/**
	 * 最大连接数
	 */
	public final static int MAX_TOTAL_CONNECTIONS = 10000;
	/**
	 * 等待超时时间
	 */
	public final static int WAIT_TIMEOUT = 10000;
	/**
	 * 每个路由最大连接数
	 */
	public final static int MAX_ROUTE_CONNECTIONS = 2000;
	/**
	 * 连接超时时间
	 */
	public final static int CONNECT_TIMEOUT = 5000;
	/**
	 * 读取超时时间
	 */
	public final static int READ_TIMEOUT = 30000;
	/**
	 * 从池中获取连接超时时间
	 */
	public final static int REQUEST_TIMEOUT = 5000;
	public static CloseableHttpClient httpclient = null;
	private final static Logger LOG = LoggerFactory.getLogger(HttpConnectionManager.class);
	
	static{
		init();
	}
	/**
	 * 初始化连接池
	 */
	public static void init(){
		if(null!=httpclient){
			return;
		}
		//创建连接管理器
		PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager();
		//设置最大连接数
		connMgr.setMaxTotal(MAX_TOTAL_CONNECTIONS);
		//设置每个路由的最大连接数
		connMgr.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
		//创建Httpclient
		httpclient = HttpClients.custom().setConnectionManager(connMgr).build();
	}
	
	public static void close(){
		if(null!=httpclient){
			try {
				httpclient.close();
			} catch (IOException e) {
				LOG.info("",e);
			}
		}
	}
	
	/**
	 * get方式请求数据
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getUrlContent(String url) throws ClientProtocolException, IOException{
		String html = "";
		//检查参数
		if(null == httpclient){
			throw new RuntimeException("httpclient not init.");
		}
		if(null == url || url.trim().length()==0){
			throw new RuntimeException("url is blank.");
		}
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("accept","/");
		httpGet.addHeader("connection", "Keep-Alive");
        httpGet.addHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		CloseableHttpResponse response = null;
		response = httpclient.execute(httpGet,HttpClientContext.create());
		HttpEntity entity = response.getEntity();
		html = EntityUtils.toString(entity,"utf-8");
		EntityUtils.consume(entity);
		return html;
	}
	
	/**
	 * post方式请求数据
	 * @return
	 */
	public static String postToUrl(String url, String content){
		return postToUrl(url,content,"application/x-www-form-urlencoded","UTF-8");
	}
	
	/**
	 * post方式请求数据（入参为键值对用此方法）
	 * @param url 请求地址
	 * @param content 请求内容，数据格式为json
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("unchecked")
	public static String nameAndValuePairPost(String url, String content) throws UnsupportedEncodingException{
		//检查参数
		if(null == httpclient){
			throw new RuntimeException("httpclient not init.");
		}
		if(null == url || url.trim().length()==0){
			throw new RuntimeException("url is blank.");
		}
		HttpPost httpPost = new HttpPost(url);
		JSONObject json = JSONObject.fromObject(content);
		ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		Iterator<String> it = json.keys();
		while(it.hasNext()){
			String key = it.next();
			pairs.add(new BasicNameValuePair(key , json.optString(key)));
		}
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(pairs,"utf-8");
		httpPost.setEntity(formEntity);
		httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE .0; Windows NT 6.1; Trident/4.0; SLCC2;)");
        httpPost.addHeader("Accept-Encoding", "*");
		//设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
				.setSocketTimeout(READ_TIMEOUT)
				.setConnectionRequestTimeout(REQUEST_TIMEOUT)
				.build();
		httpPost.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String html = EntityUtils.toString(entity,"utf-8");
			EntityUtils.consume(entity);
			return html;
		} catch (ClientProtocolException e) {
			LOG.info("",e);
		} catch (IOException e) {
			LOG.info("",e);
		} finally {
			if(null!=response){
				try {
					response.close();
				} catch (IOException e) {
					LOG.info("",e);
				}
			}
			httpPost.releaseConnection();
		}
		
		return "";
	}
	
	
	
	public static String postToUrl(String url, String content, String contentType, String charset){
		//检查参数
		if(null == httpclient){
			throw new RuntimeException("httpclient not init.");
		}
		if(null == url || url.trim().length()==0){
			throw new RuntimeException("url is blank.");
		}
		HttpPost httpPost = new HttpPost(url);
		//设置请求内容
		ContentType type = ContentType.create(contentType,Charset.forName(charset));
		StringEntity reqEntity = new StringEntity(content,type);
		httpPost.setEntity(reqEntity);
		httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE .0; Windows NT 6.1; Trident/4.0; SLCC2;)");
        httpPost.addHeader("Accept-Encoding", "*");
		//设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
				.setSocketTimeout(READ_TIMEOUT)
				.setConnectionRequestTimeout(REQUEST_TIMEOUT)
				.build();
		httpPost.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		try {
			//执行请求
			response = httpclient.execute(httpPost,HttpClientContext.create());
			//获取结果转换成String
			HttpEntity entity = response.getEntity();
			String html= EntityUtils.toString(entity,"utf-8");
			//消费内容
			EntityUtils.consume(entity);
			return html;
		} catch (ClientProtocolException e) {
			LOG.info("",e);
		} catch (IOException e) {
			LOG.info("",e);
		}finally {
			if(null != response){
				try {
					response.close();
				} catch (IOException e) {
					LOG.info("",e);
				}
			}
			httpPost.releaseConnection();
		}
		return "";
	}
}
