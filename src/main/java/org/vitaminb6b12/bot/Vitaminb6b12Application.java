package org.vitaminb6b12.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class Vitaminb6b12Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Vitaminb6b12Application.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Vitaminb6b12Application.class, args);
	}
}
