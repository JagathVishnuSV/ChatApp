package com.chat.user.repository;

import com.chat.user.models.User;
import java.util.*;
import com.mongodb.client.*;
import org.bson.Document;

public class UserRepositoryMongoDB implements UserRepository {
    private final MongoCollection<Document> collection;

    public UserRepositoryMongoDB(MongoDatabase database) {
        this.collection = database.getCollection("users");
    }

    @Override
    public User findById(String id) {
        Document doc = collection.find(new Document("id", id)).first();
        if (doc != null) {
            return new User(doc.getString("id"), doc.getString("username"), doc.getString("email"));
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        for (Document doc : collection.find()) {
            users.add(new User(doc.getString("id"), doc.getString("username"), doc.getString("email")));
        }
        return users;
    }

    @Override
    public void save(User user) {
        Document doc = new Document("id", user.getId())
                .append("username", user.getUsername())
                .append("email", user.getEmail());
        collection.insertOne(doc);
    }

    @Override
    public void update(User user) {
        Document query = new Document("id", user.getId());
        Document update = new Document("$set", new Document("username", user.getUsername())
                .append("email", user.getEmail()));
        collection.updateOne(query, update);
    }

    @Override
    public void delete(String id) {
        collection.deleteOne(new Document("id", id));
    }

    @Override
    public User findByUsername(String username) {
        Document doc = collection.find(new Document("username", username)).first();
        if (doc != null) {
            return new User(doc.getString("id"), doc.getString("username"), doc.getString("email"));
        }
        return null;
    }
}