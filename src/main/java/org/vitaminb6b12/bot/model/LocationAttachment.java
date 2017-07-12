package org.vitaminb6b12.bot.model;

public final class LocationAttachment extends Attachment {
	private String title;
	private Location payload;
	private String url;

	public LocationAttachment() {
	}

	public Location getPayload() {
		return payload;
	}

	public void setPayload(Location payload) {
		this.payload = payload;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "LocationAttachment [ payload=" + this.payload + " ]";
	}

}
