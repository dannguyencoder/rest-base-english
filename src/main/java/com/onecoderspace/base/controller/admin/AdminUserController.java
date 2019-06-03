package com.onecoderspace.base.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.onecoderspace.base.domain.User;
import com.onecoderspace.base.domain.UserRole;
import com.onecoderspace.base.service.UserRoleService;
import com.onecoderspace.base.service.UserService;
import com.onecoderspace.base.util.Constants;
import com.onecoderspace.base.util.LoginSessionHelper;
import com.onecoderspace.base.util.Return;
import com.onecoderspace.base.util.SaltMD5Util;

@Api(value = "Operational interface  user", tags = { "Operational interface  user" })
@RestController("adminUserController")
@RequestMapping(value="/admin/user")
public class AdminUserController {

	@ApiOperation(value = "Paging to get user data", notes = "Get user name based on user name to get user data")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "type", value = "User Category 0 General User 1 Operator", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "status", value = "Account Status 0Registered Unreviewed 1 Pending Review 2 Audit Passed -1 Review failed", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "username", value = "username", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "name", value = "username", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "page", value = "Pagination, page number starts from 0", required = true, dataType = "int"),
			@ApiImplicitParam(paramType = "query", name = "size", value = "Size per page", required = true, dataType = "int") })
	@RequiresPermissions(value = { "admin:user:list" })
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Page<User> list(String type, String status, String username, String name,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		Map<String, String> params = Maps.newHashMap();
		params.put("type", type);
		params.put("status", status);
		params.put("username", username);
		params.put("name", name);
		Page<User> rs = this.userService.listByPage(params,new PageRequest(page, size, new Sort(Direction.DESC, "updateTime")));

		/*//Or use the public method inside the service.
		Map<String, Object> params2 = Maps.newHashMap();
		params.put("type", type);
		params.put("status", status);
		params.put("username:like", username);
		params.put("name:like", name);
		Page<User> rs2 = this.userService.list(params2, new PageRequest(page, size, new Sort(Direction.DESC, "updateTime")));
		*/
		for (User user : rs.getContent()) {
			user.setPwd(null);
			user.setSalt(null);
		}
		return rs;
	}

	@ApiOperation(value = "All information when the user is audited", notes = "All information when the user is audited: user information, company information, company qualification information, query user based on id")
	@ApiImplicitParam(paramType = "query", name = "id", value = "用户id", required = true, dataType = "int")
	@RequiresPermissions(value = { "admin:user:audit-info" })
	@RequestMapping(value = "/audit-info", method = RequestMethod.GET)
	public Map<String, Object> auditInfo(int id) {
		Map<String, Object> map = Maps.newHashMap();
		User entity = userService.findById(id);
		entity.setPwd(null);
		entity.setSalt(null);
		map.put("user", entity);
		return map;
	}

	@ApiOperation(value = "Review user account", notes = "Review user account")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "id", value = "用户id", required = true, dataType = "int"),
			@ApiImplicitParam(paramType = "query", name = "value", value = "Value 0 failed 1 passed", required = true, dataType = "int"),
			@ApiImplicitParam(paramType = "query", name = "msg", value = "Failure reason", required = true, dataType = "String") })
	@RequiresPermissions(value = { "admin:user:audit" })
	@RequestMapping(value = "/audit", method = RequestMethod.GET)
	public Return audit(int id, int value, String msg) {
		User entity = userService.findById(id);
		if (entity == null) {
			return Return.fail("User no longer exists");
		}
		if (entity.getStatus() != User.STATUS_WAIT_AUDIT) {
			return Return.fail("User is not pending review");
		}
		return userService.doAudit(entity, value, msg);
	}

	@ApiOperation(value = "Save user information", notes = "Save user information")
	@ApiImplicitParam(paramType = "query", name = "user", value = "User entity", required = true, dataType = "user")
	@RequiresPermissions(value = { "admin:user:save" })
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public Return save(User user) {
		int currentUid = LoginSessionHelper.getCurrentUserId();
		if (user.getId() != null && user.getId() != 0) {
			User old = userService.findById(user.getId());
			if (old == null) {
				return Return.fail("Information no longer exists");
			}
			user.setUsername(old.getUsername());
			user.setPwd(old.getPwd());
			user.setSalt(old.getSalt());
			user.setStatus(old.getStatus());
			user.setType(old.getType());
			user.setDel(old.getDel());
			user.setRegisterTime(old.getRegisterTime());
			user.setEnable(old.getEnable());

		} else {
			user.setDel(Constants.DEL_NO);
			user.setStatus(User.STATUS_UNAUDIT);
			user.setEnable(1);
			String salt = SaltMD5Util.getSalt();
			String pwd = SaltMD5Util.encode(user.getPwd(), salt);
			user.setPwd(pwd);
			user.setSalt(salt);
			user.setRegisterTime(new Timestamp(System.currentTimeMillis()));

		}
		user.setUpdator(currentUid);
		user.setUpdateTime(new Timestamp(System.currentTimeMillis()));

		Return re = this.userService.saveUser(user);
		return re;
	}

	@ApiOperation(value = "Tag deletion", notes = "Tag deletion")
	@ApiImplicitParam(paramType = "query", name = "id", value = "User id",dataType = "int")
	@RequiresPermissions(value = { "admin:user:delete" })
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public Return delete(int id) {
		User user = this.userService.findById(id);
		user.setDel(Constants.DEL_YES);
		userService.save(user);
		return Return.success();
	}

	@ApiOperation(value = "User-owned role", notes = " User-owned role")
	@ApiImplicitParam(paramType = "query", name = "uid", value = "User uid", required = true, dataType = "int")
	@RequiresPermissions(value = { "admin:user:role:list" })
	@RequestMapping(value="/role/list",method=RequestMethod.GET)
	public List<UserRole> roleList(int uid) {
		List<UserRole> userRoles = userRoleService.findByUserId(uid);
		return userRoles;
	}

	@ApiOperation(value = "Set user role", notes = "Set user role")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "uid", value = "用户uid", required = true, dataType = "int"),
		@ApiImplicitParam(paramType = "query", name = "roleids", value = "角色id", required = true, dataType = "String")
		})
	@RequiresPermissions(value = { "admin:user:role:set" })
	@RequestMapping(value="/role/set",method=RequestMethod.POST)
	public Return roleSet(int uid, String roleIds) {
		return userService.doSetRoles(uid, roleIds);
	}

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;
}