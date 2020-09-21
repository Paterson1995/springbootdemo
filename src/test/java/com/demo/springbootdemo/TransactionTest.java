package com.demo.springbootdemo;

import com.demo.springbootdemo.service.IUserService;
import com.demo.springbootdemo.service.serviceImpl.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = SpringbootdemoApplication.class)
@RunWith(SpringRunner.class)
public class TransactionTest {

    @Autowired
    IUserService userService;

    @Transactional
    @Test
    public void test() {
        userService.A("789");
    }
}
