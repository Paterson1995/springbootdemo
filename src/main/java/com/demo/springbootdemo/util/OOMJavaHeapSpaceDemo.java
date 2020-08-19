package com.demo.springbootdemo.util;

import java.util.Random;

public class OOMJavaHeapSpaceDemo {

    public static void main(String[] args) {

//        byte[] bytes = new byte[11*1024*1024];
        String string = "";
        while (true) {

            /**
             * 最后一次GC
             * [GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] 5209K->5209K(9728K), 0.0009951 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
             * 最后一次Full GC
             * [Full GC (Allocation Failure) [PSYoungGen: 0K->0K(2560K)] [ParOldGen: 5209K->5209K(7168K)] 5209K->5209K(9728K),
             * [Metaspace: 3864K->3864K(1056768K)], 0.0054153 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
             */
            string = string + new Random().nextInt(888888888) + new Random().nextInt(99999999);
        }
    }
}
