package com.onecoderspace.base.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.request.RequestContextHolder;

public class LoginSessionHelper {

	/**
	 * Return current user ID
	 * @return
	 */
	public static int getCurrentUserId(){
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser == null || currentUser.getPrincipal() == null){
			return 0;
		}
		Integer userId = (Integer) currentUser.getPrincipal();
		return userId;
	}
	
	/**
	 * Returns the current user unique token. When the user is logged in, the user ID is returned. When the user is not logged in, the sessionId is returned.
	 * @return
	 */
	public static String getCurrentUserKey(){
		Subject currentUser = SecurityUtils.getSubject();
		Integer userId = (Integer) currentUser.getPrincipal();
		if(userId == null){
			String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
			return sessionId;
		}
		return String.valueOf(userId);
	}
}
