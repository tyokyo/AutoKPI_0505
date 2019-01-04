package com.mr.replay.ui.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.NumberFormat;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.mr.replay.ui.helper.ConfigHelper;
import com.mr.replay.ui.helper.JarHelper;
import com.mr.replay.ui.listener.NumberJTextField;
/**
 * 
 * @author houyin.tian
 *
 */
public class SettingPanel extends JPanel implements ActionListener{

	private JLabel lb_mk_path;
	//private JLabel lb_cyc_path;
	private JTextField tf_mk_path;
	//private JTextField tf_cyc_path;
	private JButton bt_select_mk;
	private JButton bt_mk_setting;
	//private JButton bt_cyc_setting;
	
	private JLabel ffmpeg_label;
	private JTextField ffmpeg_txtfld;
	
	private JLabel fps_label;
	private NumberJTextField fps_txtfld;
	private JButton fps_btn;
	
	private JButton ffmpeg_select_btn;
	private JButton ffmpeg_btn;
	
	private JLabel start_label;
	private NumberJTextField start_txtfld;
	private JButton start_btn;
	
	private JLabel end_label;
	private NumberJTextField end_txtfld;
	private JButton end_btn;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SettingPanel(){
		JPanel select_mk_panel = new JPanel();
		Dimension dimension = new Dimension(350, 20);
		Dimension dimensionhash = new Dimension(200, 20);
		Dimension lbDimension = new Dimension(110, 20);
		
		select_mk_panel.setLayout(new BoxLayout(select_mk_panel, BoxLayout.PAGE_AXIS));
		select_mk_panel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

		JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		
		lb_mk_path = new JLabel("monkeyrunner.bat：");
		lb_mk_path.setPreferredSize(lbDimension);
		tf_mk_path = new JTextField();
		tf_mk_path.setPreferredSize(dimension);
		
		bt_select_mk = new JButton("选择");
		bt_select_mk.setActionCommand("bt_select_mk");
		bt_select_mk.addActionListener(this);
		
		bt_mk_setting = new JButton("设置");
		bt_mk_setting.setActionCommand("bt_mk_setting");
		bt_mk_setting.addActionListener(this);
		
		rowPanel.add(lb_mk_path);
		rowPanel.add(tf_mk_path);
		rowPanel.add(bt_select_mk);
		rowPanel.add(bt_mk_setting);
		select_mk_panel.add(rowPanel);
		select_mk_panel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		
		rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		fps_label = new JLabel("fps：");
		fps_label.setPreferredSize(lbDimension);
		fps_txtfld = new NumberJTextField();
		fps_txtfld.setNumberOnly(true);
		
		fps_txtfld.setPreferredSize(dimension);
		fps_btn = new JButton("设置");
		fps_btn.setActionCommand("fps_btn");
		fps_btn.addActionListener(this);
		
		rowPanel.add(fps_label);
		rowPanel.add(fps_txtfld);
		rowPanel.add(fps_btn);
		select_mk_panel.add(rowPanel);
		select_mk_panel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		
		/*rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		lb_cyc_path = new JLabel("Cycles：");
		lb_cyc_path.setPreferredSize(lbDimension);
		tf_cyc_path = new JTextField();
		tf_cyc_path.setPreferredSize(dimension);
		bt_cyc_setting = new JButton("设置");
		bt_cyc_setting.setActionCommand("bt_cyc_setting");
		bt_cyc_setting.addActionListener(this);
		
		rowPanel.add(lb_cyc_path);
		rowPanel.add(tf_cyc_path);
		rowPanel.add(bt_cyc_setting);
		select_mk_panel.add(rowPanel);*/
		
		
		rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		ffmpeg_label = new JLabel("ffmpeg.exe：");
		ffmpeg_label.setPreferredSize(lbDimension);
		ffmpeg_txtfld = new JTextField();
		ffmpeg_txtfld.setPreferredSize(dimension);
		
		ffmpeg_select_btn = new JButton("选择");
		ffmpeg_select_btn.setActionCommand("ffmpeg_select_btn");
		ffmpeg_select_btn.addActionListener(this);
		
		ffmpeg_btn = new JButton("设置");
		ffmpeg_btn.setActionCommand("ffmpeg_btn");
		ffmpeg_btn.addActionListener(this);
		rowPanel.add(ffmpeg_label);
		rowPanel.add(ffmpeg_txtfld);
		rowPanel.add(ffmpeg_select_btn);
		rowPanel.add(ffmpeg_btn);
		select_mk_panel.add(rowPanel);
		select_mk_panel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);


		rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		start_label = new JLabel("算法开始图片相似度>=：");
		start_label.setPreferredSize(dimensionhash);
		start_txtfld =new NumberJTextField();
		start_txtfld.setMaxTextLength(1);
		start_txtfld.setNumberOnly(true);
		start_txtfld.setPreferredSize(dimensionhash);
		
		start_btn = new JButton("设置");
		start_btn.setActionCommand("start_btn");
		start_btn.addActionListener(this);
		rowPanel.add(start_label);
		rowPanel.add(start_txtfld);
		rowPanel.add(start_btn);
		select_mk_panel.add(rowPanel);
		select_mk_panel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);

		rowPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		end_label = new JLabel("算法结束图片相似度<=：");
		end_label.setPreferredSize(dimensionhash);
		end_txtfld = new NumberJTextField();
		end_txtfld.setMaxTextLength(1);
		end_txtfld.setNumberOnly(true);
		end_txtfld.setPreferredSize(dimensionhash);
		
		end_btn = new JButton("设置");
		end_btn.setActionCommand("end_btn");
		end_btn.addActionListener(this);
		rowPanel.add(end_label);
		rowPanel.add(end_txtfld);
		rowPanel.add(end_btn);
		select_mk_panel.add(rowPanel);
		select_mk_panel.add(new JSeparator(SwingConstants.HORIZONTAL), BorderLayout.CENTER);
		
		//select_mk_panel.setPreferredSize(new Dimension(500, 600));
		this.add(select_mk_panel);
		
		
		/*int x = 0;
		int y = 0;
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0 };
		gbl_contentPane.rowHeights = new int[] { 0,0};
		gbl_contentPane.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		this.setLayout(gbl_contentPane);
		
		
		//升级路径
		JPanel select_mk_panel = new JPanel();
		GridBagConstraints gbc_select_mk_panel = new GridBagConstraints();
		gbc_select_mk_panel.insets = new Insets(0, 0, 0, 0);
		gbc_select_mk_panel.fill = GridBagConstraints.BOTH;
		gbc_select_mk_panel.gridx = x;
		gbc_select_mk_panel.gridy = y;
		this.add(select_mk_panel, gbc_select_mk_panel);

		GridBagLayout gbl_select_mk_panel = new GridBagLayout();
		gbl_select_mk_panel.columnWidths = new int[] { 0, 0, 0,0 };
		gbl_select_mk_panel.rowHeights = new int[] { 0 };
		gbl_select_mk_panel.columnWeights = new double[] { 0.0,Double.MIN_VALUE, 0.0,0.0 };
		gbl_select_mk_panel.rowWeights = new double[] { Double.MIN_VALUE };
		select_mk_panel.setLayout(gbl_select_mk_panel);

		lb_mk_path = new JLabel("Monkeyrunner：");
		lb_mk_path.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_lb_mk_path = new GridBagConstraints();
		gbc_lb_mk_path.insets = new Insets(0, 0, 0, 0);
		gbc_lb_mk_path.fill = GridBagConstraints.BOTH;
		gbc_lb_mk_path.gridx = 0;
		gbc_lb_mk_path.gridy = 0;
		select_mk_panel.add(lb_mk_path, gbc_lb_mk_path);

		tf_mk_path = new JTextField();
		tf_mk_path.setSize(100, 10);
		tf_mk_path.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_tf_mk_path = new GridBagConstraints();
		gbc_tf_mk_path.insets = new Insets(0, 0, 0, 0);
		gbc_tf_mk_path.fill = GridBagConstraints.BOTH;
		gbc_tf_mk_path.gridx = 1;
		gbc_tf_mk_path.gridy = 0;
		select_mk_panel.add(tf_mk_path, gbc_tf_mk_path);

		bt_select_mk = new JButton("选择");
		bt_select_mk.setActionCommand("bt_select_mk");
		bt_select_mk.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_bt_select_mk = new GridBagConstraints();
		gbc_bt_select_mk.insets = new Insets(0, 0, 0, 0);
		gbc_bt_select_mk.gridx = 2;
		gbc_bt_select_mk.gridy = 0;
		bt_select_mk.addActionListener(this);
		select_mk_panel.add(bt_select_mk, gbc_bt_select_mk);
		
		bt_mk_setting = new JButton("设置");
		bt_mk_setting.setActionCommand("bt_mk_setting");
		bt_mk_setting.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_bt_mk_setting = new GridBagConstraints();
		gbc_bt_mk_setting.insets = new Insets(0, 0, 0, 0);
		gbc_bt_mk_setting.gridx = 3;
		gbc_bt_mk_setting.gridy = 0;
		bt_mk_setting.addActionListener(this);
		select_mk_panel.add(bt_mk_setting, gbc_bt_mk_setting);
		
		//cycles
		lb_cyc_path = new JLabel("Cycles：");
		lb_cyc_path.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_lb_cyc_path = new GridBagConstraints();
		gbc_lb_cyc_path.insets = new Insets(0, 0, 0, 0);
		gbc_lb_cyc_path.fill = GridBagConstraints.BOTH;
		gbc_lb_cyc_path.gridx = 0;
		gbc_lb_cyc_path.gridy = 1;
		select_mk_panel.add(lb_cyc_path, gbc_lb_cyc_path);

		tf_cyc_path = new JTextField();
		tf_cyc_path.setSize(100, 10);
		tf_cyc_path.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_tf_cyc_path = new GridBagConstraints();
		gbc_tf_cyc_path.insets = new Insets(0, 0, 0, 0);
		gbc_tf_cyc_path.fill = GridBagConstraints.BOTH;
		gbc_tf_cyc_path.gridx = 1;
		gbc_tf_cyc_path.gridy = 1;
		select_mk_panel.add(tf_cyc_path, gbc_tf_cyc_path);

//		bt_select_cyc = new JButton("选择");
//		bt_select_cyc.setFont(new Font("新宋体", Font.PLAIN, 12));
//		GridBagConstraints gbc_bt_select_cyc = new GridBagConstraints();
//		gbc_bt_select_cyc.insets = new Insets(0, 0, 0, 0);
//		gbc_bt_select_cyc.gridx = 2;
//		gbc_bt_select_cyc.gridy = 1;
//		bt_select_cyc.addActionListener(this);
//		select_mk_panel.add(bt_select_cyc, gbc_bt_select_cyc);
		
		bt_cyc_setting = new JButton("设置");
		bt_cyc_setting.setFont(new Font("新宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_bt_cyc_setting = new GridBagConstraints();
		gbc_bt_cyc_setting.insets = new Insets(0, 0, 0, 0);
		gbc_bt_cyc_setting.gridx = 3;
		gbc_bt_cyc_setting.gridy = 1;
//		gbc_bt_cyc_setting.gridwidth = 2;
		bt_cyc_setting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String cyc = tf_cyc_path.getText();
				if(!cyc.equals("")&&cyc!=null){
					if(!PatternHelper.testPartternInt(cyc)){
						JOptionPane.showMessageDialog(null, "你输入的循环次数不合法,请重新输入!", "ERROR",JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					boolean bool = ConfigHelper.setCycles(cyc);
					if(bool){
						JOptionPane.showMessageDialog(null, "循环次数设置成功！", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "循环次数不能为空！", "WARNING",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		select_mk_panel.add(bt_cyc_setting, gbc_bt_cyc_setting);*/
		init();
	}

	public void init(){
		String mp = ConfigHelper.getMonkeyrunner();
		if(mp!=null){
			tf_mk_path.setText(mp);
		}
		
		/*String cyc = ConfigHelper.getCycles();
		if(cyc!=null){
			tf_cyc_path.setText(cyc);
		}*/
		String fps = ConfigHelper.getfps();
		if(fps!=null){
			fps_txtfld.setText(fps);
		}else {
			fps_txtfld.setText("100");
			ConfigHelper.setfps("100");
		}
		
		String ffmpeg = ConfigHelper.getffmpeg();
		if(ffmpeg!=null){
			ffmpeg_txtfld.setText(ffmpeg);
		}
		
		String startpoint = ConfigHelper.getStartHash();
		if(startpoint!=null){
			start_txtfld.setText(startpoint);
		}else {
			start_txtfld.setText("5");
			ConfigHelper.setStartHash("5");
		}
		
		String endpoint = ConfigHelper.getEndHash();
		if(endpoint!=null){
			end_txtfld.setText(endpoint);
		}else {
			end_txtfld.setText("5");
			ConfigHelper.setEndHash("5");
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String coms = e.getActionCommand();
		if (coms.equals("bt_select_mk")) {
			JFileChooser cm = new JFileChooser(new File(JarHelper.getJarProjectPath()));
			// 设置为智能选文件
			cm.setFileSelectionMode(JFileChooser.FILES_ONLY);
			// 去掉所有
			cm.removeChoosableFileFilter(cm.getAcceptAllFileFilter());
			cm.setDialogTitle("请选择Monkeyrunner.bat");
			FileNameExtensionFilter bat = new FileNameExtensionFilter("*.bat","bat");
			cm.setFileFilter(bat);
			int rm = cm.showOpenDialog(this);
			if (rm == JFileChooser.APPROVE_OPTION) {
				File file = cm.getSelectedFile();
				tf_mk_path.setText(file.getPath());
			}
		}
		if (coms.equals("bt_mk_setting")) {
			String mkp = tf_mk_path.getText();
			if(!mkp.equals("")&&mkp!=null){
				if(!mkp.endsWith("monkeyrunner.bat")){
					JOptionPane.showMessageDialog(null, "你选择的程序不是monkeyrunner.bat,请重新选择!", "ERROR",JOptionPane.ERROR_MESSAGE);
				}
				File mpf = new File(mkp);
				if(!mpf.exists()){
					JOptionPane.showMessageDialog(null, mkp+"不存在,请重新选择!", "ERROR",JOptionPane.ERROR_MESSAGE);
				}
				boolean bool = ConfigHelper.setMonkeyrunner(mkp);
				if(bool){
					JOptionPane.showMessageDialog(null, "Monkeyrunner设置成功！", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(null, "请选择Monkeyrunner！", "WARNING",JOptionPane.WARNING_MESSAGE);
			}
		}
		//fps_btn
		if (coms.equals("fps_btn")) {
			String fps = fps_txtfld.getText();
			if(!fps.equals("")&&fps!=null){
				boolean bool = ConfigHelper.setfps(fps);
				if(bool){
					JOptionPane.showMessageDialog(null, "帧率设置成功！", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(null, "帧率不能为空,且只能是数字！", "WARNING",JOptionPane.WARNING_MESSAGE);
			}
		}
		//start point
		if (coms.equals("start_btn")) {
			String startpoint = start_txtfld.getText();
			if(!startpoint.equals("")&&startpoint!=null){
				boolean bool = ConfigHelper.setStartHash(startpoint);
				if(bool){
					JOptionPane.showMessageDialog(null, "汉明距离图片分析值设置成功！", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(null, "汉明距离值不能为空,且只能是数字！", "WARNING",JOptionPane.WARNING_MESSAGE);
			}
		}
		
		//end point
		if (coms.equals("end_btn")) {
			String endpoint = end_txtfld.getText();
			if(!endpoint.equals("")&&endpoint!=null){
				boolean bool = ConfigHelper.setEndHash(endpoint);
				if(bool){
					JOptionPane.showMessageDialog(null, "汉明距离图片分析值设置成功！", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(null, "汉明距离值不能为空,且只能是数字！", "WARNING",JOptionPane.WARNING_MESSAGE);
			}
		}
		/*if (coms.equals("bt_cyc_setting")) {
			String cyc = tf_cyc_path.getText();
			if(!cyc.equals("")&&cyc!=null){
				if(!PatternHelper.testPartternInt(cyc)){
					JOptionPane.showMessageDialog(null, "你输入的循环次数不合法,请重新输入!", "ERROR",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				boolean bool = ConfigHelper.setCycles(cyc);
				if(bool){
					JOptionPane.showMessageDialog(null, "循环次数设置成功！", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(null, "循环次数不能为空！", "WARNING",JOptionPane.WARNING_MESSAGE);
			}
		}*/
		if (coms.equals("ffmpeg_btn")) {
			String ffmpeg_txt = ffmpeg_txtfld.getText();
			if(!ffmpeg_txt.equals("")&&ffmpeg_txt!=null){
				boolean bool = ConfigHelper.setffmpeg(ffmpeg_txt);
				if(bool){
					JOptionPane.showMessageDialog(null, "ffmpeg.exe路径设置成功！", "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(null, "ffmpeg.exe路径不能为空！", "WARNING",JOptionPane.WARNING_MESSAGE);
			}
		}
		if (coms.equals("ffmpeg_select_btn")) {
			JFileChooser cm = new JFileChooser(new File(JarHelper.getJarProjectPath()));
			cm.setFileSelectionMode(JFileChooser.FILES_ONLY);
			cm.removeChoosableFileFilter(cm.getAcceptAllFileFilter());
			cm.setDialogTitle("请选择ffpmeg.exe");
			FileNameExtensionFilter bat = new FileNameExtensionFilter("*.exe","exe");
			cm.setFileFilter(bat);
			int rm = cm.showOpenDialog(this);
			if (rm == JFileChooser.APPROVE_OPTION) {
				File file = cm.getSelectedFile();
				ffmpeg_txtfld.setText(file.getPath());
			}
		}
		
	}
}
