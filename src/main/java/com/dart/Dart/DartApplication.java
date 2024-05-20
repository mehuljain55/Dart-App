package com.dart.Dart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.dart.Dart")
public class DartApplication {

	public static void main(String[] args) {
		SpringApplication.run(DartApplication.class, args);
	}

}
