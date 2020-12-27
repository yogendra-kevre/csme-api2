package com.csme.csmeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class CsmeApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CsmeApiApplication.class, args);
	}

	@Override
	 protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	  return application.sources(CsmeApiApplication.class);
	 }
}
