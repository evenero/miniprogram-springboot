package com.ucmed.model.utils;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

public class JsonUtils {
	/**
	 * 转换request为jsonobject
	 * 
	 * @param request
	 * @return
	 */
	public static JSONObject parseRequestToJsonobject(HttpServletRequest request) {
		JSONObject obj = new JSONObject();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String name = (String) parameterNames.nextElement();
			Object value = request.getParameter(name);
			obj.put(name, value);
		}
		return obj;
	}
}
