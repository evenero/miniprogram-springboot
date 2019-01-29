package com.ucmed.web.controller.wechat;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * 微信小程序门诊缴费
 * 
 * @author tangbin
 *
 */
@Controller
@RequestMapping("weixin/appletObilling.htm")
public class WeixinAppletObillingController {
	private static final Logger LOG = LoggerFactory
			.getLogger(WeixinAppletObillingController.class);

	/**
	 * 查看账单的详情
	 * @param request
	 * @param map
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, params = "action=obillingInfo")
	public String obillingInfo(HttpServletRequest request, ModelMap map,
			HttpServletResponse response) throws IOException {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String hospitalId = request.getParameter("hospital_id");
			res.put("hospital_id", hospitalId);
			String isPay = request.getParameter("isPay");
			String card = request.getParameter("card");
			res.put("isPay", isPay);
			String registNo = request.getParameter("registNo");
			String deptName = request.getParameter("deptName");
			res.put("deptName", deptName);
			String time = request.getParameter("time");
			res.put("time", time);
			String date = request.getParameter("date");
			res.put("date", date);
			res.put("registNo", registNo);
			JSONObject  params = new JSONObject();
			params.put("regist_no", registNo);
			params.put("card", card);
			if ("1981".equals(hospitalId)) {
				Integer masterId = Integer.valueOf(request.getParameter("master_id"));
				params.put("hospital_id", hospitalId);
				params.put("master_id", masterId);
			}
//			JSONObject apires = HnsrmyyApi.getInstance().requestActionParams(
//					"1966".equals(hospitalId) ? "api.hnsrmyy.obilling.details"
//							: "api.hnsrmyy.unite.obilling.details", params,null);
			JSONObject apires = JSONObject.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"result\":{\"id\":2723386,\"fee\":\"468.80\",\"dept_name\":\"急诊儿科\",\"date\":\"2018-11-13\",\"range\":\"晚上\",\"details\":[{\"receipt_no\":\"35292267\",\"pres_detail_no\":\"69057587\",\"item_name\":\"电脑血糖监测\",\"exec_dep\":\"急诊儿科\",\"price\":\"10.00000\",\"num\":\"1.000\",\"days\":\"1\",\"receipt_sum\":\"10.00\",\"status\":\"0\",\"doct_id\":\"12621\",\"dept_name\":\"急诊儿科\",\"cate_name\":\"化验费\",\"cate_id\":\"10\",\"address\":\"儿科大楼一楼\"},{\"receipt_no\":\"35292268\",\"pres_detail_no\":\"69057588\",\"item_name\":\"【手足口病血清学实验】病毒血清学试验\",\"exec_dep\":\"检验科\",\"price\":\"20.00000\",\"num\":\"4.000\",\"days\":\"1\",\"receipt_sum\":\"80.00\",\"status\":\"0\",\"doct_id\":\"12621\",\"dept_name\":\"急诊儿科\",\"cate_name\":\"化验费\",\"cate_id\":\"10\",\"address\":\"门诊6楼\"},{\"receipt_no\":\"35292269\",\"pres_detail_no\":\"69057589\",\"item_name\":\"静脉采血\",\"exec_dep\":\"急诊儿科\",\"price\":\"4.00000\",\"num\":\"1.000\",\"days\":\"1\",\"receipt_sum\":\"4.00\",\"status\":\"0\",\"doct_id\":\"12621\",\"dept_name\":\"急诊儿科\",\"cate_name\":\"治疗\",\"cate_id\":\"07\",\"address\":\"儿科大楼一楼\"},{\"receipt_no\":\"35292269\",\"pres_detail_no\":\"69057590\",\"item_name\":\"真空采血管（美国BD）\",\"exec_dep\":\"急诊儿科\",\"price\":\"1.98000\",\"num\":\"1.000\",\"days\":\"1\",\"receipt_sum\":\"2.00\",\"status\":\"0\",\"doct_id\":\"12621\",\"dept_name\":\"急诊儿科\",\"cate_name\":\"材料费\",\"cate_id\":\"09\",\"address\":\"儿科大楼一楼\"},{\"receipt_no\":\"35292272\",\"pres_detail_no\":\"69057595\",\"item_name\":\"0.9%氯化钠注射液(软袋)\",\"exec_dep\":\"急诊药房\",\"price\":\"3.47000\",\"num\":\"2.000\",\"frequency\":\"Bid\",\"usage\":\"静滴\",\"days\":\"1\",\"receipt_sum\":\"6.90\",\"status\":\"0\",\"doct_id\":\"12621\",\"dept_name\":\"急诊儿科\",\"cate_name\":\"西药\",\"cate_id\":\"01\",\"address\":\"二住院楼1楼\"},{\"receipt_no\":\"35292272\",\"pres_detail_no\":\"69057596\",\"item_name\":\"头孢硫脒注射剂\",\"exec_dep\":\"急诊药房\",\"price\":\"43.79000\",\"num\":\"2.000\",\"frequency\":\"Bid\",\"usage\":\"静滴\",\"days\":\"1\",\"receipt_sum\":\"87.60\",\"status\":\"0\",\"doct_id\":\"12621\",\"dept_name\":\"急诊儿科\",\"cate_name\":\"西药\",\"cate_id\":\"01\",\"address\":\"二住院楼1楼\"},{\"receipt_no\":\"35292273\",\"pres_detail_no\":\"69057597\",\"item_name\":\"皮内注射\",\"exec_dep\":\"急诊儿科\",\"price\":\"4.00000\",\"num\":\"1.000\",\"days\":\"1\",\"receipt_sum\":\"4.00\",\"status\":\"0\",\"doct_id\":\"12621\",\"dept_name\":\"急诊儿科\",\"cate_name\":\"治疗\",\"cate_id\":\"07\",\"address\":\"儿科大楼一楼\"},{\"receipt_no\":\"35292277\",\"pres_detail_no\":\"69057612\",\"item_name\":\"5%葡萄糖注射液(软袋)\",\"exec_dep\":\"急诊药房\",\"price\":\"3.85000\",\"num\":\"1.000\",\"frequency\":\"Qd\",\"usage\":\"静滴\",\"days\":\"1\",\"receipt_sum\":\"3.90\",\"status\":\"0\",\"doct_id\":\"12621\",\"dept_name\":\"急诊儿科\",\"cate_name\":\"西药\",\"cate_id\":\"01\",\"address\":\"二住院楼1楼\"},{\"receipt_no\":\"35292277\",\"pres_detail_no\":\"69057613\",\"item_name\":\"病毒唑注射液\",\"exec_dep\":\"急诊药房\",\"price\":\"0.34000\",\"num\":\"2.000\",\"frequency\":\"Qd\",\"usage\":\"静滴\",\"days\":\"1\",\"receipt_sum\":\"0.70\",\"status\":\"0\",\"doct_id\":\"12621\",\"dept_name\":\"急诊儿科\",\"cate_name\":\"西药\",\"cate_id\":\"01\",\"address\":\"二住院楼1楼\"},{\"receipt_no\":\"35292278\",\"pres_detail_no\":\"69057614\",\"item_name\":\"【儿科留观费】急诊观察床位费（半日）\",\"exec_dep\":\"急诊儿科\",\"price\":\"8.00000\",\"num\":\"1.000\",\"days\":\"1\",\"receipt_sum\":\"8.00\",\"status\":\"0\",\"doct_id\":\"12621\",\"dept_name\":\"急诊儿科\",\"cate_name\":\"床位费\",\"cate_id\":\"38\",\"address\":\"儿科大楼一楼\"},{\"receipt_no\":\"35292278\",\"pres_detail_no\":\"69057615\",\"item_name\":\"【儿科留观费】小儿门诊静脉输液(输血)\",\"exec_dep\":\"急诊儿科\",\"price\":\"17.00000\",\"num\":\"1.000\",\"days\":\"1\",\"receipt_sum\":\"17.00\",\"status\":\"0\",\"doct_id\":\"12621\",\"dept_name\":\"急诊儿科\",\"cate_name\":\"治疗\",\"cate_id\":\"07\",\"address\":\"儿科大楼一楼\"},{\"receipt_no\":\"35292278\",\"pres_detail_no\":\"69057616\",\"item_name\":\"【儿科留观费】门急诊留观诊查费\",\"exec_dep\":\"急诊儿科\",\"price\":\"12.00000\",\"num\":\"1.000\",\"days\":\"1\",\"receipt_sum\":\"12.00\",\"status\":\"0\",\"doct_id\":\"12621\",\"dept_name\":\"急诊儿科\",\"cate_name\":\"诊查费\",\"cate_id\":\"06\",\"address\":\"儿科大楼一楼\"},{\"receipt_no\":\"35292278\",\"pres_detail_no\":\"69057617\",\"item_name\":\"【儿科留观费】陪人费\",\"exec_dep\":\"急诊儿科\",\"price\":\"2.00000\",\"num\":\"1.000\",\"days\":\"1\",\"receipt_sum\":\"2.00\",\"status\":\"0\",\"doct_id\":\"12621\",\"dept_name\":\"急诊儿科\",\"cate_name\":\"床位费\",\"cate_id\":\"38\",\"address\":\"儿科大楼一楼\"},{\"receipt_no\":\"35292278\",\"pres_detail_no\":\"69057618\",\"item_name\":\"【儿科留观费】小儿静脉输液（第二组起）\",\"exec_dep\":\"急诊儿科\",\"price\":\"2.00000\",\"num\":\"2.000\",\"days\":\"1\",\"receipt_sum\":\"4.00\",\"status\":\"0\",\"doct_id\":\"12621\",\"dept_name\":\"急诊儿科\",\"cate_name\":\"治疗\",\"cate_id\":\"07\",\"address\":\"儿科大楼一楼\"},{\"receipt_no\":\"35292278\",\"pres_detail_no\":\"69057619\",\"item_name\":\"静脉输液（第二组起）\",\"exec_dep\":\"急诊儿科\",\"price\":\"2.00000\",\"num\":\"1.000\",\"days\":\"1\",\"receipt_sum\":\"2.00\",\"status\":\"0\",\"doct_id\":\"12621\",\"dept_name\":\"急诊儿科\",\"cate_name\":\"治疗\",\"cate_id\":\"07\",\"address\":\"儿科大楼一楼\"},{\"receipt_no\":\"35292279\",\"pres_detail_no\":\"69057620\",\"item_name\":\"手足口病病毒抗体喷剂\",\"exec_dep\":\"急诊药房\",\"price\":\"90.16000\",\"num\":\"2.000\",\"frequency\":\"Tid\",\"usage\":\"外用\",\"days\":\"0\",\"receipt_sum\":\"180.30\",\"status\":\"0\",\"doct_id\":\"12621\",\"dept_name\":\"急诊儿科\",\"cate_name\":\"西药\",\"cate_id\":\"01\",\"address\":\"二住院楼1楼\"},{\"receipt_no\":\"35292280\",\"pres_detail_no\":\"69057621\",\"item_name\":\"蒲地蓝消炎口服液\",\"exec_dep\":\"急诊药房\",\"price\":\"44.42000\",\"num\":\"1.000\",\"frequency\":\"Bid\",\"usage\":\"口服\",\"days\":\"6\",\"receipt_sum\":\"44.40\",\"status\":\"0\",\"doct_id\":\"12621\",\"dept_name\":\"急诊儿科\",\"cate_name\":\"中成药\",\"cate_id\":\"02\",\"address\":\"二住院楼1楼\"}],\"details_total\":[{\"cate_name\":\"西药\",\"sum_fee\":\"279.4\"},{\"cate_name\":\"中成药\",\"sum_fee\":\"44.4\"},{\"cate_name\":\"诊查费\",\"sum_fee\":\"12\"},{\"cate_name\":\"治疗\",\"sum_fee\":\"31\"},{\"cate_name\":\"材料费\",\"sum_fee\":\"2\"},{\"cate_name\":\"化验费\",\"sum_fee\":\"90\"},{\"cate_name\":\"床位费\",\"sum_fee\":\"10\"}]}}}");
			JSONObject obj = apires.optJSONObject("return_params");
			if (obj.optInt("ret_code") == 0) {
				JSONObject result = obj.optJSONObject("result");
				// 总费用
				res.put("result", result);
				code = 0;
				info = "获取详细成功！";
			} else {
				info = obj.optString("ret_info");
			}
		} catch (Exception e) {
			LOG.error("获取详细失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "获取详细失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}

	/**
	 * 查看缴费记录详情
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, params = "action=recordDetail")
	public String examineInfo(HttpServletRequest request, ModelMap map) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String id = request.getParameter("id");
			String type = request.getParameter("orderType");
			JSONObject params = new JSONObject();
			params.put("id", Integer.parseInt(id));
			params.put("type", type);
			res.put("deptName", request.getParameter("deptName"));
			res.put("card", request.getParameter("card"));
			res.put("payTimes", request.getParameter("payTimes"));
//			JSONObject apires = HnsrmyyApi.getInstance().requestActionParams(
//					"api.hnsrmyy.pay.record.detail", params, null);
			JSONObject apires = JSONObject.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"hospital_seq\":\"14047201\",\"obillingList\":[{\"receipt_no\":\"35207639\",\"pres_detail_no\":\"68888104\",\"item_name\":\"【儿童胸部正位片（数字激光片）】数字化摄影(DR)\",\"exec_dep\":\"放射科\",\"price\":\"20.0\",\"num\":\"1.0\",\"days\":\"1\",\"receipt_sum\":\"20.0\",\"status\":\"0\",\"doct_id\":\"10403\",\"dept_name\":\"门诊儿科\",\"address\":\"照片在(第一住院楼3楼)       CT在(第二住院楼负一楼)       核磁共振在(儿科楼1楼)\"},{\"receipt_no\":\"35207639\",\"pres_detail_no\":\"68888105\",\"item_name\":\"【儿童胸部正位片（数字激光片）】X线摄影10×12吋（激光片）\",\"exec_dep\":\"放射科\",\"price\":\"22.4\",\"num\":\"1.0\",\"days\":\"1\",\"receipt_sum\":\"22.4\",\"status\":\"0\",\"doct_id\":\"10403\",\"dept_name\":\"门诊儿科\",\"address\":\"照片在(第一住院楼3楼)       CT在(第二住院楼负一楼)       核磁共振在(儿科楼1楼)\"},{\"receipt_no\":\"35207646\",\"pres_detail_no\":\"68888120\",\"item_name\":\"血常规（五分群）\",\"exec_dep\":\"检验科\",\"price\":\"20.0\",\"num\":\"1.0\",\"days\":\"1\",\"receipt_sum\":\"20.0\",\"status\":\"0\",\"doct_id\":\"10403\",\"dept_name\":\"门诊儿科\",\"address\":\"门诊6楼\"},{\"receipt_no\":\"35207646\",\"pres_detail_no\":\"68888121\",\"item_name\":\"C—反应蛋白测定(CRP)免疫法\",\"exec_dep\":\"检验科\",\"price\":\"20.0\",\"num\":\"1.0\",\"days\":\"1\",\"receipt_sum\":\"20.0\",\"status\":\"0\",\"doct_id\":\"10403\",\"dept_name\":\"门诊儿科\",\"address\":\"门诊6楼\"}]}}");
			JSONObject obj = apires.optJSONObject("return_params");
			if (obj.optInt("ret_code") == 0) {
				JSONArray result = obj.optJSONArray("obillingList");
				res.put("hospital_seq", obj.optString("hospital_seq"));
				res.put("result", result);
				res.put("count", request.getParameter("fee"));
				code = 0;
				info = "获取缴费详情成功";
			}else{
				info = obj.optString("ret_info");
			}
		} catch (Exception e) {
			LOG.error("获取缴费详情失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "获取缴费详情失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}
}
