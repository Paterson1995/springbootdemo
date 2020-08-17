package com.demo.springbootdemo.util;

import java.util.concurrent.*;

/**
 * 第四种使用Java多线程的方式，线程池
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {
//        fixedThreadPool();
//        singleThreadPool();
//        cachedThreadPool();
        customThreadPool();
    }

    private static void customThreadPool() {
        ExecutorService threadPool=
                new ThreadPoolExecutor(2,
                        5,
                        1L,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(3),
                        Executors.defaultThreadFactory(),
                        //DiscardPolicy策略默认丢弃最后进来的请求，与DiscardOldestPolicy相反
                        new ThreadPoolExecutor.DiscardOldestPolicy()
                );
        try {
            System.out.println("CustomThreadPool Created");
            //在选择默认的拒绝策略AbortPolicy情况下，并发量大于最大线程数+阻塞队列容量时，会报错，超过数量的请求不会被处理
            //且在等待队列满之后进来的请求，会在等待队列中的请求前直接获得空闲队列的处理
            for (int i = 1; i <= 22; i++) {
                int finalI = i;
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t办理第"+ finalI +"位客户的业务");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    private static void cachedThreadPool() {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        try {
            System.out.println("CachedThreadPool Created");
            for (int i = 1; i <= 10; i++) {
                int finalI = i;
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t办理第"+ finalI +"位客户的业务");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    private static void singleThreadPool() {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        try {
            System.out.println("SingleThreadPool Created");
            for (int i = 1; i <= 10; i++) {
                int finalI = i;
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t办理第"+ finalI +"位客户的业务");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    private static void fixedThreadPool() {
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        //模拟10个用户来办理业务，每个用户就是一个线程
        try {
            System.out.println("FixedThreadPool Created");
            for (int i = 1; i <= 10; i++) {
                int finalI = i;
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t办理第"+ finalI +"位客户的业务");
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
