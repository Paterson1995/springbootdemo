package com.demo.springbootdemo.aspect;

import com.demo.springbootdemo.service.serviceImpl.OrderService;
import org.springframework.cglib.proxy.Enhancer;

public class CglibProxyTest {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        //设置被代理类，不能是接口
        enhancer.setSuperclass(OrderService.class);
        //设置回调对象，在调用中拦截对目标方法的调用
        enhancer.setCallback(new CglibInterceptor());
        //proxy继承了OrderService
        OrderService proxy = (OrderService) enhancer.create();
        proxy.testAspect("CglibProxyTest");
    }
}
