package generate.scripe.bean;

import java.util.Map;

/**
 * spark ≈‰÷√
 * @author bojue
 *
 */
public class SnakeYarnBean extends BaseBean{
	
	private String engine;
	private String tempView;
	private String scriptValue;
	private Map fields;
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
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
	public Map getFields() {
		return fields;
	}
	public void setFields(Map fields) {
		this.fields = fields;
	}
	@Override
	public String toString() {
		return "SnakeSparkBean [engine=" + engine + ", tempView=" + tempView + ", scriptValue=" + scriptValue
				+ ", fields=" + fields + "]";
	}
	
}
