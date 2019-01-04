package com.mr.replay.ui.dialog;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.mr.replay.ui.panel.AboutPanel;

/**
 * 
 * @author houyin.tian
 *
 */
public class AboutDialog extends JDialog implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int w = 400;
	int h = 300;
	
	public AboutDialog(JFrame owner) {
		super(owner,"关于", true);
		// TODO Auto-generated constructor stub
		this.setBounds(100, 100, w, h);
//		this.setUndecorated(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int x = (int) (toolkit.getScreenSize().getWidth() - w) / 2;
		int y = (int) (toolkit.getScreenSize().getHeight() - h) / 2;
		this.setLocation(x, y);// 设置窗口居中		
		this.setContentPane(new AboutPanel());
		this.setResizable(false);
	}

	/**
	 * 关闭进度条刷新timer及进度条
	 */
	public void close(){
		this.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
