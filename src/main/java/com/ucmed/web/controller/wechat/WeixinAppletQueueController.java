package com.ucmed.web.controller.wechat;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ucmed.model.utils.HnsrmyyUtil;

/**
 * 微信小程序叫号
 * @author tangbin
 *
 */
@Controller
@RequestMapping("weixin/appletQueue.htm")
public class WeixinAppletQueueController {
	private static final Logger LOG = LoggerFactory
			.getLogger(WeixinAppletQueueController.class);

	// 获取某个人的所有就诊号列表 同时拿到所有的科室列表 (用于查看其他科室的当前叫号)
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public String queueList(HttpServletRequest request, ModelMap map) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String openid = request.getParameter("openid");
			JSONObject params = new JSONObject();
			params.put("type", "4");
			params.put("indentify", openid);
//			JSONObject apires = HnsrmyyApi.getInstance().requestActionParams(
//					"api.hnsrmyy.get.call.number", params, null);
			JSONObject apires = JSONObject.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"result\":[{\"deptCode\":\"1085\",\"deptName\":\"门诊儿科\"},{\"deptCode\":\"1086\",\"deptName\":\"儿科\"}]}}");
			JSONObject obj = apires.optJSONObject("return_params");
			if (obj.optInt("ret_code") == 0) {
				// 获取与我相关的科室列表
				JSONArray result = obj.optJSONArray("result");
				res.put("result", result);
				code = 0;
				info = "获取与我相关的科室列表成功";
			} else {
				info = obj.optString("ret_info");
			}
		} catch (Exception e) {
			LOG.error("获取与我相关的科室列表失败："+HnsrmyyUtil.printErrInfo(e));
			info = "获取与我相关的科室列表失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, params = "action=getMydeptQueue")
	public String queueMyInfo(HttpServletRequest request, ModelMap map) {
		JSONObject res = new JSONObject();
		String info = "";
		try {
			String openid = request.getParameter("openid");
			String dept_code = request.getParameter("deptCode");
			JSONObject params = new JSONObject();
			params.put("dept_code", dept_code);
			params.put("count", 1);
//			JSONObject res = HnsrmyyApi.getInstance().requestActionParams(
//					"api.hnsrmyy.sche.list.detail", params, null);
			JSONObject apires = JSONObject.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"total_count\":1,\"results\":[{\"dept_name\":\"门诊儿科\",\"seq\":\"023\"},{\"dept_name\":\"门诊儿科二\",\"seq\":\"024\"}]}}");
			JSONObject obj = apires.optJSONObject("return_params");
			if(obj.optInt("ret_code") == 0) {
				JSONArray list = obj.optJSONArray("results");
				res.put("queuelist", list);
				res.put("code", 0);
			} else {
				String ret_info = obj.optString("ret_info");
				res.put("info", ret_info);
				res.put("code", 1);
			}
			JSONObject params2 = new JSONObject();
			params2.put("type", "4");
			params2.put("indentify", openid);
			params2.put("deptCode", dept_code);
//			JSONObject res2 = HnsrmyyApi.getInstance().requestActionParams(
//					"api.hnsrmyy.get.call.number.by.dept", params2, null);
			JSONObject res2 = JSONObject.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"result\":[{\"card\":\"20178202466\",\"name\":\"王奕州\",\"orderList\":[{\"deptCode\":\"1085\",\"deptName\":\"门诊儿科\",\"name\":\"王奕州\",\"queueNo\":\"8\"},{\"deptCode\":\"1086\",\"deptName\":\"儿科\",\"name\":\"张奕州\",\"queueNo\":\"18\"}],\"clinicList\":[{\"deptCode\":\"1085\",\"deptName\":\"门诊儿科\",\"name\":\"王奕州\",\"clinicNo\":\"8\"},{\"deptCode\":\"1086\",\"deptName\":\"门诊儿科二\",\"name\":\"李奕州\",\"clinicNo\":\"10\"}]}]}}");
			JSONObject obj2 = res2.optJSONObject("return_params");
			if(obj2.optInt("ret_code") == 0) {
				// 获取与我相关的科室列表
				JSONArray result = obj2.optJSONArray("result");

				String str = "";
				for(int i = 0; i < result.size(); i++) {
					JSONObject objresult = (JSONObject) result.get(i);

					JSONArray orderList = objresult.optJSONArray("orderList");
					JSONArray clinicList = objresult.optJSONArray("clinicList");
					if(orderList.size() > 0) {
						res.put("orderList", orderList);
						for(int j = 0; j < orderList.size(); j++) {
							JSONObject orderModel = (JSONObject) orderList.get(j);
							str += "<view class='jiaohaoxx2'>";
							str += "<view>" + orderModel.optString("name") + "</view><view>您的预约就诊序号为:";
							str += orderModel.optString("queueNo") + "号</view></view>";
						}
					}
					if(clinicList.size() > 0) {
						res.put("clinicList", clinicList);
						for(int j = 0; j < clinicList.size(); j++) {
							JSONObject clinicModel = (JSONObject) clinicList.get(j);
							str += "<view class='jiaohaoxx2'>";
							str += "<view>" + clinicModel.optString("name")
									+ "</view><view>您的挂号就诊序号为:";
							str += clinicModel.optString("clinicNo") + "号</view></view>";
						}
					}
				}
				res.put("result", str);
				res.put("code2", 0);
			} else {
				String ret_info = obj.optString("ret_info");
				res.put("info2", ret_info);
				res.put("code2", 1);
			}
		} catch (Exception e) {
			LOG.error("获取叫号信息失败："+HnsrmyyUtil.printErrInfo(e));
			info = "获取叫号信息失败";
		}
		res.put("ret_info", info);
		return res.toString();
	}
}
