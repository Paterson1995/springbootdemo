package com.demo.springbootdemo.service.serviceImpl;

import com.demo.springbootdemo.domain.Student;
import com.demo.springbootdemo.mapper.StudentMapper;
import com.demo.springbootdemo.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements IStudentService {

    @Autowired
    StudentMapper studentMapper;

    @Override
    public int addStudent(Student student) {

        student.setRole("user");
        return studentMapper.insert(student);
    }

    @Override
    public Student login(String name, String password) {
        return studentMapper.login(name, password);
    }

    @Override
    public <T extends Number> void test(List<T> dest, List<T> src) {

    }
}
