package org.vitaminb6b12.bot.model;

import java.util.HashMap;

public class Currency {
	private String base;
	private String date;
	private HashMap<String, Float> rates;

	public Currency() {
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public HashMap<String, Float> getRates() {
		return rates;
	}

	public void setRates(HashMap<String, Float> rates) {
		this.rates = rates;
	}
}
