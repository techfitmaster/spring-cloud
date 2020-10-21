package com.huzhengxing;

import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * @author 2020/10/21 11:00  zhengxing.hu
 * @version 1.0.0
 * @file DataSetDemo
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
public class DataSetDemo {
    private static final SparkSession spark = SparkSession.builder()
//            .appName("Simple Application")
            .master("local[*]")
            .getOrCreate();


    public static void main(String[] args) {
        Dataset<Row> df = spark.read().option("header", true).csv("spring-boot-spark/breast_hetero_guest.csv");
        df.createOrReplaceTempView("data");
        df.show();
        df.printSchema();
        df.describe().show();
        df.foreach((ForeachFunction<Row>) System.out::println);


    }
}
