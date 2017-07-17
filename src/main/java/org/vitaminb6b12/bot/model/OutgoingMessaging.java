package org.vitaminb6b12.bot.model;

public class OutgoingMessaging {
	private UserID recipient;
	private OutgoingMessage message;

	public OutgoingMessaging() {
	}

	public UserID getRecipient() {
		return recipient;
	}

	public void setRecipient(UserID recipient) {
		this.recipient = recipient;
	}

	public OutgoingMessage getMessage() {
		return message;
	}

	public void setMessage(OutgoingMessage message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "OutgoingMessaging [ receipient=" + this.recipient + ", message=" + this.message + " ]";
	}
}
