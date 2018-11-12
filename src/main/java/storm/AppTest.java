package storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class AppTest {
	private static final String SENTENCE_SPOUT_ID = "sentence-spout";
    private static final String SPLIT_BOLT_ID = "split-bolt";
    private static final String COUNT_BOLT_ID = "count-bolt";
    private static final String REPORT_BOLT_ID = "report-bolt";
    private static final String TOPOLOGY_NAME = "word-count-topology";

    public static void main( String[] args ) throws AlreadyAliveException, InvalidTopologyException, AuthorizationException //throws Exception
    {
        //System.out.println( "Hello World!" );
        //ʵ����spout��bolt

        SentenceSpout spout = new SentenceSpout();
        SplitSentenceBolt splitBolt = new SplitSentenceBolt();
        WordCountBolt countBolt = new WordCountBolt();
        ReportBolt reportBolt = new ReportBolt();

        TopologyBuilder builder = new TopologyBuilder();//������һ��TopologyBuilderʵ��

        //TopologyBuilder�ṩ��ʽ����API������topology���֮���������
        
        

        //builder.setSpout(SENTENCE_SPOUT_ID, spout);//ע��һ��sentence spout

        //��������Executeor(�߳�)��Ĭ��һ��
        builder.setSpout(SENTENCE_SPOUT_ID, spout,1);

        // SentenceSpout --> SplitSentenceBolt

        //ע��һ��bolt������sentence���������������shuffleGrouping��������StormҪ��SentenceSpout�����tuple������ȵķַ���SplitSentenceBolt��ʵ��
        //builder.setBolt(SPLIT_BOLT_ID, splitBolt).shuffleGrouping(SENTENCE_SPOUT_ID);

        //SplitSentenceBolt���ʷָ�������4��Task��2��Executeor(�߳�)
        builder.setBolt(SPLIT_BOLT_ID, splitBolt,1).setNumTasks(1).shuffleGrouping(SENTENCE_SPOUT_ID);

        // SplitSentenceBolt --> WordCountBolt

        //fieldsGrouping�������ض����ݵ�tuple·�ɵ������boltʵ����
        //����fieldsGrouping()������֤���С�word���ֶ���ͬ��tuuple�ᱻ·�ɵ�ͬһ��WordCountBoltʵ����
        //builder.setBolt(COUNT_BOLT_ID, countBolt).fieldsGrouping( SPLIT_BOLT_ID, new Fields("word"));

        //WordCountBolt���ʼ���������4��Executeor(�߳�)
        builder.setBolt(COUNT_BOLT_ID, countBolt,1).fieldsGrouping( SPLIT_BOLT_ID, new Fields("word"));

        // WordCountBolt --> ReportBolt

        //globalGrouping�ǰ�WordCountBolt���������tuple·�ɵ�Ψһ��ReportBolt
        builder.setBolt(REPORT_BOLT_ID, reportBolt).globalGrouping(COUNT_BOLT_ID);


        Config config = new Config();//Config����һ��HashMap<String,Object>�����࣬��������topology����ʱ����Ϊ
        //����worker����
        //config.setNumWorkers(2);
        LocalCluster cluster = new LocalCluster();
//        //�����ύ
        cluster.submitTopology(TOPOLOGY_NAME, config, builder.createTopology());
        
//        StormSubmitter.submitTopology("kafkaboltTest", config, builder.createTopology());

//        Utils.sleep(20000);
//        cluster.killTopology(TOPOLOGY_NAME);        
//        cluster.shutdown();

    }
}
