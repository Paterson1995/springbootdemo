package com.demo.myapplication;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@MyComponent
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName+"--------------------------------BeanPostProcessor.postProcessAfterInitialization");
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName+"--------------------------------BeanPostProcessor.postProcessBeforeInitialization");
        return bean;
    }
}
