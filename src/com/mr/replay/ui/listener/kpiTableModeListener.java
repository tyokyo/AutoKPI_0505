package com.mr.replay.ui.listener;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import com.mr.replay.ui.bean.Constant;
import com.mr.replay.ui.frame.KPIMain;
import com.mr.replay.ui.helper.JarHelper;
import com.mr.replay.ui.xml.XmlUpdate;

public class kpiTableModeListener implements TableModelListener{

	private JTable kpitTable;
	public kpiTableModeListener(JTable kpitTable){
		this.kpitTable = kpitTable;
	}
	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		int col = e.getColumn();                   
		int row = e.getFirstRow();  
		
		String xml = JarHelper.getProjectPath()+"config"+File.separator+"testxml.xml";
		
		//is selected
		if (col==1) {
			System.out.println(row+":"+col);
			String randomid = kpitTable.getValueAt(row, 3).toString();
			String isselect =kpitTable.getValueAt(row, col).toString();
			
			XmlUpdate.testCaseUpdateXml(xml, randomid, "isSelect", isselect);
			
			//KPIMain.constructTable();
		}
		//summary
		if (col==5) {
			System.out.println(row+":"+col);
			String pys = kpitTable.getValueAt(row, 4).toString();
			String randomid = kpitTable.getValueAt(row, 3).toString();
			String summary = kpitTable.getValueAt(row, col).toString();
			
			XmlUpdate.testCaseUpdateXml(xml, randomid, "summary", summary);
			
			for (int i = 0; i < KPIMain.spTable.getRowCount(); i++) {
				String pyrs =  KPIMain.spTable.getValueAt(i, 3).toString();
				if (pys.equals(pyrs)) {
					KPIMain.spTable.setValueAt(summary, i, 4);
				}
			}
			
		}
		//执行次数
		if (col==6) {
			
			String randomid = kpitTable.getValueAt(row, 3).toString();
			String iteration = kpitTable.getValueAt(row, col).toString();
			
			XmlUpdate.testCaseUpdateXml(xml, randomid, "iteration", iteration);
		
			
			KPIMain.spTableModel.content.removeAllElements();
			
			//KPIMain.constructTable();
		}
		//偏移量
		if (col==7) {
			String randomid = kpitTable.getValueAt(row, 3).toString();
			String adjust = kpitTable.getValueAt(row, col).toString();
			String pys = kpitTable.getValueAt(row, 4).toString();
			
			for (int i = 0; i < KPIMain.spTable.getRowCount(); i++) {
				String pyrs =  KPIMain.spTable.getValueAt(i, 3).toString();
				if (pys.equals(pyrs)) {
					KPIMain.spTable.setValueAt(adjust, i, 6);
				}
			}
			XmlUpdate.testCaseUpdateXml(xml, randomid, "adjust", adjust);
		
		}
		//标准值<Avg>
		if (col==8) {
			String randomid = kpitTable.getValueAt(row, 3).toString();
			String kpiAvg = kpitTable.getValueAt(row, col).toString();
			
			double kpiMax = Integer.parseInt(kpiAvg)*1.3;
			
			XmlUpdate.testCaseUpdateXml(xml, randomid, "kpiavg", kpiAvg);
			XmlUpdate.testCaseUpdateXml(xml, randomid, "kpimax", (int)kpiMax+"");
		
			KPIMain.kpiTbModel.setValueAt((int)kpiMax, row, 9);
			
		}
		//备注
		if (col==10) {
			String randomid = kpitTable.getValueAt(row, 3).toString();
			String remark = kpitTable.getValueAt(row, col).toString();
			XmlUpdate.testCaseUpdateXml(xml, randomid, "remark", remark);
		}
		
		kpitTable.repaint();
		KPIMain.constructTable();
	}

}
