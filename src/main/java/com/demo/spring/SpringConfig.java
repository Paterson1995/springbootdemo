package com.demo.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//用于定义配置类，可替换xml配置文件
@Configuration
@ComponentScan("com.demo.spring")
//引入自定义的BeanDefinition
@Import(MyImportBeanDefinitionRegistrar.class)
@MyMapperScan("com.demo.spring.mapper")
public class SpringConfig {

    //@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    //@Bean用于注册bean对象，可以用@Component代替
    @Bean(initMethod="myInit",destroyMethod="myDestroy")
    public PersonService personService() {
        return new PersonService();
    }
}
