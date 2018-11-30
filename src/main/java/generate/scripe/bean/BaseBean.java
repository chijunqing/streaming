package generate.scripe.bean;

public class BaseBean  {
	private int configType;//配置类型  (0:数据输入源   1：数据输出源   2：Sql脚本配置       3: 发布配置)
	private int component;//组件类型 (0:kafka  1:hbase 2:hive 3:hdfs 4:spark 5:storm 6:flink) 
	private String configJson;//组件对应的原始JSON
	
	
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
