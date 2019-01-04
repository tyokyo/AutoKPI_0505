package com.mr.replay.ui.bean;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTable;

import com.mr.replay.thread.StreamCaptureThread;
import com.mr.replay.ui.log.Log;

public class BeanHelper {
	//private static String Logpath = "";
	private static Logger LOG = Logger.getLogger(BeanHelper.class.getName());

	public static String execCmd(String cmd){

		String cmds = "adb  shell " +cmd;
		String output = "" ;
		System.out.println(cmds);
		try {
			Process p = null;
			try {
				p = Runtime.getRuntime().exec(cmds);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StreamCaptureThread errorStream = new StreamCaptureThread(p.getErrorStream());
			StreamCaptureThread outputStream = new StreamCaptureThread(p.getInputStream());
			new Thread(errorStream).start();
			new Thread(outputStream).start();
			p.waitFor();
			String outputString = outputStream.output.toString();
			String errorString  = errorStream.output.toString();
			output  = outputString +"\n"+errorString;
			//System.out.println("["+output+"]");

		} catch (InterruptedException e) {

		}
		return output;
	}
	public static boolean deletefile(String delpath) throws Exception {  
		try {  

			File file = new File(delpath);  
			// 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true  
			if (!file.isDirectory()) {  
				file.delete();  
			}else if (file.isDirectory()) {  
				String[] filelist = file.list();  
				for (int i = 0; i < filelist.length; i++) {  
					File delfile = new File(delpath + "\\" + filelist[i]);  
					if (!delfile.isDirectory()) {  
						delfile.delete();  
						System.out  
						.println(delfile.getAbsolutePath() + "删除文件成功");  
					} else if (delfile.isDirectory()) {  
						deletefile(delpath + "\\" + filelist[i]);  
					}  
				}  
				System.out.println(file.getAbsolutePath()+"删除成功");  
				file.delete();  
			}  

		} catch (FileNotFoundException e) {  
			System.out.println("deletefile() Exception:" + e.getMessage());  
		}  
		return true;  
	}  
	public static String exec(String cmds){

		String output = "" ;
		System.out.println(cmds);
		try {
			Process p = null;
			try {
				p = Runtime.getRuntime().exec(cmds);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StreamCaptureThread errorStream = new StreamCaptureThread(p.getErrorStream());
			StreamCaptureThread outputStream = new StreamCaptureThread(p.getInputStream());
			new Thread(errorStream).start();
			new Thread(outputStream).start();
			p.waitFor();
			String outputString = outputStream.output.toString();
			String errorString  = errorStream.output.toString();
			output  = outputString +"\n"+errorString;
			//System.out.println("["+output+"]");

		} catch (InterruptedException e) {

		}
		return output;
	}
	public static void writeProperties(String filePath, String parameterName, String parameterValue){
		Properties props = new Properties();
		try{
			//InputStream fis = new FileInputStream(filePath);

			InputStream bf=new FileInputStream(filePath);
			InputStreamReader re=new InputStreamReader(bf,"ISO-8859-1");
			BufferedReader  bfs=new BufferedReader(re); 


			props.load(bfs);
			bf.close();
			OutputStream fos = new FileOutputStream(filePath);
			//parameterValue = new String(parameterValue.getBytes(),"unicode");
			props.setProperty(parameterName, parameterValue);
			props.store(fos,"");
			fos.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public static String readValue(String filePath, String key){
		Properties props = new Properties();
		String value = "";
		try{
			props.load(new FileInputStream(filePath));
			value = props.getProperty(key);
			if (value==null) {
				value="";
			}else {
				value = new String(props.getProperty(key).toString().getBytes("ISO-8859-1"),"UTF-8");
				//LOG.info(value);
			}

			//value = new String(props.getProperty(key).toString().getBytes("ISO-8859-1"),"utf-8");
			//value = new String(value.getBytes("ISO-8859-1"), "utf-8");

		}catch(Exception e){
			e.printStackTrace();
		}
		//LOG.info("readvalue:"+value);
		return value;
	}
	public static String testSWVersion(){
		//
		String version = "";
		Process p = null;
		try {
			String cmd = "adb "+Constant.cmdSWversion;
			Log.info(cmd);
			p = Runtime.getRuntime().exec(cmd);
			StreamCaptureThread oStream = new StreamCaptureThread(p.getInputStream());
			new Thread(oStream).start();
			p.waitFor();
			String oStr = oStream.output.toString();
			version = oStr; 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.info("soft ware version:"+version);
		return version;
	}
	public static String execPsGrepUiautomator(){
		//
		String version = "";
		Process p = null;
		try {
			String cmd = "adb shell ps | grep uiautomator ";
			p = Runtime.getRuntime().exec(cmd);
			StreamCaptureThread oStream = new StreamCaptureThread(p.getInputStream());
			new Thread(oStream).start();
			p.waitFor();
			String oStr = oStream.output.toString();
			version = oStr; 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}
	public static Hashtable<String, String> readProperties(String filePath){
		Hashtable<String, String> proHashtable = new Hashtable<String, String>();
		Properties props = new Properties();
		try{
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			Enumeration<?> en = props.propertyNames();
			while(en.hasMoreElements()){
				String key = (String)en.nextElement();
				String property = props.getProperty(key);
				String valueString = new String(property.getBytes("ISO-8859-1"), "utf8");
				proHashtable.put(key, property);
				LOG.info(key + " : " + valueString);
			}
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return proHashtable;
	}
	public static ArrayList<String> readKeys(String filePath){
		ArrayList<String> propArrayList = new ArrayList<String>();
		Properties props = new Properties();
		try{
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			Enumeration<?> en = props.propertyNames();
			while(en.hasMoreElements()){
				String key = (String)en.nextElement();
				LOG.info(key);
				propArrayList.add(key);
			}
			in.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return propArrayList;
	}
	public static void writeToFile(String FileName,String strContent, boolean isAppended) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(FileName,isAppended);
			strContent=strContent+"\r\n";
			byte[] initContent = strContent.getBytes("UTF8");
			fileOutputStream.write(initContent);

			fileOutputStream.close();
			fileOutputStream.flush();
			//LOG.info(FileName);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public static boolean writeStringToFile(String fileName, String content,
			String enc) {
		File file = new File(fileName);

		try {
			if (file.isFile()) {
				file.deleteOnExit();
				file = new File(file.getAbsolutePath());
			}
			OutputStreamWriter os = null;
			if (enc == null || enc.length() == 0) {
				os = new OutputStreamWriter(new FileOutputStream(file));
			} else {
				os = new OutputStreamWriter(new FileOutputStream(file), enc);
			}
			os.write(content);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static void openResult(String FileName){
		try {
			java.awt.Desktop.getDesktop().open(new File(FileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.info("open  failed:"+ e.getMessage());
		}  
	}
	public static String getPackageNameFromManifest(File root) throws IOException {
		File manifest = new File(root.getAbsolutePath()+ "/AndroidManifest.xml");
		String result = null;
		if (manifest.exists()) {
			FileReader fr = new FileReader(manifest);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null) {
				if (line.contains("package")&&(!line.contains("package name must be"))) {
					result = line.trim();
					result = result.substring(result.indexOf("package") + 9);
					result = result.substring(0, result.indexOf("\""));
					break;
				}
				line = br.readLine();
			}
		}
		return result;
	}
	public static ArrayList<String> getPackagList(String pkgTxt){
		ArrayList<String> pkgList=new ArrayList<String>();
		try {
			File file = new File(pkgTxt);
			if (file.exists()==true) {
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String strTmp = "";
				while ((strTmp = br.readLine()) != null) {
					if (strTmp.trim().length()>0) {
						pkgList.add(strTmp.trim());
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return pkgList;
	}
	public static String getMonkeyList(String path){
		String content = "";
		try {
			File file = new File(path);
			if (file.exists()==true) {
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String strTmp = "";
				while ((strTmp = br.readLine()) != null) {
					content = content + strTmp+"<BR>";
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return content;
	}
	public static String getFileName(String asbpath){
		String name = "";
		String pyname = asbpath.replaceAll(".py", "");
		name = pyname.substring(pyname.lastIndexOf("\\")+1, pyname.length());
		return name;
	}
	public static String rightTrim(String str) {
		str = str.replaceAll("\t", "");
		int length = str.length();
		char[] data = str.toCharArray();
		int lastNotSpace = 0;
		for (int i = length - 1; i > 0; i--) {
			if (data[i] != ' ') {
				lastNotSpace = i;
				break;
			}
		}
		if (lastNotSpace==0) {
			return str;
		}else {
			return str.substring(0, lastNotSpace + 1);
		}

	}
	public static String trimLR(String str){
		int frtspace = 0;
		int lstspace = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i)!=' ') {
				frtspace=i;
				break;
			}
		}
		for (int t = str.length()-1; t >=0; t--) {
			if (str.charAt(t)!=' ') {
				lstspace=t;
				break;
			}
		}
		return str.substring(frtspace, lstspace);
	}
	public static ArrayList<String> currentDevice() throws ArrayIndexOutOfBoundsException{
		ArrayList<String> crDeviceList = new ArrayList<String>();
		Process p = null;
		String adbstr = "";
		try {
			String cmd = "adb devices";
			LOG.info(cmd);
			p = Runtime.getRuntime().exec(cmd);
			StreamCaptureThread oStream = new StreamCaptureThread(p.getInputStream());
			new Thread(oStream).start();
			p.waitFor();
			String oStr = oStream.output.toString();
			adbstr = oStr; 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOG.info(adbstr);
		String[] devstrsp = adbstr.split("attached");
		String tmpdevstr = devstrsp[1];
		String[] devList= tmpdevstr.split("device");
		for (String dev : devList) {
			if (!dev.trim().equals("")) {
				String device= trimLR(dev.replace("\n", ""));
				//LOG.info("["+device+"]");
				crDeviceList.add(device);
			}
		}
		return crDeviceList;
	}
	public static void AdbpushUiAutomatorJar(String device) throws IOException{
		String cmds = "adb " +
		" -s \""+device+"\""+
		" push " +Constant.compileJarname+
		"  /data/local/tmp/";
		System.out.println(cmds);
		try {

			Process p = Runtime.getRuntime().exec(cmds);
			StreamCaptureThread errorStream = new StreamCaptureThread(p.getErrorStream());
			StreamCaptureThread outputStream = new StreamCaptureThread(p.getInputStream());
			new Thread(errorStream).start();
			new Thread(outputStream).start();
			p.waitFor();
			//LOG.info("=============output=======================");
			String outputString = outputStream.output.toString();
			//LOG.info("=============error =======================");
			String errorString  = errorStream.output.toString();
			String output  = outputString +"\n"+errorString;
			System.out.println(output);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch bloc	k
			//outputStream.output.append("\n"+"FAILURES!!!"+"\n"+"[MainThread]"+"\n");
			//interrupt = true;
			//e.printStackTrace();
		}
	}
	public static void AdbpushConfig(String filename) throws IOException{

		String cmds = "adb  push " +filename+"  /sdcard/";
		System.out.println(cmds);
		try {
			Process p = Runtime.getRuntime().exec(cmds);
			StreamCaptureThread errorStream = new StreamCaptureThread(p.getErrorStream());
			StreamCaptureThread outputStream = new StreamCaptureThread(p.getInputStream());
			new Thread(errorStream).start();
			new Thread(outputStream).start();
			p.waitFor();
			String outputString = outputStream.output.toString();
			String errorString  = errorStream.output.toString();
			String output  = outputString +"\n"+errorString;
			System.out.println(output);

		} catch (InterruptedException e) {

		}
	}
	public static String AdbCmds(String cmds) throws IOException{

		System.out.println(cmds);
		String rtnStr = "";
		try {
			Process p = Runtime.getRuntime().exec(cmds);
			StreamCaptureThread errorStream = new StreamCaptureThread(p.getErrorStream());
			StreamCaptureThread outputStream = new StreamCaptureThread(p.getInputStream());
			new Thread(errorStream).start();
			new Thread(outputStream).start();
			p.waitFor();
			String outputString = outputStream.output.toString();
			String errorString  = errorStream.output.toString();
			String output  = outputString +"\n"+errorString;
			System.out.println(output);
			rtnStr=output;
		} catch (InterruptedException e) {

		}
		return rtnStr;
	}
	public static String trimPackage(String method){
		//System.out.println(method);
		String trimpackage = "";
		String[] spmethod = method.split("\\.");
		//System.out.println(spmethod[0]);
		trimpackage=spmethod[spmethod.length-1];
		return trimpackage;
	}
	public static String getAllFileContent(String filepath) throws FileNotFoundException{
		String srcFile = "C:\\monkey\\results\\com.mediatek.FMRadio.txt";  

		String ctent =  "";
		int bufSize = 1024;
		byte[] bs = new byte[bufSize];
		ByteBuffer byteBuf = ByteBuffer.allocate(1024);
		FileChannel channel = new RandomAccessFile(srcFile,"r").getChannel();
		try {
			while(channel.read(byteBuf) != -1) {
				int size = byteBuf.position();
				byteBuf.rewind();
				byteBuf.get(bs);
				System.out.print(new String(bs, 0, size));
				ctent = new String(bs, 0, size);
				byteBuf.clear();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ctent;
	}
	public static String KillUiAutomatorPids(){
		String uiAutostr = execPsGrepUiautomator();
		if (uiAutostr.replace("\n", "").trim().length()>=1) {
			System.out.println("-=-=-=-=-=-=--=-=---=-");
			String[] pids = uiAutostr.split("\n");
			System.out.println(pids.length);
			for (int i = 0; i < pids.length; i++) {
				pids[i] = pids[i].replace("shell", "");
				System.out.println(pids[i]);
				int start = 0;
				for (int j = 0; j < pids[i].length(); j++) {
					if (pids[i].charAt(j)!=' ') {
						start = j;
						break;
					}
				}
				int end = 0;
				for (int j = start; j < pids[i].length(); j++) {
					if (pids[i].charAt(j)==' ') {
						end = j;
						break;
					}
				}
				final String pid = pids[i].substring(start, end).trim();
				System.out.println("["+pid+"]");
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							AdbCmds("adb shell kill "+pid.trim());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}
		}
		return uiAutostr;

	}
	public static void main(String args[]) throws IOException{
		//System.out.println(execPsGrepUiautomator());
		//KillUiAutomatorPids();
		File f = new File("E:\\AutoKPI\\video\\20140321165912\\zq006\\");
		if (f.isDirectory()) {
			f.delete();
		}
	}
}

