package analyze.end;

import java.util.List;

import analyze.counter.ICounter;
import analyze.counter.SmallerCounter;
import analyze.matcher.IMatcher;
import analyze.matcher.SmallerBiggerMatcher;

import com.googlecode.javacv.cpp.opencv_core.CvRect;
import static com.googlecode.javacv.cpp.opencv_core.cvRect;

public class EndLocator {
	
	/*
	 *  方法：使用matcher和counter来查找最后一张照片
	 *  参数： dirpath， 图片序列所在的路径
	 *      cr，图片中被比较的区域
	 *  返回：根据模式规则，匹配后游标所在的位置
	 */
	public static int findlast_sample(String dirpath, CvRect cr) {
		EndPoint ep = new EndPoint();
		List<Double> result = ep.gothrudir(dirpath, cr);
		Double[] r = result.toArray(new Double[0]);
			
		// 匹配规则演示
		// 1. counter
		// 游标跳过5个小于1.0的值
		ICounter c = new SmallerCounter(r, new Double[]{-1.0});
		int index = c.count(0, 5);
		// 2. matcher
		// 游标跳过5个小于1.0的值，从此处开始，匹配规则：大于0.9999的值
		// 得到第1个大于0.9999的值所在的位置，为需要的结束位置
		IMatcher m = new SmallerBiggerMatcher(r, new Double[]{0.9999});
		index = m.match(index);
		return index;
	}
	
	public static void main(String[] args) {
		String dir = "E:\\AutoKPI\\video\\20140308175424\\mk\\001";
		System.out.println(findlast_sample(dir, 
				cvRect(152, 73, 234, 387)));
	}
	
//	// 方法：以连续两幅照片相同作为结束点
//	public static int findlast() {
//		EndPoint ep = new EndPoint();
//		List<Double> result = ep.gothrudir("D:\\tatool\\pic5\\", 
//				cvRect(152,73,233,388));
//		
//		Double[] r = result.toArray(new Double[0]);
//		int size = r.length;
//		System.out.println("result size: " + size);
//		
//		for(Double d: r) {
//			System.out.println(d);
//		}
//		
//		//如果3幅照片连续彼此相同，第一张相同的照片作为结束点
//		int last = -1; //结束点的指针
////		int count = 0; //连续相等的次数，相等一次记为1
////		for(int i=0; i<size; i++) {
////			System.out.println(String.format("%d vs. %d : %f", i, i+1, r[i]));
////			if(r[i] == 1.0) {
////				if(count == 1) {
////					return last;
////				}
////				last = i;
////				count++;
////			} else {
////				last = -1;
////				count = 0;
////			}
////			
////		}
//		
//		return last;
//	}
//	for(Double d: r) {
//	System.out.println(d);
//}
//SmallerCounter sc = new SmallerCounter(r, new Double[]{-1.0});
//SmallerBiggerMatcher sbm = new SmallerBiggerMatcher(r, new Double[]{0.9999});


}
