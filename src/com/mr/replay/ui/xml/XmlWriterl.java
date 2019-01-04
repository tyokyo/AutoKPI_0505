package com.mr.replay.ui.xml;
import org.w3c.dom.*;

import com.mr.replay.ui.bean.BeanHelper;
import com.mr.replay.ui.bean.Constant;
import com.mr.replay.ui.bean.kpiBean;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Vector;


public class XmlWriterl {
	private Document document;
	private String filename;


	public XmlWriterl(String name) throws ParserConfigurationException{
		System.out.println("Towrite:"+name);
		filename=name;
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder();
		document=builder.newDocument();

	}
	public void toWrite(Vector<kpiBean> mkVector)
	{
		Element root = document.createElement("testsuite");
		root.setAttribute("name", "uiautomator");
		root.setAttribute("version", BeanHelper.testSWVersion());
		document.appendChild(root);
		
		Element jarEmt = document.createElement("ffmpeg");
		root.appendChild(jarEmt);
		Element jarElement = document.createElement("path");
		jarEmt.appendChild(jarElement);
		Text jartouch = document.createTextNode("ffmpeg_path");
		jarElement.appendChild(jartouch);
		
		for(int i = 0;i<mkVector.size();i++)
		{
			kpiBean monkeyBean = (kpiBean) mkVector.get(i);
			String timeseconds = monkeyBean.getRandomid();
			
			Element mkEmt = document.createElement("testcase");
			root.appendChild(mkEmt);

			Element isSelectElement = document.createElement("isSelect");
			isSelectElement.setAttribute("id", timeseconds);
			mkEmt.appendChild(isSelectElement);
			Text isSelect = document.createTextNode(String.valueOf(monkeyBean.getSelected()));
			isSelectElement.appendChild(isSelect);
			
			
			Element folderElement = document.createElement("folder");
			folderElement.setAttribute("id", timeseconds);
			mkEmt.appendChild(folderElement);
			Text folder = document.createTextNode(String.valueOf(monkeyBean.getFolder()));
			folderElement.appendChild(folder);
			
			Element scriptElement = document.createElement("script");
			scriptElement.setAttribute("id", timeseconds);
			mkEmt.appendChild(scriptElement);
			Text script = document.createTextNode(String.valueOf(monkeyBean.getScript()));
			scriptElement.appendChild(script);
			
			
			Element summaryElement = document.createElement("summary");
			summaryElement.setAttribute("id", timeseconds);
			mkEmt.appendChild(summaryElement);
			Text summary = document.createTextNode(String.valueOf(monkeyBean.getSummary()));
			summaryElement.appendChild(summary);
			
			
			Element iterationElement = document.createElement("iteration");
			iterationElement.setAttribute("id", timeseconds);
			mkEmt.appendChild(iterationElement);
			Text iteration = document.createTextNode(String.valueOf(monkeyBean.getIteration()));
			iterationElement.appendChild(iteration);
			
			
			Element videoElement = document.createElement("video");
			videoElement.setAttribute("id", timeseconds);
			mkEmt.appendChild(videoElement);
			Text video = document.createTextNode(String.valueOf(monkeyBean.getVideo()));
			videoElement.appendChild(video);
			
			
			Element adjustElement = document.createElement("adjust");
			adjustElement.setAttribute("id", timeseconds);
			mkEmt.appendChild(adjustElement);
			Text adjust = document.createTextNode(String.valueOf(monkeyBean.getAdjust()));
			adjustElement.appendChild(adjust);
			
			
			Element startpicElement = document.createElement("startpic");
			startpicElement.setAttribute("id", timeseconds);
			mkEmt.appendChild(startpicElement);
			Text startpic = document.createTextNode(String.valueOf(monkeyBean.getStartpicpath()));
			startpicElement.appendChild(startpic);
			
			
			Element endpicElement = document.createElement("endpic");
			endpicElement.setAttribute("id", timeseconds);
			mkEmt.appendChild(endpicElement);
			Text endpic = document.createTextNode(String.valueOf(monkeyBean.getEndpicpath()));
			endpicElement.appendChild(endpic);
			
			
			
			Element randomidElement = document.createElement("randomid");
			randomidElement.setAttribute("id", timeseconds);
			mkEmt.appendChild(randomidElement);
			Text randomidText = document.createTextNode(timeseconds);
			randomidElement.appendChild(randomidText);
			
			
			Element remarkElement = document.createElement("remark");
			remarkElement.setAttribute("id", timeseconds);
			mkEmt.appendChild(remarkElement);
			Text remark = document.createTextNode(String.valueOf(monkeyBean.getRemark()));
			remarkElement.appendChild(remark);
			
			
			Element kpiAvgElement = document.createElement("kpiavg");
			kpiAvgElement.setAttribute("id", timeseconds);
			mkEmt.appendChild(kpiAvgElement);
			Text kpiAvg = document.createTextNode(String.valueOf(monkeyBean.getKpiavg()));
			kpiAvgElement.appendChild(kpiAvg);
			
			Element kpiMaxElement = document.createElement("kpimax");
			kpiMaxElement.setAttribute("id", timeseconds);
			mkEmt.appendChild(kpiMaxElement);
			Text kpiMax = document.createTextNode(String.valueOf(monkeyBean.getKpimax()));
			kpiMaxElement.appendChild(kpiMax);
			
		}
	}

	public void toSave(){
		try{
			TransformerFactory tf=TransformerFactory.newInstance();
			Transformer transformer=tf.newTransformer();
			DOMSource source=new DOMSource(document);
			//transformer.setOutputProperty(OutputKeys.ENCODING,"GBK");
			transformer.setOutputProperty(OutputKeys.INDENT,"yes");
			PrintWriter pw=new PrintWriter(new FileOutputStream(filename));
			StreamResult result=new StreamResult(pw);
			transformer.transform(source,result);
		}
		catch(TransformerException mye){
			mye.printStackTrace();
		}
		catch(IOException exp){
			exp.printStackTrace();
		}
	}
	public static void main(String args[]) throws IOException, InterruptedException{

		Vector<kpiBean> A = new Vector<kpiBean>();

		kpiBean tcBean;
		
		tcBean = new kpiBean();
		tcBean.setSelected("false");
		tcBean.setFolder("20132015125");
		tcBean.setScript("scripts\\TETE.py");
		tcBean.setSummary("test");
		tcBean.setIteration("1");
		tcBean.setVideo("video\\001.avi,video\\002.avi,video\\003.avi");
		tcBean.setAdjust("5");
		tcBean.setStartpicpath("/resource/11111.jpg");
		tcBean.setEndpicpath("/resource/11111.jpg");
		tcBean.setRemark("NA");
		//tcBean.setKpi("120");
		tcBean.setKpiavg("100");
		tcBean.setKpimax("130");
		
		A.add(tcBean);

		try{
			XmlWriterl myxml=new XmlWriterl(Constant.writexml_test);
			System.out.println("start swrite xml file:"+Constant.writexml_test);
			myxml.toWrite(A);
			myxml.toSave();
			System.out.print("Your writing is successful.");
		}
		catch(ParserConfigurationException exp){
			exp.printStackTrace();
			System.out.print("Your writing is failed.");
		}
	}
} 