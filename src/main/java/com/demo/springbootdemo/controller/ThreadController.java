package com.demo.springbootdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/admin/thread")
public class ThreadController {

    int flag = 0;

    Thread threadA = new Thread(() -> {
        synchronized(Thread.class) {
            System.out.println("A");
            flag = 1;
        }

    });
    Thread threadB = new Thread(() -> {
        System.out.println("B");
    });
    Thread threadC = new Thread(() -> {
        System.out.println("C");
    });



    Thread thread1 = new Thread(new Runnable() {
        @Override
        public void run() {

        }
    }, "1");
    Thread thread2 = new Thread(new Runnable() {
        @Override
        public void run() {

        }
    }, "2");
    Thread thread3 = new Thread(new Runnable() {
        @Override
        public void run() {

        }
    }, "3");











    public class ThreadThreadp {
        private int flag = 0;
        public synchronized void printa() throws InterruptedException {
            while (true)
            {
                if(flag ==0)
                {
                    System.out.print("A");
                    flag = 1;
                    notifyAll();
                }
                wait();
            }
        }
        public synchronized void printb() throws InterruptedException {
            while (true)
            {
                if(flag ==1)
                {
                    System.out.print("B");
                    flag = 2;
                    notifyAll();
                }
                wait();
            }
        }
        public synchronized void printc() throws InterruptedException {
            while (true) {
                if (flag == 2) {
                    System.out.print("C");
                    Thread.sleep(1000);
                    flag = 0;
                    notifyAll();
                }
                wait();
            }
        }

    }

    class PrintA implements Runnable{
        private  ThreadThreadp t;
        PrintA(ThreadThreadp t){
            this.t=t;
        }
        @Override
        public void run() {
            try {
                t.printa();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    class PrintB implements Runnable{

        private  ThreadThreadp t;
        PrintB(ThreadThreadp t){
            this.t=t;
        }
        @Override
        public void run() {
            try {
                t.printb();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    class PrintC implements Runnable{
        private  ThreadThreadp t;
        PrintC(ThreadThreadp t){
            this.t=t;
        }
        @Override
        public void run() {
            try {
                t.printc();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/useSyn")
    public void useSyn() throws InterruptedException {
        ThreadThreadp t = new ThreadThreadp();
        PrintA printA = new PrintA(t);
        PrintB printB = new PrintB(t);
        PrintC printC = new PrintC(t);
        Thread t1 = new Thread(printA);
        Thread t2 = new Thread(printB);
        Thread t3 = new Thread(printC);
        t1.start();
        t2.start();
        t3.start();
    }

    @RequestMapping("/join")
    public void join() throws InterruptedException {

//        测试线程优先度
//        threadA.setPriority(Thread.MIN_PRIORITY);
//        threadB.setPriority(Thread.NORM_PRIORITY);
//        threadC.setPriority(Thread.MAX_PRIORITY);

        threadA.start();
        threadB.start();
        threadC.start();
//        使用join方法

            threadA.join();

            threadB.join();

            threadC.join();
//        threadD.start();

//        使用newSingleThreadExecutor
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.submit(threadB);
//        executorService.submit(threadA);
//        executorService.submit(threadC);
//        executorService.submit(threadD);
    }

    @RequestMapping("/order1")
    public void printInOrder1() {

        //用线程池来实现 ，3个线程加入线程池
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            pool.submit(threadA);
            pool.submit(threadB);
            pool.submit(threadC);
        }
        pool.shutdown();
    }


}
