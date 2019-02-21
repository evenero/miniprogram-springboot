package com.ucmed.web.controller.admin;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucmed.biz.api.admin.UserManageApi;
import com.ucmed.model.utils.JsonUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
	 * 初始化gson
	 */
	private static Gson GSON = new GsonBuilder()
			.serializeNulls() //当字段值为空或null时，依然对该字段进行转换
			.setDateFormat("yyyy-MM-dd HH:mm:ss") //时间转化为特定格式
			.disableHtmlEscaping() //防止特殊字符出现乱码
			.create();
	
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
		return "admin/htmls/userManage/index";
	}
	/**
	 * 获取功能配置，post方式
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST)
	public String funcListManagePost(HttpServletRequest request, 
			HttpServletResponse response, ModelMap map){
		JSONObject obj = new JSONObject();
		JSONObject params = JsonUtils.parseRequestToJsonobject(request);
		JSONObject res = userManageApi.execute(params);
		JSONArray recordList = res.optJSONArray("list");
		JSONObject pageConfig = new JSONObject();
		double totalCount = res.optInt("totalCount");
		double pageSize = params.optDouble("pageSize");
		double temp = totalCount/pageSize;
		int totalPages = (int) Math.ceil(temp);
		pageConfig.put("totalPages", totalPages);
		obj.put("userList", recordList);
		obj.put("pageConfig", pageConfig);
		return obj.toString();
	}
	/**
	 *用户编辑主界面
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequiresRoles({"root"})
	@RequestMapping(method=RequestMethod.GET,value="/edit/{username}")
	public String Edit(HttpServletRequest request, 
			HttpServletResponse response, ModelMap map,@PathVariable("username") String username){
		JSONObject params = new JSONObject();
		params.put("type", "search");
		params.put("username", username);
		JSONObject user = userManageApi.execute(params);
		map.putAll(user);
		return "admin/htmls/userManage/edit";
	}
}
