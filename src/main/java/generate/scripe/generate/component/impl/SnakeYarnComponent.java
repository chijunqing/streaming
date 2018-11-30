package generate.scripe.generate.component.impl;

import generate.scripe.bean.SnakeYarnBean;
import generate.scripe.generate.component.SnakeComponentGenerate;
import generate.scripe.generate.util.ScriptLanguageEnum;

public class SnakeYarnComponent  extends SnakeComponentGenerate<SnakeYarnBean>{
	private int engineType;
	private String configResult;
	

	public  SnakeYarnComponent(SnakeYarnBean bean,int scriptLanguage,int engineType) {
		this.engineType=engineType;
		
		String result=null;
		//生成python 脚本
		 if(scriptLanguage==ScriptLanguageEnum.PYTHON.getValue()) {
			result= createScriptToPython(bean);
		 }else if(scriptLanguage==ScriptLanguageEnum.JAVA.getValue()) {
			 result= createScriptToJava(bean);
		 }else {
			 result= createScriptToScala(bean);
		 }
		 this.configResult=result;
	}



	/*
	 *  (non-Javadoc)
	 * @see generate.scripe.generate.SnakeGenerate#createScriptToPython(java.lang.Object, java.lang.String)
	 */
	@Override
	public String createScriptToPython(SnakeYarnBean t) {
		StringBuffer result= new StringBuffer();
		 //TODO spark python 实现
		return result.toString();
	}
	
	

	@Override
	public String createScriptToJava(SnakeYarnBean t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createScriptToScala(SnakeYarnBean t) {
		// TODO Auto-generated method stub
		return null;
	}


	public String getConfigResult() {
		return configResult;
	}



	public void setConfigResult(String configResult) {
		this.configResult = configResult;
	}
	
	

}
