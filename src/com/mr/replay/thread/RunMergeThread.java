package com.mr.replay.thread;

import java.io.File;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mr.replay.ui.frame.KPIMain;
import com.mr.replay.ui.helper.ServiceHelper;
import com.mr.replay.ui.lock.Lock;
import com.mr.replay.ui.log.Log;

public class RunMergeThread implements Runnable{

	String mkpath;
	String pypath;
	String device;
	String videopath;
	int index;
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		
	}
	public RunMergeThread(String mkpath,String pypath,String device,String videopath){
		this.device = device;
		this.pypath = pypath;
		this.mkpath = mkpath;
		this.videopath = videopath;
	}
	public void run() {
		Lock.replay =true;
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				KPIMain.updatesptable();
			}
		},  3000, 3000);
		
		KPIMain.runLabel.setText("脚本执行中请耐心等待!");
		Hashtable<String,Object> ret = ServiceHelper.RunCommand( mkpath, pypath, device, videopath);
		Object object = ret.get("code");
		if((Integer)object==0){
			System.out.println("脚本执行成功！");
			Log.warn("...脚本执行成功！...");
			KPIMain.runLabel.setText("执行成功");
			//KPIMain.kpiTable.setValueAt("SUCCESS", index, 10);
			
			JOptionPane.showMessageDialog(new JFrame(), "脚本执行成功!","提示", JOptionPane.INFORMATION_MESSAGE);
			
		}else{
			System.out.println("脚本执行失败！");
			Log.err("...脚本执行失败！");
			Log.err(ret.get("msg").toString());
			KPIMain.runLabel.setText("脚本执行失败");
			//KPIMain.kpiTable.setValueAt("FAIL", index, 10);
			JOptionPane.showMessageDialog(new JFrame(), "脚本执行失败!","警告", JOptionPane.WARNING_MESSAGE);
		}
		Lock.replay = false;
		KPIMain.bar.setIndeterminate(false);
		timer.cancel();
	}
}
