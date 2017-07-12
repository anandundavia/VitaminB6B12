package org.vitaminb6b12.bot.model;

public class GenericMessaging extends Messaging {
	private Message message;

	public GenericMessaging() {
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "GenericMessaging [ sender=" + super.getSender() + ", recipient=" + super.getRecipient() + ", timestamp="
				+ super.getTimestamp() + ", message=" + this.message + " ]";
	}
}
