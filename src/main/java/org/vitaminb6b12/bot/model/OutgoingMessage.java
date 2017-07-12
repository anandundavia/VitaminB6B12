package org.vitaminb6b12.bot.model;

public class OutgoingMessage {
	private UserID recipient;
	private TextMessage message;

	public OutgoingMessage() {}
	
	public UserID getRecipient() {
		return recipient;
	}

	public void setRecipient(UserID recipient) {
		this.recipient = recipient;
	}

	public TextMessage getMessage() {
		return message;
	}

	public void setMessage(TextMessage message) {
		this.message = message;
	}
	
	
	@Override
	public String toString() {
		return "OutgoingMessage [ receipient="+this.recipient+", message="+this.message+" ]";
	}
}
