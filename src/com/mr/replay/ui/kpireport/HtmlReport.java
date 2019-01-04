package com.mr.replay.ui.kpireport;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.INTERNAL;


public class HtmlReport {
	public static String REPORTBF="";
	public static int getMax(ArrayList<Integer> value){
		int max=0;
		int size = value.size();
		for (int i = 0; i < size; i++) {
			if (value.get(i)>=max) {
				max=value.get(i);
			} 
		}
		return max;
	}
	
	public static int getAvg(ArrayList<Integer> value){
		int total=0;
		int size = value.size();
		for (int i = 0; i < size; i++) {
			total = total + value.get(i);
		}
		return total/size;
	}
	public static String GenerateReport(String swversion,List<ListBean> kpiList,int max){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
		stringBuffer.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
		stringBuffer.append("<head>\n");
		stringBuffer.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n");
		stringBuffer.append("<title>AutoKpi Test Result</title>\n");
		stringBuffer.append("<style type=\"text/css\">\n");
		stringBuffer.append(" h3 {text-indent: 1em;}\n");
		stringBuffer.append(" h4 {text-indent: 2em;}\n");
		stringBuffer.append(" [indent=h3] {text-indent: 1em;}\n");
		stringBuffer.append(" [indent=h4] {text-indent: 2em;}\n");
		stringBuffer.append(" table, td, th\n");
		stringBuffer.append(" {font-size:0.9em;\n");
		stringBuffer.append(" border:1px solid #98bf21;\n");
		stringBuffer.append(" padding:3px 7px 2px 7px;}\n");
		stringBuffer.append(" .red {color:red;}\n");
		stringBuffer.append(" .darkred {color:darkred;}\n");
		stringBuffer.append(" .green {color:green;}\n");
		stringBuffer.append(" .blue {color:blue;}\n");
		stringBuffer.append(" .black {color:black;}\n");
		stringBuffer.append(" .orange {color:orange;}\n");
		stringBuffer.append(" .gray {color:gray;}\n");
		stringBuffer.append(" .comment {color:gray;font-size:0.8em;font-style:italic;}\n");
		stringBuffer.append("</style>\n");
		stringBuffer.append("</head>\n");
		stringBuffer.append("<h1>AutoKPI Report</h1>\n");
		stringBuffer.append("<tr>软件版本:"+swversion+"</tr>\n");
		stringBuffer.append("</P>\n");
		
		int PASS = 0;
		int FAIL =0;
		for (ListBean listBean : kpiList) {
			ArrayList<Integer> vlist = listBean.getValueList();
			int actAverage = getAvg(vlist);
			int actMax = getMax(vlist);
			int expctAvg = listBean.getStandardAvg();
			int expectMax = listBean.getStandardMax();
			if (actAverage<=expctAvg &&actMax<=expectMax) {
				PASS = PASS+1;
			}else {
				FAIL = FAIL+1;
			}
		}
		stringBuffer.append("<tr>测试项总数:"+kpiList.size()+",PASS:"+PASS+",FAIL:"+FAIL+"</tr>");
		
		stringBuffer.append("</P>\n");
		stringBuffer.append("</P>\n");
		stringBuffer.append("<table>\n");
		stringBuffer.append("  <tr>\n");
		
		
		stringBuffer.append("<th>测试项</th>\n");
		stringBuffer.append("<th>参考值【平均值】(毫秒)</th>\n");
		stringBuffer.append("<th>参考值【最大值】(毫秒)</th>\n");
		stringBuffer.append("<th>平均值(毫秒)</th>\n");
		for (int i = 1; i <=max; i++) {
			stringBuffer.append("<th>第"+i+"次测试(毫秒)</th>\n");
		}
		stringBuffer.append("<th>备注</th>\n");
		   
		
		stringBuffer.append("  </tr>\n");
		
		for (ListBean listBean : kpiList) {
			stringBuffer.append("<tr>\n");
			stringBuffer.append("<td>"+listBean.getTestdomain()+"</td>\n");
			//stringBuffer.append("<td>"+listBean.getKpivalue()+"</td>\n");
			
			ArrayList<Integer> vlist = listBean.getValueList();
			int average = getAvg(vlist);
			
			//int standard = listBean.getStandard();
			int standardAvg = listBean.getStandardAvg();
			int standardMax = listBean.getStandardMax();
			stringBuffer.append("<td>"+standardAvg+"</td>\n");
			stringBuffer.append("<td>"+standardMax+"</td>\n");
			if (standardAvg> average&&average<=standardMax) {
				stringBuffer.append("<td>"+average+"</td>\n");
			}else {
				stringBuffer.append("<td bgcolor=\"red\">"+average+"</td>\n");
			}
			
			int size =  vlist.size();
			for (int i = 0; i <max ; i++) {
				if (i<=size-1) {
					stringBuffer.append("<td>"+vlist.get(i)+"</td>\n");
				}else {
					stringBuffer.append("<td>"+"NA"+"</td>\n");
				}
			}
			/*<td>非首次进入图库</td>
		 	 <td>960</td>
			 <td>960</td>
			 <td>960</td>
			 <td>960</td>
			 <td>960</td>
			 <td>960</td>
			 <td>960</td>
			 </tr>*/
			stringBuffer.append("<td>"+listBean.getSummary()+"</td>\n");
			stringBuffer.append("</tr>\n");
		}
		
		stringBuffer.append("</table>\n<body>\n</body>\n</html>\n</em>");
		
		return stringBuffer.toString();
		
	}
	
}
