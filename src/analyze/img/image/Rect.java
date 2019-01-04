package analyze.img.image;

import static com.googlecode.javacv.cpp.opencv_core.cvRect;

import org.apache.log4j.Logger;

import com.googlecode.javacv.cpp.opencv_core.CvPoint;
import com.googlecode.javacv.cpp.opencv_core.CvRect;

public class Rect {
	
	private static Logger log = Logger.getLogger(Rect.class);
	
	/**
	 * 根据起始坐标和结束坐标获取CvRect
	 * @param bcp 起始坐标
	 * @param ecp 结束坐标
	 * @return 矩形
	 */
	public static CvRect getRect(CvPoint bcp,CvPoint ecp){
		int x = 0;
		int y = 0;
		int w = 0;
		int h = 0;
		if(bcp.x()==ecp.x()||bcp.y()==ecp.y()){
			log.error("所选图片区域为一条直线");
			return null;
		}
		if(bcp.x()>ecp.x()){
			x = ecp.x();
			w = bcp.x()-ecp.x();
		}else{
			x = bcp.x();
			w = ecp.x()-bcp.x();
		}
		if(bcp.y()>ecp.y()){
			y = ecp.y();
			h = bcp.y()-ecp.y();
		}else{
			y = bcp.y();
			h = ecp.y()-bcp.y();
		}
		return cvRect(x, y, w, h);
	}
	/**
	 * 根据起始坐标、宽度、高度返回矩形
	 * @param bcp 起始坐标
	 * @param w 宽度
	 * @param h 高度
	 * @return 矩形
	 */
	public static CvRect getRect(CvPoint bcp,int w,int h){
		return cvRect(bcp.x(), bcp.y(), w, h);
	}
}
