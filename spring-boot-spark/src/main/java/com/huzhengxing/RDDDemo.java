package com.huzhengxing;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.util.Arrays;

/**
 * @author 2020/10/21 14:06  zhengxing.hu
 * @version 1.0.0
 * @file RDDDemo
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
public class RDDDemo {

    private static final SparkConf conf = new SparkConf().setAppName("appName").setMaster("local[*]");
    private static final JavaSparkContext sc = new JavaSparkContext(conf);


    public static void main(String[] args) {
        JavaRDD<String> stringJavaRDD = sc.textFile("spring-boot-spark/breast_hetero_guest.csv");
//        JavaRDD<String> filter = stringJavaRDD.filter((s,t) -> {s.split(" "));

        JavaRDD<String> parallelize = sc.parallelize(Arrays.asList("1", "2"));
//        parallelize.saveAsTextFile("output");


    }


}
