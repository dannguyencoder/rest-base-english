package com.onecoderspace.base.service;

import org.springframework.data.domain.Sort;

import com.onecoderspace.base.component.common.service.BaseService;
import com.onecoderspace.base.domain.Permission;

/**
 *Menu and operation authority, tree structure
 */
public interface PermissionService extends BaseService<Permission, Integer>{

	Iterable<Permission> findAll(Sort sort);

	
}
