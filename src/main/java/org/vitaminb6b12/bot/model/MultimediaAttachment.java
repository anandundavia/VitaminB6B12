package org.vitaminb6b12.bot.model;

public final class MultimediaAttachment extends Attachment {
	private URL payload;

	public MultimediaAttachment() {
	}

	public URL getPayload() {
		return payload;
	}

	public void setPayload(URL payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "MultimediaAttachment [ payload=" + this.payload + " ]";
	}
}
