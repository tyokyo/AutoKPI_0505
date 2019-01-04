package analyze.counter;

public interface ICounter {
	/*
	 * 模式匹配出现指定的次数（不匹配则跳过不匹配的内容，继续往后计数）
	 * 参数： begin，从序列的第begin个开始进行匹配
	 *     count，模式应该被匹配的次数
	 * 返回：模式被匹配count次后，游标所在的位置
	 */
	public int count(int begin, int count);
}
