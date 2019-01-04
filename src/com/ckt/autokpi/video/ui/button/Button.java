package com.ckt.autokpi.video.ui.button;

import javax.swing.JButton;

import com.ckt.autokpi.video.helper.FontHelper;

public class Button extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Button(String title){
		super(title);
		FontHelper.setFont(this);
	}
}
