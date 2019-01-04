package com.ckt.autokpi.video.helper;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mr.replay.ui.helper.JarHelper;

/**
 * 
 * @author houyin.tian
 *
 */
public class ConfigHelper {
	
	private static String SETTING_PATH = JarHelper.getProjectPath()+"/config";
	private static String SETTING_NAME = "setting.Properties";
	private static String SETTING = SETTING_PATH+"/"+SETTING_NAME;
	private static String DPORT = "9999";//默认端口号
	private static String DFPS = "100";//默认fps
	
	static {
		File setting_path = new File(SETTING_PATH);
		if(!setting_path.exists()){
			setting_path.mkdirs();
		}
		File setting = new File(SETTING);
		if(!setting.exists()){
			try {
				setting.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取端口号
	 * 
	 * @return
	 */
	public static int getSocketServerPort(){
		String port = FileHelper.readValue(SETTING, "port");
		if(port == null){
			FileHelper.writeProperties(SETTING, "port", DPORT);
			return Integer.valueOf(DPORT);
		}else{
			if(checkPort(port)){
				return Integer.valueOf(port);
			}else{
				return Integer.valueOf(DPORT);
			}
		}
	}
	
	/**
	 * 获取fps
	 * @return
	 */
	public static long getVideoFPS(){
		String fps = FileHelper.readValue(SETTING, "fps");
		if(fps == null){
			FileHelper.writeProperties(SETTING, "fps", DFPS);
			return Long.valueOf(DFPS);
		}else{
			if(checkFPS(fps)){
				return Long.valueOf(fps);
			}else{
				return Long.valueOf(DFPS);
			}
		}
	}
	
	/**
	 * 端口号 1000-65535
	 * 
	 * @param i
	 * @return
	 */
	private static boolean checkPort(String i){
		Pattern pat = Pattern.compile("^[1-9]{1}[0-9]{3,4}$");  
		Matcher mat = pat.matcher(i);  
		if(mat.find()){
			int port = Integer.valueOf(i);
			if(port>=1000&&port<=65535){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	/**
	 * fps 10-999
	 * @param i
	 * @return
	 */
	private static boolean checkFPS(String i){
		Pattern pat = Pattern.compile("^[1-9]{1}[0-9]{1,2}$");  
		Matcher mat = pat.matcher(i);  
		return mat.find(); 
	}
	
	public static void main(String args[]){
		System.out.println(checkFPS("01"));
	}
}
