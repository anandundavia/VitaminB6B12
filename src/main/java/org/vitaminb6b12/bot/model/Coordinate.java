package org.vitaminb6b12.bot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinate {
	private float lat;
	@JsonProperty("long")
	private float lng;

	public Coordinate() {
	}

	public float getLat() {
		return lat;
	}

	public void setLat(Number lat) {
		this.lat = lat.floatValue();
	}

	public float getLng() {
		return lng;
	}

	public void setLng(Number lng) {
		this.lng = lng.floatValue();
	}

	@Override
	public String toString() {
		return "Coordinate [ lat=" + this.lat + ", lng=" + this.lng + " ]";
	}
}
