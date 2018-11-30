package generate.scripe.bean;

public class BaseBean  {
	private int configType;//��������  (0:��������Դ   1���������Դ   2��Sql�ű�����       3: ��������)
	private int component;//������� (0:kafka  1:hbase 2:hive 3:hdfs 4:spark 5:storm 6:flink) 
	private String configJson;//�����Ӧ��ԭʼJSON
	
	
	public String getConfigJson() {
		return configJson;
	}
	public void setConfigJson(String configJson) {
		this.configJson = configJson;
	}
	public int getConfigType() {
		return configType;
	}
	public void setConfigType(int configType) {
		this.configType = configType;
	}
	public int getComponent() {
		return component;
	}
	public void setComponent(int component) {
		this.component = component;
	}
	@Override
	public String toString() {
		return "BaseBean [configType=" + configType + ", component=" + component + ", configJson=" + configJson + "]";
	}
	
	
	
}
