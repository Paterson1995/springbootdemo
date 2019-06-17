package com.demo.springbootdemo.mapper;

import com.demo.springbootdemo.domain.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMapper {

        int insert(Student student);

        String getPassword(String name);

        String getRole(String name);

        Student login(String name, String password);
}
