package org.vitaminb6b12.bot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Element {
	private String title;
	private String subtitle;
	@JsonProperty("image_url")
	private String imageURL;
	@JsonProperty("default_action")
	private DefaultAction defaultAction;

	public Element() {
	}

	public Element(String title, String subtitle, String imageURL) {
		super();
		this.title = title;
		this.subtitle = subtitle;
		this.imageURL = imageURL;
		this.defaultAction = DefaultAction.getInstance();
	}

	public Element(String title, String subtitle, String imageURL, String otherURL) {
		super();
		this.title = title;
		this.subtitle = subtitle;
		this.imageURL = imageURL;
		this.defaultAction = DefaultAction.getInstance();
		this.defaultAction.setURLs(otherURL);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public DefaultAction getDefaultAction() {
		return defaultAction;
	}

	public void setDefaultAction(DefaultAction defaultAction) {
		this.defaultAction = defaultAction;
	}

	@Override
	public String toString() {
		return "Element [ title=" + this.title + ", subtitle=" + this.subtitle + ", image_url=" + this.imageURL
				+ ", default_action=" + this.defaultAction + " ]";
	}
}
