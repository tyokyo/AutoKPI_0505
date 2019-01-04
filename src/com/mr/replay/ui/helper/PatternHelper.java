package com.mr.replay.ui.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternHelper {
	
	
	public static boolean testPartternInt(String value) {
		String pattern = "^([1-9])[0-9]{0,1}";//判断是否为1-2位整数
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(value);
		boolean b = m.matches();
		return b;

	}

	public static void main(String args[]) {
		System.out.println(testPartternInt("ad"));
		System.out.println(testPartternInt("0"));
	}
}
