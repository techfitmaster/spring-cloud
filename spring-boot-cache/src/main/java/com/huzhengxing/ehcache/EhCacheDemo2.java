package com.huzhengxing.ehcache;

import lombok.SneakyThrows;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.ResourcePools;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.ExpiryPolicy;

import java.time.Duration;

/**
 * @author 2021/1/26 18:31  zhengxing.hu
 * @version 1.0.0
 * @file EhCacheDemo2 创建一个Ehcache缓存
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
public class EhCacheDemo2 {

    @SneakyThrows
    public static void main(String[] args) {
        //构建一个缓存管理器，管理一个or 多个 Cache
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

        ResourcePoolsBuilder resourcePoolsBuilder = ResourcePoolsBuilder.newResourcePoolsBuilder();

        ResourcePools resourcePools = resourcePoolsBuilder.heap(10, MemoryUnit.MB).build();

        //配置Cache参数
        CacheConfiguration<String, String> configuration = CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class, resourcePools)
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(3)))
                .build();

        Cache<String, String> cache = cacheManager.createCache("simpleCache", configuration);

        cache.put("key", "value");

        System.out.println("Value: " + cache.get("key"));

        Thread.sleep(5000);

        System.out.println("After 5 second , value must be removed , value is " + cache.get("key"));

        cacheManager.close();



    }

}
