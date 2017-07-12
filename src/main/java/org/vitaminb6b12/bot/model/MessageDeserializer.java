package org.vitaminb6b12.bot.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageDeserializer extends JsonDeserializer<Messaging> {

	@Override
	public Messaging deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		ObjectCodec x = jp.getCodec();
		JsonNode node = x.readTree(jp);
		if (node != null) {
			final ObjectMapper mapper = new ObjectMapper();
			if (node.has("postback")) {
				PostbackMessaging returnThis = new PostbackMessaging();
				returnThis.setSender(mapper.readValue(node.get("sender").toString(), UserID.class));
				returnThis.setRecipient(mapper.readValue(node.get("recipient").toString(), UserID.class));
				returnThis.setTimestamp(mapper.readValue(node.get("timestamp").toString(), Long.class));
				returnThis.setPostback(mapper.readValue(node.get("postback").toString(), Postback.class));
				return returnThis;
			} else {
				GenericMessaging returnThis = new GenericMessaging();
				returnThis.setSender(mapper.readValue(node.get("sender").toString(), UserID.class));
				returnThis.setRecipient(mapper.readValue(node.get("recipient").toString(), UserID.class));
				returnThis.setTimestamp(mapper.readValue(node.get("timestamp").toString(), Long.class));
				returnThis.setMessage(mapper.readValue(node.get("message").toString(), Message.class));
				return returnThis;
			}
		}
		return null;
	}

}
