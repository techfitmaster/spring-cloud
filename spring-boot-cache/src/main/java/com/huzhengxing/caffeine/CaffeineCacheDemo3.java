package com.huzhengxing.caffeine;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author 2021/1/27 11:07  zhengxing.hu
 * @version 1.0.0
 * @file CaffeineCacheDemo2
 * @brief Caffeine异步加载
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
public class CaffeineCacheDemo3 {

    public static void main(String[] args) {
        AsyncLoadingCache<Object, Object> cache = Caffeine.newBuilder()
                .expireAfterWrite(2000, TimeUnit.MILLISECONDS)
                .buildAsync(CaffeineCacheDemo3::createValue);


        String key1 = "key1";
        CompletableFuture<Object> future = cache.get(key1);
        //异步获取结果
//        future.thenAccept(System.out::println);

        future.thenAccept(value -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.print("After 3000 , value is ");
            System.out.println(value);
        });


        CompletableFuture<Object> ifPresent = cache.getIfPresent(key1);
        System.out.println(ifPresent);


        CompletableFuture<Map<Object, Object>> all = cache.getAll(Arrays.asList("key1", "key2", "key3"));
        all.thenAccept(System.out::println);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //转成异步再获取
        System.out.println(cache.synchronous().asMap());

    }

    private static Object createValue(Object key) {
        return key + "_" + System.currentTimeMillis();
    }

}
