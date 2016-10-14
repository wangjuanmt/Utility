package com.amuos.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class CommonUtility {
	
	/**
	 * 判断是否为空字符串
	 * @param str
	 * @return
	 */
	public static boolean isNonEmpty(String str) {
		boolean result = false;
		if(str != null && !"".trim().equals(str)) {
			 result = true;
		}
		return result;
	}
	
	/**
	 * 获取资源配置文件
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		try {
			Properties prop = new Properties();
			InputStream in = CommonUtility.class.getClassLoader()
					.getResourceAsStream("apiManager.properties");
			prop.load(in);
			return prop.getProperty(key).trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
