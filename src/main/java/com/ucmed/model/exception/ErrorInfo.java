package com.ucmed.model.exception;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="errorinfo")
public class ErrorInfo {
	/**
	 * 成功信息
	 */
	public String successInfo;
	/**
	 * 失败信息
	 */
	public String failInfo;
	/**
	 * 空值信息
	 */
	public String nullInfo;
//	/**
//	 * 通过错误代码获取错误信息
//	 * @param errorCode
//	 * @return
//	 */
//	public String getErrorInfo(String errorCode) {
//		switch (errorCode) {
//		case "200":
//			return successInfo;
//		case "500":
//			return failInfo;
//		case "null":
//			return nullInfo;
//		default:
//			return null;
//		}
//	}
	
	public String getSuccessInfo() {
		return successInfo;
	}
	public void setSuccessInfo(String successInfo) {
		this.successInfo = successInfo;
	}
	public String getFailInfo() {
		return failInfo;
	}
	public void setFailInfo(String failInfo) {
		this.failInfo = failInfo;
	}
	public String getNullInfo() {
		return nullInfo;
	}
	public void setNullInfo(String nullInfo) {
		this.nullInfo = nullInfo;
	}
	@Override
	public String toString() {
		return "ErrorInfo [successInfo=" + successInfo + ", failInfo=" + failInfo + ", nullInfo=" + nullInfo + "]";
	}
}
