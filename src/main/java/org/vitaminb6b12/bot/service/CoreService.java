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
import org.vitaminb6b12.bot.model.TextMessage;

@Service
public class CoreService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CoreService.class.getName());
	private static final TaskExecutor executor = new SimpleAsyncTaskExecutor();

	@Autowired
	private OutgoingService outgoingService;

	public void handleIncomingMessage(IncomingMessage incomingMessage) {
		synchronized (executor) {
			executor.execute(new MessageHandler(incomingMessage));
		}
	}

	class MessageHandler implements Runnable {
		private final IncomingMessage incomingMessage;

		public MessageHandler(IncomingMessage incomingMessage) {
			this.incomingMessage = incomingMessage;
		}

		@Override
		public void run() {

			final List<PageEntry> entryList = incomingMessage.getEntry();
			for (PageEntry entry : entryList) {
				// Assuming that the webhook is configured for only and only
				// one
				// page
				// Thus we do not have to worry about the id field
				final List<Messaging> messagingList = entry.getMessaging();
				for (Messaging messaging : messagingList) {
					LOGGER.info(messaging.toString());
					final OutgoingMessage outgoingMessage = new OutgoingMessage();
					outgoingMessage.setRecipient(messaging.getSender());
					final TextMessage text = new TextMessage();
					try {
						if (messaging instanceof GenericMessaging) {
							final GenericMessaging message = (GenericMessaging) messaging;
							final Message receivedMessage = message.getMessage();
							if (receivedMessage.getText() == null) {
								if (receivedMessage.getStickerId() != 0L) {
									text.setText("Even I love stickers! \u263A");
								} else if (receivedMessage.getAttachments() != null) {
									text.setText("Hmm... I smell attachments. \u263A");
								} else {
									text.setText("I literally have no idea what did you send \uD83D\uDE10");
								}
							} else {
								text.setText(message.getMessage().getText());
							}
						} else if (messaging instanceof PostbackMessaging) {
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
						outgoingMessage.setMessage(text);
						outgoingService.sendMessage(outgoingMessage);
					}

				}
			}

		}
	}
}
