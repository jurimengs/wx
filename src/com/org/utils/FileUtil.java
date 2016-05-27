package com.org.utils;

public class FileUtil {
	/**
	 * 获取项目的物理根路径
	 * @param fieldName
	 * @param toUpper
	 * @return
	 */
	public static String getPhysicalPath() {
		return FileUtil.class.getClassLoader().getResource("").getPath();
	}
}
