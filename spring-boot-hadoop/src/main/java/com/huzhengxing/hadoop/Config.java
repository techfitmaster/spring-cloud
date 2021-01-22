package com.huzhengxing.hadoop;

import org.springframework.cloud.stream.app.hdfs.hadoop.store.config.annotation.EnableDataStoreTextWriter;
import org.springframework.cloud.stream.app.hdfs.hadoop.store.config.annotation.SpringDataStoreTextWriterConfigurerAdapter;
import org.springframework.cloud.stream.app.hdfs.hadoop.store.config.annotation.builders.DataStoreTextWriterConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 * @author 2020/10/30 17:25  zhengxing.hu
 * @version 1.0.0
 * @file Config
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@Configuration
@EnableDataStoreTextWriter
public class Config
        extends SpringDataStoreTextWriterConfigurerAdapter {



    @Override
    public void configure(DataStoreTextWriterConfigurer config)
            throws Exception {
        config
                .basePath("/tmp/store")
                .idleTimeout(60000)
                .closeTimeout(120000)
                .inWritingSuffix(".tmp")
                .withPartitionStrategy()
                .map("dateFormat('yyyy/MM/dd/HH/mm', timestamp)")
                .and()
                .withNamingStrategy()
                .name("data")
                .uuid()
                .rolling()
                .name("txt", ".")
                .and()
                .withRolloverStrategy()
                .size("1M");
    }
}
