package org.vitaminb6b12.bot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The only use of this controller is to setup webhook on Facebook Messenger
 * Platform Read more about that here:
 * 
 * <pre>
 * https://developers.facebook.com/docs/graph-api/webhooks
 * </pre>
 * 
 * <br>
 * This controller only has one GET method which is
 * 
 * <pre>
 * /verify
 * </pre>
 * 
 * @author anand
 *
 */
@RestController
public class VerifyController {

	// This is the verification token which is to be matched against provided
	// token while setting up the webhook
	// Read more about that here:
	// <pre>https://developers.facebook.com/docs/graph-api/webhooks</pre>
	private static final String VERIFICATION_TOKEN = "VitaminB6B12_FACEBOOK_CHATBOT";
	private static final String MODE = "subscribe";

	// Return message if the verification is not successful
	private static final String UNSUPPORTED = "n0t$upp0rted";

	@RequestMapping("/verify")
	public String testMethod(@RequestParam("hub.mode") String mode, @RequestParam("hub.challenge") String challenge,
			@RequestParam("hub.verify_token") String verifyToken) {
		if (mode.equals(MODE) && verifyToken.equals(VERIFICATION_TOKEN))
			return challenge;
		return UNSUPPORTED;
	}
}
