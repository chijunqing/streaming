package generate.scripe.bean;

import java.util.LinkedHashMap;

/**
 * spark 配置
 * @author bojue
 *
 */
public class SnakeSparkBean extends BaseBean{
	
	private String tempView;
	private String scriptValue;
	private LinkedHashMap<String ,String> fields;
	/**
	 * 批处理时间，单位秒{1}
	 */
	private int batchDuration;
	
	
	public int getBatchDuration() {
		return batchDuration;
	}
	public void setBatchDuration(int batchDuration) {
		this.batchDuration = batchDuration;
	}
 
	public String getTempView() {
		return tempView;
	}
	public void setTempView(String tempView) {
		this.tempView = tempView;
	}
	public String getScriptValue() {
		return scriptValue;
	}
	public void setScriptValue(String scriptValue) {
		this.scriptValue = scriptValue;
	}
	public LinkedHashMap<String,String> getFields() {
		return fields;
	}
	
	public String getFiledStr() {
		StringBuffer filedStr=new StringBuffer();
		if(fields !=null) {
			for (String key : fields.keySet()) {
				filedStr.append(key+" ");
			}
		}else {
			filedStr.append("");
		}
		return filedStr.toString();
	}
	
	public void setFields(LinkedHashMap<String,String> fields) {
		this.fields = fields;
	}
	
	@Override
	public String toString() {
		return "SnakeSparkBean [ tempView=" + tempView + ", scriptValue=" + scriptValue
				+ ", fields=" + fields + ", batchDuration=" + batchDuration + "]";
	}
	
}
