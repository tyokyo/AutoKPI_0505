package com.mr.replay.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.mr.replay.thread.AnalyzeThread;
import com.mr.replay.thread.ParseVideoThread;
import com.mr.replay.thread.RunCaseThread;
import com.mr.replay.thread.RunMergeThread;
import com.mr.replay.thread.parseAndAnalyzeThread;
import com.mr.replay.ui.bean.ReadFile;
import com.mr.replay.ui.dialog.SettingDialog;
import com.mr.replay.ui.frame.KPIMain;
import com.mr.replay.ui.helper.ConfigHelper;
import com.mr.replay.ui.helper.JarHelper;
import com.mr.replay.ui.lock.Lock;
import com.mr.replay.ui.log.Log;
import com.mr.replay.ui.merge.CodeMerger;
import com.mr.replay.ui.merge.TextFile;
import com.mr.replay.ui.panel.PyReplayPanel;
import com.mr.replay.ui.report.AutokpiReport;


public class scriptListener implements ActionListener{
	public scriptListener(){

	}
	public ArrayList<Integer> getSelectdspRows(){
		ArrayList<Integer> sArrayList = new ArrayList<Integer>();
		int rowCnt = KPIMain.spTable.getRowCount();
		for (int i = 0; i < rowCnt; i++) {
			boolean select = Boolean.parseBoolean(KPIMain.spTable.getValueAt(i, 12).toString());
			if (select) {
				sArrayList.add(i);
			}
		}
		Log.info(sArrayList.toString());
		return sArrayList;
	}
	public ArrayList<Integer> getNARows(){
		ArrayList<Integer> sArrayList = new ArrayList<Integer>();
		int rowCnt = KPIMain.spTable.getRowCount();
		for (int i = 0; i < rowCnt; i++) {
			boolean select = Boolean.parseBoolean(KPIMain.spTable.getValueAt(i, 12).toString());
			String NAa = KPIMain.spTable.getValueAt(i, 11).toString();
			if (select) {
				if (NAa.equals("NA")) {
					sArrayList.add(i);
				}
			}
		}
		Log.info(sArrayList.toString());
		return sArrayList;
	}
	public ArrayList<Integer> getSelectdkpiRows(){
		ArrayList<Integer> sArrayList = new ArrayList<Integer>();
		int rowCnt = KPIMain.kpiTable.getRowCount();
		for (int i = 0; i < rowCnt; i++) {
			boolean select = Boolean.parseBoolean(KPIMain.kpiTable.getValueAt(i, 1).toString());
			if (select) {
				sArrayList.add(i);
			}
		}
		Log.info(sArrayList.toString());
		return sArrayList;
	}
	public String getSelectdspRowsPop(){
		StringBuffer stringBuffer = new StringBuffer();
		int rowCnt = KPIMain.spTable.getRowCount();
		int selectCnt = getSelectdspRows().size();
		if (selectCnt>=20) {
			stringBuffer.append(selectCnt+"项数据");
		}else {
			for (int i = 0; i < rowCnt; i++) {
				boolean select = Boolean.parseBoolean(KPIMain.spTable.getValueAt(i, 12).toString());
				if (select) {
					stringBuffer.append(KPIMain.spTable.getValueAt(i, 2).toString()+":");
					stringBuffer.append(KPIMain.spTable.getValueAt(i, 5).toString()+"\n");
				}
			}
		}
		//Log.info(stringBuffer.toString());
		return stringBuffer.toString();
	}
	public String getSelectdkpiRowsPop(){
		StringBuffer stringBuffer = new StringBuffer();
		int rowCnt = KPIMain.kpiTable.getRowCount();
		int selectCnt = getSelectdkpiRows().size();
		if (selectCnt>=20) {
			stringBuffer.append(selectCnt+"项数据");
		}else {
			for (int i = 0; i < rowCnt; i++) {
				boolean select = Boolean.parseBoolean(KPIMain.kpiTable.getValueAt(i, 1).toString());
				if (select) {
					stringBuffer.append(KPIMain.kpiTable.getValueAt(i, 3).toString()+":");
					stringBuffer.append(KPIMain.kpiTable.getValueAt(i, 4).toString()+"\n");
				}
			}
		}
		//Log.info(stringBuffer.toString());
		return stringBuffer.toString();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getActionCommand().equals("DeleteAnalyzeData")){
			ArrayList<Integer> rowArrayList = getSelectdspRows();
			if (rowArrayList.size()==0) {
				JOptionPane.showMessageDialog(new JFrame(), "没有选中!","提示", JOptionPane.INFORMATION_MESSAGE);
			}else {
				if(JOptionPane.showConfirmDialog(new JFrame(),"确定重置?\n"+getSelectdspRowsPop(), 
						"Warning",JOptionPane.YES_NO_OPTION) == 0){
					for (Integer i : rowArrayList) {
						String videoname = KPIMain.spTable.getValueAt(i, 5).toString().replaceAll("path=", "");
						File video = new File(videoname);
						if (video.exists()) {
							video.delete();
							Log.info("delete:"+videoname);
						}
						String videoFolder = videoname.replaceAll(".avi", "");
						try {
							ReadFile.deletefile(videoFolder);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					KPIMain.constructTable();
					JOptionPane.showMessageDialog(new JFrame(), "重置成功!","提示", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		if (e.getActionCommand().equals("AnalyzeData")){
			int row = KPIMain.spTable.getSelectedRow();
			if (row==-1) {
				JOptionPane.showMessageDialog(new JFrame(), "请选中一列数据!","提示", JOptionPane.INFORMATION_MESSAGE);
			}else {
				String startpoint = KPIMain.spTable.getValueAt(row, 9).toString();
				String endpoint = KPIMain.spTable.getValueAt(row, 10).toString();
				String video =  KPIMain.spTable.getValueAt(row, 5).toString();
				KPIMain.AnalyzeDialog(row,video,new JFrame(),startpoint,endpoint);
			}
		}
		if (e.getActionCommand().equals("replayMksingle")){
			int rowcnt = KPIMain.kpiTable.getRowCount();
			if (rowcnt==0) {
				JOptionPane.showMessageDialog(new JFrame(), "请添加脚本!","提示", JOptionPane.INFORMATION_MESSAGE);
			}else {
				int devicecnt = PyReplayPanel.cb_device.getItemCount();
				if(devicecnt==0){
					JOptionPane.showMessageDialog(new JFrame(), "测试设备异常，请检查！","提示", JOptionPane.INFORMATION_MESSAGE);
				}else if (Lock.replay) {
					JOptionPane.showMessageDialog(new JFrame(), "还有脚本正在执行，请稍等!", "提示",JOptionPane.INFORMATION_MESSAGE);
				}else if(Lock.record){
					JOptionPane.showMessageDialog(new JFrame(), "正在录制视频，请稍等!","提示", JOptionPane.INFORMATION_MESSAGE);
				}else if(Lock.parse==true){
					JOptionPane.showMessageDialog(new JFrame(), "正在进行视频转换为图片，请稍等!","提示", JOptionPane.INFORMATION_MESSAGE);
				}else if (Lock.analyze==true) {
					JOptionPane.showMessageDialog(new JFrame(), "正在进行视频图片分析，请稍等!","提示", JOptionPane.INFORMATION_MESSAGE);
				}else {
					String pyerror="";
					for (int i = 0; i < rowcnt; i++) {
						String py = KPIMain.kpiTable.getValueAt(i, 4).toString();
						if (!new File(py).exists()) {
							pyerror =pyerror+py+"文件不存在\n";
						}
					}
					int selectcnt=getSelectdkpiRows().size();
					if (selectcnt==0) {
						JOptionPane.showMessageDialog(new JFrame(), "请选择脚本!","警告", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (pyerror.equals("")) {
						if(JOptionPane.showConfirmDialog(new JFrame(),"确定合并选中的"+selectcnt+"个脚本再执行?\n"+getSelectdkpiRowsPop(), 
								"Warning",JOptionPane.YES_NO_OPTION) == 0){
							
							KPIMain.bar.setIndeterminate(true);

							List<Map<String, String>> list = new ArrayList<Map<String, String>>();
							for (int i = 0; i < rowcnt; i++) {
								Boolean isselect = Boolean.parseBoolean(KPIMain.kpiTable.getValueAt(i, 1).toString());
								if (isselect){
									Map<String, String> map = new HashMap<String, String>();
									String folder =  KPIMain.kpiTable.getValueAt(i, 2).toString();
									String srcfolder =  KPIMain.kpiTable.getValueAt(i, 3).toString();
									String videopath = "path="+JarHelper.getProjectPath()+"video"+File.separator+folder+File.separator+srcfolder+File.separator;
									String scriptpath =KPIMain.kpiTable.getValueAt(i, 4).toString();
									int repeat = Integer.parseInt(KPIMain.kpiTable.getValueAt(i, 6).toString())+1;

									System.out.println(videopath);
									System.out.println(scriptpath);
									System.out.println(repeat);

									map.put("videopath", videopath.replaceAll("\\\\", "/"));
									map.put("scriptpath",scriptpath);
									map.put("repeat", String.valueOf(repeat));

									list.add(map);
								}
							}
							CodeMerger cm = new CodeMerger(list);
							System.out.println(cm.make());
							String mergepath = JarHelper.getProjectPath()+"merge.py";
							TextFile.writeWhenever(mergepath, cm.make().toString());

							final String  mkpath = ConfigHelper.getMonkeyrunner();
							final String pypath = mergepath;
							final String videopath =JarHelper.getProjectPath()+KPIMain.spTable.getValueAt(0, 2).toString();
							final String device = PyReplayPanel.cb_device.getSelectedItem().toString();

							final btnListener bListener = new btnListener("replayMksingle");
							bListener.disable();
							
							Thread t1 = new Thread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									RunMergeThread rThread = new RunMergeThread(mkpath, pypath, device, videopath);
									Thread runthread = new Thread(rThread);
									runthread.start();
									try {
										runthread.join();
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									bListener.enable();
								}
							});
							t1.start();
						}
					}else {
						JOptionPane.showMessageDialog(new JFrame(), "测试脚本不存在，请重新添加脚本\n"+pyerror,"警告", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		}
		if (e.getActionCommand().equals("replayAll"))
		{
			String mpath = ConfigHelper.getMonkeyrunner();
			if (mpath == null) {
				int retcode = JOptionPane.showConfirmDialog(new JFrame(),"Monkeyrunner路径未设置，是否立即设置？", "询问",JOptionPane.YES_NO_OPTION);
				if (retcode == 0) {
					SettingDialog sd = new SettingDialog(new JFrame());
					sd.setVisible(true);
				}
				Lock.replay = false;
				return;
			} else {
				File mpf = new File(mpath);
				if (!mpf.exists()) {
					int retcode = JOptionPane.showConfirmDialog(new JFrame(),
							"monkeyrunner.bat不存在，是否立即设置？", "询问",
							JOptionPane.YES_NO_OPTION);
					if (retcode == 0) {
						SettingDialog sd = new SettingDialog(new JFrame());
						sd.setVisible(true);
					}
					Lock.replay = false;
					return;
				}
			}

			int devicecnt = PyReplayPanel.cb_device.getItemCount();
			if(Lock.replay){
				JOptionPane.showMessageDialog(new JFrame(), "还有脚本正在执行，请稍等!","提示", JOptionPane.INFORMATION_MESSAGE);
			}else if (devicecnt==0) {
				JOptionPane.showMessageDialog(new JFrame(), "测试设备异常，请检查！", "提示",JOptionPane.INFORMATION_MESSAGE);
			}else if (getSelectdspRows().size()<=0) {
				JOptionPane.showMessageDialog(new JFrame(), "请选择脚本！", "提示",JOptionPane.INFORMATION_MESSAGE);
			}else{
				if(JOptionPane.showConfirmDialog(new JFrame(),"确定执行选择的"+getSelectdspRows().size()+"个脚本?\n"+getSelectdspRowsPop(), 
						"Warning",JOptionPane.YES_NO_OPTION) == 0){

					final btnListener bListener = new btnListener("replayAll");
					bListener.disable();

					final ArrayList<Integer> rows = getSelectdspRows();
					final int rowcnt =  KPIMain.spTable.getRowCount();
					System.out.println(rowcnt);
					final String  mkpath = ConfigHelper.getMonkeyrunner();
					Thread t1 = new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							for (Integer i : rows) {
								KPIMain.spTable.setRowSelectionInterval(i, i);
								//KPIMain.spTable.setEnabled(false);

								String pypath = KPIMain.spTable.getValueAt(i, 3).toString();
								String videopath = KPIMain.spTable.getValueAt(i, 5).toString();

								PyReplayPanel.tf_video_name.setText(videopath);
								PyReplayPanel.tf_file_path.setText(pypath);

								String device = PyReplayPanel.cb_device.getSelectedItem().toString();

								Log.warn("...正在执行，请稍候...");
								Log.info("【"+mkpath+"】");
								Log.info("【"+pypath+"】");
								Log.info("【"+device+"】");
								Log.info("【"+videopath+"】");

								RunCaseThread rThread = new RunCaseThread(i, mkpath, pypath, device, videopath);
								Thread runthread = new Thread(rThread);
								runthread.start();
								try {
									runthread.join();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								//String videorcpath = videopath.replaceAll("path=", "");
								
							}
							
							/*for (int i = 0; i < rowcnt; i++) {

								KPIMain.spTable.setRowSelectionInterval(i, i);
								//KPIMain.spTable.setEnabled(false);

								String pypath = KPIMain.spTable.getValueAt(i, 3).toString();
								String videopath = KPIMain.spTable.getValueAt(i, 5).toString();

								PyReplayPanel.tf_video_name.setText(videopath);
								PyReplayPanel.tf_file_path.setText(pypath);

								String device = PyReplayPanel.cb_device.getSelectedItem().toString();

								Log.warn("...正在执行，请稍候...");
								Log.info("【"+mkpath+"】");
								Log.info("【"+pypath+"】");
								Log.info("【"+device+"】");
								Log.info("【"+videopath+"】");

								RunCaseThread rThread = new RunCaseThread(i, mkpath, pypath, device, videopath);
								Thread runthread = new Thread(rThread);
								runthread.start();
								try {
									runthread.join();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								String videorcpath = videopath.replaceAll("path=", "");
								if (new File(videorcpath).exists()) {
									KPIMain.spTable.setValueAt("执行完成", i, 7);
								}else {
									KPIMain.spTable.setValueAt("无视频", i, 7);
								}
							}*/
							bListener.enable();
							PyReplayPanel.tf_video_name.setText("");
							JOptionPane.showMessageDialog(new JFrame(), "回放完成!","提示", JOptionPane.INFORMATION_MESSAGE);
						} 

					});
					t1.start();
				}
			}
		}
		else if (e.getActionCommand().equals("parseAndAnalyze")) {
			String ffmpegpath = ConfigHelper.getffmpeg();
			if (ffmpegpath == null) {
				int retcode = JOptionPane.showConfirmDialog(new JFrame(),"ffmpeg路径未设置，是否立即设置？", "询问",JOptionPane.YES_NO_OPTION);
				if (retcode == 0) {
					SettingDialog sd = new SettingDialog(new JFrame());
					sd.setVisible(true);
				}
				Lock.parse = false;
				return;
			} else {
				File ffmpegf = new File(ffmpegpath);
				if (!ffmpegf.exists()) {
					int retcode = JOptionPane.showConfirmDialog(new JFrame(),
							"ffmpeg.exe不存在，是否立即设置？", "询问",
							JOptionPane.YES_NO_OPTION);
					if (retcode == 0) {
						SettingDialog sd = new SettingDialog(new JFrame());
						sd.setVisible(true);
					}
					Lock.parse = false;
					return;
				}
			}
			if(Lock.parse==true){
				JOptionPane.showMessageDialog(new JFrame(), "正在进行视频转换为图片，请稍等!","提示", JOptionPane.INFORMATION_MESSAGE);
			}else if (Lock.analyze==true) {
				JOptionPane.showMessageDialog(new JFrame(), "正在进行视频图片分析，请稍等!","提示", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				if(JOptionPane.showConfirmDialog(new JFrame(),"确定视频转换为图片并且分析图片?", 
						"Warning",JOptionPane.YES_NO_OPTION) == 0){
					final btnListener bListener = new btnListener("parseAndAnalyze");
					bListener.disable();

					final int rowcnt =  KPIMain.spTable.getRowCount();
					System.out.println(rowcnt);
					final int frameRate = Integer.parseInt(ConfigHelper.getfps());
					Thread t1 = new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							for (int j = 0; j < rowcnt; j++) {
								KPIMain.spTable.setRowSelectionInterval(j, j);
								String videopath = KPIMain.spTableModel.getValueAt(j, 5).toString();
								String vpath = videopath.replaceAll("path=", "");
								int adjust  = Integer.parseInt(KPIMain.spTableModel.getValueAt(j, 6).toString());
								if (new File(vpath).exists()) {
									PyReplayPanel.tf_video_name.setText(videopath);
									parseAndAnalyzeThread rThread = new parseAndAnalyzeThread(vpath, frameRate, adjust,j);
									Thread runthread = new Thread(rThread);
									runthread.start();
									try {
										runthread.join();
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}else {
									KPIMain.spTableModel.setValueAt("转换失败", j, 8);
									Log.err("视频文件:"+videopath+" 不存在");
								}
							}
							bListener.enable();
							JOptionPane.showMessageDialog(null, "完成视频转换和分析!","提示", JOptionPane.INFORMATION_MESSAGE);
						}
					});
					t1.start();
				}
			}
		}
		else if (e.getActionCommand().equals("videoParsetoImage")) {

			String ffmpegpath = ConfigHelper.getffmpeg();
			if (ffmpegpath == null) {
				int retcode = JOptionPane.showConfirmDialog(new JFrame(),"ffmpeg路径未设置，是否立即设置？", "询问",JOptionPane.YES_NO_OPTION);
				if (retcode == 0) {
					SettingDialog sd = new SettingDialog(new JFrame());
					sd.setVisible(true);
				}
				Lock.parse = false;
				return;
			} else {
				File ffmpegf = new File(ffmpegpath);
				if (!ffmpegf.exists()) {
					int retcode = JOptionPane.showConfirmDialog(new JFrame(),
							"ffmpeg.exe不存在，是否立即设置？", "询问",
							JOptionPane.YES_NO_OPTION);
					if (retcode == 0) {
						SettingDialog sd = new SettingDialog(new JFrame());
						sd.setVisible(true);
					}
					Lock.parse = false;
					return;
				}
			}
			
			final ArrayList<Integer> selectedArrayList = getSelectdspRows();
			final int slectrowcnt =  selectedArrayList.size();
			System.out.println(slectrowcnt);
			
			if(Lock.parse==true){
				JOptionPane.showMessageDialog(new JFrame(), "正在进行视频转换为图片，请稍等!","提示", JOptionPane.INFORMATION_MESSAGE);
			}else if (slectrowcnt==0) {
				JOptionPane.showMessageDialog(new JFrame(), "请选择数据!","提示", JOptionPane.INFORMATION_MESSAGE);
			}else {
				if(JOptionPane.showConfirmDialog(new JFrame(),"确定视频转换为图片?\n"+getSelectdspRowsPop(), 
						"Warning",JOptionPane.YES_NO_OPTION) == 0){
					final btnListener bListener = new btnListener("videoParsetoImage");
					bListener.disable();
					
					final int frameRate = Integer.parseInt(ConfigHelper.getfps());
					Thread t1 = new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							/*for (int j = 0; j < rowcnt; j++) {
								KPIMain.spTable.setRowSelectionInterval(j, j);
								String videopath = KPIMain.spTableModel.getValueAt(j, 5).toString();
								String vpath = videopath.replaceAll("path=", "");
								int adjust  = Integer.parseInt(KPIMain.spTableModel.getValueAt(j, 6).toString());
								if (new File(vpath).exists()) {
									PyReplayPanel.tf_video_name.setText(videopath);
									ParseVideoThread rThread = new ParseVideoThread(vpath, frameRate, adjust);
									Thread runthread = new Thread(rThread);
									runthread.start();
									try {
										runthread.join();
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									KPIMain.spTableModel.setValueAt("SUCCESS", j, 8);
								}else {
									KPIMain.spTableModel.setValueAt("FAIL", j, 8);
									Log.err("视频文件:"+videopath+" 不存在");
								}
							}
							bListener.enable();
							JOptionPane.showMessageDialog(null, "视频转换完成!","提示", JOptionPane.INFORMATION_MESSAGE);
							*/
							
							for (Integer j : selectedArrayList) {
								KPIMain.spTable.setRowSelectionInterval(j, j);
								String videopath = KPIMain.spTableModel.getValueAt(j, 5).toString();
								String vpath = videopath.replaceAll("path=", "");
								int adjust  = Integer.parseInt(KPIMain.spTableModel.getValueAt(j, 6).toString());
								if (new File(vpath).exists()) {
									PyReplayPanel.tf_video_name.setText(videopath);
									ParseVideoThread rThread = new ParseVideoThread(j,vpath, frameRate, adjust);
									Thread runthread = new Thread(rThread);
									runthread.start();
									try {
										runthread.join();
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}else {
									KPIMain.spTableModel.setValueAt("FAIL", j, 8);
									Log.err("视频文件:"+videopath+" 不存在");
								}
							}
							bListener.enable();
							JOptionPane.showMessageDialog(null, "视频转换完成!","提示", JOptionPane.INFORMATION_MESSAGE);
						}
					});
					t1.start();
				}
			}
		}else if (e.getActionCommand().equals("report")) {
			ArrayList<Integer> selectList = getSelectdspRows();
			ArrayList<Integer> naList = getNARows();
			final int rowcnt = KPIMain.spTable.getRowCount();
			boolean analyzed=false;
			for (int i = 0; i < rowcnt; i++) {
				String time = KPIMain.spTable.getValueAt(i, 11).toString();
				if (time.equals("NA")) {
					analyzed =true;
				}
			}
			
			if(Lock.report==true){
				JOptionPane.showMessageDialog(new JFrame(), "正在生成测试报告，请稍等!","提示", JOptionPane.INFORMATION_MESSAGE);
			}else if (naList.size()>=1) {
				JOptionPane.showMessageDialog(new JFrame(), "存在未分析项(结果==NA)","提示", JOptionPane.INFORMATION_MESSAGE);
			}else {
				if (selectList.size()>=1) {
					if(JOptionPane.showConfirmDialog(new JFrame(),"确定生成测试报告?", 
							"Warning",JOptionPane.YES_NO_OPTION) == 0){

						
						Thread t1 = new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								List<Map<String, String>> source = new ArrayList<Map<String, String>>();

								for (int i = 0; i < rowcnt; i++) {
									String summary = KPIMain.spTable.getValueAt(i, 4).toString();

									//String pyname = new File(pypath).getName().replaceAll(".avi", "");
									String time = KPIMain.spTable.getValueAt(i, 11).toString();

									Map m1 = new HashMap<String, String>();
									m1.put("name", summary); //脚本
									m1.put("result",time); //响应时间，毫秒
									source.add(m1);
								}

								AutokpiReport r = new AutokpiReport(source);
								r.debugOutput(r.output);
								r.generate();
								
								Lock.report=false;
							}
						});
						t1.start();
						
					}else {

					}
				}
			}
		}else if (e.getActionCommand().equals("AnalyzeImage")) {
			final ArrayList<Integer> slIntegers = getSelectdspRows();
			if(Lock.analyze==true){
				JOptionPane.showMessageDialog(new JFrame(), "正在进行视频图片分析，请稍等!","提示", JOptionPane.INFORMATION_MESSAGE);
			}else if (slIntegers.size()<=0) {
				JOptionPane.showMessageDialog(new JFrame(), "请选择数据!","提示", JOptionPane.INFORMATION_MESSAGE);
			}else {
				if(JOptionPane.showConfirmDialog(new JFrame(),"确定分析图片?\n"+getSelectdspRowsPop(), 
						"Warning",JOptionPane.YES_NO_OPTION) == 0){

					final btnListener bListener = new btnListener("AnalyzeImage");
					bListener.disable();

					//final int rowcnt =  KPIMain.spTable.getRowCount();
					
					Thread t1 = new Thread(new Runnable() {
						@SuppressWarnings("static-access")
						@Override
						public void run() {
							// TODO Auto-generated method stub
							for (Integer selected : slIntegers) {
								KPIMain.spTable.setRowSelectionInterval(selected, selected);

								String videopath = KPIMain.spTable.getValueAt(selected, 5).toString();
								String analyzefolder = videopath.replaceAll("path=", "").replaceAll(".avi", "");
								if (new File(analyzefolder).exists()) {

									PyReplayPanel.tf_video_name.setText(videopath);
									AnalyzeThread rThread = new AnalyzeThread(selected,analyzefolder);
									Thread runthread = new Thread(rThread);
									runthread.start();
									try {
										runthread.join();
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									try {
										Thread.currentThread().sleep(3000);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									//KPIMain.spTableModel.setValueAt("分析成功", j, 9);
								}else {
									KPIMain.spTableModel.setValueAt("FAIL", selected, 9);
									Log.err("图片文件夹:"+analyzefolder+" 不存在");
								}
							}
							/*for (int j = 0; j < rowcnt; j++) {

								KPIMain.spTable.setRowSelectionInterval(j, j);

								String videopath = KPIMain.spTable.getValueAt(j, 5).toString();
								String analyzefolder = videopath.replaceAll("path=", "").replaceAll(".avi", "");
								if (new File(analyzefolder).exists()) {

									PyReplayPanel.tf_video_name.setText(videopath);
									AnalyzeThread rThread = new AnalyzeThread(j,analyzefolder);
									Thread runthread = new Thread(rThread);
									runthread.start();
									try {
										runthread.join();
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									try {
										Thread.currentThread().sleep(3000);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									//KPIMain.spTableModel.setValueAt("分析成功", j, 9);
								}else {
									KPIMain.spTableModel.setValueAt("FAIL", j, 9);
									Log.err("图片文件夹:"+analyzefolder+" 不存在");
								}
							}*/
							bListener.enable();
							PyReplayPanel.tf_video_name.setText("");
							Lock.analyze=false;
							JOptionPane.showMessageDialog(new JFrame(), "分析完成!","提示", JOptionPane.INFORMATION_MESSAGE);
						}
					});
					t1.start();

					/*Log.info("开始计算结束点图片...");
					String dir = "E:\\AutoKPI\\video\\20140308175424\\mk\\001";
					Log.info("开始计算："+dir);
					int endlocator1 = EndLocator.findlast_sample(dir, cvRect(152, 73, 234, 387));
					System.out.println(endlocator1);
					Log.info("开始计算："+dir);
					int endlocator2 = EndLocator.findlast_sample(dir, cvRect(152, 73, 234, 387));
					System.out.println(endlocator2);
					Log.info("计算结束点图片结束...");*/
				}
			}
		}else {

		}
	}
}