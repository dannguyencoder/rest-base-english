package com.onecoderspace.base.util;

/**
 * Global status code
 * @author yangwk
 */
public enum ServiceStatusEnum {
	UNLOGIN("0001"), //Not logged in
	ILLEGAL_TOKEN("0002"),//Illegal token
	;
	public String code;
	
	private ServiceStatusEnum(String code){
		this.code = code;
	}
	
}
