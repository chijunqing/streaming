package spark;

import java.io.IOException;

import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;

public class SparkLanuncher {
	 public static void main(String[] args) throws IOException {
	        SparkAppHandle handler = new SparkLauncher()
	                .setAppName("hello-world")
	                .setSparkHome("/usr/hdp/current/spark2-client")
	                .setMaster("yarn")
	                .setConf("spark.driver.memory", "1g")
	                .setConf("spark.executor.memory", "1g")
	                .setConf("spark.executor.cores", "1")
	                .setAppResource("./Spark-Sql-kafka2.py")
	                //此处应写类的全限定名
	                .setMainClass("HelloWorld")
	                .addAppArgs("I come from Launcher")
	                .setDeployMode("cluster")
	                .addJar("./spark-streaming-kafka-0-10_2.11-2.0.2.jar")
	                .addJar("./spark-sql-kafka-0-10_2.11-2.0.2.jar")
	                .addJar("./kafka-clients-0.10.2.0.jar")
//	                .setConf("spark.app.id", "11222")
//	                .setConf("spark.driver.memory", "2g")
//	                .setConf("spark.akka.frameSize", "200")
//	                .setConf("spark.executor.memory", "1g")
//	                .setConf("spark.executor.instances", "32")
//	                .setConf("spark.executor.cores", "3")
//	                .setConf("spark.default.parallelism", "10")
//	                .setConf("spark.driver.allowMultipleContexts", "true")
	                .startApplication(new SparkAppHandle.Listener(){
	                    @Override
	                    public void stateChanged(SparkAppHandle handle) {
	                        System.out.println("**********  state  changed  **********");
	                    }

	                    @Override
	                    public void infoChanged(SparkAppHandle handle) {
	                        System.out.println("**********  info  changed  **********");
	                    }
	                });


	        while(!"FINISHED".equalsIgnoreCase(handler.getState().toString()) && !"FAILED".equalsIgnoreCase(handler.getState().toString())){
	            System.out.println("id    "+handler.getAppId());
	            System.out.println("state "+handler.getState());

	            try {
	                Thread.sleep(10000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	        
	 
	    }
}
