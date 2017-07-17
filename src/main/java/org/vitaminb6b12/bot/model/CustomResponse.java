package org.vitaminb6b12.bot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomResponse {
	@JsonProperty("first_name")
	private String firstName;
	
	public CustomResponse() {}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
}
