package analyze.video.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileOp {

	/** 
	  * 删除某个文件夹下的所有文件夹和文件 
	  * 
	  * @param delpath 
	  *            String 
	  * @throws FileNotFoundException 
	  * @throws IOException 
	  * @return boolean 
	  */  
	public static boolean deletefile(String delpath) {  
		try {  
			File file = new File(delpath);  
			// 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true  
			if (!file.isDirectory()) {  
				file.delete();  
			} else if (file.isDirectory()) {  
				String[] filelist = file.list();  
				for (int i = 0; i < filelist.length; i++) {  
					File delfile = new File(delpath + "\\" + filelist[i]);  
					if (!delfile.isDirectory()) {  
						delfile.delete();  
						//System.out.println(delfile.getAbsolutePath() + "删除文件成功");  
					} else if (delfile.isDirectory()) {  
						deletefile(delpath + "\\" + filelist[i]);  
					}  
				}  
//				System.out.println(file.getAbsolutePath()+"删除成功");  
				file.delete();  
			}  
		} catch (Exception e) {  
			System.out.println("deletefile() Exception:" + e.getMessage());  
		}  
		return true;  
	}  

	public static void renameFile(String path, String oldname, String newname) {
		if(!oldname.equals(newname)) {//新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile=new File(path+"/"+oldname);
            File newfile=new File(path+"/"+newname);
            if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
            	System.out.println(newname+"已经存在！");
            else {
                oldfile.renameTo(newfile);
            }
        }         
    }
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileOp.deletefile("D:\\tmp__\\test\\");
		FileOp.renameFile("D:\\tmp__\\test", "01.jpg", "a.jpg");
	}

}
