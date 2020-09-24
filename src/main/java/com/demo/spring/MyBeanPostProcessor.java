package com.demo.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    /**
     * 此时就会调用我们的 AnnotationAwareAspectJAutoProxyCreator 的 postProcessAfterInitialization 方法。
     * 该方法会查找我们定义的切面类（使用 @Aspect 注解），
     * 创建切面类中定义的增强器（使用 @Before、@After、@Around 等注解），
     * 并根据 @Pointcut 的 execution 表达式筛选出适用于当前遍历的 bean 的增强器，
     * 将适用于当前遍历的 bean 的增强器作为参数之一创建对应的 AOP 代理。
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
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
