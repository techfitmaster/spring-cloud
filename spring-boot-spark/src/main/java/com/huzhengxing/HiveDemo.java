package com.huzhengxing;

import org.apache.spark.sql.SparkSession;

import java.io.File;

/**
 * @author 2020/11/18 10:22  zhengxing.hu
 * @version 1.0.0
 * @file HiveDemo
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
public class HiveDemo {
    String warehouseLocation = new File("spark-warehouse").getAbsolutePath();
    SparkSession spark = SparkSession
            .builder()
            .appName("Java Spark Hive Example")
            .config("spark.sql.warehouse.dir", warehouseLocation)
            .enableHiveSupport()
            .getOrCreate();


    public void test() {

        

    }
}

