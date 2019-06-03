package com.onecoderspace.base.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;

import com.onecoderspace.base.util.security.xss.JsoupUtil;

/** 
 * <code>{@link XssHttpServletRequestWrapper}</code> 
 */  
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {  
    HttpServletRequest orgRequest = null;  
    private boolean isIncludeRichText = false;
  
    public XssHttpServletRequestWrapper(HttpServletRequest request, boolean isIncludeRichText) {  
        super(request);  
        orgRequest = request;
        this.isIncludeRichText = isIncludeRichText;
    }  
  
    /** 
    * Override the getParameter method, and filter the parameter name and parameter values by xss. Tr
    * If you need to get the original value, get it by super.getParameterValues(name)<br/>
    * getParameterNames, getParameterValues and getParameterMap may also need to be overwritten
    */  
    @Override  
    public String getParameter(String name) {  
	    	if(("content".equals(name) || name.endsWith("WithHtml")) && !isIncludeRichText){
	    		return super.getParameter(name);
	    	}
	    	name = JsoupUtil.clean(name);
        String value = super.getParameter(name);  
        if (StringUtils.isNotBlank(value)) {
            value = JsoupUtil.clean(value);  
        }
        return value;  
    }  
    
    @Override
    public String[] getParameterValues(String name) {
    	String[] arr = super.getParameterValues(name);
    	if(arr != null){
    		for (int i=0;i<arr.length;i++) {
    			arr[i] = JsoupUtil.clean(arr[i]);
    		}
    	}
    	return arr;
    }
    
  
    /** 
    * Override the getHeader method, and filter the parameter name and parameter values by xss. Tr
    * If you need to get the original value, get it by super.getHeaders(name)<br/>
    * getHeaderNames may also need to be overwritten
    */  
    @Override  
    public String getHeader(String name) {  
    		name = JsoupUtil.clean(name);
        String value = super.getHeader(name);  
        if (StringUtils.isNotBlank(value)) {  
            value = JsoupUtil.clean(value); 
        }  
        return value;  
    }  
  
    /** 
    * Get the most original request
    * 
    * @return 
    */  
    public HttpServletRequest getOrgRequest() {  
        return orgRequest;  
    }  
  
    /** 
    * Get the static method of the most primitive request
    * 
    * @return 
    */  
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {  
        if (req instanceof XssHttpServletRequestWrapper) {  
            return ((XssHttpServletRequestWrapper) req).getOrgRequest();  
        }  
  
        return req;  
    }  
  
}  
