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
 * 微信小程序报告查询
 * 
 * @author tangbin
 *
 */
@Controller
@RequestMapping("weixin/appletReport.htm")
public class WeixinAppletReportController {
	private static final Logger LOG = LoggerFactory
			.getLogger(WeixinAppletReportController.class);

	

	// 查看检查单详情
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, params = "action=checkInfo")
	public String checkInfo(HttpServletRequest request, ModelMap map) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String checkId = request.getParameter("checkId");
			String card = request.getParameter("card");
			JSONObject params = new JSONObject();
			params.put("card", card);
			params.put("day", 180);
//			JSONObject apires = HnsrmyyApi.getInstance().requestActionParams(
//					"api.hnsrmyy.unite.report.check", params, null);
			JSONObject apires = JSONObject.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"page_count\":1,\"total_count\":2,\"results\":[{\"name\":\"罗莎\",\"sex\":\"女\",\"age\":\"18Y\",\"modality\":\"US\",\"report\":\"患者餐后急诊扫查，受大量肠气干扰及仪器严重限制，部分切面显示不清，所示如下：\\n肝上界 6 肋间、肋下(-)；所示切面左肝长正常；厚正常；斜径正常；肝轮廓清晰，肝表面光滑，肝实质回声分布均匀。肝内管系走向欠清晰；门静脉内径正常。\\n脾厚正常；肋下(-)；所示切面实质回声分布均匀。\\n餐后胆囊，所示切面壁毛糙。\\n胆总管内径正常；管壁回声可，胆总管所示段内暂未见明显异常回声。\\n胰头厚正常；胰体厚正常,胰尾部显示不清；所示切面实质回声分布均匀；主胰管内径未见明显扩张。\\n双肾所示切面形态规则，轮廓清晰，大小正常；包膜完整，所示切面实质回声分布均匀；集合系统无分离，内未见明显光团声像。\\nCDFI：肝所示切面内未探及明显异常血流信号，门脉流速正常，为入肝血流。\\n提示：右下腹阑尾区可探及一盲管样回声，直径最大处约9.4mm，压痛明显，压之不闭。腔内见块状强回声伴声影，大小约9.5×6.4mm。周围见强回声网膜包绕。\",\"conclusion\":\"餐后胆囊声像\\n提示：右下腹异常回声--肿胀阑尾（伴腔内粪石）可能，请结合临床\\n急诊超声 结果仅供参考 建议门诊复查\",\"report_name\":\"陈红天/肖炯\",\"report_date\":\"2018-11-11\",\"expert_date\":\"2018-11-11\",\"study_part\":\"腹部彩色超声检查（含肝、胆、脾、胰、双肾及门静脉）\",\"jgdm\":\"01\"},{\"name\":\"罗莎\",\"sex\":\"女\",\"age\":\"18Y\",\"modality\":\"CT\",\"report\":\"阑尾增粗，直径约1.1cm，内见多个类圆形高密度灶，壁稍增厚，周围脂肪间隙模糊，邻近腹膜稍增厚，邻近系膜根部见多个轻度肿大的淋巴结。子宫、膀胱未见明显异常密度灶。盆腔未见积液。\",\"conclusion\":\"考虑阑尾多发粪石、急性阑尾炎伴局限性腹膜炎 请结合临床\",\"report_name\":\"刘芳\",\"report_date\":\"2018-11-11\",\"expert_name\":\"刘芳\",\"expert_date\":\"2018-11-11\",\"study_part\":\"下腹部\",\"jgdm\":\"01\"}]}}");
			JSONObject obj = apires.optJSONObject("return_params");
			if (obj.optInt("ret_code") == 0) {
				JSONArray array = obj.getJSONArray("results");
				int i = 1;
				for (Object model : array) {
					if (checkId.equals(i + "")) {
						JSONObject check = (JSONObject) model;
						res.put("check", check);
					}
					i++;
				}
				code = 0;
				info = "获取检查单详情成功";
			} else {
				info = obj.optString("ret_info");
			}
		} catch (Exception e) {
			LOG.error("获取检查单详情失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "获取检查单详情失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}

	// 查看检验单详情
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, params = "action=examineInfo")
	public String examineInfo(HttpServletRequest request, ModelMap map) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String card_day = request.getParameter("card_day");
			String rep_code = request.getParameter("rep_code");
			String jgdm = request.getParameter("jgdm");
			res.put("rep_code", rep_code);
			JSONObject params = new JSONObject();
			params.put("card_day", card_day);
			params.put("rep_code", rep_code);
			if ("01".equals(jgdm)) {
				params.put("hospital_id", "1966");
			} else if ("02".equals(jgdm)) {
				params.put("hospital_id", "1981");
			}
//			JSONObject apires = HnsrmyyApi.getInstance().requestActionParams(
//					"api.hnsrmyy.unite.report.lis.detail", params, null);
			JSONObject apires = JSONObject.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"result\":{\"rep_name\":\"生化报告(AU5821)\",\"rep_cate_Name\":\"住院\",\"status\":\"0\",\"check_time\":\"2018-11-12\",\"items\":[{\"item_name\":\"总胆红素\",\"result\":\"11.7\",\"unit\":\"umol/L\",\"status\":\"0\",\"range\":\"5.1~20.0 umol/L\",\"up_bound\":\"5.1\",\"down_bound\":\"20.0\"},{\"item_name\":\"直接胆红素\",\"result\":\"2.2\",\"unit\":\"umol/L\",\"status\":\"0\",\"range\":\"0~6.1 umol/L\",\"up_bound\":\"0\",\"down_bound\":\"6.1\"},{\"item_name\":\"间接胆红素\",\"result\":\"9.50\",\"unit\":\"umol/L\",\"status\":\"0\",\"range\":\"5.10~20.0 umol/L\",\"up_bound\":\"5.10\",\"down_bound\":\"20.0\"},{\"item_name\":\"总蛋白\",\"result\":\"46.4\",\"unit\":\"g/L\",\"status\":\"2\",\"range\":\"65.0~85.0 g/L\",\"up_bound\":\"65.0\",\"down_bound\":\"85.0\"},{\"item_name\":\"白蛋白\",\"result\":\"30.3\",\"unit\":\"g/L\",\"status\":\"2\",\"range\":\"35.0~55.0 g/L\",\"up_bound\":\"35.0\",\"down_bound\":\"55.0\"},{\"item_name\":\"球蛋白\",\"result\":\"16.10\",\"unit\":\"g/L\",\"status\":\"2\",\"range\":\"20.0~40.0 g/L\",\"up_bound\":\"20.0\",\"down_bound\":\"40.0\"},{\"item_name\":\"白/球比值\",\"result\":\"1.88\",\"status\":\"0\",\"range\":\"1.50~2.50\",\"up_bound\":\"1.50\",\"down_bound\":\"2.50\"},{\"item_name\":\"谷丙转氨酶\",\"result\":\"88.8\",\"unit\":\"u/L\",\"status\":\"1\",\"range\":\"7~40 u/L\",\"up_bound\":\"7\",\"down_bound\":\"40\"},{\"item_name\":\"谷草转氨酶\",\"result\":\"53.91\",\"unit\":\"u/L\",\"status\":\"1\",\"range\":\"13~35 u/L\",\"up_bound\":\"13\",\"down_bound\":\"35\"},{\"item_name\":\"谷丙/谷草转氨酶\",\"result\":\"1.65\",\"status\":\"0\"},{\"item_name\":\"谷草/谷丙转氨酶\",\"result\":\"0.61\",\"status\":\"0\"},{\"item_name\":\"总胆汁酸\",\"result\":\"23.2\",\"unit\":\"umol/L\",\"status\":\"0\",\"range\":\"0~25.0 umol/L\",\"up_bound\":\"0\",\"down_bound\":\"25.0\"},{\"item_name\":\"尿素氮\",\"result\":\"4.90\",\"unit\":\"mmol/L\",\"status\":\"0\",\"range\":\"1.70~8.30 mmol/L\",\"up_bound\":\"1.70\",\"down_bound\":\"8.30\"},{\"item_name\":\"肌酐\",\"result\":\"38.00\",\"unit\":\"umol/L\",\"status\":\"2\",\"range\":\"40.0~100.0 umol/L\",\"up_bound\":\"40.0\",\"down_bound\":\"100.0\"},{\"item_name\":\"尿酸\",\"result\":\"283.1\",\"unit\":\"umol/L\",\"status\":\"0\",\"range\":\"155~357 umol/L\",\"up_bound\":\"155\",\"down_bound\":\"357\"},{\"item_name\":\"L-乳酸脱氢酶\",\"result\":\"272.01\",\"unit\":\"U/L\",\"status\":\"1\",\"range\":\"100.0~240.0 U/L\",\"up_bound\":\"100.0\",\"down_bound\":\"240.0\"},{\"item_name\":\"肌酸激酶\",\"result\":\"42.1\",\"unit\":\"U/L\",\"status\":\"0\",\"range\":\"10.0~175.0 U/L\",\"up_bound\":\"10.0\",\"down_bound\":\"175.0\"},{\"item_name\":\"心型肌酸激酶\",\"result\":\"29\",\"unit\":\"U/L\",\"status\":\"1\",\"range\":\"0~24.0 U/L\",\"up_bound\":\"0\",\"down_bound\":\"24.0\"},{\"item_name\":\"肌红蛋白\",\"result\":\"17.6\",\"unit\":\"ng/ml\",\"status\":\"0\",\"range\":\"0~85 ng/ml\",\"up_bound\":\"0\",\"down_bound\":\"85\"},{\"item_name\":\"葡萄糖\",\"result\":\"5.45\",\"unit\":\"mmol/L\",\"status\":\"0\",\"range\":\"3.90~6.10 mmol/L\",\"up_bound\":\"3.90\",\"down_bound\":\"6.10\"},{\"item_name\":\"果糖胺\",\"result\":\"2.09\",\"unit\":\"mmol/L\",\"status\":\"0\",\"range\":\"1.0~3.0 mmol/L\",\"up_bound\":\"1.0\",\"down_bound\":\"3.0\"},{\"item_name\":\"β-羟丁酸\",\"result\":\"23.709\",\"unit\":\"umol/L\",\"status\":\"2\",\"range\":\"30~300.0 umol/L\",\"up_bound\":\"30\",\"down_bound\":\"300.0\"},{\"item_name\":\"钙\",\"result\":\"2.26\",\"unit\":\"mmol/L\",\"status\":\"0\",\"range\":\"2.10~2.90 mmol/L\",\"up_bound\":\"2.10\",\"down_bound\":\"2.90\"},{\"item_name\":\"镁\",\"result\":\"0.75\",\"unit\":\"mmol/L\",\"status\":\"0\",\"range\":\"0.60~1.20 mmol/L\",\"up_bound\":\"0.60\",\"down_bound\":\"1.20\"},{\"item_name\":\"磷\",\"result\":\"1.55\",\"unit\":\"mmol/L\",\"status\":\"1\",\"range\":\"0.70~1.50 mmol/L\",\"up_bound\":\"0.70\",\"down_bound\":\"1.50\"},{\"item_name\":\"总二氧化碳\",\"result\":\"25.2\",\"unit\":\"mmol/L\",\"status\":\"0\",\"range\":\"19.0~30.0 mmol/L\",\"up_bound\":\"19.0\",\"down_bound\":\"30.0\"},{\"item_name\":\"钠\",\"result\":\"141\",\"unit\":\"mmol/L\",\"status\":\"0\",\"range\":\"137.0~147.0 mmol/L\",\"up_bound\":\"137.0\",\"down_bound\":\"147.0\"},{\"item_name\":\"氯\",\"result\":\"104\",\"unit\":\"mmol/L\",\"status\":\"0\",\"range\":\"99.0~110.0 mmol/L\",\"up_bound\":\"99.0\",\"down_bound\":\"110.0\"},{\"item_name\":\"钾\",\"result\":\"4.07\",\"unit\":\"mmol/L\",\"status\":\"0\",\"range\":\"3.5~5.3 mmol/L\",\"up_bound\":\"3.5\",\"down_bound\":\"5.3\"},{\"item_name\":\"前白蛋白\",\"result\":\"108\",\"unit\":\"mg/L\",\"status\":\"2\",\"range\":\"200.0~400.0 mg/L\",\"up_bound\":\"200.0\",\"down_bound\":\"400.0\"},{\"item_name\":\"5-核苷酸酶\",\"result\":\"3.3\",\"unit\":\"U/L\",\"status\":\"0\",\"range\":\"2.0~25.0 U/L\",\"up_bound\":\"2.0\",\"down_bound\":\"25.0\"},{\"item_name\":\"碱性磷酸酶\",\"result\":\"255\",\"unit\":\"U/L\",\"status\":\"1\",\"range\":\"35~100 U/L\",\"up_bound\":\"35\",\"down_bound\":\"100\"},{\"item_name\":\"r-谷氨酰转肽酶\",\"result\":\"42.1\",\"unit\":\"U/L\",\"status\":\"0\",\"range\":\"7~45 U/L\",\"up_bound\":\"7\",\"down_bound\":\"45\"},{\"item_name\":\"胆碱脂酶\",\"result\":\"4060\",\"unit\":\"U/L\",\"status\":\"0\",\"range\":\"3500~13500 U/L\",\"up_bound\":\"3500\",\"down_bound\":\"13500\"},{\"item_name\":\"β2-微球蛋白\",\"result\":\"0.89\",\"unit\":\"mg/L\",\"status\":\"2\",\"range\":\"1.0~3.0 mg/L\",\"up_bound\":\"1.0\",\"down_bound\":\"3.0\"},{\"item_name\":\"视黄醇结合蛋白\",\"result\":\"33.55\",\"unit\":\"mg/L\",\"status\":\"0\",\"range\":\"20.0~75.0 mg/L\",\"up_bound\":\"20.0\",\"down_bound\":\"75.0\"},{\"item_name\":\"胱抑素C\",\"result\":\"0.37\",\"unit\":\"mg/L\",\"status\":\"2\",\"range\":\"0.51~1.09 mg/L\",\"up_bound\":\"0.51\",\"down_bound\":\"1.09\"},{\"item_name\":\"缺血修饰白蛋白\",\"result\":\"105.07\",\"unit\":\"U/ml\",\"status\":\"1\",\"range\":\"0~85.0 U/ml\",\"up_bound\":\"0\",\"down_bound\":\"85.0\"},{\"item_name\":\"ACB\",\"result\":\"49.2\",\"unit\":\"U/ml\",\"status\":\"1\",\"up_bound\":\"64.7\"},{\"item_name\":\"肌钙蛋白\",\"result\":\"0.006\",\"unit\":\"ng/ml\",\"status\":\"0\",\"range\":\"0~0.034 ng/ml\",\"up_bound\":\"0\",\"down_bound\":\"0.034\"},{\"item_name\":\"总胆固醇\",\"result\":\"5.90\",\"unit\":\"mmol/L\",\"status\":\"1\",\"range\":\"2.90~5.70 mmol/L\",\"up_bound\":\"2.90\",\"down_bound\":\"5.70\"},{\"item_name\":\"甘油三脂\",\"result\":\"6.48\",\"unit\":\"mmol/L\",\"status\":\"1\",\"range\":\"0.50~1.70 mmol/L\",\"up_bound\":\"0.50\",\"down_bound\":\"1.70\"},{\"item_name\":\"高密度脂蛋白胆固醇\",\"result\":\"0.59\",\"unit\":\"mmol/L\",\"status\":\"2\",\"range\":\"0.80~1.90 mmol/L\",\"up_bound\":\"0.80\",\"down_bound\":\"1.90\"},{\"item_name\":\"低密度脂蛋白胆固醇\",\"result\":\"2.42\",\"unit\":\"mmol/L\",\"status\":\"0\",\"range\":\"1.25~4.25 mmol/L\",\"up_bound\":\"1.25\",\"down_bound\":\"4.25\"},{\"item_name\":\"载脂蛋白A\",\"result\":\"0.95\",\"unit\":\"g/L\",\"status\":\"2\",\"range\":\"0.98~1.70 g/L\",\"up_bound\":\"0.98\",\"down_bound\":\"1.70\"},{\"item_name\":\"载脂蛋白B\",\"result\":\"1.09\",\"unit\":\"g/L\",\"status\":\"1\",\"range\":\"0.58~1.05 g/L\",\"up_bound\":\"0.58\",\"down_bound\":\"1.05\"},{\"item_name\":\"脂蛋白-a\",\"result\":\"30.2\",\"unit\":\"mg/L\",\"status\":\"0\",\"range\":\"0~300.0 mg/L\",\"up_bound\":\"0\",\"down_bound\":\"300.0\"},{\"item_name\":\"载脂蛋白A/载脂蛋白B\",\"result\":\"0.87\",\"status\":\"0\"},{\"item_name\":\"甘油三脂/总胆固醇\",\"result\":\"1.10\",\"status\":\"0\"},{\"item_name\":\"同型半胱氨酸\",\"result\":\"4.3\",\"unit\":\"umol/L\",\"status\":\"0\",\"range\":\"0~15 umol/L\",\"up_bound\":\"0\",\"down_bound\":\"15\"}]}}}");
			JSONObject obj = apires.optJSONObject("return_params");
			if (obj.optInt("ret_code") == 0) {
				JSONObject result = obj.optJSONObject("result");
				res.put("result", result);
				code = 0;
				info = "获取检验单详情成功";
			} else {
				info = obj.optString("ret_info");
			}
		} catch (Exception e) {
			LOG.error("获取检验单详情失败:" + HnsrmyyUtil.printErrInfo(e));
			info = "获取检验单详情失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}
}
