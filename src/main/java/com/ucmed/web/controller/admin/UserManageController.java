package com.ucmed.web.controller.admin;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ucmed.biz.api.admin.UserManageApi;

/**
 * 用户管理控制器
 * @author ucmed
 *
 */
@Controller
@RequestMapping(value="/admin/user")
public class UserManageController {
	private static final Logger LOG = LoggerFactory.getLogger(UserManageController.class);
	@Autowired
	private UserManageApi userManageApi;
	
	/**
	 *用户管理主界面
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String Login(HttpServletRequest request, 
			HttpServletResponse response, ModelMap map){
		String uuid = UUID.randomUUID().toString();
		map.put("uuid", uuid);
		return "admin/htmls/userManagement";
	}
}
