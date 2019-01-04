package com.mr.replay.ui.merge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mr.replay.ui.frame.KPIMain;

public class TextFile {
	/*
	 * 文件是否存在
	 */
	public static boolean fileExists(String filepath) {
		File f = new File(filepath);
		return f.exists();
	}
	/*
	 * 文件所在的目录是否存在
	 */
	public static boolean dirExists(String filepath) {
		File f = new File(filepath);
		return f.getParentFile().exists();
	}
	public static List<String> readWithNoN(String filepath) {
		BufferedReader in;
    	List<String> lines = new ArrayList<String>();
		try {
			String s;
			in = new BufferedReader( new FileReader(filepath));
			while((s = in.readLine())!=null) {
				if (!s.trim().equals("")) {
					lines.add(s);
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
    }
	public static String getDuration(String filepath){
		String durString = "0";
		List<String> lineList = read(filepath);
		for (String line : lineList) {
			if (line.startsWith("Duration=")) {
				durString = line.replaceAll("Duration=", "");
				break;
			}
		}
		return durString;
	}
	public static void refreshDuration(int row){
		String videotxt = KPIMain.spTable.getValueAt(row, 5).toString().replaceAll("path=", "").replaceAll(".avi", ".txt");
		if (new File(videotxt).exists()) {
			String ducation = getDuration(videotxt);
			KPIMain.spTable.setValueAt(ducation, row, 15);
			KPIMain.spTable.repaint();
		}
	}
	/*
	 * 读取文件内容，返回字符串
	 */
	public static List<String> read(String filepath) {
		BufferedReader in;
    	List<String> lines = new ArrayList<String>();
		try {
			String s;
			in = new BufferedReader( new FileReader(filepath));
			while((s = in.readLine())!=null) {
				lines.add(s);
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
    }
	/*
	 * 写内容到文件
	 */
	public static void write(String filepath, List<String> lines) {
		File file = new File(filepath);
		File dir = file.getParentFile();
		try {
			if(!dir.exists()) {
				dir.mkdirs();
			}
			if(!file.exists()) {
				file.createNewFile();
			} else {
				file.delete();
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    	PrintWriter out;
		try {
			out = new PrintWriter(filepath);
			for(String line: lines) {
				out.println(line);
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }
	/*
	 * 写内容到文件，如果没有此文件则创建后再写入
	 */
	public static void writeWhenever(String filepath, List<String> lines) {
		File file = new File(filepath);
		File dir = file.getParentFile();
		try {
			if(!dir.exists()) {
				dir.mkdirs();
			}
			if(!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		write(filepath, lines);
	}
	
	public static void writeWhenever(String filepath, String content) {
		String[] lines = content.split("\n");
		List<String> list = Arrays.asList(lines);
		writeWhenever(filepath, list);
	}
	
	public static void main(String[] args) {
		List<String> content = read("cases//zq001.py"); //"D:\\tatool\\pic2res.txt"
		for(String line: content) {
			System.out.println(line);
		}
		writeWhenever("cases//newcase.py", content);
	}
}
