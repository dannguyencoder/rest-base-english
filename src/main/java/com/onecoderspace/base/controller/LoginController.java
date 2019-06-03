package com.onecoderspace.base.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.onecoderspace.base.domain.User;
import com.onecoderspace.base.service.UserService;
import com.onecoderspace.base.util.SaltMD5Util;

@Api(value="User login",tags={"User login"})
@RestController
public class LoginController {
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Value("${server.session.timeout}")
	private String serverSessionTimeout;

	/**
	 * User login interface Log in with username and password
	 */
	@ApiOperation(value = "User login interface Log in with username and password", notes = "User login interface Log in with username and password")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "pwd", value = "密码", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "autoLogin", value = "自动登录", required = true, dataType = "boolean")})
	@RequestMapping(value = "/login/submit",method={RequestMethod.GET,RequestMethod.POST})
	public Map<String, String> subm(HttpServletRequest request,HttpServletResponse response,
			String username,String pwd,@RequestParam(value = "autoLogin", defaultValue = "false") boolean autoLogin) {
		Map<String, String> map = Maps.newLinkedHashMap();
		Subject currentUser = SecurityUtils.getSubject();
		User user = userService.findByUsername(username);
		if (user == null) {
			map.put("code", "-1");
			map.put("description", "Account does not exist");
			return map;
		}
		if (user.getEnable() == 0) { //账号被禁用
			map.put("code", "-1");
			map.put("description", "Account has been disabled");
			return map;
		}

		String salt = user.getSalt();
		UsernamePasswordToken token = null;
		Integer userId = user.getId();
		token = new UsernamePasswordToken(userId.toString(),SaltMD5Util.encode(pwd, salt));
		token.setRememberMe(autoLogin);

		loginValid(map, currentUser, token);

		// Verify that the login is successful
		if (currentUser.isAuthenticated()) {
			map.put("code","1");
			map.put("description", "ok");
			map.put("id", String.valueOf(userId));
			map.put("username", user.getUsername());
			map.put("name", user.getName());
			String uuidToken = UUID.randomUUID().toString();
			map.put("token", uuidToken);
			
			currentUser.getSession().setTimeout(NumberUtils.toLong(serverSessionTimeout, 1800)*1000);
			request.getSession().setAttribute("token",uuidToken );
		} else {
			map.put("code", "-1");
			token.clear();
		}
		return map;
	}
	
	@RequestMapping(value="test/set",method=RequestMethod.GET)
    public String testSet(HttpServletRequest request) {
		request.getSession().setAttribute("test", "test");
        return "success";
    }
	
	@RequestMapping(value="test/get",method=RequestMethod.GET)
    public String testGet(HttpServletRequest request) {
		String value = (String) request.getSession().getAttribute("test");
        return value == null ? "null" : value;
    }
	
	@RequestMapping(value="logout",method=RequestMethod.GET)
    public Map<String, String> logout() {
        Map<String, String> map = Maps.newLinkedHashMap();
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        map.put("code", "logout");
        return map;
    }
	
	@RequestMapping(value="unauth",method=RequestMethod.GET)
    public Map<String, String> unauth() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("code", "403");
        map.put("msg", "你没有访问权限");
        return map;
    }

	private boolean loginValid(Map<String, String> map,Subject currentUser, UsernamePasswordToken token) {
		String username = null;
		if (token != null) {
			username = (String) token.getPrincipal();
		}

		try {
			// After calling the login method, the SecurityManager will receive the AuthenticationToken and send it to the configured Realm to perform the necessary authentication checks.
			// Each Realm can react to the submitted AuthenticationTokens when necessary
			// So when this step calls the login(token) method, it will go to the MyRealm.doGetAuthenticationInfo() method. See the method for the specific verification method.
			currentUser.login(token);
			return true;
		} catch (UnknownAccountException | IncorrectCredentialsException ex) {
			map.put("description", "Incorrect username or password");
		} catch (LockedAccountException lae) {
			map.put("description","Account locked");
		} catch (ExcessiveAttemptsException eae) {
			map.put("description", "Too many errors");
		} catch (AuthenticationException ae) {
			// By handling Shiro's runtime AuthenticationException, you can control the situation when the user fails to log in or the password is wrong.
			map.put("description", "Login failed");
			logger.warn(String.format("Login verification for user [%s]: verification failed", username),ae);
		}
		return false;
	}
	
	@Autowired
	private UserService userService;
}
