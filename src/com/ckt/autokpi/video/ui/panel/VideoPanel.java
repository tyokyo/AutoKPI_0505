package com.ckt.autokpi.video.ui.panel;

import com.ckt.autokpi.video.camera.Camera;
import com.ckt.autokpi.video.camera.RecordVideo;
import com.ckt.autokpi.video.helper.ConfigHelper;
import com.ckt.autokpi.video.helper.FontHelper;
import com.ckt.autokpi.video.helper.ImageHelper;
import com.ckt.autokpi.video.socket.VideoServer;
import com.ckt.autokpi.video.ui.dialog.ProgressBarDialog;
import com.ckt.autokpi.video.ui.label.Label;
import com.ckt.autokpi.video.ui.log.Log;
import com.mr.replay.ui.frame.KPIMain;
import com.mr.replay.ui.lock.Lock;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * 
 * 
 * @author houyin.tian
 * 
 */

public class VideoPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Label display = null;

	private JTextPane textPane;
	private JScrollPane testNumJsp;

	private static Camera camera = null;
	private RecordVideo recordVideo = null;
	private static RefreshDisplay refreshDisplay = null;
	private long fps = ConfigHelper.getVideoFPS();
	private Label record_time_lb;
	private Label record_start_time_lb;
	private Label record_stop_time_lb;

	private long nwts = 1000000000L / fps;

	private static VideoServer videoServer;
	private KPIMain parent;

	/**
	 * 
	 * @param kpiMain
	 */
	public VideoPanel(KPIMain kpiMain) {
		super();
		this.parent = kpiMain;
		this.setBorder(new EmptyBorder(0, 0, 0, 0));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0 };
		gbl_contentPane.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		this.setLayout(gbl_contentPane);

		JPanel display_panel = new JPanel();
		GridBagConstraints gbc_display_panel = new GridBagConstraints();
		gbc_display_panel.insets = new Insets(0, 0, 0, 0);
		gbc_display_panel.fill = GridBagConstraints.BOTH;
		gbc_display_panel.gridx = 0;
		gbc_display_panel.gridy = 0;
		this.add(display_panel, gbc_display_panel);

		GridBagLayout gbl_display_panel = new GridBagLayout();
		gbl_display_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_display_panel.rowHeights = new int[] { 0, 0 };
		gbl_display_panel.columnWeights = new double[] { 1.0, 1.0, 1.0 };
		gbl_display_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		display_panel.setLayout(gbl_display_panel);

		GridBagConstraints gbc_record_time = new GridBagConstraints();
		gbc_record_time.insets = new Insets(0, 0, 0, 0);
		gbc_record_time.fill = GridBagConstraints.BOTH;

		record_start_time_lb = new Label("开始时间：");
		gbc_record_time.gridx = 0;
		gbc_record_time.gridy = 0;
		display_panel.add(record_start_time_lb, gbc_record_time);
		record_start_time_lb.setForeground(Color.RED);

		record_stop_time_lb = new Label("结束时间：");
		gbc_record_time.gridx = 1;
		gbc_record_time.gridy = 0;
		display_panel.add(record_stop_time_lb, gbc_record_time);
		record_stop_time_lb.setForeground(Color.RED);

		record_time_lb = new Label("视频时长：00:00:00:000");
		gbc_record_time.gridx = 2;
		gbc_record_time.gridy = 0;
		display_panel.add(record_time_lb, gbc_record_time);
		record_time_lb.setForeground(Color.RED);

		display = new Label();
		GridBagConstraints gbc_display_image = new GridBagConstraints();
		gbc_display_image.insets = new Insets(0, 0, 0, 0);
		gbc_display_image.fill = GridBagConstraints.BOTH;
		gbc_display_image.gridx = 0;
		gbc_display_image.gridy = 1;
		gbc_display_image.gridwidth = 3;
		display_panel.add(display, gbc_display_image);

		// 日志模块
		JPanel log_panel = new JPanel();
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 0, 0);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 1;
		this.add(log_panel, gbc_textArea);

		GridBagLayout gbl_log_panel = new GridBagLayout();
		gbl_log_panel.columnWidths = new int[] { 0 };
		gbl_log_panel.rowHeights = new int[] { 0 };
		gbl_log_panel.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_log_panel.rowWeights = new double[] { Double.MIN_VALUE };
		log_panel.setLayout(gbl_log_panel);

		textPane = new JTextPane() {
			private static final long serialVersionUID = 1050724550342188152L;

			public boolean getScrollableTracksViewportWidth() {
				return false;
			}

			public void setSize(Dimension d) {
				if (d.width < getParent().getSize().width) {
					d.width = getParent().getSize().width;
				}
				d.width += 100;
				super.setSize(d);
			}
		};

		TitledBorder tb_log = BorderFactory.createTitledBorder("日志");
		FontHelper.setFont(tb_log);
		log_panel.setBorder(tb_log);

		FontHelper.setFont(textPane);
		testNumJsp = new JScrollPane(textPane);

		GridBagConstraints gbc_log_panel = new GridBagConstraints();
		gbc_log_panel.insets = new Insets(0, 0, 0, 0);
		gbc_log_panel.fill = GridBagConstraints.BOTH;
		gbc_log_panel.gridx = 0;
		gbc_log_panel.gridy = 0;
		log_panel.add(testNumJsp, gbc_log_panel);

		textPane.setEditable(true);
		Log.setLog(textPane);
		this.init();

	}

	public void init() {
		this.connectCamera(false);
		this.getRefreshDisplayThread();
		refreshDisplay.start();
		videoServer = new VideoServer(this);
		videoServer.start();
	}

	/**
	 * 显示视频开始时间
	 * 
	 * @param text
	 */
	public void setStartTime(String text) {
		record_start_time_lb.setText("开始时间：" + text);
	}

	/**
	 * 显示视频结束时间
	 * 
	 * @param text
	 */
	public void setStopTime(String text) {
		record_stop_time_lb.setText("结束时间：" + text);
	}

	/**
	 * 显示视频长度
	 * 
	 * @param text
	 */
	public void setVideoLength(String text) {
		record_time_lb.setText("视频长度：" + text);
	}

	/**
	 * 连接摄像头
	 * 
	 * @return
	 */
	public void connectCamera(boolean reconnect) {
		if (Lock.record) {
			int code = JOptionPane.showConfirmDialog(null, new Label(
					"重连将停止视频录制，是否继续？"), "询问", JOptionPane.YES_NO_OPTION);
			if (code == 0) {
				this.stopRecord();
			} else {
				return;
			}
		}
		if (reconnect) {
			refreshDisplay.setSuspendFlag();
		}
		if (camera != null) {
			camera.close();
			ProgressBarDialog pbd = new ProgressBarDialog(parent,"正在重连摄像头，请稍等！");
			pbd.setVisible(true);
			pbd.close();// 关闭进度条
			camera = null;
		}
		camera = new Camera(fps);
		while (camera.connect() == false) {
			int code = JOptionPane.showConfirmDialog(null, new Label(
					"连接摄像头失败，是否重试？"), "错误", JOptionPane.YES_NO_OPTION);
			if (code != 0) {
				break;
			}
		}
		if (camera.isConnected()) {
			camera.start();
			if (reconnect) {
				JOptionPane.showMessageDialog(new JFrame(), new Label("连接摄像头成功！"),"提示", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if (reconnect) {
			refreshDisplay.setResume();
		}
	}

	/**
	 * 获取界面刷新线程
	 * 
	 * @return
	 */
	private RefreshDisplay getRefreshDisplayThread() {
		if (refreshDisplay == null) {
			refreshDisplay = new RefreshDisplay();
		}
		return refreshDisplay;
	}

	/**
	 * 获取摄像头分辨率
	 * 
	 * @return
	 */
	public Dimension getImageSize() {
		if (camera != null && camera.isConnected()) {
			return camera.getSize();
		} else {
			BufferedImage bi = ImageHelper.getDefaultImage();
			return new Dimension(bi.getWidth(), bi.getHeight());
		}
	}

	/**
	 * 关闭视频监控线程
	 */
	public void close() {
		videoServer.close();
		refreshDisplay.close();
		camera.close();
	}

	/**
	 * 开始录制视频
	 */
	public void startRecord(String path) {
		System.out.println("start record camera.......");
		if (camera == null || camera.isConnected() == false) {
			JOptionPane.showMessageDialog(new JFrame(), new Label("请先连接摄像头！"),"错误提示", JOptionPane.ERROR_MESSAGE);
		} else if (Lock.record == false) {
			recordVideo = new RecordVideo(this, camera, fps, path);
			recordVideo.start();
			Lock.record = true;
		}
	}

	/**
	 * 停止录制视频
	 */
	public void stopRecord() {
		if (recordVideo != null && Lock.record) {
			recordVideo.close();
			Lock.record = false;
		}
	}

	class RefreshDisplay extends Thread {
		private boolean bool = true;
		private long bt = 0L;
		private long et = 0L;
		private long nm = 0L;
		private long hm = 0L;
		private int nanos = 0;
		private long i = 0L;
		private BufferedImage bi;
		private boolean suspendFlag = false;

		public void run() {
			try {
				while (bool) {
					synchronized (this) {
						while (suspendFlag) {
							wait();
						}
					}
					i++;
					bi = getImage();
					if (bi == null) {
						break;
					}
					display.setIcon(new ImageIcon(bi));
					et = System.nanoTime();
					nm = nwts * i - (et - bt);
					hm = nm / 1000000L;
					nanos = new Long(nm - hm * 1000000L).intValue();
					if (hm < 0 || nanos < 0) {

					} else {
						sleep(hm, nanos);
					}
				}
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}

		/**
		 * 
		 * @return
		 */
		public BufferedImage getImage() {
			BufferedImage bi = ImageHelper.getDefaultImage();
			if (camera != null && camera.isConnected()) {
				BufferedImage cbi = camera.getBufferedImage();
				if (cbi == null) {

					int code = JOptionPane.showConfirmDialog(null, new Label(
							"摄像头断开连接，是否重连？"), "询问", JOptionPane.YES_NO_OPTION);
					if (code == 0) {
						connectCamera(true);
					}
					return bi;
				} else {
					return cbi;
				}
			} else {
				return bi;
			}

		}

		/**
		 * 线程暂停
		 */
		public void setSuspendFlag() {
			this.suspendFlag = true;
		}

		/**
		 * 唤醒线程
		 */
		public synchronized void setResume() {
			this.suspendFlag = false;
			notify();
		}

		/**
		 * 关闭线程
		 */
		public void close() {
			bool = false;
		}

	}

}
