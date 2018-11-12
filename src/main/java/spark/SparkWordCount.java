package spark;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class SparkWordCount {

	  public static void main(String[] args) throws Exception {


        SparkConf sparkconf = new SparkConf().setAppName("JavaWordCount").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(sparkconf);
        JavaRDD<String> lines = sc.textFile("G:\\other\\word.txt");

        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String,String>(){

            public Iterator<String> call (String s) {
                return  Arrays.asList(s.split(" ")).iterator();
            }
        });

        JavaPairRDD<String,Integer> ones = words.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<String,Integer>(s,1);
            }
        });

        JavaPairRDD<String,Integer> counts = ones.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer i1, Integer i2) throws Exception {
                return i1+i2;
            }
        });

        List<Tuple2<String,Integer>> output = counts.collect();
        for(Tuple2<String,Integer> t:output){
            System.out.println(t._1()+ "=>" +t._2());
        }

        sc.stop();
    }
}