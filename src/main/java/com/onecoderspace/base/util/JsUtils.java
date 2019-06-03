package com.onecoderspace.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsUtils {
	private static Logger logger = LoggerFactory.getLogger(JsUtils.class);

	/**
	 * Get the value of a variable in the js code
	 * @author yangwk
	 * @time September 7, 2017 1:57:46 PM
	 * @param jsCode
	 * @param property
	 * @return
	 */
	public static String getProperty(String jsCode,String property){
		try {
			ScriptEngineManager manager = new ScriptEngineManager();   
			ScriptEngine engine = manager.getEngineByName("javascript");
			engine.eval(jsCode);   
			String value = (String) engine.get(property);
			return value;
		} catch (ScriptException e) {
			logger.error(String.format("jsCode=%s,property=%s", jsCode,property),e);
		}
		return "";
	}
	
	public static void main(String[] args) throws Exception {
		List<String> list = IOUtils.readLines(new FileInputStream(new File("D:/workspace/crawler-task/doc/test.js")));
		System.out.println(getProperty(list.get(0), "code"));
	}
	
}
