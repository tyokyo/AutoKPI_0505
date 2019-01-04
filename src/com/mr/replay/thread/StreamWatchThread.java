package com.mr.replay.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.mr.replay.ui.log.Log;

/**
 * 
 * @author houyin.tian
 *
 */
public class StreamWatchThread extends Thread {
	InputStream is;
	String type;
	StringBuffer output = new StringBuffer();
	boolean debug = false;
	public StreamWatchThread(InputStream is, String type) {
		this(is, type, false);
	}
	public StreamWatchThread(InputStream is, String type, boolean debug) {
		this.is = is;
		this.type = type;
		this.debug = debug;
	}
	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is,"UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				output.append(line+"\n");
				if (debug){
					if(type.equals("info")){
						Log.info(line);
					}else{
						Log.err(line);
					}
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	public String getOutput() {
		return output.toString();
	}
}
