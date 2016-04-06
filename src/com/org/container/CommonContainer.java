package com.org.container;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

public class CommonContainer {
	public static Map<Object, Object> map = new HashMap<Object, Object>();
	// 缓存数据
	public static Map<String, Object> dataMap = new HashMap<String, Object>();

	public static void saveContext(ServletContext servletContext) {
		map.put("servletContext", servletContext);
	}

	public static ServletContext getServletContext(){
		if(map.get("servletContext") != null){
			return (ServletContext)map.get("servletContext");
		}
		return null;
	}

	public static void saveData(String key, Object data) {
		dataMap.put(key, data);
	}

	public static Object getData(String key){
		if(dataMap.containsKey(key) && dataMap.get(key) != null){
			return map.get(key);
		}
		return null;
	}
	
}
