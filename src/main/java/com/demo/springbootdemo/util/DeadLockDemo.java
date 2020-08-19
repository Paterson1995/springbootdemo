package com.demo.springbootdemo.util;

import java.util.concurrent.TimeUnit;

/**
 * 死锁的排查
 * 1.jps -l：查看系统当前正在运行的java程序
 * 2.jstack 进程号：查看进程的堆栈状态
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new HoldLockThread(lockA, lockB), "ThreadA").start();
        new Thread(new HoldLockThread(lockB, lockA), "ThreadB").start();
    }
}

class HoldLockThread implements Runnable {
    private String lockA;
    private String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t自己持有：" + lockA + "\t尝试获取：" + lockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t已经获取了lockB");
            }
        }
    }
}
