package com.mr.replay.ui.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import freemarker.template.TemplateException;

public class AutokpiReport2 {
	HtmlFreemarker hf;
	List<Map<String, String>> source;
	Map<String, String> reference;
	Map<String, String> remark;
	Map<String, Double> average;
	Map<String, List<Double>> result;
	int max = 0; //结果最多的情况
	String reportName; 
	
	/*
	 * Map的结构
	 * "name": "into camera"; "result": "300"; "reference": "280"; "remark": "xxx"
	 */
	public AutokpiReport2(List<Map<String, String>> source) {
		this.source = source;
		this.hf = new HtmlFreemarker("template", "autokpi.ftl");
		this.reportName = String.format("kpi_%s.html",
				System.currentTimeMillis());
		toOutput();
		countAverage();
	}
	
	/**
	 * 测试项	参考值	平均值	第一次	第二次	第三次	第四次	第五次	备注
	 */
	public void generate() {
		//放置参数到 HtmlFreemarker
		hf.root.put("reference", this.reference);
		hf.root.put("remark", this.remark);
		hf.root.put("ave", this.average);
		hf.root.put("result", this.result);
		hf.root.put("max", this.max);
		
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
		this.result = new HashMap<String, List<Double>>();
		this.reference = new HashMap<String, String>();
		this.remark = new HashMap<String, String>();
		
		for(Map<String, String> m: this.source) {
			String name = m.get("name");
			double result = Double.valueOf(m.get("result"));
			if(!this.result.containsKey(name)) {
				this.result.put(name, new ArrayList<Double>());
			}
			if(!this.reference.containsKey(name)) {
				this.reference.put(name, m.get("reference"));
			}
			if(!this.remark.containsKey(name)) {
				this.remark.put(name, m.get("remark"));
			}
			this.result.get(name).add(result);
		}
	}
	
	public void countAverage() {
		this.average = new HashMap<String, Double>();
		Iterator<String> i = this.result.keySet().iterator();
		while(i.hasNext()) {
			String key = i.next();
			List<Double> list = this.result.get(key);
			
			//更新结果数量，记录最多的结果数量
			int count = list.size();
			if(count > this.max) {
				this.max = count;
			}
			
			Double total = 0.0;
			Double ave;
			for(double d: list) {
				total += d;
			}
			if(count > 0) {
				ave = total/count;
			} else {
				ave = 0.0;
			}
			this.average.put(key, ave);
		}
	}
	
	public static void debugOutput(Map<String, List<Double>> output) {
		Iterator<String> i = output.keySet().iterator();
		while(i.hasNext()) {
			String key = i.next();
			System.out.println("key: " + key);
			System.out.println("reference: " + "");
			System.out.println("remark: " + "");
			for(double d: output.get(key)) {
				System.out.println(d);
			}
		}
	}
	
	public static void debugReport(AutokpiReport2 report) {
		Iterator<String> i = report.result.keySet().iterator();
		while(i.hasNext()) {
			String key = i.next();
			System.out.println("key: " + key);
			System.out.println("reference: " + report.reference.get(key));
			System.out.println("remark: " + report.remark.get(key));
			System.out.println("average: " + report.average.get(key));
			System.out.println("size: " + report.result.get(key).size());
			for(double d: report.result.get(key)) {
				System.out.println(d);
			}
		}
	}
	
	public static void reportSample() {
		List<Map<String, String>> source = new ArrayList<Map<String, String>>();
		
		Map m1 = new HashMap<String, String>();
		m1.put("name", "case1"); //脚本
		m1.put("result", "320"); //响应时间，毫秒
		m1.put("reference", "280"); //参考时间，毫秒
		m1.put("remark", "xxx"); //备注
		source.add(m1);
		
		Map m2 = new HashMap<String, String>();
		m2.put("name", "case1");
		m2.put("result", "290");
		m2.put("reference", "280"); //参考时间，毫秒
		m2.put("remark", "yyy"); //备注
		source.add(m2);
		
		Map m3 = new HashMap<String, String>();
		m3.put("name", "case1"); //脚本
		m3.put("result", "310"); //响应时间，毫秒
		m3.put("reference", "280"); //参考时间，毫秒
		m3.put("remark", "zzz"); //备注
		source.add(m3);
		
		Map m4 = new HashMap<String, String>();
		m4.put("name", "case2"); //脚本
		m4.put("result", "230"); //响应时间，毫秒
		m4.put("reference", "240"); //参考时间，毫秒
		m4.put("remark", "aaa"); //备注
		source.add(m4);
		
		Map m5 = new HashMap<String, String>();
		m5.put("name", "case2");
		m5.put("result", "250");
		m5.put("reference", "220"); //参考时间，毫秒
		m5.put("remark", "bbb"); //备注
		source.add(m5);
		
		Map m6 = new HashMap<String, String>();
		m6.put("name", "case3"); //脚本
		m6.put("result", "230"); //响应时间，毫秒
		m6.put("reference", "210"); //参考时间，毫秒
		m6.put("remark", "ccc"); //备注
		source.add(m6);
		
		Map m7 = new HashMap<String, String>();
		m7.put("name", "case4");
		m7.put("result", "250");
		m7.put("reference", "200"); //参考时间，毫秒
		m7.put("remark", "ddd"); //备注
		source.add(m7);
		
		Map m8 = new HashMap<String, String>();
		m8.put("name", "case5"); //脚本
		m8.put("result", "230"); //响应时间，毫秒
		m8.put("reference", "190"); //参考时间，毫秒
		m8.put("remark", "eee"); //备注
		source.add(m8);
		
		Map m9 = new HashMap<String, String>();
		m9.put("name", "case6");
		m9.put("result", "250");
		m9.put("reference", "180"); //参考时间，毫秒
		m9.put("remark", "fff"); //备注
		source.add(m9);
		
		AutokpiReport2 r = new AutokpiReport2(source);
		debugReport(r);
		r.generate();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		reportSample();
	}

}
