package com.mr.replay.ui.kpireport;


public class rptBean {
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		System.out.println("---------------------");
		System.out.println(this.testdomain);
		System.out.println(this.kpivalue);
		System.out.println(this.value);
		System.out.println(this.summary);
		System.out.println(this.video);
		System.out.println(this.pysp);
		System.out.println("---------------------");
		return super.toString();
	}
	public int getKpivalue() {
		return kpivalue;
	}
	public void setKpivalue(int kpivalue) {
		this.kpivalue = kpivalue;
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
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
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
	private int kpivalue;
	private int value;
	private String summary;
	private String video;
	private String pysp;
}
