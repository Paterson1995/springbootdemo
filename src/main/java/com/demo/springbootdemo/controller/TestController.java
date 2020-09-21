package com.demo.springbootdemo.controller;

import com.demo.springbootdemo.service.IUserService;
import com.demo.springbootdemo.service.serviceImpl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RestController
public class TestController {

    @Autowired
    IUserService userService;

    @Transactional
    @RequestMapping("/testTransaction")
    public void testTransaction(String s) {
        userService.A(s);
    }
}
