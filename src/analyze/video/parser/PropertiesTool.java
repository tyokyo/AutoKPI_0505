package analyze.video.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesTool {
	/*
	 * read
	 */
	public static Properties read(String path) {
		//创建输入字节流
		InputStream is;
		//创建一个Properties容器
        Properties prop = new Properties();
		try {
			//将properties文件加载到输入字节流中
			is = new FileInputStream(path);
			//从流中加载properties文件信息
	        prop.load(is);
	        is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return prop;
	}
	
	public static Properties readFromXml(String path) {
		//创建输入字节流
		InputStream is;
		//创建一个Properties容器
        Properties prop = new Properties();
		try {
			//将properties文件加载到输入字节流中
			is = new FileInputStream(path);
			//从xml加载配置信息，填充Properties容器
            prop.loadFromXML(is);
	        is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return prop;
	}
	
	/*
	 * write
	 */
	public static void write(String path, Properties prop, String comments) {
        OutputStream os;
		try {
			//定义一个输出流
			os = new FileOutputStream(path);
			//从Properties对象导出properties文件
	        prop.store(os, comments);
	        os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	public static void writeToXml(String path, Properties prop, String comments) {
        OutputStream os;
		try {
			//定义一个输出流
			os = new FileOutputStream(path);
			//从Properties对象导出导出到xml
	        prop.storeToXML(os, comments);
	        os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/*
	 * print
	 */
	public static void printProp(Properties prop) {
		//循环输出配置信息
        for (Object key : prop.keySet()) {
                System.out.println(key + "=" + prop.get(key));
        }
	}
	
	public static void main(String[] args) {
		Properties prop = read("prop\\test.properties");
		printProp(prop);
		Properties propxml = readFromXml("prop\\ttt.xml");
		printProp(propxml);
		
		prop.put("name", "kemin");
		prop.put("team", "vas app");
		write("prop\\add.properties", prop, "我的配置");
		writeToXml("prop\\add.xml", prop, "我的配置");
		
		prop = read("prop\\add.properties");
		printProp(prop);
		propxml = readFromXml("prop\\add.xml");
		printProp(propxml);
	}

}
