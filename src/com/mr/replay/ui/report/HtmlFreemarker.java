package com.mr.replay.ui.report;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mr.replay.ui.bean.BeanHelper;
import com.mr.replay.ui.helper.JarHelper;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class HtmlFreemarker {
	private Configuration cfg ;  
    private Template template;   
    private PrintWriter printWriter;
    
    private String templatedir = null;
    private String templatefile = null;
    public SimpleHash root = new SimpleHash(); // 将会使用默认的包装器
    
    public HtmlFreemarker(String dir, String file) {
    	this.templatedir = dir;
    	this.templatefile = file;
    	try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
   
    /** 
     * 初始化方法 
     * @throws IOException 
     */  
    public void init() throws IOException{  
         // 获取freemarker的Configuration实例  
        cfg = new Configuration();  
        cfg.setDefaultEncoding("UTF-8");
         //设置模板文件目录  
        cfg.setDirectoryForTemplateLoading(new File(templatedir));  
         // 取得模板文件  
        template = cfg.getTemplate(templatefile);   
    }  
    /** 
     * 开始处理 
     * @throws TemplateException 
     * @throws IOException 
     */  
    public void make(String outputfile) throws TemplateException, IOException{ 
    	File dir = new File("report");
    	if( (!dir.exists()) || (!dir.isDirectory()) ) {
    		dir.mkdir();
    	}
        //实例化一个文件输出流  
    	String folder = JarHelper.getProjectPath()+"report\\";
    	if (!new File(folder).exists()) {
    		new File(folder).mkdirs();
		}
    	String outputpath = JarHelper.getProjectPath()+"report\\" + outputfile;
    	System.out.println(outputpath);
    	printWriter = new PrintWriter(outputpath, "UTF-8");    
        //合并模板和数据模型，并输出到stringWriter中  
        template.process(root, printWriter); 
        
        JOptionPane.showMessageDialog(new JFrame(), "测试报告已打开!\n"+outputpath,"提示", JOptionPane.INFORMATION_MESSAGE);
        BeanHelper.openResult(outputpath);
    } 
    
	public static void main(String[] args) {
		
	}
}
