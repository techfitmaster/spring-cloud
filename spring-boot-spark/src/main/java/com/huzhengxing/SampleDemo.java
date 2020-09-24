package com.huzhengxing;


import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import scala.collection.Seq;

import java.util.Arrays;
import java.util.List;

/**
 * @author 2020/9/22 18:12  zhengxing.hu
 * @version 1.0.0
 * @file SmapleDemo
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
public class SampleDemo {
    private static final SparkSession spark = SparkSession.builder()
//            .appName("Simple Application")
            .master("local[*]")
            .getOrCreate();

    public static void testReadTime() {
        String path = "/Users/Albert/Nvxclouds/anonymous1_200000.csv";
        long start = System.currentTimeMillis();
        Dataset<String> stringDataset = spark.read().textFile(path);
//        spark.read().csv(path);

        System.out.println("read file time:" + (System.currentTimeMillis() - start));
    }

    public static void testReadTextFile() {
        Dataset<Row> dataset = spark.read().text("/Users/Albert/Nvxclouds/ID_matching--1599677219265.csv");
        dataset.show();
        Dataset<Row> describe = dataset.describe();
        describe.show();
    }

    public static void main(String[] args) {
//        testReadTime();
        testReadTextFile();
    }
}
