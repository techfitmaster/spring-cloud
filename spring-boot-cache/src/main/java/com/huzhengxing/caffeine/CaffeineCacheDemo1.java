package com.huzhengxing.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author 2021/1/27 10:48  zhengxing.hu
 * @version 1.0.0
 * @file CaffeineCacheDemo1
 * @brief 手动加载方式
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
public class CaffeineCacheDemo1 {

    public static void main(String[] args) {
        Cache<String, Object> cache = Caffeine.newBuilder()
                .expireAfterWrite(2000, TimeUnit.MILLISECONDS)
                .maximumSize(10000)
                .build();


        Function<Object, Object> getFuc = key -> key + "_" + System.currentTimeMillis();

        String key1 = "key1";
        // 获取Key1的值，如果不存在，则调用getFuc函数，并缓存数据
        Object value = cache.get(key1, getFuc);
        System.out.println(value); // print key1_1611716226432
        System.out.println(cache.asMap());
        System.out.println(cache.getIfPresent(key1));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Object value1 = cache.getIfPresent(key1);
        System.out.println(value1);


        cache.put(key1,"value of key1");
        System.out.println(cache.get(key1,getFuc));

        ConcurrentMap<String, Object> map = cache.asMap();
        System.out.println(map);
        //删除Key1
        cache.invalidate(key1);
        System.out.println(cache.asMap());
    }

}
