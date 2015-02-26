package cq.base.util;

import java.io.File;
import java.io.IOException;

public class FileManager {
	/**
	 * 根据路径创建一个文件
	 * @param path
	 * @return
	 */
	public static File createFile(String path)
	{
		String url=path.replace("/", File.separator).replace("\\",File.separator);
		String dir=url.substring(0, url.lastIndexOf(File.separator));
		File fileDir=new File(dir);
		if(!fileDir.exists())
		{
			fileDir.mkdirs();
		}
		File file=new File(url);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	/**
	 * 删除该目录下的所有文件
	 * @param file
	 */
	public static boolean delete(File file) {   
	    if(file.isFile()) {   //如果为文件则直接删除   
	    	file.delete();
	        return true;
	    }   
	    //若果为文件夹 递归遍历删除文件   
	    File[] fileArr = file.listFiles();   
	    for(File f:fileArr) {
	        delete(f); 
	    }   
	    file.delete();  //最后删除剩余的空文件夹  
	    return true;
    }
	
	/**
	 * 删除该路径下的所有文件
	 * @param path
	 */
	public static boolean delete(String path) {   
		File file=new File(path);
		return delete(file);
    }
}
