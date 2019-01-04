package com.mr.replay.ui.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import freemarker.template.TemplateException;

public class AutokpiReport {
	HtmlFreemarker hf;
	List<Map<String, String>> source;
	public static Map<String, List<Double>> output;
	String reportName; 
	
	/*
	 * Map的结构
	 * "name": "into camera"; "result": "300"
	 */
	public AutokpiReport(List<Map<String, String>> source) {
		this.source = source;
		this.hf = new HtmlFreemarker("template", "autokpi.ftl");
		this.reportName = String.format("kpi_%s.html",
				System.currentTimeMillis());
		toOutput();
	}
	
	public void generate() {
		//放置参数到 HtmlFreemarker
		hf.root.put("output", this.output);
		
		//产生报告
		try {  
            hf.make(this.reportName);
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (TemplateException e) {  
            e.printStackTrace();  
        }  
	}
	
	public void toOutput() {
		this.output = new HashMap<String, List<Double>>();
		for(Map<String, String> m: this.source) {
			String name = m.get("name");
			double result = Double.valueOf(m.get("result"));
			if(!this.output.containsKey(name)) {
				this.output.put(name, new ArrayList<Double>());
			}
			this.output.get(name).add(result);
		}
	}
	
	public static void debugOutput(Map<String, List<Double>> output) {
		Iterator<String> i = output.keySet().iterator();
		while(i.hasNext()) {
			String key = i.next();
			//System.out.println("key: " + key);
			for(double d: output.get(key)) {
				System.out.println(d);
			}
		}
	}
	
	public static void reportSample() {
		List<Map<String, String>> source = new ArrayList<Map<String, String>>();
		
		Map m1 = new HashMap<String, String>();
		m1.put("name", "case1"); //脚本
		m1.put("result", "320"); //响应时间，毫秒
		source.add(m1);
		
		Map m2 = new HashMap<String, String>();
		m2.put("name", "case1");
		m2.put("result", "290");
		source.add(m2);
		
		Map m3 = new HashMap<String, String>();
		m3.put("name", "case1"); //脚本
		m3.put("result", "310"); //响应时间，毫秒
		source.add(m3);
		
		Map m4 = new HashMap<String, String>();
		m4.put("name", "case2"); //脚本
		m4.put("result", "230"); //响应时间，毫秒
		source.add(m4);
		
		Map m5 = new HashMap<String, String>();
		m5.put("name", "case2");
		m5.put("result", "250");
		source.add(m5);
		
		Map m6 = new HashMap<String, String>();
		m6.put("name", "case3"); //脚本
		m6.put("result", "230"); //响应时间，毫秒
		source.add(m6);
		
		Map m7 = new HashMap<String, String>();
		m7.put("name", "case4");
		m7.put("result", "250");
		source.add(m7);
		
		Map m8 = new HashMap<String, String>();
		m8.put("name", "case5"); //脚本
		m8.put("result", "230"); //响应时间，毫秒
		source.add(m8);
		
		Map m9 = new HashMap<String, String>();
		m9.put("name", "case6");
		m9.put("result", "250");
		source.add(m9);
		
		AutokpiReport r = new AutokpiReport(source);
		debugOutput(r.output);
		r.generate();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		reportSample();
	}

}
