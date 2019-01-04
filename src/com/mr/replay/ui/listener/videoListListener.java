package com.mr.replay.ui.listener;

import java.io.File;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mr.replay.ui.frame.KPIMain;
import com.mr.replay.ui.helper.JarHelper;

public class videoListListener implements ListSelectionListener{

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		if (KPIMain.videosListModel.size()>=1) {
			String videoname = KPIMain.list.getSelectedValue().replaceAll(".avi", "");
			System.out.println(videoname);

			int index = KPIMain.kpiTable.getSelectedRow();
			String folder = KPIMain.kpiTable.getValueAt(index, 2).toString();
			String resfolder = KPIMain.kpiTable.getValueAt(index, 3).toString();

			String videoresultpath = videoname+"_result";
			String resultAbspath = 
				String.format("%s\\%s\\%s\\%s\\%s",
						JarHelper.getProjectPath(),
						"video",
						folder,
						resfolder,
						videoresultpath);
			System.out.println(resultAbspath);
			File pifFile = new File(resultAbspath);
			if (pifFile.exists()) {
				String startpic =resultAbspath+"\\start";
				String endpic =resultAbspath+"\\end";
				File startf= new File(startpic);
				if (startf.exists()) {
					File[] startList = startf.listFiles();
					if (startList.length==1) {
						KPIMain.starTextField.setText(startList[0].getName());
					}
				}
				File endf= new File(endpic);
				if (endf.exists()) {
					File[] endList = endf.listFiles();
					if (endList.length==1) {
						KPIMain.endTextField.setText(endList[0].getName());
					}
				}
			}else {

			}
		}
	}

}
