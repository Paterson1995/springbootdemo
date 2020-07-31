package com.demo.springbootdemo.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rhwayfun on 16-4-2.
 */
public class WaitNotifyThread {

    //条件是否满足的标志
    private static boolean flag = true;
    //对象的监视器锁
    private static Object lock = new Object();
    //日期格式化器
    private static DateFormat format = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) throws InterruptedException {
        Thread waitThread = new Thread(new WaitThread(),"WaitThread");
        waitThread.start();
        Thread.currentThread().sleep(1000);
        Thread notifyThread = new Thread(new NotifyThread(),"NotifyThread");
        notifyThread.start();
    }

    /**
     * 等待线程
     */
    private static class WaitThread implements Runnable{
        public void run() {
            //加锁，持有对象的监视器锁
            synchronized (lock){
                //只有成功获取对象的监视器才能进入这里
                //当条件不满足的时候，继续wait，直到某个线程执行了通知
                //并且释放了lock的监视器（简单来说就是锁）才能从wait
                //方法返回
                while (flag){
                    try {
                        System.out.println(Thread.currentThread().getName() + " flag is true,waiting at "
                                + format.format(new Date()));
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //条件满足，继续工作
                System.out.println(Thread.currentThread().getName() + " flag is false,running at "
                        + format.format(new Date()));
            }
        }
    }

    /**
     * 通知线程
     */
    private static class NotifyThread implements Runnable{
        public void run() {
            synchronized (lock){
                //获取lock锁，然后执行通知，通知的时候不会释放lock锁
                //只有当前线程退出了lock后，waitThread才有可能从wait返回
                System.out.println(Thread.currentThread().getName() + " holds lock. Notify waitThread at "
                        + format.format(new Date()));
                lock.notifyAll();
                flag = false;
                try {
                    Thread.currentThread().sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //再次加锁
            synchronized (lock){
                System.out.println(Thread.currentThread().getName() + " holds lock again. NotifyThread will sleep at "
                        + format.format(new Date()));
                try {
                    Thread.currentThread().sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
