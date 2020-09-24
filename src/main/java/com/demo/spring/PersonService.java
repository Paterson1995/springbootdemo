package com.demo.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**基于Java Config的配置bean需要加这个注解，xml中配置不需要，
 * 根据xml中<bean>或者@Component注解类中的信息生成一个BeanDefinition对象，并放入BeanFactory的beanDefinitionMap(ConcurrentHashMap)
 * key为beanName,value为BeanDefinition对象
 * 最终生成的单例bean也是放在BeanFactory的singletonObjects(单例池)(ConcurrentHashMap)中
 */
@Component
public class PersonService implements BeanNameAware,BeanFactoryAware,ApplicationContextAware,InitializingBean,DisposableBean{

    private String name;

    public String getName() {
        return name;
    }

    @Value("qqq")
    public void setName(String name) {
        this.name = name;
        System.out.println("第二步，填充属性。调用set方法，name="+name);
    }

    public PersonService(){
        System.out.println("第一步，实例化bean");
    }

    @Override
    public void setBeanName(String arg0){
        //该方法可以给出正在被调用的bean的id
        System.out.println("第三步，setBeanName被调用，调用的id名为："+arg0);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        //该方法可以传递beanFactory
        System.out.println("第四步，setBeanFactory被调用，beanFactory为："+beanFactory);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //该方法传递一个ApplicationContext
        System.out.println("第五步，调用setApplicationContext方法："+applicationContext);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("第七步，调用afterPropertiesSet()方法");
    }

    public void myInit(){
        System.out.println("第八步，调用我自己的初始化方法");
    }

    @Override
    public void destroy() throws Exception {
        //关闭数据连接，socket，文件流，释放资源
        //看不到
        System.out.println("第十步，销毁方法（但不建议使用这种方式释放资源）");
    }

    public void myDestroy(){
        //也看不到
        System.out.println("第十一步，调用自定义的销毁方法");
    }
}
