package org.vitaminb6b12.bot.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.vitaminb6b12.bot.model.AttachmentMessage;
import org.vitaminb6b12.bot.model.Conversation;
import org.vitaminb6b12.bot.model.Element;
import org.vitaminb6b12.bot.model.GenericMessaging;
import org.vitaminb6b12.bot.model.IncomingMessage;
import org.vitaminb6b12.bot.model.Message;
import org.vitaminb6b12.bot.model.Messaging;
import org.vitaminb6b12.bot.model.OutgoingAttachment;
import org.vitaminb6b12.bot.model.OutgoingMessaging;
import org.vitaminb6b12.bot.model.PageEntry;
import org.vitaminb6b12.bot.model.Payload;
import org.vitaminb6b12.bot.model.PostbackMessaging;
import org.vitaminb6b12.bot.model.QuickReplyOption;
import org.vitaminb6b12.bot.model.TextMessage;
import org.vitaminb6b12.bot.persistence.ConversationRepository;

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

	// Following variables are used to match the context type from the bot
	private static final String ASK_DELIVERY_TYPE_CONTEXT = "send_package_dialog_params_delivery_type";
	private static final String ASK_PICKUP_TYPE_CONTEXT = "send_package_dialog_params_pickup_type";
	private static final String ASK_INQUIRY_TYPE_CONTEXT = "book_inquiry_dialog_params_inquiry_type";

	// Following are the actions
	private static final String GET_QUOTE_FINISHED = "get_quote";
	private static final String GET_HELP_FINISHED = "smalltalk.agent.can_you_help";
	private static final String BOOK_INQUIRY_FINISHED = "book_inquiry";
	private static final String STORE_LOCATE_FINISHED = "locate_store";

	// private static final String LENGTH_PARAM = "length";
	// private static final String WIDTH_PARAM = "width";
	// private static final String HEIGHT_PARAM = "height";
	// private static final String WEIGHT_PARAM = "weight";
	//
	// private static final String ALLOWED_LENGTH_UNIT = "cm";

	private static final String GET_STARTED_PAYLOAD = "vitaminb6b12_get_started";

	private static final String GET_STARTED_MESSAGE = "I am the chatbot prepared by VitaminB6B12 team for UNITEDBYHCL hackathon!\n\n"
			+ "I can help you in a few ways.\n"
			+ "You can get estimated time and rate quote of your parcel. Say 'I want to get rate and time quote' and I will help you further\n"
			+ "I can help you contact customer cate! Say 'I want have an inquiry' if you want to do so\n\n"
			+ "I am always available for little chitchat ;)";

	private static final Logger LOGGER = LoggerFactory.getLogger(CoreService.class.getName());
	private static final TaskExecutor executor = new SimpleAsyncTaskExecutor();

	@Autowired
	private OutgoingService outgoingService;

	@Autowired
	private APIAIService aiService;

	@Autowired
	private FacebookService fbService;

	@Autowired
	private ConversationRepository repository;

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
					final OutgoingMessaging outgoingMessage = new OutgoingMessaging();
					// The sender of the message will now be the receiver
					outgoingMessage.setRecipient(messaging.getSender());
					final TextMessage text = new TextMessage();

					AIResponse reply = null;
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
									reply = aiService.getReply(message);
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
							handlePayload(text, message);
						} else {
							LOGGER.warn("Cannot determine type of the message: " + messaging);
							text.setText("I do not know what to say \uD83D\uDE10");
						}
					} catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
						text.setText("I threw en exception \u2639");
					} finally {
						// Ah finally after long ups and downs, sending the
						// reply. But before that, check if it is empty
						if (!text.getText().equals("")) {
							// It is not!
							outgoingMessage.setMessage(text);
							outgoingService.sendMessage(outgoingMessage);
						}

						// Did we have any completed action?
						if (reply != null && !reply.getResult().isActionIncomplete()) {
							// Yeah we had. Time to handle em
							actionComplete(reply, outgoingMessage);
							saveConversation(reply);

						}
					}

				}
			}

		}

		private void saveConversation(AIResponse reply) {
			final String action = reply.getResult().getAction();
			boolean save = false;
			switch (action) {
			case GET_QUOTE_FINISHED: {
				save = true;
				break;
			}
			case BOOK_INQUIRY_FINISHED: {
				save = true;
				break;
			}
			case STORE_LOCATE_FINISHED: {
				save = true;
				break;
			}
			}
			if (save) {
				final Conversation saveThis = new Conversation(reply);
				repository.save(saveThis);
				LOGGER.info("Successfully saved conversation with id: " + saveThis.getId());
			}

		}

		// private void findAndValidateParams(AIResponse reply, TextMessage
		// text) {
		// final HashMap<String, JsonElement> parameters =
		// reply.getResult().getParameters();
		// if (parameters.containsKey(LENGTH_PARAM)) {
		// JsonElement element = parameters.get(LENGTH_PARAM);
		// JsonObject object = element.getAsJsonObject();
		// System.out.println("Yeah! it has the length");
		// String unit = object.get("unit").getAsString();
		// } else if (parameters.containsKey(WIDTH_PARAM)) {
		//
		// } else if (parameters.containsKey(HEIGHT_PARAM)) {
		//
		// } else if (parameters.containsKey(WEIGHT_PARAM)) {
		//
		// }
		// }

		private void handlePayload(TextMessage text, PostbackMessaging message) {
			final String payload = message.getPostback().getPayload();
			switch (payload) {
			case GET_STARTED_PAYLOAD: {
				final String firstName = fbService.getFirstName(message.getSender().getId());
				text.setText("Hi " + firstName + "!\n" + GET_STARTED_MESSAGE
						+ "\nJust in case you are stuck any where, say 'help' to know what I can do for ya! :)");
				break;
			}
			default: {
				break;
			}
			}

		}

		private void actionComplete(AIResponse reply, OutgoingMessaging outgoingMessage) {

			final String action = reply.getResult().getAction();
			switch (action) {
			case GET_QUOTE_FINISHED: {
				final AttachmentMessage attachment = new AttachmentMessage();
				int onlineCost = ((int) (1000000 * Math.random()) % 12211) + 159;
				final Element shipOnline = new Element("Ship online. Cost: " + onlineCost + "Rs.",
						"Ship online and schedule a courier to pick up your parcel at your home or office",
						"https://www.mydhl.dhl.com/content/dam/Local_Images/g0/express/mydhl/shipping_click.png");

				int dropoffCost = ((int) (1000000 * Math.random()) % 12211) + 127;
				final Element dropOff = new Element("Drop off. Cost: " + dropoffCost + "Rs.",
						"An easy way to send documents and parcels – just drop off your parcel at the nearest DHL Service Point.",
						"https://www.mydhl.dhl.com/content/dam/Local_Images/g0/express/mydhl/shipping_walk.png");

				final ArrayList<Element> elements = new ArrayList<>();
				elements.add(shipOnline);
				elements.add(dropOff);

				final Payload payload = new Payload();
				payload.setTemplateType("list");
				payload.setTopElementStyle("compact"); // Well if you don't do
														// this, then the first
														// element look ugly
				payload.setElements(elements);

				final OutgoingAttachment outAttachment = new OutgoingAttachment();
				outAttachment.setPayload(payload);
				attachment.setAttachment(outAttachment);
				outgoingMessage.setMessage(attachment);
				outgoingService.sendMessage(outgoingMessage);
				break;

			}
			case GET_HELP_FINISHED: {
				final TextMessage msg = new TextMessage();
				msg.setText(GET_STARTED_MESSAGE);
				outgoingMessage.setMessage(msg);
				outgoingService.sendMessage(outgoingMessage);
				break;
			}
			case BOOK_INQUIRY_FINISHED: {
				break;
			}
			case STORE_LOCATE_FINISHED: {
				final AttachmentMessage attachment = new AttachmentMessage();
				int onlineCost = ((int) (1000000 * Math.random()) % 12211) + 159;
				final Element shipOnline = new Element("Ship online. Cost: " + onlineCost + "Rs.",
						"Ship online and schedule a courier to pick up your parcel at your home or office",
						"https://www.mydhl.dhl.com/content/dam/Local_Images/g0/express/mydhl/shipping_click.png");

				int dropoffCost = ((int) (1000000 * Math.random()) % 12211) + 127;
				final Element dropOff = new Element("Drop off. Cost: " + dropoffCost + "Rs.",
						"An easy way to send documents and parcels – just drop off your parcel at the nearest DHL Service Point.",
						"https://www.mydhl.dhl.com/content/dam/Local_Images/g0/express/mydhl/shipping_walk.png");

				final ArrayList<Element> elements = new ArrayList<>();
				elements.add(shipOnline);
				elements.add(dropOff);

				final Payload payload = new Payload();
				payload.setTemplateType("list");
				payload.setTopElementStyle("compact");
				payload.setElements(elements);

				final OutgoingAttachment outAttachment = new OutgoingAttachment();
				outAttachment.setPayload(payload);
				attachment.setAttachment(outAttachment);
				outgoingMessage.setMessage(attachment);
				outgoingService.sendMessage(outgoingMessage);
				break;
			}
			default: {

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
				case ASK_INQUIRY_TYPE_CONTEXT: {
					final QuickReplyOption tracking = new QuickReplyOption("text", "Tracking", "Tracking");
					final QuickReplyOption service = new QuickReplyOption("text", "Service", "Service");
					final QuickReplyOption website = new QuickReplyOption("text", "Website", "Website");
					final QuickReplyOption technical = new QuickReplyOption("text", "Technical", "Technical");
					final QuickReplyOption sales = new QuickReplyOption("text", "Sales", "Sales");
					final QuickReplyOption complaint = new QuickReplyOption("text", "Complaint", "Complaint");
					final QuickReplyOption other = new QuickReplyOption("text", "Other", "Other");

					text.addQuickReplyOption(tracking);
					text.addQuickReplyOption(service);
					text.addQuickReplyOption(website);
					text.addQuickReplyOption(technical);
					text.addQuickReplyOption(sales);
					text.addQuickReplyOption(complaint);
					text.addQuickReplyOption(other);

					break;
				}

				default: {

				}
				}
			}

		}
	}
}
