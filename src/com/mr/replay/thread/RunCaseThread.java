package com.mr.replay.thread;

import java.util.Hashtable;

import com.mr.replay.ui.frame.KPIMain;
import com.mr.replay.ui.helper.ServiceHelper;
import com.mr.replay.ui.lock.Lock;
import com.mr.replay.ui.log.Log;
import com.mr.replay.ui.merge.TextFile;

public class RunCaseThread implements Runnable{

	String mkpath;
	String pypath;
	String device;
	String videopath;
	int index;
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		
	}
	public RunCaseThread(int index ,String mkpath,String pypath,String device,String videopath){
		this.device = device;
		this.pypath = pypath;
		this.mkpath = mkpath;
		this.videopath = videopath;
		this.index = index;
	}
	public void run() {
		Hashtable<String,Object> ret = ServiceHelper.RunCommand( mkpath, pypath, device, videopath);
		Object object = ret.get("code");
		if((Integer)object==0){
			System.out.println("脚本执行成功！");
			Log.warn("...脚本执行成功！...");
			KPIMain.spTable.setValueAt("SUCCESS", index, 7);
			
			TextFile.refreshDuration(index);
		}else{
			System.out.println("脚本执行失败！");
			Log.err("...脚本执行失败！");
			Log.err(ret.get("msg").toString());
			KPIMain.spTable.setValueAt("FAIL", index, 7);
			
			TextFile.refreshDuration(index);
		}
		Lock.replay = false;
	}
}
