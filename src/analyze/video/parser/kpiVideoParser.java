package analyze.video.parser;

import java.io.File;
import java.util.Properties;

import com.mr.replay.ui.helper.ConfigHelper;
import com.mr.replay.ui.lock.Lock;
import com.mr.replay.ui.log.Log;

/*
 * 输入：视频文件
 * 输出：该视频文件同一级建立同名目录，名字不包含后缀，存放分解后的照片；
 *     根据调整信息，删除前面照片几张；
 *     剩余的照片按顺序排列，名称n_m：n为序号，m为相对起点的毫秒偏移
 */
public class kpiVideoParser {
	
	private File video;
	private int frameRate;
	private int adjust; //毫秒
	private String ffmpeg;
	
	private File newdir = null;
	private double frameInterval;
	public kpiVideoParser(File video, int adjust) {
		super();
		this.video = video;
		this.adjust = adjust;
		init();
	}
	
	private void init() {
		//准备ffmpg
		//Properties p = PropertiesTool.read("config/config.prop");
		ffmpeg = ConfigHelper.getffmpeg();
		//Properties p = PropertiesTool.read("E:\\AutoKPI\\config\\config.prop");
		//this.ffmpeg = p.getProperty("ffmpeg");
		//this.ffmpeg = "E:\\AutoKPI\\ffmpeg\\bin\\ffmpeg.exe";
		Log.info("【ffmpg："+this.ffmpeg+"】");
		//准备帧之间的毫秒间隔
		int fps = Integer.parseInt(ConfigHelper.getfps());
		frameInterval = 1000 / fps;
		Log.info("【帧之间的毫秒间隔："+frameInterval+"】");
		//准备newdir
		File dir = video.getParentFile();
		String name = video.getName();
		newdir = new File(dir, name.split("\\.avi")[0]);
		Log.info("写入文件夹："+newdir);
		if(this.newdir.isDirectory() && this.newdir.exists()) {
			FileOp.deletefile(this.newdir.getPath());
			this.newdir.mkdir();
		} else {
			this.newdir.mkdir();
		}
	}
	
	public boolean videoToImage() {
		//Log.info("开始转换视频,请耐心等待....");
		String[] commands =  { 
				this.ffmpeg, "-i", video.getPath(), 
				this.newdir.getPath()+"\\%5d.jpg" };
		for(String s: commands) {
			Log.info("【"+s+"】");
		}
		String output = CommandRunner.execCommands(commands);
		if (output.contains("Input/output error")) {
			return false;
		}else {
			return true;
		}
		//Log.info("转换视频结束....");
	}
	
	//根据adjust和framerate计算应该删除前面几张
	private int howmany() {
		int howmany = (int) Math.rint(this.adjust / this.frameInterval);
		Log.info("adjust="+adjust);
		Log.info("删除前面:"+howmany+"张图片");
		return howmany;
	}
	
	public void adjust() {
		Log.info("开始删除图片....");
		File[] files = this.newdir.listFiles();
		if (files==null) {
			Lock.parse=false;
		}else {
			for(File f: files) {
				//Log.info(f.getName());
				//System.out.println(f.getName());
			}
			
			int totalCount = files.length;
			int delCount = this.howmany();
			for(int i=0; i<delCount; i++) {
				files[i].delete();
				Log.info("删除图片："+files[i].getName());
			}
			Log.info("删除图片结束....");
			String parentPath = this.newdir.getPath();
			int index = 1;
			for(int i=delCount; i<totalCount; i++) {
				String newname = String.format("%05d_%05d.jpg", index, 
						(int)(Math.rint(this.frameInterval*index)));
				FileOp.renameFile(parentPath, files[i].getName(), newname);
				index++;
			}
		}
	}
	/*
	 * 解析视频样例
	 * 参数： 视频的绝对路径
	 * 功能：该视频文件同一级建立同名目录，名字不包含后缀，存放分解后的照片；
	 *     删除前面大约100毫秒对应的照片
	 *     剩余的照片在新建的目录下按顺序排列，名称n_m：n为序号，m为相对起点的毫秒偏移
	 */
	public static void parse_sample(String path) {
		File v = new File(path);
		kpiVideoParser vp = new kpiVideoParser(v, 100);
		vp.videoToImage();
		vp.adjust();
	}
	
	public static void main(String[] args) {
		//parse_sample("D:\\tmp__\\test.avi");
		parse_sample("E:\\AutoKPI\\video\\20140308175424\\TETE12\\001.avi");
	}

}
