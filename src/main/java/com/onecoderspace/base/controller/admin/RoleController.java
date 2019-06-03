package com.onecoderspace.base.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

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

import com.onecoderspace.base.domain.Role;
import com.onecoderspace.base.domain.RolePermission;
import com.onecoderspace.base.service.RolePermissionService;
import com.onecoderspace.base.service.RoleService;
import com.onecoderspace.base.util.Return;

@Api(value="Role management",tags={"Operational interface role"})
@RestController
@RequestMapping("/admin/role")
public class RoleController {

	@ApiOperation(value="Paging query",notes="Paging query")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name="page",value="Pagination, page number starts from 0",required=true,dataType="int"),
		@ApiImplicitParam(paramType="query",name="size",value="Size per page",required=true,dataType="int")}
	)
	@RequiresPermissions(value={"admin:permission:list"})
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Page<Role> list(String name, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Page<Role> rs = this.roleService.listByPage(name, new PageRequest(page, size, new Sort(Direction.DESC, "id")));
		return rs;
	}

	@ApiOperation(value="Add & Modify",notes="Add & Modify")
	@ApiImplicitParam(paramType="query",name="role",value="角色",dataType="Role")
	@RequiresPermissions(value={"admin:permission:save"})
	@RequestMapping(value="/save",method=RequestMethod.GET)
	public Return save(Role role) {
		this.roleService.save(role);
		return Return.success();
	}

	@ApiOperation(value="Inquire",notes="Query based on id")
	@ApiImplicitParam(paramType="query",name="id",value="角色id",required=true,dataType="int")
	@RequiresPermissions(value={"admin:permission:get"})
	@RequestMapping(value="/get",method=RequestMethod.GET)
	public Role get(int id) {
		Role entity = roleService.findById(id);
		return entity;
	}
	
	@ApiOperation(value="delete",notes="Delete by id")
	@ApiImplicitParam(paramType="query",name="id",value="Role id",required=true,dataType="int")
	@RequiresPermissions(value={"admin:permission:delete"})
	@RequestMapping("/delete")
	public Return delete(int id){
		this.roleService.del(id);
		return Return.success();
	}

	@ApiOperation(value = "Role has permissions", notes = "Query the permissions owned by the user based on the id")
	@ApiImplicitParam(paramType="query",name="roleId",value="Role id",required=true,dataType="int")
	@RequiresPermissions(value={"admin:permission:permission:list"})
	@RequestMapping(value="/permission/list",method=RequestMethod.GET)
	public List<RolePermission> permissionList(int roleId) {
		List<RolePermission> rolePermissions = rolePermissionService.findByRoleId(roleId);
		return rolePermissions;
	}

	@ApiOperation(value = "Set the permissions that the role has", notes = "Set permissions based on role id")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "roleId", value = "角色id", required = true, dataType = "int"),
			@ApiImplicitParam(paramType = "query", name = "permissionIds", value = "Privilege ID, multiple privilege IDs separated by commas", required = true, dataType = "int") })
	@RequiresPermissions(value = { "admin:permission:permission:set" })
	@RequestMapping(value = "/permission/set", method = RequestMethod.POST)
	public Return permissionSet(int roleId, String permissionIds) {
		return roleService.doSetPermissions(roleId, permissionIds);
	}

	@Autowired
	private RoleService roleService;

	@Autowired
	private RolePermissionService rolePermissionService;
}