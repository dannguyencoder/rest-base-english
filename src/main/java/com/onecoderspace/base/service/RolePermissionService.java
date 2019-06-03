package com.onecoderspace.base.service;

import java.util.List;
import java.util.Set;

import com.onecoderspace.base.component.common.service.BaseService;
import com.onecoderspace.base.domain.Permission;
import com.onecoderspace.base.domain.RolePermission;

/**
 *Role permission association table
 */
public interface RolePermissionService extends BaseService<RolePermission, Integer>{

	List<RolePermission> findByRoleId(int roleId);

	void deleleByRoleId(int roleId);

	List<Permission> getPermissions(Set<Integer> roles);

	
}
