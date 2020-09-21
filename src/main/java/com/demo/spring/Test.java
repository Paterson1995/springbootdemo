package com.demo.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class Test {
    public static void main(String[] args) {
//        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-config.xml");
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig.class);

//        BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("spring-config.xml"));
//        System.out.println("************************************************");
//        PersonService personService = (PersonService) beanFactory.getBean("personService");
        /**
         * 第一步，实例化bean
         * 第二步，调用set方法
         * 第三步，setBeanName被调用，调用的id名为：personService
         * 第四步，setBeanFactory被调用，beanFactory为：org.springframework.beans.factory.support.DefaultListableBeanFactory@63e2203c: defining beans [personService,myBeanPostProcessor]; root of factory hierarchy
         * （第五步，调用setApplicationContext方法：org.springframework.context.support.ClassPathXmlApplicationContext@5479e3f, started on Fri Sep 18 12:47:07 CST 2020）
         * （第六步，postProcessBeforeInitialization方法被调用com.demo.spring.PersonService@704d6e83被创建的时间为Fri Sep 18 12:47:07 CST 2020）
         * 第七步，调用afterPropertiesSet()方法
         * 第八步，调用我自己的init()方法
         * （第九步，postProcessAfterInitialization方法被调用）
         */
    }
}