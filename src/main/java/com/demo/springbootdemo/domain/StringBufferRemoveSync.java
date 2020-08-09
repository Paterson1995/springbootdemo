package com.demo.springbootdemo.domain;

/**
 * 消除锁是虚拟机另外一种锁的优化，这种优化更彻底，Java虚拟机在JIT编译时(可以简单理解为当某段代码即将第一次被执行时进行编译，又称即时编译)，
 * 通过对运行上下文的扫描，去除不可能存在共享资源竞争的锁，通过这种方式消除没有必要的锁，
 * 可以节省毫无意义的请求锁时间，如下StringBuffer的append是一个同步方法，
 * 但是在add方法中的StringBuffer属于一个局部变量，并且不会被其他线程所使用，因此StringBuffer不可能存在共享资源竞争的情景，
 * JVM会自动将其锁消除，但是比如i++，这个i是全局变量共享资源。那么该变量所对应的方法的锁则不会被消除。
 */
public class StringBufferRemoveSync {

    public void add(String str1, String str2) {
        //StringBuffer是线程安全,由于sb只会在append方法中使用,不可能被其他线程引用
        //因此sb属于不可能共享的资源,JVM会自动消除内部的锁
        StringBuffer sb = new StringBuffer();
        sb.append(str1).append(str2);
        System.out.println(sb);
    }

    public static void main (String[] args) {
        StringBufferRemoveSync rmsync = new StringBufferRemoveSync();
        for (int i = 0; i < 100; i++) {
            rmsync.add("abc","123");
        }
    }

}
