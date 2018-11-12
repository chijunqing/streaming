package stromkafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class WordCountBolt extends BaseBasicBolt {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OutputCollector collector;
    //�洢���ʺͶ�Ӧ�ļ���
    private HashMap<String, Long> counts = null;//ע���������л���������prepare��ʵ����

    /**
     * �󲿷�ʵ������ͨ������prepare()�н���ʵ������������ģʽ����topology�Ĳ���ʽ������
     * ��Ϊ�ڲ�������ʱ,���spout��bolt���������Ϸ��͵����л���ʵ��������
     * ���spout��bolt���κ�non-serializableʵ�����������л�֮ǰ��ʵ����(����,�ڹ��캯���д���)
     * ���׳�NotSerializableException�������˽��޷�������
     * ��������ΪHashMap �ǿ����л���,���Կ��԰�ȫ���ڹ��캯����ʵ������
     * ���ǣ�ͨ�������������ڹ��캯���жԻ����������ͺͿ����л��Ķ�����и��ƺ�ʵ����
     * ����prepare()�����жԲ������л��Ķ������ʵ������
     */
    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        // TODO Auto-generated method stub
        this.collector = collector;
        this.counts = new HashMap<String, Long>();
    }

    /**
     * ��execute()������,���ǲ��ҵ��յ��ĵ��ʵļ���(��������ڣ���ʼ��Ϊ0)
     * Ȼ�����Ӽ������洢,����һ���µĴʺ͵�ǰ������ɵĶ�Ԫ�顣
     * ���������Ϊ���������˵�����bolt���ĺ�ִ�ж���Ĵ���
     */
    @Override
    public void execute(Tuple input,BasicOutputCollector collector) {
        // TODO Auto-generated method stub

        String word = input.getStringByField("word");
        Long count = this.counts.get(word);
        if (count == null) {
            count = 0L;//��������ڣ���ʼ��Ϊ0
        }
        count++;//���Ӽ���
        this.counts.put(word, count);//�洢����
        collector.emit(new Values(word,count));
    }

    /**
     * 
     */
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // TODO Auto-generated method stub
        //����һ�������������tuple�����˵��ʺͶ�Ӧ�ļ����������
        //����bolt���Զ��������������һ������
        declarer.declare(new Fields("word","count"));
    }

	 
}
