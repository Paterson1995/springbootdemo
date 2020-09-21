package com.demo.springbootdemo.service.serviceImpl;

import com.demo.springbootdemo.mapper.UserMapper;
import com.demo.springbootdemo.service.IOrderService;
import com.demo.springbootdemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements IUserService {

    static ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<>();

    @Autowired
    UserMapper userMapper;

    @Autowired
    OrderService orderService;

    @Transactional
    @Override
    public void A(String s) {
        userMapper.update(s);
//        int i = 1/0;
//        orderService.B();
//        B();
    }

    @Transactional
    @Override
    public void B() {
        int i = 1/0;
    }
}
