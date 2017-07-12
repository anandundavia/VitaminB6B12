package org.vitaminb6b12.bot.model;

public class QuickReply {
	private String payload;

	public QuickReply() {
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "QuickReply [ payload=" + this.payload + " ]";
	}
}
