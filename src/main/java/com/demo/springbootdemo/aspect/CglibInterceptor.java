package com.demo.springbootdemo.aspect;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibInterceptor implements MethodInterceptor {

    /**
     * proxy：增强后的对象，即cglib生成的代理对象
     * method：拦截的方法，即目标对象方法
     * args：方法入参
     * methodProxy: 代理方法
     */
    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("intercept start");
        /**
         * 不是反射
         * 当我们去调用方法一的时候，在代理类中会先判断是否实现了方法拦截的接口，没实现的话直接调用目标类的方法一；
         * 如果实现了那就会被方法拦截器拦截（当前方法），处理自定义的逻辑，
         * 然后调用invokeSuper方法，对目标类中所有的方法建立索引，将每个方法的引用保存在数组中，这样就可以根据数组的下标直接调用方法，而不是用反射；
         * 索引建立完成之后，方法拦截器内部就会调用invoke方法（这个方法在生成的FastClass中实现），
         * 在invoke方法内就是调用CGLIB$方法一$这种方法，也就是调用对应的目标类的方法一；
         */
        Object result = methodProxy.invokeSuper(proxy, args);
        System.out.println("intercept end");
        return result;
    }
}
