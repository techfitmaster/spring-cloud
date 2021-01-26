package com.huzhengxing.ehcache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author 2021/1/26 17:47  zhengxing.hu
 * @version 1.0.0
 * @file UserDAOTest
 * @brief
 * @par
 * @warning
 * @par 杭州锘崴信息科技有限公司版权所有©2020版权所有
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserDAOTest {

    @Autowired
    private UserDAO userDAO;


    @Test
    public void cacheData() throws InterruptedException {

        UserDO user1 = userDAO.cacheData("1");
        System.out.println("First:" + user1.getName());

        Thread.sleep(2000);
        System.out.println("Second:" + userDAO.cacheData("1").getName());

        Thread.sleep(9000);
        System.out.println("Third:" + userDAO.cacheData("1").getName());

    }

    @Test
    public void cacheDataAndProcess() throws InterruptedException {

        String id = "2";
        System.out.println("First：" + userDAO.cacheDataAndProcess(id).getName());

        Thread.sleep(2000);
        System.out.println("Second " + userDAO.cacheDataAndProcess(id).getName());

        Thread.sleep(9000);
        System.out.println("Third " + userDAO.cacheDataAndProcess(id).getName());

    }

    @Test
    public void cacheRemoved() throws InterruptedException {

        String id = "1";
        UserDO user1 = userDAO.cacheData(id);
        System.out.println("First:" + user1.getName());

        Thread.sleep(2000);
        System.out.println("Second: " + userDAO.cacheData(id).getName());

        System.out.println("Third: " + userDAO.cacheRemoved(id).getName());

        System.out.println("Forth: " + userDAO.cacheData(id).getName());

    }




}