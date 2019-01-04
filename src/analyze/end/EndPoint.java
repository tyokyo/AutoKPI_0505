package analyze.end;

import static com.googlecode.javacv.cpp.opencv_core.cvRect;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import analyze.img.exception.PathNotFoundException;
import analyze.img.exception.RectOverstepException;
import analyze.img.image.Compare;

import com.googlecode.javacv.cpp.opencv_core.CvRect;

public class EndPoint {

	/*
	 * cvRect
	 * OpenCV里面区域函数
	 * 函数功能： 通过方形左上角坐标和方形的高和宽来确定一个矩形区域
	 * typedef struct CvRect
	 * {
	 * int x; // 方形的左上角的x-坐标
	 * int y; // 方形的左上角的y-坐标
	 * int width; // 宽 
	 * int height; // 高 
	 * }
	 * x,y用来确定区域左上角的坐标
	 * width和height为矩形区域的宽和高
	 */
	
	public void checkpic() {
		String p1 = "D:\\tatool\\pic\\009_136.jpg";
		String p2 = "D:\\tatool\\pic\\010_151.jpg";
		CvRect cr = null;
		cr = cvRect(154, 38, 231, 421);
		double cp = 0;
		try {
			try {
				cp = Compare.CmpPic(p1, p2, cr);
			} catch (RectOverstepException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (PathNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(cp);
	}
	
	public double check2pic(File f1, File f2, CvRect rect) {
		double cp = 0;
		try {
			try {
				cp = Compare.CmpPic(f1.getPath(), f2.getPath(), rect);
			} catch (RectOverstepException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (PathNotFoundException  e) {
			e.printStackTrace();
		}
		return cp;
	}
	
	public List<Double> gothrudir(String dirpath, CvRect cr) {
		List<Double> result = new ArrayList<Double>();
		
		File dir = new File(dirpath);
		File[] allfiles = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				if(file.isDirectory()) return false;
				String name=file.getName();
				return name.endsWith(".jpg");
			}
		});
		
		int count = allfiles.length - 1;
		File one = null;
		File two = null;
		for(int index = 0; index < count; index++) {
			one = allfiles[index];
			two = allfiles[index+1];
//			System.out.println(one.getName() + " vs. " + two.getName() 
//					+ ": " + check2pic(one, two, cr));
			result.add(check2pic(one, two, cr));
		}
		
		return result;
	}
	
	public static void main(String args[]) {
		EndPoint ep = new EndPoint();
//		List<Double> res = ep.gothrudir("D:\\tatool\\pic4\\", 
//				cvRect(45, 98, 504, 295));
		
		List<Double> res = ep.gothrudir("D:\\tatool\\pic5\\", 
				cvRect(152, 73, 234, 387));
		
		Object[] a = res.toArray();
		for(Object d: a) {
			System.out.println(d);
		}
	}

	private void gothrudir2() {
		File dir = new File("D:\\tatool\\pic3\\");
		File[] allfiles = dir.listFiles();
		
		CvRect cr = null;
		cr = cvRect(154, 38, 231, 421);
		
//		System.out.println(allfiles.length);
		int count = allfiles.length - 1;
		File one = allfiles[0];
		File two = null;
		for(int index = 0; index < count; index++) {
//			one = allfiles[index];
			two = allfiles[index+1];
			System.out.println(one.getName() + " vs. " + two.getName() 
					+ ": " + check2pic(one, two, cr));
		}
	}
}
