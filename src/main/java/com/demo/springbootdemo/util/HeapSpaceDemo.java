package com.demo.springbootdemo.util;

public class HeapSpaceDemo {

    public static void main(String[] args) {
        //运行前配置JVM参数
        //-Xmx1024m -Xms1024m -XX:+PrintGCDetails
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        System.out.println("-Xmx:"+(maxMemory/1024)+"K");
        System.out.println("-Xms:"+(totalMemory/1024)+"K");

        /**
         * -Xmx:1005056KB
         * -Xms:1005056KB
         * Heap
         *  PSYoungGen      total 305664K, used 20971K [0x00000000eab00000, 0x0000000100000000, 0x0000000100000000)
         *   eden space 262144K, 8% used [0x00000000eab00000,0x00000000ebf7afb8,0x00000000fab00000)
         *   from space 43520K, 0% used [0x00000000fd580000,0x00000000fd580000,0x0000000100000000)
         *   to   space 43520K, 0% used [0x00000000fab00000,0x00000000fab00000,0x00000000fd580000)
         *  ParOldGen       total 699392K, used 0K [0x00000000c0000000, 0x00000000eab00000, 0x00000000eab00000)
         *   object space 699392K, 0% used [0x00000000c0000000,0x00000000c0000000,0x00000000eab00000)
         *  Metaspace       used 3246K, capacity 4496K, committed 4864K, reserved 1056768K
         *   class space    used 353K, capacity 388K, committed 512K, reserved 1048576K
         */
    }
}
