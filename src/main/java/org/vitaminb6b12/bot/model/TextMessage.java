package org.vitaminb6b12.bot.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TextMessage extends OutgoingMessage {
	private String text;
	@JsonProperty("quick_replies")
	private List<QuickReplyOption> quickReplies;

	public TextMessage() {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<QuickReplyOption> getQuickReplies() {
		return quickReplies;
	}

	public void setQuickReplies(List<QuickReplyOption> quickReplies) {
		this.quickReplies = quickReplies;
	}

	public void addQuickReplyOption(QuickReplyOption option) {
		if (this.quickReplies == null) {
			this.quickReplies = new ArrayList<>();
		}
		this.quickReplies.add(option);
	}

	@Override
	public String toString() {
		return "TextMessage [ text=" + this.text + ", quickReplies=" + this.quickReplies + " ]";
	}
}
