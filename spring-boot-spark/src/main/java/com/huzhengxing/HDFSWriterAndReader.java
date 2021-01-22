package com.huzhengxing;

import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.ml.feature.MinMaxScaler;
import org.apache.spark.ml.feature.MinMaxScalerModel;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.linalg.VectorUDT;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.sparkproject.guava.primitives.Doubles;

import java.util.*;

import static org.apache.spark.sql.functions.col;

/**
 * @author 2020/11/13 16:22  zhengxing.hu
 * @version 1.0.0
 * @file HDFSWriterAndReader
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
public class HDFSWriterAndReader {

    private static final SparkSession spark = SparkSession.builder()
//            .appName("Simple Application")
            .master("local[*]")
            .getOrCreate();

//        private final Dataset<Row> dataset = spark.read()
//                .format("txt")
//                .option("header", true)
//                .option("inferSchema", true)
//                .text(DATA_10K);
//    private final Dataset<Row> dataset = spark.read()
//            .option("inferSchema", true)
//            .option("header", true)
//            .text(DATA_10K);

    public void saveFileToHDFS() {
//        dataset.createOrReplaceTempView("dataset");
//        Dataset<Row> header = spark.sql("select * from dataset limit 1");
//        dataset.show();
//        dataset.printSchema();
//        dataset.write().option("header",true).csv("hdfs://127.0.0.1:9000/data_10k.txt");
        Dataset<Row> dataset = spark.read().option("header", true).csv("/Users/Albert/Library/Containers/com.tencent.xinWeChat/Data/Library/Application\\ Support/com.tencent.xinWeChat/2.0b4.0.9/1837755c2b3e4bdc0227fd5ec306f3dd/Message/MessageTemp/250314f6743fb45a3c6754f9ac9282ae/File/10k/store_simulate_0_10000_y.csv ");
//        dataset.show();
        dataset.write().option("header", true).csv("hdfs://192.168.10.19:9000/data_10k.csv");
//        Row head = dataset.head();

//        Seq<Seq<String>> rows = dataset.getRows(0, 10);

//        System.out.println(head);
    }


    public void readTxt() {
        Dataset<Row> dataset = spark.read()
                .option("inferSchema", true)
                .option("header", true)
                .csv("hdfs://127.0.0.1:9000/data_200k.csv");
        List<String> strings = Arrays.asList("id", "dorky-peach-sheepdog-ordinal", "snazzy-harlequin-chicken-distraction");

        Column[] columns = new Column[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            columns[i] = col(strings.get(i));
        }
        Dataset<Row> id = dataset.select(columns);
        id.show();
    }


    public void readBySQL() {
        Dataset<Row> dataset = spark.read()
                .option("inferSchema", true)
                .option("header", true)
                .csv("hdfs://127.0.0.1:9000/data_200k.csv");
        dataset.show();
        dataset.createOrReplaceTempView("dataset");
//        spark.sql("select 'id','dorky-peach-sheepdog-ordinal','snazzy-harlequin-chicken-distraction' from dataset").show();

    }

    public void readByPage(int page, int perPage) {
        Dataset<Row> dataset = spark.read()
                .option("inferSchema", true)
                .option("header", true)
                .csv("hdfs://192.168.10.19:9000/data_200k.csv");
        dataset.show();
        String[] features = new String[]{"id", "x10", "x11"};
        List<String> strings = Arrays.asList(features);
        Column[] columns = new Column[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            columns[i] = col(strings.get(i));
        }
        Dataset<Row> rowDataset = dataset.select(columns);
        rowDataset.show(30);
        Dataset<Row> exceptDataset = rowDataset.limit(perPage * (page - 1));
        rowDataset.limit(perPage * page).except(exceptDataset).show();
    }

    public void testSql() {
        Dataset<Row> dataset = spark.read()
                .option("inferSchema", true)
                .option("header", true)
                .csv("hdfs://192.168.10.19:9000/data_200k.csv");
        dataset.show();
        String[] features = new String[]{"id", "x10", "x11"};
        List<String> strings = Arrays.asList(features);
        Column[] columns = new Column[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            columns[i] = col(strings.get(i));
        }
        Dataset<Row> limitData = dataset.limit(10);

        limitData.show();
        limitData.createOrReplaceTempView("data");
        String join = String.join(",", features);
        String sql = String.format("select %s from data", join);
        System.out.println("sql -> " + sql);
        Dataset<Row> sql1 = spark.sql(sql);
        sql1.createOrReplaceTempView("data2");
    }

    public void testMapFlat(int page, int perPage) {
        Dataset<Row> dataset = spark.read()
                .option("inferSchema", true)
                .option("header", true)
                .csv("hdfs://192.168.10.19:9000/data_200k.csv");
        dataset.show();
//        Encoder<String> rowEncoder = Encoders.STRING();
//        dataset.flatMap((FlatMapFunction<Row, String>) r->{
//
//        },rowEncoder);
    }


    public void testCastMethod() {
        Dataset<Row> dataset = spark.read()
                .option("inferSchema", true)
//                .option("header", true)
                .csv("hdfs://192.168.10.19:9000/data_200k.csv");
        dataset.printSchema();
        Column col = dataset.col("_c0");
        Column cast = col.cast(DataTypes.IntegerType);
        Dataset<Row> dataset1 = dataset.withColumn("_c0", cast);
        dataset1.printSchema();
        dataset1.show();
    }

    public void testMinMaxScalerOfficial() {
        List<Row> data = Arrays.asList(
                RowFactory.create(0, Vectors.dense(1.0, 0.1, 2.0, 1.0)),
                RowFactory.create(1, Vectors.dense(2.0, 1.1, 1.0, 2.0)),
                RowFactory.create(2, Vectors.dense(3.0, 10.1, 3.0, 3.0))
        );
        StructType schema = new StructType(new StructField[]{
                new StructField("id", DataTypes.IntegerType, false, Metadata.empty()),
                new StructField("features", new VectorUDT(), false, Metadata.empty())
        });
        Dataset<Row> dataFrame = spark.createDataFrame(data, schema);
        dataFrame.show();
        MinMaxScaler scaler = new MinMaxScaler()
                .setInputCol("features")
                .setOutputCol("scaledFeatures");

// Compute summary statistics and generate MinMaxScalerModel
        MinMaxScalerModel scalerModel = scaler.fit(dataFrame);

// rescale each feature to range [min, max].
        Dataset<Row> scaledData = scalerModel.transform(dataFrame);
        System.out.println("Features scaled to range: [" + scaler.getMin() + ", "
                + scaler.getMax() + "]");
        Dataset<Row> rowDataset = scaledData.select("features", "scaledFeatures");
        rowDataset.show();
        Iterator<Row> rowIterator = rowDataset.toLocalIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            org.apache.spark.ml.linalg.Vector vector = (Vector) row.get(1);
            double[] values = vector.toArray();
            System.out.println(Arrays.toString(values));
        }
    }

    public Column[] getColumnArrayBy(List<String> features, Dataset<Row> dataset) {
        Column[] columns = new Column[features.size()];
        for (int i = 0; i < features.size(); i++) {
            columns[i] = dataset.col(features.get(i));
        }
        return columns;
    }

    public void testMinMaxScaler() {
        List<String> features = Arrays.asList("x10", "x11", "x12", "x13");
        Dataset<Row> dataset = spark.read()
                .option("inferSchema", true)
                .option("header", true)
                .csv("hdfs://192.168.10.19:9000/data_200k.csv");
        dataset.show();


        Column[] columns = getColumnArrayBy(features, dataset);
        Dataset<Row> minMaxDataset = dataset.select(columns);
        List<Row> data = new ArrayList<>();
        List<Row> rows = minMaxDataset.collectAsList();
        for (int i = 0; i < rows.size(); i++) {
            Row row = rows.get(i);
            int length = row.length();
            double[] tmp = new double[length];
            for (int j = 0; j < length; j++) {
                tmp[j] = row.getDouble(j);
            }
            data.add(RowFactory.create(Vectors.dense(tmp)));
        }


        StructType schema = new StructType(
                new StructField[]{
//                        new StructField("id", DataTypes.IntegerType, false, Metadata.empty()),
                        new StructField("features", new VectorUDT(), false, Metadata.empty())});
        Dataset<Row> dataFrame = spark.createDataFrame(data, schema);
        MinMaxScaler scaler = new MinMaxScaler().setInputCol("features").setOutputCol("scaledFeatures");
        MinMaxScalerModel scalerModel = scaler.fit(dataFrame);
        Dataset<Row> transform = scalerModel.transform(dataFrame);
        transform.show(false);
        Encoder<Row> rowEncoder = Encoders.bean(Row.class);
//        transform.map((MapFunction<Row, Row>) row -> {
////            Vector vector = (Vector)  row.get(1);
////            double[] values = vector.toArray();
////            String list = Arrays.toString(values);
////            System.out.println(list);
////            return RowFactory.create(Doubles.asList(values));
//            return row;
//        }, rowEncoder).show();

        transform.filter((FilterFunction<Row>) r->{
         return true;
        }).show();

    }

    public static void main(String[] args) {
        HDFSWriterAndReader hdfsWriterAndReader = new HDFSWriterAndReader();
        hdfsWriterAndReader.saveFileToHDFS();
//        hdfsWriterAndReader.readTxt();
//        hdfsWriterAndReader.readBySQL();
//        List<String> strings = Arrays.asList("id", "dorky-peach-sheepdog-ordinal", "snazzy-harlequin-chicken-distraction");
//        System.out.println(String.join(",",strings));
//        hdfsWriterAndReader.readByPage(1, 10);
//        hdfsWriterAndReader.testSql();
//        hdfsWriterAndReader.testCastMethod();
//        hdfsWriterAndReader.testMinMaxScaler();
//        hdfsWriterAndReader.testMinMaxScalerOfficial();
    }

}
