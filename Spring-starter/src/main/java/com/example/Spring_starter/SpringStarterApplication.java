package com.example.Spring_starter;

import com.example.Spring_starter.Bean.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringStarterApplication {

	public static void main(String[] args) {

		var container = SpringApplication.run(SpringStarterApplication.class, args);
		Student student= container.getBean(Student.class);

		System.out.println(student);
	}

}
