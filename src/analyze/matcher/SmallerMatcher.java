package analyze.matcher;

import java.util.Arrays;

/*
 * 本匹配器的规则：
 *  如果模式为负数，则表示应该小于绝对值。比如-1，则表示要小于1；-0.5，则表示要小于0.5
 *  如果模式为正数，则表示应该等于绝对值。比如1，则表示要等于1；0.5，则表示要等于0.5
 */
public class SmallerMatcher implements IMatcher {
	public Double[] match = null;
	public Double[] format = null;
	
	private int matchSize = 0;
	private int formatSize = 0;
	
	/*
	 * match - double序列, 被匹配的序列
	 * format - double序列， 匹配的模式序列（模式含义见匹配器规则）
	 */
	public SmallerMatcher(Double[] match, Double[] format) {
		this.match = match;
		this.format = format;
		this.matchSize = this.match.length;
		this.formatSize = this.format.length;
	}
	
	//如果模式为-1，则表示要小于1；-0.5，则表示要小于0.5
	//如果模式为1，则表示要等于1；0.5，则表示要等于0.5
	private boolean basicCheck(Double[] m, Double[] f) {
		if(f.length > m.length) return false;
		
		for(int i=0; i<formatSize; i++) {
			if(Double.compare(f[i], 0) < 0) { //-d，表示要小于d
				if(Double.compare(m[i], (-f[i])) >= 0) {
					System.out.println(
							String.format("m[%d]: %f,  f[%d]: %f", i, m[i], i, f[i]));
					return false;
				}
			} else { //d，表示要等于d
				if(Double.compare(m[i], f[i]) != 0) {
					System.out.println(
							String.format("m[%d]: %f,  f[%d]: %f", i, m[i], i, f[i]));
					return false;
				}
			}
		}
		return true;
	}
	
	public int basic(int begin) {
		if(formatSize > matchSize) return -1;
		if(begin > (matchSize - formatSize)) return -1;
		
		int checkCount = matchSize - formatSize + 1;
		for(int i=begin; i<checkCount; i++) {
			System.out.println("=========== " + i + " ============");
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
	
	public static void main(String[] args) {
		Double[] m = {
				1.0,  
				0.9977465441030628,
				0.9881108800010918,
				0.9881108800010918,
				0.9893672769945371, //4
				1.0,
				0.9989498143520752,
				1.0,
				0.9968947744675406,
				1.0, //10
				0.9863672769945371
		};
		Double[] f = {-0.99, -0.99, 1.0};
		
		SmallerMatcher fm = new SmallerMatcher(m,f);
		System.out.println(fm.match(4));
	}

}
