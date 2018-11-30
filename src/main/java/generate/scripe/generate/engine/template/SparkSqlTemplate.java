package generate.scripe.generate.engine.template;

import java.text.MessageFormat;
import java.util.Date;

import generate.scripe.generate.util.SnakeUtil;

public class SparkSqlTemplate{

	/**
	 * 应用名称 {0}
	 */
	private String appName;

	/**
	 * 批处理时间，单位秒{1}
	 */
	private int batchDuration=30;
	
	
	/**
	 * 
	 * 数据输入源{2}
	 */
	private String inPutDataSource;
	
	/**
	 * 数据输出源{3}
	 */
	private String outPutDataSource;
	
	/**
	 * 字段 多个中间用空格分隔 如： c1 c2 c3  {4}
	 */
	private String fields;
	
	/**
	 * spark 临时试图 类似表名称{5}
	 */
	private String tempView;
	
	/**
	 * sql 脚本  {6}
	 */
	private String sqlScript;
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public int getBatchDuration() {
		return batchDuration;
	}

	public void setBatchDuration(int batchDuration) {
		this.batchDuration = batchDuration;
	}

	public String getInPutDataSource() {
		return inPutDataSource;
	}

	public void setInPutDataSource(String inPutDataSource) {
		this.inPutDataSource = inPutDataSource;
	}

	public String getOutPutDataSource() {
		return outPutDataSource;
	}

	public void setOutPutDataSource(String outPutDataSource) {
		this.outPutDataSource = outPutDataSource;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getTempView() {
		return tempView;
	}

	public void setTempView(String tempView) {
		this.tempView = tempView;
	}

	public String getSqlScript() {
		return sqlScript;
	}

	public void setSqlScript(String sqlScript) {
		this.sqlScript = sqlScript;
	}
	
	/**
	 * 根据模板生成文件
	 * @param templateName
	 * @return
	 */
	public String createFile() {
		String root=SparkSqlTemplate.class.getResource("").getPath();
		root=root.substring(1, root.length());
		String outPutFilePath=null;
		String inPutFilePath=null;
		Date date=new Date();
		try {
			
			inPutFilePath=root+"SparkSqltemplatePython.py";
			outPutFilePath = root+"../../script/"+this.appName+"_"+tempView+"_"+date.getTime()+".py";
			System.out.println(inPutFilePath);
			System.out.println(outPutFilePath);
			String templet=SnakeUtil.readFile(inPutFilePath);
			
			String result = MessageFormat.format(templet, appName,batchDuration,inPutDataSource,outPutDataSource,fields,tempView,sqlScript); 
			SnakeUtil.writeFile(outPutFilePath,result);
			System.out.println("文件生成目录:"+outPutFilePath);
		} catch (Exception e) {
			System.out.println("生成文件失败！"+e.getMessage());
			e.printStackTrace();
		}
		
		return outPutFilePath;
	}
	
 public static void main(String[] args) {
	 SparkSqlTemplate sst=new SparkSqlTemplate();
			 sst.createFile();
//	 System.out.println(sst.getClass().getResource("").getPath());
//	 System.out.println(sst.getClass().getResource("/").getPath());
//	 System.out.println(SparkSqlTemplate.class.getResource(""));
//	 System.out.println(SparkSqlTemplate.class.getResource("/"));
//	 System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
//	 System.out.println(SparkSqlTemplate.class.getClassLoader().getResource(""));
//	 System.out.println(ClassLoader.getSystemResource(""));
	 
}

	@Override
	public String toString() {
		return "SparkSqlTemplate [appName=" + appName + ", batchDuration=" + batchDuration + ", inPutDataSource="
				+ inPutDataSource + ", outPutDataSource=" + outPutDataSource + ", fields=" + fields + ", tempView="
				+ tempView + ", sqlScript=" + sqlScript + "]";
	}

	
	

}
