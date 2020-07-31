package com.demo.springbootdemo.service;

import com.demo.springbootdemo.domain.Student;

import java.util.List;

public interface IStudentService {

    int addStudent(Student student);

    Student login(String name, String password);

    public <T extends Number> void test(List<T> dest, List<T> src);

}
