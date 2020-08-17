package com.demo.springbootdemo.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {

    public static void main(String[] args) {
        BoundedBuffer1 boundedBuffer = new BoundedBuffer1();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t生产线程启动");
            try {
                for (int i=0; i<50; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    boundedBuffer.put(i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "prod").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t消费线程启动");
            try {
                while(true)
                    boundedBuffer.take();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "cons").start();
    }
}

class BoundedBuffer {
    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            //数组满了，等待take
            while (count == items.length)
                notFull.await();
            items[putptr] = x;
            //添加元素后若数组满了，则添加的坐标变为0
            if (++putptr == items.length) putptr = 0;
            //每添加了一个元素count加1，取走一个减1，count是缓存数组中当前的元素数量
            ++count;
            System.out.println(Thread.currentThread().getName()+"线程往缓存中添加元素："+x+"，缓存中的元素总数为："+count);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        try {
            //数组为空，等待put
            while (count == 0)
                notEmpty.await();
            //从数组第一个元素开始取
            Object x = items[takeptr];
            //拿取最后一个元素后，取的坐标从头开始
            if (++takeptr == items.length) takeptr = 0;
            //
            --count;
            System.out.println(Thread.currentThread().getName()+"线程从缓存中取走元素："+x+"，缓存中的元素总数为："+count);
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}

class BoundedBuffer1 {

    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    public synchronized void put(Object x) throws InterruptedException {
            //数组满了，等待take
            while (count == items.length)
                wait();
            items[putptr] = x;
            //添加元素后若数组满了，则添加的坐标变为0
            if (++putptr == items.length) putptr = 0;
            //每添加了一个元素count加1，取走一个减1，count是缓存数组中当前的元素数量
            ++count;
            System.out.println(Thread.currentThread().getName() + "线程往缓存中添加元素：" + x + "，缓存中的元素总数为：" + count);
            notifyAll();
    }

    public synchronized Object take() throws InterruptedException {
            //数组为空，等待put
            while (count == 0)
                wait();
            //从数组第一个元素开始取
            Object x = items[takeptr];
            //拿取最后一个元素后，取的坐标从头开始
            if (++takeptr == items.length) takeptr = 0;
            //
            --count;
            System.out.println(Thread.currentThread().getName() + "线程从缓存中取走元素：" + x + "，缓存中的元素总数为：" + count);
            notifyAll();
            return x;
    }
}