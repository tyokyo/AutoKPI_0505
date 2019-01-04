package com.mr.replay.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.mr.replay.ui.bean.BeanHelper;
import com.mr.replay.ui.frame.KPIMain;
import com.mr.replay.ui.helper.JarHelper;
import com.mr.replay.ui.helper.ServiceHelper;
import com.mr.replay.ui.kpireport.HtmlReport;
import com.mr.replay.ui.kpireport.ListBean;
import com.mr.replay.ui.kpireport.rptBean;
import com.mr.replay.ui.lock.Lock;
import com.mr.replay.ui.log.Log;

public class ReportListener implements ActionListener{
	public ReportListener(){

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
		Log.info("NA Rows:"+sArrayList.toString());
		return sArrayList;
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
		//Log.info(sArrayList.toString());
		return sArrayList;
	}
	public int getMaxIteration(){
		ArrayList<Integer> selectList = getSelectdkpiRows();
		ArrayList<Integer> sArrayList = new ArrayList<Integer>();
		for (int i = 0; i < selectList.size(); i++) {
			int interation=Integer.parseInt(KPIMain.kpiTable.getValueAt(i, 6).toString());
			sArrayList.add(interation);
		}
		Collections.sort(sArrayList);
		int maxint = sArrayList.get(sArrayList.size()-1);
		Log.info("max interation is :"+maxint);
		return maxint;
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
		//Log.info(sArrayList.toString());
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
		if (e.getActionCommand().equals("report")) {
			final ArrayList<Integer> selectRowList = getSelectdspRows();
			final ArrayList<Integer> kpiRowList = getSelectdkpiRows();
			ArrayList<Integer> naList = getNARows();

			/*boolean analyzed=false;
			for (int i = 0; i < selectRowList.size(); i++) {
				String time = KPIMain.spTable.getValueAt(i, 11).toString();
				if (time.equals("NA")) {
					analyzed =true;
				}
			}*/

			if(Lock.report==true){
				JOptionPane.showMessageDialog(new JFrame(), "正在生成测试报告，请稍等!","提示", JOptionPane.INFORMATION_MESSAGE);
			}else if (naList.size()>=1) {
				JOptionPane.showMessageDialog(new JFrame(), "存在未分析项(结果==NA)","提示", JOptionPane.INFORMATION_MESSAGE);
			}else {
				if (selectRowList.size()>=1) {
					if(JOptionPane.showConfirmDialog(new JFrame(),"确定生成测试报告?", 
							"Warning",JOptionPane.YES_NO_OPTION) == 0){

						Thread t1 = new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								List<rptBean> source = new ArrayList<rptBean>();
								int maxint = getMaxIteration();
								String sWversion= ServiceHelper.getSWVersion();
								for (Integer i : selectRowList) {
									rptBean bean = new rptBean();

									String testdomain=KPIMain.spTable.getValueAt(i, 4).toString();;
									int kpivalue=Integer.parseInt(KPIMain.spTable.getValueAt(i, 13).toString());
									String summary=KPIMain.spTable.getValueAt(i, 4).toString();;
									String video = KPIMain.spTable.getValueAt(i, 5).toString();
									String pyname =KPIMain.spTable.getValueAt(i, 3).toString();
									int value =Integer.parseInt(KPIMain.spTable.getValueAt(i, 11).toString());

									bean.setKpivalue(kpivalue);
									bean.setPysp(pyname);
									bean.setSummary(summary);
									bean.setTestdomain(testdomain);
									bean.setValue(value);
									bean.setVideo(video);

									//bean.toString();

									source.add(bean);
								}

								List<ListBean> kpiList = new ArrayList<ListBean>();
								for (Integer i : kpiRowList) {
									ListBean lBean = new ListBean();

									String testdomain=KPIMain.kpiTable.getValueAt(i, 5).toString();;
									int standardAvg=Integer.parseInt(KPIMain.kpiTable.getValueAt(i, 8).toString());
									int standardMax=Integer.parseInt(KPIMain.kpiTable.getValueAt(i, 9).toString());
									String summary=KPIMain.kpiTable.getValueAt(i, 10).toString();;
									String pyname =KPIMain.kpiTable.getValueAt(i, 4).toString();
									ArrayList<Integer> valueList = new ArrayList<Integer>();
									lBean.setTestdomain(testdomain);
									lBean.setStandardAvg(standardAvg);
									lBean.setStandardMax(standardMax);
									lBean.setPysp(pyname);
									lBean.setSummary(summary);
									for (rptBean rpBean : source) {
										if (rpBean.getPysp().equals(lBean.getPysp())) {
											valueList.add(rpBean.getValue());
										}
									}
									/*取消排序*/
									//Collections.sort(valueList);
									
									lBean.setValueList(valueList);

									//lBean.toString();
									kpiList.add(lBean);
								}
								String content = HtmlReport.GenerateReport(sWversion, kpiList, maxint);
								//System.out.println(content);
								String folder = JarHelper.getProjectPath()+"report\\";
								if (!new File(folder).exists()) {
									new File(folder).mkdirs();
								}
								String reportName = String.format("kpi_%s.html",System.currentTimeMillis());
								String outputpath = JarHelper.getProjectPath()+"report\\" + reportName;
								Log.info(outputpath);
								ServiceHelper.writeFile(outputpath, content);

								JOptionPane.showMessageDialog(new JFrame(), "测试报告位置!\n"+outputpath,"提示", JOptionPane.INFORMATION_MESSAGE);
								BeanHelper.openResult(outputpath);

								Lock.report=false;
							}
						});
						t1.start();
					}
				}else {
					JOptionPane.showMessageDialog(new JFrame(), "无数据,请选择！","提示", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
}

