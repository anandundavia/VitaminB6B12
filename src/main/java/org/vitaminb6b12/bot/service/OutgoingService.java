package org.vitaminb6b12.bot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vitaminb6b12.bot.model.OutgoingMessage;

/**
 * This service is used to make a POST request to Facebook servers which will
 * result in the message reply
 * 
 * @author anand
 *
 */
@Service
public class OutgoingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OutgoingService.class.getName());

	private static final String PAGE_ACCESS_TOKEN = "EAASaePGbZCr4BAC5zigzUZB4P3Car4mIqdPg5RrW0wiekcEkPEnHGHDDfepZBjKav2PbKi0pWu9m6txFJhVA7qpZBI1uPNeeh8hqkV5ns2PFQtUONejBAQ7aqIZBuOBNb5AS23fdB0aFElHYNcLKhWIV9oHr9FIYifhu2KluQqRSD1TcvkQTS";
	private static final String SENDING_URL = "https://graph.facebook.com/v2.6/me/messages?access_token="
			+ PAGE_ACCESS_TOKEN;

	public void sendMessage(OutgoingMessage outgoingMessage) {
		RestTemplate rt = new RestTemplate();
		LOGGER.info("Sending: " + outgoingMessage);
		Object postForObject = rt.postForObject(SENDING_URL, outgoingMessage, Object.class);
		LOGGER.info("Message successfully sent: " + postForObject);
	}
}
