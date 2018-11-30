package generate.scripe.generate.component.impl;

import generate.scripe.bean.SnakeKafkaBean;
import generate.scripe.generate.component.SnakeComponentGenerate;
import generate.scripe.generate.util.ComponentEnum;
import generate.scripe.generate.util.ScriptLanguageEnum;

public class SnakeKafkaProductComponment  extends SnakeComponentGenerate<SnakeKafkaBean>{
	private int engineType;
	private String configResult;
	

	public  SnakeKafkaProductComponment(SnakeKafkaBean bean,int scriptLanguage,int engineType) {
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
	public String createScriptToPython(SnakeKafkaBean t) {
		StringBuffer result= new StringBuffer();
		if(ComponentEnum.COMPONENT_STORM.getValue()==this.engineType) {
			// TODO Auto-generated method stub
		}else if(ComponentEnum.COMPONENT_FLINK.getValue()==this.engineType) {
			// TODO Auto-generated method stub
		}else {//SPARK
			result.append("producer = KafkaProducer(bootstrap_servers=['"+t.getBootstrapServers()+"'],value_serializer=lambda v: json.dumps(v).encode('utf-8'))");
			result.append("print(\"kafka 接收到数据：\")");
			result.append("producer.send('"+t.getTopics()+"', message);");
			result.append("producer.flush();");
		}
		
		return result.toString();
	}
	
	

	@Override
	public String createScriptToJava(SnakeKafkaBean t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createScriptToScala(SnakeKafkaBean t) {
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
