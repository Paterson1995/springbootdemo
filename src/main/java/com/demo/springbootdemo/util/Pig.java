package com.demo.springbootdemo.util;

/**
 * 类的初始化和实例化
 */
public class Pig extends Zoo {
    public static String staticPk = staticPk();
    public String pk = pk();

    static {
        System.out.println("子类静态代码块!");
    }

    {
        System.out.println("子类非静态代码块");
    }

    public static String staticPk() {
        System.out.println("子类静态方法!");
        return "PK";
    }

    @Override
    public String pk() {
        System.out.println("子类普通方法!");
        return "PK";
    }

    public Pig() {
        System.out.println("子类构造器");
    }

    public static void main(String[] args) {
        //初始化
        Pig pig;
        System.out.println("---------");
        //实例化
        new Pig();
    }
}

class Zoo {
    public static String staticPk = staticPk();
    public String pk = pk();

    static {
        System.out.println("父类静态代码块!");
    }

    {
        System.out.println("父类非静态代码块");
    }

    public static String staticPk() {
        System.out.println("父类静态方法!");
        return "PK";
    }

    public String pk() {
        System.out.println("父类普通方法!");
        return "PK";
    }

    public Zoo() {
        System.out.println("父类构造器");
    }
}