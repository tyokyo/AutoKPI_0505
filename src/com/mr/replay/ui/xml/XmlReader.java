package com.mr.replay.ui.xml;
import java.io.File;
import java.io.FileInputStream;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mr.replay.ui.bean.kpiBean;

public class XmlReader {
	static Document document;
	static boolean validating;

	public XmlReader() {
	}
	public static String toReadRootAttr(String filename,String attrKey){
		String attrValue="";
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			factory.setValidating(validating);
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new FileInputStream(filename));
			//document = builder.parse(new FileInputStream(filename),"GBK");
			Element root = document.getDocumentElement();
			attrValue = root.getAttribute(attrKey);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return attrValue;
	}
	public String toReadffmpeg(String filename)
	{
		String jar = "";
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			factory.setValidating(validating);
			DocumentBuilder builder = factory.newDocumentBuilder();

			document = builder.parse(new File(filename));

			Element root = document.getDocumentElement();
			NamedNodeMap attr;
			NodeList children = root.getElementsByTagName("ffmpeg");
			attr = root.getAttributes();

			if(attr!=null)
			{
				for(int i = 0;i<children.getLength();i++)
				{
					Element element= (Element) children.item(i);
					NodeList list = element.getChildNodes();
					jar = list.item(1).getTextContent();
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return jar;
	}
	public Vector<kpiBean> toRead(String filename)
	{
		System.out.println("start Read xml: "+filename);
		Vector<kpiBean> mkVector=new Vector<kpiBean>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			factory.setValidating(validating);
			DocumentBuilder builder = factory.newDocumentBuilder();

			document = builder.parse(new File(filename));

			Element root = document.getDocumentElement();
			System.out.println(root.getAttribute("name"));
			System.out.println(root.getAttribute("version"));
			NamedNodeMap attr;
			NodeList children = root.getElementsByTagName("testcase");
			attr = root.getAttributes();

			if(attr!=null)
			{
				for(int i = 0;i<children.getLength();i++)
				{
					kpiBean mkbean = new kpiBean();
					Element element= (Element) children.item(i);
					NodeList list = element.getChildNodes();
					Node child = list.item(i);
					mkbean.setSelected(list.item(1).getTextContent());
					mkbean.setFolder(list.item(3).getTextContent());
					mkbean.setScript(list.item(5).getTextContent());
					mkbean.setSummary(list.item(7).getTextContent());
					mkbean.setIteration(list.item(9).getTextContent());
					mkbean.setVideo(list.item(11).getTextContent());
					mkbean.setAdjust(list.item(13).getTextContent());
					mkbean.setStartpicpath(list.item(15).getTextContent());
					mkbean.setEndpicpath(list.item(17).getTextContent());
					mkbean.setRandomid(list.item(19).getTextContent());
					mkbean.setRemark(list.item(21).getTextContent());
					mkbean.setKpiavg(list.item(23).getTextContent());
					mkbean.setKpimax(list.item(25).getTextContent());
					
					mkVector.add(mkbean);
				}
			}
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			return null;
		}
		return mkVector;
	}

	public static void main(String[] args) {
		/*Vector<CaseBean> A = null;
		XmlReader my = new XmlReader();
		A = my.toRead(Constant.writexml);
		for (CaseBean monkeyBean : A) {
			monkeyBean.toString();
		}
		System.out.println("================:"+toReadRootAttr(Constant.writexml,"name"));
		System.out.println("================:"+toReadRootAttr(Constant.writexml,"version"));
	*/
		String xml = "E:\\AutoKPI\\config\\autokpi.xml";
		XmlReader my = new XmlReader();
		System.out.println(my.toReadffmpeg(xml));
		System.out.println(my.toRead(xml));
	}
} 