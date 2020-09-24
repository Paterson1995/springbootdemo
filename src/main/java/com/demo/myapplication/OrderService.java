package com.demo.myapplication;

import org.springframework.beans.factory.InitializingBean;

@MyComponent(name = "orderService",scope = "prototype")
public class OrderService implements InitializingBean {

    @MyAutowired
    UserService userService;

    public void test() {
        System.out.println(userService);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("OrderService--------------------------------InitializingBean.afterPropertiesSet()");
    }
}
