package org.vitaminb6b12.bot.model;

public final class Location {
	private Coordinate coordinates;

	public Location() {
	}

	public Coordinate getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinate coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public String toString() {
		return "Location [ coordinates=" + this.coordinates + " ]";
	}
}
