package generate.scripe;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;

import generate.scripe.bean.BaseBean;
import generate.scripe.bean.SnakeKafkaBean;
import generate.scripe.bean.SnakeSparkBean;
import generate.scripe.bean.SnakeYarnBean;
import generate.scripe.generate.component.SnakeComponentGenerate;
import generate.scripe.generate.component.impl.SnakeKafkaConsumerComponment;
import generate.scripe.generate.component.impl.SnakeKafkaProductComponment;
import generate.scripe.generate.component.impl.SnakeSparkSqlComponent;
import generate.scripe.generate.component.impl.SnakeYarnComponent;
import generate.scripe.generate.engine.template.SparkSqlTemplate;
import generate.scripe.generate.util.ComponentEnum;
import generate.scripe.generate.util.ConfigTypeEnum;
import generate.scripe.generate.util.SnakeUtil;

public class SnakeGenerateFactory {
	
	/**
	 * 应用名称
	 */
	private String appName;
	
	/**
	 * 发布脚本语言
	 * 0:python
	 * 1:java
	 * 2:scala
	 */
	private int scriptLanguage=0;
	
	/**
	 * 0:spark 1:storm 2:flink
	 */
	private int engineType;
	
	/**
	 * 输入源
	 */
	private SnakeComponentGenerate inPutDataSource;
	
	/**
	 * 输出源
	 */
	private SnakeComponentGenerate outPutDataSource;

	/**
	 * 流式处理脚本  
	 */
	private SnakeComponentGenerate script;
	
	/**
	 * 集群配置
	 */
	private SnakeComponentGenerate cluster;
	
	
	public SnakeGenerateFactory(String appName ,List<String> configJson,int scriptLanguage,int engineType ) {
		this.scriptLanguage=scriptLanguage;
		this.engineType=engineType;
		this.appName=appName;
		
		init(configJson);
	}
	
	private void init(List<String> configJson) {
		if(configJson !=null && configJson.size()>0) {
			//根据json配置，获取各个组件信息
			for(String json:configJson) {
				//根据json配置 生成组件配置（kafka、spark....）
				configJson(json);
			}
		}
	}
	/**
	 * 根据json信息 实例化组件配置
	 * @param json
	 * @return
	 */
	private void configJson(String json) {
		Preconditions.checkNotNull(json,"json参数不正确");
		
		BaseBean bb=  (BaseBean) SnakeUtil.getBean(json,BaseBean.class);
		Preconditions.checkNotNull(bb,"json参数不正确");
		
		//数据输入源配置
		if(bb.getConfigType()==ConfigTypeEnum.CONFIG_INPUT.getValue()) {
			//根据json 序列号kafka配置对象
			this.inPutDataSource=getInPutDataSource(bb,json);;
		}else if(bb.getConfigType()==ConfigTypeEnum.CONFIG_OUTPUT.getValue()) {
			//数据输出配置
			this.outPutDataSource=getOutPutDataSource(bb,json);
		}else if(bb.getConfigType()==ConfigTypeEnum.CONFIG_SCRIPT.getValue()) {
			//流脚本配置
			this.script=getScripts(bb,json);
		}else if(bb.getConfigType()==ConfigTypeEnum.CONFIG_CLUSTER.getValue()) {
			//部署配置
			this.cluster=getCluster(bb, json);;
		}
	}
	
	/**
	 * 获取输入源
	 * @param bb
	 * @param json
	 * @return
	 */
	private SnakeComponentGenerate<SnakeKafkaBean> getInPutDataSource(BaseBean bb,String json) {
		SnakeComponentGenerate<SnakeKafkaBean> scg=null;
		//数据输入源-kafka组件
		if( bb.getComponent()==ComponentEnum.COMPONENT_KAFKA.getValue()) {
			//根据json 序列号kafka配置对象
			SnakeKafkaBean kafkaBean=(SnakeKafkaBean) SnakeUtil.getBean(json,SnakeKafkaBean.class) ;
			scg= new SnakeKafkaConsumerComponment(kafkaBean,scriptLanguage,engineType);
		}else if(bb.getComponent()==ComponentEnum.COMPONENT_HIVE.getValue()) {
			//TODO 待实现
		}
		
		return scg;
	}
	
	
	/**
	 * 获取输出源
	 * @param bb 
	 * @param json
	 * @return
	 */
	private SnakeComponentGenerate<SnakeKafkaBean> getOutPutDataSource(BaseBean bb,String json) {
		SnakeComponentGenerate<SnakeKafkaBean> scg=null;
		//数据输出源-kafka组件
		if( bb.getComponent()==ComponentEnum.COMPONENT_KAFKA.getValue()) {
			//根据json 序列号kafka配置对象
			SnakeKafkaBean kafkaBean=(SnakeKafkaBean) SnakeUtil.getBean(json,SnakeKafkaBean.class) ;
			scg= new SnakeKafkaProductComponment(kafkaBean,scriptLanguage,engineType);
		}else if(bb.getComponent()==ComponentEnum.COMPONENT_HIVE.getValue()) {
			//TODO 待实现
		}
		
		return scg;
	}

	
	/**
	 * 脚本
	 * @param bb 
	 * @param json
	 * @return
	 */
	private SnakeComponentGenerate<SnakeSparkBean> getScripts(BaseBean bb,String json) {
		SnakeComponentGenerate<SnakeSparkBean> scg=null;
		//流式处理脚本配置
		if( bb.getComponent()==ComponentEnum.COMPONENT_SPARK.getValue()) {
			//根据json 生成spark模板
			SnakeSparkBean sparkBean=(SnakeSparkBean) SnakeUtil.getBean(json,SnakeSparkBean.class) ;
			scg= new SnakeSparkSqlComponent(sparkBean,scriptLanguage);
		}else if(bb.getComponent()==ComponentEnum.COMPONENT_HIVE.getValue()) {
			//TODO 待实现
		}
		
		return scg;
	}

	
	/**
	 * 获取输出源
	 * @param bb 
	 * @param json
	 * @return
	 */
	private SnakeComponentGenerate<SnakeYarnBean> getCluster(BaseBean bb,String json) {
		SnakeComponentGenerate<SnakeYarnBean> scg=null;
		//流式处理脚本submit 集群配置
		if( bb.getComponent()==ComponentEnum.COMPONENT_YARN.getValue()) {
			//根据json 生成配置
			SnakeYarnBean sparkBean=(SnakeYarnBean) SnakeUtil.getBean(json,SnakeYarnBean.class) ;
			scg= new SnakeYarnComponent(sparkBean,scriptLanguage,engineType);
		}else if(bb.getComponent()==ComponentEnum.COMPONENT_MESOS.getValue()) {
			//TODO 待实现
		}
		
		return scg;
	}
	
	
	
	public static void main(String[] args) {
		// 使用new方法
		Gson gson = new Gson();
//		SnakeSparkBean ssb =new SnakeSparkBean();
//		ssb.setEngine("spark");
//		ssb.setTempView("table");
//		 Map map=new HashMap();
//		 map.put("c1", "c1");
//		 map.put("c2", "c2");
//		ssb.setFields(map);
//		
//		// toJson 将bean对象转换为json字符串
//		String jsonStr = gson.toJson(ssb, SnakeSparkBean.class);
//		System.out.println(jsonStr);
//		// fromJson 将json字符串转为bean对象
//		
		String jsonStr2="{\"bootstrapServers\":\"127.0.0.1\",\"port\":\"8888\",\"fields\":{\"c1\":\"字段1\",\"c2\":\"字段2\"}}";
		SnakeSparkBean skb2=(SnakeSparkBean) SnakeUtil.getBean(jsonStr2, SnakeSparkBean.class);
		System.out.println(skb2);
		SnakeKafkaBean skb3= (SnakeKafkaBean) SnakeUtil.getBean(jsonStr2, SnakeKafkaBean.class);
		System.out.println(skb3);
		
		
		
//		SnakeKafkaBean ssb =new SnakeKafkaBean();
//		 ssb.getSeparator();
//		 ssb.setTimeSlice(30);
//		 ssb.setTopics("topic");
//		 ssb.setComponent(0);
//		 ssb.setConfigType(0);
//		// toJson 将bean对象转换为json字符串
//		String jsonStr = gson.toJson(ssb, SnakeKafkaBean.class);
//		System.out.println(jsonStr);
		// fromJson 将json字符串转为bean对象
		
//		String jsonStr2="{\"bootstrapServers\":\"127.0.0.1\",\"port\":\"8888\",\"fields\":{\"c1\":\"字段1\",\"c2\":\"字段2\"}}";
//		SnakeSparkBean skb2= gson.fromJson(jsonStr2, SnakeSparkBean.class);
//		System.out.println(skb2);
//		SnakeKafkaBean skb3= gson.fromJson(jsonStr2, SnakeKafkaBean.class);
//		System.out.println(skb3);
		
//		SnakeGenerateFactory sgf=new SnakeGenerateFactory(null) ;
//		String jsonStr3="{\"bootstrapServers\":\"127.0.0.1:6667\",\"topics\":\"topic\",\"format\":\"json\",\"timeSlice\":30,\"configType\":0,\"component\":0,\"fields\":{\"c1\":\"字段1\",\"c2\":\"字段2\"}}";
//		
//		sgf.getComponentBeanByJSON(jsonStr3);
		
		
		
		/**
		 *流引擎 
		 */
//		SnakeSparkBean ssb =new SnakeSparkBean();
//		 ssb.setComponent(ComponentEnum.COMPONENT_SPARK.getValue());
//		 ssb.setConfigType(ConfigTypeEnum.CONFIG_SCRIPT.getValue());
//		 ssb.setTempView("table_name");
//		// toJson 将bean对象转换为json字符串
//		String jsonStr = gson.toJson(ssb, SnakeSparkBean.class);
//		System.out.println(jsonStr);
		
		/**
		 * 输出源
		 * */
//		SnakeKafkaBean ssb =new SnakeKafkaBean();
//		 ssb.setBootstrapServers("127.0.0.1");
//		 ssb.setComponent(0);
//		 ssb.setTopics("topic");
//		 ssb.setConfigType(1);
////		 ssb.setFormat(",");
//		// toJson 将bean对象转换为json字符串
//		String jsonStr = gson.toJson(ssb, SnakeKafkaBean.class);
//		System.out.println(jsonStr);
		
	}
	
	
 
	/**
	 * 生成脚本文件
	 * @return
	 */
	public String createScriptFile() {
		String filePath=null;
		//根据组件信息生成流式处理脚本文件
		if(this.engineType==ComponentEnum.COMPONENT_SPARK.getValue()) {
			//加载spark流处理模板
			SparkSqlTemplate sst=new SparkSqlTemplate();
			SnakeSparkBean ssb=(SnakeSparkBean) script.getT();
			SnakeKafkaBean skb=(SnakeKafkaBean)inPutDataSource.getT();
			//字段 和 时间片  存储在 kafka的json，为了不让前端再转，在这里重新解析一次kafka的json
			SnakeSparkBean ssb2 =(SnakeSparkBean) SnakeUtil.getBean(skb.getConfigJson(), SnakeSparkBean.class);
			
			sst.setAppName(appName);
			sst.setTempView(ssb.getTempView());
			sst.setFields(ssb2.getFiledStr());
			sst.setBatchDuration(ssb2.getBatchDuration());
			sst.setSqlScript(ssb.getScriptValue());
			sst.setInPutDataSource(inPutDataSource.getConfigResult());
			sst.setOutPutDataSource(outPutDataSource.getConfigResult());
			filePath=sst.createFile();
			
		}else if(this.engineType==ComponentEnum.COMPONENT_STORM.getValue()) {
			//加载storm流处理模板
		}
		
		return filePath;
		
	}
	 
	
	
	
}
