package com.chat.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DatabaseConfig {
    private static MongoDatabase database;

    public static MongoDatabase getDatabase() {
        if (database == null) {
            String uri = "mongodb://localhost:27017";
            String dbName = "chat_application";

            MongoClient mongoClient = MongoClients.create(uri);
            database = mongoClient.getDatabase(dbName);
        }
        return database;
    }
}