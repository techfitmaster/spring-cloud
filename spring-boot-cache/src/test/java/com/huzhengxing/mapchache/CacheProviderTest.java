package com.huzhengxing.mapchache;


import org.junit.Test;

import java.util.UUID;

/**
 * @author - 2021/1/22 15:38 albert
 * @version - 2021/1/22 15:38 1.0.0
 * @file CacheProviderTest
 * @brief 文件简要说明
 * @par 其他重要信息：
 * @warning 警告信息:
 * @par 版权信息：
 * ©2019 杭州锘崴信息科技有限公司 版权所有
 */

public class CacheProviderTest {

    @Test
    public void test(){
        String key = "id";
        String value = "id->value";
        CacheProvider cacheProvider = new CacheProvider();
        cacheProvider.put(key,value,1000L);
        System.out.println("查询添加缓存的Key------------");
        System.out.println("key:" + key + ",value:" + cacheProvider.get(key));
        cacheProvider.remove(key);
        System.out.println("删除Key之后查询------------");
        System.out.println("key:" + key + ",value:" + cacheProvider.get(key));

        cacheProvider.put(key,value,1000L);
        System.out.println("重新添加Key----------------");
        System.out.println("key:" + key + ",value:" + cacheProvider.get(key));
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("暂停2S后，查看Key-----------");
        System.out.println("key:" + key + ",value:" + cacheProvider.get(key));
        cacheProvider.remove(key);
        System.out.println("--------------过期策略测试--------------");
        cacheProvider = new CacheProvider(10);
        for (int i = 0; i < 15; i++) {
            cacheProvider.put(key + i, UUID.randomUUID().toString());
        }
        cacheProvider.print();
    }

}