package com.ucmed.web.controller.wechat;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.ucmed.model.utils.*;

/**
 * 微信小程序预约挂号
 * 
 * @author tangbin
 *
 */
@Controller
@RequestMapping("weixin/appletOrder.htm")
public class WeixinAppletOrderController {
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
			String type = request.getParameter("type");
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
			String openid = request.getParameter("openid");
			JSONObject params = new JSONObject();
			params.put("dept_code", dept_code);
			params.put("hospital_id", hospitalId);
			params.put("skuNumber", openid);
			JSONObject apires = HnsrmyyApi
					.getInstance()
					.requestActionParams(
							"1966".equals(hospitalId) ? "api.hnsrmyy.order.get.schedule"
									: "api.hnsrmyy.unite.order.get.schedule",
							params, null);
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
		} catch (Exception e) {
			LOG.error("获取排班列表失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "获取排班列表失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);

		return res.toString();
	}

	// 查看号源详细 分为上午下午的
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, params = "action=getscheduleInfo")
	public String getscheduleInfo(HttpServletRequest request, ModelMap map) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String sche_id = request.getParameter("sche_id");
			String visit_cost = request.getParameter("visit_cost");
			String doc_pos = request.getParameter("doc_pos");
			String clinic_date = request.getParameter("clinic_date");
			String clinic_week = request.getParameter("clinic_week");
			String deptName = request.getParameter("deptName");
			String docName = request.getParameter("docName");
			String openid = request.getParameter("openid");
			String hospitalId = request.getParameter("hospital_id");
			JSONObject object = new JSONObject();
			object.put("deptName", deptName);
			object.put("docName", docName);
			object.put("clinic_date", clinic_date);
			object.put("clinic_week", clinic_week);
			object.put("sche_id", sche_id);
			object.put("doc_pos", doc_pos);
			object.put("visit_cost", visit_cost);
			object.put("doc_pos", doc_pos);
			redisUtils.set(Constants.HAOYUAN_CACHE + openid, object.toString(),
					30L, TimeUnit.MINUTES);
			JSONObject params = new JSONObject();
			params.put("sche_id", sche_id);
			params.put("skuNumber", openid);
			JSONObject apires = HnsrmyyApi
					.getInstance()
					.requestActionParams(
							"1966".equals(hospitalId) ? "api.hnsrmyy.order.get.schedule.number"
									: "api.hnsrmyy.unite.order.get.schedule.number",
							params, null);
			JSONObject obj = apires.optJSONObject("return_params");
			if (obj.optInt("ret_code") == 0) {
				JSONObject numbers = obj.getJSONObject("numbers");
				res.put("sche_id", numbers.optString("sche_id"));
				res.put("time_range", numbers.optString("time_range"));
				JSONArray array = numbers.optJSONArray("number");
				res.put("list", array);
				code = 0;
				info = "获取号源详情成功";
			} else {
				info = obj.optString("ret_info");
			}
			res.put("doc_pos", doc_pos);
			res.put("docName", docName);
			res.put("deptName", deptName);
			res.put("clinic_date", clinic_date);
			res.put("clinic_week", clinic_week);
			res.put("visit_cost", visit_cost);
			res.put("hospital_id", hospitalId);
		} catch (Exception e) {
			LOG.error("获取号源详情失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "获取号源详情失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);

		return res.toString();
	}

	// 进入预约界面
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, params = "action=toOrder")
	public String toOrderPage(HttpServletRequest request, ModelMap map) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String time_slot = request.getParameter("time_slot");
			String number_id = request.getParameter("number_id");
			String time_range = request.getParameter("time_range");
			String openid = request.getParameter("openid");
			String hospitalId = request.getParameter("hospital_id");
			Object numObject = redisUtils.get(Constants.HAOYUAN_CACHE + openid);
			if (numObject == null) {
				res.put("ret_code", code);
				res.put("ret_info", "请重新获取号源信息");
				return res.toString();
			}
			JSONObject object = JSONObject.fromObject(numObject);
			/* session 中获取数据 */
			String clinic_date = object.optString("clinic_date");
			String clinic_week = object.optString("clinic_week");
			String deptName = object.optString("deptName");
			String docName = object.optString("docName");
			String visit_cost = object.optString("visit_cost");
			String doc_pos = object.optString("doc_pos");
			String sche_id = object.optString("sche_id");

			JSONObject params = new JSONObject();
			String type = "2";
			params.put("type", type);
			params.put("identify", openid);
			params.put("hospital_id", hospitalId);
			// JSONObject apires = HnsrmyyApi
			// .getInstance().requestActionParams(
			// "1966".equals(hospitalId)
			// ?"api.hnsrmyy.ali.account.card.list":"api.hnsrmyy.unite.card.list",
			// params, null);
			String aString = "{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"count\":2,\"result\":[{\"id\":1522348,\"name\":\"孙沐辰\",\"card\":\"430522201501170267\",\"idCard\":\"430522201501170267\"},{\"id\":1522348,\"name\":\"王沐辰\",\"card\":\"430522222201170267\",\"idCard\":\"430522201501170267\"},{\"id\":1522173,\"name\":\"孙逸琳\",\"card\":\"430522198701120024\",\"idCard\":\"430522198701120024\"}]}}";
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
			res.put("sche_id", sche_id);
			res.put("time_slot", time_slot);
			res.put("number_id", number_id);
			res.put("time_range", time_range);
			res.put("visit_cost", visit_cost);
			res.put("clinic_date", clinic_date);
			res.put("clinic_week", clinic_week);
			res.put("deptName", deptName);
			res.put("docName", docName);
			res.put("doc_pos", doc_pos);
			res.put("hospital_id", hospitalId);
		} catch (Exception e) {
			LOG.error("获取就诊卡列表失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "获取就诊卡列表失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);

		return res.toString();
	}

	// 进入预约界面
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, params = "action=order")
	public String orderPage(HttpServletRequest request, ModelMap map) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String formData = request.getParameter("formData");
			JSONObject object = JSONObject.fromObject(formData);
			String hospitalId = object.optString("hospital_id");
			String sche_id = object.optString("sche_id");
			String time_slot = object.optString("time_slot");
			String number_id = object.optString("number_id");
			String clinic_date = object.optString("clinic_date");
			String card = object.optString("card");
			String name = object.optString("name");
			String remark = object.optString("remark");
			String openid = request.getParameter("openid");
			String formId = request.getParameter("formId");
			JSONObject params = new JSONObject();
			params.put("sche_id", sche_id);
			params.put("time_slot", time_slot);
			params.put("number_id", number_id);
			params.put("clinic_date", clinic_date);
			params.put("card", card);
			params.put("name", name);
			params.put("trade_type", "2");// 预约类型 1 支付宝 2微信 3app
			params.put("remark", remark);
			params.put("hospital_id", hospitalId);
			params.put("skuNumber", openid);
			params.put("form_id", formId);
			// JSONObject apires = HnsrmyyApi
			// .getInstance().requestActionParams(
			// "1966".equals(hospitalId)
			// ?"api.hnsrmyy.order.order":"api.hnsrmyy.unite.order.order",
			// params, null);
			// 测试数据
			String aString = "{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"result\":{\"id\":\"1677423\",\"name\":\"孙沐辰\",\"dept_name\":\"小儿哮喘门诊\",\"doctor_name\":\"李云\",\"clinic_date\":\"2018-11-21\",\"time_slot\":\"14:50-15:00\",\"time_range\":\"下午\",\"card\":\"430522201501170267\",\"phone\":\"18573922277\",\"rese_id\":\"2719423\",\"his_id\":\"4907026\",\"pass_word\":\"160312\",\"balance\":\"0.00\",\"own_cost\":\"70.00\"}}}";
			JSONObject apires = JSONObject.fromObject(aString);
			JSONObject obj = apires.optJSONObject("return_params");
			if (obj.optInt("ret_code") == 0) {
				JSONObject result = obj.getJSONObject("result");
				res.put("result", result);
				code = 0;
				info = "提交预约成功";
			} else {
				info = obj.optString("ret_info");
			}
			res.put("hospital_id", hospitalId);
		} catch (Exception e) {
			LOG.error("提交预约失败" + HnsrmyyUtil.printErrInfo(e));
			info = "提交预约失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}
	

	/**
	 * 获取预约挂号详情
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, params = "action=orderInfo")
	public String orderInfo(HttpServletRequest request, ModelMap map) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String hospitalId = request.getParameter("hospital_id");
			String id = request.getParameter("orderId");
			String type = request.getParameter("orderType");
			JSONObject params = new JSONObject();
			params.put("id", id);
			params.put("type", type);
			params.put("hospital_id", hospitalId);
//			JSONObject apires = HnsrmyyApi.getInstance().requestActionParams(
//					"api.hnsrmyy.unite.record.detail", params, null);
			JSONObject apires = JSONObject.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"result\":{\"id\":1657548,\"dept_name\":\"门诊儿科\",\"doctor_name\":\"钟礼立\",\"clinic_date\":\"2019-12-29\",\"time_desc\":\"上午\",\"time_slot\":\"09:24-09:36\",\"patient_name\":\"邱诚浩\",\"phone\":\"18008422843\",\"card\":\"431382201404190270\",\"fee\":\"70.0\",\"order_status\":\"4\",\"pay_status\":\"1\",\"doctor_desc\":\"名老专家\",\"create_time\":\"2018-11-07 11:15\",\"pass_word\":\"164833\",\"addr\":\"门诊1楼\",\"clinic_no\":\"8\",\"address\":\"门诊1楼\",\"hospital_seq\":\"14056819\"}}}");
			JSONObject obj = apires.optJSONObject("return_params");
			if (obj.optInt("ret_code") == 0) {
				JSONObject result = obj.optJSONObject("result");
				res.put("order", result);
				String clinicDate = result.optString("clinic_date");
				String curDate = DateUtil.getyyyy_MM_dd(new Date());
				String orderStatus = result.optString("order_status");
				String timeSlot = result.optString("time_slot","0");
				String timeDesc = result.optString("time_desc");
				if ("01".equals(type) || "11".equals(type)) {
					if (curDate.compareTo(clinicDate) < 0
							&& "4".equals(orderStatus)) {
						res.put("is_cancel", 1);
					}
				}else if ("02".equals(type) || "12".equals(type)) {// add 2018-12-25 by tangbin--新增当日挂号在线退号
					// orderStatus 0未处理，1挂号成功 2挂号失败4取消挂号
					if (curDate.equals(clinicDate) && "1".equals(orderStatus)) {
						if ("0".equals(timeSlot)) {//急诊挂号展示退号按钮
							res.put("is_cancel", 1);
						}else {
							if ("上午".equals(timeDesc)) {
								if(DateUtil.isInEndZone(DateUtil.getLong("12:20"), DateUtil.getCurrentTimeForHHmm())){
									res.put("is_cancel", 1);
								}
							}else if("下午".equals(timeDesc)){
								if(DateUtil.isInZone(DateUtil.getLong("12:20"),DateUtil.getLong("18:30"), DateUtil.getCurrentTimeForHHmm())){
									res.put("is_cancel", 1);
								}
							}
						}
					}
				}
				code = 0;
				info = "获取预约挂号详情成功";
			}
			res.put("type", type);
			res.put("hospital_id", hospitalId);
		} catch (Exception e) {
			LOG.error("获取挂号详情失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "获取挂号详情失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}

	/**
	 * 取消预约
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, params = "action=cancelOrder")
	public String cancelOrder(HttpServletRequest request, ModelMap map) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String orderId = request.getParameter("orderId");
			String hospitalId = request.getParameter("hospital_id");
			JSONObject params = new JSONObject();
			params.put("order_id", orderId);
			params.put("hospital_id", hospitalId);
			JSONObject apires = HnsrmyyApi.getInstance().requestActionParams(
					"api.hnsrmyy.unite.order.order.cancel", params, null);
			JSONObject obj = apires.optJSONObject("return_params");
			if (obj.optInt("ret_code") == 0) {
				code = 0;
				info = "取消预约成功！";
			} else {
				info = "取消预约失败！";
			}
		} catch (Exception e) {
			LOG.error("取消预约失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "取消预约失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}
	
	/**
	 * 取消预约退费
	 * @param request
	 * @param map
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, params = "action=cancelPayOrder")
	public String cancelOrder(HttpServletRequest request, ModelMap map,
			HttpServletResponse response) throws IOException {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String type = request.getParameter("ordertype");
			String id = request.getParameter("id");
			String card = request.getParameter("card");
			String clinicDate = request.getParameter("clinic_date");
			String hospitalId = request.getParameter("hospital_id");
			JSONObject params = new JSONObject();
			params.put("type", type);
			params.put("pay_id", id);
			params.put("clinic_date", clinicDate);
			params.put("card", card);
			params.put("hospital_id", hospitalId);
//			JSONObject apires = HnsrmyyApi.getInstance().requestActionParams(
//					"api.hnsrmyy.refund.order", params, null);
			JSONObject apires = JSONObject.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"ret_info\":\"取消退费成功\"}}");
			JSONObject obj = apires.optJSONObject("return_params");
			code = obj.optInt("ret_code");
			info = obj.optString("ret_info");
		} catch (Exception e) {
			LOG.error("取消预约失败："+HnsrmyyUtil.printErrInfo(e));
			info = "取消预约失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}

}
