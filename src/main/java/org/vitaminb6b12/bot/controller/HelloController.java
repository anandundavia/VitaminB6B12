package org.vitaminb6b12.bot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@RequestMapping("/test")
	public String testMethod() {
		return "Hello World!";
	}
}
