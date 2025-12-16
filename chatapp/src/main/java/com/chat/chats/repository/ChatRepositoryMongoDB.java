package com.chat.chats.repository;

import com.chat.chats.models.Chat;
import com.mongodb.client.*;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class ChatRepositoryMongoDB implements ChatRepository {
    private final MongoCollection<Document> collection;

    public ChatRepositoryMongoDB(MongoDatabase database) {
        this.collection = database.getCollection("chats");
    }

    @Override
    public Chat findById(String id) {
        Document doc = collection.find(new Document("id", id)).first();
        if (doc != null) {
            return new Chat(doc.getString("id"), doc.getString("name"), doc.getList("participants", String.class));
        }
        return null;
    }

    @Override
    public List<Chat> findAll() {
        List<Chat> chats = new ArrayList<>();
        for (Document doc : collection.find()) {
            chats.add(new Chat(doc.getString("id"), doc.getString("name"), doc.getList("participants", String.class)));
        }
        return chats;
    }

    @Override
    public void save(Chat chat) {
        Document doc = new Document("id", chat.getId())
                .append("name", chat.getName())
                .append("participants", chat.getParticipants());
        collection.insertOne(doc);
    }

    @Override
    public void update(Chat chat) {
        Document query = new Document("id", chat.getId());
        Document update = new Document("$set", new Document("name", chat.getName())
                .append("participants", chat.getParticipants()));
        collection.updateOne(query, update);
    }

    @Override
    public void delete(String id) {
        collection.deleteOne(new Document("id", id));
    }
}