package com.huzhengxing.guavacache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.Callable;

/**
 * @author 2021/1/23 11:15  zhengxing.hu
 * @version 1.0.0
 * @file GuavaCacheDemo1
 * @brief Guava Cache 回收方式-基于引用回收
 * 1. CacheLoader
 * 2. Callable
 * @par
 * @warning
 */
public class GuavaCacheDemo3 {

    public static void main(String[] args) {
        Cache<String, Object> cache = CacheBuilder.newBuilder()
                //开启弱应用，可以被垃圾收集器回收
                .weakValues()
                .build();

        Object obj = new Object();

        cache.put("key1", obj);

        obj = new String("123");
        System.gc();
        // 如果不存在返回Null
        System.out.println(cache.getIfPresent("key1"));
        System.out.println(cache.stats());
    }
}

