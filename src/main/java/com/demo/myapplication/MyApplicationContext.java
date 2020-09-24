package com.demo.myapplication;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MyApplicationContext {

    private ConcurrentHashMap<String, MyBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();
    private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    public static void main(String[] args) {

        MyApplicationContext context = new MyApplicationContext(AppConfig.class);

        OrderService orderService1 = (OrderService) context.getBean("orderService");
        OrderService orderService2 = (OrderService) context.getBean("orderService");

        System.out.println(orderService1);
        System.out.println(orderService2);

        orderService1.test();
        orderService2.test();
    }

    public MyApplicationContext(Class configClass) {

        List<Class> classList = scanComponent(configClass);
        generateBeanDefinition(classList);
    }

    /**
     * 根据配置类上的注解的扫描路径，得到需要生成bean的类
     * @param configClass
     * @return
     */
    private List<Class> scanComponent(Class configClass) {

        List<Class> classList = new ArrayList<>();

        MyComponentScan myComponentScan = (MyComponentScan) configClass.getAnnotation(MyComponentScan.class);
        String scanPath = myComponentScan.value();

        ClassLoader classLoader = MyApplicationContext.class.getClassLoader();
        URL resource = classLoader.getResource(scanPath.replace(".","/"));

        File file = new File(resource.getFile());
        File[] files = file.listFiles();
        for (File f: files) {

            String fString = f.toString();
            String fPath = fString.substring(fString.indexOf("com"), fString.indexOf(".class")).replace("\\", ".");
            try {
                //?需要有
                Class<?> clazz = classLoader.loadClass(fPath);
                MyComponent component = clazz.getAnnotation(MyComponent.class);
                if (component != null) {
                    classList.add(clazz);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        return classList;
    }

    public void generateBeanDefinition(List<Class> classList) {

        for (Class<?> clazz : classList) {

            MyComponent component = clazz.getAnnotation(MyComponent.class);
            MyBeanDefinition beanDefinition = new MyBeanDefinition();
            beanDefinition.setBeanClass(clazz);
            beanDefinition.setScope(component.scope());

            beanDefinitionMap.put(component.name(), beanDefinition);

            //检测是否有组件实现了BeanPostProcessor接口
            if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
                try {
                    beanPostProcessorList.add((BeanPostProcessor) clazz.getDeclaredConstructor().newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Object getBean(String beanName) {

        MyBeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition.getScope().equals("singleton")) {
            Object bean = singletonObjects.get(beanName);
            if (bean == null) {
                bean = createBean(beanName, beanDefinition);
                singletonObjects.put(beanName, bean);
            }
            return bean;
        } else if ((beanDefinition.getScope().equals("prototype"))) {
            return createBean(beanName, beanDefinition);
        }
        return null;
    }

    public Object createBean(String beanName, MyBeanDefinition beanDefinition) {
        try {
            Class beanClass = beanDefinition.getBeanClass();
            //实例化
            Object bean = beanClass.getDeclaredConstructor().newInstance();

            //填充属性
            Field[] fields = beanClass.getDeclaredFields();
            for (Field field: fields) {

                if (field.isAnnotationPresent(MyAutowired.class)) {
                    //filed = userService
                    Object autowiredBean = getBean(field.getName());
                    field.setAccessible(true);
                    field.set(bean, autowiredBean);
                }
            }

            //aware

            //
            for (BeanPostProcessor postProcessor: beanPostProcessorList) {
                bean = postProcessor.postProcessBeforeInitialization(bean, beanName);
            }

            //初始化
            if (bean instanceof InitializingBean) {
                ((InitializingBean) bean).afterPropertiesSet();
            }

            //
            for (BeanPostProcessor postProcessor: beanPostProcessorList) {
                bean = postProcessor.postProcessAfterInitialization(bean, beanName);
            }

            return bean;

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
