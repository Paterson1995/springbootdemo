package com.demo.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("第九步，postProcessAfterInitialization方法被调用");
        return null;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("第六步，postProcessBeforeInitialization方法被调用"+bean+"被创建的时间为"+new java.util.Date());
        /*
        在这里，能做的事情可就不止上面的这么简单的一句输出了，它还可以过滤每个对象的ip
        还可以给所有对象添加属性或者函数，总之就是所有对象！
        其实，这里体现了AOP编程的思想，AOP呢就是面向切成编程（针对所有对象编程）
         */

        return null;
    }
}
