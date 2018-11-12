package spark;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class UploadFileToHdfs {
	
	/**
	 * 本地文件上传到 远程hdfs 服务器上
	 * @param source：文件来源 路径（本地路径）
	 * @param targ：文件目标存储路径（hdfs路径）
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	public static void copyFromLocal(String source,String targ) throws IOException, URISyntaxException{
		//1、创建config
		Configuration config =new Configuration();
		URI uri= new URI("hdfs://sdc13.sefonsoft.com:8020");
		//2、创建 hdfs 链接 
		FileSystem hdfs =FileSystem.get(uri, config);
		//3、创建本地路径
		Path sourcePath=new Path(source);
		//4、创建dhfs路径
		Path targPath=new Path(targ);
		//5、判断dfs路径是否存在，如果不存在 则创建
		if(!hdfs.exists(targPath)){
			hdfs.mkdirs(targPath);
		}
		
		//6、执行文件上传操作
		hdfs.copyFromLocalFile(sourcePath, targPath);

		System.out.println(source +" to " + targ);
	}

	
	public static void main(String[] args) throws IOException, URISyntaxException {
		UploadFileToHdfs.copyFromLocal("G:/other/word.txt", "/data2/test1");
	}
}
