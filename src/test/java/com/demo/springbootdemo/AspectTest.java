package com.demo.springbootdemo;

import com.demo.springbootdemo.service.serviceImpl.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = SpringbootdemoApplication.class)
@RunWith(SpringRunner.class)
public class AspectTest {

    @Autowired
    OrderService orderService;

    @Test
    public void test() {
        orderService.testAspect("参数name");
    }
}
