package com.demo.springbootdemo.util;

import java.util.concurrent.TimeUnit;

public class SynchronizedBlocked implements Runnable {

    public synchronized void f() {
        System.out.println("Trying to call f()");
        while (true)
            // Never releases lock
            Thread.yield();
    }

    /**
     * 在构造器中创建新线程并启动获取对象锁
     */
    public SynchronizedBlocked() {
        //该线程已持有当前实例锁
        new Thread() {
            public void run() {
                f();
                // Lock acquired by this thread
            }
        }.start();
    }

    public void run() {
        //中断判断
        while (true) {
            if (Thread.interrupted()) {
                System.out.println("中断线程!!");
                break;
            } else {
                f();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedBlocked sync = new SynchronizedBlocked();
        Thread t = new Thread(sync);
        //启动后调用f()方法,无法获取当前实例锁处于等待状态
        t.start();
        TimeUnit.SECONDS.sleep(1);

        /**
         * Thread.interrupt()方法不会中断一个正在运行的线程。它的作用是，在线程受到阻塞时抛出一个中断信号，这样线程就得以退出阻塞的状态。
         * 更确切的说，如果线程被Object.wait()、Thread.join()以及Thread.sleep()三种方法之一阻塞，
         * 那么，它将接收到一个中断异常（InterruptedException），从而提早地终结被阻塞状态。
         */
        //中断线程,无法生效
        t.interrupt();
    }
}
