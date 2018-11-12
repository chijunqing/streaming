package stromkafka;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

public class ReportBolt extends BaseBasicBolt {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, Long> counts = null;//���浥�ʺͶ�Ӧ�ļ���

	@Override
    public void prepare(Map stormConf, TopologyContext context) {
        // TODO Auto-generated method stub

        this.counts = new HashMap<String, Long>();
    }

    public void execute(Tuple input,BasicOutputCollector collector) {
        // TODO Auto-generated method stub

        String word = input.getStringByField("word");
        Long count = input.getLongByField("count");
        this.counts.put(word, count);

        //ʵʱ���
        System.out.println("���:"+this.counts);
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // TODO Auto-generated method stub
        //������ĩ��bolt������Ҫ�������������������趨��
    	System.out.println("===============end===================");
    	System.out.println(declarer);
    	System.out.println("===============end===================");
    }

    /**
     * cleanup��IBolt�ӿ��ж���
     * Storm����ֹһ��bolt֮ǰ������������
     * ������������cleanup()������topology�ر�ʱ������յļ������
     * ͨ������£�cleanup()���������ͷ�boltռ�õ���Դ����򿪵��ļ���������ݿ�����
     * ���ǵ�Storm������һ����Ⱥ�����У�IBolt.cleanup()�������ܱ�ִ֤�У������ǿ���ģʽ������������Ҫ����������
     */
    public void cleanup(){
        System.out.println("---------- FINAL COUNTS -----------");

        ArrayList<String> keys = new ArrayList<String>();
        keys.addAll(this.counts.keySet());
        Collections.sort(keys);
        for(String key : keys){
            System.out.println(key + " : " + this.counts.get(key));
        }
        System.out.println("----------------------------");
    }

}
