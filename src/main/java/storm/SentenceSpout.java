package storm;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class SentenceSpout extends BaseRichSpout {
	
	   //BaseRichSpout��ISpout�ӿں�IComponent�ӿڵļ�ʵ�֣��ӿڶ��ò����ķ����ṩ��Ĭ�ϵ�ʵ��

    private SpoutOutputCollector collector;
    private int index=0;
    private String[] sentences = {
            "hello word",
            "test"
    };

    /**
     * nextTuple()�������κ�Spoutʵ�ֵĺ��ġ�
     * Storm��������������������collector����tuple��
     * ������,����ֻ�Ƿ�����ǰ�����ľ��ӣ������Ӹ�����׼��������һ�����ӡ�
     */
    public void nextTuple() {
        //collector.emit(new Values("hello world this is a test"));

        // TODO Auto-generated method stub
        this.collector.emit(new Values(sentences[index]));
        index++;
        if (index>=sentences.length) {
            index=0;
        }
        Utils.sleep(10000);
    }
    /**
     * open()��������ISpout�ӿ��ж��壬��Spout�����ʼ��ʱ�����á�
     * open()������������:һ������Storm���õ�Map,һ��TopologyContext�����ṩ��topology���������Ϣ,SpoutOutputCollector�����ṩ����tuple�ķ�����
     * �����������,���ǲ���Ҫִ�г�ʼ��,ֻ�Ǽ򵥵Ĵ洢��һ��SpoutOutputCollectorʵ��������
     */
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        // TODO Auto-generated method stub
        this.collector = collector;
    }


    /**
     * declareOutputFields����IComponent�ӿ��ж���ģ�����Storm�������spout��bolt��������ʵ������ӿ�
     * ���ڸ���Storm��������ᷢ����Щ��������ÿ������tuple���������ֶ�
     */
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

        declarer.declare(new Fields("sentence"));//���������������������sentence�ֶ�

    }

}
