package com.mr.replay.ui.xml;

import java.io.File;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mr.replay.ui.bean.Constant;
import com.mr.replay.ui.bean.kpiBean;

public class XmlUpdate {
    public static boolean doc2XmlFile(Document document, String filename) {
        boolean flag = true;
        try {
            
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            
            //transformer.setOutputProperty(OutputKeys.ENCODING, "GBK");
            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);
        } catch (Exception ex) {
            flag = false;
            ex.printStackTrace();
        }
        return flag;
    }
 
    public static Document load(String filename) {
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new File(filename));
            document.normalize();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }
 
    
    public static void xmlUpdateKey(String filename,String node,String value) {
        Document document = load(filename);
        Node root = document.getDocumentElement();
        boolean nodeExist = false;
        if (root.hasChildNodes()) {
        	System.out.println(root.getAttributes().item(0).getNodeValue());
            NodeList ftpnodes = root.getChildNodes();
            
            for (int i = 0; i < ftpnodes.getLength(); i++) {
                NodeList ftplist = ftpnodes.item(i).getChildNodes();
                
               // System.out.println(ftpnodes.item(i).getTextContent());
                System.out.println();
                
                for (int k = 0; k < ftplist.getLength(); k++) {
                    Node subnode = ftplist.item(k);
                    
                    // if (subnode.getNodeType()==Node.ELEMENT_NODE&&subnode.
                    // getNodeName()=="ftp-chn")
                    // {
                    // ftpnodes.item(i).removeChild(subnode);
                    // }
                    
                    if (subnode.getNodeType() == Node.ELEMENT_NODE
                            && subnode.getNodeName() == node) {
                    	/*String nodeid= subnode.getAttributes().item(0).getNodeValue();
                    	if (rmid.equals(nodeid)) {
                    		subnode.getFirstChild().setNodeValue(value);
						}*/
                    	subnode.getFirstChild().setNodeValue(value);
                    	nodeExist = true;
                    }
                }
            }
        }
        doc2XmlFile(document,filename );
        
        if (nodeExist==false) {
        	addNode(filename, node, value);
		}
        
    }
    public static void addNode(String xmlfile,String nodename,String nodevalue){
    	try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlfile);
            Element brandElement = doc.createElement(nodename);
            
            brandElement.setTextContent(nodevalue);
            Element phoneElement=(Element)doc.getElementsByTagName("testcase").item(0);
            phoneElement.appendChild(brandElement);
            TransformerFactory transformerFactory=TransformerFactory.newInstance();
            Transformer transformer=transformerFactory.newTransformer();
            DOMSource domSource=new DOMSource(doc);
            transformer.setOutputProperty(OutputKeys.ENCODING, "GB2312");
            StreamResult result=new StreamResult(new FileOutputStream(xmlfile));
            transformer.transform(domSource, result);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void testCaseUpdateXml(String filename,kpiBean testCase,String item,String value) {
        Document document = load(filename);
        Node root = document.getDocumentElement();
        System.out.println("start Update xml: "+filename+" with "+item+"="+value);
        if (root.hasChildNodes()) {
            NodeList ftpnodes = root.getChildNodes();
            for (int i = 0; i < ftpnodes.getLength(); i++) {
                NodeList ftplist = ftpnodes.item(i).getChildNodes();
                for (int k = 0; k < ftplist.getLength(); k++) {
                    Node subnode = ftplist.item(k);
                    
                    // if (subnode.getNodeType()==Node.ELEMENT_NODE&&subnode.
                    // getNodeName()=="ftp-chn")
                    // {
                    // ftpnodes.item(i).removeChild(subnode);
                    // }
                    
                    if (subnode.getNodeType() == Node.ELEMENT_NODE
                            && subnode.getNodeName() == item) {
                    	String itemid= subnode.getAttributes().item(0).getNodeValue();
                    	if (itemid.equals(testCase.getRandomid())) {
                    		subnode.getFirstChild().setNodeValue(value);
						}
                    }
                }
            }
        }
        doc2XmlFile(document,filename );
    }
    public static void testCaseUpdateXml(String filename,String randomid,String item,String value) {
    	value=value.replaceAll("<", "").replaceAll(">", "").replaceAll("/>", "").replaceAll("/<", "");
    	Document document = load(filename);
        Node root = document.getDocumentElement();
        System.out.println("start Update xml: "+filename+" with "+item+"="+value);
        if (root.hasChildNodes()) {
            NodeList ftpnodes = root.getChildNodes();
            for (int i = 0; i < ftpnodes.getLength(); i++) {
                NodeList ftplist = ftpnodes.item(i).getChildNodes();
                for (int k = 0; k < ftplist.getLength(); k++) {
                    Node subnode = ftplist.item(k);
                    
                    // if (subnode.getNodeType()==Node.ELEMENT_NODE&&subnode.
                    // getNodeName()=="ftp-chn")
                    // {
                    // ftpnodes.item(i).removeChild(subnode);
                    // }
                    
                    if (subnode.getNodeType() == Node.ELEMENT_NODE
                            && subnode.getNodeName() == item) {
                    	String itemid= subnode.getAttributes().item(0).getNodeValue();
                    	if (itemid.equals(randomid)) {
                    		subnode.getFirstChild().setNodeValue(value);
						}
                    }
                }
            }
        }
        doc2XmlFile(document,filename );
    }
    public static void main(String args[]) throws Exception {
    	//s = new String(s.getBytes("ISO8859_1"), "GBK");
    	String fileName= Constant.writexml_test;
    	kpiBean kBean = new kpiBean();
    	kBean.setRandomid("1394272464464");
    	XmlUpdate.testCaseUpdateXml(fileName, "1394272464464", "summary", "A123456");
       
    }
}