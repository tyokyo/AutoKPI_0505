package com.mr.replay.ui.dialog;


import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.mr.replay.ui.panel.ProgressBarPanel;

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
	
	int w = 150;
	int h = 50;
	Timer timer;
	
	public ProgressBarDialog(JFrame parent){
		this.setBounds(100, 100, w, h);
		this.setUndecorated(true);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int x = (int) (toolkit.getScreenSize().getWidth() - w) / 2;
		int y = (int) (toolkit.getScreenSize().getHeight() - h) / 2;
		this.setLocation(x, y);// 设置窗口居中		
		this.setContentPane(new ProgressBarPanel());
		this.setResizable(false);
		timer = new Timer();
		final long bt = System.currentTimeMillis();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				long et = System.currentTimeMillis();
				if((et-bt)>5000){
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
