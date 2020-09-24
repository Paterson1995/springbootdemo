package com.demo.spring;

import com.demo.springbootdemo.aspect.MyInvocationHandler;
import com.demo.springbootdemo.mapper.UserMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
//        GenericBeanDefinition beanDefinition = (GenericBeanDefinition) configurableListableBeanFactory.getBeanDefinition("personService");
//        System.out.println("+++++++++++++++postProcessBeanFactory:"+beanDefinition.getBeanClassName());

        System.out.println("MyBeanFactoryPostProcessor------------------------------postProcessBeanFactory");
        //手动实例化一个对象注册到IoC容器中
//        configurableListableBeanFactory.registerSingleton("", Proxy.newProxyInstance(MyBeanFactoryPostProcessor.class.getClassLoader(),
//                new Class[]{UserMapper.class}, new InvocationHandler() {
//                    @Override
//                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                        return null;
//                    }
//                }));


    }
}
