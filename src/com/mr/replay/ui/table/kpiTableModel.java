package com.mr.replay.ui.table;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.mr.replay.ui.frame.KPIMain;

public class kpiTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -7495940408592595397L;

	@SuppressWarnings("rawtypes")
	public static Vector content = null;

	//private String[] title_name = { "id", "target","class","case","selected"};
	private String[] title_name = {
			"id",
			"选择",
			"文件夹",
			"资源文件夹", 
			"脚本",
			"描述",
			"执行次数",
			"偏移量",
			//"视频文件",
			//"开始图片",
			//"结束图片",
			"标准值[Avg]",
			"标准值[Max]",
			"备注"
	};

	@SuppressWarnings("rawtypes")
	public kpiTableModel() {
		content = new Vector();
	}

	public kpiTableModel(int count) {
		content = new Vector(count);
	}

	public void addRow(
			boolean selected,
			String folder,
			String randomid,
			String script,
			String summary,
			String iteration,
			String adjust,
			//String video,
			//String startpic,
			//String endpic, 
			String kpiavg,
			String kpimax,
			String remark
	)
	{
		Vector v = new Vector(10);
		v.add(0, new Integer(content.size()));
		v.add(1, new Boolean(selected));
		v.add(2, folder);
		v.add(3, randomid);
		v.add(4, script);
		v.add(5, summary);
		v.add(6, iteration);
		v.add(7, adjust);
		//v.add(8, video);
		//v.add(9, startpic);
		//v.add(10, endpic);
		v.add(8, kpiavg);
		v.add(9, kpimax);
		v.add(10, remark);
		content.add(v);
		this.fireTableDataChanged();
	}

	/*    public void removeRow(int row) {
        content.remove(row);
    }

    public void Upmodify(int row) {
    	if(row==0){
    		Vector CurrentVenctor=(Vector)content.get(row);
        	Vector UPVenctor=(Vector)content.get(content.size()-1);
        	content.setElementAt((Object)UPVenctor, row);
        	content.setElementAt((Object)CurrentVenctor, content.size()-1);
    	}
    	else{
    		Vector CurrentVenctor=(Vector)content.get(row);
    		Vector UPVenctor=(Vector)content.get(row-1);
    		content.setElementAt((Object)UPVenctor, row);
        	content.setElementAt((Object)CurrentVenctor, row-1);
    	}

    }
    public void Downmodify(int row) {

    	if(content.size()==row+1){
    		Vector UPVenctor=(Vector)content.get(row);
    		Vector DownVenctor=(Vector)content.get(0);
    		content.setElementAt((Object)DownVenctor,row);
        	content.setElementAt((Object)UPVenctor, 0);
    	}
    	else{
    		Vector UPVenctor=(Vector)content.get(row);
    		Vector DownVenctor=(Vector)content.get(row+1);
    		content.setElementAt((Object)DownVenctor, row);
        	content.setElementAt((Object)UPVenctor, row+1);
    	}


    }*/

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		/*if (columnIndex==1||columnIndex==5||columnIndex==6||columnIndex==7) {
			return true;
		}else {
			return false;
		}*/
		if (columnIndex==1) {
			if (KPIMain.editSelect.isSelected()) {
				return true;
			}else {
				return false;
			}
		}
		else if (columnIndex==5) {
			if (KPIMain.editSummary.isSelected()) {
				return true;
			}else {
				return false;
			}
		}
		else if (columnIndex==6) {
			if (KPIMain.editInteration.isSelected()) {
				return true;
			}else {
				return false;
			}
		}
		else if (columnIndex==7) {
			if (KPIMain.editAdjust.isSelected()) {
				return true;
			}else {
				return false;
			}
		}
		else if (columnIndex==8) {
			if (KPIMain.editKpi.isSelected()) {
				return true;
			}else {
				return false;
			}
		}
		else if (columnIndex==10) {
			if (KPIMain.editStandard.isSelected()) {
				return true;
			}else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	public void setValueAt(Object value, int row, int col) {
		((Vector) content.get(row)).remove(col);
		((Vector) content.get(row)).add(col, value);
		this.fireTableCellUpdated(row, col);
	}

	public String getColumnName(int col) {
		return title_name[col];
	}

	public int getColumnCount() {
		return title_name.length;
	}

	public int getRowCount() {
		return content.size();
	}

	public Object getValueAt(int row, int col) {
		return ((Vector) content.get(row)).get(col);
	}

	public Class getColumnClass(int col) {
		return getValueAt(0, col).getClass();
	}
}





