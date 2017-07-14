package org.vitaminb6b12.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Base class for the whole application. Contains method which are used while
 * Initialization
 * 
 * @author anand
 *
 */

@SpringBootApplication
public class Vitaminb6b12Application extends SpringBootServletInitializer {

	/**
	 * This function is used where the Spring Boot application is not
	 * initialized stand alone but run in external Tomcat container. It lets
	 * Tomcat container know which class to initialize
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Vitaminb6b12Application.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Vitaminb6b12Application.class, args);
	}
}
