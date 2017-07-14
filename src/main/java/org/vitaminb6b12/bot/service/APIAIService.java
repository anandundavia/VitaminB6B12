package org.vitaminb6b12.bot.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.vitaminb6b12.bot.model.GenericMessaging;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

@Service
public class APIAIService {

	private static final String API_AI_CLIENT_ACCESS_TOKEN = "cbde8ed367b1492c843af1298a80e17b";
	private final AIConfiguration configuration = new AIConfiguration(API_AI_CLIENT_ACCESS_TOKEN);

	// This in-memory HashMap saves the AIDataService for each user.
	// Helps in separating chats of different users
	// Word of caution? This will cause OutOfMemory error if there are so many
	// users.
	// Consider saving it to persistent storage
	private final HashMap<String, AIDataService> userMap = new HashMap<>();

	/**
	 * This method will take the input message and get the reply from API AI
	 * chatbot
	 * 
	 * @param messaging
	 * @return
	 * @throws AIServiceException
	 */
	public AIResponse getReply(GenericMessaging messaging) throws AIServiceException {
		final String senderId = messaging.getSender().getId();
		final String message = messaging.getMessage().getText();
		if (!userMap.containsKey(senderId)) {
			userMap.put(senderId, new AIDataService(configuration));
		}
		final AIDataService dataService = userMap.get(senderId);
		final AIRequest request = new AIRequest(message);
		final AIResponse response = dataService.request(request);
		return response;
	}
}
