package com.mr.replay.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import com.mr.replay.ui.bean.ReadFile;
import com.mr.replay.ui.bean.kpiBean;
import com.mr.replay.ui.frame.KPIMain;
import com.mr.replay.ui.helper.JarHelper;
import com.mr.replay.ui.log.Log;
import com.mr.replay.ui.xml.XmlInsert;
import com.mr.replay.ui.xml.XmlReader;
import com.mr.replay.ui.xml.XmlWriterl;

public class kpiListener implements ActionListener{

	public kpiListener() {
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
	public String getSelectdkpiFolderPop(){
		StringBuffer stringBuffer = new StringBuffer();
		int rowCnt = KPIMain.kpiTable.getRowCount();
		for (int i = 0; i < rowCnt; i++) {
			boolean select = Boolean.parseBoolean(KPIMain.kpiTable.getValueAt(i, 1).toString());
			if (select) {
				stringBuffer.append("删除文件夹："+KPIMain.kpiTable.getValueAt(i, 4).toString().replaceAll(".py", "")+"\n");
			}
		}
		Log.info(stringBuffer.toString());
		return stringBuffer.toString();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		final String cmd = e.getActionCommand();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				ArrayList<String> randomidArrayList = new ArrayList<String>();
				String xml = JarHelper.getProjectPath()+"config"+File.separator+"testxml.xml";
				
				if (cmd.equals("dataDel")){
					ArrayList<Integer> rowArrayList =  getSelectdkpiRows();
					if (rowArrayList.size()>=1) {
						String popStr = getSelectdkpiFolderPop();
						if(JOptionPane.showConfirmDialog(new JFrame(),"确定删除选中的"+rowArrayList.size()+"项数据?\n"+popStr+"\n包括视频,图片,分析结果,请慎重... ", 
								"Warning",JOptionPane.YES_NO_OPTION) == 0){
							String jarpath = JarHelper.getProjectPath()+"video\\";
							for (Integer i : rowArrayList) {
								String random = KPIMain.kpiTable.getValueAt(i, 3).toString();
								String src = KPIMain.kpiTable.getValueAt(i, 2).toString();
								String path = src+File.separator+random;
								
								String ppf = jarpath+path+"\\";
								try {
									ReadFile.deletefile(ppf);
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								new File(ppf).mkdirs();
								
							}
							KPIMain.videosListModel.removeAllElements();
							KPIMain.videosListModel.addElement("无数据...");
							KPIMain.list.repaint();
							KPIMain.constructTable();
							
							JOptionPane.showMessageDialog(new JFrame(), "成功删除资源文件!\n"+popStr,"提示", JOptionPane.INFORMATION_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(new JFrame(), "没有选中数据!","Warning", JOptionPane.WARNING_MESSAGE);
					}
				}
				if (cmd.equals("scrpitsDel")){
					
					int rowcnt =  KPIMain.kpiTable.getRowCount();
					
					Hashtable<String, Object> isselecthashtable = new Hashtable<String,Object>();
					for (int i = 0; i < rowcnt; i++) {
						Boolean selected = Boolean.parseBoolean(KPIMain.kpiTbModel.getValueAt(i, 1).toString());
						if (selected) {
							String random = KPIMain.kpiTable.getValueAt(i, 3).toString();
							Object  obj= KPIMain.kpiTbModel.content.get(i);
							isselecthashtable.put(random, obj);
						}
					}
					if (isselecthashtable.size()>=1) {
						String pop = getSelectdkpiRowsPop();
						if(JOptionPane.showConfirmDialog(new JFrame(),"确定删除选中的"+isselecthashtable.size()+"个脚本?\n"+pop, 
								"Warning",JOptionPane.YES_NO_OPTION) == 0){
							
							KPIMain.bar.setIndeterminate(true);
							KPIMain.runLabel.setText("删除中...");
							
							for(Iterator itr = isselecthashtable.keySet().iterator(); itr.hasNext();){
								String folder = (String) itr.next();
								Object object =  isselecthashtable.get(folder);
								
								String random = folder;
								
								KPIMain.kpiTbModel.content.removeElement(object);
								KPIMain.kpiTable.updateUI();
								
								KPIMain.endTextField.setText("");
								KPIMain.starTextField.setText("");
								KPIMain.videosListModel.removeAllElements();
								KPIMain.list.updateUI();
								
								Vector<kpiBean> A = null;
								XmlReader my = new XmlReader();
								A = my.toRead(xml);
								for (int i = 0; i < A.size(); i++) {
									if (A.get(i).getRandomid().equals(random)) {
										A.remove(i);
									}
								}
								try{
									XmlWriterl myxml=new XmlWriterl(xml);
									System.out.println("start swrite xml file:"+xml);
									myxml.toWrite(A);
									myxml.toSave();
									System.out.print("Your writing is successful.");
								}
								catch(ParserConfigurationException exp){
									exp.printStackTrace();
									System.out.print("Your writing is failed.");
								}
								KPIMain.constructTable();
							}
							KPIMain.bar.setIndeterminate(false);
							KPIMain.runLabel.setText("删除完成...");
							JOptionPane.showMessageDialog(new JFrame(), "成功删除选中的"+isselecthashtable.size()+"个脚本!\n"+pop,"提示", JOptionPane.INFORMATION_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(new JFrame(), "没有选中脚本!","提示", JOptionPane.INFORMATION_MESSAGE);
					}
					
					/*if (index==-1) {
						if(JOptionPane.showConfirmDialog(new JFrame(),"确定删除选中的数据? ", 
								"Warning",JOptionPane.YES_NO_OPTION) == 0){
							String random = KPIMain.kpiTable.getValueAt(index, 3).toString();
							
							KPIMain.kpiTbModel.content.remove(index);
							KPIMain.kpiTable.updateUI();
							
							KPIMain.endTextField.setText("");
							KPIMain.starTextField.setText("");
							KPIMain.videosListModel.removeAllElements();
							KPIMain.list.updateUI();
							
							Vector<kpiBean> A = null;
							XmlReader my = new XmlReader();
							A = my.toRead(Constant.writexml);
							for (int i = 0; i < A.size(); i++) {
								if (A.get(i).getRandomid().equals(random)) {
									A.remove(i);
								}
							}
							try{
								XmlWriterl myxml=new XmlWriterl(Constant.writexml);
								System.out.println("start swrite xml file:"+Constant.writexml);
								myxml.toWrite(A);
								myxml.toSave();
								System.out.print("Your writing is successful.");
							}
							catch(ParserConfigurationException exp){
								exp.printStackTrace();
								System.out.print("Your writing is failed.");
							}
							KPIMain.constructTable();
						}
						
					}else {
						JOptionPane.showMessageDialog(new JFrame(), "没有选中数据!","Warning", JOptionPane.WARNING_MESSAGE);
					}*/
				}
				if (cmd.equals("scrpitsAdd")){
					String path = JarHelper.getJarProjectPath() + "\\scripts";
					File f = new File(path);
					if (!f.exists()) {
						f.mkdirs();
					}
					JFileChooser c = new JFileChooser(new File(JarHelper.getJarProjectPath() + "\\scripts"));
					c.setMultiSelectionEnabled(true);
					c.setFileSelectionMode(JFileChooser.FILES_ONLY);
					c.removeChoosableFileFilter(c.getAcceptAllFileFilter());
					c.setDialogTitle("请选择测试脚本");
					FileNameExtensionFilter py = new FileNameExtensionFilter("Python(*.py)", "py");
					c.setFileFilter(py);
					int result = c.showOpenDialog(new JFrame());
					if (result == JFileChooser.APPROVE_OPTION) {
						File[] files = c.getSelectedFiles();
						
						ArrayList<File> afiFiles = new ArrayList<File>();
						
						ArrayList<String> mutiname = new ArrayList<String>();
						String poprepeat ="";
						String poponly ="";
						String pop="";
						for (File file : files) {
							String fname = file.getName();
							int rows = KPIMain.kpiTable.getRowCount();
							boolean repeat=false;
							for (int i = 0; i < rows; i++) {
								String spt = KPIMain.kpiTable.getValueAt(i, 4).toString();
								File sptfFile = new File(spt);
								String tname = sptfFile.getName();
								if (fname.equals(tname)) {
									mutiname.add(file.getAbsolutePath());
									
									poprepeat = poprepeat +file.getAbsolutePath()+"\n";
								
									repeat = true;
								}
							}
							if (repeat) {
								
							}else{
								afiFiles.add(file);
								poponly =poponly+ file.getAbsolutePath()+"\n";
							}
						}
						if (mutiname.size()>=1) {
							pop =poprepeat+"重复的脚本不能添加....\n"+poponly+"可以添加,确定添加吗?\n";
						}else{
							pop ="确定添加脚本?\n"+poponly;
						}
						if (afiFiles.size()>=1) {
							int retcode = JOptionPane.showConfirmDialog(new JFrame(), pop,"询问", JOptionPane.YES_NO_OPTION);
							if (retcode==0) {
								try{
									KPIMain.bar.setIndeterminate(true);
									KPIMain.runLabel.setText("添加中...");
									
									XmlInsert myxml=new XmlInsert(xml, afiFiles);
									System.out.println("start swrite xml file:"+xml);
									Vector<kpiBean> addVector = myxml.toWrite();
									myxml.toSave();
									KPIMain.AddKpiTable(addVector);
									System.out.print("Your writing is successful.");
									
									KPIMain.bar.setIndeterminate(false);
									KPIMain.runLabel.setText("添加完成...");
									JOptionPane.showMessageDialog(new JFrame(), poponly+"添加成功!","提示", JOptionPane.INFORMATION_MESSAGE);
								}
								catch(ParserConfigurationException exp){
									exp.printStackTrace();
									System.out.print("Your writing is failed.");
								}
							}
						}else {
							JOptionPane.showMessageDialog(new JFrame(), "不能添加重复脚本名!\n"+poprepeat,"提示", JOptionPane.INFORMATION_MESSAGE);
						}
						
						/*if (mutiname.size()>=1) {
							JOptionPane.showMessageDialog(new JFrame(), "不能添加重复脚本名!\n"+mutiname.toString(),"Warning", JOptionPane.WARNING_MESSAGE);
						}else {
							try{
								XmlInsert myxml=new XmlInsert(Constant.writexml, afiFiles);
								System.out.println("start swrite xml file:"+Constant.writexml);
								Vector<kpiBean> addVector = myxml.toWrite();
								myxml.toSave();
								KPIMain.AddKpiTable(addVector);
								System.out.print("Your writing is successful.");
							}
							catch(ParserConfigurationException exp){
								exp.printStackTrace();
								System.out.print("Your writing is failed.");
							}
						}*/
					}
				}
			}});
		t1.start();
	}
}
