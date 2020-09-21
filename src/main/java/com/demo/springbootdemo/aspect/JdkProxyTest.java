package com.demo.springbootdemo.aspect;

import com.demo.springbootdemo.service.IOrderService;
import com.demo.springbootdemo.service.serviceImpl.OrderService;

import java.lang.reflect.Proxy;

public class JdkProxyTest {
    public static void main(String[] args) {
        /**
         * 生成代理对象，代理类继承了Proxy类并实现了被代理的接口
         * loader：定义加载代理类的classloader
         * interfaces：一组代理类要实现的接口
         * h：代理对象调用所实现接口的方法时关联的InvocationHandler对象，最终会调用其invoke方法
         */
        IOrderService proxy = (IOrderService) Proxy.newProxyInstance(JdkProxyTest.class.getClassLoader(),
                new Class[]{IOrderService.class}, new MyInvocationHandler(new OrderService()));
        proxy.testAspect("JdkProxyTest");
    }
}