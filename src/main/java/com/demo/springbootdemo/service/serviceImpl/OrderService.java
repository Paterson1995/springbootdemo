package com.demo.springbootdemo.service.serviceImpl;

import com.demo.springbootdemo.service.IOrderService;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class OrderService implements IOrderService {

    @Override
    public void testAspect(String name) {
        System.out.println("执行OrderService的testAspect()方法,参数："+name);
    }

    @Transactional
    @Override
    public void B() {
        int i = 1/0;
    }
}
