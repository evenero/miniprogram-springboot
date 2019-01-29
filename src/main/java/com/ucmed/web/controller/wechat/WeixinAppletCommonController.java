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
 * 微信小程序获取openID
 * 
 * @author tangbin
 *
 */
@Controller
@RequestMapping("weixin/appletCommon.htm")
public class WeixinAppletCommonController {
	private static final Logger LOG = LoggerFactory
			.getLogger(WeixinAppletCommonController.class);
	@Autowired
	private YmlData ymlData;
	@Autowired
	private RedisUtils redisUtils;

	/**
	 * 获取openID
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, params = "action=getOpenId")
	public String getOpenId(HttpServletRequest request, ModelMap map) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			// String userCode = request.getParameter("code");// 微信小程序前端获取code
			// String grant_type = "authorization_code";
			// String sr = HttpConnectionManager
			// .getUrlContent("https://api.weixin.qq.com/sns/jscode2session?appid="
			// + hospitalConfigData.getAppId()
			// + "&secret="
			// + hospitalConfigData.getAppSecret()
			// + "&js_code="
			// + userCode + "&grant_type=" + grant_type);
			// JSONObject resultObject = JSONObject.fromObject(sr);
			// LOG.info("微信小程序返回：" + resultObject);
			// String errcode = resultObject.optString("errcode");
			// if (errcode != null && !"".equals(errcode)) {
			// info = "请求微信服务器失败！" + resultObject.optString("errmsg");
			// } else {
			// String openid = resultObject.optString("openid");
			// res.put("openid", openid);
			// code = 0;
			// info = "获取openID成功";
			// }
			res.put("openid", "oROtJt-SerUfQevPOrvoulJfW-PE");
			code = 0;
			info = "获取openID成功";
		} catch (Exception e) {
			LOG.info("获取openID失败:" + HnsrmyyUtil.printErrInfo(e));
			info = "获取openID失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);

		return res.toString();
	}

	// 拦截是否有就诊卡用户
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, params = "action=cardFilter")
	public String cardFilter(HttpServletRequest request, ModelMap map) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String hospitalId = request.getParameter("hospital_id");
			String openid = request.getParameter("openid");
			JSONObject params = new JSONObject();
			String type = "4";
			params.put("type", type);
			params.put("identify", openid);
			params.put("hospital_id", hospitalId);
			// JSONObject apires = HnsrmyyApi.getInstance().requestActionParams(
			// "api.hnsrmyy.unite.card.list", params, null);
			String aString = "{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"count\":2,\"result\":[{\"id\":1522348,\"name\":\"孙沐辰\",\"card\":\"430522201501170267\",\"idCard\":\"430522201501170267\"},{\"id\":1522348,\"name\":\"王沐辰\",\"card\":\"430522222201170267\",\"idCard\":\"430522201501170267\"},{\"id\":1522173,\"name\":\"孙逸琳\",\"card\":\"430522198701120024\",\"idCard\":\"430522198701120024\"}]}}";
			JSONObject apires = JSONObject.fromObject(aString);
			JSONObject obj = apires.optJSONObject("return_params");
			if (obj.optInt("ret_code") == 0) {
				JSONArray result = obj.optJSONArray("result");
				if (result.size() == 0) {
					res.put("isCard", 1);
				}
				res.put("result", result);
				code = 0;
				info = "获取卡列表成功";
			} else {
				info = obj.optString("ret_info");
			}
		} catch (Exception e) {
			LOG.error("获取卡列表失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "获取卡列表失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);

		return res.toString();
	}

	// 跳转到卡详情
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, params = "action=cardInfo")
	public String cardInfo(HttpServletRequest request, ModelMap map) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String cardId = request.getParameter("cardId");
			String card = request.getParameter("card");
			String openid = request.getParameter("openid");
			if (HnsrmyyUtil.getCardList("4", openid, card)) {
				JSONObject params = new JSONObject();
				params.put("id", cardId);
				params.put("card", card);
//				JSONObject apiRes = HnsrmyyApi.getInstance()
//						.requestActionParams("api.hnsrmyy.unite.card.detail",
//								params, null);
				JSONObject apiRes = JSONObject.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"result\":{\"id\":1615759,\"name\":\"莫滨宇\",\"sex\":\"男\",\"address\":\"雨花区融圣国际\",\"idCard\":\"43032120110611011X\",\"card\":\"43032120110611011X\",\"phone\":\"15084927838\"}}}");
				JSONObject obj = apiRes.optJSONObject("return_params");
				if (obj.optInt("ret_code") == 0) {
					JSONObject result = obj.getJSONObject("result");
					res.put("card", result);
					code = 0;
					info = "获取卡详情成功";
				} else {
					info = obj.optString("ret_info");
				}
			} else {
				info = "该卡暂无详情";
			}
		} catch (Exception e) {
			LOG.error("获取卡详情失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "获取卡详情失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);

		return res.toString();
	}

	// 解绑卡
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, params = "action=cancelCard")
	public String cancelCard(HttpServletRequest request, ModelMap map,
			HttpServletResponse response) throws IOException {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String card = request.getParameter("card");
			String id = request.getParameter("id");
			JSONObject params = new JSONObject();
			params.put("card", card);
			params.put("type", "4");
			params.put("id", id);
//			JSONObject apiRes = HnsrmyyApi.getInstance().requestActionParams(
//					"api.hnsrmyy.unite.card.unband", params, null);
			JSONObject apiRes = JSONObject.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"ret_info\":\"解绑成功\"}}");
			JSONObject obj = apiRes.optJSONObject("return_params");
			if (obj.optInt("ret_code") == 0) {
				code = 0;
				info = "解绑成功 ！";
			} else {
				info = obj.optString("ret_info");
			}
		} catch (Exception e) {
			LOG.error("解绑失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "解绑失败！";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}

	// 添加绑卡
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, params = "action=addcard")
	public String addcard(HttpServletRequest request, ModelMap map,
			HttpServletResponse response) throws IOException {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String formData = request.getParameter("formData");
			JSONObject object = JSONObject.fromObject(formData);
			String card = object.optString("card");
			String name = object.optString("name");
			String phone = object.optString("phone");
			String addr = object.optString("addr");
			String openid = request.getParameter("openid");
			JSONObject params = new JSONObject();
			params.put("name", name);
			params.put("card", card);
			params.put("identify", openid);
			params.put("phone", phone);
			params.put("addr", addr);
			params.put("source", "8");// 微信小程序
//			JSONObject apiRes = HnsrmyyApi.getInstance().requestActionParams(
//					"api.hnsrmyy.unite.card.band", params, null);
			JSONObject apiRes = JSONObject.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0}}");
			JSONObject obj = apiRes.optJSONObject("return_params");
			if (obj.optInt("ret_code") == 0) {
				code = 0;
				info = "绑定成功";
				// 支付成功时推送消息
				JSONObject params2 = new JSONObject();
				params2.put("title", "绑定成功");
				params2.put("msgtype", "text");
				card = (card.length() == 15) ? card.substring(0, 3)
						+ "********" + card.substring(11, 15) : card.substring(
						0, 3) + "********" + card.substring(14, 18);
				params2.put("content", "您已经成功绑定了了卡号为【" + card
						+ "】的就诊卡,可以【挂号、缴费、查询检验检查结果等】");
				params2.put("touser", openid);
				JSONObject res2 = HnsrmyyApi.getInstance().requestActionParams(
						"api.hnsrmyy.wx.push.msg", params2, null);
				JSONObject obj2 = res2.optJSONObject("return_params");
				if (obj2.optInt("ret_code") == 0) {
					res.put("push", 0);
				} else {
					res.put("push", 1);
				}
			} else if (obj.optInt("ret_code") == 1) {
				String ret_info = obj.optString("ret_info");
				code = 0;
				info = "完善就诊卡信息成功" + ret_info;
			} else {
				info = "绑定失败，" + obj.optString("ret_info");
			}
		} catch (Exception e) {
			LOG.error("绑定失败：" + HnsrmyyUtil.printErrInfo(e));
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}

	// 跳转到需要查询的页面
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, params = "action=toQueryUrl")
	public String toQueryUrl(HttpServletRequest request, ModelMap map) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String name = request.getParameter("name");
			String card = request.getParameter("card");
			String queryType = request.getParameter("queryType");
			String hospital_id = request.getParameter("hospital_id");
			String openid = request.getParameter("openid");
			if (HnsrmyyUtil.getCardList("4", openid, card)) {
				JSONObject apiRes = getApiRes(queryType, name, card,
						hospital_id);
				return apiRes.toString();
			} else {
				info = "请重新进入！";
			}
		} catch (Exception e) {
			LOG.error("失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "跳转到需要查询的页面失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}

	/**
	 * 从湖南卫计委查询用户信息
	 * 
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, params = "action=queryIdcard")
	public String queryUserInfoFromWJW(HttpServletRequest request,
			ModelMap map, HttpServletResponse response) throws IOException {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String openid = request.getParameter("openid");
			String idcard = request.getParameter("id_card");
			if (openid == null) {
				info = "请重新进入小程序";
			} else {
				boolean b = IdcardUtil.isIdcard(idcard);
				if (b) {
					JSONObject params = new JSONObject();
					params.put("idcard", idcard);
					params.put("identify", openid);
					params.put("source", "2");// 来源：1支付宝，2微信，3app
					JSONObject apirRes = HnsrmyyApi.getInstance()
							.requestActionParams(
									"api.hnsrmyy.query.user.info.from.wjw",
									params, null);
					JSONObject obj = apirRes.optJSONObject("return_params");
					if (obj.optInt("ret_code") == 0) {
						JSONObject result = obj.optJSONObject("result");
						String name = result.optString("name");
						name = yincang(name);
						res.put("name", name);
						res.put("address", result.optString("address"));
						code = 0;
						info = "获取身份证信息成功";
						Object numObject = redisUtils
								.get(Constants.QUERY_IDCARD_CACHE + openid
										+ DateUtil.getToday2());
						if (numObject == null) {
							Integer numInteger = 1;
							redisUtils
									.set(Constants.QUERY_IDCARD_CACHE + openid
											+ DateUtil.getToday2(), numInteger,
											60 * 60 * 24L, TimeUnit.SECONDS);
						} else {
							Integer numInteger = Integer.valueOf(numObject
									.toString());
							if (numInteger < 5) {
								numInteger++;
								redisUtils.set(Constants.QUERY_IDCARD_CACHE
										+ openid, numInteger, 60 * 60 * 24L,
										TimeUnit.SECONDS);
							} else {
								info = "已达查询上限，请勿重复操作！";
							}
						}
					} else {
						info = obj.optString("ret_info");
					}
				} else {
					info = "请输入正确的身份证号";
				}
			}
		} catch (Exception e) {
			LOG.error("通过卫计委查询身份证信息失败：" + HnsrmyyUtil.printErrInfo(e));
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}

	// 微信支付
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, params = "action=getPayParam")
	public String weixinAppletpay(HttpServletRequest request, ModelMap map) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String hospitalId = request.getParameter("hospital_id");
			String openid = request.getParameter("openid");
			String payId = request.getParameter("payId");
			String payType = request.getParameter("payType");
			String fee = request.getParameter("fee");
			JSONObject params = new JSONObject();
			params.put("type", payType);
			params.put("pay_id", payId);
			params.put("trade_type", "04");// 微信小程序
			params.put("fee", fee);
			params.put("openid", openid);
			params.put("spbill_create_ip", "61.187.87.30");
			params.put("hospital_id", hospitalId);

//			JSONObject resApi = HnsrmyyApi.getInstance().requestActionParams(
//					"api.hnsrmyy.unite.get.pay.param", params, null);
			JSONObject resApi = JSONObject.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"weixin\":{\"package\":\"prepay_id=wx25000338820829314f365b7d2502486933\",\"appId\":\"wx3fdecc3c10998f33\",\"timeStamp\":\"1545667419\",\"signType\":\"MD5\",\"nonceStr\":\"1545667419\",\"paySign\":\"433BE9F007EABC4E18AE9256CEDBAC34\",\"flow_id\":\"1545667419000111\"}}}");
			JSONObject obj = resApi.optJSONObject("return_params");
			if (!obj.isEmpty()) {
				if (obj.optInt("ret_code") == 0) {
					JSONObject weixin = obj.optJSONObject("weixin");
					res.put("appId", weixin.optString("appId"));
					res.put("pack", weixin.optString("package"));
					res.put("timeStamp", weixin.optString("timeStamp"));
					res.put("signType", weixin.optString("signType"));
					res.put("nonceStr", weixin.optString("nonceStr"));
					res.put("paySign", weixin.optString("paySign"));
					res.put("flow_id", weixin.optString("flow_id"));
					code = 0;
					info = "支付下单成功";
				} else {
					info = obj.optString("ret_info");
				}
			}else {
				info="支付失败";
			}
			res.put("hospital_id", hospitalId);
		} catch (Exception e) {
			LOG.error("支付失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "支付失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}

	// 支付的结果页面
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, params = "action=resultStatus")
	public String payPage(HttpServletRequest request, ModelMap map) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String flowId = request.getParameter("flowId");
			String hospitalId = request.getParameter("hospital_id");
			String openid = request.getParameter("openid");
			String payType = request.getParameter("payType");
			JSONObject params = new JSONObject();
			params.put("hospital_id", hospitalId);
			params.put("flowId", flowId);
//			JSONObject apiRes = HnsrmyyApi.getInstance().requestActionParams(
//					"api.hnsrmyy.pay.success.return", params, null);
			/**
			 * 测试数据
			 */
			JSONObject apiRes = null;
			if ("01".equals(payType)||"11".equals(payType)) {
				apiRes = JSONObject.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"status\":\"1\",\"type\":\"01\",\"user_id\":\"oROtJt3m0utiUHtGmQYX_ZfUjcpo\",\"bank_code\":\"02\",\"hospital_id\":\"1966\",\"result\":{\"id\":1825184,\"dept_name\":\"疼痛门诊\",\"doctor_name\":\"张宇\",\"clinic_date\":\"2018-12-26\",\"time_desc\":\"上午\",\"time_slot\":\"10:36-10:42\",\"patient_name\":\"黄盛准\",\"phone\":\"16607497606\",\"card\":\"430681199307117652\",\"fee\":\"15.0\",\"order_status\":\"4\",\"pay_status\":\"1\",\"doctor_desc\":\"副主任医师\",\"create_time\":\"2018-12-25 00:04\",\"pass_word\":\"164171\",\"addr\":\"门诊4楼\",\"clinic_no\":\"27\",\"hospital_seq\":\"14315292\"}}}");
			}else if ("02".equals(payType)||"12".equals(payType)) {
				apiRes = JSONObject.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"status\":\"1\",\"type\":\"02\",\"user_id\":\"oROtJt36famLDEXZ_KEZZvu3cA24\",\"bank_code\":\"02\",\"hospital_id\":\"1966\",\"result\":{\"id\":609003,\"dept_name\":\"急诊儿科\",\"doctor_name\":\"未写医生\",\"clinic_date\":\"2018-12-25\",\"time_desc\":\"深夜\",\"patient_name\":\"李梓欣\",\"phone\":\"13203109620\",\"card\":\"430122201601150106\",\"fee\":\"7.0\",\"order_status\":\"1\",\"pay_status\":\"1\",\"doctor_desc\":\"主治医师\",\"create_time\":\"2018-12-25 00:05\",\"addr\":\"儿科大楼一楼\",\"clinic_no\":\"30003\",\"hospital_seq\":\"14315294\"}}}");
			}else if ("03".equals(payType)||"13".equals(payType)) {
				apiRes = JSONObject.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"status\":\"1\",\"type\":\"03\",\"user_id\":\"2088702094593115\",\"bank_code\":\"01\",\"result\":{\"id\":2876111,\"patient_name\":\"阮诗琪\",\"card\":\"20178206318\",\"fee\":\"378.80\",\"hospital_seq\":\"14308074\",\"patient_type\":\"自费病人\",\"receipt_no\":\"201801826416077\",\"regist_no\":\"14308074\",\"obilling_date\":\"2018-12-25 00:06:30\"}}}");
			}else if ("04".equals(payType)||"14".equals(payType)) {
				apiRes = JSONObject.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"status\":\"1\",\"type\":\"04\",\"user_id\":\"2088302182153294\",\"bank_code\":\"01\",\"result\":{\"id\":85880,\"dept_name\":\"肝胆二科（肝脏外一科）\",\"patient_name\":\"李金福\",\"fee\":\"5000.0\",\"admission_no\":\"00716121\",\"admission_date\":\"2018-12-25 01:10:38\",\"hospital_id\":\"1966\"}}}");
			}
			if (apiRes != null) {
				JSONObject obj = apiRes.optJSONObject("return_params");
				int codeRes = obj.getInt("ret_code");
				if (codeRes == 0) {
					if ("1".equals(obj.optString("status"))) {
						JSONObject result = obj.optJSONObject("result");
						res.put("result", result);
						String content = concat(result, hospitalId, payType);
						if (content != null && !"".equals(content)) {
							LOG.info("推送消息内容："+content);
//							JSONObject params2 = new JSONObject();
//							params2.put("title", getTitle(payType));
//							params2.put("msgtype", "text");
//							params2.put("content", content);
//							params2.put("touser", openid);
//							params2.put("pushType", "2");//代表微信小程序推送
//							JSONObject res2 = HnsrmyyApi.getInstance()
//									.requestActionParams(
//											"api.hnsrmyy.wx.push.msg", params2,
//											null);
//							JSONObject obj2 = res2
//									.optJSONObject("return_params");
//							if (obj2.optInt("ret_code") == 0) {
//								res.put("push", 0);
//							} else {
//								res.put("push", 1);
//							}
						} else {
							info = "系统繁忙，请稍后再试";
						}
					} else if (code == 1) {
						info = obj.getString("ret_info")
								+ "医院已将费用退还,1-3个工作日内即可到账,给您造成不便深表歉意!";
					} else if (code == 2) {
						info = "系统处理延迟，请稍后在挂号记录中查询结果，如有疑问，请联系医院！";
					} else {
						info = obj.optString("ret_info");
						if ("".equals(info)) {
							info = "缴费出错，请稍后再试！如有疑问，请联系医院！";
						}
					}
					code = 0;
				} else {
					info = obj.optString("ret_info");
				}
			} else {
				info = "系统繁忙，请稍后再试！";
			}
			res.put("hospital_id", hospitalId);
		} catch (Exception e) {
			LOG.error("支付结果跳转失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "支付结果跳转失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}

	private String concat(JSONObject result, String hospitalId, String payType) {
		StringBuffer sb = new StringBuffer();
		String card = result.optString("card");
		card = (card.length() == 18) ? card.substring(0, 3) + "********"
				+ card.substring(14, 18) : card;
		if ("01".equals(payType) || "11".equals(payType)) {
			sb.append(result.optString("patient_name")).append(",您的卡号：")
					.append(card).append("已经成功预约【")
					.append(result.optString("dept_name")).append("】科室的【")
					.append(result.optString("doctor_name"))
					.append("】医生,预计就诊时间为【")
					.append(result.optString("clinic_date")).append(" ")
					.append(result.optString("time_desc")).append(" ")
					.append(result.optString("time_slot")).append("】,就诊地址为【")
					.append(result.optString("addr")).append("】,就诊序号为【")
					.append(result.optString("clinic_no")).append("】,挂号序号为【")
					.append(result.optString("hospital_seq")).append("】");
			if ("1966".equals(hospitalId)) {
				sb.append(",无需取号。请务必提前半小时到达相应诊区候诊，【迟到者系统自动排序至最末】。如需取消就诊预约，请在就诊日零点前在线取消。注意：过期未就诊不能退号！湖南省人民医院天心阁院区（解放西路61号）");
			} else if ("1981".equals(hospitalId)) {
				sb.append(",请您提前1小时前往相应科室候诊，无需至窗口取号，过号需重新排序。湖南省人民医院马王堆院区（古汉路89号）");
			}
		} else if ("02".equals(payType) || "12".equals(payType)) {
			sb.append(result.optString("patient_name")).append(",您的卡号：")
					.append(card).append("已经成功挂号【")
					.append(result.optString("dept_name")).append("】科室的【")
					.append(result.optString("doctor_name"))
					.append("】医生,预计就诊时间为【")
					.append(result.optString("clinic_date"))
					.append(result.optString("time_desc")).append("】,就诊地址为【")
					.append(result.optString("addr")).append("】,就诊序号为【")
					.append(result.optString("clinic_no")).append("】,挂号序号为【")
					.append(result.optString("hospital_seq")).append("】 ");
			if ("1981".equals(hospitalId)) {
				sb.append("湖南省人民医院马王堆院区（古汉路89号）");
			} else if ("1966".equals(hospitalId)) {
				sb.append("湖南省人民医院天心阁院区（解放西路61号）");
			}
		} else if ("03".equals(payType) || "13".equals(payType)) {
			sb.append(result.optString("patient_name"));
			sb.append(",您已成功缴费门诊号【");
			sb.append(result.optString("regist_no"));
			sb.append("】的费用【");
			sb.append(result.optString("fee"));
			sb.append("元】,请您携带身份证/就诊卡直接前往取药或者检查。");
			if ("1966".equals(hospitalId)) {
				sb.append("湖南省人民医院天心阁院区（解放西路61号）");
			} else if ("1981".equals(hospitalId)) {
				sb.append("湖南省人民医院马王堆院区（古汉路89号）");
			}
		} else if ("04".equals(payType) || "14".equals(payType)) {
			sb.append(result.optString("patient_name"));
			sb.append(",您已成功缴费住院号【");
			sb.append(result.optString("admission_no"));
			sb.append("】的住院预缴费用【");
			sb.append(result.optString("fee"));
			sb.append("元】。");
			if ("1966".equals(hospitalId)) {
				sb.append("湖南省人民医院天心阁院区（解放西路61号）");
			} else if ("1981".equals(hospitalId)) {
				sb.append("湖南省人民医院马王堆院区（古汉路89号）");
			}
		}
		return sb.toString();
	}

	private String getTitle(String payType) {
		String title = "";
		if ("01".equals(payType) || "11".equals(payType)) {
			title = "预约成功";
		} else if ("02".equals(payType) || "12".equals(payType)) {
			title = "挂号成功";
		} else if ("03".equals(payType) || "13".equals(payType)) {
			title = "缴费成功";
		} else if ("04".equals(payType) || "14".equals(payType)) {
			title = "住院预交金支付成功";
		}
		return title;
	}

	private String yincang(String name) {
		String ming = name.substring(1);
		return "*" + ming;
	}

	private JSONObject getApiRes(String queryType, String name, String card,
			String hospitalId) throws Exception {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		String apiName = "";
		JSONObject apiRes = null;
		JSONObject params = new JSONObject();
		params.put("card", card);
		if ("1".equals(queryType)) {// 门诊缴费
			// 设置当前的时间 只拿到当前时间的就诊记录
			String startDate = DateUtil.getDateByDays2(-1);
			String endDate = DateUtil.getyyyy_MM_dd(new Date());
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			params.put("hospital_id", hospitalId);
			apiName = "1966".equals(hospitalId) ? "api.hnsrmyy.obilling.list"
					: "api.hnsrmyy.unite.obilling.list";
			apiRes = JSONObject
					.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"ret_info\":\"成功\",\"result\":[{\"regist_no\":\"123456789\",\"pres_no\":\"987654321\",\"time_range\":\"15:00-16:00\",\"reg_date\":\"2018-12-21\",\"doct_name\":\"陈红天\",\"dep_name\":\"儿科门诊\",\"status\":\"1\",\"hospital_id\":\"1966\"},{\"regist_no\":\"45646578146\",\"pres_no\":\"789652845565\",\"time_range\":\"13:00-14:00\",\"reg_date\":\"2018-11-25\",\"doct_name\":\"陈白天\",\"dep_name\":\"妇科门诊\",\"status\":\"1\",\"hospital_id\":\"1966\"},{\"regist_no\":\"6465656\",\"pres_no\":\"28543854263845354\",\"time_range\":\"11:00-12:00\",\"reg_date\":\"2018-10-25\",\"doct_name\":\"陈黄天\",\"dep_name\":\"中医科管他什么门诊\",\"status\":\"0\",\"hospital_id\":\"1966\"}]}}");
		} else if ("2".equals(queryType)) {// 住院预缴
			if ("1966".equals(hospitalId)) {
				params.put("is_open", "1");
			}
			params.put("card_type", "0");
			params.put("hospital_id", hospitalId);
			apiName = "1966".equals(hospitalId) ? "api.hnsrmyy.admission.record.list"
					: "api.hnsrmyy.unite.admission.record.list";
			apiRes = JSONObject
					.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"ret_info\":\"成功\",\"result\":[{\"bed_no\":\"12\",\"patient_name\":\"张三\",\"card\":\"430421199105167136\",\"admission_no\":\"123456789\",\"age\":\"27岁\",\"sex\":\"1\",\"balance_type\":\"自费\",\"his_type\":\"社会保\",\"dept_name\":\"中医门诊\",\"admission_date\":\"2018-12-12\",\"collector_name\":\"李四\",\"advanced_fund\":\"2000\",\"sum_fee\":\"3000\",\"deposit\":\"-1000\",\"patient_id\":\"001234\"},{\"bed_no\":\"13\",\"patient_name\":\"张吴\",\"card\":\"430421199505167136\",\"admission_no\":\"123456789\",\"age\":\"25岁\",\"sex\":\"1\",\"balance_type\":\"自费\",\"his_type\":\"社会保\",\"dept_name\":\"儿科门诊\",\"admission_date\":\"2018-12-14\",\"collector_name\":\"赵柳\",\"advanced_fund\":\"1000\",\"sum_fee\":\"2500\",\"deposit\":\"-1500\",\"patient_id\":\"001234\"}]}}");
		} else if ("3".equals(queryType)) {// 挂号记录
			params.put("page", 1);
			params.put("size", 1000);
			apiName = "api.hnsrmyy.unite.record.list";
			// 挂号
			apiRes = JSONObject
					.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"result\":[{\"id\":1660688,\"dept_name\":\"门诊儿科\",\"name\":\"邱诚浩\",\"fee\":\"22\",\"status\":\"4\",\"dates\":\"2018-11-11\",\"type\":\"01\",\"hospital_id\":\"1966\"},{\"id\":1657548,\"dept_name\":\"门诊儿科\",\"name\":\"邱诚浩\",\"fee\":\"70\",\"status\":\"4\",\"dates\":\"2018-11-14\",\"type\":\"01\",\"hospital_id\":\"1966\"}]}}");
		} else if ("4".equals(queryType)) {// 缴费记录
			apiName = "api.hnsrmyy.pay.record.list";
			apiRes = JSONObject
					.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"result\":[{\"id\":84854,\"patientName\":\"蒋金梅\",\"deptName\":\"普外三科（乳甲外科）\",\"fee\":\"5000.0\",\"type\":\"04\",\"card\":\"432923196807015127\",\"payTimes\":\"2018-11-13 08:03\",\"bank_code\":\"02\"},{\"id\":2702615,\"patientName\":\"蒋金梅\",\"deptName\":\"乳甲外科门诊\",\"fee\":\"200.0\",\"type\":\"03\",\"card\":\"432923196807015127\",\"payTimes\":\"2018-11-08 08:15\",\"bank_code\":\"02\"}]}}");
		} else if ("5".equals(queryType)) {// 检查报告
			params.put("day", 180);
			apiName = "api.hnsrmyy.unite.report.check";
			apiRes = JSONObject
					.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"page_count\":1,\"total_count\":6,\"results\":[{\"name\":\"李国先\",\"sex\":\"男\",\"age\":\"59Y\",\"modality\":\"DR\",\"report\":\"双肺未见实质性病变。心膈影未见异常。\",\"report_name\":\"蔡卓言\",\"report_date\":\"2018-11-5\",\"expert_name\":\"贺亚琼\",\"expert_date\":\"2018-11-05\",\"study_part\":\"胸部正位片\",\"jgdm\":\"01\"},{\"name\":\"李国先\",\"sex\":\"男\",\"age\":\"59Y\",\"modality\":\"US\",\"report\":\"AO:30mm LA:36mm LV:DM:42mm SM:29mm PA:16mm\\nRA:30mm RV:29mm IVS:11mm LVPW:10mm RVOT：23mm\\n    左房增大，余各房室大小正常，房室间隔连续，室间隔及左室后壁不厚，运动协调异向；主、肺动脉内径及位置关系正常；各瓣膜成分清晰，启闭自如；心包及心包腔未见明显异常声像。\\n    M：二尖瓣前叶曲线呈“双峰”，前后叶运动异向。\\n    CDFI：房室间隔未见过隔血流，各瓣膜口血流测值如下：\\nMV:  E峰 0.51 m/s  A峰 0.72 m/s E〈A峰，PV:0.82m/s；AV:0.93m/s，主动脉瓣口→左室侧DM期见以红色为主的局限性五彩返流束，余瓣膜未见明显反流束。\\n    心功能：EF:56%; FS:29%\",\"conclusion\":\"左房增大\\n左室舒张功能减退 收缩功能测值正常范围\",\"report_name\":\"向力群\",\"report_date\":\"2018-11-7\",\"expert_date\":\"2018-11-07\",\"study_part\":\"心脏彩超\",\"jgdm\":\"01\"},{\"name\":\"李国先\",\"sex\":\"男\",\"age\":\"59Y\",\"modality\":\"US\",\"report\":\"肝上界 6 肋间、肋下(-)；左肝长正常；厚正常；斜径正常；肝轮廓清晰，肝表面光滑，肝实质回声分布均匀，肝内可探及无回声区，大小约44×36mm，形态规则，边界清，内透声可。肝内管系走向欠清晰，左右肝管汇合处可见一中等回声团块，大小约31×34mm，致左右肝管汇合处截断，形态欠规则，边界欠清，内分布不均匀。肝内可见胆管扩张，与门静脉呈“双管征”，较宽处三级胆管内径分别宽约:6.1mm（左），6.8mm（右）；门静脉内径正常。\\n脾厚正常；肋下(-)；实质回声分布均匀。\\n胆囊区未见正常胆囊回声，仅见一大小约40×11mm的稍高回声区，内未见明显胆汁回声。\\n胆总管内径正常；管壁回声可，胆总管内未见明显异常回声。\\n胰头厚正常；胰体厚正常；实质回声分布均匀；主胰管内径不扩张。\\n双肾形态规则，轮廓清晰，大小正常；包膜完整，实质回声分布均匀；集合系统无分离，内未见明显光团声像。\\nCDFI：肝内未探及明显异常血流信号，门脉流速正常，为入肝血流。\",\"conclusion\":\"肝门部中等回声团块 考虑肝门胆管Ca 并肝内胆管扩张\\n胆囊区声像改变 疑胆囊萎缩\\n肝囊肿\",\"report_name\":\"向力群\",\"report_date\":\"2018-11-7\",\"expert_date\":\"2018-11-07\",\"study_part\":\"腹部彩色超声检查\",\"jgdm\":\"01\"},{\"name\":\"李国先\",\"sex\":\"男\",\"age\":\"59Y\",\"modality\":\"MR\",\"report\":\"肝脏边缘光整，肝体积增大，肝叶比例失调，肝实质多发囊状长T1长T2信号，边界清晰，较大者位于肝S6段大小约4.1×3.7cm。肝内胆管扩张，扩张胆管于肝门部局限性狭窄，可见稍长T1稍长T2软组织信号，胆总管下段未见扩张，其内未见异常信号。胆囊缩小，其内未见异常信号。胰腺形态、信号正常，主胰管未见扩张。脾脏形态正常，未见异常信号。腹膜后未见明显肿大淋巴结，腹腔内未见腹水征。\\n    MRCP序列成像：肝内胆管、左右肝管明显扩张，于肝门部胆管局限性狭窄，胆总管下段未见扩张，未见充盈缺损。胆囊未见显示。胰管走行、信号正常。腹腔内未见腹水征。\",\"conclusion\":\"肝门胆管局限性狭窄伴肝内胆管扩张，考虑肝门胆管Ca可能大，建议进一步检查\\n胆囊萎缩\\n胆源性肝硬化\\n肝脏多发囊肿\",\"report_name\":\"刘超\",\"report_date\":\"2018-11-7\",\"expert_name\":\"曾琦\",\"expert_date\":\"2018-11-07\",\"study_part\":\"上腹部,MRCP\",\"jgdm\":\"01\"},{\"name\":\"李国先\",\"sex\":\"男\",\"age\":\"59Y\",\"modality\":\"CT\",\"report\":\"肝脏大小、形态正常，肝表面光整，肝各叶比例正常；肝内可见多个类圆形无强化灶，边缘光整，大者位于肝右叶，直径约3.7cm；余示肝实质内未见异常密度及异常强化灶。肝内胆管明显扩张，于肝门区狭窄梗阻，肝门区可见多发小淋巴结显示；胆总管未见明显扩张。胆囊不增大，胆囊壁增厚，胆囊内可见条片状高密度结石影。脾脏大小、形态及密度未见异常。胰腺大小、形态及密度未见异常；主胰管稍扩张。腹膜后未见肿大淋巴结。腹腔未见积液。\",\"conclusion\":\"肝门区胆管狭窄、梗阻，考虑肝门胆管Ca，并肝内胆管扩张；\\n胆囊结石，胆囊炎；\\n肝多发性囊肿。\",\"report_name\":\"程彬\",\"report_date\":\"2018-11-7\",\"expert_name\":\"曾琦\",\"expert_date\":\"2018-11-08\",\"study_part\":\"上腹部\",\"jgdm\":\"01\"},{\"name\":\"李国先\",\"sex\":\"男\",\"age\":\"59Y\",\"modality\":\"US\",\"report\":\"患者取左侧卧位，常规彩超扫查肝脏。龙胆紫定位体表最佳穿刺点。常规消毒、铺巾、局麻、无菌手套包裹探头。彩超引导下将PTCD管分别置入左、右肝胆管内，拔出穿刺针，送入引流管，缝线及蝶形胶布双重固定后接上引流管、消毒、敷盖无菌纱布、固定。  术中、术后患者未诉特殊不适。\",\"conclusion\":\"彩超引导下左、右肝PTCD置管成功\",\"report_name\":\"赵康\",\"report_date\":\"2018-11-8\",\"expert_date\":\"2018-11-08\",\"study_part\":\"彩色超声引导下穿刺活检 【其它：PTCD】\",\"jgdm\":\"01\"}]}}");
		} else if ("6".equals(queryType)) {// 检验报告
			params.put("page", 1);
			params.put("size", 1000);
			params.put("day", 180);
			apiName = "api.hnsrmyy.unite.report.lis";
			apiRes = JSONObject
					.fromObject("{\"return_code\":0,\"return_params\":{\"ret_code\":0,\"card_day\":\"20189043169_180\",\"page_count\":1,\"total_count\":1,\"results\":[{\"rep_code\":\"4029391\",\"rep_name\":\"血常规\",\"rep_cate\":\"1\",\"rep_cate_name\":\"门诊\",\"check_time\":\"2018-11-13\",\"jgdm\":\"02\"}]}}");
		}
		// apiRes = HnsrmyyApi.getInstance().requestActionParams(
		// apiName, params, null);
		JSONObject obj = apiRes.optJSONObject("return_params");
		if (obj.optInt("ret_code") == 0) {
			JSONArray result = new JSONArray();
			if ("5".equals(queryType)) {
				result = obj.optJSONArray("results");
			} else if ("6".equals(queryType)) {
				result = obj.optJSONArray("results");
				String card_day = obj.optString("card_day");
				res.put("card_day", card_day);
			} else {
				result = obj.optJSONArray("result");
			}
			res.put("list", result);
			code = 0;
			info = "查询成功";
		} else {
			info = obj.optString("ret_info");
		}
		res.put("name", name);
		res.put("card", card);
		res.put("queryType", queryType);
		res.put("hospital_id", hospitalId);
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res;

	}
	
	public static void main(String[] args) {
		JSONObject obj = JSONObject.fromObject("{}");
		if (!obj.isEmpty()) {
			System.out.println("有数据");
		}else {
			System.out.println("无数据");
		}
		System.out.println();
	}

}
