package com.mr.replay.ui.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import com.mr.replay.ui.bean.ReadFile;
import com.mr.replay.ui.frame.KPIMain;
import com.mr.replay.ui.helper.JarHelper;
import com.mr.replay.ui.log.Log;

public class kpiTableKeyListener implements KeyListener{

	JTable kpiTable;
	public kpiTableKeyListener(JTable kpiTable){
		this.kpiTable = kpiTable;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	public String getSelectdkpiFolderPop(){
		StringBuffer stringBuffer = new StringBuffer();
		int rowCnt = KPIMain.kpiTable.getRowCount();
		for (int i = 0; i < rowCnt; i++) {
			boolean select = Boolean.parseBoolean(KPIMain.kpiTable.getValueAt(i, 1).toString());
			if (select) {
				stringBuffer.append("删除文件夹："+KPIMain.kpiTable.getValueAt(i, 4).toString().replaceAll(".py", "")+"\n");
			}
		}
		Log.info(stringBuffer.toString());
		return stringBuffer.toString();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode()==KeyEvent.VK_DELETE){
			int rowcnt =  KPIMain.kpiTable.getRowCount();
			ArrayList<String> isselect =new  ArrayList<String>();
			String popStr = getSelectdkpiFolderPop();
			for (int i = 0; i < rowcnt; i++) {
				Boolean selected = Boolean.parseBoolean(KPIMain.kpiTbModel.getValueAt(i, 1).toString());
				if (selected) {
					String random = KPIMain.kpiTable.getValueAt(i, 3).toString();
					String src = KPIMain.kpiTable.getValueAt(i, 2).toString();
					String path = src+File.separator+random;
					isselect.add(path);
				}
			}
			if (isselect.size()>=1) {
				if(JOptionPane.showConfirmDialog(new JFrame(),"确定删除选中的"+isselect.size()+"项数据?\n"+popStr+"\n包括视频,图片,分析结果,请慎重... ", 
						"Warning",JOptionPane.YES_NO_OPTION) == 0){
					String jarpath = JarHelper.getProjectPath()+"video\\";
					for (String fp : isselect) {
						String ppf = jarpath+fp+"\\";
						try {
							ReadFile.deletefile(ppf);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						new File(ppf).mkdirs();
					}
					KPIMain.videosListModel.removeAllElements();
					KPIMain.videosListModel.addElement("无数据...");
					KPIMain.list.repaint();
					
					KPIMain.constructTable();
					
					JOptionPane.showMessageDialog(new JFrame(), "成功删除资源文件!\n"+popStr,"恭喜", JOptionPane.WARNING_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(new JFrame(), "没有选中数据!","Warning", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
