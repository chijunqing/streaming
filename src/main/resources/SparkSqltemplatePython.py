from __future__ import print_function
from pyspark.sql import SparkSession
from pyspark import SparkContext
from pyspark.streaming import StreamingContext
from pyspark.streaming.kafka import KafkaUtils
from pyspark.sql.types import *
import json
from kafka import KafkaProducer

#��ʼ��
def init():
    sc = SparkContext(appName="{0}")
    # ��ʼ��Spark������
    ssc = StreamingContext(sc, {1})
    return ssc

# ������������-���ݸ������ö�̬�滻
def inputDataSource(ssc):
    {2}

# �����������-���ݸ������ö�̬�滻
def outputDataSource(message):
    {3}

#�������ݽṹ����rddת��dataFrame-���ݸ������ö�̬�滻
def getDataFrame(spark,rdd):
    schemaString = "{4}"
    fields = [StructField(field_name, StringType(), True) for field_name in schemaString.split(" ")]
    schema = StructType(fields)
    dataFrame = spark.read.schema(schema).json(rdd);
    return dataFrame

#ִ��sql ��ѯ����������-���ݸ������ö�̬�滻
def sql_select(spark,rdddata):
    rdddata.createOrReplaceTempView("{5}")
    dataFrame = spark.sql("{6} ")
    return dataFrame


#���ݴ�������-ģ��̶�����
def process(time, rdd):
    print("========= %s =====ִ��ʱ��====" % str(time))
    try:
        # Get the singleton instance of SparkSession
        spark = getSparkSessionInstance(rdd.context.getConf())
        dataFrame=getDataFrame(spark,rdd)
        resultDataFrame =sql_select(spark, dataFrame);
        resultDataFrame.show();
        resultDataFrame.foreach(outputDataSource)
    except BaseException as e:
        print(e);

#ģ���е�����-�̶�
def getSparkSessionInstance(sparkConf):
    if ('sparkSessionSingletonInstance' not in globals()):
        globals()['sparkSessionSingletonInstance'] = SparkSession\
            .builder\
            .config(conf=sparkConf)\
            .getOrCreate()
    return globals()['sparkSessionSingletonInstance']

#ģ��̶�����
if __name__ == "__main__":
    #��ʼ��������
    ssc=init();
    #��ȡ����Դ
    words=inputDataSource(ssc);
    #���ݴ���
    words.foreachRDD(process)
    ssc.start()
    ssc.awaitTermination()

