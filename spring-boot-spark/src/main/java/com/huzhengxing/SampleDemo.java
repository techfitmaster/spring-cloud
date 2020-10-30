package com.huzhengxing;

import org.apache.spark.ml.attribute.Attribute;
import org.apache.spark.ml.attribute.AttributeGroup;
import org.apache.spark.ml.attribute.NumericAttribute;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.feature.*;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.linalg.VectorUDT;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.*;
import scala.collection.mutable.WrappedArray;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.spark.sql.functions.*;
import static org.apache.spark.sql.types.DataTypes.*;
// col("...") is preferable to df.col("...")

/**
 * @author 2020/9/22 18:12 zhengxing.hu
 * @version 1.0.0
 * @file SmapleDemo
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
public class SampleDemo {
        private static final SparkSession spark = SparkSession.builder()
                        // .appName("Simple Application")
                        .master("local[*]").getOrCreate();

        public static void testReadTime() throws FileNotFoundException {
                String path = "/Users/Albert/Nvxclouds/anonymous1_200000.csv";
                long start = System.currentTimeMillis();
                Dataset<String> stringDataset = spark.read().textFile(path);
                // spark.read().csv(path);

                System.out.println("read file time:" + (System.currentTimeMillis() - start));
        }

        public static void testReadTextFile() {
                Dataset<Row> dataset = spark.read().text("/Users/Albert/Nvxclouds/ID_matching--1599677219265.csv");
                dataset.show();
                Dataset<Row> describe = dataset.describe();
                describe.show();
                                

        }
        
        public static void main(String[] args) {
                // testReadTime();
                // testReadTextFile();
                // testOneHotEncoding();
                // testVectorAssembler();

                // testTF_IDF();
                // testWord2Vec();
                // testTokenizer();
                // testStopWordsRemover();
                // testN_Gram();
                // testBinarizer();
                // testPCA();
                // testPolynomialExpansion();
                // testVectorSlicer();
                // testMinMaxScalar();
                // testBucketizer();
                testDTC();
        }

        public static void testDTC() {
                List<Row> data = Arrays.asList(RowFactory.create(Vectors.dense(0.0, 1.0, -2.0, 3.0)),
                                RowFactory.create(Vectors.dense(-1.0, 2.0, 4.0, -7.0)),
                                RowFactory.create(Vectors.dense(14.0, -2.0, -5.0, 1.0)));
                StructType schema = new StructType(new StructField[] {
                                new StructField("features", new VectorUDT(), false, Metadata.empty()), });
                Dataset<Row> df = spark.createDataFrame(data, schema);
                df.show(false);
                DCT dct = new DCT().setInputCol("features").setOutputCol("featuresDCT").setInverse(false);

                Dataset<Row> dctDf = dct.transform(df);

                dctDf.show(false);
        }

        public static void testBucketizer() {
                double[] splits = { Double.NEGATIVE_INFINITY, -0.5, 0.0, 0.5, Double.POSITIVE_INFINITY };

                List<Row> data = Arrays.asList(RowFactory.create(-999.9), RowFactory.create(-0.5),
                                RowFactory.create(-0.3), RowFactory.create(0.0), RowFactory.create(0.2),
                                RowFactory.create(999.9));
                StructType schema = new StructType(new StructField[] {
                                new StructField("features", DataTypes.DoubleType, false, Metadata.empty()) });
                Dataset<Row> dataFrame = spark.createDataFrame(data, schema);

                Bucketizer bucketizer = new Bucketizer().setInputCol("features").setOutputCol("bucketedFeatures")
                                .setSplits(splits);

                // Transform original data into its bucket index.
                Dataset<Row> bucketedData = bucketizer.transform(dataFrame);

                System.out.println("Bucketizer output with " + (bucketizer.getSplits().length - 1) + " buckets");
                bucketedData.show();

                // Bucketize multiple columns at one pass.
                double[][] splitsArray = { { Double.NEGATIVE_INFINITY, -0.5, 0.0, 0.5, Double.POSITIVE_INFINITY },
                                { Double.NEGATIVE_INFINITY, -0.3, 0.0, 0.3, Double.POSITIVE_INFINITY } };

                List<Row> data2 = Arrays.asList(RowFactory.create(-999.9, -999.9), RowFactory.create(-0.5, -0.2),
                                RowFactory.create(-0.3, -0.1), RowFactory.create(0.0, 0.0), RowFactory.create(0.2, 0.4),
                                RowFactory.create(999.9, 999.9));
                StructType schema2 = new StructType(new StructField[] {
                                new StructField("features1", DataTypes.DoubleType, false, Metadata.empty()),
                                new StructField("features2", DataTypes.DoubleType, false, Metadata.empty()) });
                Dataset<Row> dataFrame2 = spark.createDataFrame(data2, schema2);

                Bucketizer bucketizer2 = new Bucketizer().setInputCols(new String[] { "features1", "features2" })
                                .setOutputCols(new String[] { "bucketedFeatures1", "bucketedFeatures2" })
                                .setSplitsArray(splitsArray);
                // Transform original data into its bucket index.
                Dataset<Row> bucketedData2 = bucketizer2.transform(dataFrame2);

                System.out.println("Bucketizer output with [" + (bucketizer2.getSplitsArray()[0].length - 1) + ", "
                                + (bucketizer2.getSplitsArray()[1].length - 1) + "] buckets for each input column");
                bucketedData2.show();
        }

        public static void testMinMaxScalar() {
                List<Row> data = Arrays.asList(RowFactory.create(0, Vectors.dense(1.0, 0.1, -1.0)),
                                RowFactory.create(1, Vectors.dense(2.0, 1.1, 1.0)),
                                RowFactory.create(2, Vectors.dense(3.0, 10.1, 3.0)));
                StructType schema = new StructType(new StructField[] {
                                new StructField("id", DataTypes.IntegerType, false, Metadata.empty()),
                                new StructField("features", new VectorUDT(), false, Metadata.empty()) });
                Dataset<Row> dataFrame = spark.createDataFrame(data, schema);

                MinMaxScaler scaler = new MinMaxScaler().setInputCol("features").setOutputCol("scaledFeatures");

                // Compute summary statistics and generate MinMaxScalerModel
                MinMaxScalerModel scalerModel = scaler.fit(dataFrame);

                // rescale each feature to range [min, max].
                Dataset<Row> scaledData = scalerModel.transform(dataFrame);
                System.out.println("Features scaled to range: [" + scaler.getMin() + ", " + scaler.getMax() + "]");
                scaledData.select("features", "scaledFeatures").show();
                scaledData.show(false);
        }

        ;

        public static void testVectorSlicer() {
                Attribute[] attrs = { NumericAttribute.defaultAttr().withName("f1"),
                                NumericAttribute.defaultAttr().withName("f2"),
                                NumericAttribute.defaultAttr().withName("f3") };
                AttributeGroup group = new AttributeGroup("userFeatures", attrs);

                List<Row> data = Arrays.asList(
                                RowFactory.create(Vectors.sparse(3, new int[] { 0, 1 }, new double[] { -2.0, 2.3 })),
                                RowFactory.create(Vectors.dense(-2.0, 2.3, 0.0)));

                Dataset<Row> dataset = spark.createDataFrame(data, (new StructType()).add(group.toStructField()));
                dataset.show();
                VectorSlicer vectorSlicer = new VectorSlicer().setInputCol("userFeatures").setOutputCol("features");

                // vectorSlicer.setIndices(new int[]{1}).setNames(new String[]{"f3"});
                vectorSlicer.setIndices(new int[] { 1 });
                // slicer.setNames(new String[]{"f2", "f3"})

                Dataset<Row> output = vectorSlicer.transform(dataset);
                output.show(false);
        }

        public static void testPolynomialExpansion() {
                PolynomialExpansion polyExpansion = new PolynomialExpansion().setInputCol("features")
                                .setOutputCol("polyFeatures").setDegree(3);

                List<Row> data = Arrays.asList(RowFactory.create(Vectors.dense(2.0, 1.0)),
                                RowFactory.create(Vectors.dense(0.0, 0.0)),
                                RowFactory.create(Vectors.dense(3.0, -1.0)));
                StructType schema = new StructType(new StructField[] {
                                new StructField("features", new VectorUDT(), false, Metadata.empty()), });
                Dataset<Row> df = spark.createDataFrame(data, schema);
                df.show(false);
                Dataset<Row> polyDF = polyExpansion.transform(df);
                polyDF.show(false);
        }

        public static void testPCA() {
                List<Row> data = Arrays.asList(
                                RowFactory.create(Vectors.sparse(5, new int[] { 1, 3 }, new double[] { 1.0, 7.0 })),
                                RowFactory.create(Vectors.dense(2.0, 0.0, 3.0, 4.0, 5.0)),
                                RowFactory.create(Vectors.dense(4.0, 0.0, 0.0, 6.0, 7.0)));

                StructType schema = new StructType(new StructField[] {
                                new StructField("features", new VectorUDT(), false, Metadata.empty()), });

                Dataset<Row> df = spark.createDataFrame(data, schema);
                df.show(false);
                PCAModel pca = new PCA().setInputCol("features").setOutputCol("pcaFeatures").setK(3).fit(df);

                Dataset<Row> result = pca.transform(df).select("pcaFeatures");
                result.show(false);
        }

        /**
         * 二进制话，设置一个阈值，如果大于这个阈值则是1反之0
         */
        public static void testBinarizer() {
                List<Row> data = Arrays.asList(RowFactory.create(0, 0.1), RowFactory.create(1, 0.8),
                                RowFactory.create(2, 0.2));
                StructType schema = new StructType(new StructField[] {
                                new StructField("id", DataTypes.IntegerType, false, Metadata.empty()),
                                new StructField("feature", DataTypes.DoubleType, false, Metadata.empty()) });
                Dataset<Row> continuousDataFrame = spark.createDataFrame(data, schema);
                continuousDataFrame.show(false);
                Binarizer binarizer = new Binarizer().setInputCol("feature").setOutputCol("binarized_feature")
                                .setThreshold(0.2);

                Dataset<Row> binarizedDataFrame = binarizer.transform(continuousDataFrame);

                System.out.println("Binarizer output with Threshold = " + binarizer.getThreshold());
                binarizedDataFrame.show(false);
        }

        public static void testN_Gram() {
                List<Row> data = Arrays.asList(
                                RowFactory.create(0, Arrays.asList("Hi", "I", "heard", "about", "Spark")),
                                RowFactory.create(1,
                                                Arrays.asList("I", "wish", "Java", "could", "use", "case", "classes")),
                                RowFactory.create(2, Arrays.asList("Logistic", "regression", "models", "are", "neat")),
                                RowFactory.create(3, Arrays.asList("是", "中国", "人", "我们")));

                StructType schema = new StructType(new StructField[] {
                                new StructField("id", DataTypes.IntegerType, false, Metadata.empty()),
                                new StructField("words", DataTypes.createArrayType(DataTypes.StringType), false,
                                                Metadata.empty()) });

                Dataset<Row> wordDataFrame = spark.createDataFrame(data, schema);
                wordDataFrame.show(false);
                NGram ngramTransformer = new NGram().setN(2).setInputCol("words").setOutputCol("ngrams");

                Dataset<Row> ngramDataFrame = ngramTransformer.transform(wordDataFrame);
                ngramDataFrame.select("ngrams").show(false);

        }

        public static void testStopWordsRemover() {
                StopWordsRemover remover = new StopWordsRemover().setInputCol("raw").setOutputCol("filtered");

                List<Row> data = Arrays.asList(RowFactory.create(Arrays.asList("I", "saw", "the", "red", "balloon")),
                                RowFactory.create(Arrays.asList("Mary", "had", "a", "little", "lamb")));

                StructType schema = new StructType(new StructField[] { new StructField("raw",
                                DataTypes.createArrayType(DataTypes.StringType), false, Metadata.empty()) });
                Dataset<Row> dataset = spark.createDataFrame(data, schema);
                dataset.show(false);
                remover.transform(dataset).show(false);

        }

        public static void testTokenizer() {
                List<Row> data = Arrays.asList(RowFactory.create(0, "Hi I heard about Spark"),
                                RowFactory.create(1, "I wish Java could use case classes"),
                                RowFactory.create(2, "Logistic,regression,models,are,neat"));

                StructType schema = new StructType(new StructField[] {
                                new StructField("id", DataTypes.IntegerType, false, Metadata.empty()),
                                new StructField("sentence", DataTypes.StringType, false, Metadata.empty()) });

                Dataset<Row> sentenceDataFrame = spark.createDataFrame(data, schema);
                sentenceDataFrame.show();
                Tokenizer tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words");

                RegexTokenizer regexTokenizer = new RegexTokenizer().setInputCol("sentence").setOutputCol("words")
                                .setPattern("\\W"); // alternatively .setPattern("\\w+").setGaps(false);

                spark.udf().register("countTokens", (WrappedArray<?> words) -> words.size(), DataTypes.IntegerType);

                Dataset<Row> tokenized = tokenizer.transform(sentenceDataFrame);
                tokenized.show();
                tokenized.select("sentence", "words").withColumn("tokens", callUDF("countTokens", col("words")))
                                .show(false);

                Dataset<Row> regexTokenized = regexTokenizer.transform(sentenceDataFrame);
                regexTokenized.show();
                regexTokenized.select("sentence", "words").withColumn("tokens", callUDF("countTokens", col("words")))
                                .show(false);
                regexTokenized.show();
        }

        public static void testWord2Vec() {
                // Input data: Each row is a bag of words from a sentence or document.
                List<Row> data = Arrays.asList(RowFactory.create(Arrays.asList("Hi I heard about Spark".split(" "))),
                                RowFactory.create(Arrays.asList("I wish Java could use case classes".split(" "))),
                                RowFactory.create(Arrays.asList("Logistic regression models are neat".split(" "))));
                StructType schema = new StructType(new StructField[] { new StructField("text",
                                new ArrayType(DataTypes.StringType, true), false, Metadata.empty()) });
                Dataset<Row> documentDF = spark.createDataFrame(data, schema);
                documentDF.show();
                // Learn a mapping from words to Vectors.
                Word2Vec word2Vec = new Word2Vec().setInputCol("text").setOutputCol("result").setVectorSize(3)
                                .setMinCount(0);

                Word2VecModel model = word2Vec.fit(documentDF);
                Dataset<Row> result = model.transform(documentDF);
                result.show();
                for (Row row : result.collectAsList()) {
                        List<String> text = row.getList(0);
                        Vector vector = (Vector) row.get(1);
                        System.out.println("Text: " + text + " => \nVector: " + vector + "\n");
                }
        }

        public static void testTF_IDF() {
                List<Row> data = Arrays.asList(RowFactory.create(0.0, "Hi I heard about Spark"),
                                RowFactory.create(0.0, "I wish Java could use case classes"),
                                RowFactory.create(1.0, "Logistic regression models are neat"));
                StructType schema = new StructType(new StructField[] {
                                new StructField("label", DataTypes.DoubleType, false, Metadata.empty()),
                                new StructField("sentence", DataTypes.StringType, false, Metadata.empty()) });
                Dataset<Row> sentenceData = spark.createDataFrame(data, schema);
                sentenceData.show();
                Tokenizer tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words");
                Dataset<Row> wordsData = tokenizer.transform(sentenceData);
                wordsData.show();
                int numFeatures = 20;
                HashingTF hashingTF = new HashingTF().setInputCol("words").setOutputCol("rawFeatures")
                                .setNumFeatures(numFeatures);

                wordsData.show();
                Dataset<Row> featurizedData = hashingTF.transform(wordsData);
                // alternatively, CountVectorizer can also be used to get term frequency vectors

                IDF idf = new IDF().setInputCol("rawFeatures").setOutputCol("features");
                IDFModel idfModel = idf.fit(featurizedData);

                Dataset<Row> rescaledData = idfModel.transform(featurizedData);
                rescaledData.select("label", "features").show();
                rescaledData.show();
        }

        public static void testVectorAssembler() {
                StructType schema = createStructType(new StructField[] { createStructField("id", IntegerType, false),
                                createStructField("hour", IntegerType, false),
                                createStructField("mobile", DoubleType, false),
                                createStructField("userFeatures", new VectorUDT(), false),
                                createStructField("clicked", DoubleType, false) });
                Row row = RowFactory.create(0, 18, 1.0, Vectors.dense(0.0, 10.0, 0.5), 1.0);
                Dataset<Row> dataset = spark.createDataFrame(Collections.singletonList(row), schema);
                dataset.show();
                VectorAssembler assembler = new VectorAssembler()
                                .setInputCols(new String[] { "hour", "mobile", "userFeatures" })
                                .setOutputCol("features");

                Dataset<Row> output = assembler.transform(dataset);
                System.out.println(
                                "Assembled columns 'hour', 'mobile', 'userFeatures' to vector column " + "'features'");
                output.select("features", "clicked").show(false);
                output.show();
        }

        public static void testOneHotEncoding() {
                List<Row> data = Arrays.asList(RowFactory.create(5.0, 1.0), RowFactory.create(1.0, 0.0),
                                RowFactory.create(2.0, 1.0), RowFactory.create(4.0, 2.0), RowFactory.create(5.0, 1.0),
                                RowFactory.create(2.0, 0.0));

                StructType schema = new StructType(new StructField[] {
                                new StructField("categoryIndex1", DataTypes.DoubleType, false, Metadata.empty()),
                                new StructField("categoryIndex2", DataTypes.DoubleType, false, Metadata.empty()) });

                Dataset<Row> df = spark.createDataFrame(data, schema);
                df.show(false);

                OneHotEncoder encoder = new OneHotEncoder()
                                .setInputCols(new String[] { "categoryIndex1", "categoryIndex2" })
                                .setOutputCols(new String[] { "categoryVec1", "categoryVec2" }).setDropLast(false);

                OneHotEncoderModel model = encoder.fit(df);
                Dataset<Row> encoded = model.transform(df);
                encoded.show(false);
        }

}
