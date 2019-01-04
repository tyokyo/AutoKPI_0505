package com.ckt.autokpi.video.camera;

import java.text.DecimalFormat;

import com.ckt.autokpi.video.helper.FileHelper;
import com.ckt.autokpi.video.ui.panel.VideoPanel;
import com.googlecode.javacv.cpp.opencv_highgui;
import com.googlecode.javacv.cpp.opencv_highgui.CvVideoWriter;

/**
 * 录制视频
 * 
 * @author houyin.tian
 *
 */
public class RecordVideo extends Thread{
	private boolean bool = true;
	private String path = null;
	private long bt = 0L;// 开始录制视频时间
	private long et = 0L;// 结束录制视频时间
	private long nm = 0L;// 写入一帧后需要暂停的总纳秒数
	private long hm = 0L;// 写入一帧后需要暂停的毫秒数
	private int nanos = 0;// 写入一帧后需要暂停的纳秒数
	private long i = 0L;// 写入的帧数
	private CvVideoWriter writer = null;
	private Camera camera;
	private long nwts = 30;
	private long startTime = 0;
	private long stopTime = 0;
	private String STR_FORMAT2 = "00";
	private String STR_FORMAT3 = "000";
	private DecimalFormat df2 = new DecimalFormat(STR_FORMAT2);
	private DecimalFormat df3 = new DecimalFormat(STR_FORMAT3);
	private VideoPanel vp;
	
	public RecordVideo(VideoPanel vp,Camera camera,long fps,String path) {
		this.vp = vp;
		this.camera = camera;
		this.path = path;
		nwts = 1000000000L / fps;
		// writer = cvCreateVideoWriter(path, CV_FOURCC('M', 'J', 'P',
		// 'G'),fps, refreshCameraThread.getCvSize(), 1);
		// writer = cvCreateVideoWriter(path, CV_FOURCC('D', 'I', 'V',
		// '3'),fps, refreshCameraThread.getCvSize(), 1);
		writer = opencv_highgui.cvCreateVideoWriter(path, opencv_highgui.CV_FOURCC('M', 'P', '4', '2'),fps, camera.getCvSize(), 1);
	}

	public void run() {
		try {
			bt = System.nanoTime();
			vp.setStartTime(bt+"");
			while (bool) {
				i++;
				opencv_highgui.cvWriteFrame(writer, camera.getImage());
				et = System.nanoTime();
				vp.setVideoLength(getVideoLength());
				nm = nwts * i - (et - bt);
				hm = nm / 1000000L;
				nanos = new Long(nm - hm * 1000000L).intValue();
				if (hm < 0 || nanos < 0) {
					
				} else {
					sleep(hm, nanos);
				}
			}
			vp.setStopTime(et+"");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		opencv_highgui.cvReleaseVideoWriter(writer);
		String tpath = path.replace(".avi", ".txt");
		FileHelper.writeProperties(tpath, "Totalframes", i + "");
		FileHelper.writeProperties(tpath, "StartTime", bt + "");
		FileHelper.writeProperties(tpath, "EndTime", et + "");
		FileHelper.writeProperties(tpath, "Duration", (et - bt) / 1000000L+ "");
	}
	/**
	 * 停止视频录制
	 */
	public void close() {
		bool = false;
	}
	/**
	 * 获取录制视频开始时间
	 * @return
	 */
	public long getStartTime(){
		return startTime;
	}
	/**
	 * 获取录制视频停止时间
	 * @return
	 */
	public long getStopTime(){
		return stopTime;
	}
	
	/**
	 * 获取视频长度
	 * @return
	 */
	public String getVideoLength() {
		long nm = et - bt;
		long hm = nm / 1000000L;
		long ms = hm % 1000L;// 毫秒
		long s = hm / 1000L % 60L;// 秒
		long m = hm / 60000L % 60L;// 分
		long h = hm / 3600000L % 60L;// 小时
		String length  = df2.format(h) + ":" + df2.format(m) + ":" + df2.format(s) + ":" + df3.format(ms);
		return length;
	}
}
