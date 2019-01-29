package com.ucmed.shiro.exception;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
 
/**
 * ucmed
 * 全局异常处理
 */
public class MyExceptionHandler implements HandlerExceptionResolver {
	private static final Logger LOG = LoggerFactory.getLogger(MyExceptionHandler.class);
 
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception ex) {
    	String code = "";
    	String info = "";
        ModelAndView mv = new ModelAndView();
        httpServletResponse.setStatus(HttpStatus.OK.value()); //设置状态码
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType
        httpServletResponse.setCharacterEncoding("UTF-8"); //避免乱码
        httpServletResponse.setHeader("Cache-Control", "no-cache, must-revalidate");
        if (ex instanceof UnauthenticatedException) {
			code = "1000001";
			info = "token错误";
		} else if (ex instanceof UnauthorizedException) {
			code = "1000002";
			info = "用户无权限";
		} else {
			code = "1000003";
			info = ex.getMessage();
		}
		returnResInfo(httpServletResponse, code, info);
        return mv;
    }
    
    private void returnResInfo(HttpServletResponse httpServletResponse, String code, String info) {
    	JSONObject res = new JSONObject();
    	res.put("code", code);
    	res.put("msg", info);
    	try {
			httpServletResponse.getWriter().write(res.toString());
		} catch (IOException e) {
			LOG.info(""+e);
		}
    }
}