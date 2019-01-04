package com.mr.replay.ui.listener;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mr.replay.ui.dialog.SettingDialog;
import com.mr.replay.ui.helper.ConfigHelper;
import com.mr.replay.ui.log.Log;

public class EnviromentListener {
	public static void initenv(){
		ConfigHelper configHelper = new ConfigHelper();
		String ffmpeg = configHelper.getffmpeg();
		String monkrunner = configHelper.getMonkeyrunner();
		String warn = "";
		if (ffmpeg==null) {
			Log.warn("ffmpeg.exe 配置错误");
			warn = warn+"ffmpeg.exe 配置错误\n";
		}else {
			File ffpegFile = new File(ffmpeg);
			if (!ffpegFile.exists()) {
				warn = warn+"ffmpeg.exe 配置错误\n";
				Log.warn("ffmpeg.exe 配置错误");
			}
		}
		if (monkrunner==null) {
			Log.warn("monkeyrunner.bat 配置错误");
			warn = warn+"monkeyrunner.bat 配置错误\n";
		}else {
			File mkrFile = new File(monkrunner);
			if (!mkrFile.exists()) {
				warn = warn+"monkeyrunner.bat 配置错误\n";
				Log.warn("monkeyrunner.bat 配置错误");
			}
		}
		
		if (!warn.equals("")) {
			JOptionPane.showMessageDialog(new JFrame(), warn,"警告", JOptionPane.WARNING_MESSAGE);
			SettingDialog sd = new SettingDialog(new JFrame());
			sd.setVisible(true);
		}
	}
}
