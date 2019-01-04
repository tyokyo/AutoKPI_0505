package com.mr.replay.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JOptionPane;

import com.mr.replay.ui.dialog.AboutDialog;
import com.mr.replay.ui.dialog.HelpDialog;
import com.mr.replay.ui.dialog.SettingDialog;
import com.mr.replay.ui.helper.JarHelper;
import com.mr.replay.ui.helper.ServiceHelper;
import com.mr.replay.ui.lock.Lock;

public class ReplayListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("btnrecord")) {
			
		}
	}

	/*case "录制":
		if(Lock.replay){
			JOptionPane.showMessageDialog(null, "正在回放，请稍等!","警告", JOptionPane.WARNING_MESSAGE);
			break;
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
		break;
	case "回放":
		if(Lock.replay){
			JOptionPane.showMessageDialog(null, "还有一个脚本正在执行，请稍等!","警告", JOptionPane.WARNING_MESSAGE);
		}else{
			Lock.replay = true;
			prp.replay();
		}
		break;
	case "清空日志":
		prp.clear();
		break;
	case "设置":
		SettingDialog sd = new SettingDialog(this);
		sd.setVisible(true);
		break;
		
	case "帮助":
		HelpDialog hd = new HelpDialog(this);
		hd.setVisible(true);
		break;
	case "关于":
		AboutDialog ad = new AboutDialog(this);
		ad.setVisible(true);
		break;*/
}
