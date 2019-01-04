package com.ckt.autokpi.video.ui.label;

import javax.swing.JLabel;

import com.ckt.autokpi.video.helper.FontHelper;

/**
 * 
 * @author houyin.tian
 *
 */
public class Label extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Label(String msg){
		super(msg);
		FontHelper.setFont(this);
	}
	public Label(){
		super();
		FontHelper.setFont(this);
	}
}
