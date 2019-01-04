package com.ckt.autokpi.video.ui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.ckt.autokpi.video.helper.FontHelper;
import com.ckt.autokpi.video.ui.button.Button;
import com.ckt.autokpi.video.ui.label.Label;
import com.ckt.autokpi.video.ui.panel.VideoPanel;
import com.mr.replay.ui.dialog.ProgressBarDialog;
import com.mr.replay.ui.frame.KPIMain;
import com.mr.replay.ui.helper.JarHelper;
import com.mr.replay.ui.lock.Lock;

/**
 * 
 * @author houyin.tian
 * 
 */
public class VideoFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VideoPanel videoPanel = null;
	private JMenuBar menuBar;
	private JToolBar toolBar;
	private Dimension size;
	private Button record_start;
	private Button record_stop;
	private Button reconnect;
	private int width = 640;
	private int height = 800;

	public VideoFrame() {
		super("AutoKPI Video v1.0");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.getVideoPanel();
		this.size = videoPanel.getImageSize();
		this.width = size.width;
		this.setSize(width, height);
		this.setCenter();
		this.getContentPane().add(getVideoPanel(), BorderLayout.CENTER);
		this.getContentPane().add(getVideoToolBar(), BorderLayout.PAGE_START);
		this.setVisible(true);
	}

	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			if (Lock.record) {
				int i = JOptionPane.showConfirmDialog(null, new Label(
				"正在录制视频，是否强制退出？"), "警告", JOptionPane.OK_CANCEL_OPTION);
				if (i == 0) {
					videoPanel.stopRecord();
					videoPanel.close();
					ProgressBarDialog pbd = new ProgressBarDialog(KPIMain.frame);
					//ProgressBarDialog pbd = new ProgressBarDialog(this,"正在关闭，请稍候……");
					pbd.setVisible(true);
					pbd.close();// 关闭进度条
					System.exit(0);// 退出系统
				}
			} else {
				int retcode = JOptionPane.showConfirmDialog(null, new Label(
				"是否退出？"), "询问", JOptionPane.YES_NO_OPTION);
				if (retcode == 0) {
					videoPanel.close();
					ProgressBarDialog pbd = new ProgressBarDialog(KPIMain.frame);
					//ProgressBarDialog pbd = new ProgressBarDialog(this,"正在关闭，请稍候……");
					pbd.setVisible(true);
					pbd.close();// 关闭进度条
					System.exit(0);// 退出系统
				}
			}
		}
		super.processWindowEvent(e); // 该语句会执行窗口事件的默认动作(如：隐藏)
	}

	private void setCenter() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int x = (int) (toolkit.getScreenSize().getWidth() - width) / 2;
		int y = (int) (toolkit.getScreenSize().getHeight() - height) / 2;
		this.setLocation(x, y);// 设置窗口居中
	}

	private JMenuBar getVideoMenuBar(){
		if(menuBar == null){
			menuBar = new JMenuBar();

			JMenu file = new JMenu("文件");
			FontHelper.setFont(file);
			menuBar.add(file);

		}
		return menuBar;
	}
	/**
	 * 工具栏
	 * 
	 * @return JToolBar
	 */
	private JToolBar getVideoToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();

			record_start = new Button("开始录制");
			record_start.setActionCommand("record_start");
			record_start.addActionListener(this);
			toolBar.add(record_start);

			record_stop = new Button("停止录制");
			record_stop.setActionCommand("record_stop");
			record_stop.addActionListener(this);
			toolBar.add(record_stop);

			reconnect = new Button("重新连接");
			reconnect.setActionCommand("reconnect");
			reconnect.addActionListener(this);
			toolBar.add(reconnect);

			toolBar.setBackground(Color.lightGray);
		}

		return toolBar;
	}

	public VideoPanel getVideoPanel() {
		if (videoPanel == null) {
			//videoPanel = new VideoPanel(this);
		}
		return videoPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();
		if (command.equals("record_start")) {
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
			int result = aviChooser.showSaveDialog(this);

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
			if (Lock.record) {
				videoPanel.stopRecord();
				//ProgressBarDialog pbd = new ProgressBarDialog(this,"正在停止，请稍候……");
				ProgressBarDialog pbd = new ProgressBarDialog(KPIMain.frame);
				pbd.setVisible(true);
				pbd.close();// 关闭进度条
			}
		}
		if (command.equals("reconnect")) {
			videoPanel.connectCamera(true);
		}
		/*switch (command) {
		case "开始录制":
			
			break;
		case "停止录制":
			
			break;
		case "重新连接":
			break;
		default:
			break;
		}*/
	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new VideoFrame();
			}
		});
	}
}
