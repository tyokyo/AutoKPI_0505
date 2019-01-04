package com.mr.replay.ui.xml;
import org.w3c.dom.*;

import com.mr.replay.ui.bean.kpiBean;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;


public class XmlInsert {
	private Document document;
	private String filename;
	private ArrayList<File> files;
	Vector<kpiBean> addVector;
	
	
	public XmlInsert(String writefile,ArrayList<File> files) throws ParserConfigurationException{
		System.out.println("Towrite:"+writefile);
		addVector = new Vector<kpiBean>();
		this.filename=writefile;
		this.files = files;
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder();
		document=builder.newDocument();
		
	}
	public Vector<kpiBean> toWrite()
	{
		Vector<kpiBean> mkVector = null;
		XmlReader my = new XmlReader();
		mkVector = my.toRead(filename);
		
		if (mkVector.size()==0) {
			System.out.println("size is zero..........");
			Date date=new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String foldername = formatter.format(date);
			String folder = foldername;
			
			/*File df = new File(folder);
			if (!df.exists()) {
				df.mkdir();
			}*/
			
			for (File file : files) {
				String randomid = file.getName().replaceAll(".py", "").replaceAll(".PY", "");
				kpiBean tcBean = new kpiBean();
				tcBean = new kpiBean();
				tcBean.setSelected("true");
				tcBean.setFolder(folder);
				tcBean.setScript(file.getAbsolutePath());
				tcBean.setSummary("kpitest");
				tcBean.setIteration("1");
				tcBean.setVideo("video\\001.avi,video\\002.avi,video\\003.avi");
				tcBean.setAdjust("100");
				tcBean.setStartpicpath("/resource/11111.jpg");
				tcBean.setEndpicpath("/resource/11111.jpg");
				tcBean.setRandomid(randomid);
				tcBean.setRemark("NA");
				
				//tcBean.setKpi("NA");
				
				tcBean.setKpiavg("0");
				tcBean.setKpimax("0");
				
				mkVector.add(tcBean);
				addVector.add(tcBean);
			}
			
		}else {
			kpiBean tcBean0 = mkVector.get(0);
			String folder = tcBean0.getFolder();
			
			int i = 1;
			for (File file : files) {
				String randomid = file.getName().replaceAll(".py", "").replaceAll(".PY", "");
				
				kpiBean tcBean = new kpiBean();
				tcBean = new kpiBean();
				tcBean.setSelected("false");
				tcBean.setFolder(folder);
				tcBean.setScript(file.getAbsolutePath());
				tcBean.setSummary("test");
				tcBean.setIteration("1");
				tcBean.setVideo("video\\001.avi,video\\002.avi,video\\003.avi");
				tcBean.setAdjust("100");
				tcBean.setStartpicpath("/resource/11111.jpg");
				tcBean.setEndpicpath("/resource/11111.jpg");
				tcBean.setRandomid(randomid);
				tcBean.setRemark("NA");
				//tcBean.setKpi("NA");
				tcBean.setKpiavg("0");
				tcBean.setKpimax("0");
				
				//add to table
				mkVector.add(tcBean);
				addVector.add(tcBean);
				i = i+1;
			}
			
			/*File df = new File(folder);
			if (!df.exists()) {
				df.mkdir();
			}*/
		}

		Element root = document.createElement("testsuite");
		root.setAttribute("name", "monkey");
		root.setAttribute("version", "NA");
		//root.setAttribute("version", ServiceHelper.getSWVersion());
		document.appendChild(root);
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


			/*Element kpiElement = document.createElement("kpi");
			kpiElement.setAttribute("id", timeseconds);
			mkEmt.appendChild(kpiElement);
			Text kpi = document.createTextNode(String.valueOf(monkeyBean.getKpi()));
			kpiElement.appendChild(kpi);*/
		
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
		return addVector;
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
	public static void main(String args[]) throws IOException, InterruptedException, ParserConfigurationException{

		//mk2WriteXmls.WritePmList();


		/*String xmlp = Constant.mk2+"mk2.xml";
		mk2Insert Insert = new mk2Insert(xmlp,"ckt.cm");
		Insert.toWrite();
		Insert.toSave();*/
		//mk2WriteXml2.WritepkgFile();
	}
} 