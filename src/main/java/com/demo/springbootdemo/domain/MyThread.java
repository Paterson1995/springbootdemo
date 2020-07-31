package com.demo.springbootdemo.domain;

public class MyThread extends Thread {

    private static Integer flag = 1;

    private String name;

    public MyThread(Integer flag, String name) {
        this.flag = flag;
        this.name = name;
    }

    public static void main(String[] args) {

        Object o = new Object();
        o.toString();
        if (String.valueOf(o) == "null") {
            System.out.println(o);
        }

        String s = null;
        if (String.valueOf(s) == "null") {
            System.out.println(s);
        }
//        new MyThread(flag, "A").start();
//        new MyThread(flag,"B").start();
//        new MyThread(flag,"C").start();
    }

    @Override
    public void run() {
        synchronized (flag) {
            for (int i = 0; i < 10; i++) {

                if (name.equals("A")) {
                    while (flag != 1) {
                        try {
                            flag.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(name);
                    flag = 2;
                    flag.notifyAll();
                } else if (name.equals("B")) {
                    while (flag != 2) {
                        try {
                            flag.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(name);
                    flag = 3;
                    flag.notifyAll();
                } else if (name.equals("C")) {
                    while (flag != 3) {
                        try {
                            flag.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(name);
                    flag = 1;
                    flag.notifyAll();
                }

            }
        }
    }
}