package com.demo.springbootdemo.util;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreeThreadPrint {

    public static void main(String[] args) {
        SharedResource1 sharedResource = new SharedResource1();
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                    sharedResource.print(3);
                }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                    sharedResource.print(6);

            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                    sharedResource.print(9);
            }
        }, "C").start();
    }
}

class SharedResource {
    private int number = 1;
    public synchronized void print(int count) {

            String threadName = Thread.currentThread().getName();
            //1. 判断,wait()和await()必须放在while循环内，不能用if，否则会造成虚假唤醒
            while ((!threadName.equals("A") && number == 1) ||
                    (!threadName.equals("B") && number == 2) ||
                    (!threadName.equals("C") && number == 3)) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //2. 干活
            for (int i = 0; i < count; i++) {
                System.out.println(threadName + "\t" + (i + 1));
            }
            //3. 通知
            if (number == 3)
                number = 1;
            else
                number++;
            notifyAll();
    }
}

class SharedResource1 {

    Lock lock = new ReentrantLock();
    private int number = 1;
    private Condition c = lock.newCondition();
    public void print(int count) {

        lock.lock();
        try {
            String threadName = Thread.currentThread().getName();
            //1. 判断,wait()和await()必须放在while循环内，不能用if，否则会造成虚假唤醒
            while ((!threadName.equals("A") && number == 1) ||
                    (!threadName.equals("B") && number == 2) ||
                    (!threadName.equals("C") && number == 3)) {
                c.await();
            }
            //2. 干活
            for (int i = 0; i < count; i++) {
                System.out.println(threadName + "\t" + (i + 1));
            }
            //3. 通知
            if (number == 3)
                number = 1;
            else
                number++;
            c.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}


