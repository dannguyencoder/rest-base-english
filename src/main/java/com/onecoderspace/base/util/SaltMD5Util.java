package com.onecoderspace.base.util;

import org.apache.commons.lang3.StringUtils;


/**
  * Salted MD5 algorithm
  * Save the salt in the user account information, and md5 (compone(salt, pwd)). When the user logs in, use the same algorithm to encrypt and match.
  * This makes it extremely difficult for an attacker to crack user information even if user data is compromised.
  * @author yangwenkui
  * @version v2.0
  */
public class SaltMD5Util {

	/**
	 * Get MD5 encryption parameters
	 * @author yangwenkui
	 * @time April 25, 2017 4:53:57 PM
	 * @param length length value 16 or 32 bits recommended
	 * @return
	 */
	public static String getSalt(){
		return TokenUtils.generateToken(16);
	}

	/**
	 * Encrypted by salt+MD5
	 * @author yangwenkui
	 * @time April 25, 2017 5:20:32 PM
	 * @param pwd
	 * @param salt
	 * @return
	 */
	public static String encode(String pwd,String salt){
		if(StringUtils.isBlank(salt) || salt.length() != 16){
			salt = TokenUtils.generateToken(16);
		}
		String mixResult = mixStr(pwd,salt);
		return MD5.encodeMd5(mixResult);
	}

	/**
	 * Mix the password plain text and salt, the mixing method is salt one character + one password plain text character, append one by one
	 * For example: password plain text (123), salt (abcd), the result is a1b2c3d
	 * @author yangwenkui
	 * @time April 25, 2017 5:12:02 PM
	 * @param pwd password clear text
	 * @param salt Randomly obtained encrypted characters 16 bits
	 * @return
	 */
	private static String mixStr(String pwd, String salt) {
		StringBuilder builder = new StringBuilder();
		builder.append(salt.substring(0, 5));
		String saltLast = salt.substring(5);
		for(int i=0; i<pwd.length(); i++){
			if(saltLast.length() > i){
				builder.append(saltLast.charAt(i));
			}
			builder.append(pwd.charAt(i));
		}
		if(saltLast.length() > pwd.length()){
			builder.append(saltLast.substring(pwd.length()));
		}
		return builder.toString();
	}
	
	public static void main(String[] args) {
		String salt = "HpsuQ2d4FkqjGU01";//getSalt();
		System.err.println(salt);
		System.err.println(encode("eva2017admin", salt));
	}
	
}

