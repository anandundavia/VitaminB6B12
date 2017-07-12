package org.vitaminb6b12.bot.model;

public class TextMessage {
	private String text;

	public TextMessage() {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "TextMessage [ text=" + this.text + " ]";
	}
}
