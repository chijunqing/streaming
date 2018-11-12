package kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;

import com.esotericsoftware.kryo.serializers.DefaultSerializers.StringSerializer;

public class Producer {

	
	public static void main(String[] args) {
		Properties props = new Properties();
//        props.put("bootstrap.servers", "master:9092,slave1:9092,slave2:9092");
		props.put("bootstrap.servers", "sdc13:9092,sdc10:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
//        producer.send(record)
	}
}
