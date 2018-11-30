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
	 * Ӧ������
	 */
	private String appName;
	
	/**
	 * �����ű�����
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
	 * ����Դ
	 */
	private SnakeComponentGenerate inPutDataSource;
	
	/**
	 * ���Դ
	 */
	private SnakeComponentGenerate outPutDataSource;

	/**
	 * ��ʽ����ű�  
	 */
	private SnakeComponentGenerate script;
	
	/**
	 * ��Ⱥ����
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
			//����json���ã���ȡ���������Ϣ
			for(String json:configJson) {
				//����json���� ����������ã�kafka��spark....��
				configJson(json);
			}
		}
	}
	/**
	 * ����json��Ϣ ʵ�����������
	 * @param json
	 * @return
	 */
	private void configJson(String json) {
		Preconditions.checkNotNull(json,"json��������ȷ");
		
		BaseBean bb=  (BaseBean) SnakeUtil.getBean(json,BaseBean.class);
		Preconditions.checkNotNull(bb,"json��������ȷ");
		
		//��������Դ����
		if(bb.getConfigType()==ConfigTypeEnum.CONFIG_INPUT.getValue()) {
			//����json ���к�kafka���ö���
			this.inPutDataSource=getInPutDataSource(bb,json);;
		}else if(bb.getConfigType()==ConfigTypeEnum.CONFIG_OUTPUT.getValue()) {
			//�����������
			this.outPutDataSource=getOutPutDataSource(bb,json);
		}else if(bb.getConfigType()==ConfigTypeEnum.CONFIG_SCRIPT.getValue()) {
			//���ű�����
			this.script=getScripts(bb,json);
		}else if(bb.getConfigType()==ConfigTypeEnum.CONFIG_CLUSTER.getValue()) {
			//��������
			this.cluster=getCluster(bb, json);;
		}
	}
	
	/**
	 * ��ȡ����Դ
	 * @param bb
	 * @param json
	 * @return
	 */
	private SnakeComponentGenerate<SnakeKafkaBean> getInPutDataSource(BaseBean bb,String json) {
		SnakeComponentGenerate<SnakeKafkaBean> scg=null;
		//��������Դ-kafka���
		if( bb.getComponent()==ComponentEnum.COMPONENT_KAFKA.getValue()) {
			//����json ���к�kafka���ö���
			SnakeKafkaBean kafkaBean=(SnakeKafkaBean) SnakeUtil.getBean(json,SnakeKafkaBean.class) ;
			scg= new SnakeKafkaConsumerComponment(kafkaBean,scriptLanguage,engineType);
		}else if(bb.getComponent()==ComponentEnum.COMPONENT_HIVE.getValue()) {
			//TODO ��ʵ��
		}
		
		return scg;
	}
	
	
	/**
	 * ��ȡ���Դ
	 * @param bb 
	 * @param json
	 * @return
	 */
	private SnakeComponentGenerate<SnakeKafkaBean> getOutPutDataSource(BaseBean bb,String json) {
		SnakeComponentGenerate<SnakeKafkaBean> scg=null;
		//�������Դ-kafka���
		if( bb.getComponent()==ComponentEnum.COMPONENT_KAFKA.getValue()) {
			//����json ���к�kafka���ö���
			SnakeKafkaBean kafkaBean=(SnakeKafkaBean) SnakeUtil.getBean(json,SnakeKafkaBean.class) ;
			scg= new SnakeKafkaProductComponment(kafkaBean,scriptLanguage,engineType);
		}else if(bb.getComponent()==ComponentEnum.COMPONENT_HIVE.getValue()) {
			//TODO ��ʵ��
		}
		
		return scg;
	}

	
	/**
	 * �ű�
	 * @param bb 
	 * @param json
	 * @return
	 */
	private SnakeComponentGenerate<SnakeSparkBean> getScripts(BaseBean bb,String json) {
		SnakeComponentGenerate<SnakeSparkBean> scg=null;
		//��ʽ����ű�����
		if( bb.getComponent()==ComponentEnum.COMPONENT_SPARK.getValue()) {
			//����json ����sparkģ��
			SnakeSparkBean sparkBean=(SnakeSparkBean) SnakeUtil.getBean(json,SnakeSparkBean.class) ;
			scg= new SnakeSparkSqlComponent(sparkBean,scriptLanguage);
		}else if(bb.getComponent()==ComponentEnum.COMPONENT_HIVE.getValue()) {
			//TODO ��ʵ��
		}
		
		return scg;
	}

	
	/**
	 * ��ȡ���Դ
	 * @param bb 
	 * @param json
	 * @return
	 */
	private SnakeComponentGenerate<SnakeYarnBean> getCluster(BaseBean bb,String json) {
		SnakeComponentGenerate<SnakeYarnBean> scg=null;
		//��ʽ����ű�submit ��Ⱥ����
		if( bb.getComponent()==ComponentEnum.COMPONENT_YARN.getValue()) {
			//����json ��������
			SnakeYarnBean sparkBean=(SnakeYarnBean) SnakeUtil.getBean(json,SnakeYarnBean.class) ;
			scg= new SnakeYarnComponent(sparkBean,scriptLanguage,engineType);
		}else if(bb.getComponent()==ComponentEnum.COMPONENT_MESOS.getValue()) {
			//TODO ��ʵ��
		}
		
		return scg;
	}
	
	
	
	public static void main(String[] args) {
		// ʹ��new����
		Gson gson = new Gson();
//		SnakeSparkBean ssb =new SnakeSparkBean();
//		ssb.setEngine("spark");
//		ssb.setTempView("table");
//		 Map map=new HashMap();
//		 map.put("c1", "c1");
//		 map.put("c2", "c2");
//		ssb.setFields(map);
//		
//		// toJson ��bean����ת��Ϊjson�ַ���
//		String jsonStr = gson.toJson(ssb, SnakeSparkBean.class);
//		System.out.println(jsonStr);
//		// fromJson ��json�ַ���תΪbean����
//		
		String jsonStr2="{\"bootstrapServers\":\"127.0.0.1\",\"port\":\"8888\",\"fields\":{\"c1\":\"�ֶ�1\",\"c2\":\"�ֶ�2\"}}";
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
//		// toJson ��bean����ת��Ϊjson�ַ���
//		String jsonStr = gson.toJson(ssb, SnakeKafkaBean.class);
//		System.out.println(jsonStr);
		// fromJson ��json�ַ���תΪbean����
		
//		String jsonStr2="{\"bootstrapServers\":\"127.0.0.1\",\"port\":\"8888\",\"fields\":{\"c1\":\"�ֶ�1\",\"c2\":\"�ֶ�2\"}}";
//		SnakeSparkBean skb2= gson.fromJson(jsonStr2, SnakeSparkBean.class);
//		System.out.println(skb2);
//		SnakeKafkaBean skb3= gson.fromJson(jsonStr2, SnakeKafkaBean.class);
//		System.out.println(skb3);
		
//		SnakeGenerateFactory sgf=new SnakeGenerateFactory(null) ;
//		String jsonStr3="{\"bootstrapServers\":\"127.0.0.1:6667\",\"topics\":\"topic\",\"format\":\"json\",\"timeSlice\":30,\"configType\":0,\"component\":0,\"fields\":{\"c1\":\"�ֶ�1\",\"c2\":\"�ֶ�2\"}}";
//		
//		sgf.getComponentBeanByJSON(jsonStr3);
		
		
		
		/**
		 *������ 
		 */
//		SnakeSparkBean ssb =new SnakeSparkBean();
//		 ssb.setComponent(ComponentEnum.COMPONENT_SPARK.getValue());
//		 ssb.setConfigType(ConfigTypeEnum.CONFIG_SCRIPT.getValue());
//		 ssb.setTempView("table_name");
//		// toJson ��bean����ת��Ϊjson�ַ���
//		String jsonStr = gson.toJson(ssb, SnakeSparkBean.class);
//		System.out.println(jsonStr);
		
		/**
		 * ���Դ
		 * */
//		SnakeKafkaBean ssb =new SnakeKafkaBean();
//		 ssb.setBootstrapServers("127.0.0.1");
//		 ssb.setComponent(0);
//		 ssb.setTopics("topic");
//		 ssb.setConfigType(1);
////		 ssb.setFormat(",");
//		// toJson ��bean����ת��Ϊjson�ַ���
//		String jsonStr = gson.toJson(ssb, SnakeKafkaBean.class);
//		System.out.println(jsonStr);
		
	}
	
	
 
	/**
	 * ���ɽű��ļ�
	 * @return
	 */
	public String createScriptFile() {
		String filePath=null;
		//���������Ϣ������ʽ����ű��ļ�
		if(this.engineType==ComponentEnum.COMPONENT_SPARK.getValue()) {
			//����spark������ģ��
			SparkSqlTemplate sst=new SparkSqlTemplate();
			SnakeSparkBean ssb=(SnakeSparkBean) script.getT();
			SnakeKafkaBean skb=(SnakeKafkaBean)inPutDataSource.getT();
			//�ֶ� �� ʱ��Ƭ  �洢�� kafka��json��Ϊ�˲���ǰ����ת�����������½���һ��kafka��json
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
			//����storm������ģ��
		}
		
		return filePath;
		
	}
	 
	
	
	
}
