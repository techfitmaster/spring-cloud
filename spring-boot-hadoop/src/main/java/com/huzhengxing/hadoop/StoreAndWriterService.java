package com.huzhengxing.hadoop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.app.hdfs.hadoop.store.DataReader;
import org.springframework.cloud.stream.app.hdfs.hadoop.store.DataStoreWriter;
import org.springframework.cloud.stream.app.hdfs.hadoop.store.PartitionDataStoreWriter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author 2020/10/30 17:52  zhengxing.hu
 * @version 1.0.0
 * @file StoreAndWriterService
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@Component
public class StoreAndWriterService {
    @Autowired
    private DataStoreWriter<String> writer;

    @Autowired
    private DataReader<String> reader;

    @Autowired
    PartitionDataStoreWriter<String, Map<String, Object>> partitionDataStoreWriter;


    public void write() throws IOException {

        writer.write("123412341234");

    }

    public void read() throws IOException {
        String read = reader.read();

    }

}
