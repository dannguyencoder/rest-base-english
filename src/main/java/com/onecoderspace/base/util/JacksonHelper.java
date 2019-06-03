package com.onecoderspace.base.util;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JacksonHelper {

	private static final ObjectMapper mapper = new ObjectMapper();
	
	private static Logger logger = LoggerFactory.getLogger(JacksonHelper.class);


	/**
	 * Convert objects to json
	 * @author yangwenkui
	 * @time March 16, 2017 2:55:10 PM
	 * @param obj object to be converted
	 * @return returns null when an exception occurs in the conversion
	 */
	public static String toJson(Object obj){
		if(obj == null){
			return null;
		}
		try {
			return mapper.writeValueAsString(obj);
		} catch (IOException e) {
			logger.error(String.format("obj=[%s]", obj.toString()), e);
		}
		return null;
	}

	/**
	 * Convert json to an object
	 * @author yangwenkui
	 * @time March 16, 2017 2:56:26 PM
	 * @param json json object
	 * @param clazz object type to be converted
	 * @return returns null when an exception occurs in the conversion
	 */
	public static <T> T fromJson(String json,Class<T> clazz){
		if(json == null){
			return null;
		}
		try {
			return mapper.readValue(json, clazz);
		} catch (IOException e) {
			logger.error(String.format("json=[%s]", json), e);
		}
		return null;
	}

	/**
	 * Convert json objects to collection types
	 * @author yangwenkui
	 * @time March 16, 2017 2:57:15 PM
	 * @param json json object
	 * @param collectionClazz The class of the concrete collection class, such as: ArrayList.class
	 * @param clazz The class of the object stored in the collection
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <T> Collection<T> fromJson(String json,Class<? extends Collection> collectionClazz,Class<T> clazz){
		if(json == null){
			return null;
		}
		try {
			Collection<T> collection =  mapper.readValue(json, getCollectionType(collectionClazz,clazz));
			return collection;
		} catch (IOException e) {
			logger.error(String.format("json=[%s]", json), e);
		}
		return null;
	}
	
	private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {   
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
	}
	
	public static void main(String[] args) {
		String s = "{\"ss\":\"12\",\"dd\":\"33\",\"tt\":23}";
		@SuppressWarnings("unchecked")
		Map<String, Object> map = fromJson(s, Map.class);
		for (Entry<String, Object> entry : map.entrySet()) {
			System.out.println(entry);
		}
	}
}
