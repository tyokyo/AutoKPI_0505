package com.mr.replay.ui.merge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mr.replay.ui.helper.JarHelper;

public class CodeMerger {
	
	public List<Map<String, String>> all;
	public List<String> common;
	public StringBuffer merge;
	
	public CodeMerger(List<Map<String, String>> all) {
		this.all = all;
		init();
	}
	
	private void init() {
		this.common = TextFile.read(JarHelper.getJarProjectPath()+"config\\script");
		this.merge = new StringBuffer();
	}
	
	private void merge() {
		this.merge = new StringBuffer();
		Iterator<Map<String, String>> i = this.all.iterator();
		while(i.hasNext()) {
			Map<String, String> map = i.next();
			StepGenerator sg = new StepGenerator(
					map.get("videopath"),
					map.get("scriptpath"),
					Integer.valueOf(map.get("repeat")));
			sg.handle();
			this.merge.append(sg.output());
		}	
	}
	
	public String make() {
		this.merge();
		
		StringBuffer sb = new StringBuffer();
		for(String line: this.common) {
			sb.append(line).append("\n");
		}
		sb.append(this.merge.toString());
		return sb.toString();
	}

	public static void usage() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("videopath", "path=E:\\\\AutoKPI\\\\video\\\\20140320162333\\\\zq001\\\\");
		map1.put("scriptpath", "E:\\autoviewer\\zq001.py");
		map1.put("repeat", String.valueOf(1));
		
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("videopath", "path=E:\\\\AutoKPI\\\\video\\\\20140320162333\\\\zq002\\\\");
		map2.put("scriptpath", "E:\\autoviewer\\zq002.py");
		map2.put("repeat", String.valueOf(2));
		
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("videopath", "path=E:\\\\AutoKPI\\\\video\\\\20140320162333\\\\zq003\\\\");
		map3.put("scriptpath", "E:\\autoviewer\\zq003.py");
		map3.put("repeat", String.valueOf(3));
		
		list.add(map1);
		list.add(map2);
		list.add(map3);
		
		CodeMerger cm = new CodeMerger(list);
		System.out.println(cm.make());
		TextFile.writeWhenever("E:\\autoviewer\\new_case.py", cm.make().toString());
	}
	
	public static void main(String[] args) {
		usage();
	}

}
