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

@RestController
public class IncomingMessageController {

	@Autowired
	private CoreService coreService;

	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void handleIncomingMessage(@RequestBody IncomingMessage incomingMessage) {
		coreService.handleIncomingMessage(incomingMessage);
	}
}
	