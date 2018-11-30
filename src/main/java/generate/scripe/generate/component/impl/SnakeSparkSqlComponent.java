package generate.scripe.generate.component.impl;

import generate.scripe.bean.SnakeSparkBean;
import generate.scripe.generate.component.SnakeComponentGenerate;
import generate.scripe.generate.util.ComponentEnum;
import generate.scripe.generate.util.ScriptLanguageEnum;

public class SnakeSparkSqlComponent  extends SnakeComponentGenerate<SnakeSparkBean>{
	private int engineType;

	public  SnakeSparkSqlComponent(SnakeSparkBean bean,int scriptLanguage) {
		
		String result=null;
		//生成python 脚本
		 if(scriptLanguage==ScriptLanguageEnum.PYTHON.getValue()) {
			result= createScriptToPython(bean);
		 }else if(scriptLanguage==ScriptLanguageEnum.JAVA.getValue()) {
			 result= createScriptToJava(bean);
		 }else {
			 result= createScriptToScala(bean);
		 }
		 this.setConfigResult(result);
	}



	/*
	 *  (non-Javadoc)
	 * @see generate.scripe.generate.SnakeGenerate#createScriptToPython(java.lang.Object, java.lang.String)
	 */
	@Override
	public String createScriptToPython(SnakeSparkBean t) {
		
		StringBuffer result= new StringBuffer();
		if(ComponentEnum.COMPONENT_STORM.getValue()==this.engineType) {
			// TODO Auto-generated method stub
		}else if(ComponentEnum.COMPONENT_FLINK.getValue()==this.engineType) {
			// TODO Auto-generated method stub
		}else {//SPARK
			 //不需要实现，只需要返回t 对象即可
		}
		
		return result.toString();
	}
	
	

	@Override
	public String createScriptToJava(SnakeSparkBean t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createScriptToScala(SnakeSparkBean t) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}
