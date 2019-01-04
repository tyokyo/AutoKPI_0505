package com.ckt.autokpi.video.ui.dialog;


import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.ckt.autokpi.video.ui.frame.VideoFrame;
import com.ckt.autokpi.video.ui.panel.ProgressBarPanel;
import com.mr.replay.ui.frame.KPIMain;



/**
 * 
 * @author houyin.tian
 *
 */
public class ProgressBarDialog extends JDialog{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private int w = 150;
	private int h = 50;
	private Timer timer;
	public ProgressBarDialog(KPIMain parent,String msg){
		//super(parent, "", true);
		this.setBounds(100, 100, w, h);
		this.setUndecorated(true);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int x = (int) (toolkit.getScreenSize().getWidth() - w) / 2;
		int y = (int) (toolkit.getScreenSize().getHeight() - h) / 2;
		this.setLocation(x, y);// 设置窗口居中		
		this.setContentPane(new ProgressBarPanel(msg));
		this.setResizable(false);
		timer = new Timer();
		final long bt = System.currentTimeMillis();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				long et = System.currentTimeMillis();
				if((et-bt)>3000){
					close();
				}
			}
		}, 1, 1000);
	}
	/**
	 * 关闭进度条刷新timer及进度条
	 */
	public void close(){
		timer.cancel();
		timer.purge();
		this.dispose();
	}
}
