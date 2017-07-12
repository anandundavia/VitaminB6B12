package org.vitaminb6b12.bot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class URL {
	private String url;
	@JsonProperty("sticker_id")
	private long stickerId;

	public URL() {
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getStickerId() {
		return stickerId;
	}

	public void setStickerId(long stickerId) {
		this.stickerId = stickerId;
	}

	@Override
	public String toString() {
		return "Payload [ url=" + this.url + " ]";
	}
}
