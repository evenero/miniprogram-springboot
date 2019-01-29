package com.ucmed.model.exception;

public class BussinessException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9211687033666759226L;
	private final String errorCode;
	private final String errorMsg;
	
	/**
	 * 异常构造方法
	 * @param errorCode 异常代码
	 * @param errorMsg 异常信息
	 */
	public BussinessException(String errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
}
