package com.huzhengxing.mapcache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author - 2021/1/22 14:58 albert
 * @version - 2021/1/22 14:58 1.0.0
 * @file CacheProvider
 * @brief 通过Map实现缓存工具
 * 缓存特点：
 * 过期时间，
 * 过期策略，
 * 能够并发更新数据；
 * @par 其他重要信息：
 * @warning 警告信息:
 * @par 版权信息：
 */
public class CacheProvider {

    private Map<String, CacheData> cacheData = null;
    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);

    private static final int MAX_CACHE_SIZE = 10;
    private static final float LOAD_FACTORY = 0.75f;

    /**
     * 实现FIFO淘汰策略
     */
    public CacheProvider(int maxCacheSize) {
        //通过容器的最大缓存大小和负载因子计算容量
        int capacity = (int) (Math.ceil(MAX_CACHE_SIZE / LOAD_FACTORY) + 1);
        cacheData = new LinkedHashMap<String, CacheData>(capacity, LOAD_FACTORY, false) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, CacheData> eldest) {
                return cacheData.size() > MAX_CACHE_SIZE;
            }
        };
    }

    /**
     * 实现FIFO淘汰策略
     */
    public CacheProvider() {
        this(10);
    }

    /**
     * 添加缓存，默认方式
     *
     * @param key
     * @param value
     */
    public synchronized void put(String key, Object value) {
        cacheData.put(key, new CacheData(value, -1L));
    }

    /**
     * 添加缓存
     *
     * @param key
     * @param value
     * @param expire -1L表示没有失效时间
     */
    public synchronized void put(String key, Object value, Long expire) {
        cacheData.remove(key);
        if (expire < 1) {
            cacheData.put(key, new CacheData(value, -1L));
        } else {
            cacheData.put(key, new CacheData(value, expire));
            executor.schedule(() -> {
                System.out.println("定时删除：" + "key:" + key + ",value:" + value);
                synchronized (this) {
                    remove(key);
                }
            }, expire, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 获取缓存数据
     *
     * @param key
     * @return
     */
    public synchronized Object get(String key) {
        return cacheData.get(key) == null ? null : cacheData.get(key).data;
    }

    /**
     * 删除缓存数据
     *
     * @param key
     */
    public synchronized void remove(String key) {
        cacheData.remove(key);
    }


    public void print() {
        cacheData.forEach((k, v) -> {
            System.out.println("key:" + k + ",value:" + v);
        });
    }


    class CacheData {

        public Object data;

        private Long expire;

        public CacheData(Object data, Long expire) {
            this.data = data;
            this.expire = expire;
        }
    }

}


