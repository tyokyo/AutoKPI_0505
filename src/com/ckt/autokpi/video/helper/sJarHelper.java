package com.ckt.autokpi.video.helper;

import java.io.File;

/**
 * 
 * @author houyin.tian
 *
 */
public class sJarHelper {
    
    /**
     * 获取当前jar项目路径
     * @return
     */
    public static String getProjectPath() {  
    	String path = System.getProperty("user.dir");
        File file = new File(path);
        String ppath = file.getPath();
        if(ppath.endsWith(".jar")){
        	return file.getParent();
        }else{
        	return file.getPath();
        }
    }
}
