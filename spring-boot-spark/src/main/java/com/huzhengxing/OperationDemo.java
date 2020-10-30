package com.huzhengxing;

import java.util.*;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.api.java.function.MapPartitionsFunction;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.*;
import scala.Int;
import scala.collection.generic.SeqFactory;

/**
 * 各种算子测试
 * 1. 都是通过Executor执行
 *
 * <a>http://spark.apache.org/docs/latest/rdd-programming-guide.html#resilient-distributed-datasets-rdds</a>
 *
 *
 */
public class OperationDemo {

    private static final SparkSession spark = SparkSession.builder()
            // .appName("Simple Application")
            .master("local[*]").getOrCreate();
    private Dataset<Row> dataset = spark.read().option("header", true).csv(Constants.SAMPLE);

    private static final SparkConf conf = new SparkConf().setAppName("appName").setMaster("local[*]");
    private static final SparkContext sc = new SparkContext(conf);


    public void testMap() {
        dataset.show(false);
        Encoder<String> rowEncoder = Encoders.STRING();
        Dataset<String> strDataset = dataset.map((MapFunction<Row, String>) r -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < r.length(); i++) {
                sb.append(r.get(i));
            }
            return sb.toString();
        }, rowEncoder);
        strDataset.show(false);
    }

    /**
     * 
     *  1.速度快
     *  2.可能出现OOM
     * 
     */
    public void testMapPartition() {
        Encoder<String> rowEncoder = Encoders.STRING();
        Dataset<String> strDataset = dataset.mapPartitions((MapPartitionsFunction<Row, String>) row -> {
            List<String> list = new ArrayList<>();
            row.forEachRemaining(r->{
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < r.length(); i++) {
                    sb.append(r.get(i));
                }
                list.add(sb.toString());
            });
           return list.iterator();
        }, rowEncoder);
        strDataset.show(false);
        Dataset<Row> intersect = dataset.intersect(dataset);
    }

   
    public void testMapFlatMap() {
        Encoder<String> rowEncoder = Encoders.STRING();
        Dataset<String> strDataset = dataset.flatMap((FlatMapFunction<Row, String>) r-> {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < r.length(); i++) {
                list.add(r.getString(i));
            }
            return list.iterator();
        }, rowEncoder);
        strDataset.show(false);
    }


    public void testSample() {
        Dataset<Row> testCsvDataset = spark.read().csv(Constants.TEST_CSV);
        JavaRDD<Row> rowJavaRDD = testCsvDataset.toJavaRDD();
        testCsvDataset.show();
        testCsvDataset.sample(10).show();
    }

    public void testUnion() {
        dataset.show(false);
        dataset.union(dataset).show(false);
    }

    /**
     * 交集
     */
    public void testIntersection() {
        dataset.show(false);
        dataset.intersect(dataset).show(false);
        
    }

    /**
     * 将分区的数据库
     */
    public void testGlom() {

        RDD<Row> rdd = dataset.rdd();

        RDD<Object> glom = rdd.glom();


    }


    public static void main(String[] args) {
        OperationDemo operationDemo = new OperationDemo();
//        operationDemo.testMap();
//        operationDemo.testMapPartition();
//        operationDemo.testMapFlat();
//        operationDemo.testSample();
//        operationDemo.testUnion();
        operationDemo.testIntersection();
    }

}
