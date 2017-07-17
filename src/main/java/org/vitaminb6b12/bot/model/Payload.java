package org.vitaminb6b12.bot.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Payload {
	@JsonProperty("template_type")
	private String templateType;
	@JsonProperty("top_element_style")
	private String topElementStyle;
	private List<Element> elements;

	public Payload() {
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

	public String getTopElementStyle() {
		return topElementStyle;
	}

	public void setTopElementStyle(String topElementStyle) {
		this.topElementStyle = topElementStyle;
	}

	@Override
	public String toString() {
		return "Payload [ template_type=" + this.templateType + ", elements=" + this.elements + " ]";
	}
}
