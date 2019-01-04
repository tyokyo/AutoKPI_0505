package analyze.img.image;

import java.io.File;

import analyze.img.exception.PathNotFoundException;
import analyze.img.exception.RectOverstepException;

import com.googlecode.javacv.cpp.opencv_core.CvRect;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

public class Compare {

	/**
	 * 根据选择比较区域，比较2张本地图片指定区域的相似度
	 * 
	 * @param path1 第一张图片
	 * @param path2 第二张图片
	 * @param cr 比较区域
	 * @return 两张图片的相似度 相同为0,不同
	 */
	public static double CmpPic(String path1, String path2, CvRect cr) throws PathNotFoundException, RectOverstepException{
		IplImage img1 = Cut.cut(path1, cr);
		IplImage img2 = Cut.cut(path2, cr);
		double cmp =  CmpPic(img1,img2);
		cvReleaseImage(img1);
		cvReleaseImage(img2);
		return cmp;
	}
	
	/**
	 * 比较两张本土图片的相似度
	 * @param path1 第一张图片
	 * @param path2 第二张图片
	 * @return 两张图片的相似度
	 */
	public static double CmpPic(String path1, String path2) throws PathNotFoundException{
		if(!exists(path1)){
			throw new PathNotFoundException(path1+"不存在");
		}
		if(!exists(path2)){
			throw new PathNotFoundException(path2+"不存在");
		}
		IplImage img1 = cvLoadImage(path1,CV_LOAD_IMAGE_GRAYSCALE);
		IplImage img2 = cvLoadImage(path2,CV_LOAD_IMAGE_GRAYSCALE);
		double cmp = CmpPic(img1,img2);
		cvReleaseImage(img1);
		cvReleaseImage(img2);
		return cmp;
	}
	
	/**
	 * 根据比较区域，比较两张图片的相似度
	 * @param img1 第一张图片
	 * @param img2 第二张图片
	 * @param cr 比较区域
	 * @return 相似度
	 */
	public static double CmpPic(IplImage img1, IplImage img2, CvRect cr) throws RectOverstepException{
		IplImage i1 = Cut.cut(img1, cr);
		IplImage i2 = Cut.cut(img2, cr);
		double cmp = CmpPic(i1,i2);
		cvReleaseImage(i1);
		cvReleaseImage(i2);
		return cmp;
	}
	/**
	 * 根据起始坐标和结束坐标比较两张图片指定区域相似度
	 * @param path1 第一张本地图片
	 * @param path2 第二张本地图片
	 * @param bcp 开始坐标
	 * @param ecp 结束坐标
	 * @return 相似度
	 */
	public static double cmpPic(String path1, String path2,CvPoint bcp,CvPoint ecp)throws PathNotFoundException, RectOverstepException{
		CvRect cr = Rect.getRect(bcp, ecp);
		double cmp = CmpPic(path1, path2, cr);
		return cmp;
	}
	/**
	 * 根据起始坐标和结束坐标比较两张图片指定区域相似度
	 * @param img1 第一张图片
	 * @param img2 第二张图片
	 * @param bcp 开始坐标
	 * @param ecp 结束坐标
	 * @return 相似度
	 */
	public static double cmpPic(IplImage img1, IplImage img2,CvPoint bcp,CvPoint ecp)throws RectOverstepException{
		CvRect cr = Rect.getRect(bcp, ecp);
		double cmp = CmpPic(img1, img2, cr);
		return cmp;
	}
	/**
	 * 根据起始坐标、宽度、高度比较两张图片的相似度
	 * @param path1 第一张本地图片
	 * @param path2 第二章本地图片
	 * @param bcp 开始坐标
	 * @param w 宽度
	 * @param h 高度
	 * @return 相似度
	 */
	public static double cmpPic(String path1, String path2,CvPoint bcp,int w,int h)throws PathNotFoundException, RectOverstepException{
		CvRect cr = Rect.getRect(bcp, w,h);
		double cmp = CmpPic(path1, path2, cr);
		return cmp;
	}
	
	/**
	 * 根据起始坐标、宽度、高度比较两张图片的相似度
	 * @param img1 第一张图片
	 * @param img2 第二张图片
	 * @param bcp 起始坐标
	 * @param w 宽度
	 * @param h 高度
	 * @return 相似度
	 */
	public static double cmpPic(IplImage img1, IplImage img2,CvPoint bcp,int w,int h)throws RectOverstepException{
		CvRect cr = Rect.getRect(bcp, w,h);
		double cmp = CmpPic(img1, img2, cr);
		return cmp;
	}
	
	/**
	 * 比较两张图片相似度
	 * @param img1 第一张图片
	 * @param img2 第二章图片
	 * @return 相似度
	 */
	public static double CmpPic(IplImage img1, IplImage img2) {
		if(img1 == null || img2 == null){
			return -1;
		}
		int l_bins = 256;
		int hist_size[] = { l_bins };
		float v_ranges[] = { 0, 255 };
		float ranges[][] = { v_ranges };
		IplImage i1 = null;
		IplImage i2 = null;
		if(img1.depth()!=IPL_DEPTH_8U||img1.nChannels()!=1){
			i1 = cvCreateImage(cvGetSize(img1), IPL_DEPTH_8U, 1);
			cvCvtColor(img1, i1, CV_BGR2GRAY);
		}else{
			i1 = img1;
		}
		if(img2.depth()!=IPL_DEPTH_8U||img2.nChannels()!=1){
			i2 = cvCreateImage(cvGetSize(img2), IPL_DEPTH_8U, 1);
			cvCvtColor(img2, i2, CV_BGR2GRAY);
		}else{
			i2 = img2;
		}

		IplImage ia1[] = { i1 };
		IplImage ia2[] = { i2 };

		CvHistogram ch1 = cvCreateHist(1, hist_size, CV_HIST_ARRAY, ranges, 1);
		CvHistogram ch2 = cvCreateHist(1, hist_size, CV_HIST_ARRAY, ranges, 1);

		cvCalcHist(ia1, ch1, 0, null);
		cvCalcHist(ia2, ch2, 0, null);
		
		cvNormalizeHist(ch1, 1);
		cvNormalizeHist(ch2, 1);

		/* 
    	– CV_COMP_CORREL Correlation相关系数，相同为1，相似度范围为[ 1, 0 )
    	– CV_COMP_CHISQR Chi-Square卡方，相同为0，相似度范围为[ 0, +inf )
    	– CV_COMP_INTERSECT Intersection直方图交,数越大越相似，，相似度范围为[ 0, +inf )
    	– CV_COMP_BHATTACHARYYA Bhattacharyya distance做常态分别比对的Bhattacharyya 距离，相同为0,，相似度范围为[ 0, +inf )
		 */
		
		double cmp = cvCompareHist(ch1, ch2, CV_COMP_CORREL);
		
		cvReleaseHist(ch1);
		cvReleaseHist(ch2);
		cvReleaseImage(i1);
		cvReleaseImage(i2);

		return cmp;
	}
	private static boolean exists(String path){
		File f = new File(path);
		return f.exists();
	}
	public static void main(String args[]) {

		
		String p1 = "D:\\tatool\\pic\\000_0.jpg";
		String p2 = "D:\\tatool\\pic\\009_136.jpg";
		CvRect cr = cvRect(154, 38, 231, 421);
		double cp = 0;
		try {
			try {
				cp = Compare.CmpPic(p1, p2, cr);
			} catch (RectOverstepException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (PathNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(cp);
		try {
			cp = Compare.CmpPic(p1, p2);
		} catch (PathNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(cp);
	}
}
