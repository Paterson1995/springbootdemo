package com.demo.spring;

import com.demo.spring.mapper.OrderMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("MyBeanFactoryPostProcessor------------------------------postProcessBeanFactory");

        //得到容器中的BeanDefinition，并做一些处理,但是不能注册BeanDefinition，因为此时BeanFactory已经组建完成
//        GenericBeanDefinition beanDefinition = (GenericBeanDefinition) configurableListableBeanFactory.getBeanDefinition("myFactoryBean");

        //手动注册bean到spring容器
        configurableListableBeanFactory.registerSingleton("manualOrderService", OrderService.class);

    }
}
