package com.mr.replay.ui.listener;

import com.mr.replay.ui.frame.KPIMain;
import com.mr.replay.ui.panel.PyReplayPanel;


public class btnListener {
	private String btnname;
	public btnListener(String btnname){
		System.out.println(btnname);
		this.btnname = btnname;
	}
	public void RadioDisableAll(){
		
		//KPIMain.kpiTable.getModel().removeTableModelListener(null);
		//KPIMain.kpiTable.setEnabled(false);
		KPIMain.editSelect.setSelected(false);
		KPIMain.editSummary.setSelected(false);
		KPIMain.editAdjust.setSelected(false);
		KPIMain.editInteration.setSelected(false);
		KPIMain.editKpi.setSelected(false);
		KPIMain.editStandard.setSelected(false);
		
		KPIMain.editStandard.setEnabled(false);
		KPIMain.editSelect.setEnabled(false);
		KPIMain.editSummary.setEnabled(false);
		KPIMain.editAdjust.setEnabled(false);
		KPIMain.editInteration.setEnabled(false);
		KPIMain.editKpi.setEnabled(false);
		
		KPIMain.selectAll.setEnabled(false);
		
	}
	public void RadioEnableeAll(){
		
		//KPIMain.kpiTableListener = new kpiTableModeListener(KPIMain.kpiTable);
		//KPIMain.kpiTable.getModel().addTableModelListener(KPIMain.kpiTableListener);
		//KPIMain.kpiTable.setEnabled(true);
		
		KPIMain.editSelect.setSelected(false);
		KPIMain.editSummary.setSelected(false);
		KPIMain.editAdjust.setSelected(false);
		KPIMain.editInteration.setSelected(false);
		KPIMain.editKpi.setSelected(false);
		KPIMain.editStandard.setSelected(false);
		
		KPIMain.editSelect.setEnabled(true);
		KPIMain.editSummary.setEnabled(true);
		KPIMain.editAdjust.setEnabled(true);
		KPIMain.editInteration.setEnabled(true);
		KPIMain.editKpi.setEnabled(true);
		KPIMain.editStandard.setEnabled(true);
		
		KPIMain.selectAll.setEnabled(true);
	}
	public void disable(){
		RadioDisableAll();
		if (btnname.equals("AnalyzeData")) {
			KPIMain.scrpitsAdd.setEnabled(false);
			KPIMain.scrpitsDel.setEnabled(false);
			KPIMain.report.setEnabled(false);
			KPIMain.recoder.setEnabled(false);
			KPIMain.replay.setEnabled(false);
			KPIMain.replayAll.setEnabled(false);
			KPIMain.AnalyzeImage.setEnabled(false);
			KPIMain.record_start.setEnabled(false);
			KPIMain.record_stop.setEnabled(false);
			KPIMain.videoParsetoImage.setEnabled(false);
			PyReplayPanel.tf_video_name.setEnabled(false);
			PyReplayPanel.tf_file_path.setEnabled(false);
			
			KPIMain.DeleteAnalyzeData.setEnabled(false);
		}
		if (btnname.equals("replayMksingle")) {
			KPIMain.scrpitsAdd.setEnabled(false);
			KPIMain.scrpitsDel.setEnabled(false);
			KPIMain.report.setEnabled(false);
			KPIMain.recoder.setEnabled(false);
			KPIMain.replay.setEnabled(false);
			KPIMain.replayAll.setEnabled(false);
			KPIMain.AnalyzeImage.setEnabled(false);
			KPIMain.record_start.setEnabled(false);
			KPIMain.record_stop.setEnabled(false);
			KPIMain.videoParsetoImage.setEnabled(false);
			
			KPIMain.AnalyzeData.setEnabled(false);
			PyReplayPanel.tf_video_name.setEnabled(false);
			PyReplayPanel.tf_file_path.setEnabled(false);
			
			KPIMain.DeleteAnalyzeData.setEnabled(false);
		}
		if (btnname.equals("parseAndAnalyze")) {
			KPIMain.scrpitsAdd.setEnabled(false);
			KPIMain.scrpitsDel.setEnabled(false);
			KPIMain.report.setEnabled(false);
			KPIMain.recoder.setEnabled(false);
			KPIMain.replay.setEnabled(false);
			KPIMain.replayAll.setEnabled(false);
			KPIMain.AnalyzeImage.setEnabled(false);
			KPIMain.record_start.setEnabled(false);
			KPIMain.record_stop.setEnabled(false);
			KPIMain.videoParsetoImage.setEnabled(false);
			
			KPIMain.AnalyzeData.setEnabled(false);
			PyReplayPanel.tf_video_name.setEnabled(false);
			PyReplayPanel.tf_file_path.setEnabled(false);
			
			KPIMain.DeleteAnalyzeData.setEnabled(false);
		}
		if (btnname.equals("videoParsetoImage")) {
			KPIMain.scrpitsAdd.setEnabled(false);
			KPIMain.scrpitsDel.setEnabled(false);
			KPIMain.report.setEnabled(false);
			KPIMain.recoder.setEnabled(false);
			KPIMain.replay.setEnabled(false);
			KPIMain.replayAll.setEnabled(false);
			KPIMain.AnalyzeImage.setEnabled(false);
			KPIMain.record_start.setEnabled(false);
			KPIMain.record_stop.setEnabled(false);
			
			KPIMain.AnalyzeData.setEnabled(false);
			PyReplayPanel.tf_video_name.setEnabled(false);
			PyReplayPanel.tf_file_path.setEnabled(false);
			
			KPIMain.DeleteAnalyzeData.setEnabled(false);
			//KPIMain.reconnect.setEnabled(false);
		}
		if (btnname.equals("replay")||btnname.equals("replayAll")) {
			System.out.println("disable");
			KPIMain.videoParsetoImage.setEnabled(false);
			KPIMain.scrpitsAdd.setEnabled(false);
			KPIMain.scrpitsDel.setEnabled(false);
			KPIMain.report.setEnabled(false);
			KPIMain.recoder.setEnabled(false);
			KPIMain.AnalyzeImage.setEnabled(false);
			KPIMain.record_start.setEnabled(false);
			KPIMain.record_stop.setEnabled(false);
			
			KPIMain.AnalyzeData.setEnabled(false);
			PyReplayPanel.tf_video_name.setEnabled(false);
			PyReplayPanel.tf_file_path.setEnabled(false);
			
			KPIMain.DeleteAnalyzeData.setEnabled(false);
			
		}
		if (btnname.equals("AnalyzeImage")) {
			KPIMain.scrpitsAdd.setEnabled(false);
			KPIMain.scrpitsDel.setEnabled(false);
			KPIMain.report.setEnabled(false);
			KPIMain.recoder.setEnabled(false);
			KPIMain.replay.setEnabled(false);
			KPIMain.replayAll.setEnabled(false);
			KPIMain.videoParsetoImage.setEnabled(false);
			KPIMain.record_start.setEnabled(false);
			KPIMain.record_stop.setEnabled(false);
			
			KPIMain.AnalyzeData.setEnabled(false);
			PyReplayPanel.tf_video_name.setEnabled(false);
			PyReplayPanel.tf_file_path.setEnabled(false);
			
			KPIMain.DeleteAnalyzeData.setEnabled(false);
		}
	}
	public void enable(){
		RadioEnableeAll();
		if (btnname.equals("AnalyzeData")) {
			KPIMain.scrpitsAdd.setEnabled(true);
			KPIMain.scrpitsDel.setEnabled(true);
			KPIMain.report.setEnabled(true);
			KPIMain.recoder.setEnabled(true);
			KPIMain.replay.setEnabled(true);
			KPIMain.replayAll.setEnabled(true);
			KPIMain.AnalyzeImage.setEnabled(true);
			KPIMain.record_start.setEnabled(true);
			KPIMain.record_stop.setEnabled(true);
			KPIMain.videoParsetoImage.setEnabled(true);
			PyReplayPanel.tf_video_name.setEnabled(true);
			PyReplayPanel.tf_file_path.setEnabled(true);
			
			KPIMain.DeleteAnalyzeData.setEnabled(true);
		}
		
		if (btnname.equals("replayMksingle")) {
			KPIMain.scrpitsAdd.setEnabled(true);
			KPIMain.scrpitsDel.setEnabled(true);
			KPIMain.report.setEnabled(true);
			KPIMain.recoder.setEnabled(true);
			KPIMain.replay.setEnabled(true);
			KPIMain.replayAll.setEnabled(true);
			KPIMain.AnalyzeImage.setEnabled(true);
			KPIMain.record_start.setEnabled(true);
			KPIMain.record_stop.setEnabled(true);
			KPIMain.videoParsetoImage.setEnabled(true);
			KPIMain.AnalyzeData.setEnabled(true);
			PyReplayPanel.tf_video_name.setEnabled(true);
			PyReplayPanel.tf_file_path.setEnabled(true);
			
			KPIMain.DeleteAnalyzeData.setEnabled(true);
		}
		
		if (btnname.equals("parseAndAnalyze")) {
			KPIMain.scrpitsAdd.setEnabled(true);
			KPIMain.scrpitsDel.setEnabled(true);
			KPIMain.report.setEnabled(true);
			KPIMain.recoder.setEnabled(true);
			KPIMain.replay.setEnabled(true);
			KPIMain.replayAll.setEnabled(true);
			KPIMain.AnalyzeImage.setEnabled(true);
			KPIMain.record_start.setEnabled(true);
			KPIMain.record_stop.setEnabled(true);
			KPIMain.videoParsetoImage.setEnabled(true);
			KPIMain.AnalyzeData.setEnabled(true);
			PyReplayPanel.tf_video_name.setEnabled(true);
			PyReplayPanel.tf_file_path.setEnabled(true);
			
			KPIMain.DeleteAnalyzeData.setEnabled(true);
		}
		if (btnname.equals("videoParsetoImage")) {
			KPIMain.scrpitsAdd.setEnabled(true);
			KPIMain.scrpitsDel.setEnabled(true);
			KPIMain.report.setEnabled(true);
			KPIMain.recoder.setEnabled(true);
			KPIMain.replay.setEnabled(true);
			KPIMain.replayAll.setEnabled(true);
			KPIMain.AnalyzeImage.setEnabled(true);
			KPIMain.record_start.setEnabled(true);
			KPIMain.record_stop.setEnabled(true);
			KPIMain.AnalyzeData.setEnabled(true);
			PyReplayPanel.tf_video_name.setEnabled(true);
			PyReplayPanel.tf_file_path.setEnabled(true);
			
			KPIMain.DeleteAnalyzeData.setEnabled(true);
			//KPIMain.reconnect.setEnabled(true);
		}
		if (btnname.equals("replay")||btnname.equals("replayAll")) {
			System.out.println("enable");
			KPIMain.videoParsetoImage.setEnabled(true);
			KPIMain.scrpitsAdd.setEnabled(true);
			KPIMain.scrpitsDel.setEnabled(true);
			KPIMain.report.setEnabled(true);
			KPIMain.recoder.setEnabled(true);
			KPIMain.AnalyzeImage.setEnabled(true);
			KPIMain.record_start.setEnabled(true);
			KPIMain.record_stop.setEnabled(true);
			
			PyReplayPanel.tf_video_name.setEnabled(true);
			PyReplayPanel.tf_file_path.setEnabled(true);
			KPIMain.DeleteAnalyzeData.setEnabled(true);
		}
		if (btnname.equals("AnalyzeImage")) {
			KPIMain.scrpitsAdd.setEnabled(true);
			KPIMain.scrpitsDel.setEnabled(true);
			KPIMain.report.setEnabled(true);
			KPIMain.recoder.setEnabled(true);
			KPIMain.replay.setEnabled(true);
			KPIMain.replayAll.setEnabled(true);
			KPIMain.videoParsetoImage.setEnabled(true);
			KPIMain.record_start.setEnabled(true);
			KPIMain.record_stop.setEnabled(true);
			
			PyReplayPanel.tf_video_name.setEnabled(true);
			PyReplayPanel.tf_file_path.setEnabled(true);
			KPIMain.DeleteAnalyzeData.setEnabled(true);
		}
	}
	
}
/*public static JButton scrpitsAdd;
public static JButton scrpitsDel;
public static JButton report;
public static JButton replay_select;
public static JButton recoder;
public static JButton replay;
public static JButton replayAll;
public static JButton videoParsetoImage;
public static JButton AnalyzeImage;
public static Button record_start;
public static Button record_stop;
public static Button reconnect;*/