package org.vitaminb6b12.bot.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.vitaminb6b12.bot.model.Conversation;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, Long> {
}
