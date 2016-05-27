package com.org.utils;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.org.container.CommonContainer;
/**
 * 
 * .properties file include 
 *
 */
public class PropertyUtil {
	private static Log log = LogFactory.getLog(PropertyUtil.class);
	
	public static Map<String,Properties>  proMaps = new HashMap<String, Properties>();
	
	public static void initProperties(ServletContext webContext){

		// Web-Info/class
		// 通过读取物理路径，来获取目录下的当前配置文件列表
    	String phyPath = FileUtil.getPhysicalPath();
    	phyPath = phyPath.replace("classes", "config");
		File f = new File(phyPath);
		
		InputStream is = null;
		if(f.exists()) {
			// 列出所有的配置文件
			String[] fileArray = f.list();
			String fileName = null;
			String simpleName = null;
			
			for (int i = 0; i < fileArray.length; i++) {
				fileName = fileArray[i];
				try {
					// linux 为 \ 
					simpleName = fileName.replace(".properties", "");
					fileName = "\\WEB-INF\\config\\"+fileArray[i];
					is = webContext.getResourceAsStream(fileName);
					Properties pro = new Properties();
					pro.load(is);
					proMaps.put(simpleName, pro);
					log.info("加载配置参数文件" + fileName + "成功....");
				}catch(Exception e) {
					log.info("加载配置文件失败：" + fileName);
					log.info(e.getMessage());
				}
			}
		}
	}

	public static Properties getProperties(String name){
		if(proMaps != null && proMaps.size() > 0){
			return proMaps.get(name);
		}
		return null;
	}

	public static Properties getPropertiesReload(String name){
		String realName = "\\WEB-INF\\config\\" + name + ".properties";
		InputStream is = CommonContainer.getServletContext().getResourceAsStream(realName);
		Properties pro = new Properties();
		try {
			pro.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pro;
	}
	
	public static String getValue(String name,String key){
		if(proMaps != null && proMaps.size() > 0){
			Properties pro =  proMaps.get(name);
			if(pro != null && pro.containsKey(key)){				
				return pro.getProperty(key);
			}else{
				log.info("查找文件名["+name+"]失败,原因:找不到对应配置文件");
				return null;				
			}
		}
		return null;
	}
	
	public static void setPropertiesSetValue(String name, String key, String value){
		if(proMaps != null && proMaps.size() > 0){
			Properties pro =  proMaps.get(name);
			if(pro != null){
				pro.setProperty(key, value);
			}else{
				log.info("查找文件名["+name+"]失败,原因:找不到对应配置文件");
			}
		}
	}
	
	public static String getValue(String name,String key,String defaultValue){
		if(proMaps != null && proMaps.size() > 0){
			Properties pro =  proMaps.get(name);
			if(pro != null && pro.containsKey(key)){				
				return pro.getProperty(key,defaultValue);
			}else{
				log.info("查找文件名["+name+"]失败,原因:找不到对应配置文件");
				return defaultValue;				
			}
		}
		return defaultValue;
	}
	
	public static boolean isProductMode(){
		String modeType = getValue("core", "model_type");
		if("product".equalsIgnoreCase(modeType)){
			return true;
		}else {
			return false;
		}
	}
	
	

}
