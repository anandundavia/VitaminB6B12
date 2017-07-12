package org.vitaminb6b12.bot.model;

import java.util.List;

public class IncomingMessage {
	private final String obejct;
	private List<PageEntry> entry;

	public IncomingMessage() {
		this.obejct = "page";
	}

	public List<PageEntry> getEntry() {
		return entry;
	}

	public void setEntry(List<PageEntry> entry) {
		this.entry = entry;
	}

	public String getObejct() {
		return obejct;
	}

	@Override
	public String toString() {
		return "IncomingMessage [ object=" + this.obejct + ", entry=" + this.entry + " ]";

	}

}
