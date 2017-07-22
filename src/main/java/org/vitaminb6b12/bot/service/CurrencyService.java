package org.vitaminb6b12.bot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vitaminb6b12.bot.model.Currency;

@Service
public class CurrencyService {

	private static final String BASE_URL = "http://api.fixer.io/latest?";
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyService.class.getName());

	/**
	 * Returns the factor to be multiplied to convert the currency from given
	 * 'from' to 'to'
	 * 
	 * @param from
	 * @param to
	 * @return Multiplier factor
	 */
	public float convert(String from, String to) {

		float returnThis = 1.0F;
		if (from.equals(to)) {
			return returnThis;
		}
		try {
			final String requestURL = BASE_URL + "base=" + from + "&symbols=" + to;
			RestTemplate rt = new RestTemplate();
			final Currency response = rt.getForObject(requestURL, Currency.class);
			returnThis = response.getRates().get(to);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return returnThis;
	}
}
