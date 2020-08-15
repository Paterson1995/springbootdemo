package com.demo.springbootdemo.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache cache = new MyCache();
        //写
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                cache.put(tempInt + "", tempInt + "");
            }, String.valueOf(i)).start();
        }
        //读
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                cache.get(tempInt + "");
            }, String.valueOf(i)).start();
        }
    }
}

class MyCache {
    //缓存更新快，需要用volatile修饰
    private volatile Map<String, Object> map = new HashMap<>();
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void put(String key, Object value) {
        //写锁保证写写之间是互斥的，一个写操作不能在打断另一个正在写的操作
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + "正在写入： " + key);
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                map.put(key, value);
                System.out.println(Thread.currentThread().getName() + "\t" + "写入完成");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                rwLock.writeLock().unlock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void get(String key) {
        //必须加读锁，不能不加锁，这样才能保证读写之间的互斥，且读操作必须在所有的写操作完成后才能执行
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + "正在读取： " + key);
            //模拟网络传输
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "读取完成： " + map.get(key));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rwLock.readLock().unlock();
        }
    }
}
