package org.vitaminb6b12.bot.model;

public class AttachmentMessage extends OutgoingMessage {
	private OutgoingAttachment attachment;

	public AttachmentMessage() {
	}

	public OutgoingAttachment getAttachment() {
		return attachment;
	}

	public void setAttachment(OutgoingAttachment attachment) {
		this.attachment = attachment;
	}

	@Override
	public String toString() {
		return "AttachmentMessage [ attachment=" + this.attachment + " ]";
	}
}
