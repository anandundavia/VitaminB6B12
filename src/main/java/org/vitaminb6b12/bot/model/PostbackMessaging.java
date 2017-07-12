package org.vitaminb6b12.bot.model;

public class PostbackMessaging extends Messaging {
	private Postback postback;

	public PostbackMessaging() {
	}

	public Postback getPostback() {
		return postback;
	}

	public void setPostback(Postback postback) {
		this.postback = postback;
	}

	@Override
	public String toString() {
		return "Postback [ postback=" + this.postback + " ] ";
	}
}
