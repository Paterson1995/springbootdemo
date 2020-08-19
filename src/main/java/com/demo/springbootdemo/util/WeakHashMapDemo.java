package com.demo.springbootdemo.util;

import java.util.HashMap;
import java.util.WeakHashMap;

public class WeakHashMapDemo {
    public static void main(String[] args) {

        HashMap<String, String> hashMap = new HashMap<>();
        String key = new String("key");
        hashMap.put(key, "value");
        System.out.println(hashMap);

        key = null;
        System.out.println(hashMap);
        System.gc();
        System.runFinalization();
        System.out.println(hashMap);
        System.out.println("-------------------------------");

        WeakHashMap whm = new WeakHashMap();

        //放入两个没有外部引用的键值对
        whm.put(new String("语文") , "良好");
        whm.put(new String("数学") , "及格");
        System.out.println("原始的集合元素：whm="+whm);
        //key语文和数学，没有外部的引用指向它
        //垃圾回收器会回收调这两个键值对key-value
        // 通知系统立即进行垃圾回收
        System.gc();
        System.runFinalization();
        //垃圾回收器把集合中这两个没有外部引用的键值对释放掉
        System.out.print("垃圾回收器，回收key没有外部引用的WeakHashMap集合元素: whm="+whm+"\n");//whm={}
        System.out.println("-------------------------------");

        String yy = new String("英文");
        whm.put(yy , "中等");//放入有外部引用的键值对的
        System.out.println("原始的集合元素：whm="+whm);
        // 通知系统立即进行垃圾回收
        System.gc();
        System.runFinalization();
        System.out.println("key有外部引用指向的集合元素，垃圾回收器不会回收：whm="+whm);
        yy=null;//把外部引用置空
        System.gc();
        System.runFinalization();
        System.out.println("key的外部引用置空后：whm="+whm);
        System.out.println("-------------------------------");

        //key为Integer类型时结果一致，不过在元素被回收map打印为空的情况下size为1？？？
        String zz = "java";
        whm.put(zz, "中等");//key "java" 在字符串常量池，垃圾会收器回收不到
        System.out.println("原始的集合元素：whm="+whm);
        System.gc();
        System.runFinalization();
        System.out.println("垃圾回收器无法通过常量区的键回收：whm="+whm);
    }

}
