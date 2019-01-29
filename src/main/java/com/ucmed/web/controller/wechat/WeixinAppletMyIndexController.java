package com.ucmed.web.controller.wechat;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ucmed.model.utils.HnsrmyyApi;
import com.ucmed.model.utils.HnsrmyyUtil;


/**
 * 微信小程序意见反馈
 * 
 * @author tangbin
 *
 */
@Controller
@RequestMapping("weixin/appletMyIndex.htm")
public class WeixinAppletMyIndexController {
	private static final Logger LOG = LoggerFactory
			.getLogger(WeixinAppletMyIndexController.class);

	// 提交建议或者查询 异步提交
	// 解绑卡
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, params = "action=subAdvice")
	public String queueInfo(HttpServletRequest request, ModelMap map,
			HttpServletResponse response) throws IOException {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String type = request.getParameter("type");
			String suggestion = request.getParameter("suggestion");
			String contact = request.getParameter("contact");
			String source_type = "1";//微信
			JSONObject params = new JSONObject();
			params.put("type", type);
			params.put("suggestion", suggestion);
			params.put("contact", contact);
			params.put("source_type", source_type);
			JSONObject apires = HnsrmyyApi.getInstance().requestActionParams(
					"api.hnsrmyy.suggestion", params, null);
			JSONObject obj = apires.optJSONObject("return_params");
			if (obj.optInt("ret_code") == 0) {
				info = "提交成功";
				code = 0;
			} else {
				info = obj.optString("ret_info");
			}
		} catch (Exception e) {
			LOG.error("解绑失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "提交失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}
}
