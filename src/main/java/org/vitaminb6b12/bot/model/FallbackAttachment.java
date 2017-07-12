package org.vitaminb6b12.bot.model;

public final class FallbackAttachment extends Attachment {
	private String title;
	private String url;
	private Object payload;

	public FallbackAttachment() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "FallbackAttachment [ title=" + this.title + ", url=" + this.url + ", payload=" + this.payload + " ]";
	}
}
