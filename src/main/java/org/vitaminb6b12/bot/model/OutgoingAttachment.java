package org.vitaminb6b12.bot.model;

public class OutgoingAttachment {
	private String type;
	private Payload payload;

	public OutgoingAttachment() {
		this.type = "template";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "OutgoingAttachment [ type=" + this.type + ", payload=" + this.payload + " ]";
	}
}
