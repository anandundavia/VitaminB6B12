package org.vitaminb6b12.bot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.api.model.AIResponse;

@Document(collection = "conversations")
public class Conversation {
	@Id
	@Indexed
	private final Long id;
	private AIResponse data;

	public Conversation() {
		this.id = System.currentTimeMillis();
	}

	public Conversation(AIResponse reply) {
		this.id = System.currentTimeMillis();
		data = reply;
	}

	public Long getId() {
		return id;
	}

	public AIResponse getData() {
		return data;
	}

	public void setData(AIResponse data) {
		this.data = data;
	}

}
