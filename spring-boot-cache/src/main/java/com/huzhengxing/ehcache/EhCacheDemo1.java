package com.huzhengxing.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author 2021/1/26 18:25  zhengxing.hu
 * @version 1.0.0
 * @file EhCacheDemo1
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
public class EhCacheDemo1 {

    public static void main(String[] args) {
        CacheManager cacheManager = new CacheManager();

        Cache cache = cacheManager.getCache("simpleCache");

        cache.put(new Element("user", "Albert"));
        System.out.println("Key of user:" + cache.get("user"));

        System.out.println("Cache size: " + cache.getSize());

    }


}
