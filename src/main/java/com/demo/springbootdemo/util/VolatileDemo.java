package com.demo.springbootdemo.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData {

    int number;
    volatile int vNumber;
    AtomicInteger atomicInteger = new AtomicInteger();

    public void setTo60() {
        this.number = 60;
        this.vNumber = 60;
    }

    //此时number前面已经加了volatile，但是不保证原子性
    public void addPlusPlus() {
        number++;
        vNumber++;
        atomicInteger.getAndIncrement();
    }

}

public class VolatileDemo {
    public static void main(String[] args) {
        atomicDemo();
        volatileVisibilityDemo();
    }

    private static void atomicDemo() {
        System.out.println("原子性测试");
        MyData myData = new MyData();
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myData.addPlusPlus();
                }
            }, String.valueOf(i)).start();
        }

        //主线程一直让步直到只有主线程和后台GC线程活跃，即等待上面所有线程运行完成
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + "\t volatile int type finally number value: " + myData.number);
        System.out.println(Thread.currentThread().getName() + "\t AtomicInteger type finally number value: " + myData.atomicInteger);
    }

    //volatile可以保证可见性，及时通知其它线程主物理内存的值已被修改
    private static void volatileVisibilityDemo() {
        System.out.println("可见性测试");
        MyData myData = new MyData();//资源类
        //启动一个线程操作共享数据
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
                myData.setTo60();
                System.out.println(Thread.currentThread().getName() + "\t update number value: " + myData.number);
                System.out.println(Thread.currentThread().getName() + "\t update vNumber value: " + myData.vNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAA").start();

//        while (myData.number == 0) {
//            main线程持有共享数据的拷贝，一直为0
//        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + "\t mission is over. main get number value: " + myData.number);
        System.out.println(Thread.currentThread().getName() + "\t mission is over. main get vNumber value: " + myData.vNumber);
    }
}

