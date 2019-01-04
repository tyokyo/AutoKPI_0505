package com.ckt.autokpi.video.camera;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_core.CvSize;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_highgui;
import com.googlecode.javacv.cpp.opencv_highgui.CvCapture;

/**
 * 
 * @author houyin.tian
 *
 */
public class Camera extends Thread{
	private boolean connected = false;
	private IplImage image = null;
	private CvCapture cvCapture = null;
	private long bt = 0L;// 开始录制视频时间
	private long et = 0L;// 结束录制视频时间
	private long nm = 0L;// 写入一帧后需要暂停的总纳秒数
	private long hm = 0L;// 写入一帧后需要暂停的毫秒数
	private int nanos = 0;// 写入一帧后需要暂停的纳秒数
	private long i = 0L;// 写入的帧数
	private long nwts = 30;
	
	public Camera(long fps) {
		nwts = 1000000000L / fps;
	}

	public boolean connect(){
		if(connected == false||image==null){
			cvCapture = opencv_highgui.cvCreateCameraCapture(0);
		}
		if(cvCapture!=null){
			image = this.getImage();
			if(image!=null){
				connected = true;
			}else{
				connected = false;
			}
		}else{
			connected = false;
		}
		return connected;
		
	}
	
	public void run() {
		try {
			bt = System.nanoTime();
			while ((image = opencv_highgui.cvQueryFrame(cvCapture))!=null) {
				i++;
				et = System.nanoTime();
				nm = nwts * i - (et - bt);
				hm = nm / 1000000L;
				nanos = new Long(nm - hm * 1000000L).intValue();
				if (hm < 0 || nanos < 0) {
					
				} else {
					sleep(hm, nanos);
				}
			}
			if(image!=null){
				opencv_core.cvReleaseImage(image);
			}
			connected = false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 关闭
	public void close() {
		if(cvCapture!=null){
			opencv_highgui.cvReleaseCapture(cvCapture);
			cvCapture = null;
		}
	}

	// 获取摄像头图片
	public IplImage getImage() {
		if (image == null&&cvCapture!=null) {
			image = opencv_highgui.cvQueryFrame(cvCapture);
		}
		if(image==null){
			connected = false;
		}
		return image;
	}

	/**
	 * 
	 * @return
	 */
	public BufferedImage getBufferedImage(){
		IplImage bimage = this.getImage();
		if(bimage==null){
			return null;
		}else{
			return bimage.getBufferedImage();
		}
	}
	/**
	 * 
	 * @return
	 */
	public CvSize getCvSize() {
		IplImage cimage = this.getImage();
		if(cimage == null){
			return null;
		}else{
			return cimage.cvSize();
		}
	}
	
	/**
	 * 返回摄像头分辨率
	 * @return
	 */
	
	public Dimension getSize(){
		CvSize csize = getCvSize();
		if(csize==null){
			return null;
		}else{
			return new Dimension(csize.width(), csize.height());
		}
	}
	/**
	 * 返回当前camera的连接状态
	 * 
	 * @return
	 */
	public boolean isConnected(){
		return connected;
	}
}
