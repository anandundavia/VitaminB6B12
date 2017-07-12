package org.vitaminb6b12.bot.model;

public class Postback {
	private String payload;
	private Reference referral;

	public Postback() {
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public Reference getReferral() {
		return referral;
	}

	public void setReferral(Reference referral) {
		this.referral = referral;
	}

	@Override
	public String toString() {
		return "Postback [ payload=" + this.payload + ", referral=" + this.referral + " ]";
	}
}
