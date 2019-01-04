package com.mr.replay.ui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import com.mr.replay.ui.merge.TextFile;

public class SummaryPanel extends JPanel implements ActionListener{
	private static JPanel dataPanel ;
	private static JTextField starPointTextField;
	private static JTextField endPointTextField;
	private static Dimension dimension3 =new Dimension(100, 20);
	static Dimension dimension =new Dimension(150, 20);
	public SummaryPanel(String videopath,String startpont,String endpoint){
		//this.add(select_mk_panel);
		int totalLine = 0;

		String analyzefolder = videopath.replaceAll("path=", "").replaceAll(".avi", "");
		//String starttxt = "E:/AutoKPI/video/20140321165912/zq006/001/start.txt";
		//String endtxt = "E:/AutoKPI/video/20140321165912/zq006/001/end.txt";
		String starttxt = analyzefolder+File.separator+"start.txt";
		String endtxt   = analyzefolder+File.separator+"end.txt";
		List<String> starthashs = null;
		List<String> endhashs = null;
		if (new File(starttxt).exists()) {
			starthashs = TextFile.readWithNoN(starttxt);
		}
		if (new File(endtxt).exists()) {
			endhashs = TextFile.readWithNoN(endtxt);
		}
		//mkrcaseDialog.setLayout(new GridLayout(3, 1));
		Object[][] dataVectorcm = null ;

		/*table.setToolTipText("<HTML><BODY>" +
				"<P> Event.DELETE to delect scripts selected" +"<BR/>" +"</P>" +
		"</BODY></HTML>");*/
		Object[] columnIdentifiers = {"第一张图片", "搜索开始点图片","比较结果","最后一张图片", "搜索结束点图片","比较结果"};

		dataPanel = new JPanel();

		dataPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JLabel stLabel = new JLabel("渲染开始点图片:");
		JLabel edLabel = new JLabel("渲染结束点图片:");

		JButton setButton = new JButton("设置");
		setButton.setPreferredSize(dimension3);
		setButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		starPointTextField = new JFormattedTextField(NumberFormat.getIntegerInstance());
		starPointTextField.setText(startpont);
		endPointTextField = new JFormattedTextField(NumberFormat.getIntegerInstance());
		endPointTextField.setText(endpoint);
		starPointTextField.setPreferredSize(dimension);
		endPointTextField.setPreferredSize(dimension);

		rowPanel.add(stLabel);
		rowPanel.add(starPointTextField);

		dataPanel.add(rowPanel);
		dataPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		rowPanel.add(edLabel);
		rowPanel.add(endPointTextField);
		rowPanel.add(setButton);

		dataPanel.add(rowPanel);

		dataPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

		DefaultTableCellRenderer tcr1 = new DefaultTableCellRenderer(){
			public void setValue(Object value) { 
				int start = Integer.parseInt(starPointTextField.getText());
				int index = Integer.parseInt(value.toString().split("_")[0]);
				setForeground((index  == start) ? Color.red : Color.black); 
				setText((value == null) ? "" : value.toString());   
			}   
		};
		DefaultTableCellRenderer tcr4 = new DefaultTableCellRenderer(){
			public void setValue(Object value) { 
				int end = Integer.parseInt(endPointTextField.getText());
				int index = Integer.parseInt(value.toString().split("_")[0]);
				setForeground((index == end) ? Color.red : Color.black); 
				setText((value == null) ? "" : value.toString());   
			}   
		};

		if (starthashs!=null) {
			totalLine = starthashs.size();
			dataVectorcm = new Object[totalLine][];
		}

		for (int i = 0; i < totalLine; i++) {
			String startline = starthashs.get(i);
			String endline = endhashs.get(i);

			String[] startstr = startline.split(":");
			String[] endstr = endline.split(":");

			Object[] rowbject = new Object[6];
			if (startstr.length==3) {
				rowbject[0]= startstr[0];
				rowbject[1]= startstr[1];
				rowbject[2]= startstr[2];
			}

			if (endstr.length==3) {
				rowbject[3]= endstr[0];
				rowbject[4]= endstr[1];
				rowbject[5]= endstr[2];
			}
			dataVectorcm[i]=rowbject;
		}

		Object[] pobject = new Object[2];
		pobject[0]="20140319092043/04/001/";
		pobject[1]="20140319092043/04/002/";

		DefaultTableModel monkeyModel = new DefaultTableModel(dataVectorcm, columnIdentifiers);
		JTable table = new JTable(monkeyModel){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row,int column){
				return false; 
			}
		};
		TableColumn startColumn = table.getColumn("搜索开始点图片");   
		TableColumn endColumn = table.getColumn("搜索结束点图片");   
		//tcr1.setHorizontalAlignment(SwingConstants.CENTER);
		//tcr4.setHorizontalAlignment(SwingConstants.CENTER);
		//table.getColumnModel().getColumn(1).setCellRenderer(tcr1); 
		//table.getColumnModel().getColumn(4).setCellRenderer(tcr4); 

		startColumn.setCellRenderer(tcr1);
		endColumn.setCellRenderer(tcr4);

		table.setRowSelectionAllowed(false);
		//monkeyModel.setDataVector(dataVectorcm, columnIdentifiers);

		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		//Dimension dsn=new Dimension(40,40);
		//table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );

		table.setBackground(Color.white);

		//table.setRowSelectionInterval(0, 0);

		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(2).setPreferredWidth(10);
		tcm.getColumn(5).setPreferredWidth(10);

		//UnitDialog.add(pane,BorderLayout.CENTER);
		JScrollPane tablepan = new JScrollPane(table);
		this.add(dataPanel);
		this.add(tablepan);

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}