package com.onecoderspace.base.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.onecoderspace.base.component.common.service.BaseService;
import com.onecoderspace.base.domain.User;
import com.onecoderspace.base.util.Return;

/**
 *用户
 */
public interface UserService extends BaseService<User, Integer>{

	User findByUsername(String username);

	/**
	 * set password
	 * @author yangwk
	 * @time July 27, 2017 10:15:12 AM
	 * @param entity
	 * @param pwd
	 * @return
	 */
	Return changePwd(User entity, String pwd);

	/**
	 * Conditional paging query
	 * @author yangwk
	 * @time July 27, 2017 10:15:26 AM
	 * @param params
	 * @param pageable
	 * @return
	 */
	Page<User> listByPage(Map<String, String> params,Pageable pageable);

	/**
	 * Save the audit results
	 * @author yangwk
	 * @time July 27, 2017 10:14:11 AM
	 * @param entity material new
	 * @param value Audit result 1 Pass 0 failed
	 * @param msg Remarks new
	 * @return
	 */
	Return doAudit(User entity, int value, String msg);

	/**
	 * Set the role of the user
	 * @author yangwk
	 * @time July 27, 2017 10:15:01 AM
	 * @param uid
	 * @param roleIds
	 * @return
	 */
	Return doSetRoles(int uid, String roleIds);

	/**
	 * Save user
	 * @author yangwk
	 * @time July 28, 2017 1:37:24 PM
	 * @param user User Information
	 * @param company Company Information
	 * @return
	 */
	Return saveUser(User user);
	
}
