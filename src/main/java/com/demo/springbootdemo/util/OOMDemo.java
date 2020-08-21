package com.demo.springbootdemo.util;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OOMDemo {

    public static void main(String[] args) {

//        javaHeapSpace();
//        GCOverheadLimitExceeded();
//        directBufferMemory();
        int i=0;
        try {
            while (true) {
                i++;
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(MetaspaceOOM.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invoke(o, args);
                    }
                });
                enhancer.create();
            }
        } catch (Throwable e) {
            System.out.println(i+"次后发生了异常***************************");
            e.printStackTrace();
        }

    }

    public static void javaHeapSpace() {
//        byte[] bytes = new byte[11*1024*1024];
        String string = "";
        while (true) {

            /**
             * 最后一次GC
             * [GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] 5209K->5209K(9728K), 0.0009951 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
             * 最后一次Full GC
             * [Full GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] [ParOldGen: 5209K->5209K(7168K)] 5209K->5209K(9728K),
             * [Metaspace: 3864K->3864K(1056768K)], 0.0054153 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
             */
            string = string + new Random().nextInt(888888888) + new Random().nextInt(99999999);
        }
    }

    public static void GCOverheadLimitExceeded() {
        int i = 0;
        List<String> list = new ArrayList<>();
        try {
            while (true) {
                list.add(String.valueOf(++i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("************i=" + i);
        }
    }

    public static void directBufferMemory() {
        System.out.println("配置的maxDirectMemory: " + (sun.misc.VM.maxDirectMemory() / (double) 1024 / 1024) + "MB");
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(6 * 1024 * 1024);
    }

    public static void unableToCreateNewNativeThread() {
        for (int i = 0; ; i++) {
            System.out.println("***********" + i);
            new Thread(() -> {
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "" + i).start();
        }
    }

    static class MetaspaceOOM {}

}

