package com.demo.springbootdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@MapperScan("com.demo.springbootdemo.mapper")
@ServletComponentScan("com.demo.springbootdemo.filter")
public class SpringbootdemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringbootdemoApplication.class, args);
	}

}
