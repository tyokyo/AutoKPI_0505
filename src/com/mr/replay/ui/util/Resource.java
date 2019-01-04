package com.mr.replay.ui.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 
 * @author houyin.tian
 *
 */
public class Resource {
	
	/**
	 * 读取Properties文件中的值
	 * @param filePath Properties文件路径
	 * @param key 
	 * @return
	 */
	public static String readValue(String filePath, String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 读取Properties文件中的所有值
	 * @param filePath
	 */
	public static void readProperties(String filePath) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			Enumeration<?> en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
				System.out.println(Property);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 写Properties
	 * @param filePath
	 * @param parameterName
	 * @param parameterValue
	 * @return
	 */
	@SuppressWarnings("finally")
	public static boolean writeProperties(String filePath, String parameterName,String parameterValue) {
		Properties prop = new Properties();
		boolean bool = false;
		try {
			InputStream fis = new FileInputStream(filePath);
			prop.load(fis);
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(parameterName, parameterValue);
			prop.store(fos, "Update '" + parameterName + "' value");
			bool = true;
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Visit " + filePath + " for updating "+ parameterName + " value error");
		}finally{
			return bool;
		}
	}
}
