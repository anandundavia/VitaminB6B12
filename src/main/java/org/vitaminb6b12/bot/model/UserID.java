package org.vitaminb6b12.bot.model;

public final class UserID {
	private String id;

	public UserID() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() { 
		return "Sender [ id=" + this.id + " ]";
	}
}
