package com.demo.springbootdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @RequestMapping("/index")
    @ResponseBody
    public String index(String name) {

        if(name == null) {
            throw new NullPointerException();
        }
        return "index"+name;
    }

    @RequestMapping("/index1")
    @ResponseBody
    public String index1(String name, String name1) {

        if(name == null||name1 ==null) {
            throw new NullPointerException();
        }
        return "index1"+name+name1;
    }

    @RequestMapping("/index2")
    @ResponseBody
    public String index2(String name) {

        if(name == null) {
            throw new NullPointerException();
        }
        return "index2"+name;
    }
}
