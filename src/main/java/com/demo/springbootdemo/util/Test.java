package com.demo.springbootdemo.util;

import java.util.Date;
import java.util.concurrent.*;

public class Test {

    public static void testScheduledThreadPool() {
        System.out.println("Main Thread: Starting at: " + new Date());
        ScheduledThreadPoolExecutor exec = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(10);   //创建大小为10的线程池
        for (int i = 0; i < 10; i++) {
            exec.schedule(new Handle(String.valueOf(i)), 10, TimeUnit.SECONDS);//延迟10秒执行
        }
        exec.shutdown();  //执行到此处并不会马上关闭线程池
        //主线程会等待线程池里的所有任务执行结束再继续
        while (!exec.isTerminated()) {
            //wait for all tasks to finish
        }
        System.out.println("Main Thread: Finished at:" + new Date());
    }

    public static void testCachedThreadPool() {
        System.out.println("Main: Starting at: " + new Date());
        ExecutorService exec = Executors.newCachedThreadPool();   //创建一个缓冲池，缓冲池容量大小为Integer.MAX_VALUE
        for (int i = 0; i < 10; i++) {
            exec.execute(new Handle(String.valueOf(i)));
        }
        exec.shutdown();  //执行到此处并不会马上关闭线程池,但之后不能再往线程池中加线程，否则会报错
//        主线程的执行与线程池里的线程分开，有可能主线程结束了，但是线程池还在运行
//        放入线程池的线程并不一定会按其放入的先后而顺序执行
        System.out.println("Main: Finished all threads at" + new Date());
    }


    /**
     * 初始化延迟0ms开始执行，每隔2000ms重新执行一次任务
     * 间隔指的是连续两次任务开始执行的间隔。对于scheduleAtFixedRate方法，当执行任务的时间大于我们指定的间隔时间时，
     * 它并不会在指定间隔时开辟一个新的线程并发执行这个任务。而是等待该线程执行完毕。
     */
    public static void testExecuteFixedRate() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
        executor.scheduleAtFixedRate(
                new Handle(),
                0,
                2000,
                TimeUnit.MILLISECONDS);
    }

    /**
     * 以固定延迟时间进行执行
     * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
     * 间隔指的是连续上次执行完成和下次开始执行之间的间隔。
     */
    public static void testExecuteFixedDelay() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
        executor.scheduleWithFixedDelay(
                new Handle(),
                0,
                2000,
                TimeUnit.MILLISECONDS);
    }
}
