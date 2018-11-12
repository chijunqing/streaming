package spark;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class UploadFileToHdfs {
	
	/**
	 * �����ļ��ϴ��� Զ��hdfs ��������
	 * @param source���ļ���Դ ·��������·����
	 * @param targ���ļ�Ŀ��洢·����hdfs·����
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	public static void copyFromLocal(String source,String targ) throws IOException, URISyntaxException{
		//1������config
		Configuration config =new Configuration();
		URI uri= new URI("hdfs://sdc13.sefonsoft.com:8020");
		//2������ hdfs ���� 
		FileSystem hdfs =FileSystem.get(uri, config);
		//3����������·��
		Path sourcePath=new Path(source);
		//4������dhfs·��
		Path targPath=new Path(targ);
		//5���ж�dfs·���Ƿ���ڣ���������� �򴴽�
		if(!hdfs.exists(targPath)){
			hdfs.mkdirs(targPath);
		}
		
		//6��ִ���ļ��ϴ�����
		hdfs.copyFromLocalFile(sourcePath, targPath);

		System.out.println(source +" to " + targ);
	}

	
	public static void main(String[] args) throws IOException, URISyntaxException {
		UploadFileToHdfs.copyFromLocal("G:/other/word.txt", "/data2/test1");
	}
}
