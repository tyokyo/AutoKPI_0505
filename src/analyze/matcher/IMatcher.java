package analyze.matcher;

public interface IMatcher {
	/*
	 * 参数： begin，从序列的第begin个开始进行匹配
	 * 返回：匹配所在的位置
	 */
	public int match(int begin);
}
