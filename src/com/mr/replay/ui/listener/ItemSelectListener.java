package com.mr.replay.ui.listener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import com.mr.replay.ui.frame.KPIMain;

public class ItemSelectListener implements ItemListener{

	public ItemSelectListener(){
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int rowcount = KPIMain.kpiTbModel.getRowCount();
				// TODO Auto-generated method stub
				if (rowcount>=1) {
					boolean flag = Boolean.parseBoolean(KPIMain.kpiTbModel.getValueAt(0, 1).toString());
					if (flag==true) {
						for (int i = 0; i < rowcount; i++) {
							KPIMain.kpiTbModel.setValueAt(false, i, 1);
						}
					}else {
						for (int i = 0; i < rowcount; i++) {
							KPIMain.kpiTbModel.setValueAt(true, i, 1);
						}
					}
				}
			}
		});
		t1.start();
	}

}
