package org.vitaminb6b12.bot.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = MessageDeserializer.class)
public abstract class Messaging {
	private UserID sender;
	private UserID recipient;
	private long timestamp;

	public UserID getSender() {
		return sender;
	}

	public void setSender(UserID sender) {
		this.sender = sender;
	}

	public UserID getRecipient() {
		return recipient;
	}

	public void setRecipient(UserID recipient) {
		this.recipient = recipient;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
