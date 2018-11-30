package generate.scripe.generate.component;

public abstract class SnakeComponentGenerate <T>{
	
	/**
	 * 组件Bean
	 */
	private T t;
	
	/**
	 * 根据配置生成的脚本 代码
	 */
	private String configResult;
	
	
	
	public String getConfigResult() {
		return configResult;
	}

	public void setConfigResult(String configResult) {
		this.configResult = configResult;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	/**
	 * 生成python版本脚本
	 * @param t
	 * @return
	 */
	public abstract String createScriptToPython(T t);
	
	/**
	 * 生成java版本的脚本
	 * @param t
	 * @return
	 */
	public abstract String createScriptToJava(T t);
	
	/**
	 * 生成scala 脚本
	 * @param t
	 * @return
	 */
	public abstract String createScriptToScala(T t) ;
	

}
