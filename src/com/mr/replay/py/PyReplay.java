package com.mr.replay.py;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mr.replay.exec.Exec;
import com.mr.replay.ui.helper.JarHelper;
import com.mr.replay.ui.listener.btnListener;
import com.mr.replay.ui.lock.Lock;
import com.mr.replay.ui.log.Log;

/**
 * 
 * @author houyin.tian
 *
 */
public class PyReplay extends Thread{
	
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
	public PyReplay(String mpath,String pys,String device,String vpath){
		this.mpath = mpath;
		this.pys = pys;
		this.device = device;
		this.vpath = vpath;
	}
	
	public PyReplay(){
		
	}
	
	public void run(){
		btnListener bListener = new btnListener("replay");
		bListener.disable();
		
		List<String> command = getCommand(mpath,pys,device,vpath);
		Log.info(command.toString());
		Hashtable<String,Object> ret = Exec.exec(command,true);
		Object object = ret.get("code");
		if((Integer)object==0){
			JOptionPane.showMessageDialog(new JFrame(), "执行成功!","恭喜", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("脚本执行成功！");
			Log.info("脚本执行成功！");
		}else{
			JOptionPane.showMessageDialog(new JFrame(), "执行失败!","警告", JOptionPane.WARNING_MESSAGE);
			System.out.println("脚本执行失败！");
			Log.info(ret.get("msg").toString());
		}
		Lock.replay = false;
		bListener.enable();
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
		//com.add(JarHelper.getJarProjectPath());
		com.add(device);
		com.add(vpath+"");
		return com;
	}
	
}
