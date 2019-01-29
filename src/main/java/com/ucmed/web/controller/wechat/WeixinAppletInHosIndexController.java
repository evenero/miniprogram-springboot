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
 * 走进医院
 * @author tangbin
 *
 */
@Controller
@RequestMapping("weixin/appletInHospital.htm")
public class WeixinAppletInHosIndexController {
	private static final Logger LOG = LoggerFactory
			.getLogger(WeixinAppletInHosIndexController.class);

	/**
	 * 医院介绍
	 * 
	 * @param request
	 * @param map
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, params = "action=hospitalIntroduce")
	public String hospitalIntroduceInfo(HttpServletRequest request, ModelMap map,
			HttpServletResponse response) throws IOException {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String hospitalId = request.getParameter("hospital_id");
			String name = "";
			String content = "";
			JSONArray array = new JSONArray();
			for (int i = 0; i < 4; i++) {
				JSONObject object = new JSONObject();
				if (i==0) {
					name = "医院介绍";
					content = "这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍这是医院介绍";
				}else if(i==1){
					name = "医院交通";
					content = "这是医院交通这是医院交通这是医院交通这是医院交通这是医院交通这是医院交通这是医院交通这是医院交通这是医院交通这是医院交通这是医院交通这是医院交通这是医院交通这是医院交通这是医院交通这是医院交通这是医院交通这是医院交通这是医院交通这是医院交通这是医院交通这是医院交通这是医院交通这是医院交通";
				}else if(i==2){
					name = "官方网站";
					content = "http://www.hnsrmyy.com/index.php";
				}else if(i==3){
					name = "医院电话";
					content = "0731-8888888";
				}
				object.put("name", name);
				object.put("content", content);
				array.add(object);
			}
			res.put("list", array);
			code = 0;
			info = "获取医院介绍成功！";
			res.put("hospital_id", hospitalId);
		} catch (Exception e) {
			LOG.error("获取医院介绍失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "获取医院介绍失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}
	
	/**
	 * 科室列表
	 * @param request
	 * @param map
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, params = "action=deptListIntroduce")
	public String deptListIntroduce(HttpServletRequest request, ModelMap map,
			HttpServletResponse response) throws IOException {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String hospitalId = request.getParameter("hospital_id");
			String deptId = "";
			String deptName = "";
			JSONArray array = new JSONArray();
			for (int i = 0; i < 11; i++) {
				JSONObject object = new JSONObject();
				if (i==0) {
					deptId = "1";
					deptName = "内科";
				}else if(i==1){
					deptId = "2";
					deptName = "外科";
				}else if(i==2){
					deptId = "3";
					deptName = "心内科";
				}else if(i==3){
					deptId = "4";
					deptName = "中医科";
				}else if(i==4){
					deptId = "5";
					deptName = "中医内科";
				}else if(i==5){
					deptId = "6";
					deptName = "中医外科";
				}else if(i==6){
					deptId = "7";
					deptName = "肾外科";
				}else if(i==7){
					deptId = "8";
					deptName = "男科";
				}else if(i==8){
					deptId = "9";
					deptName = "女科";
				}else if(i==9){
					deptId = "10";
					deptName = "产科";
				}else if(i==10){
					deptId = "11";
					deptName = "妇产科";
				}
				object.put("deptId", deptId);
				object.put("deptName", deptName);
				array.add(object);
			}
			res.put("list", array);
			code = 0;
			info = "获取科室列表成功！";
			res.put("hospital_id", hospitalId);
		} catch (Exception e) {
			LOG.error("获取科室列表失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "获取科室列表失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}
	
	/**
	 * 科室详情
	 * @param request
	 * @param map
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, params = "action=deptInfoIntroduce")
	public String deptInfoIntroduce(HttpServletRequest request, ModelMap map,
			HttpServletResponse response) throws IOException {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String hospitalId = request.getParameter("hospital_id");
			String deptID = request.getParameter("deptID");
			JSONObject object = new JSONObject();
			object.put("id", 1);
			object.put("deptId", 1);
			object.put("deptName", "神经内科");
			object.put("deptSC", "这是擅长这是擅长这是擅长这是擅长");
			object.put("deptIntro", "这是神经内科这是神经内科这是神经内科这是神经内科这是神经内科这是神经内科这是神经内科这是神经内科这是神经内科这是神经内科这是神经内科这是神经内科这是神经内科这是神经内科");
			res.put("deptInfo", object);
			code = 0;
			info = "获取科室详情成功！";
			res.put("hospital_id", hospitalId);
		} catch (Exception e) {
			LOG.error("获取科室详情失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "获取科室详情失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}
	
	/**
	 * 医生列表
	 * @param request
	 * @param map
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, params = "action=doctListIntroduce")
	public String doctListIntroduce(HttpServletRequest request, ModelMap map,
			HttpServletResponse response) throws IOException {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String hospitalId = request.getParameter("hospital_id");
			String deptID = request.getParameter("deptID");
			String doctId = "";
			String doctName = "";
			String doctIntro = "";
			String doctImage = "";
			String doctorCode = "";
			String doctPosition = "";
			JSONArray array = new JSONArray();
			for (int i = 0; i < 4; i++) {
				JSONObject object = new JSONObject();
				if (i==0) {
					doctId = "1";
					doctorCode = "1";
					doctName = "王帅哥";
					doctPosition = "主任医师";
					doctImage = "https://huatuoapi.zwjk.com/images/upload/doctor/2014/12/09/1418090889207_84_120.jpg";
					doctIntro = "王帅哥很牛逼王帅哥很牛逼王帅哥很牛逼王帅哥很牛逼王帅哥很牛逼王帅哥很牛逼李帅哥很牛逼李帅哥很牛逼李帅哥很牛逼李帅哥很牛逼李帅哥很牛逼";
				}else if(i==1){
					doctId = "2";
					doctorCode = "1";
					doctName = "李帅哥";
					doctPosition = "副主任医师";
					doctImage = "https://huatuoapi.zwjk.com/images/upload/doctor/2014/12/09/1418091270476_42_60.jpg";
					doctIntro = "李帅哥很牛逼李帅哥很牛逼李帅哥很牛逼李帅哥很牛逼李帅哥很牛逼李帅哥很牛逼李帅哥很牛逼李帅哥很牛逼李帅哥很牛逼";
				}else if(i==2){
					doctId = "3";
					doctorCode = "1";
					doctName = "赵美女";
					doctPosition = "医师";
					doctImage = "https://huatuoapi.zwjk.com/images/upload/doctor/2014/12/09/1418092583279_84_120.jpg";
					doctIntro = "赵美女很牛逼赵美女很牛逼赵美女很牛逼";
				}else if(i==3){
					doctId = "4";
					doctorCode = "1";
					doctName = "唐帅哥";
					doctPosition = "院长";
					doctImage = "https://huatuoapi.zwjk.com/images/upload/doctor/2014/12/09/1418091338983_84_120.jpg";
					doctIntro = "唐帅哥很帅很牛逼唐帅哥很帅很牛逼唐帅哥很帅很牛逼";
				}
				object.put("doctId", doctId);
				object.put("doctName", doctName);
				object.put("doctIntro", doctIntro);
				object.put("doctorCode", doctorCode);
				object.put("doctImage", doctImage);
				object.put("doctPosition", doctPosition);
				array.add(object);
			}
			res.put("list", array);
			code = 0;
			info = "获取科室下医生列表成功！";
			res.put("hospital_id", hospitalId);
		} catch (Exception e) {
			LOG.error("获取科室下医生列表失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "获取科室下医生列表失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}
	
	/**
	 * 医生详情
	 * @param request
	 * @param map
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, params = "action=doctInfoIntroduce")
	public String doctInfoIntroduce(HttpServletRequest request, ModelMap map,
			HttpServletResponse response) throws IOException {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		try {
			String hospitalId = request.getParameter("hospital_id");
			String doctID = request.getParameter("doctID");
			String doctCode = request.getParameter("doctCode");
			JSONObject object = new JSONObject();
			object.put("id", 1);
			object.put("doctName", "王帅哥");
			object.put("doctImage", "https://huatuoapi.zwjk.com/images/upload/doctor/2014/12/09/1418090889207_84_120.jpg");
			object.put("doctPosition", "主任医师");
			object.put("doctSC", "这是擅长这是擅长这是擅长这是擅长");
			object.put("doctIntro", "这是王帅哥简介这是王帅哥简介这是王帅哥简介这是王帅哥简介这是王帅哥简介这是王帅哥简介这是王帅哥简介这是王帅哥简介这是王帅哥简介这是王帅哥简介");
			res.put("doctInfo", object);
			code = 0;
			info = "获取医生详情成功！";
			res.put("hospital_id", hospitalId);
		} catch (Exception e) {
			LOG.error("获取医生详情失败：" + HnsrmyyUtil.printErrInfo(e));
			info = "获取医生详情失败";
		}
		res.put("ret_code", code);
		res.put("ret_info", info);
		return res.toString();
	}
	
}
