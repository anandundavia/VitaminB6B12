package org.vitaminb6b12.bot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuickReplyOption {
	@JsonProperty("content_type")
	private String contentType;
	private String title;
	private String payload;

	public QuickReplyOption() {
	}

	public QuickReplyOption(String contentType, String title, String payload) {
		super();
		this.contentType = contentType;
		this.title = title;
		this.payload = payload;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "QuickReplyOption [ content_type=" + this.contentType + ", title=" + this.title + ", payload="
				+ this.payload + " ]";
	}
}
