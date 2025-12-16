package com.chat.user.repository;

import com.chat.user.models.User;
import com.chat.config.DatabaseConfig;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryMongoDB implements UserRepository {
    private final MongoCollection<Document> collection;

    public UserRepositoryMongoDB() {
        MongoDatabase database = DatabaseConfig.getDatabase();
        this.collection = database.getCollection("users");
    }

    @Override
    public User findById(String id) {
        Document doc = collection.find(new Document("userid", id)).first();
        if (doc == null) doc = collection.find(new Document("id", id)).first();
        if (doc != null) {
            Integer uid = doc.getInteger("userid");
            int userid = uid != null ? uid : (doc.containsKey("id") ? Integer.parseInt(String.valueOf(doc.get("id"))) : 0);
            return new User(userid,
                    doc.getString("username"),
                    doc.getString("phone_no"),
                    doc.getString("profile_url"),
                    doc.getString("end_to_end_Key"),
                    doc.getString("created_at"),
                    doc.getString("bio")
            );
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        for (Document doc : collection.find()) {
            Integer uid = doc.getInteger("userid");
            int userid = uid != null ? uid : 0;
            users.add(new User(
                    userid,
                    doc.getString("username"),
                    doc.getString("phone_no"),
                    doc.getString("profile_url"),
                    doc.getString("end_to_end_Key"),
                    doc.getString("created_at"),
                    doc.getString("bio")
            ));
        }
        return users;
    }

    @Override
    public void save(User user) {
        Document doc = new Document("userid", user.getUserId())
                .append("username", user.getUsername())
                .append("phone_no", user.getPhone_no())
                .append("profile_url", user.getProfile_url())
                .append("end_to_end_Key", user.getPublicKey())
                .append("created_at", user.getCreated_at())
                .append("bio", user.getBio());
        collection.insertOne(doc);
    }

    @Override
    public void update(User user) {
        Document query = new Document("userid", user.getUserId());
        Document update = new Document("$set", new Document("username", user.getUsername())
                .append("phone_no", user.getPhone_no())
                .append("profile_url", user.getProfile_url())
                .append("end_to_end_Key", user.getPublicKey())
                .append("created_at", user.getCreated_at())
                .append("bio", user.getBio()));
        collection.updateOne(query, update);
    }

    @Override
    public void delete(String id) {
        try {
            int val = Integer.parseInt(id);
            collection.deleteOne(new Document("userid", val));
        } catch (NumberFormatException e) {
            collection.deleteOne(new Document("userid", id));
            collection.deleteOne(new Document("id", id));
        }
    }

    @Override
    public User findByUsername(String username) {
        Document doc = collection.find(new Document("username", username)).first();
        if (doc != null) {
            Integer uid = doc.getInteger("userid");
            int userid = uid != null ? uid : 0;
            return new User(userid,
                    doc.getString("username"),
                    doc.getString("phone_no"),
                    doc.getString("profile_url"),
                    doc.getString("end_to_end_Key"),
                    doc.getString("created_at"),
                    doc.getString("bio")
            );
        }
        return null;
    }
}
