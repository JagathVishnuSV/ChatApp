package com.chat.message.repository;

import com.chat.message.models.Message;
import com.chat.config.DatabaseConfig;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MessageRepositoryMongoDB implements MessageRepository {
    private final MongoCollection<Document> collection;

    public MessageRepositoryMongoDB() {
        MongoDatabase database = DatabaseConfig.getDatabase();
        this.collection = database.getCollection("messages");
    }

    @Override
    public Message findById(String id) {
        Document doc = collection.find(new Document("messageid", id)).first();
        if (doc != null) {
            return new Message(
                doc.getInteger("messageid"),
                doc.getInteger("chatid"),
                doc.getInteger("senderid"),
                doc.getString("message_content"),
                doc.getString("message_type"),
                doc.getString("status"),
                doc.getString("seen_at"),
                doc.getString("sent_at")
            );
        }
        return null;
    }

    @Override
    public List<Message> findAll() {
        List<Message> messages = new ArrayList<>();
        for (Document doc : collection.find()) {
            messages.add(new Message(
                doc.getInteger("messageid"),
                doc.getInteger("chatid"),
                doc.getInteger("senderid"),
                doc.getString("message_content"),
                doc.getString("message_type"),
                doc.getString("status"),
                doc.getString("seen_at"),
                doc.getString("sent_at")
            ));
        }
        return messages;
    }

    @Override
    public void save(Message message) {
        Document doc = new Document("messageid", message.getMessageId())
                .append("chatid", message.getChatId())
                .append("senderid", message.getSenderId())
                .append("message_content", message.getMessageContent())
                .append("message_type", message.getMessageType())
                .append("status", message.getStatus())
                .append("seen_at", message.getSeenAt())
                .append("sent_at", message.getSentAt());
        collection.insertOne(doc);
    }

    @Override
    public void update(Message message) {
        Document query = new Document("messageid", message.getMessageId());
        Document update = new Document("$set", new Document("chatid", message.getChatId())
                .append("senderid", message.getSenderId())
                .append("message_content", message.getMessageContent())
                .append("message_type", message.getMessageType())
                .append("status", message.getStatus())
                .append("seen_at", message.getSeenAt())
                .append("sent_at", message.getSentAt()));
        collection.updateOne(query, update);
    }

    @Override
    public void delete(String id) {
        collection.deleteOne(new Document("messageid", id));
    }
}