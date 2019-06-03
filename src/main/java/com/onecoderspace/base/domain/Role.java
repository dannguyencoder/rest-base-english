package com.onecoderspace.base.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.onecoderspace.base.component.common.domain.BaseModel;

/**
 * Character
 */
@Entity
@Table(name = "role")
public class Role implements BaseModel<Integer>{
	
	private static final long serialVersionUID = 254681740752863107L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;//Primary key
	
	@Column(name="name",length=50)
	private String name; //The name of the role, usually in Chinese, for display
	
	@Column(name="code",length=45)
	private String code; //The value of the role, used to verify permissions, usually in English
	
	@Column(name="remark",length=255)
	private String remark; //Remarks
	
	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	
   	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", code=" + code
				+ ", remark=" + remark + "]";
	}

}
