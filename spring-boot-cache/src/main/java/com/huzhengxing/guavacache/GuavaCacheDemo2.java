package com.huzhengxing.guavacache;

import com.google.common.cache.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author 2021/1/23 11:15  zhengxing.hu
 * @version 1.0.0
 * @file GuavaCacheDemo1
 * @brief Guava Cache 缓存加载方式-Callable
 * 1. CacheLoader
 * 2. Callable
 * @par
 * @warning
 */
public class GuavaCacheDemo2 {

    public static void main(String[] args) {
        Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(5).build();
        cache.put("key1", "value1");
        // 如果不存在返回Null
        System.out.println(cache.getIfPresent("key"));
        try {

            String value = cache.get("key1", new Callable<String>() {
                // 如果存在会返回Value，不存在会调用Call方法
                @Override
                public String call() throws Exception {
                    // TODO Auto-generated methdod stub
                    System.out.println("key 不存在");
                    return null;
                }
            });
            System.out.println(value);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

