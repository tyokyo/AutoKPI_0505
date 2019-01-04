package com.mr.replay.ui.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.mr.replay.ui.helper.ConfigHelper;
import com.mr.replay.ui.helper.ServiceHelper;
import com.mr.replay.ui.log.Log;

public class hashCompare {
	private static String folder;
	public hashCompare(String folder){
		this.folder = folder;
	}
	public static int getFileCount(String folder){
		File f = new File(folder);
		int count= 0 ;
		File[] files = f.listFiles();
		for (File file : files) {
			if (file.getName().endsWith(".jpg")) {
				count = count+1;
			}
		}
		return count;
	}
	public static String indexcovertoname(int index){
		int fps = Integer.parseInt(ConfigHelper.getfps());
		int frameInterval = 1000 / fps;
		
		String picname = String.format("%05d_%05d.jpg", index, index*frameInterval);
		
		return picname;
	}
	public int startPic(int dis,int interval) throws FileNotFoundException, Exception{
		int indexNm=0;
		int total = getFileCount(folder);
		ArrayList<Integer> results = new ArrayList<Integer>();
		ImageHash phash = new ImageHash();
		String image1;
		String image2;
		//String path = "E:/AutoKPI/video/20140319092043/04/001/";
		//String path1 = folder+"00001_00010.jpg";
		String cmpname = indexcovertoname(1);
		String path1 = folder+File.separator+cmpname;
		//Log.info("比较基准图片:"+path1);
		image1= phash.getHash(new FileInputStream(new File(path1)));
		String startFileName = folder+File.separator+"start.txt";
		File startFile = new File(startFileName);
		System.out.println(startFileName);
		Log.info(startFileName);
		if (!startFile.exists()) {
			startFile.createNewFile();
		}else {
			ServiceHelper.writeToFile(startFileName, "", false);
		}
		
		for (int i = 1; i <=total; i++) {
			String index = indexcovertoname(i);
			String indexpath =folder+File.separator+index;
			image2= phash.getHash(new FileInputStream(new File(indexpath)));
			int distance = phash.distance(image1, image2);
			String strContent = cmpname+":"+index+":"+distance;
			Log.info(cmpname+":"+index+" diff data " + distance);
			//System.out.println(cmpname+":"+index+" diff data is " + phash.distance(image1, image2));
			results.add(distance);
			ServiceHelper.writeToFile(startFileName, strContent, true);
		}
		for (int i = 0; i < total-5; i++) {
			int key0 =results.get(i);
			int key1 =results.get(i+1);
			int key2 =results.get(i+2);
			int key3 =results.get(i+3);
			int key4 =results.get(i+4);
			if (key0>=dis&&key1>=dis&&key2>=dis&&key3>=dis&&key4>=dis) {
				indexNm=i;
				System.out.println("===========================");
				break;
			}
		}
		Log.info(""+indexNm);
		return indexNm;
	}
	public int endPic(int dis,int interval) throws FileNotFoundException, Exception{
		int indexNm=0;
		int total = getFileCount(folder);
		ArrayList<Integer> results = new ArrayList<Integer>();
		ImageHash phash = new ImageHash();
		String image1;
		String image2;
		//String path = "E:/AutoKPI/video/20140319092043/04/001/";
		//String cmppicname = String.format("%05d_%05d",total,total*interval);
		//String path1 = folder+cmppicname+".jpg";
		String cmpname = indexcovertoname(total);
		String path1 = folder+File.separator+cmpname;
		image1= phash.getHash(new FileInputStream(new File(path1)));

		String endFileName = folder+File.separator+"end.txt";
		File endFile = new File(endFileName);
		System.out.println(endFileName);
		Log.info(endFileName);
		if (!endFile.exists()) {
			endFile.createNewFile();
		}else {
			ServiceHelper.writeToFile(endFileName, "", false);
		}
		for (int i = 1; i <=total; i++) {
			String index = indexcovertoname(i);
			String indexpath =folder+File.separator+index;
			image2= phash.getHash(new FileInputStream(new File(indexpath)));
			int distance = phash.distance(image1, image2);
			String strContent = cmpname+":"+index+":"+distance;
			Log.info(cmpname+":"+index+" diff data " + distance);
			results.add(distance);
			ServiceHelper.writeToFile(endFileName, strContent, true);
		}
		for (int i = 0; i < total-5; i++) {
			int key0 =results.get(i);
			int key1 =results.get(i+1);
			int key2 =results.get(i+2);
			int key3 =results.get(i+3);
			int key4 =results.get(i+4);
			if (key0<=dis&&key1<=dis&&key2<=dis&&key3<=dis&&key4<=dis) {
				indexNm=i;
				System.out.println("===========================");
				break;
			}
		}
		Log.info(""+indexNm);
		return indexNm;
	}
	public static void main(String args[]) throws FileNotFoundException, Exception{
		String folderString ="E:/AutoKPI/video/20140319092043/04/001/";
		hashCompare s = new hashCompare(folderString);
		s.startPic(4,10);
		s.endPic(3, 10);
	}

}
