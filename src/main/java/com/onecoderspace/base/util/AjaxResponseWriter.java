package com.onecoderspace.base.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Maps;

/**
 * When the current end requests through ajax, it directly writes the returned result back to the front end.
 * @author yangwk
 */
public class AjaxResponseWriter {

	/**
	 * Write back data to the front end
	 * @param request
	 * @param response
	 * @param status {@link ServiceStatusEnum}
	 * @param message returned description
	 * @throws IOException
	 */
	public static void write(HttpServletRequest request,HttpServletResponse response,ServiceStatusEnum status,String message) throws IOException{
		String contentType = "application/json";
		response.setContentType(contentType);
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
		
		Map<String, String> map = Maps.newLinkedHashMap();
		map.put("service_status", status.code);
		map.put("description", message);
		String result = JacksonHelper.toJson(map);
		PrintWriter out = response.getWriter();
		try{
			out.print(result);
			out.flush();
		} finally {
			out.close();
		}
	}
	
}
