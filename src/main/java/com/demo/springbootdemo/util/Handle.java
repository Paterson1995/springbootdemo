package com.demo.springbootdemo.util;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 */
class Handle implements Runnable {
    private String name;

    public Handle () {
    }
    public Handle(String name) {
        this.name = "thread"+name;
    }
    @Override
    public void run() {
        System.out.println( name +" Start. Time = "+new Date());
        processCommand();
        System.out.println( name +" End. Time = "+new Date());
    }
    private void processCommand() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String toString(){
        return this.name;
    }

    public static void main(String[] args) {

//        Test.testScheduledThreadPool();
//        Test.testExecuteFixedRate();
        Test.testExecuteFixedDelay();
    }


}
