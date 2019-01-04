package com.ckt.autokpi.video.socket;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.ckt.autokpi.video.helper.ConfigHelper;
import com.ckt.autokpi.video.ui.log.Log;
import com.ckt.autokpi.video.ui.panel.VideoPanel;

/**
 * 
 * @author houyin.tian
 *
 */
public class VideoServer extends Thread{
	private ExecutorService exec;
	private ServerSocket server;
	private VideoPanel videoPanel;
	private boolean bool = true;

	private List<VideoClient> clientList = new ArrayList<VideoClient>();
	
	public VideoServer(VideoPanel videoPanel) {
		this.videoPanel = videoPanel;
	}

	/**
	 * 
	 */
	public void run(){
		try {
			server = new ServerSocket(ConfigHelper.getSocketServerPort());
			exec = Executors.newCachedThreadPool();
			Log.info("服务器已启动！");
			Socket client = null;
			while (bool) {
				client = server.accept();// 接收客户连接
				VideoClient handler = new VideoClient(client,videoPanel,clientList);
				clientList.add(handler);
				exec.execute(handler);
			}
		}catch(BindException be){
			System.out.println("=====BindException======");
			be.printStackTrace();
		}catch(SocketException se){
			
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}
	
	/**
	 * 通知所有客户端，server即将退出。
	 */
	public void close(){
		bool = false;
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(exec!=null){
			exec.shutdown();
		}
		for(int i=clientList.size()-1;i>=0;i--){
			VideoClient ch = clientList.get(i);
			ch.send("exit");
			ch.close();
		}
	}
	
	/**
	 * 当前连接客户端
	 * 
	 * @return
	 */
	public int getActiveCount() {
		int activeCount = ((ThreadPoolExecutor) exec).getActiveCount();
		return activeCount;
	}
	
	
}