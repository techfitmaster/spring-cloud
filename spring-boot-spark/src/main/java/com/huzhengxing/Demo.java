package com.huzhengxing;

import org.apache.spark.ml.feature.MinMaxScaler;
import org.apache.spark.ml.feature.MinMaxScalerModel;
import org.apache.spark.ml.linalg.*;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.soundex;

/**
 * @author 2020/10/21 10:40  zhengxing.hu
 * @version 1.0.0
 * @file Demo
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
public class Demo {
    private static final SparkSession spark = SparkSession.builder()
//            .appName("Simple Application")
            .master("local[*]")
            .getOrCreate();


    /**
     * 模拟数据预处理
     * 1. 确定数据类型 id, 类别，数值
     * id: 校验重复
     * 数值： 校验是否是number
     * 类别：
     * <p>
     * <p>
     * 2. 校验数据库为空
     * <p>
     * <p>
     * <p>
     * 3. 预处理数据
     */
    private static void preprocessingData() {
        new Thread(() -> {
            while (true) {
            }
        }).start();
        StructType schema = new StructType(new StructField[]{
                new StructField("id", DataTypes.DoubleType, false, Metadata.empty()),
                new StructField("y", DataTypes.DoubleType, false, Metadata.empty()),
                new StructField("x0", DataTypes.DoubleType, false, Metadata.empty()),
                new StructField("x1", DataTypes.DoubleType, false, Metadata.empty()),
                new StructField("x2", DataTypes.DoubleType, false, Metadata.empty()),
                new StructField("x3", DataTypes.DoubleType, false, Metadata.empty()),
                new StructField("x4", DataTypes.DoubleType, false, Metadata.empty()),
                new StructField("x5", DataTypes.DoubleType, false, Metadata.empty()),
                new StructField("x6", DataTypes.DoubleType, false, Metadata.empty())
        });
        Dataset<Row> dataset = spark.read().option("header", true)
                .schema(schema)
                .csv("spring-boot-spark/breast_hetero_guest.csv");
        dataset.printSchema();
        AtomicReference<String> id = new AtomicReference<>("");
        AtomicInteger index = new AtomicInteger();
        dataset.foreach(row -> {
            for (int i = 0; i < row.length(); i++) {
                if (row.isNullAt(i)) {
                    id.set((String) row.get(0));
                    index.set(i);
                    System.out.println(row.get(0) + "," + i);
                }
            }
        });
        Dataset<Row> dataNotHandle = dataset.select("id", "y");
        dataNotHandle.show();
        Dataset<Row> dataOfDrop = dataset.drop("id", "y");
        dataOfDrop.show();
        Dataset<Row> describeData = dataOfDrop.describe();
        describeData.show();


        StructType schema1 = new StructType(new StructField[]{
                new StructField("id", DataTypes.DoubleType, false, Metadata.empty()),
                new StructField("features", new VectorUDT(), false, Metadata.empty())
        });


        List<Row> data = new ArrayList<>();

//        dataset.foreach(row -> {
//            double[] tmp = new double[row.length() - 1];
//            for (int i = 1; i < tmp.length; i++) {
//                tmp[i - 1] = row.getDouble(i);
//            }
//            data.add(RowFactory.create(row.get(0), Vectors.dense(tmp)));
//        });

        //确定是否这样做？
        List<Row> rows = dataset.collectAsList();
        for (int i = 1; i < rows.size(); i++) {
            Row row = rows.get(i);
            double[] tmp = new double[row.length() - 1];
            for (int j = 1; j < tmp.length; j++) {
                tmp[j - 1] = row.getDouble(j);
            }
            data.add(RowFactory.create(row.get(0), Vectors.dense(tmp)));
        }

//        Dataset<Row> filter = dataset.foreach();
//        filter.show();

        Dataset<Row> dataFrame = spark.createDataFrame(data, schema1);
        dataFrame.show(false);
        MinMaxScaler minMaxScaler = new MinMaxScaler().setInputCol("features").setOutputCol("y0");
        MinMaxScalerModel maxScalerModel = minMaxScaler.fit(dataFrame);
        Dataset<Row> transform = maxScalerModel.transform(dataFrame);
        transform.show(false);
        Dataset<Row> y0 = transform.select("y0");
        y0.foreach(row -> {
            Vector vector = (Vector) row.get(0);
            double[] values = vector.toArray();
            System.out.println(Arrays.toString(values));
//            System.out.println(row.getJavaMap(0));
        });
        System.out.println(y0);

    }


    public static void main(String[] args) {
        preprocessingData();
    }
}
