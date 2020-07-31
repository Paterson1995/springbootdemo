package com.demo.springbootdemo.controller;


import com.demo.springbootdemo.domain.Student;
import com.demo.springbootdemo.serviceImpl.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    private StudentService studentService;

    //登录操作
    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        Student s1 = studentService.login(name, password);
        if (s1 == null) {
            model.addAttribute("message","用户名或密码错误");
            model.addAttribute("operation_message", "重新登录");
            model.addAttribute("link", "/");
        } else {
            //登录成功后将用户放入session中，设置超时时间为60分钟，用于拦截
            request.getSession().setAttribute("session_user", s1);
            request.getSession().setMaxInactiveInterval(60*60);
            model.addAttribute("message", "登录成功");
            model.addAttribute("operation_message", "退出登录");
            model.addAttribute("link","/logout");
        }
        return "login_register_result";
    }

    //跳转注册页
    @RequestMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    //注册操作
    @RequestMapping("/register")
    public String register(Student student, Model model) {

        try {
            studentService.addStudent(student);
            model.addAttribute("message","注册成功");
            model.addAttribute("operation_message","回到登录页面");
            model.addAttribute("link", "/");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "注册失败");
            model.addAttribute("operation_message","重新注册");
            model.addAttribute("link", "/toRegister");
        }
        return "login_register_result";
    }

    //退出登录
    @RequestMapping("/logout")
    public void outUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("session_user");
        response.sendRedirect("/");
    }
}

