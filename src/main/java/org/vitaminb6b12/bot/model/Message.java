package org.vitaminb6b12.bot.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
	private String mid;
	private String text;
	@JsonProperty("sticker_id")
	private long stickerId;
	private int seq;
	private List<Attachment> attachments;
	@JsonProperty("quick_reply")
	private QuickReply quickReply;

	public Message() {
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public QuickReply getQuickReply() {
		return quickReply;
	}

	public void setQuickReply(QuickReply quickReply) {
		this.quickReply = quickReply;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	

	public long getStickerId() {
		return stickerId;
	}

	public void setStickerId(long stickerId) {
		this.stickerId = stickerId;
	}

	@Override
	public String toString() {
		return "Message [ mid=" + this.mid + ", text=" + this.text + ", attachments=" + this.attachments
				+ ", quick_reply=" + this.quickReply + " ]";
	}
}
