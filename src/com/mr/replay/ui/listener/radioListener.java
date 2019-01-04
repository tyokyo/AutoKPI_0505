package com.mr.replay.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import com.mr.replay.ui.frame.KPIMain;

public class radioListener implements ActionListener{

	private JRadioButton jRadioButton;
	public radioListener(JRadioButton jRadioButton){
		this.jRadioButton = jRadioButton;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("editSummary")) {
			boolean isselect= jRadioButton.isSelected();
			if (isselect==true) {
				System.out.println("====================");
				KPIMain.kpiTable.setEditingColumn(5);
			}
		}
	}
}
