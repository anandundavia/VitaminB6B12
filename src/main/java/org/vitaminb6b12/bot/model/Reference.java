package org.vitaminb6b12.bot.model;

public class Reference {
	private String ref;
	private String source;
	private String type;

	public Reference() {
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Reference [ref=" + this.ref + ", source=" + this.source + ", type=" + this.type + "]";
	}
}
