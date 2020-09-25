package com.demo.spring;

import com.demo.spring.mapper.OrderMapper;
import com.demo.springbootdemo.mapper.UserMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@PropertySource(value = {"application.properties"})
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Value("${my.spring.mapper.interface.location}")
    private String mapperInterfaceLocation;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {

        System.out.println("MyBeanDefinitionRegistryPostProcessor------------------------------postProcessBeanDefinitionRegistry");
        //在这里也可以手动注册BeanDefinition，可以从配置文件里得到需要配置类或接口的路径
        //null  ??????????????????
        System.out.println(mapperInterfaceLocation);

        List<Class> mappers = new ArrayList<>();
        mappers.add(UserMapper.class);
        mappers.add(OrderMapper.class);

        for (Class mapper: mappers) {

            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition();
            AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();

            beanDefinition.setBeanClass(MyFactoryBean.class);
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(mapper);

            beanDefinitionRegistry.registerBeanDefinition(mapper.getSimpleName(), beanDefinition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("MyBeanDefinitionRegistryPostProcessor------------------------------postProcessBeanFactory");
    }
}
