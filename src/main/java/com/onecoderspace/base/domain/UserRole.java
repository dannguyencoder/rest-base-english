package com.onecoderspace.base.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.onecoderspace.base.component.common.domain.BaseModel;

/**
 * User role
 */
@Entity
@Table(name = "user_role")
public class UserRole implements BaseModel<Integer>{
	
	private static final long serialVersionUID = 5806516492008208503L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;//Primary key
	
	@Column(name="user_id",columnDefinition="int default 0")
	private int userId; //User ID
	
	@Column(name="role_id",columnDefinition="int default 0")
	private int roleId; //Role ID
	
	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
   	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
   	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "UserRole [id=" + id + ", userId=" + userId + ", roleId="
				+ roleId + "]";
	}

}
