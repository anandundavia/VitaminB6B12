package org.vitaminb6b12.bot.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenericPayload extends Payload {
	@JsonProperty("template_type")
	private String templateType;
	private List<Element> elements;

	public GenericPayload() {
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public List<Element> getElements() {
		return elements;
	}

	public void setElements(List<Element> elements) {
		this.elements = elements;
	}

	@Override
	public String toString() {
		return "GenericPayload [ template_type=" + this.templateType + ", elements=" + this.elements + " ]";
	}
}
