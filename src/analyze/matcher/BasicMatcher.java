package analyze.matcher;

import java.util.Arrays;

/*
 * 本匹配器的规则：
 *  模式表示应该则表示应该等于该值。比如1，则表示要等于1；-0.5，则表示要等于-0.5
 *  实际相似度不可能为负数，所以负数无效
 */
public class BasicMatcher implements IMatcher {
	public Double[] match = null;
	public Double[] format = null;
	
	private int matchSize = 0;
	private int formatSize = 0;
	
	/*
	 * match - double序列, 被匹配的序列
	 * format - double序列， 匹配的模式序列（模式含义见匹配器规则）
	 */
	public BasicMatcher(Double[] match, Double[] format) {
		this.match = match;
		this.format = format;
		this.matchSize = this.match.length;
		this.formatSize = this.format.length;
	}
	
	//如果模式为1，则表示要等于1；0.99，则表示要等于0.99
	private boolean basicCheck(Double[] m, Double[] f) {
		System.out.println("=======================");
		printArray(m);
		printArray(f);
		
		if(f.length > m.length) return false;
		
		for(int i=0; i<formatSize; i++) {
			if(Double.compare(m[i], f[i]) != 0) {
				System.out.println(m[i] + "!=" + f[i]);
				return false;
			}
		}
		return true;
	}
	
	private void printArray(Double[] d) {
		System.out.println("length: " + d.length);
		for(int i=0; i<d.length; i++) {
			System.out.println("d[" + i + "]: " + d[i]);
		}
	}
	
	public int basic(int begin) {
		if(formatSize > matchSize) return -1;
		if(begin > (matchSize - formatSize)) return -1;
		
		int checkCount = matchSize - formatSize + 1;
		for(int i=begin; i<checkCount; i++) {
			Double[] m = Arrays.copyOfRange(match, i, i+formatSize);
			if(basicCheck(m, format))
				return i;
		}
		return -1;
	}
	
	public int basic() {
		return basic(0);
	}
	
	@Override
	public int match(int begin) {
		return basic(begin);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Double[] m = {
				1.0,
				0.9977465441030628,
				1.0,
				0.9993672769945371,
				1.0, //4
				0.9989498143520752,
				1.0,
				0.9968947744675406,
				1.0,
				0.9981108800010918, //9
				1.0,
				0.9986556137793362,
				1.0,
				1.0,
				1.0, //14
				0.9984573838921528,
				1.0,
				0.9984554540353687,
				1.0,
				1.0	//19
		};
		Double[] f = {1.0, 1.0, 1.0};
		
		BasicMatcher fm = new BasicMatcher(m,f);
		System.out.println(fm.match(13));
	}

}
