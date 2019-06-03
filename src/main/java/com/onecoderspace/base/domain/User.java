package com.onecoderspace.base.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.onecoderspace.base.component.common.domain.BaseModel;

import java.sql.Timestamp;

/**
 * user
 */
@Entity
@Table(name = "user")
public class User implements BaseModel<Integer>{
	
	private static final long serialVersionUID = -91221104520172449L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;//Primary key
	
	@ApiModelProperty(value="User Category 0 General User 1 Operator")
	@Column(columnDefinition="tinyint default 0")
	private int type = 0;
	
	@ApiModelProperty(value="account number")
	@Column(name="username",length=50)
	private String username; 
	
	@ApiModelProperty(value="Name")
	@Column(name="name",length=50)
	private String name; 
	
	@ApiModelProperty(value="password（md5(mix(password,salt))）")
	@Column(name="pwd",length=50)
	private String pwd;
	
	@ApiModelProperty(value="Password-encrypted \"salt\"")
	@Column(name="salt",length=16)
	private String salt;
	
	@ApiModelProperty(value="phone number")
	@Column(name="mobile",length=15)
	private String mobile;
	
	@ApiModelProperty(value="mailbox")
	@Column(name="email",length=50)
	private String email; 
	
	@ApiModelProperty(value="nickname")
	@Column(name="nick_name",length=50)
	private String nickName;
	
	@ApiModelProperty(value="Gender 0 is not set 1 male 2 female")
	@Column(name="gender",columnDefinition="tinyint default 0")
	private int gender = 0;
	
	@ApiModelProperty(value="Account Status 0Registered Unreviewed 1 Pending Review 2 Audit Passed -1 Review failed")
	@Column(name="status",columnDefinition="tinyint default 0")
	private int status = 0;
	
	@ApiModelProperty(value="Whether the account is valid 1 valid 0 is invalid")
	@Column(name="enable",columnDefinition="tinyint default 1")
	private int enable = 0; 
	
	@ApiModelProperty(value="User avatar relative path, do not bring a domain name")
	@Column(name="avatar",length=255)
	private String avatar;
	
	@ApiModelProperty(value="Registration time")
	@Column(name="register_time")
	private Timestamp registerTime;
	
	@ApiModelProperty(value="Last updater")
	@Column(name="updator",columnDefinition="int default 0")
	private int updator = 0;
	
	@ApiModelProperty(value="Last update time")
	@Column(name="update_time")
	private Timestamp updateTime;
	
	@ApiModelProperty(value="Tag Delete Field 1 Deleted 0 Not Deleted")
	@Column(name="del",columnDefinition="tinyint default 0")
	private int del = 0;
	
	
	//Account Status 0Registered Unreviewed 1 Pending Review 2 Audit Passed -1 Review failed
	public static final int	STATUS_UNAUDIT = 0;
	public static final int	STATUS_WAIT_AUDIT = 1;
	public static final int	STATUS_AUDIT_PASS = 2;
	public static final int	STATUS_AUDIT_UNPASS = -1;
	
	//User Category 0 General User 1 Operator
	public static final int	TYPE_USER = 0;
	public static final int	TYPE_OPERATOR = 1;
	
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
   	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
   	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
   	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
   	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
   	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
   	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
   	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}
	
   	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
   	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}
	
   	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
   	public Timestamp getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}
	
   	public int getUpdator() {
		return updator;
	}

	public void setUpdator(int updator) {
		this.updator = updator;
	}
	
   	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
   	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}

	@Override
	public String toString() {
		return String
				.format("User [id=%s, type=%s, username=%s, name=%s, pwd=%s, salt=%s, mobile=%s, email=%s, nickName=%s, gender=%s, status=%s, enable=%s, avatar=%s, registerTime=%s, updator=%s, updateTime=%s, del=%s]",
						id, type, username, name, pwd, salt, mobile, email,
						nickName, gender, status, enable, avatar, registerTime,
						updator, updateTime, del);
	}

}
