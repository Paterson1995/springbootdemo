package com.demo.springbootdemo.util;

import java.util.concurrent.TimeUnit;

public class InterruputSleepThread {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread() {
            @Override
            public void run() {
                //while在try中，通过异常中断就可以退出run循环
                try {
                    while (true) {
                        //当前线程处于阻塞状态，异常必须捕捉处理，无法往外抛出
                        TimeUnit.SECONDS.sleep(2);
                    }
                } catch (InterruptedException e) {
                    System.out.println("Interruted When Sleep");
                    boolean interrupt = this.isInterrupted();
                    //中断状态被复位，false
                    System.out.println("interrupted:"+interrupt);
                }
                System.out.println("continue");
            }
        };

        t1.start();
        TimeUnit.SECONDS.sleep(2);
        //中断处于阻塞状态的线程,
        // 当一个线程处于被阻塞状态或者试图执行一个阻塞操作时，使用Thread.interrupt()方式中断该线程，
        // 注意此时将会抛出一个InterruptedException的异常，同时中断状态将会被复位(由中断状态改为非中断状态)
        //处于运行期且非阻塞的状态的线程，这种情况下，直接调用Thread.interrupt()中断线程是不会得到任响应的
        t1.interrupt();
    }
}
