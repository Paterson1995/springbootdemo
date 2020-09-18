package com.demo.springbootdemo.util;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class PhantomReferenceDemo {
    public static void main(String[] args) {
        Object o1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        //软引用和弱引用也可以通过这种方式添加到引用队列中
        PhantomReference phantomReference = new PhantomReference(o1, referenceQueue);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());

        System.out.println("===========GC后");

        o1 = null;
        System.gc();
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());
    }
}
