package com.demo.springbootdemo.util;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ReferenceDemo {
    public static void main(String[] args) {
//        strongRef();
//        softRefMemoryEnough();
        softRefMemoryNotEnough();
//        weakRef();
    }

    public static void strongRef() {
        System.out.println("\n===========strongRef:");
        Object o1=new Object();
        Object o2=new Object();
        o1=null;
        System.gc();
        System.out.println("===========after GC");
        System.out.println(o2);
    }

    private static void softRefMemoryEnough() {
        System.out.println("\n===========softRefMemoryEnough:");
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());
        o1 = null;
        System.gc();
        System.out.println("===========after GC");
        System.out.println(softReference.get());
    }

    private static void softRefMemoryNotEnough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());
        o1 = null;
        System.gc();
        System.out.println("===========after GC");
        try {
            byte[] bytes = new byte[30 * 1024 * 1024];
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(softReference.get());
        }
    }

    public static void weakRef() {
        System.out.println("\n===========weakRef:");
        Object o1 = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(o1);
        System.out.println(o1);
        System.out.println(weakReference.get());
        o1 = null;
        System.gc();
        System.out.println("===========after GC");
        System.out.println(weakReference.get());
    }
}
