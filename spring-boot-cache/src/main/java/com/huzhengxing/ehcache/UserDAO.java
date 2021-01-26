package com.huzhengxing.ehcache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author 2021/1/26 14:28  zhengxing.hu
 * @version 1.0.0
 * @file userDAO
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */
@Service
public class UserDAO {
    /**
     * "@Cacheable"：数据添加到缓存；
     *      value: 指定cache.xml中缓存的配置策略；
     *      key: 值参数中的Id为缓存中的Key
     *
     * @param id
     * @return
     */
    @Cacheable(value = "simpleCache", key = "#id")
    public UserDO cacheData(String id) {
        System.out.println("Test Cacheable");
        UserDO userDO = new UserDO();
        userDO.setName("Albert");
        userDO.setPassword("123456");
        return userDO;
    }

    /**
     *
     * "@CachePut"：不仅会缓存还会执行代码；属性同Cacheable
     *
     * @param id
     * @return
     */
    @CachePut(value = "simpleCache", key = "#id")
    public UserDO cacheDataAndProcess(String id) {
        System.out.println("Test CachePut");
        UserDO userDO = new UserDO();
        userDO.setName("Albert");
        userDO.setPassword("123456");
        return userDO;
    }

    /**
     *  "@CacheEvict": 清除内存中的缓存
     *
     *
     * @param id
     * @return
     */
    @CacheEvict(value = "simpleCache", key = "#id")
    public UserDO cacheRemoved(String id) {
        System.out.println("Test CacheEvict");
        UserDO userDO = new UserDO();
        userDO.setName("Albert");
        userDO.setPassword("123456");
        return userDO;
    }


}
