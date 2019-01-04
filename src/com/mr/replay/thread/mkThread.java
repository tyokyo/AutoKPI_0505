package com.mr.replay.thread;

import java.util.Hashtable;
import com.mr.replay.ui.helper.ServiceHelper;
import com.mr.replay.ui.lock.Lock;
import com.mr.replay.ui.log.Log;

public class mkThread implements Runnable{

	String mkpath;
	String pypath;
	String device;
	String videopath;
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		
	}
	public mkThread(String mkpath,String pypath,String device,String videopath){
		this.device = device;
		this.pypath = pypath;
		this.mkpath = mkpath;
		this.videopath = videopath;
	}
	public void run() {
		Hashtable<String,Object> ret = ServiceHelper.RunCommand( mkpath, pypath, device, videopath);
		Object object = ret.get("code");
		if((Integer)object==0){
			System.out.println("脚本执行成功！");
			Log.err("...脚本执行成功！...");
		}else{
			System.out.println("脚本执行失败！");
			Log.err("...脚本执行失败！");
			Log.err(ret.get("msg").toString());
		}
		Lock.replay = false;
	}
}
