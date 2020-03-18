package com.huzhengxing;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getUserAll(){
        List<User> users = userService.getUserAll();
        if (!CollectionUtils.isEmpty(users)) {
            users.forEach(u->log.info(u.toString()));
        }
    }

}
