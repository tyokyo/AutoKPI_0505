package com.mr.replay.ui.bean;

public class kpiBean {
	public String getKpiavg() {
		return kpiavg;
	}
	public void setKpiavg(String kpiavg) {
		this.kpiavg = kpiavg;
	}
	public String getKpimax() {
		return kpimax;
	}
	public void setKpimax(String kpimax) {
		this.kpimax = kpimax;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRandomid() {
		return randomid;
	}
	public void setRandomid(String randomid) {
		this.randomid = randomid;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		System.out.println("===============");
		System.out.println(this.selected);
		System.out.println(this.adjust);
		System.out.println(this.folder);
		System.out.println(this.iteration);
		System.out.println(this.getKpiavg());
		System.out.println(this.getKpimax());
		System.out.println(this.remark);
		System.out.println(this.script);
		System.out.println(this.summary);
		System.out.println(this.video);
		System.out.println(this.startpicpath);
		System.out.println(this.endpicpath);
		System.out.println(this.randomid);
		System.out.println("===============");
		return super.toString();
	}
	public String getEndpicpath() {
		return endpicpath;
	}
	public void setEndpicpath(String endpicpath) {
		this.endpicpath = endpicpath;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getIteration() {
		return iteration;
	}
	public void setIteration(String iteration) {
		this.iteration = iteration;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getAdjust() {
		return adjust;
	}
	public void setAdjust(String adjust) {
		this.adjust = adjust;
	}
	/*public String getKpi() {
		return kpi;
	}
	public void setKpi(String kpi) {
		this.kpi = kpi;
	}*/
	public String getStartpicpath() {
		return startpicpath;
	}
	public void setStartpicpath(String startpicpath) {
		this.startpicpath = startpicpath;
	}
	private String selected;
	private String folder;
	private String script;
	private	String summary;
	private	String iteration;
	private	String video;
	private	String adjust;
	private	String startpicpath;
	private	String endpicpath;
	private	String remark;
	private	String kpiavg;
	private	String kpimax;
	private String randomid;
		
}
