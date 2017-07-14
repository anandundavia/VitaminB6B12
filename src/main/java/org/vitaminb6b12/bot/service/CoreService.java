package org.vitaminb6b12.bot.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.vitaminb6b12.bot.model.GenericMessaging;
import org.vitaminb6b12.bot.model.IncomingMessage;
import org.vitaminb6b12.bot.model.Message;
import org.vitaminb6b12.bot.model.Messaging;
import org.vitaminb6b12.bot.model.OutgoingMessage;
import org.vitaminb6b12.bot.model.PageEntry;
import org.vitaminb6b12.bot.model.PostbackMessaging;
import org.vitaminb6b12.bot.model.QuickReplyOption;
import org.vitaminb6b12.bot.model.TextMessage;

import ai.api.AIServiceException;
import ai.api.model.AIOutputContext;
import ai.api.model.AIResponse;

/**
 * This is communication and logical bridge between Facebook messenger and API
 * AI chatbot All the external logic outside the API AI chatbot resides here.
 * 
 * @author anand
 *
 */
@Service
public class CoreService {

	// Following veriables are used to match the context type from the bot
	private static final String ASK_DELIVERY_TYPE_CONTEXT = "send_package_dialog_params_delivery_type";
	private static final String ASK_PICKUP_TYPE_CONTEXT = "send_package_dialog_params_pickup_type";

	private static final Logger LOGGER = LoggerFactory.getLogger(CoreService.class.getName());
	private static final TaskExecutor executor = new SimpleAsyncTaskExecutor();

	@Autowired
	private OutgoingService outgoingService;

	@Autowired
	private APIAIService aiService;

	/**
	 * It adds the incoming message in the process queue and returns ASAP. So
	 * that calling controller can return 200 OK immediately
	 * 
	 * @param incomingMessage
	 */
	public void handleIncomingMessage(IncomingMessage incomingMessage) {
		synchronized (executor) {
			executor.execute(new MessageHandler(incomingMessage));
		}
	}

	/**
	 * This class does the actual work of handling the incoming message
	 * 
	 * @author anand
	 *
	 */
	class MessageHandler implements Runnable {
		private final IncomingMessage incomingMessage;

		public MessageHandler(IncomingMessage incomingMessage) {
			this.incomingMessage = incomingMessage;
		}

		@Override
		public void run() {

			final List<PageEntry> entryList = incomingMessage.getEntry();
			for (PageEntry entry : entryList) {
				// The webhook is only configured for the one page. So it will
				// only have messages received on one page (VitaminB6B12).
				// Thus skipping the checking of page id
				final List<Messaging> messagingList = entry.getMessaging();
				for (Messaging messaging : messagingList) {
					LOGGER.info(messaging.toString());
					// Prepare one reply for each message sent!
					final OutgoingMessage outgoingMessage = new OutgoingMessage();
					// The sender of the message will now be the receiver
					outgoingMessage.setRecipient(messaging.getSender());
					final TextMessage text = new TextMessage();
					try {
						if (messaging instanceof GenericMessaging) {
							final GenericMessaging message = (GenericMessaging) messaging;
							final Message receivedMessage = message.getMessage();
							// Text in the received message can be null in case
							// if there is attachment in the message
							// Read more here:
							// https://developers.facebook.com/docs/messenger-platform/webhook-reference/message
							// Do we have text?
							if (receivedMessage.getText() == null) {
								// Nope we don't have text.
								// Let's check if we have any stickers in the
								// message
								if (receivedMessage.getStickerId() != 0L) {
									// Yeah! we have stickers
									text.setText("Even I love stickers! \u263A");
								} else if (receivedMessage.getAttachments() != null) {
									// We don't have stickers but we have
									// attachments!
									// TODO: You might want to customize the
									// type of the reply based on the
									// attachment.
									// For example "I wander what image is this"
									// or "It is sad that I can not listen to
									// voices"
									text.setText("Hmm... I smell attachments. \u263A");
								} else {
									// We neither have text nor stickers nor
									// attachments.
									// Did Facebook change the message
									// structure?
									text.setText("I literally have no idea what did you send \uD83D\uDE10");
								}
							} else {
								// We have some text! Put API AI to work!
								try {
									final AIResponse reply = aiService.getReply(message);
									final String speech = reply.getResult().getFulfillment().getSpeech();
									findContextAndSetQuickReply(reply, text);
									text.setText(speech);
								} catch (AIServiceException e) {
									LOGGER.error(e.getMessage(), e);
									text.setText("My AI has somehow thrown exception :( ");
								}
							}
						} else if (messaging instanceof PostbackMessaging) {
							// This will happen only when we enable 'Get
							// Started' button
							// or We have some sorta Persistence Menu or Buttons
							// Read here:
							// https://developers.facebook.com/docs/messenger-platform/webhook-reference/postback
							PostbackMessaging message = (PostbackMessaging) messaging;
							text.setText(message.getPostback().getPayload());
						} else {
							LOGGER.warn("Cannot determine type of the message: " + messaging);
							text.setText("I do not know what to say \uD83D\uDE10");
						}
					} catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
						text.setText("I threw en exception \u2639");
					} finally {
						// Ah finally after long ups and downs, sending the
						// reply
						outgoingMessage.setMessage(text);
						outgoingService.sendMessage(outgoingMessage);
					}

				}
			}

		}

		/**
		 * This methods finds the context from the result of the AI output and
		 * sets the quick reply if any
		 * 
		 * @param reply
		 * @param text
		 */
		private void findContextAndSetQuickReply(AIResponse reply, TextMessage text) {
			final List<AIOutputContext> contexts = reply.getResult().getContexts();
			for (AIOutputContext ctx : contexts) {
				switch (ctx.getName()) {
				case ASK_DELIVERY_TYPE_CONTEXT: {
					final QuickReplyOption domestic = new QuickReplyOption("text", "Domestic", "Domestic");
					final QuickReplyOption international = new QuickReplyOption("text", "International",
							"International");
					text.addQuickReplyOption(domestic);
					text.addQuickReplyOption(international);
					return;
				}
				case ASK_PICKUP_TYPE_CONTEXT: {
					final QuickReplyOption domestic = new QuickReplyOption("text", "Drop Off", "Drop Off");
					final QuickReplyOption international = new QuickReplyOption("text", "Schedule Pick Up",
							"Schedule Pick Up");
					text.addQuickReplyOption(domestic);
					text.addQuickReplyOption(international);
					return;
				}
				default: {

				}
				}
			}

		}
	}
}
