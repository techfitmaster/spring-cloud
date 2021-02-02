package com.huzhengxing.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * @author 2021/1/27 11:07  zhengxing.hu
 * @version 1.0.0
 * @file CaffeineCacheDemo2
 * @brief 过期策略
 * 淘汰策略：
 * LFU算法
 * 缓存大小
 * 权重大小
 * 基于引用
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
public class CaffeineCacheDemo4 {

    /**
     * 基于时间淘汰策略
     */
    public void abortByTime() {
        Cache<Object, Object> cache = Caffeine.newBuilder()
                //设置在缓存中的存在时间；
                .expireAfterWrite(2000, TimeUnit.MILLISECONDS)
                .build();

        cache.put("key", "value");
        System.out.println("Cache data:" + cache.asMap());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("After 3s , Cache data :" + cache.asMap());

    }

    /**
     * 基于权重淘汰
     */
    public void abortByWeigher() {
        Cache<Object, Object> cache = Caffeine.newBuilder()
                // 缓存最大的权重
                .maximumWeight(2)
                // 自定义权重
                .weigher((key, value) -> {
                    System.out.printf("Custom weigher, key is %s, value is %s", key, value);
                    System.out.println();
                    return (int) value;
                })
                .removalListener(((key, value, cause) -> System.out.printf("Cache data removed , key is %s , value is %s , cause by %s%n", key, value, cause.name())))
                .build();

        for (int i = 0; i < 20; i++) {
            cache.put(i, i);
        }
        System.out.println(cache.asMap());

//        cache.invalidate(1);
//        cache.invalidateAll();
        cache.cleanUp();
        System.out.println(cache.asMap());

    }

    /**
     * 基于内存的大小淘汰，LFU算法
     */
    public void abortBySize() {
        Cache<Object, Object> cache = Caffeine.newBuilder()
                .maximumSize(10)
                .removalListener(((key, value, cause) -> System.out.printf("Cache data removed , key is %s , value is %s , cause by %s%n", key, value, cause.name())))
                .build();

        for (int i = 0; i < 20; i++) {
            cache.put(i, i + "_value");
        }
        System.out.println(cache.asMap());
        for (int i = 0; i < 100; i++) {
            for (int j = 5; j < 10; j++) {
                Object o = cache.getIfPresent(j);
            }
        }
        cache.cleanUp();
        System.out.println(cache.asMap());
        cache.cleanUp();
        System.out.println(cache.asMap());
    }


    /**
     * 基于引用淘汰策略-弱引用
     */
    public void abortByWeakReference() {
        LoadingCache<Object, Object> cache = Caffeine.newBuilder()
                .weakKeys()
//                .softValues()
                .weakValues()
                .recordStats()
                .removalListener(((key, value, cause) -> System.out.printf("Cache data removed , key is %s , value is %s , cause by %s%n", key, value, cause.name())))
                .build(CaffeineCacheDemo4::createValue);


        Object obj1 = new Object();
        String key = "key";
        cache.put(key,obj1);
        System.out.println(cache.getIfPresent(key));
        obj1 = new String("1234");
        System.gc();
        System.out.println("After gc , value of key is " + cache.get(key));
    }

    private static Object createValue(Object key) {
        return key + "_" + System.currentTimeMillis();
    }


    public static void main(String[] args) {
        CaffeineCacheDemo4 caffeineCacheDemo4 = new CaffeineCacheDemo4();
//        caffeineCacheDemo4.abortByTime();
//        caffeineCacheDemo4.abortByWeigher();
//        caffeineCacheDemo4.abortBySize();
        caffeineCacheDemo4.abortByWeakReference();
    }


}
