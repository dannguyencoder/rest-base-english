package com.onecoderspace.base.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.onecoderspace.base.component.common.domain.BaseModel;

/**
 * Menu and operation authority, tree structure
 */
@Entity
@Table(name = "permission")
public class  Permission implements BaseModel<Integer>{
	
	private static final long serialVersionUID = 6581772362165179231L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;//Primary key
	
	@Column(name="type",columnDefinition="tinyint default 0")
	private int type; //Permission type 0 menu 1 button 2 operation authority
	
	@Column(name="name",length=50)
	private String name; //Permission name to display
	
	@Column(name="code",length=50)
	private String code; //The value of the permission, used when verifying permissions
	
	private String url;//操作权限的url
	
	@Column(name="weight",columnDefinition="int default 10000")
	private int weight = 10000; //Sort value, ascending
	
	@Column(name="pid",columnDefinition="int default 0")
	private int pid; //Parent node ID
	
	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
   	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
   	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
   	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
   	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
   	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}
	
	@Override
	public String toString() {
		return "Permission [id=" + id + ", type=" + type + ", name=" + name
				+ ", code=" + code + ", weight=" + weight + ", pid=" + pid
				+ "]";
	}

}
