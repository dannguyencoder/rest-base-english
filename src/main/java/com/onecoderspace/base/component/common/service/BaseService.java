package com.onecoderspace.base.component.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.onecoderspace.base.component.common.domain.BaseModel;

public interface BaseService<T extends BaseModel<ID>, ID extends Serializable> {

	/**
	 * Add or update
	 */
	T save(T t);

	/**
	 * Add or update
	 * Note that the number is not too large, especially when using data migration.
	 */
	Iterable<T> save(Iterable<T> entities);

	/**
	 * Delete by ID
	 */
	void del(ID id);

	/**
	 * Delete by entity
	 */
	void del(T t);

	/**
	 * Find objects by ID
	 */
	T findById(ID id);

	List<T> findAll();

	/**
	 * Paging sorting to get data
	 * Do not use this interface for count operations.
	 * Pageable pageable = new PageRequest(0, 10, new Sort(Sort.Direction.DESC,"id"));
	 * @param pageable
	 * @return
	 */
	Page<T> findAll(Pageable pageable);

	/**
	 * Multiple conditional query
	 * Note: The relationship between multiple conditions is and relationship & parameter is the type corresponding to the attribute. Please avoid using the result set too large.
	 * @author yangwk
	 * @time 2017年8月1日 下午3:50:46
	 * @param params {"username:like":"test"} The format of the key is the field name: filtering method, see {@code QueryTypeEnum} for filtering.
	 * @return
	 */
	List<T> list(Map<String, Object> params);

	/**
	 * Paging multi-condition query
	 * Note: Multiple conditions are between and relationship & parameter is the type corresponding to the attribute
	 * @author yangwk
	 * @time 2017年8月1日 下午3:50:46
	 * @param params {"username:like":"test"} The format of the key is the field name: filtering method, see {@code QueryTypeEnum} for filtering.
	 * @param pageable Paging information new PageRequest(page, size, new Sort(Direction.DESC, "updateTime"))
	 * @return
	 */
	Page<T> list(Map<String, Object> params,Pageable pageable);

}
