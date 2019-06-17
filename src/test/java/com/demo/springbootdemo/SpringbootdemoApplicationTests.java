package com.demo.springbootdemo;

import com.demo.springbootdemo.domain.Student;
import com.demo.springbootdemo.mapper.StudentMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootdemoApplicationTests {

	@Autowired
	StudentMapper studentMapper;

	@Test
	public void insertStudent() {
		Student s = new Student();
		s.setNo(3);
		s.setName("sunu");
		s.setMajor("cs");
		s.setBirthdate(new Date());
		s.setPassword("123");
		s.setSex("男");
		s.setRole("user");

		int count = studentMapper.insert(s);
		System.out.println("+++++++++++++++"+count);
	}

}
