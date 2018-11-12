package stromkafka;

import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;

public class Report extends BaseFunction {

    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        for(String word: tuple.getString(0).split(" ")) {
            System.out.println("word:"+word);
        }
    }
    
}
