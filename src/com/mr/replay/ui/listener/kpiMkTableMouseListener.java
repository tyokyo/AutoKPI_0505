package com.mr.replay.ui.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import com.mr.replay.ui.bean.BeanHelper;
import com.mr.replay.ui.frame.KPIMain;
import com.mr.replay.ui.helper.JarHelper;
import com.mr.replay.ui.log.Log;

public class kpiMkTableMouseListener implements MouseListener {

	public kpiMkTableMouseListener(){
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getClickCount()==2) {
			int row = KPIMain.spTable.getSelectedRow();
			if (row>=0) {
				String folder = KPIMain.spTable.getValueAt(row, 5).toString().replaceAll("path=", "").replaceAll(".avi", "");
				if (new File(folder).exists()==true) {
					Log.info("打开文件夹:"+folder);
					BeanHelper.openResult(folder);
				}else {
					Log.info("文件夹:"+folder+"不存在");
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
