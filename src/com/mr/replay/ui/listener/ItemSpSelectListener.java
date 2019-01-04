package com.mr.replay.ui.listener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import com.mr.replay.ui.frame.KPIMain;

public class ItemSpSelectListener implements ItemListener{

	public ItemSpSelectListener(){
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int rowcount = KPIMain.spTable.getRowCount();
				// TODO Auto-generated method stub
				if (rowcount>=1) {
					boolean flag = Boolean.parseBoolean(KPIMain.spTable.getValueAt(0, 12).toString());
					if (flag==true) {
						for (int i = 0; i < rowcount; i++) {
							KPIMain.spTableModel.setValueAt(false, i, 12);
						}
					}else {
						for (int i = 0; i < rowcount; i++) {
							KPIMain.spTableModel.setValueAt(true, i, 12);
						}
					}
				}
			}
		});
		t1.start();
	}
}
