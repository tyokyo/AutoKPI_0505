package com.mr.replay.ui.panel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * 
 * @author houyin.tian
 *
 */
public class ProgressBarPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar aJProgressBar;
	private JLabel lb;
	public ProgressBarPanel(){
		super();
		aJProgressBar = new JProgressBar(0, 100);
		aJProgressBar.setIndeterminate(true);
		aJProgressBar.setBackground(Color.green);
		this.add(aJProgressBar);
		lb = new JLabel("正在关闭，请稍候……");
		lb.setFont(new Font("新宋体", Font.PLAIN, 12));
		this.add(lb);
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.setBackground(Color.GRAY);
	}
}
