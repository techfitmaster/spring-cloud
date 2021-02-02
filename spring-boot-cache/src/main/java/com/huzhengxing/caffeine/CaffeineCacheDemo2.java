package com.huzhengxing.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 2021/1/27 11:07  zhengxing.hu
 * @version 1.0.0
 * @file CaffeineCacheDemo2
 * @brief Caffeine同步加载
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
public class CaffeineCacheDemo2 {

    public static void main(String[] args) {
        LoadingCache<Object, Object> cache = Caffeine.newBuilder()
                .expireAfterWrite(2000, TimeUnit.MILLISECONDS)
                .build(CaffeineCacheDemo2::createValue);


        String key1 = "Key1";

        Object value = cache.get(key1);
        System.out.println(value);
        System.out.println(cache.asMap());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        cache.refresh("a");
        Map<Object, Object> all = cache.getAll(Arrays.asList("key1", "key2", "key3"));
        System.out.println(all);



    }

    private static Object createValue(Object key) {
        return key + "_" + System.currentTimeMillis();
    }

}
