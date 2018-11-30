package generate.scripe.generate.component;

public abstract class SnakeComponentGenerate <T>{
	
	/**
	 * ���Bean
	 */
	private T t;
	
	/**
	 * �����������ɵĽű� ����
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
	 * ����python�汾�ű�
	 * @param t
	 * @return
	 */
	public abstract String createScriptToPython(T t);
	
	/**
	 * ����java�汾�Ľű�
	 * @param t
	 * @return
	 */
	public abstract String createScriptToJava(T t);
	
	/**
	 * ����scala �ű�
	 * @param t
	 * @return
	 */
	public abstract String createScriptToScala(T t) ;
	

}
