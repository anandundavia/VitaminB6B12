package org.vitaminb6b12.bot.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vitaminb6b12.bot.model.CustomResponse;

@Service
public class FacebookService {

	private static final String PAGE_ACCESS_TOKEN = "EAASaePGbZCr4BAC5zigzUZB4P3Car4mIqdPg5RrW0wiekcEkPEnHGHDDfepZBjKav2PbKi0pWu9m6txFJhVA7qpZBI1uPNeeh8hqkV5ns2PFQtUONejBAQ7aqIZBuOBNb5AS23fdB0aFElHYNcLKhWIV9oHr9FIYifhu2KluQqRSD1TcvkQTS";

	public String getFirstName(String userid) {
		final String sendingUrl = "https://graph.facebook.com/v2.6/" + userid + "?fields=first_name&access_token="
				+ PAGE_ACCESS_TOKEN;
		System.out.println(sendingUrl);
		RestTemplate rt = new RestTemplate();
		CustomResponse req = rt.getForObject(sendingUrl, CustomResponse.class);
		return req.getFirstName();
	}

}
