package com.ucmed.web.controller.verifycode;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ucmed.model.utils.ImageVerifyCodeUtil;
import com.ucmed.model.utils.RedisUtils;


/**
 * 图形验证码控制器
 * @author ucmed
 *
 */
@Controller
@RequestMapping(value="/imgverifycode")
public class ImageVerifyCodeController {
	private final static Logger LOG = LoggerFactory.getLogger(ImageVerifyCodeController.class);
	
	@Autowired
	private RedisUtils redisUtils;
	
	/**
	 * 获取验证码
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(method=RequestMethod.GET)
	public void getImageVerifyCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// 设置响应的类型格式为图片格式
	    response.setContentType("image/jpeg");
	    //禁止图像缓存。
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Cache-Control", "no-cache");
	    response.setDateHeader("Expires", 0);
	    ImageVerifyCodeUtil vCode = new ImageVerifyCodeUtil(120,40,4,50);
	    HttpSession session = request.getSession();
	    session.setAttribute("vCode", vCode.getCode());
	    String uuid = request.getParameter("uuid");
	    redisUtils.set(redisUtils.getCacheConfig().getImageVerifyCodePrefix()+uuid, vCode.getCode(),redisUtils.getCacheConfig().getImageVerifyCodeTimeOut(), TimeUnit.MINUTES); //放入缓存
	    vCode.write(response.getOutputStream());
	}
	/**
	 * 校验验证码是否正确，ajax调用
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws Exception
	 */
	@RequestMapping(method=RequestMethod.POST)
	public void checkImageValidateCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String code = request.getParameter("vcode");
		String uuid = request.getParameter("uuid");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String verifyCode = (String) redisUtils.get(redisUtils.getCacheConfig().getImageVerifyCodePrefix()+uuid);
		if(null!=verifyCode && null!= code && !"".equals(verifyCode) && !"".equals(code)) {
			if (!StringUtils.equalsIgnoreCase(code, verifyCode)) {  //忽略验证码大小写 
				response.getWriter().write("图形验证码错误");
				LOG.error("验证码对应不上code=" + code + "  sessionCode=" + verifyCode);
			}else {
				response.getWriter().write("true");
			}
		redisUtils.remove(redisUtils.getCacheConfig().getImageVerifyCodePrefix()+uuid); //验证完成后删除缓存
		}else {
			response.getWriter().write("图形验证码过期,请刷新验证码");
		}
	}
}
