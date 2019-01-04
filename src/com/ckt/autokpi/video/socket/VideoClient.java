package com.ckt.autokpi.video.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import com.ckt.autokpi.video.ui.log.Log;
import com.ckt.autokpi.video.ui.panel.VideoPanel;
import com.mr.replay.ui.lock.Lock;

/**
 * 
 * @author houyin.tian
 *
 */
public class VideoClient implements Runnable{
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private String msg;
	private String path;
	private VideoPanel videoPanel;
	private List<VideoClient> clientList;

	public VideoClient(Socket socket,VideoPanel videoPanel,List<VideoClient> clientList) throws IOException {
		this.socket = socket;
		this.videoPanel = videoPanel;
		this.clientList = clientList;
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
	}

	public void run() {
		try {
			msg = "【" + socket.getInetAddress() + "】进入连接！当前已有【"+ clientList.size() + "】个连接";
			Log.info(msg);
			while ((msg = in.readUTF()) != null) {
				Log.info("【" + socket.getInetAddress() + "】" + msg);
				if (msg.trim().equals("exit")) {
					videoPanel.stopRecord();
					break;
				} else if (msg.trim().equals("stop")) {
					if (Lock.record) {
						Log.info("【" + socket.getInetAddress() + "】停止录制时间："+ System.nanoTime());
						videoPanel.stopRecord();
					} else {
						this.send("还未开始录制！");
					}
				} else if (msg.startsWith("path=")&&msg.endsWith(".avi")) {
					if (!Lock.record) {
						path = msg.split("=")[1];
						File dir = new File(new File(path).getParent());
						if(!dir.exists()){
							dir.mkdirs();
						}
						Log.info("【" + socket.getInetAddress() + "】开始录制时间："+ System.nanoTime());
						videoPanel.startRecord(path);
					} else {
						this.send("已经开始录制，请先停止！");
					}
				}
			}
		} catch (SocketException se) {
			videoPanel.stopRecord();
			Log.err("【" + socket.getInetAddress() + "】异常断开连接！");
		} catch (EOFException eofe) {
			videoPanel.stopRecord();
			Log.warn("【" + socket.getInetAddress() + "】退出！");
		} catch (IOException ie) {
			videoPanel.stopRecord();
			ie.printStackTrace();
			Log.err(ie.toString());
		} finally {
			this.close();
		}
	}

	/**
	 * 向客户端发送消息
	 * 
	 * @param msg
	 */
	public void send(String msg) {
		try {
			if(out!=null){
				out.writeUTF(msg);
				out.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 关闭当前客户端
	 */
	public void close(){
		for(int i=clientList.size()-1;i>=0;i--){
			if(clientList.get(i) == this){
				clientList.remove(i);
				break;
			}
		}
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = "【" + socket.getInetAddress() + "】退出连接！当前还有【"+ clientList.size()+ "】个连接";
		Log.info(msg);
	}
}
