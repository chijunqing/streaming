package spark;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;

public class SparkTest {
	@SuppressWarnings("deprecation")
    public static void main(String[] args) throws InterruptedException {
         
        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("WordCountOnline");
       
        JavaStreamingContext jsc = new JavaStreamingContext(conf, Durations.seconds(3));
        JavaReceiverInputDStream<String> lines = jsc.socketTextStream("localhost", 9999);
        
        JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;
            public Iterator<String> call(String s) {
                return  (Iterator<String>) Arrays.asList(s.split(" "));
            }
        });

        JavaPairDStream<String, Integer> ones = words.mapToPair(new PairFunction<String, String, Integer>() {
            private static final long serialVersionUID = 1L;

            public Tuple2<String, Integer> call(String s) {
                return new Tuple2<String, Integer>(s, 1);
            }
        });

        JavaPairDStream<String, Integer> counts = ones.reduceByKey(new Function2<Integer, Integer, Integer>() {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            public Integer call(Integer i1, Integer i2) {
                return i1 + i2;
            }
        });
         
       
         counts.print();
         /*counts.foreachRDD(new VoidFunction<JavaPairRDD<String,Integer>>() {

            
            private static final long serialVersionUID = 1L;

            @Override
            public void call(JavaPairRDD<String, Integer> pairRDD) throws Exception {
                
                pairRDD.foreach(new VoidFunction<Tuple2<String,Integer>>() {

                    *//**
                     * 
                     *//*
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void call(Tuple2<String, Integer> tuple)
                            throws Exception {
                        System.out.println("tuple ---- "+tuple );
                    }
                });
            }
        });*/
         jsc.start();
         jsc.awaitTermination();
         jsc.stop(false);
    }
}
