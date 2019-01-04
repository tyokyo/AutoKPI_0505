package analyze.img.image;

import java.io.File;

import analyze.img.exception.PathNotFoundException;
import analyze.img.exception.RectOverstepException;

import com.googlecode.javacv.cpp.opencv_core.CvRect;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

public class Cut {
	
	/**
	 * 根据图片资源、矩形区域截取矩形图片
	 * @param src 图片资源
	 * @param cr 截图矩形区域
	 * @return IplImage
	 */
	public static IplImage cut(IplImage src,CvRect cr) throws RectOverstepException{
		if(src == null){
			return null;
		}
		if(cr == null){
			return null;
		}
		if((cr.x()+cr.width())>src.width()||(cr.y()+cr.height())>src.height()){
			throw new RectOverstepException("截图区域超过图片边界");
		}
		cvSetImageROI(src,cr);  
		IplImage dst = cvCreateImage(cvSize(cr.width(),cr.height()), src.depth(), src.nChannels());
		cvCopy(src, dst);
		cvResetImageROI(src);
		return dst;
	}
	/**
	 * 根据图片资源、起始坐标、矩形宽度和高度截取矩形图片
	 * @param src 图片资源
	 * @param x 起始x坐标
	 * @param y 起始y坐标
	 * @param w 矩形宽度
	 * @param h 矩形高度
	 * @return IplImage
	 */
	public static IplImage cut(IplImage src,int x,int y,int w,int h)throws RectOverstepException{
		CvRect cr = cvRect(x, y, w, h);
		IplImage dst = cut(src,cr);
		return dst;
	}
	/**
	 * 根据图片路径、矩形区域截取矩形图片
	 * @param path 图片路径
	 * @param cr 截图矩形区域
	 * @return IplImage
	 */
	public static IplImage cut(String path,CvRect cr) throws PathNotFoundException, RectOverstepException{
		if(!exists(path)){
			throw new PathNotFoundException(path+"不存在");
		}
		IplImage src = cvLoadImage(path);
		IplImage dst = cut(src,cr);
		cvReleaseImage(src);
		return dst;
	}
	/**
	 * 根据图片路径、起始坐标、矩形宽度和高度截取矩形图片
	 * @param path 图片路径
	 * @param x 起始x坐标
	 * @param y 起始y坐标
	 * @param w 矩形宽度
	 * @param h 矩形高度
	 * @return IplImage
	 */
	public static IplImage cut(String path,int x,int y,int w,int h) throws PathNotFoundException, RectOverstepException{
		CvRect cr = cvRect(x, y, w, h);
		IplImage dst = cut(path,cr);
		
		return dst;
	}
	/**
	 * 根据图片资源、起始坐标、结束坐标截取矩形图片
	 * @param src 图片资源
	 * @param bcp 起始坐标
	 * @param ecp 结束坐标
	 * @return IplImage
	 */
	public static IplImage cut(IplImage src,CvPoint bcp,CvPoint ecp)throws RectOverstepException{
		CvRect cr = Rect.getRect(bcp,ecp);
		IplImage dst = cut(src, cr);
		return dst;
	}
	
	/**
	 * 根据图片路径、起始坐标、结束坐标截取矩形图片
	 * @param path 图片路径
	 * @param bcp 起始坐标
	 * @param ecp 结束坐标
	 * @return IplImage
	 */
	public static IplImage cut(String path,CvPoint bcp,CvPoint ecp) throws PathNotFoundException, RectOverstepException{
		CvRect cr = Rect.getRect(bcp,ecp);
		IplImage dst = cut(path, cr);
		return dst;
	}
	/**
	 * 
	 * @param path
	 * @return
	 */
	private static boolean exists(String path){
		File f = new File(path);
		return f.exists();
	}
	public static void main(String args[])  {
		// new CompareHist().ch();
		String p1 = "F:\\image\\eii.jpg";
		
		try {
			try {
				Cut.cut(p1, cvPoint(100, 1000), cvPoint(100, 2000));
			} catch (RectOverstepException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (PathNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
