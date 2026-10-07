package com.revision.springboot2026;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Marks the application entry point. This is a convenient combination of
// Spring configuration, auto-configuration, and component scanning. It applies
// sensible defaults and scans this package and its children for Spring beans.
// Without it, we would write much more setup and bean-registration code by hand.
@SpringBootApplication
public class Springboot2026Application {

	public static void main(String[] args) {
		SpringApplication.run(Springboot2026Application.class, args);
	}

}
