package com.mr.replay.py;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.mr.replay.exec.Exec;
import com.mr.replay.ui.helper.JarHelper;
import com.mr.replay.ui.lock.Lock;
import com.mr.replay.ui.log.Log;

/**
 * 
 * @author houyin.tian
 *
 */
public class PysReplay extends Thread{
	
	String mpath = "D:\\eclipse\\android-sdk-windows\\tools\\monkeyrunner.bat";//monkeyrunner.bak路径
	String pys = "D:\\xxxxx.py";//脚本路径
	String device = "HIKe 828";//设备名称
	String vpath;
	//int n = 0;
	/**
	 * 
	 * @param mpath monkeyrunner.bak路径
	 * @param pys 脚本路径
	 * @param device 设备名称
	 */
	public PysReplay(String mpath,String pys,String device,String vpath){
		this.mpath = mpath;
		this.pys = pys;
		this.device = device;
		this.vpath = vpath;
	}
	
	public PysReplay(){
		
	}
	
	public void run(){
		List<String> command = getCommand(mpath,pys,device,vpath);
		Log.info(command.toString());
		Hashtable<String,Object> ret = Exec.execpy("\"E:\\Program Files\\android-sdk\\tools\\monkeyrunner.bat\", \"E:\\AutoKPI\\scripts\\mk.py\",\"E:\\AutoKPI\\\", \"HIKe 828\", path=E:\\AutoKPI\\video\\test.avi",true);
		Object object = ret.get("code");
		if((Integer)object==0){
			System.out.println("脚本执行成功！");
			Log.info("脚本执行成功！");
		}else{
			System.out.println("脚本执行失败！");
			Log.info(ret.get("msg").toString());
		}
		Lock.replay = false;
	}
	/**
	 * 获取执行脚本执行命名
	 * @param mpath monkeyrunner.bak路径
	 * @param pys python脚本路径
	 * @param device 设备名称
	 * @param n 第N次执行
	 * @return
	 */
	
	public List<String> getCommand(String mpath,String pys,String device,String vpath){
		List<String> com = new ArrayList<String>();
		com.add(mpath);
		com.add(pys);
		com.add(JarHelper.getJarProjectPath());
		com.add(device);
		com.add(vpath+"");
		return com;
	}
	
}
