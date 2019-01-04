package com.mr.replay.ui.frame;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import com.mr.replay.ui.dialog.AboutDialog;
import com.mr.replay.ui.dialog.HelpDialog;
import com.mr.replay.ui.dialog.ProgressBarDialog;
import com.mr.replay.ui.dialog.SettingDialog;
import com.mr.replay.ui.helper.ConfigHelper;
import com.mr.replay.ui.helper.JarHelper;
import com.mr.replay.ui.helper.ServiceHelper;
import com.mr.replay.ui.lock.Lock;
import com.mr.replay.ui.panel.PyReplayPanel;

/**
 * 
 * @author houyin.tian
 *
 */
public class MRReplayFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	// 设置窗口宽度
	private int w = 800;
	// 设置高度
	private int h = 600;
	private PyReplayPanel prp;
	private JMenuBar menuBar;
	private JToolBar toolBar;
	private JButton recoder;
	private JButton replay;

	public MRReplayFrame() {
		super();
		this.setTitle("AutoKPI Replay");
		// 激活窗口事件
		this.enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setBounds(100, 100, w, h);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int x = (int) (toolkit.getScreenSize().getWidth() - w) / 2;
		int y = (int) (toolkit.getScreenSize().getHeight() - h) / 2;
		this.setLocation(x, y);// 设置窗口居中
		this.setJMenuBar(getJMenuBar());// 设置菜单项
		this.getContentPane().add(getToolBar(), BorderLayout.PAGE_START);
		this.getContentPane().add(getPyReplayPanel(), BorderLayout.CENTER);
		this.setVisible(true);
		
		this.settingInit();
	}

	//重写窗口事件
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			
			if(Lock.replay){
				JOptionPane.showMessageDialog(null, "正在回放脚本，禁止退出！", "警告",JOptionPane.WARNING_MESSAGE);
				return;
			}
			int retcode = JOptionPane.showConfirmDialog(null, "是否退出？","询问", JOptionPane.YES_NO_OPTION);
			System.out.println(retcode);
			if (retcode == 0) {
				prp.closeTimer();//关闭设备刷新线程
				ProgressBarDialog pbd = new ProgressBarDialog(this);
				pbd.setVisible(true);
				pbd.close();//关闭进度条
				System.exit(0);//退出系统
			} else {
				return; // 直接返回，阻止默认动作，阻止窗口关闭
			}
		}
		super.processWindowEvent(e); // 该语句会执行窗口事件的默认动作(如：隐藏)
	}

	

	private PyReplayPanel getPyReplayPanel() {
		if (prp == null) {
			prp = new PyReplayPanel(this);
		}
		return prp;
	}

	/**
	 * 菜单
	 */
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

	/**
	 * 
	 * @return
	 */
	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();
			recoder = new JButton("录制");
			recoder.setActionCommand("recoder");
			recoder.setFont(new Font("新宋体", Font.PLAIN, 12));
			// recoder.setIcon(new
			// ImageIcon(ConfigHelper.getBufferedImage("/com/mr/replay/ui/image/recode_1.png")));
			recoder.addActionListener(this);
			toolBar.add(recoder);

			replay = new JButton("回放");
			replay.setActionCommand("replay");
			replay.setFont(new Font("新宋体", Font.PLAIN, 12));
			replay.addActionListener(this);
			toolBar.add(replay);

			toolBar.setBackground(Color.lightGray);
		}
		return toolBar;
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
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();
		if (command.equals("recoder")) {
			if(Lock.replay){
				JOptionPane.showMessageDialog(null, "正在回放，请稍等!","警告", JOptionPane.WARNING_MESSAGE);
			}
			List<Hashtable<String, String>> devList = ServiceHelper.getDevice();
			if (devList.size() == 0) {
				JOptionPane.showMessageDialog(null, "device not found",
						"错误", JOptionPane.ERROR_MESSAGE);
			} else if (devList.size() > 1) {
				JOptionPane.showMessageDialog(null,
						"more than one device and emulator", "错误",
						JOptionPane.ERROR_MESSAGE);
			} else {
				if (JOptionPane.showConfirmDialog(null,
						"Are you sure to start AutoKPI Recorder ? ", "询问",
						JOptionPane.YES_NO_OPTION) == 0) {
					String rcpath = JarHelper.getJarProjectPath()
							+ "\\AutoKPI Recorder.jar";
					if (new File(rcpath).exists() == true) {
						this.setVisible(false);
						ServiceHelper.record(rcpath);
						this.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, rcpath
								+ " not exist", "警告",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			if (command.equals("replay")) {
				if(Lock.replay){
					JOptionPane.showMessageDialog(null, "还有一个脚本正在执行，请稍等!","警告", JOptionPane.WARNING_MESSAGE);
				}else{
					Lock.replay = true;
					prp.replay();
				}
			}
			if (command.equals("clear")) {
				prp.clear();
			}
			if (command.equals("setting")) {
				SettingDialog sd = new SettingDialog(this);
				sd.setVisible(true);
			}
			if (command.equals("help")) {
				HelpDialog hd = new HelpDialog(this);
				hd.setVisible(true);
			}
			if (command.equals("about")) {
				AboutDialog ad = new AboutDialog(this);
				ad.setVisible(true);
			}
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if(ServiceHelper.checkJarInProcess()){
						JOptionPane.showMessageDialog(null, "已经有一个相同的程序正在运行!","警告", JOptionPane.WARNING_MESSAGE);
					}else{
						new MRReplayFrame();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
