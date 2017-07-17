package org.vitaminb6b12.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.vitaminb6b12.bot.model.IncomingMessage;
import org.vitaminb6b12.bot.service.CoreService;

/**
 * This controller is used to receive POST calls from the Facebook servers
 * whenever there is some incoming message to the bot. The endpoint
 * <code>/verify</code> will be called with a POST request and relevant data in
 * case of incoming message
 * 
 * @author anand
 *
 */
@RestController
public class IncomingMessageController {

	@Autowired
	private CoreService coreService;

	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void handleIncomingMessage(@RequestBody IncomingMessage incomingMessage) {
		coreService.handleIncomingMessage(incomingMessage);
	}

//	@RequestMapping(value = "/verify", method = RequestMethod.POST)
//	@ResponseStatus(value = HttpStatus.OK)
//	public void handleIncomingMessage(@RequestBody String incomingMessage) {
//		System.out.println(incomingMessage);
//	}
}
