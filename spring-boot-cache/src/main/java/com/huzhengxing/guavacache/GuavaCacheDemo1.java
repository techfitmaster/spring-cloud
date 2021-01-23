package com.huzhengxing.guavacache;

import com.google.common.cache.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author 2021/1/23 11:15  zhengxing.hu
 * @version 1.0.0
 * @file GuavaCacheDemo1
 * @brief Guava Cache 缓存加载方式-CacheLoader
 * 1. CacheLoader
 * 2. Callable
 * @par
 * @warning
 */
public class GuavaCacheDemo1 {


    public static void main(String[] args) {

        // 缓存加载器
        CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            //如果缓存中没有Key，则会调用Load方法；
            @Override
            public String load(String key) throws Exception {
                System.out.println(String.format("缓存中没有的Key：key->%s",key));
                return String.format("Load %s",key);
            }
        };


        RemovalListener<String,String> removalListener = new RemovalListener<String, String>() {
            //缓存删除元素时调用
            @Override
            public void onRemoval(RemovalNotification<String, String> removalNotification) {
                System.out.println(String.format("删除缓存数据:key->%s , value-> %s",removalNotification.getKey(),removalNotification.getValue()));
            }
        };

        // 加载缓存
        LoadingCache<String,String> cache = CacheBuilder.newBuilder()
                //缓存容量
                .maximumSize(5)
                //设置失效时间
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .expireAfterAccess(10,TimeUnit.SECONDS)
                //设置删除监听器
                .removalListener(removalListener)
                //设置加载器
                .build(loader);

        for (int i = 0; i < 10; i++) {
            String key = "key" + i;
            String value = "value" + i;
            cache.put(key,value);
        }

        try {
            System.out.println(cache.get("key3"));
            cache.get("key100");
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("没有获取key");
        }

    }


}

