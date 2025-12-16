package com.chat.chats.repository;

import com.chat.chats.models.Chat;
import com.chat.config.DatabaseConfig;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ChatRepositoryMongoDB implements ChatRepository {
    private final MongoCollection<Document> collection;

    public ChatRepositoryMongoDB() {
        MongoDatabase database = DatabaseConfig.getDatabase();
        this.collection = database.getCollection("chats");
    }

    @Override
    public Chat findById(String id) {
        Document doc = collection.find(new Document("chatid", id)).first();
        if (doc == null) doc = collection.find(new Document("id", id)).first();
        if (doc != null) {
            Integer cid = doc.getInteger("chatid");
            int chatid = cid != null ? cid : (doc.containsKey("id") ? Integer.parseInt(String.valueOf(doc.get("id"))) : 0);
            return new Chat(chatid, doc.getString("chat_name"), doc.getString("created_at"));
        }
        return null;
    }

    @Override
    public List<Chat> findAll() {
        List<Chat> chats = new ArrayList<>();
        for (Document doc : collection.find()) {
            Integer cid = doc.getInteger("chatid");
            int chatid = cid != null ? cid : 0;
            chats.add(new Chat(chatid, doc.getString("chat_name"), doc.getString("created_at")));
        }
        return chats;
    }

    @Override
    public void save(Chat chat) {
        Document doc = new Document("chatid", chat.getChatId())
                .append("chat_name", chat.getChatName())
                .append("created_at", chat.getCreatedAt());
        collection.insertOne(doc);
    }

    @Override
    public void update(Chat chat) {
        Document query = new Document("chatid", chat.getChatId());
        Document update = new Document("$set", new Document("chat_name", chat.getChatName())
                .append("created_at", chat.getCreatedAt()));
        collection.updateOne(query, update);
    }

    @Override
    public void delete(String id) {
        try {
            int val = Integer.parseInt(id);
            collection.deleteOne(new Document("chatid", val));
        } catch (NumberFormatException e) {
            collection.deleteOne(new Document("chatid", id));
            collection.deleteOne(new Document("id", id));
        }
    }
}
