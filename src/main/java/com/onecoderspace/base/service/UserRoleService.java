package com.onecoderspace.base.service;

import java.util.List;
import java.util.Set;

import com.onecoderspace.base.component.common.service.BaseService;
import com.onecoderspace.base.domain.UserRole;

/**
 *User role
 */
public interface UserRoleService extends BaseService<UserRole, Integer>{

	List<UserRole> findByUserId(int uid);

	void deleleByUserId(int uid);

	/**
	 * Add a role
	 * @author yangwk
	 * @time July 28, 2017 2:12:35 PM
	 * @param userId user ID
	 * @param code character encoding
	 */
	void add(int id, String code);

	
	Set<Integer> findRoleIds(int userId);
	
}
