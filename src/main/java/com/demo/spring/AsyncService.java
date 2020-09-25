package com.demo.spring;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncService {

    /**
     * 异步方法，它会在调用方的当前线程之外的独立的线程中执行，
     * 其实就相当于我们自己new Thread(()-> System.out.println("hello world !"))
     * 这样在另一个线程中去执行相应的业务逻辑。
     */
    @Async
    public void test() {
        System.out.println("当前线程：" + Thread.currentThread().getName());
    }
}
