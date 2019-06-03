package com.onecoderspace.base.component.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

/**
 * Global exception capture
 * @author yangwenkui
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

	/**
	 * Unauthorized exception handling
	 * @author yangwk
	 * @return
	 */
	@ExceptionHandler(value = UnauthorizedException.class)
	@ResponseBody
	public Map<String, Object> unauthorizedHandler(HttpServletRequest req,HttpServletResponse response, Exception e) {
		// 打印异常信息：
		response.setStatus(403);
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("code", "-10000");
		map.put("msg", "You don't have access");
		return map;
	}
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Map<String, Object> defaultErrorHandler(HttpServletRequest req,HttpServletResponse response, Exception e) {
		// 打印异常信息：
		logger.error("defaultErrorHandler:", e);
		response.setStatus(500);
		
		Map<String, Object> map = Maps.newHashMap();
		map.put("code", "-10000");
		map.put("msg", "The system is busy");
		return map;
	}

}
