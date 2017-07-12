package org.vitaminb6b12.bot.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = MultimediaAttachment.class, name = "audio"),
		@Type(value = MultimediaAttachment.class, name = "video"),
		@Type(value = MultimediaAttachment.class, name = "image"),
		@Type(value = MultimediaAttachment.class, name = "file"),
		@Type(value = FallbackAttachment.class, name = "fallback"),
		@Type(value = LocationAttachment.class, name = "location"), })
public abstract class Attachment {

}
