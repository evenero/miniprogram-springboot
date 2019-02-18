package com.ucmed.web.controller.dashborad;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ucmed.model.config.CacheConfig;
import com.ucmed.model.exception.ErrorInfo;
import com.ucmed.biz.api.admin.FunctionConfigApi;
import com.ucmed.model.utils.DateUtil;
import com.ucmed.model.utils.JsonUtils;
import com.ucmed.shiro.model.bean.pojo.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/admin")
public class DashboradController {
	private static final Logger LOG = LoggerFactory.getLogger(DashboradController.class);
	@Autowired
	private CacheConfig cacheConfig;
	@Autowired
	private ErrorInfo errorInfo;
	@Autowired
	private FunctionConfigApi functionConfigApi;
	/**
	 * 管理员登录页面
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
		System.err.println(cacheConfig.toString());
		System.err.println(errorInfo.toString());
		return "admin/htmls/login";
	}
	/**
	 * 管理员登录
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST, value="/login")
	public String loginPost(HttpServletRequest request, HttpSession session) {
		JSONObject res = new JSONObject();
		int code = 1;
		String info = "";
		JSONObject params = JsonUtils.parseRequestToJsonobject(request);
		String username = params.optString("account");
		String password = params.optString("password");
		UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,password);
		Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);//交给shiro执行登录校验
            User user=(User) subject.getPrincipal();
            session.setAttribute("user", user);
            code = 0;
            info = "登录成功";
        } catch (IncorrectCredentialsException e) {
        	info = "登录密码错误.";
        } catch (ExcessiveAttemptsException e) {
        	info = "登录失败次数过多";
        } catch (LockedAccountException e) {
        	info = "帐号已被锁定.";
        } catch (DisabledAccountException e) {
        	info = "帐号已被禁用.";
        } catch (ExpiredCredentialsException e) {
        	info = "帐号已过期.";
        } catch (UnknownAccountException e) {
        	info = "帐号不存在.";
        } catch (UnauthorizedException e) {
        	info = "您没有得到相应的授权！" + e.getMessage();
        } catch(Exception e) {
        	LOG.info("管理员登录错误，错误信息为："+e);
            info = "登录错误，错误信息："+e.getMessage();//抛出错误信息
        }
        res.put("ret_code", code);
        res.put("ret_info", info);
        return res.toString();
	}
	
	@RequestMapping("/logout")
    public String logOut(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        session.removeAttribute("user");
        return "login";
    }
	
	/**
	 * 管理员登录进入首页面
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, value="/index")
	@RequiresAuthentication
	public String index(HttpServletRequest request, 
			HttpServletResponse response, ModelMap map){
		//获取已登录用户信息
//		Object tmp = SecurityUtils.getSubject().getPrincipal();
		//因为devtools的热部署容器不同，导致直接强转的话会报错，需代码处理
//		User user = ShiroUtils.convertObjectToBean(tmp, User.class);
//		Integer orderNum = 0;
//		Integer orderCurNum = 0;
//		Integer clinicNum = 0;
//		Integer inHospitalNum = 0;
//		Integer payNum = 0;
//		String queryNum = request.getParameter("queryNum") == null?"day":request.getParameter("queryNum");
//		JSONObject params = new JSONObject();
//		params.put("query_num", queryNum);
//		apiCommonService.getAdminIndext(params, map);
//		orderNum = (Integer) map.get("orderNum");
//		orderCurNum = (Integer) map.get("orderCurNum");
//		clinicNum = (Integer) map.get("clinicNum");
//		inHospitalNum = (Integer) map.get("inHospitalNum");
//		payNum = orderNum + orderCurNum + clinicNum + inHospitalNum;
//		map.put("payNum",payNum);
		map.put("nowdate", DateUtil.getyyyy_MM_dd(new Date()));
		return "admin/htmls/dashboard";
	}
	/**
	 * 功能管理
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/config")
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
	@RequestMapping(method=RequestMethod.POST, value="/config")
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
	@RequestMapping(method=RequestMethod.POST, value="/config/edit")
	public String funcConfigEditPost(HttpServletRequest request, 
			HttpServletResponse response, ModelMap map){
		JSONObject params = JsonUtils.parseRequestToJsonobject(request);
		params.put("type", "edit");
		JSONObject res = functionConfigApi.execute(params);
		return res.toString();
	}
	/**
	 * 轮播图管理
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET,value="/rotation_chart")
	public String rotationChartManage() {
		return "admin/htmls/rotationChartManagent";
	}
}
