package com.ucmed.web.controller.wechat;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ucmed.model.utils.HnsrmyyApi;
import com.ucmed.model.utils.HnsrmyyUtil;
import com.ucmed.model.utils.RedisUtils;

/**
 * 微信小程序当日挂号
 * 
 * @author tangbin
 *
 */
@Controller
@RequestMapping("weixin/appletOrderCur.htm")
public class WeixinAppletOrderCurController {
	private static final Logger LOG = LoggerFactory
			.getLogger(WeixinAppletOrderController.class);

	@Autowired
	private RedisUtils redisUtils;

	// 获取所有的科室 包括一级科室 以及二级科室
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, params = "action=deptlist")
	public String deptClassList(HttpServletRequest request, ModelMap map) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String hospitalId = request.getParameter("hospital_id");
			String type = request.getParameter("type").trim();
			JSONObject params = new JSONObject();
			params.put("hospital_id", hospitalId);
			params.put("page", 1);
			params.put("size", 1000);
			params.put("type", type);// 0挂号，1预约
			JSONObject apires = HnsrmyyApi.getInstance().requestActionParams(
					"1966".equals(hospitalId) ? "api.hnsrmyy.get.all.dept"
							: "api.hnsrmyy.unite.order.dept", params, null);
			JSONObject obj = apires.optJSONObject("return_params");
			if (obj.optInt("ret_code") == 0) {
				JSONArray list = obj.optJSONArray("depts");
				res.put("list", list);
				code = 0;
				info = "获取科室成功";
			} else {
				info = obj.optString("ret_info");
			}
		} catch (Exception e) {
			LOG.error("获取科室失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "获取科室失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);

		return res.toString();
	}

	// 根据科室代码获取号源信息 多医生号源
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, params = "action=getscheduleByDept")
	public String getscheduleByDept(HttpServletRequest request, ModelMap map) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String hospitalId = request.getParameter("hospital_id");
			String dept_code = request.getParameter("deptNo");
			String deptName = request.getParameter("deptName");
			String time_range = request.getParameter("timeRange");
			String isJz = request.getParameter("is_jz");
			String openid = request.getParameter("openid");
			JSONObject params = new JSONObject();
			params.put("skuNumber", openid);
			params.put("dept_name", deptName);
			params.put("dept_code", dept_code);
			params.put("time_range", time_range);
			params.put("is_jz", isJz);
			params.put("hospital_id", hospitalId);
			JSONObject apires = HnsrmyyApi.getInstance().requestActionParams(
					"1966".equals(hospitalId) ? "api.hnsrmyy.cur.get.schedule"
							: "api.hnsrmyy.unite.cur.order.schedule", params,
					null);
			JSONObject obj = apires.optJSONObject("return_params");
			if (obj.optInt("ret_code") == 0) {
				JSONArray scheduleArray = obj.optJSONArray("schedule");
				res.put("list", scheduleArray);
				code = 0;
				info = "获取排班列表成功";
			} else {
				info = obj.optString("ret_info");
			}
			res.put("dept_name", deptName);
			res.put("hospital_id", hospitalId);
		} catch (Exception e) {
			LOG.error("获取排班列表失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "获取排班列表失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}

	// 进入挂号界面
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, params = "action=toOrderCur")
	public String toOrderPage(HttpServletRequest request, ModelMap map) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String hospitalId = request.getParameter("hospital_id");
			String sche_id = request.getParameter("sche_id");
			String visit_cost = request.getParameter("visit_cost");
			String time_range = request.getParameter("time_range");
			String deptName = request.getParameter("deptName");
			String docName = request.getParameter("docName");
			String doc_pos = request.getParameter("doc_pos");
			String openid = request.getParameter("openid");
			res.put("sche_id", sche_id);
			res.put("time_range", time_range);
			res.put("visit_cost", visit_cost);
			res.put("deptName", deptName);
			res.put("docName", docName);
			res.put("doc_pos", doc_pos);
			Calendar c = new GregorianCalendar();
			int year = c.get(Calendar.YEAR);// 获取年份
			int month = c.get(Calendar.MONTH);// 获取月份
			int day = c.get(Calendar.DATE);// 获取日期
			String clinic_date = year + "-";
			clinic_date += (month + 1) + "-";
			clinic_date += day;
			res.put("clinic_date", clinic_date);
			res.put("hospital_id", hospitalId);
			JSONObject params = new JSONObject();
			String type = "2";
			params.put("type", type);
			params.put("identify", openid);
			params.put("hospital_id", hospitalId);
//			JSONObject apires = HnsrmyyApi.getInstance().requestActionParams(
//							"1966".equals(hospitalId) ? "api.hnsrmyy.ali.account.card.list"
//									: "api.hnsrmyy.unite.card.list", params,null);
			String aString = "{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"count\":2,\"result\":[{\"id\":1522348,\"name\":\"孙沐辰\",\"card\":\"430522201501170267\",\"idCard\":\"430522201501170267\"},{\"id\":1522348,\"name\":\"王沐辰\",\"card\":\"430522222201170267\",\"idCard\":\"430522201501170267\"},{\"id\":1522173,\"name\":\"孙逸琳\",\"card\":\"430522198701120024\",\"idCard\":\"430522198701120024\"},{\"id\":1522173,\"name\":\"孙一琳\",\"card\":\"430522198501120024\",\"idCard\":\"430522198701120024\"},{\"id\":1522173,\"name\":\"孙二琳\",\"card\":\"430522198901120024\",\"idCard\":\"430522198701120024\"},{\"id\":1522173,\"name\":\"孙三琳\",\"card\":\"430522198801120024\",\"idCard\":\"430522198701120024\"}]}}";
			JSONObject apires = JSONObject.fromObject(aString);
			JSONObject obj = apires.optJSONObject("return_params");
			if (obj.optInt("ret_code") == 0) {
				JSONArray result = obj.optJSONArray("result");
				res.put("list", result);
				code = 0;
				info = "获取就诊卡列表成功";
			} else {
				info = obj.optString("ret_info");
			}
		} catch (Exception e) {
			LOG.error("获取就诊卡列表失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "获取就诊卡列表失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}

	// 发送挂号请求
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, params = "action=orderCur")
	public String orderPage(HttpServletRequest request, ModelMap map) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String formData = request.getParameter("formData");
			JSONObject object = JSONObject.fromObject(formData);
			String hospitalId = object.optString("hospital_id");
			String sche_id = object.optString("sche_id");
			String card = object.optString("card");
			String trade_type = object.optString("trade_type");
			String name = object.optString("name");
			String openid = request.getParameter("openid");
			JSONObject params = new JSONObject();
			params.put("skuNumber", openid);
			params.put("sche_id", sche_id);
			params.put("card", card);
			params.put("trade_type", trade_type);
			params.put("name", name);
			params.put("hospital_id", hospitalId);
			// JSONObject apires = HnsrmyyApi.getInstance().requestActionParams(
			// "1966".equals(hospitalId) ? "api.hnsrmyy.cur.order.pay"
			// : "api.hnsrmyy.unite.cur.order.pay", params, null);
			String aString = "{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"order_cur_id\":566541}}";
			JSONObject apires = JSONObject.fromObject(aString);
			JSONObject obj = apires.optJSONObject("return_params");
			if (obj.optInt("ret_code") == 0) {
				int order_cur_id = obj.optInt("order_cur_id");
				// 如果保存挂号信息成功 那么保存挂号的id 下一个页面跳到支付页面
				res.put("payId", order_cur_id);
				info = "挂号成功";
				code = 0;
			} else {
				info = obj.optString("ret_info");
			}
			res.put("hospital_id", hospitalId);
		} catch (Exception e) {
			LOG.error("挂号失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "挂号失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}
}
