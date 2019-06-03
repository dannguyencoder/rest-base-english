package com.onecoderspace.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegixUtils {

	/**
	 * Get the value of the regular match
	 * @author yangwk
	 * @time 2017年9月13日 下午2:39:57
	 * @param content String
	 * @param regix Regular rule
	 * @param num Matching group subscript
	 * @return
	 */
	public static String match(String content,String regix,int num){
		Pattern r = Pattern.compile(regix);
	    // Now create a matcher object
		Matcher m = r.matcher(content);
		if (m.find()) {
			return m.group(num);
		}
		return "";
	}
	
}
