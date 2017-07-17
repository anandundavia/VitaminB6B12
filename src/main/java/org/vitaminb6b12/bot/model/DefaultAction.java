package org.vitaminb6b12.bot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DefaultAction {
	private String type;
	private String url;
	@JsonProperty("messenger_extensions")
	private boolean messengerExtensions;
	@JsonProperty("webview_height_ratio")
	private String webviewHeightRatio;
	@JsonProperty("fallback_url")
	private String fallbackUrl;

	private static final String DHL_URL = "https://www.mydhl.dhl.com/landing?utm_source=IN&utm_medium=nav&utm_campaign=get_rate_link&ccOverride=IN&language=en#portletShippingOptions";

	private static DefaultAction instance;

	private DefaultAction() {
	}

	public static DefaultAction getInstance() {
		if (instance == null) {
			instance = new DefaultAction();
			instance.setType("web_url");
			instance.setUrl(DHL_URL);
			instance.setMessengerExtensions(true);
			instance.setWebviewHeightRatio("tall");
			instance.setFallbackUrl(DHL_URL);
		}
		return instance;
	}

	private void setType(String type) {
		this.type = type;
	}

	private void setUrl(String url) {
		this.url = url;
	}

	private void setMessengerExtensions(boolean messengerExtensions) {
		this.messengerExtensions = messengerExtensions;
	}

	private void setWebviewHeightRatio(String webviewHeightRatio) {
		this.webviewHeightRatio = webviewHeightRatio;
	}

	private void setFallbackUrl(String fallbackUrl) {
		this.fallbackUrl = fallbackUrl;
	}

	public String getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

	public boolean isMessengerExtensions() {
		return messengerExtensions;
	}

	public String getWebviewHeightRatio() {
		return webviewHeightRatio;
	}

	public String getFallbackUrl() {
		return fallbackUrl;
	}

	@Override
	public String toString() {
		return "DefaultAction [ type=" + this.type + ", url=" + this.url + ", messenger_extensions="
				+ this.messengerExtensions + ", webview_height_ratio=" + this.webviewHeightRatio + ", fallback_url="
				+ this.fallbackUrl + " ]";
	}
}
