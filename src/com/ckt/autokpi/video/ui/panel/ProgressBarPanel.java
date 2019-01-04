package com.ckt.autokpi.video.ui.panel;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.ckt.autokpi.video.ui.label.Label;

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
	public ProgressBarPanel(String msg){
		super();
		aJProgressBar = new JProgressBar(0, 100);
		aJProgressBar.setIndeterminate(true);
		aJProgressBar.setBackground(Color.green);
		this.add(aJProgressBar);
		this.add(new Label(msg));
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.setBackground(Color.GRAY);
	}
}
