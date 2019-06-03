package com.onecoderspace.base.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.onecoderspace.base.component.common.domain.BaseModel;

/**
 * Role permission association table
 */
@Entity
@Table(name = "role_permission")
public class RolePermission implements BaseModel<Integer>{
	
	private static final long serialVersionUID = 2562960339678855205L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;//Primary key
	
	@Column(name="role_id",columnDefinition="int default 0")
	private int roleId; //Role ID
	
	@Column(name="permission_id",columnDefinition="int default 0")
	private int permissionId; //Privilege ID
	
	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
   	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public int getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

	@Override
	public String toString() {
		return "RolePermission [id=" + id + ", roleId=" + roleId
				+ ", permissionId=" + permissionId + "]";
	}

}
