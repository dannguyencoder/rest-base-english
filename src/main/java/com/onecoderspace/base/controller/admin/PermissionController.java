package com.onecoderspace.base.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.onecoderspace.base.domain.Permission;
import com.onecoderspace.base.service.PermissionService;
import com.onecoderspace.base.util.Return;


@Api(value = "Operation interface", tags = { "Operation interface" })
@RestController
@RequestMapping("/admin/permission")
public class PermissionController {
	
	@ApiOperation(value = "Get all permissions", notes = "Get all permissions")
	@RequiresPermissions(value={"admin:permission:list"})
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Iterable<Permission> list(){
		Iterable<Permission> list = this.permissionService.findAll(new Sort(Direction.ASC, "weight","id"));
		return list;
	}	
	
	@ApiOperation(value = "Add/Modify Permissions", notes = "Save New/Modified Permissions")
	@ApiImplicitParam(paramType = "query", name = "permission", value = "Menu operation authority entity", required = true, dataType = "Permission")
	@RequiresPermissions(value={"admin:permission:save"})
	@RequestMapping(value="/save",method=RequestMethod.GET)
	public Return save(Permission permission){
		this.permissionService.save(permission);
		return Return.success();
	}
	
	
	@ApiOperation(value = "Delete permission", notes = "Delete permissions based on id")
	@ApiImplicitParam(paramType = "query", name = "id", value = "Permission id", required = true, dataType = "int")
	@RequiresPermissions(value={"admin:permission:delete"})
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public Return delete(int id){
		this.permissionService.del(id);
		return Return.success();
	}

	@ApiOperation(value = "Get permission", notes = "Query permissions based on id")
	@ApiImplicitParam(paramType = "query", name = "id", value = "权限id", required = true, dataType = "int")
	@RequiresPermissions(value={"admin:permission:get"})
	@RequestMapping(value="/get",method=RequestMethod.GET)
	public Permission get(int id){
		Permission entity = permissionService.findById(id);
		return entity;
	}	
	
	@Autowired
	private PermissionService permissionService;
	
}