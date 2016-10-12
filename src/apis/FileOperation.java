package apis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.LinkedList;

public class FileOperation 
{
	public static LinkedList<String> list ;
	public static BufferedWriter writeFile(String location) throws Exception
	{
		File file_write = new File(location);
	    BufferedWriter bw = new BufferedWriter(new FileWriter(file_write)); 
	    return bw;
	}
	
	/**
	  * ���Ƶ����ļ������Ŀ���ļ����ڣ��򲻸��ǡ�
	  * @param srcFileName �����Ƶ��ļ���
	  * @param destFileName Ŀ���ļ���
	  * @return  ������Ƴɹ����򷵻�true�����򷵻�false
	  */
	 public static boolean copyFile(String srcFileName, String destFileName){
	    	return FileOperation.copyFile(srcFileName, destFileName, false);
	 }
	 
	 /**
	  * ���Ƶ����ļ�
	  * @param srcFileName �����Ƶ��ļ��� 
	  * @param destFileName Ŀ���ļ���
	  * @param overlay  ���Ŀ���ļ����ڣ��Ƿ񸲸�
	  * @return ������Ƴɹ����򷵻�true�����򷵻�false
	  */
	 public static boolean copyFile(String srcFileName,String destFileName, boolean overlay) {
		  //�ж�ԭ�ļ��Ƿ����
		  File srcFile = new File(srcFileName);
		  if (!srcFile.exists()){
			   System.out.println("�����ļ�ʧ�ܣ�ԭ�ļ�" + srcFileName + "�����ڣ�");
			   return false;
		  } else if (!srcFile.isFile()){
			   System.out.println("�����ļ�ʧ�ܣ�" + srcFileName + "����һ���ļ���");
			   return false;
		  }
		  //�ж�Ŀ���ļ��Ƿ����
		  File destFile = new File(destFileName);
		  if (destFile.exists()){
				//���Ŀ���ļ����ڣ����Ҹ���ʱ�����ǡ�
			   /*if (overlay){
				    //ɾ���Ѵ��ڵ�Ŀ���ļ�������Ŀ���ļ���Ŀ¼���ǵ����ļ�
				    System.out.println("Ŀ���ļ��Ѵ��ڣ�׼��ɾ������");
				    boolean success = DeleteFileUtil.deleteDir(destFile); 
				    if(success = false){
					     System.out.println("�����ļ�ʧ�ܣ�ɾ��Ŀ���ļ�" + destFileName + "ʧ�ܣ�");
					     return false;
				    }
			  } else {
				    System.out.println("�����ļ�ʧ�ܣ�Ŀ���ļ�" + destFileName + "�Ѵ��ڣ�");
				    return false;
			  }*/
		  }else {
			   if (!destFile.getParentFile().exists()){
			    //���Ŀ���ļ����ڵ�Ŀ¼�����ڣ��򴴽�Ŀ¼
			    System.out.println("Ŀ���ļ����ڵ�Ŀ¼�����ڣ�׼����������");
			    if(!destFile.getParentFile().mkdirs()){
			     System.out.println("�����ļ�ʧ�ܣ�����Ŀ���ļ����ڵ�Ŀ¼ʧ�ܣ�" );
			     return false;
			    }
			   }
		  }
		  //׼�������ļ�
		  int byteread = 0;//��ȡ��λ��
		  InputStream in = null;
		  OutputStream out = null;
		  try {
			   //��ԭ�ļ�
			   in = new FileInputStream(srcFile);  
			   //�����ӵ�Ŀ���ļ��������
			   out = new FileOutputStream(destFile);
			   byte[] buffer = new byte[1024];
			   //һ�ζ�ȡ1024���ֽڣ���bytereadΪ-1ʱ��ʾ�ļ��Ѿ�����
			   while ((byteread = in.read(buffer)) != -1) {
				    //����ȡ���ֽ�д�������
				    out.write(buffer, 0, byteread);
			   }
				System.out.println("���Ƶ����ļ�" + srcFileName + "��" + destFileName + "�ɹ���");
				return true;
		  } catch (Exception e) {
			   System.out.println(destFile);
			   System.out.println("�����ļ�ʧ�ܣ�" + e.getMessage());
			   return false;
		  } finally {
			   //�ر������������ע���ȹر���������ٹر�������
			   if (out != null){
				    try {
				    	out.close();
				    } catch (IOException e) {
				    	e.printStackTrace();
				    }
			   }
			   if (in != null){
				    try {
				    	in.close();
				    } catch (IOException e) {
				    	e.printStackTrace();
				    }
			   }
		  }
	 }

	
	public static BufferedReader openFile(String location) throws Exception
	{
		File f=new File(location);    
        InputStreamReader isr1=new InputStreamReader(new FileInputStream(f));  
        BufferedReader br=new BufferedReader(isr1);  
	    return br;
	}
	public static LinkedList<String> getFileNames(String location) throws Exception
	{
		list = new LinkedList<String>();  
		getFileNamesHelper(location);		
		return list;
	}
	
	public static void getFileNamesHelper(String path) throws Exception
	{
		    File file = new File(path);   
	        // get the folder list   
	        File[] array = file.listFiles();   	          
	        for(int i=0;i<array.length;i++){   
	            if(array[i].isFile()){   
	            	list.add(array[i].getName());   	                 
	            }else if(array[i].isDirectory()){   
	            	getFileNamesHelper(array[i].getPath());   
	            } 
	        }
	}
	
}
