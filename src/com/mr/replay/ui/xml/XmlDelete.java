package com.mr.replay.ui.xml;

import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.mr.replay.ui.bean.kpiBean;


public class XmlDelete {
	String _path_delete;
	public XmlDelete(String _path_delete){
		this._path_delete=_path_delete;
	}
	public void deleteNode(kpiBean kpBean){
		if (kpBean!=null) {
			try {
				String randid= kpBean.getRandomid();
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				String xmlname = this._path_delete;
				Document doc = db.parse(xmlname);
				NodeList list=doc.getElementsByTagName("testcase");
				for(int i=0;i<list.getLength();i++){
					Element element= (Element) list.item(i);
					NodeList nodeList = element.getChildNodes();
					String scriptname = nodeList.item(5).getTextContent();
					String randomid = nodeList.item(19).getTextContent();
					if(randid.equals(randomid)){
						element.getParentNode().removeChild(element);
						System.out.println("remove node: "+scriptname +"with id:"+randomid+" success");
					}
				}
				TransformerFactory transformerFactory=TransformerFactory.newInstance();
				Transformer transformer=transformerFactory.newTransformer();
				DOMSource domSource=new DOMSource(doc);
				transformer.setOutputProperty(OutputKeys.ENCODING, "GBK");
				StreamResult result=new StreamResult(new FileOutputStream(xmlname));
				transformer.transform(domSource, result);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}


	}
	public static void main(String args[]){
		
	}
}
