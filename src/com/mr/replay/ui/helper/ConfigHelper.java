package com.mr.replay.ui.helper;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.mr.replay.ui.util.Resource;

/**
 * 
 * @author houyin.tian
 *
 */
public class ConfigHelper {
	
	
	public static String getStartHash(){
		String sp = getSetting();
		if(!new File(sp).exists()){
			return null;
		}else{
			return Resource.readValue(sp, "starthash");
		}		
	}
	public static String getEndHash(){
		String sp = getSetting();
		if(!new File(sp).exists()){
			return null;
		}else{
			return Resource.readValue(sp, "endhash");
		}		
	}
	public static boolean setStartHash(String value){
		initSetting();
		return Resource.writeProperties(getSetting(),"starthash",value);
	}
	public static boolean setEndHash(String value){
		initSetting();
		return Resource.writeProperties(getSetting(),"endhash",value);
	}
	
	public static String getfps(){
		String sp = getSetting();
		if(!new File(sp).exists()){
			return null;
		}else{
			return Resource.readValue(sp, "fps");
		}		
	}
	public static boolean setfps(String value){
		initSetting();
		return Resource.writeProperties(getSetting(),"fps",value);
	}
	public static String getffmpeg(){
		String sp = getSetting();
		if(!new File(sp).exists()){
			return null;
		}else{
			return Resource.readValue(sp, "ffmpeg");
		}		
	}
	public static boolean setffmpeg(String value){
		initSetting();
		return Resource.writeProperties(getSetting(),"ffmpeg",value);
	}
	/**
	 * 
	 * @return
	 */
	public static String getMonkeyrunner(){
		String sp = getSetting();
		if(!new File(sp).exists()){
			return null;
		}else{
			return Resource.readValue(sp, "monkeyrunner");
		}		
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static boolean setMonkeyrunner(String value){
		initSetting();
		return Resource.writeProperties(getSetting(),"monkeyrunner",value);
	}
	
	public static boolean setCycles(String value){
		initSetting();
		return Resource.writeProperties(getSetting(),"cycles",value);
	}
	
	public static String getCycles(){
		String sp = getSetting();
		if(!new File(sp).exists()){
			return null;
		}else{
			return Resource.readValue(sp, "cycles");
		}
	}
	
	public static void initSetting(){
		String projectPath = JarHelper.getJarProjectPath();
		String dir = projectPath+"\\config";
		String fp = dir+"\\setting.Properties";
		File pf = new File(dir);
		File sf = new File(fp);
		if(!pf.exists()){
			pf.mkdirs();
		}
		if(!sf.exists()){
			try {
				sf.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static String getSetting(){
		String projectPath = JarHelper.getJarProjectPath();
		String dir = projectPath+"\\config";
		String sp = dir+"\\setting.Properties";
		return sp;
	}
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static BufferedImage getBufferedImage(String name){
		BufferedImage bi = null;
		InputStream in = ConfigHelper.class.getResourceAsStream(name);
		//System.out.print(Class.class.getClass().getName());
		if(in == null){
			System.err.println("Image not found."); 
			return null;
		}
		try{
			bi = ImageIO.read(in);
		}catch(IOException e){
			 System.err.println("Unable to read image.");
             e.printStackTrace(); 
		}
		BufferedImage scaledImage = new BufferedImage(20, 20,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = scaledImage.createGraphics();
		g2d.drawImage(bi, 0, 0, scaledImage.getWidth(),scaledImage.getHeight(), null);
		g2d.dispose();
		
		return scaledImage;
	}
}
