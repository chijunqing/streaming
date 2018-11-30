from __future__ import print_function
from pyspark.sql import SparkSession
from pyspark import SparkContext
from pyspark.streaming import StreamingContext
from pyspark.streaming.kafka import KafkaUtils
from pyspark.sql.types import *
import json
from kafka import KafkaProducer

#初始化
def init():
    sc = SparkContext(appName="{0}")
    # 初始化Spark上下文
    ssc = StreamingContext(sc, {1})
    return ssc

# 定义数据输入-内容根据配置动态替换
def inputDataSource(ssc):
    {2}

# 定义数据输出-内容根据配置动态替换
def outputDataSource(message):
    {3}

#定义数据结构并将rdd转换dataFrame-内容根据配置动态替换
def getDataFrame(spark,rdd):
    schemaString = "{4}"
    fields = [StructField(field_name, StringType(), True) for field_name in schemaString.split(" ")]
    schema = StructType(fields)
    dataFrame = spark.read.schema(schema).json(rdd);
    return dataFrame

#执行sql 查询，过滤数据-内容根据配置动态替换
def sql_select(spark,rdddata):
    rdddata.createOrReplaceTempView("{5}")
    dataFrame = spark.sql("{6} ")
    return dataFrame


#数据处理流程-模板固定内容
def process(time, rdd):
    print("========= %s =====执行时间====" % str(time))
    try:
        # Get the singleton instance of SparkSession
        spark = getSparkSessionInstance(rdd.context.getConf())
        dataFrame=getDataFrame(spark,rdd)
        resultDataFrame =sql_select(spark, dataFrame);
        resultDataFrame.show();
        resultDataFrame.foreach(outputDataSource)
    except BaseException as e:
        print(e);

#模板中的内容-固定
def getSparkSessionInstance(sparkConf):
    if ('sparkSessionSingletonInstance' not in globals()):
        globals()['sparkSessionSingletonInstance'] = SparkSession\
            .builder\
            .config(conf=sparkConf)\
            .getOrCreate()
    return globals()['sparkSessionSingletonInstance']

#模板固定内容
if __name__ == "__main__":
    #初始化上下文
    ssc=init();
    #获取数据源
    words=inputDataSource(ssc);
    #数据处理
    words.foreachRDD(process)
    ssc.start()
    ssc.awaitTermination()

