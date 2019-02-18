package com.ucmed.web.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ucmed.biz.api.admin.FunctionConfigApi;
import com.ucmed.model.utils.JsonUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 配置管理控制器
 * @author ucmed
 *
 */
@Controller
@RequestMapping(value="/admin/config")
public class FunctionManageController {
	private static final Logger LOG = LoggerFactory.getLogger(FunctionManageController.class);
	@Autowired
	private FunctionConfigApi functionConfigApi;
	/**
	 * 功能管理
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String funcListManage() {
		return "admin/htmls/funcManagement";
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
		JSONObject res = functionConfigApi.execute(params);
		JSONArray recordList = res.optJSONArray("list");
		JSONObject pageConfig = new JSONObject();
		double totalCount = res.optInt("totalCount");
		double pageSize = params.optDouble("pageSize");
		double temp = totalCount/pageSize;
		int totalPages = (int) Math.ceil(temp);
		pageConfig.put("totalPages", totalPages);
		obj.put("recordList", recordList);
		obj.put("pageConfig", pageConfig);
		return obj.toString();
	}
	/**
	 * 修改功能配置，post方式
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST, value="/edit")
	public String funcConfigEditPost(HttpServletRequest request, 
			HttpServletResponse response, ModelMap map){
		JSONObject params = JsonUtils.parseRequestToJsonobject(request);
		params.put("type", "edit");
		JSONObject res = functionConfigApi.execute(params);
		LOG.info("Someone has changed config--p_adress is:"+request.getRemoteAddr()+"--result is:"+res.toString());
		return res.toString();
	}
}
