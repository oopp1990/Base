package cq.base.util;

import java.io.File;
import java.io.IOException;

public class FileManager {
	/**
	 * ����·������һ���ļ�
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
	 * ɾ����Ŀ¼�µ������ļ�
	 * @param file
	 */
	public static boolean delete(File file) {   
	    if(file.isFile()) {   //���Ϊ�ļ���ֱ��ɾ��   
	    	file.delete();
	        return true;
	    }   
	    //����Ϊ�ļ��� �ݹ����ɾ���ļ�   
	    File[] fileArr = file.listFiles();   
	    for(File f:fileArr) {
	        delete(f); 
	    }   
	    file.delete();  //���ɾ��ʣ��Ŀ��ļ���  
	    return true;
    }
	
	/**
	 * ɾ����·���µ������ļ�
	 * @param path
	 */
	public static boolean delete(String path) {   
		File file=new File(path);
		return delete(file);
    }
}
