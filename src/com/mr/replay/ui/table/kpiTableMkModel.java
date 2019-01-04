package com.mr.replay.ui.table;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

public class kpiTableMkModel extends AbstractTableModel {

	private static final long serialVersionUID = -7495940408592595397L;

	@SuppressWarnings("rawtypes")
	public static Vector content = null;

	//private String[] title_name = { "id", "target","class","case","selected"};
	private String[] title_name = {
			"id",
			"文件夹",
			"资源文件夹", 
			"脚本",
			"描述",
			"视频路径",
			"偏移量",
			"脚本结果",
			"转换结果",
			"开始渲染点",
			"结束渲染点",
			"结果(毫秒)",
			"选择",
			"Avg",
			"Max",
			"视频长度"
	};

	@SuppressWarnings("rawtypes")
	public kpiTableMkModel() {
		content = new Vector();
	}

	public kpiTableMkModel(int count) {
		content = new Vector(count);
	}

	public void addRow(
			String folder,
			String randomid,
			String script,
			String summary,
			String videoname,
			String adjust,
			String result,
			String parsert,
			String startpic,
			String endpic, 
			String analyzert,
			boolean selected,
			String kpiavg,
			String kpimax,
			String duration
	)
	{
		Vector v = new Vector(16);
		v.add(0, new Integer(content.size()));
		v.add(1, folder);
		v.add(2, randomid);
		v.add(3, script);
		v.add(4, summary);
		v.add(5, videoname);
		v.add(6, adjust);
		v.add(7, result);
		v.add(8, parsert);
		v.add(9, startpic);
		v.add(10, endpic);
		v.add(11, analyzert);
		v.add(12, selected);
		v.add(13, kpiavg);
		v.add(14, kpimax);
		v.add(15, duration);
		content.add(v);
		this.fireTableDataChanged();
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex==12) {
			return true;
		}else {
			return false;
		}
	}
	public void setValueAt(Object value, int row, int col) {
		System.out.println("value:"+value.toString());
		System.out.println("row:"+row);
		System.out.println("col:"+col);
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





