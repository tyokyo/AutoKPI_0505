package com.mr.replay.ui.kpireport;

import java.util.ArrayList;

public class ListBean {
	public int getStandardAvg() {
		return standardAvg;
	}
	public void setStandardAvg(int standardAvg) {
		this.standardAvg = standardAvg;
	}
	public int getStandardMax() {
		return standardMax;
	}
	public void setStandardMax(int standardMax) {
		this.standardMax = standardMax;
	}
	/*public int getStandard() {
		return standard;
	}
	public void setStandard(int standard) {
		this.standard = standard;
	}*/
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		System.out.println("========================");
		System.out.println(this.testdomain);
		System.out.println(this.summary);
		System.out.println(this.pysp);
		System.out.println(this.standardAvg);
		System.out.println(this.standardMax);
		System.out.println("");
		for (Integer i : valueList) {
			System.out.print(i+":");
		}
		System.out.println("");
		System.out.println("========================");
		return super.toString();
	}
	public ArrayList<Integer> getValueList() {
		return valueList;
	}
	public void setValueList(ArrayList<Integer> valueList) {
		this.valueList = valueList;
	}
	public String getPysp() {
		return pysp;
	}
	public void setPysp(String pysp) {
		this.pysp = pysp;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getTestdomain() {
		return testdomain;
	}
	public void setTestdomain(String testdomain) {
		this.testdomain = testdomain;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	private String testdomain;
	private int standardAvg;
	private int standardMax;
	private ArrayList<Integer> valueList;
	private String summary;
	private String video;
	private String pysp;
}
