package com.mr.replay.thread;

import java.io.File;
import analyze.video.parser.kpiVideoParser;
import com.mr.replay.ui.frame.KPIMain;
import com.mr.replay.ui.helper.ConfigHelper;
import com.mr.replay.ui.lock.Lock;
import com.mr.replay.ui.log.Log;

public class parseAndAnalyzeThread implements Runnable{

	String videopath;
	int frameRate;
	int adjust;
	int row;
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		
	}
	public parseAndAnalyzeThread(String videopath,int frameRate,int adjust,int row){
		this.videopath = videopath;
		this.frameRate = frameRate;
		this.adjust = adjust;
		this.row = row;
	}
	public String indexcovertoname(int index){
		int fps = Integer.parseInt(ConfigHelper.getfps());
		int frameInterval = 1000 / fps;
		
		String picname = String.format("%05d_%05d.jpg", 
				index, (int)(Math.rint(frameInterval*index)));
		return picname;
	}
	public void run() {
		Lock.parse = true;
		Lock.analyze=true;
		
		Log.info("开始转换视频文件,请耐心等待...");
		Log.info(videopath);
		
		File v = new File(this.videopath);
		kpiVideoParser vp = new kpiVideoParser(v, this.adjust);
		vp.videoToImage();
		//vp.adjust(); (不需要次操作)
		Log.info("转换视频文件成功...");
		
		KPIMain.spTableModel.setValueAt("SUCCESS", row, 8);
		
		Lock.parse = false;
		/*Hashtable<String,Object> ret = ServiceHelper.RunCommand( mkpath, pypath, device, videopath);
		Object object = ret.get("code");
		if((Integer)object==0){
			System.out.println("脚本执行成功！");
			Log.err("...脚本执行成功！...");
		}else{
			System.out.println("脚本执行失败！");
			Log.err("...脚本执行失败！");
			Log.err(ret.get("msg").toString());
		}*/
	}
}
