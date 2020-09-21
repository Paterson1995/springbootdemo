package com.demo.springbootdemo.aspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {

    //被代理对象
    private Object origin;

    public MyInvocationHandler(Object origin) {
        this.origin = origin;
    }

    /**
     * proxy:代理对象com.sun.proxy.$Proxy0
     * method:被代理对象方法的Method对象
     * args:被代理对象方法传递的参数
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke start");
        Object result = method.invoke(origin, args);
        System.out.println("invoke end");
        return result;
    }
}

