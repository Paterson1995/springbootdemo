package com.demo.spring;

import com.demo.spring.mapper.OrderMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {
//        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-config.xml");

        /**
         * 1.this():调用父类构造器初始化了BeanFactory。将Spring默认的一些后置处理器(ConfigurationClassPostProcessor,
         * AutowiredAnnotationBeanPostProcessor(处理@Autowired，@Value，@Inject),
         * CommonAnnotationBeanPostProcessor(@Resource、@PostConstruct、@PreDestroy))注册到容器
         * 2.this.register(annotatedClasses):将配置类解析为BeanDefinition，放入beanDefinitionMap，即注册到容器
         * 3.this.refresh()：
         *      1) invokeBeanFactoryPostProcessors(beanFactory):执行所有的BeanFactory后置处理器，包括spring内置的和自定义的。
         *      会先执行实现了BeanDefinitionRegistryPostProcessor接口的类，然后执行BeanFactoryPostProcessor的类
         *      默认情况下，容器中只有一个BeanFactoryPostProcessor,即Spring内置的ConfigurationClassPostProcessor
         *      ConfigurationClassPostProcessor类的postProcessBeanDefinitionRegistry()进行了
         *      @Configuration类的解析，@ComponentScan的扫描，以及@Import注解的处理，
         *      并将所有交由spring管理的类解析为BeanDefinition，放入到beanFactory的beanDefinitionMap中
         *      然后执行了postProcessBeanFactory(),这个方法对加了@Configuration注解的类进行CGLIB代理，
         *      并向容器中添加一个ImportAwareBeanPostProcessor后置处理器
         *      2）registerBeanPostProcessors(beanFactory)：在beanDefinitionMap中找到所有的BeanPostProcessor的BeanDefinition，调用了getBean()方法，
         *      将所有的BeanPostProcessor实例化，并放入到BeanFactory的beanPostProcessors的列表中。
         *      最后再重新注册了ApplicationListenerDetector，这样做的目的是为了将ApplicationListenerDetector放入到后置处理器的最末端
         *      3）finishBeanFactoryInitialization(beanFactory):实例化和初始化剩余的非懒加载的单例bean
         *      在Spring创建Bean的过程中，是先将Bean通过反射创建对象，然后通过BeanPostProcessor来为对象的属性赋值。
         */
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig.class);
        String[] names = ac.getBeanDefinitionNames();
        for (String name: names) {
            System.out.println(name);
        }

//        ac.getBean(AsyncService.class).test();

//        OrderMapper orderMapper = ac.getBean(OrderMapper.class);
//        System.out.println(orderMapper);
//        System.out.println(orderMapper.test());

//        System.out.println(ac.getBean("myFactoryBean"));
//        System.out.println(ac.getBean("&myFactoryBean"));

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