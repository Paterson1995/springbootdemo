package com.demo.spring;

import com.demo.spring.mapper.OrderMapper;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//@Component
//工厂类型Bean，调用getBean时得到的是他的产生的对象，主要通过是实现该接口自定义实例化bean，在Spring中最为典型的一个应用就是用来创建AOP的代理对象。
//生产一类bean
public class MyFactoryBean implements FactoryBean {

    private Class mapper;

    public MyFactoryBean(Class mapper) {
        this.mapper = mapper;
    }

    @Override
    public Object getObject() throws Exception {
        //手动使用JDK接口代理，将代理对象注册到IoC容器中
        Object o = Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{mapper}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) {
                        System.out.println(mapper.getSimpleName()+"的代理类执行方法："+method.getName());
                        return "proxy";
                    }
                });
        return o;
    }

    @Override
    public Class<?> getObjectType() {
        return mapper;
    }
}
