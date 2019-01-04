package com.mr.replay.ui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import com.ckt.autokpi.video.helper.FontHelper;
import com.ckt.autokpi.video.ui.button.Button;
import com.ckt.autokpi.video.ui.label.Label;
import com.ckt.autokpi.video.ui.panel.VideoPanel;
import com.mr.replay.ui.bean.Constant;
import com.mr.replay.ui.bean.kpiBean;
import com.mr.replay.ui.dialog.AboutDialog;
import com.mr.replay.ui.dialog.HelpDialog;
import com.mr.replay.ui.dialog.ProgressBarDialog;
import com.mr.replay.ui.dialog.SettingDialog;
import com.mr.replay.ui.helper.ConfigHelper;
import com.mr.replay.ui.helper.JarHelper;
import com.mr.replay.ui.helper.ServiceHelper;
import com.mr.replay.ui.listener.EnviromentListener;
import com.mr.replay.ui.listener.ItemSelectListener;
import com.mr.replay.ui.listener.ItemSpSelectListener;
import com.mr.replay.ui.listener.NumberJTextField;
import com.mr.replay.ui.listener.ReportListener;
import com.mr.replay.ui.listener.kpiListener;
import com.mr.replay.ui.listener.kpiMkTableMouseListener;
import com.mr.replay.ui.listener.kpiTableKeyListener;
import com.mr.replay.ui.listener.kpiTableModeListener;
import com.mr.replay.ui.listener.kpiTableMouseListener;
import com.mr.replay.ui.listener.scriptListener;
import com.mr.replay.ui.lock.Lock;
import com.mr.replay.ui.log.Log;
import com.mr.replay.ui.merge.TextFile;
import com.mr.replay.ui.panel.PyReplayPanel;
import com.mr.replay.ui.table.kpiTableMkModel;
import com.mr.replay.ui.table.kpiTableModel;
import com.mr.replay.ui.xml.XmlReader;
public class KPIMain implements ActionListener {
	private static final long serialVersionUID = 1L;
	// 设置窗口宽度
	private int w = 1200;
	// 设置高度
	private int h = 600;
	public static PyReplayPanel prp;
	private JMenuBar menuBar;
	private JPanel toolBarPanel;
	private JPanel toolkpiPanel;
	public static JTextField starTextField;
	public static JTextField endTextField;
	public static JRadioButton selectAll;
	public static JRadioButton selectAllSP;
	public static  JRadioButton editSelect;
	public static  JRadioButton editSummary;
	public static  JRadioButton editInteration;
	public static  JRadioButton editAdjust;
	public static  JRadioButton editKpi;
	public static  JRadioButton editStandard;

	public static JButton scrpitsAdd;
	public static JButton scrpitsDel;
	public static JButton dataDel;
	public static JButton report;
	//public static JButton replay_select;
	public static JButton recoder;
	public static JButton replay;
	public static JButton replayAll;
	public static JButton replayMksingle;
	public static JButton parseAndAnalyze;
	public static JButton videoParsetoImage;
	public static JButton AnalyzeImage;
	public static JButton AnalyzeData;
	public static JButton DeleteAnalyzeData;
	public static Button record_start;
	public static Button record_stop;
	public static Button reconnect;

	private JTabbedPane jTabbedPane;
	private JPanel repjPanel;
	private JPanel recMkpanel;
	private JPanel cameraPanel;
	private JPanel kpijPanel;
	private JScrollPane kpijJScrollPane;
	public static JTable kpiTable; 
	public static kpiTableModel kpiTbModel;

	//sp table
	public static JTable spTable;
	public static kpiTableMkModel spTableModel;
	private static JScrollPane sptableScrollPane;
	//video
	public static DefaultListModel<String> videosListModel;
	public static JList<String> list;
	private VideoPanel videoPanel = null;

	private JPanel video_toolBarPanel;
	static Dimension dimension =new Dimension(150, 20);
	static Dimension dimension3 =new Dimension(100, 20);
	Dimension dimension2 =new Dimension(250, 20);
	//Dimension editdmension =new Dimension(75, 20);

	public static JProgressBar bar;
	public static JLabel runLabel;

	//log jdialog
	private static JDialog AnalyzeDialog ;
	private static JPanel dataPanel ;
	private static NumberJTextField starPointTextField;
	private static NumberJTextField endPointTextField;

	public static JFrame frame;
	public KPIMain() {
		//super();
		frame = new JFrame();
		frame.setTitle("AutoKPI Replay");
		// 激活窗口事件
		//enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		w=(int)toolkit.getScreenSize().getWidth()-100;
		h=(int)toolkit.getScreenSize().getHeight()-100;
		frame.setBounds(100, 100, w, h);
		int x = (int) (toolkit.getScreenSize().getWidth() - w) / 2;
		int y = (int) (toolkit.getScreenSize().getHeight() - h) / 2;
		frame.setLocation(x, y);// 设置窗口居中
		frame.setJMenuBar(getJMenuBar());// 设置菜单项
		frame.getContentPane().add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.PAGE_START);
		frame.getContentPane().add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.PAGE_START);
		frame.getContentPane().add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.PAGE_START);
		frame.getContentPane().add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.PAGE_START);
		frame.getContentPane().add(geTabbedPane(), BorderLayout.CENTER);
		frame.setVisible(true);
		this.settingInit();

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				processWindowEvent(e);
			}
		});

	}
	public static void updatesptable(){
		int count = spTable.getRowCount();
		if (count>=1) {
			for (int i = 0; i < count; i++) {
				String video = KPIMain.spTable.getValueAt(i, 5).toString().replaceAll("path=", "");
				if (new File(video).exists()) {
					KPIMain.spTable.setValueAt("执行成功", i, 7);
				}else {
					KPIMain.spTable.setValueAt("未执行", i, 7);
				}
				TextFile.refreshDuration(i);
			}
		}
	}
	public JPanel getCameraPanel(){
		cameraPanel = new JPanel();
		cameraPanel.setPreferredSize(new Dimension(w-10,h-100));
		cameraPanel.setLayout(new BorderLayout());
		cameraPanel.add(getVideoPanel(),BorderLayout.CENTER);
		cameraPanel.add(getVideoToolBar(),BorderLayout.PAGE_START);
		return cameraPanel;
		//this.getContentPane().add(getVideoPanel(), BorderLayout.CENTER);
		//this.getContentPane().add(getVideoToolBar(), BorderLayout.PAGE_START);
	}
	public JPanel getKpiSetPanel(){
		kpijPanel = new JPanel();
		kpijPanel.setPreferredSize(new Dimension(w-10,h-100));
		kpijPanel.setLayout(new BorderLayout());
		kpijPanel.add(getkpiToolPanel(),BorderLayout.PAGE_START);
		kpijPanel.add(getkpiTablePanel(),BorderLayout.CENTER);
		return kpijPanel;

	}
	public JPanel getRecMkPane(){
		recMkpanel = new JPanel();
		recMkpanel.setLayout(new BoxLayout(recMkpanel, BoxLayout.PAGE_AXIS));
		JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		recoder = new JButton("录制脚本");
		recoder.setActionCommand("recoder");
		recoder.setPreferredSize(dimension);
		recoder.setFont(new Font("新宋体", Font.PLAIN, 12));
		recoder.addActionListener(this);

		rowPanel.add(recoder);
		recMkpanel.add(rowPanel);
		recMkpanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		return recMkpanel;
	}
	public JPanel getRecordPane(){
		repjPanel = new JPanel();
		repjPanel.setPreferredSize(new Dimension(w-10,h-100));
		repjPanel.setLayout(new BorderLayout());
		repjPanel.add(getToolBar(),BorderLayout.PAGE_START);
		repjPanel.add(getPyReplayPanel(),BorderLayout.CENTER);
		repjPanel.add(getsptTablePanel(),BorderLayout.PAGE_END);
		return repjPanel;
	}
	public JScrollPane getkpiTablePanel(){
		kpijJScrollPane = new JScrollPane(getkpitable());
		kpijJScrollPane.setPreferredSize(new Dimension(w-10,h-100));
		return kpijJScrollPane;
	}
	public JScrollPane getsptTablePanel(){
		sptableScrollPane = new JScrollPane(getsptTable());
		return sptableScrollPane;
	}
	public JTabbedPane geTabbedPane(){
		jTabbedPane = new JTabbedPane();
		JPanel jPanel = getRecordPane();
		jTabbedPane.add("录制视频",getCameraPanel());
		jTabbedPane.add("录制脚本",getRecMkPane());
		jTabbedPane.add("执行配置",getKpiSetPanel());
		jTabbedPane.add("执行脚本",jPanel);
		//jTabbedPane.add("解析视频",new JLabel());
		return jTabbedPane;
	}
	private PyReplayPanel getPyReplayPanel() {
		if (prp == null) {
			prp = new PyReplayPanel(frame);
		}
		return prp;
	}
	private JPanel getToolBar() {
		if (toolBarPanel == null) {
			toolBarPanel = new JPanel();
			toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.PAGE_AXIS));
			toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

			JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			/*recoder = new JButton("录制脚本");
			recoder.setActionCommand("recoder");
			recoder.setPreferredSize(dimension);
			recoder.setFont(new Font("新宋体", Font.PLAIN, 12));
			recoder.addActionListener(this);

			rowPanel.add(recoder);
			toolBarPanel.add(rowPanel);*/

			toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
			rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			replay = new JButton("回放特定脚本");
			replay.setPreferredSize(dimension);
			replay.setActionCommand("replay");
			replay.setFont(new Font("新宋体", Font.PLAIN, 12));
			replay.addActionListener(this);


			replayAll = new JButton("1:回放选中脚本");
			replayAll.setPreferredSize(dimension);
			replayAll.setActionCommand("replayAll");
			replayAll.addActionListener(new scriptListener());
			replayAll.setFont(new Font("新宋体", Font.PLAIN, 12));

			parseAndAnalyze = new JButton("2,3:视频转换和图片分析");
			parseAndAnalyze.setPreferredSize(dimension2);
			parseAndAnalyze.setActionCommand("parseAndAnalyze");
			parseAndAnalyze.addActionListener(new scriptListener());
			parseAndAnalyze.setFont(new Font("新宋体", Font.PLAIN, 12));

			rowPanel.add(replay);
			rowPanel.add(replayAll);
			//rowPanel.add(parseAndAnalyze);
			toolBarPanel.add(rowPanel);

			toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
			rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			JLabel rowLabel = new JLabel("全选:");
			selectAllSP = new JRadioButton("选择全部");
			selectAllSP.setActionCommand("spselectAll");
			selectAllSP.addItemListener(new ItemSpSelectListener());
			selectAllSP.setPreferredSize(dimension);
			rowPanel.add(rowLabel);
			rowPanel.add(selectAllSP);
			toolBarPanel.add(rowPanel);
			toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
			toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

			toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
			rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			videoParsetoImage =new JButton("2:视频转换为图片");
			videoParsetoImage.setPreferredSize(dimension);
			videoParsetoImage.setActionCommand("videoParsetoImage");
			videoParsetoImage.addActionListener(new scriptListener());
			videoParsetoImage.setFont(new Font("新宋体", Font.PLAIN, 12));

			rowPanel.add(videoParsetoImage);

			toolBarPanel.add(rowPanel);

			toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
			rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			AnalyzeImage = new JButton("3:分析图片");
			AnalyzeImage.setPreferredSize(dimension);
			AnalyzeImage.setActionCommand("AnalyzeImage");
			AnalyzeImage.addActionListener(new scriptListener());
			AnalyzeImage.setFont(new Font("新宋体", Font.PLAIN, 12));

			AnalyzeData = new JButton("查看分析数据");
			AnalyzeData.setPreferredSize(dimension);
			AnalyzeData.setActionCommand("AnalyzeData");
			AnalyzeData.addActionListener(new scriptListener());
			AnalyzeData.setFont(new Font("新宋体", Font.PLAIN, 12));

			DeleteAnalyzeData = new JButton("重置数据");
			DeleteAnalyzeData.setPreferredSize(dimension);
			DeleteAnalyzeData.setActionCommand("DeleteAnalyzeData");
			DeleteAnalyzeData.addActionListener(new scriptListener());
			DeleteAnalyzeData.setFont(new Font("新宋体", Font.PLAIN, 12));
			
			rowPanel.add(AnalyzeImage);
			rowPanel.add(AnalyzeData);
			rowPanel.add(DeleteAnalyzeData);
			toolBarPanel.add(rowPanel);
			toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

			rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			report = new JButton("4:生成测试报告");
			report.setActionCommand("report");
			report.addActionListener(new ReportListener());
			report.setPreferredSize(dimension);
			rowPanel.add(report);
			toolBarPanel.add(rowPanel);
			toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

			//toolBar.setBackground(Color.lightGray);
		}
		return toolBarPanel;
	}
	public JPanel getVideoPanel() {

		if (videoPanel == null) {
			videoPanel = new VideoPanel(this);
		}
		return videoPanel;
	}
	private JPanel getVideoToolBar() {
		if (video_toolBarPanel == null) {
			video_toolBarPanel = new JPanel();
			video_toolBarPanel.setLayout(new BoxLayout(video_toolBarPanel, BoxLayout.PAGE_AXIS));
			video_toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
			video_toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

			record_start = new Button("开始录制");
			record_start.setPreferredSize(dimension);
			record_start.setActionCommand("record_start");
			record_start.addActionListener(this);

			record_stop = new Button("停止录制");
			record_stop.setPreferredSize(dimension);
			record_stop.setActionCommand("record_stop");
			record_stop.addActionListener(this);

			reconnect = new Button("重新连接");
			reconnect.setPreferredSize(dimension);
			reconnect.setActionCommand("reconnect");
			reconnect.addActionListener(this);


			JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			rowPanel.add(record_start);
			rowPanel.add(record_stop);
			rowPanel.add(reconnect);
			video_toolBarPanel.add(rowPanel);
			video_toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
			video_toolBarPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

			//video_toolBar.setBackground(Color.lightGray);
		}

		return video_toolBarPanel;
	}
	private JPanel getkpiToolPanel() {
		if (toolkpiPanel == null) {
			toolkpiPanel = new JPanel();
			toolkpiPanel.setLayout(new BoxLayout(toolkpiPanel, BoxLayout.PAGE_AXIS));
			toolkpiPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

			JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));


			JLabel rowLabel = new JLabel("列高:");
			final JComboBox<Integer> rowhBox = new JComboBox<Integer>();
			rowhBox.setPreferredSize(dimension);
			for (int i = 0; i < 20; i++) {
				rowhBox.addItem(20+i*5);
			}
			rowhBox.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub
					int rowHeight = Integer.parseInt(rowhBox.getSelectedItem().toString());
					System.out.println(rowHeight);
					kpiTable.setRowHeight(rowHeight);
					kpiTable.repaint();
				}
			});

			rowPanel.add(rowLabel);
			rowPanel.add(rowhBox);
			toolkpiPanel.add(rowPanel);
			toolkpiPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

			/*rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			replay_select = new JButton("执行选择");
			replay_select.setActionCommand("replay_select");
			replay_select.setPreferredSize(dimension);
			rowPanel.add(replay_select);
			toolkpiPanel.add(rowPanel);
			toolkpiPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
			 */

			rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			scrpitsAdd = new JButton("添加脚本");
			scrpitsAdd.setActionCommand("scrpitsAdd");
			scrpitsAdd.addActionListener(new kpiListener());
			scrpitsAdd.setPreferredSize(dimension);

			scrpitsDel = new JButton("删除脚本");
			scrpitsDel.setActionCommand("scrpitsDel");
			scrpitsDel.addActionListener(new kpiListener());
			scrpitsDel.setPreferredSize(dimension);


			dataDel = new JButton("删除数据");
			dataDel.setActionCommand("dataDel");
			dataDel.addActionListener(new kpiListener());
			dataDel.setPreferredSize(dimension);

			rowPanel.add(scrpitsAdd);
			rowPanel.add(scrpitsDel);
			rowPanel.add(dataDel);
			toolkpiPanel.add(rowPanel);
			toolkpiPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

			/*rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			analyzeselect = new JButton("解析选中");
			analyzeselect.setActionCommand("analyzeselect");
			analyzeselect.setPreferredSize(dimension);
			rowPanel.add(analyzeselect);
			toolkpiPanel.add(rowPanel);
			toolkpiPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
			 */
			toolkpiPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
			rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			replayMksingle = new JButton("回放所有脚本(合并脚本)");
			replayMksingle.setPreferredSize(dimension2);
			replayMksingle.setActionCommand("replayMksingle");
			replayMksingle.addActionListener(new scriptListener());
			replayMksingle.setFont(new Font("新宋体", Font.PLAIN, 12));
			rowPanel.add(replayMksingle);
			toolkpiPanel.add(rowPanel);
			toolkpiPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);


			rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			JLabel runbarLabel = new JLabel("");
			bar = new JProgressBar(JProgressBar.HORIZONTAL );  
			bar.setPreferredSize(dimension);
			bar.setToolTipText("执行中.....");
			bar.setPreferredSize(new Dimension(400, 20));
			final SimulatedTarget target = new SimulatedTarget(100);
			bar.setMinimum(0);   
			bar.setMaximum(target.getAmount()); 

			runLabel = new JLabel("");
			//rowPanel.add(runbarLabel);
			rowPanel.add(bar);
			rowPanel.add(runLabel);
			toolkpiPanel.add(rowPanel);

			toolkpiPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
			rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			rowLabel = new JLabel("全选:");
			selectAll = new JRadioButton("选择全部");
			selectAll.setActionCommand("selectAll");
			selectAll.addItemListener(new ItemSelectListener());
			selectAll.setPreferredSize(dimension);
			rowPanel.add(rowLabel);
			rowPanel.add(selectAll);
			toolkpiPanel.add(rowPanel);
			toolkpiPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
			toolkpiPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);


			toolkpiPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
			rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			rowLabel = new JLabel("Video:");
			list = new JList<String>();  
			JScrollPane listScrollPane = new JScrollPane();
			videosListModel = new DefaultListModel<String>(); 
			videosListModel.addElement("无数据...");  

			list.setModel(videosListModel);  
			listScrollPane.setViewportView(list);
			//list.addListSelectionListener(new videoListListener());
			//rowPanel.add(rowLabel);
			rowPanel.add(listScrollPane);
			list.setPreferredSize(new Dimension(150, 300));

			toolkpiPanel.add(rowPanel);
			toolkpiPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

			toolkpiPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
			rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			rowLabel = new JLabel("开始图片:");
			starTextField = new JTextField();
			starTextField.setPreferredSize(dimension);
			//rowPanel.add(rowLabel);
			//rowPanel.add(starTextField);
			rowLabel = new JLabel("结束图片:");
			endTextField = new JTextField();
			endTextField.setPreferredSize(dimension);
			//rowPanel.add(rowLabel);
			//rowPanel.add(endTextField);
			//toolkpiPanel.add(rowPanel);


			toolkpiPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
			toolkpiPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
			rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			rowLabel = new JLabel("编辑:");

			editSelect= new JRadioButton("选择");
			editSelect.setActionCommand("editSelect");

			editSummary = new JRadioButton("描述");
			editSummary.setActionCommand("editSummary");

			editInteration = new JRadioButton("执行次数");
			editInteration.setActionCommand("editInteration");

			editAdjust = new JRadioButton("偏移量");
			editAdjust.setActionCommand("editAdjust");

			editKpi = new JRadioButton("标准值");
			editKpi.setActionCommand("editKpi");

			editStandard = new JRadioButton("备注");
			editStandard.setActionCommand("editStandard");

			rowPanel.add(rowLabel);
			rowPanel.add(editSelect);
			rowPanel.add(editSummary);
			rowPanel.add(editInteration);
			rowPanel.add(editAdjust);
			rowPanel.add(editKpi);
			rowPanel.add(editStandard);
			toolkpiPanel.add(rowPanel);

			toolkpiPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
			toolkpiPanel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

			//toolkpiPanel.setBackground(Color.lightGray);
		}
		return toolkpiPanel;
	}
	public static void constructTable(){
		Vector<kpiBean> mkVector = null;
		XmlReader my = new XmlReader();
		String jarpath = JarHelper.getProjectPath();
		String xml = jarpath+File.separator+"config"+File.separator+"testxml.xml";
		mkVector = my.toRead(xml);

		spTableModel.content.removeAllElements();
		for (kpiBean kBean : mkVector) {
			//Update script table Data
			boolean isselected = Boolean.parseBoolean(kBean.getSelected());
			if (isselected) {
				String fp = String.format("%s%s%s%s", 
						jarpath,
						"video\\",
						kBean.getFolder()+"\\",
						kBean.getRandomid()+"\\");

				//System.out.println(fp);
				File srcdir = new File(fp);
				if (!srcdir.exists()) {
					srcdir.mkdirs();
				}

				int interation = Integer.parseInt(kBean.getIteration());
				for (int i = 1; i <=interation; i++) {
					String aviname =String.format("%03d",i)+".avi";
					//System.out.println("video:"+aviname);

					String videoAbsPath =  String.format("%s%s%s%s%s",
							jarpath,
							"video\\",
							kBean.getFolder()+"\\",
							kBean.getRandomid()+"\\",
							aviname);

					String resulttxt = videoAbsPath.replaceAll(".avi", "")+"\\result.txt";
					String vpath = String.format("path=%s",
							videoAbsPath);
					//path=E:\\AutoKPI\\video\\test.avi
					String result = "";
					//System.out.println(videoAbsPath);
					String videotxt = videoAbsPath.replaceAll(".avi", ".txt");
					String duration = "0";
					
					if (new File(videoAbsPath).exists()) {
						result="SUCCESS";
						duration = TextFile.getDuration(videotxt);
					}else {
						result="NOTRUN";
					}
					List<String> allList;
					String[] data = null;
					File rFile = new File(resulttxt);
					if (rFile.exists()) {
						allList=TextFile.read(resulttxt);
						if (allList.size()==1) {
							//System.out.println(allList.get(0));
							data = allList.get(0).split(":");
						}
					}
					if (data!=null&&data.length==4) {
						spTableModel.addRow(
								kBean.getFolder(),
								kBean.getRandomid(),
								kBean.getScript(),
								kBean.getSummary(),
								vpath,
								kBean.getAdjust(),
								result,
								data[0],
								data[1],
								data[2],
								data[3],
								true,
								kBean.getKpiavg(),
								kBean.getKpimax(),
								duration
								);
					}
					else {
						spTableModel.addRow(
								kBean.getFolder(),
								kBean.getRandomid(),
								kBean.getScript(),
								kBean.getSummary(),
								vpath,
								kBean.getAdjust(),
								result,
								"NA",
								"NA",
								"NA",
								"NA",
								true,
								kBean.getKpiavg(),
								kBean.getKpimax(),
								duration);
					}	
				}
			}
		}
	}
	public static void AddKpiTable(Vector<kpiBean> addVector){
		for (kpiBean kBean : addVector) {
			//ImageIcon startpicpath = new ImageIcon(kBean.getStartpicpath());
			//startpicpath.getImage().getScaledInstance(100, 80, Image.SCALE_DEFAULT);

			kBean.toString();

			//ImageIcon endpicpath = new ImageIcon(kBean.getEndpicpath());

			kpiTbModel.addRow(
					Boolean.parseBoolean(kBean.getSelected()),
					kBean.getFolder(),
					kBean.getRandomid(),
					kBean.getScript(),
					kBean.getSummary(),
					kBean.getIteration(),
					kBean.getAdjust(), 
					//kBean.getVideo(), 
					//startpicpath,
					//endpicpath,
					//kBean.getStartpicpath(),
					//kBean.getEndpicpath(),
					kBean.getKpiavg(),
					kBean.getKpimax(),
					kBean.getRemark());

			kpiTable.repaint();

			constructTable();
			//kBean.toString();
		}
	}
	private static void initNullXmlFile(String filename){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
		stringBuffer.append("<testsuite name=\"uiautomator\" version=\"NA\">\n");
		stringBuffer.append("</testsuite>");
		ServiceHelper.writeToFile(filename, stringBuffer.toString(), false);

	}
	public static void initkpiTable(){
		String jarpath = JarHelper.getProjectPath();
		String xml = jarpath+File.separator+"config"+File.separator+"testxml.xml";
		
		Vector<kpiBean> A = null;
		XmlReader my = new XmlReader();
		File  file = new File(xml);
		if (!file.exists()) {
			System.out.println(file.getParentFile().getAbsolutePath());
			file.getParentFile().mkdirs();
			initNullXmlFile(xml);
		}
		A = my.toRead(xml);
		for (kpiBean kBean : A) {

			kpiTbModel.addRow(
					Boolean.parseBoolean(kBean.getSelected()),
					kBean.getFolder(),
					kBean.getRandomid(),
					kBean.getScript(),
					kBean.getSummary(),
					kBean.getIteration(),
					kBean.getAdjust(), 
					//kBean.getVideo(), 
					//startpicpath,
					//endpicpath,
					//kBean.getStartpicpath(),
					//kBean.getEndpicpath(),
					kBean.getKpiavg(),
					kBean.getKpimax(),
					kBean.getRemark()); 
		}
		constructTable();
	}
	public static JTable getsptTable(){
		spTableModel = new kpiTableMkModel(20);
		spTable = new JTable(spTableModel){
			private static final long serialVersionUID = 1L;
		};
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		spTable.getTableHeader().setReorderingAllowed(false);
		
		spTable.setToolTipText("<HTML><BODY bgcolor=\"#00FFCC\">" +
				"<P> 1:点击SPACE键查看分析数据,修改测试数据" +"<BR/>" +"</P>" +
				"<P> 2:选中某行,双击查看资源文件" +"<BR/>" +"</P>" +
		"</BODY></HTML>");
		
		TableColumnModel tcm = spTable.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(2);
		tcm.getColumn(1).setPreferredWidth(20);
		tcm.getColumn(2).setPreferredWidth(20);
		tcm.getColumn(3).setPreferredWidth(40);
		tcm.getColumn(4).setPreferredWidth(40);
		tcm.getColumn(5).setPreferredWidth(40);
		tcm.getColumn(6).setPreferredWidth(15);
		tcm.getColumn(7).setPreferredWidth(15);
		tcm.getColumn(8).setPreferredWidth(15);
		tcm.getColumn(9).setPreferredWidth(20);
		tcm.getColumn(10).setPreferredWidth(20);
		tcm.getColumn(11).setPreferredWidth(20);
		tcm.getColumn(12).setPreferredWidth(5);
		tcm.getColumn(13).setPreferredWidth(15);
		tcm.getColumn(14).setPreferredWidth(15);
		tcm.getColumn(15).setPreferredWidth(5);

		DefaultTableCellRenderer tcr7 = new DefaultTableCellRenderer(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				if (String.valueOf(value).contains("失败")||String.valueOf(value).contains("FAIL")){
					setForeground(Color.RED);						
				}else if (String.valueOf(value).contains("NOTRUN")) {
					setForeground(Color.BLUE);
				}else{
					setForeground(Color.BLACK);
				}
				return super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, row, column);
			}
		};
		DefaultTableCellRenderer tcr8 = new DefaultTableCellRenderer(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				if (String.valueOf(value).contains("失败")||String.valueOf(value).contains("FAIL")){
					setForeground(Color.red);						
				}else{
					setForeground(Color.BLACK);
				}
				return super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, row, column);
			}
		};
		DefaultTableCellRenderer tcr11 = new DefaultTableCellRenderer(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				if (String.valueOf(value).contains("NA")){
					setForeground(Color.RED);						
				}else{
					setForeground(Color.BLACK);
				}
				return super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, row, column);
			}
		};
		spTable.addMouseListener(new kpiMkTableMouseListener());
		spTable.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode()==KeyEvent.VK_SPACE){
					int row = spTable.getSelectedRow();
					Log.info("Open row index:"+row);
					if (row==-1) {
						JOptionPane.showMessageDialog(new JFrame(), "请选中一列数据!","警告", JOptionPane.WARNING_MESSAGE);
					}else {
						String startpoint = KPIMain.spTable.getValueAt(row, 9).toString();
						String endpoint = KPIMain.spTable.getValueAt(row, 10).toString();
						String video =  KPIMain.spTable.getValueAt(row, 5).toString();
						if (Lock.analyze) {
							if (PyReplayPanel.tf_video_name.getText().equals(video)) {
								JOptionPane.showMessageDialog(new JFrame(), "正在分析中,请等待!","警告", JOptionPane.WARNING_MESSAGE);
							}else {
								/*AnalyzeDialog sd = new AnalyzeDialog(this.KPIMain, video, startpoint, endpoint);
								sd.setVisible(true);
								 */
								AnalyzeDialog(row,video,frame,startpoint,endpoint);	
							}
						}else {
							AnalyzeDialog(row,video,frame,startpoint,endpoint);
						}
					}
					//spTable.setRowSelectionInterval(row,row);
				}
			}
		});

		tcr7.setHorizontalAlignment(SwingConstants.CENTER);
		tcr8.setHorizontalAlignment(SwingConstants.CENTER);
		spTable.getColumnModel().getColumn(7).setCellRenderer(tcr7); 
		spTable.getColumnModel().getColumn(8).setCellRenderer(tcr8); 
		spTable.getColumnModel().getColumn(11).setCellRenderer(tcr11); 
		spTable.setRowHeight(20);  	
		//spTable.getTableHeader().setReorderingAllowed(false);

		JTableHeader tableHeader = spTable.getTableHeader();
		tableHeader.setReorderingAllowed(false);   //设置表格列不可重排
		DefaultTableCellRenderer hr =(DefaultTableCellRenderer)tableHeader.getDefaultRenderer();  //获得表格头的单元格对象
		hr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);  //列名居中

		spTable.getTableHeader().setResizingAllowed(true);
		spTable.setRowSelectionAllowed(true);
		spTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );

		updatesptable();
		return spTable;
	}

	public JTable getkpitable(){

		kpiTbModel = new kpiTableModel(20);
		kpiTable = new JTable(kpiTbModel){
			private static final long serialVersionUID = 1L;
		};

		kpiTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				return super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, row, column);
			}

		};
		
		kpiTable.setToolTipText("<HTML><BODY bgcolor=\"#00FFCC\">" +
				"<P> 1:点击DELETE键将删除选中行的视频文件,图片,分析数据" +"<BR/>" +"</P>" +
				//"<P> 包括" +"<BR/>" +"</P>" +
				"<P> 2:选中某行,双击查看资源文件" +"<BR/>" +"</P>" +
		"</BODY></HTML>");
		
		kpiTable.getTableHeader().setReorderingAllowed(false);
		kpiTable.addKeyListener(new kpiTableKeyListener(kpiTable));
		kpiTable.addMouseListener(new kpiTableMouseListener(kpiTable,list,videosListModel));

		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		//kpiTable.getColumnModel().getColumn(9).setCellRenderer(tcr);

		/*URL openFolderimgURL = KPIMain.class.getResource("/resource/11111.jpg");
		ImageIcon openFolder = new ImageIcon(openFolderimgURL);
		 */

		initkpiTable();
		kpiTable.getModel().addTableModelListener(new kpiTableModeListener(kpiTable));

		/*for (int i = 0; i < 2; i++) {

			kpiTbModel.addRow(
					true,
					"20132015125",
					"scripts\\TETE.py",
					"通话",
					"1",
					"video\\001.avi",
					"5",
					openFolder,
					openFolder, 
					"NA",
					"150"
					);
			kpiTable.repaint();
		}*/
		TableColumnModel tcm = kpiTable.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(2);
		tcm.getColumn(1).setPreferredWidth(5);
		tcm.getColumn(2).setPreferredWidth(80);
		tcm.getColumn(3).setPreferredWidth(80);
		tcm.getColumn(4).setPreferredWidth(160);
		tcm.getColumn(5).setPreferredWidth(30);
		tcm.getColumn(6).setPreferredWidth(5);
		tcm.getColumn(7).setPreferredWidth(5);
		tcm.getColumn(8).setPreferredWidth(5);
		tcm.getColumn(9).setPreferredWidth(5);
		tcm.getColumn(10).setPreferredWidth(50);
		//tcm.getColumn(10).setPreferredWidth(50);
		//tcm.getColumn(11).setPreferredWidth(20);
		//tcm.getColumn(12).setPreferredWidth(10);


		DefaultTableColumnModel dcm5 = (DefaultTableColumnModel)kpiTable.getColumnModel();
		TableColumn column5 = dcm5.getColumn(5);
		JTextField jTextField5 = new JTextField();
		jTextField5.setEditable(true);
		DefaultCellEditor editor5 = new DefaultCellEditor(jTextField5);
		column5.setCellEditor(editor5);


		DefaultTableColumnModel dcm8 = (DefaultTableColumnModel)kpiTable.getColumnModel();
		TableColumn column8 = dcm8.getColumn(8);
		NumberJTextField jTextField8 = new NumberJTextField();
		jTextField8.setNumberOnly(true);
		jTextField8.setEditable(true);
		DefaultCellEditor editor8 = new DefaultCellEditor(jTextField8);
		column8.setCellEditor(editor8);

		DefaultTableColumnModel dcm9 = (DefaultTableColumnModel)kpiTable.getColumnModel();
		TableColumn column9 = dcm9.getColumn(9);
		NumberJTextField jTextField9 = new NumberJTextField();
		jTextField9.setNumberOnly(true);
		jTextField9.setEditable(true);
		DefaultCellEditor editor9 = new DefaultCellEditor(jTextField9);
		column9.setCellEditor(editor9);

		DefaultTableColumnModel dcm10 = (DefaultTableColumnModel)kpiTable.getColumnModel();
		TableColumn column10 = dcm10.getColumn(10);
		JTextField jTextField10 = new JTextField();
		DefaultCellEditor editor10 = new DefaultCellEditor(jTextField10);
		column10.setCellEditor(editor10);
		
		
		int iteration_colum = 6;
		int adjust_colum = 7;

		final JComboBox<Integer> interationBox = new JComboBox<Integer>();
		
		for (int j = 1; j <=200; j++) {
			interationBox.addItem(j);
		}
		/*if (!ComponentOrientation.getOrientation(Locale.getDefault()).isLeftToRight()) { 
			((JLabel)interationBox.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT); 
		}*/
		

		kpiTable.getColumnModel().getColumn(iteration_colum).setCellEditor(new DefaultCellEditor(interationBox));

		JComboBox<Integer> adjustBox = new JComboBox<Integer>();
		
		for (int t = 0; t <=200; t++) {
			adjustBox.addItem(t);
		}
		/*if (!ComponentOrientation.getOrientation(Locale.getDefault()).isLeftToRight()) { 
			((JLabel)adjustBox.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT); 
		}*/
		
		kpiTable.getColumnModel().getColumn(adjust_colum).setCellEditor(new DefaultCellEditor(adjustBox));


		kpiTable.setRowHeight(20);  	
		//kpiTable.getTableHeader().setReorderingAllowed(false);
		kpiTable.getTableHeader().setResizingAllowed(true);

		JTableHeader tableHeader = kpiTable.getTableHeader();
		tableHeader.setReorderingAllowed(false);   //设置表格列不可重排
		DefaultTableCellRenderer hr =(DefaultTableCellRenderer)tableHeader.getDefaultRenderer();  //获得表格头的单元格对象
		hr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);  //列名居中

		/*//所有列居中显示
		DefaultTableCellRenderer defaultTableCellRenderer=new DefaultTableCellRenderer();   
		defaultTableCellRenderer.setHorizontalAlignment(JLabel.CENTER);   
		kpiTable.setDefaultRenderer(Object.class,   defaultTableCellRenderer);
		 */
		kpiTable.setRowSelectionAllowed(true);
		kpiTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
		//kpiTable.setBackground(Color.white);

		return kpiTable;
	}
	public void exit(){

		videoPanel.close();

		prp.closeTimer();//关闭设备刷新线程
		ProgressBarDialog pbd = new ProgressBarDialog(frame);
		pbd.setVisible(true);
		pbd.close();//关闭进度条
		System.exit(0);//退出系统
	}

	//重写窗口事件
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {

			if(Lock.replay){
				/*int retcode = JOptionPane.showConfirmDialog(new JFrame(), "正在回放脚本，确定强制退出？\n退出将会丢失现有的分析数据","询问", JOptionPane.YES_NO_OPTION);
				if (retcode==0) {
					exit();
					ServiceHelper.KillMoneyPids(false);
				}*/
				JOptionPane.showMessageDialog(new JFrame(), "正在回放脚本，禁止退出！", "警告",JOptionPane.WARNING_MESSAGE);
				return;
			}else if(Lock.parse){
				/*int retcode = JOptionPane.showConfirmDialog(new JFrame(), "正在转换图片，确定强制退出？\n退出将会丢失现有的分析数据","询问", JOptionPane.YES_NO_OPTION);
				if (retcode==0) {
					exit();
				}*/
				JOptionPane.showMessageDialog(new JFrame(), "正在转换图片，禁止退出！", "警告",JOptionPane.WARNING_MESSAGE);
				return;
			}
			else if(Lock.analyze){
				/*int retcode = JOptionPane.showConfirmDialog(new JFrame(), "正在分析图片，确定强制退出？\n退出将会丢失现有的分析数据","询问", JOptionPane.YES_NO_OPTION);
				if (retcode==0) {
					exit();
				}*/
				JOptionPane.showMessageDialog(new JFrame(), "正在分析图片，禁止退出！", "警告",JOptionPane.WARNING_MESSAGE);
				return;
			}
			else if(Lock.record){
				/*int retcode = JOptionPane.showConfirmDialog(new JFrame(), "正在录制视频，确定强制退出？\n退出将会丢失现有的分析数据","询问", JOptionPane.YES_NO_OPTION);
				if (retcode==0) {
					exit();
				}*/
				JOptionPane.showMessageDialog(new JFrame(), "正在录制视频，禁止退出！", "警告",JOptionPane.WARNING_MESSAGE);
				return;
			}else {
				int retcode = JOptionPane.showConfirmDialog(new JFrame(), "是否退出？\n退出将会丢失现有的分析数据\n建议保存分析数据","询问", JOptionPane.YES_NO_OPTION);
				System.out.println(retcode);
				if (retcode == 0) {
					exit();
				} else {
					return; // 直接返回，阻止默认动作，阻止窗口关闭
				}
			}
		}
		//super.processWindowEvent(e); // 该语句会执行窗口事件的默认动作(如：隐藏)
	}

	public JMenuBar getJMenuBar() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			JMenu menu_file = new JMenu("文件");
			menu_file.setFont(new Font("新宋体", Font.PLAIN, 12));
			menuBar.add(menu_file);

			JMenu menu_edit = new JMenu("编辑");
			menu_edit.setFont(new Font("新宋体", Font.PLAIN, 12));
			menuBar.add(menu_edit);

			JMenuItem clear = new JMenuItem("清空日志");
			clear.setActionCommand("clear");
			clear.setFont(new Font("新宋体", Font.PLAIN, 12));
			clear.addActionListener(this);
			menu_edit.add(clear);

			JMenu menu_setting = new JMenu("设置");
			menu_setting.setFont(new Font("新宋体", Font.PLAIN, 12));
			menuBar.add(menu_setting);

			JMenuItem setting = new JMenuItem("设置");
			setting.setActionCommand("setting");
			setting.setFont(new Font("新宋体", Font.PLAIN, 12));
			setting.addActionListener(this);
			menu_setting.add(setting);

			JMenu menu_help = new JMenu("帮助");
			menu_help.setFont(new Font("新宋体", Font.PLAIN, 12));
			menuBar.add(menu_help);

			JMenuItem help = new JMenuItem("帮助");
			help.setActionCommand("help");
			help.setFont(new Font("新宋体", Font.PLAIN, 12));
			help.addActionListener(this);
			menu_help.add(help);

			JMenuItem about = new JMenuItem("关于");
			about.setActionCommand("about");
			about.setFont(new Font("新宋体", Font.PLAIN, 12));
			about.addActionListener(this);
			menu_help.add(about);

		}
		return menuBar;
	}
	public static JDialog AnalyzeDialog(final int row ,String videopath,final JFrame frm,String startpont,String endpoint){
		AnalyzeDialog = new JDialog(frm,videopath){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected JRootPane createRootPane(){
				KeyStroke  stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0);
				JRootPane rootPane = new JRootPane();
				rootPane.registerKeyboardAction(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						AnalyzeDialog.dispose();
					}
				},stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
				return rootPane;
			}
		};
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
		AnalyzeDialog.setLayout(new BorderLayout());
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

		JButton setButton = new JButton("确定修改");
		setButton.setPreferredSize(dimension3);
		final String resulttxt   = analyzefolder+File.separator+"result.txt";

		setButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if(JOptionPane.showConfirmDialog(new JFrame(),"确定保存修改？", 
						"Warning",JOptionPane.YES_NO_OPTION) == 0){
					String startp = starPointTextField.getText();
					String endp = endPointTextField.getText();
					spTable.setValueAt(startp, row, 9);
					spTable.setValueAt(endp, row, 10);
					spTable.setValueAt(Integer.parseInt(endp)*10, row, 11);

					String parestatus = KPIMain.spTableModel.getValueAt(row, 8).toString();
					String sttime = KPIMain.spTableModel.getValueAt(row, 9).toString();
					String edtime = KPIMain.spTableModel.getValueAt(row, 10).toString();
					String result = KPIMain.spTableModel.getValueAt(row, 11).toString();

					String strContent = String.format("%s:%s:%s:%s", parestatus,sttime,edtime,result);
					ServiceHelper.writeToFile(resulttxt, strContent, false); 
					AnalyzeDialog.dispose();

				}
			}
		});
		starPointTextField = new NumberJTextField();
		starPointTextField.setNumberOnly(true);
		starPointTextField.setText(startpont);
		//endPointTextField = new JFormattedTextField(NumberFormat.getIntegerInstance());
		endPointTextField = new NumberJTextField();
		endPointTextField.setNumberOnly(true);
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
		((JComponent) AnalyzeDialog.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		AnalyzeDialog.setSize(700,800);
		AnalyzeDialog.setLocationRelativeTo(frm);

		//mkrcaseDialog.getContentPane().add(scrollPane,BorderLayout.NORTH);
		AnalyzeDialog.getContentPane().add(tablepan,BorderLayout.CENTER);
		AnalyzeDialog.getContentPane().add(dataPanel,BorderLayout.NORTH);
		AnalyzeDialog.setVisible(true);
		return AnalyzeDialog;
	}
	/**
	 * 初始化设置
	 */
	private void settingInit(){
		//设置monkeyrunner.bat路径

		String mrpath = ConfigHelper.getMonkeyrunner();
		if(mrpath==null || mrpath.equals("")){
			String android_home = ServiceHelper.getEnv("ANDROID_HOME");
			if(android_home!=null && !android_home.equals("")){
				ConfigHelper.setMonkeyrunner(android_home+"\\tools\\monkeyrunner.bat");
			}
		}
		/*//cycles 设置循环次数
		String cycles = ConfigHelper.getCycles();
		if(cycles==null || cycles.equals("")){
			ConfigHelper.setCycles(5+"");
		}*/
		EnviromentListener.initenv();
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ServiceHelper.KillMoneyPids(false);
			}
		});
		t1.start();
	}
	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();
		System.out.println(command);
		if (command.equals("recoder")) {
			if(Lock.replay){
				JOptionPane.showMessageDialog(new JFrame(), "正在回放，请稍等!","警告", JOptionPane.WARNING_MESSAGE);
			}
			List<Hashtable<String, String>> devList = ServiceHelper.getDevice();
			if (devList.size() == 0) {
				JOptionPane.showMessageDialog(new JFrame(), "device not found",
						"错误", JOptionPane.ERROR_MESSAGE);
			} else if (devList.size() > 1) {
				JOptionPane.showMessageDialog(new JFrame(),
						"more than one device and emulator", "错误",
						JOptionPane.ERROR_MESSAGE);
			} else {
				if (JOptionPane.showConfirmDialog(new JFrame(),
						"Are you sure to start AutoKPI Recorder ? ", "询问",
						JOptionPane.YES_NO_OPTION) == 0) {
					final String rcpath = JarHelper.getJarProjectPath()
					+ "AutoKPI Recorder.jar";
					if (new File(rcpath).exists() == true) {
						//this.setVisible(false);
						System.out.println(rcpath);
						Thread t1 = new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								ServiceHelper.record(rcpath);
							}
						});
						t1.start();
						//this.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(new JFrame(), rcpath
								+ " not exist", "警告",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
		if (command.equals("replay")) {
			if(Lock.replay){
				JOptionPane.showMessageDialog(new JFrame(), "还有一个脚本正在执行，请稍等!","警告", JOptionPane.WARNING_MESSAGE);
			}else{
				if(JOptionPane.showConfirmDialog(new JFrame(),"确定执行选择的脚本?", 
						"Warning",JOptionPane.YES_NO_OPTION) == 0){
					Lock.replay = true;
					prp.replay();
				}
			}
		}
		if (command.equals("clear")) {
			prp.clear();
		}
		if (command.equals("setting")) {
			SettingDialog sd = new SettingDialog(frame);
			sd.setVisible(true);
		}
		if (command.equals("help")) {
			HelpDialog hd = new HelpDialog(frame);
			hd.setVisible(true);
		}
		if (command.equals("about")) {
			AboutDialog ad = new AboutDialog(frame);
			ad.setVisible(true);
		}

		//camera video record
		if (command.equals("record_start")) {
			System.out.println("record_start");
			if (Lock.record) {
				JOptionPane.showMessageDialog(new JFrame(), new Label("正在录制视频，请稍等！！"),
						"警告", JOptionPane.INFORMATION_MESSAGE);
			}
			String path = JarHelper.getProjectPath() + "\\video";
			File dfile = new File(path);
			if (!dfile.exists()) {
				dfile.mkdirs();
			}
			JFileChooser aviChooser = new JFileChooser(new File(path));
			FontHelper.setFont(aviChooser);
			// 设置为智能选文件
			aviChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			// 去掉所有
			aviChooser.removeChoosableFileFilter(aviChooser.getAcceptAllFileFilter());
			aviChooser.setDialogTitle("请输入文件名");
			FileNameExtensionFilter filter_avi = new FileNameExtensionFilter(
					"视频(*.avi)", "avi");
			aviChooser.setFileFilter(filter_avi);
			int result = aviChooser.showSaveDialog(frame);

			if (result == JFileChooser.APPROVE_OPTION) {
				File avi = aviChooser.getSelectedFile();
				String filename = avi.getPath();
				if (!filename.endsWith(".avi")) {
					filename = filename + ".avi";
				}
				if (new File(filename).exists()) {
					int retcode = JOptionPane.showConfirmDialog(null,
							new Label("视频已经存在，是否覆盖？"), "询问",
							JOptionPane.YES_NO_OPTION);
					if (retcode != 0) {

					}
				}
				videoPanel.startRecord(filename);
			}
		}
		if (command.equals("record_stop")) {
			System.out.println("record_stop camera.......");
			if (Lock.record) {
				videoPanel.stopRecord();
				ProgressBarDialog pbd = new ProgressBarDialog(frame);
				pbd.setVisible(true);
				pbd.close();// 关闭进度条
			}
		}
		if (command.equals("reconnect")) {
			videoPanel.connectCamera(true);
		}
	}
	class SimulatedTarget implements Runnable  
	{   
		private volatile int current;  
		private int amount;  
		public SimulatedTarget(int amount)  
		{    
			current = 0;  
			this.amount = amount;  
		}  
		public int getAmount()  
		{    
			return amount;  
		}  
		public int getCurrent()  
		{    
			return current;  
		}  
		public void run()  
		{    
			while (current < amount)  
			{   
				try 
				{  
					Thread.sleep(50);  
				}  
				catch(InterruptedException e)  
				{  
				}  
				current++;  
			}  
		}  
	} 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		String lnf = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lnf);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//设置TableHeader.font
		/*
		 * 名称：类型
Table.ancestorInputMap ：Inputmap
Table.ancestorInputMap.RightToLeft： Inputmap
Table.background：Color
Table.focusCellBackground：Color
Table.focusCellForeground：Color
Table.focusCellHighlightBorder：Border
Table.font：Font
Table.foreground： Color
Table.gridColor： Color
Table.scrollPaneBorder： Border
Table.selectionBackground： Color
Table.selectionForeground： Color
TableHeader.background： Color
TableHeader.cellBorder： Border
TableHeader.font： Font
TableHeader.foreground： Color
		 * 
		 * */
		//UIManager.put("TableHeader.font",new Font("Monospaced",Font.PLAIN,11));

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if(ServiceHelper.checkJarInProcess()){
						JOptionPane.showMessageDialog(new JFrame(), "已经有一个相同的程序正在运行!","警告", JOptionPane.WARNING_MESSAGE);
					}else{
						new KPIMain();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
