package com.ucmed.shiro.utils;

//import com.ucmed.shiro.model.bean.pojo.User;

import net.sf.json.JSONObject;

public class ShiroUtils {
	@SuppressWarnings("unchecked")
	public static final <T> T convertObjectToBean(Object obj,Class<T> clazz) {
		Object temp = new Object();
//		if(obj instanceof Class) {
//			temp = obj;
//		} else {
			temp = JSONObject.toBean(JSONObject.fromObject(obj), clazz);
//		}
		return (T) temp;
	}
	
//	public static void main(String[] args) {
//		User obj = new User();
//		obj.setExtend("11111111111");
//		System.err.println(convertObjectToBean(obj, User.class));
//	}
}
