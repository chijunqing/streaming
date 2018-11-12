package stromkafka;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class SplitSentenceBolt extends BaseBasicBolt {

	//BaseRichBolt��IComponent��IBolt�ӿڵ�ʵ��
    //�̳�����࣬�Ͳ���ȥʵ�ֱ��������ĵķ���

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OutputCollector collector;

    /**
     * prepare()����������ISpout ��open()������
     * ���������blot��ʼ��ʱ���ã���������׼��bolt�õ�����Դ,�������ݿ����ӡ�
     * �����Ӻ�SentenceSpout��һ��,SplitSentenceBolt�಻��Ҫ̫�����ĳ�ʼ��,
     * ����prepare()����ֻ����OutputCollector��������á�
     */
	@Override
    public void prepare(Map stormConf, TopologyContext context) {
        // TODO Auto-generated method stub
        this.collector=collector;

    }

    /**
     * SplitSentenceBolt���Ĺ���������IBolt����execute()���������������IBolt�ӿ��ж��塣
     * ÿ��Bolt��������һ�����ĵ�tuple������������������
     * ������,�յ���Ԫ���в��ҡ�sentence����ֵ,
     * ������ֵ��ֳɵ����Ĵ�,Ȼ�󰴵��ʷ����µ�tuple��
     */
    public void execute(Tuple input, BasicOutputCollector collector) {
        // TODO Auto-generated method stub
//        String sentence = input.getStringByField("sentence");
        String value=input.getStringByField("value");
//        String value=input.getStringByField("str");

        String[] words = value.split(" ");
        for (String word : words) {
            collector.emit(new Values(word));//����һ��bolt��������
        }       
    }

    /**
     * plitSentenceBolt�ඨ��һ��Ԫ����,ÿ������һ���ֶ�(��word��)��
     */
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // TODO Auto-generated method stub
        declarer.declare(new Fields("word"));
    }

 

}
