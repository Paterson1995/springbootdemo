package com.demo.spring;

import com.demo.spring.mapper.OrderMapper;
import com.demo.springbootdemo.mapper.UserMapper;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

//        System.out.println("MyImportBeanDefinitionRegistrar------------------------------registerBeanDefinitions()");
//
//        Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(MyMapperScan.class.getName());
//        System.out.println(annotationAttributes.get("value"));
//
//        List<Class> mappers = new ArrayList<>();
//        mappers.add(UserMapper.class);
//        mappers.add(OrderMapper.class);
//
//        for (Class mapper: mappers) {
//
//            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition();
//            AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
//
//            beanDefinition.setBeanClass(MyFactoryBean.class);
//            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(mapper);
//
//            beanDefinitionRegistry.registerBeanDefinition(mapper.getSimpleName(), beanDefinition);
//        }

    }
}
