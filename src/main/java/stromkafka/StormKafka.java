package stromkafka;

import java.util.Properties;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.DRPCExecutionException;
import org.apache.storm.hbase.trident.mapper.SimpleTridentHBaseMapMapper;
import org.apache.storm.hbase.trident.state.HBaseMapState;
import org.apache.storm.kafka.BrokerHosts;
//import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.kafka.spout.KafkaSpoutRetryExponentialBackoff;
import org.apache.storm.kafka.spout.KafkaSpoutRetryExponentialBackoff.TimeInterval;
import org.apache.storm.kafka.spout.KafkaSpoutRetryService;
import org.apache.storm.kafka.trident.OpaqueTridentKafkaSpout;
import org.apache.storm.kafka.trident.TridentKafkaConfig;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.thrift.TException;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.state.StateFactory;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.testing.MemoryMapState;
import org.apache.storm.trident.testing.Split;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class StormKafka {
//	private static final String zkIp="127.0.0.1";
//	private static final String zkPort="2181";
//	private static final String kafkaIp="127.0.0.1";
//	private static final String kafkaPort="9092";
	private static final String zkIp="10.0.10.10";
	private static final String zkPort="2181";
	private static final String kafkaIp="10.0.10.10";
	private static final String kafkaPort="6667";
	private static final String topic="test5";
 
    public static void main(String[] args) throws DRPCExecutionException, AuthorizationException, TException{
    	
		TridentTopology topology = new TridentTopology();      
 
		OpaqueTridentKafkaSpout spout=(OpaqueTridentKafkaSpout) getKafkaSpout();
//		FixedBatchSpout spout=getFixeSpout();
		
		Config config = new Config();  
		 topology.newStream("sentencestream", spout)  
              .each(new Fields("str"), new Split(), new Fields("word"))  
              .groupBy(new Fields("word"))  
              .persistentAggregate(getHbaseStateFactory(config), new Count(), new Fields("count"))  
              .parallelismHint(1); 
	    
        Properties props = new Properties();
        // 配置Kafka broker地址
        props.put("metadata.broker.list", kafkaIp +":"+kafkaPort);
        // serializer.class为消息的序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        config.put("kafka.broker.properties", props);
        // 配置KafkaBolt生成的topic
        config.put("topic", topic);
	    config.setDebug(false);  
    	LocalCluster cluster = new LocalCluster();
    	cluster.submitTopology("TOPOLOGY_NAME",config,topology.build());
//    	StormSubmitter.bmitTopology("my-wordcount", config, topology.build());
    }
    
    public static StateFactory getHbaseStateFactory(Config config) {
//    	MyHbaseState.Options options	=	new	MyHbaseState.Options();	
//		options.setTableName("test5");  
//		options.setColumFamily("word");  
//		options.setQualifier("q1");  
//    	return new MyHbaseState.HbaseFactory(options);
//    	==========================================================
    	
    	
//    	Map<String,String>hbconfig=new HashMap<String,String>();
//    	hbconfig.put("hbase.rootdir", "hdfs://sdc13.sefonsoft.com:8020/apps/hbase/data");
//    	
//    	config.put("hbconfig", hbconfig);
//        TridentHBaseMapper tridentHBaseMapper = new SimpleTridentHBaseMapper()
//            .withColumnFamily("word")
//            .withColumnFields(new Fields("word"))
//            .withCounterFields(new Fields("count"))
//            .withRowKeyField("word");
//
//        HBaseValueMapper rowToStormValueMapper = new WordCountValueMapper();
//
//        HBaseProjectionCriteria projectionCriteria = new HBaseProjectionCriteria();
//        projectionCriteria.addColumn(new HBaseProjectionCriteria.ColumnMetaData("word", "count"));

//        HBaseState.Options options = new HBaseState.Options()
//            .withConfigKey("hbconfig")
//            .withDurability(Durability.SYNC_WAL)
//            .withMapper(tridentHBaseMapper)
//            .withProjectionCriteria(projectionCriteria)
//            .withRowToStormValueMapper(rowToStormValueMapper)
//            .withTableName("test5");
        
      //HBaseState使用HBaseMapState
        HBaseMapState.Options options = new HBaseMapState.Options();
        options.tableName = "test5";
        options.columnFamily = "word";
        options.mapMapper = new SimpleTridentHBaseMapMapper("q1");
        
        StateFactory factory =HBaseMapState.transactional(options); 
		
    	return factory;
    }
    
    public static StateFactory getMemoryMapStateFactory() {
    	 return new MemoryMapState.Factory();
    }
    
    public static OpaqueTridentKafkaSpout getKafkaSpout() {
    	
//    	String topicIn = "test"; //输入topic的名称,完整的目录为/kafka/brokers/topics/topic1		
    	BrokerHosts zk = new ZkHosts(zkIp+":"+zkPort);
    	TridentKafkaConfig spoutConf = new TridentKafkaConfig(zk, topic);
    	spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());
    	OpaqueTridentKafkaSpout spout = new OpaqueTridentKafkaSpout(spoutConf);
    	return spout;
    }
    

    
    public static KafkaSpout getKafkaSpout2() {
    	 // 配置Zookeeper地址
    	String brokerZkStr = zkIp+":"+zkPort;
        BrokerHosts brokerHosts = new ZkHosts(brokerZkStr);
        // 配置Kafka订阅的Topic，以及zookeeper中数据节点目录和名字
        SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, "test", "/", "topicMsgTopology");
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
        KafkaSpout spout= new KafkaSpout(spoutConfig);
        return spout;

    }
    
    public static FixedBatchSpout getFixeSpout() {
    	Fields fields = new Fields("str");
        FixedBatchSpout spout = new FixedBatchSpout(fields, 4,
                    new Values("storm"),
                    new Values("trident"),
                    new Values("needs"),
                    new Values("javadoc")
        );
        spout.setCycle(true);
        return spout;
    }
    protected static KafkaSpoutRetryService getRetryService() {
        return new KafkaSpoutRetryExponentialBackoff(TimeInterval.microSeconds(500),
            TimeInterval.milliSeconds(2), Integer.MAX_VALUE, TimeInterval.seconds(10));
    }
    
 }
