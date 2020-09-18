package com.demo.springbootdemo.util;

public class StringDemo {

    public static void main(String[] args) {

        Object o = new Object();
        System.out.println(o);

        //String的构造方法没有参数时，相当于""
        String s = new String();
        System.out.println(s);

        Object nullObject = null;
        //println会对参数调用String.valueOf(),其参数为null时，会转换为"null",
        // 其底层调用的是toString()，该方法空对象调用时会报NullPointerException
        System.out.println(nullObject);
//        nullObject.toString();

    }

}
