package com.demo.springbootdemo.serviceImpl;

import com.demo.springbootdemo.domain.Student;
import com.demo.springbootdemo.mapper.StudentMapper;
import com.demo.springbootdemo.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService implements IStudentService {

    @Autowired
    StudentMapper studentMapper;

    public int addStudent(Student student) {

        student.setRole("user");
        return studentMapper.insert(student);
    }

    @Override
    public Student login(String name, String password) {
        return studentMapper.login(name, password);
    }


}
