package com.demo.springbootdemo.service;

import com.demo.springbootdemo.domain.Student;

public interface IStudentService {

    int addStudent(Student student);

    Student login(String name, String password);

}
