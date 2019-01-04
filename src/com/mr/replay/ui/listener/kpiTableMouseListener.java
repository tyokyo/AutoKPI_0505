package com.mr.replay.ui.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTable;

import com.mr.replay.ui.bean.BeanHelper;
import com.mr.replay.ui.helper.JarHelper;

public class kpiTableMouseListener implements MouseListener {

	JTable kpiTable;
	JList<String> list;
	DefaultListModel<String> videosListModel;
	public kpiTableMouseListener(JTable kpiTable,JList<String> list,DefaultListModel<String> videosListModel){
		this.kpiTable = kpiTable;
		this.list = list;
		this.videosListModel = videosListModel;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int row = kpiTable.getSelectedRow();
		ArrayList<String> videos = new ArrayList<String>();
		String folder = kpiTable.getValueAt(row, 2).toString();
		String resfolder = kpiTable.getValueAt(row, 3).toString();
		String videopath = JarHelper.getProjectPath()+"video\\"+folder+"\\"+resfolder;
		System.out.println(videopath);
		videos = JarHelper.getVideos(videopath);
		videosListModel.removeAllElements();
		list.repaint();
		if (videos.size()>=1) {
			for (String video : videos) {
				videosListModel.addElement(video);
				list.repaint();
			}
		}else {
			videosListModel.addElement("无数据...");
			list.repaint();
		}
		if (e.getClickCount()==2) {
			System.out.println("===============");
			if (new File(videopath).exists()) {
				BeanHelper.openResult(videopath);
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
