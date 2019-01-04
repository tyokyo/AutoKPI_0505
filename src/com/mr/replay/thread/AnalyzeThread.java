package com.mr.replay.thread;

import java.io.File;
import java.io.FileNotFoundException;
import com.mr.replay.ui.frame.KPIMain;
import com.mr.replay.ui.helper.ConfigHelper;
import com.mr.replay.ui.helper.ServiceHelper;
import com.mr.replay.ui.image.hashCompare;
import com.mr.replay.ui.lock.Lock;
import com.mr.replay.ui.log.Log;

public class AnalyzeThread implements Runnable{

	private int row ;
	private String analyzefolder;
	int fps;
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		
	}
	public AnalyzeThread(int row,String analyzefolder){
		this.row = row;
		this.analyzefolder = analyzefolder;
		this.fps =  Integer.parseInt(ConfigHelper.getfps());
	}
	public String indexcovertoname(int index){
		int frameInterval = 1000 / fps;
		String picname = String.format("%05d_%05d.jpg", 
				index, (int)(Math.rint(frameInterval*index)));
		return picname;
	}
	public void run() {
		Lock.analyze = true;
		
		Log.info("开始分析...");
		//String videopath = KPIMain.spTable.getValueAt(row, 5).toString();
		//String analyzefolder = videopath.replaceAll(".avi", "");
		
		//Log.info("开始分析图片文件夹:"+analyzefolder);
		//Log.info("开始计算结束点图片,请耐心等待...");
		//String dir = "E:\\AutoKPI\\video\\20140308175424\\mk\\001";
		
		/*int index = EndLocator.findlast_sample(analyzefolder, cvRect(152, 73, 234, 387));
		Log.info("结束点Index:"+index);
		
		int fps = Integer.parseInt(ConfigHelper.getfps());
		int frameInterval = 1000 / fps;
		
		String picname = String.format("%05d_%05d.jpg", 
				index, (int)(Math.rint(frameInterval*index)));
		*/
		//KPIMain.spTableModel.setValueAt(picname, row, 9);
		Log.info("开始分析："+analyzefolder);
		
		if (new File(analyzefolder).exists()) {
			Log.info("开始计算结束点图片,请耐心等待...");
			//String videopath = KPIMain.spTable.getValueAt(row, 5).toString();
			//String analyzefolder = videopath.replaceAll(".avi", "");
			
			Log.info("开始分析图片文件夹:"+analyzefolder);
			//String dir = "E:\\AutoKPI\\video\\20140308175424\\mk\\001";
			
			//start analyze picture.....................
			/*int index = EndLocator.findlast_sample(analyzefolder, cvRect(152, 73, 234, 387));
			Log.info("结束点Index:"+index);*/
			
			//String folderString ="E:/AutoKPI/video/20140319092043/04/001/";
			int fps = Integer.parseInt(ConfigHelper.getfps());
			int frameInterval = 1000 / fps;
			
			hashCompare hash = new hashCompare(analyzefolder);
			int startpoint = Integer.parseInt(ConfigHelper.getStartHash());
			int endpoint = Integer.parseInt(ConfigHelper.getEndHash());
			Log.info("开始点相似度设置为:"+startpoint);
			Log.info("结束点相似度设置为:"+endpoint);
			try {
				int startIndex = hash.startPic(startpoint,frameInterval);
				KPIMain.spTableModel.setValueAt(startIndex, row, 9);
				Log.info("开始渲染点图片名字:"+indexcovertoname(startIndex));
				
				int endIndex = hash.endPic(endpoint, frameInterval);
				KPIMain.spTableModel.setValueAt(endIndex, row, 10);
				Log.info("结束渲染点图片名字:"+indexcovertoname(endIndex));
				
				//int time = (endIndex -startIndex)* frameInterval;
				int time = (endIndex)* frameInterval;
				
				KPIMain.spTableModel.setValueAt(time, row, 11);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//end analyze picture......................
			
			
			//KPIMain.spTableModel.setValueAt(picname, row, 9);
			//KPIMain.spTableModel.setValueAt(index*frameInterval, row, 11);
			
			Log.info("分析结束...");
		}else {
			KPIMain.spTableModel.setValueAt("FAIL", row, 11);
			Log.err("分析图片文件夹："+analyzefolder+" 不存在...");
		}
		
		//Log.info("分析结束...");
		
		Lock.analyze = false;
		
		String parestatus = KPIMain.spTableModel.getValueAt(row, 8).toString();
		String sttime = KPIMain.spTableModel.getValueAt(row, 9).toString();
		String edtime = KPIMain.spTableModel.getValueAt(row, 10).toString();
		String result = KPIMain.spTableModel.getValueAt(row, 11).toString();
		
		String strContent = String.format("%s:%s:%s:%s", parestatus,sttime,edtime,result);
		String dataFile = analyzefolder+"\\"+"result.txt";
		ServiceHelper.writeToFile(dataFile, strContent, false);
		Log.info(dataFile+":"+strContent);
	}
}
