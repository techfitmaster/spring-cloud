package com.huzhengxing;

import org.apache.spark.sql.SparkSession;

/**
 * spark session 应用
 * 
 * 
 */
public class QuickStart {

    SparkSession spark = SparkSession.builder().appName("Java Spark SQL basic example")
            .config("spark.some.config.option", "some-value").getOrCreate();

    public void createDataFrames() {
        spark.read().json("");
    }

}
